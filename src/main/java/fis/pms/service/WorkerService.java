package fis.pms.service;

import fis.pms.controller.dto.WorkerFormRequest;
import fis.pms.domain.Worker;
import fis.pms.exception.WorkerException;
import fis.pms.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class WorkerService {

    private final WorkerRepository workerRepository;

    public Worker saveWorker(Worker worker) {
        validateDuplicateWorker(worker);
        workerRepository.save(worker);
        return worker;
    }

    private void validateDuplicateWorker(Worker worker) {
        Optional<Worker> findWorker = workerRepository.findByNickname(worker.getNickname());
        if(!findWorker.isEmpty()) {
            throw new WorkerException("이미 존재하는 닉네임입니다.");
        }
    }

    public Worker updateWorker(Long id, WorkerFormRequest request) {
        Worker worker = workerRepository.findOne(id)
                .orElseThrow(() -> new WorkerException("업데이트 하려고 하는 유저가 존재하지 않습니다"));
        worker.updateWorker(request);
        validateDuplicateWorker(worker);
        return worker;
    }

    public Long remove(Long w_id) {
        return workerRepository.remove(w_id);
    }

    public List<Worker> findAll() {
        return workerRepository.findAll();
    }
}
