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

    /**
     * @throws WorkerException
     * @param worker 저장할 작업자 객체
     * @return 저장 완료한 작업자 객체
     * @author 현승구
     */
    public Worker saveWorker(Worker worker) {
        validateDuplicateWorker(worker);
        workerRepository.save(worker);
        return worker;
    }

    /**
     * @implNote 작업자 등록 수정시에 닉네임 존재 유무로 유효성 검사
     * @throws WorkerException
     * @param worker
     * @author 현승구
     */
    private void validateDuplicateWorker(Worker worker) {
        Optional<Worker> findWorker = workerRepository.findByNickname(worker.getNickname());
        if(!findWorker.isEmpty()) {
            throw new WorkerException("이미 존재하는 닉네임입니다.");
        }
    }

    /**
     * @param id 작업자 id
     * @param request 업데이트 할 정보 객체
     * @throws WorkerException
     * @return 업데이트를 완료한 작업자의 정보를 반환
     * @author 현승구
     */
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
