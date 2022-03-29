package fis.pms.service;

import fis.pms.repository.WorkListRepository;
import fis.pms.repository.dto.PerformanceDTO;
import fis.pms.service.dto.OverallPerformanceDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class WorkListService {

    private final WorkListRepository workListRepository;

    public void getOverallPerformance() {
        List<PerformanceDTO> dto = workListRepository.getOverallPerformance();
        log.info("==");
    }
}
