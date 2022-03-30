package fis.pms.service;

import fis.pms.domain.Files;
import fis.pms.domain.WorkList;
import fis.pms.domain.Worker;
import fis.pms.domain.fileEnum.F_process;
import fis.pms.exception.WorkerException;
import fis.pms.repository.WorkListRepository;
import fis.pms.repository.WorkerRepository;
import fis.pms.repository.dto.PerformanceDTO;
import fis.pms.service.dto.OverallPerformanceDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class WorkListService {

    private final WorkListRepository workListRepository;
    private final WorkerRepository workerRepository;

    public void getOverallPerformance() {
        List<PerformanceDTO> dto = workListRepository.getOverallPerformance();
        log.info("==");
    }

    public void createWorkList(Files file, Long workerId, F_process f_process) {
        Worker worker = workerRepository.findOne(workerId).orElseThrow(() -> new WorkerException("존재하지 않는 사용자입니다."));
        WorkList workList = WorkList.createWorkList(file, worker, f_process);
        workListRepository.save(workList);
    }
}
