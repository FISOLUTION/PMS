package fis.pms.service;

import fis.pms.domain.WorkPlan;
import fis.pms.repository.WorkListRepository;
import fis.pms.repository.dto.PerformanceDTO;
import fis.pms.service.dto.OverallPerformanceDTO;
import fis.pms.service.dto.PreparePlanDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class WorkListService {

    private final WorkListRepository workListRepository;

    public OverallPerformanceDTO getOverallPerformance() {
        List<PerformanceDTO> dto = workListRepository.getPerformanceList();
        return OverallPerformanceDTO.createOverall(dto);
    }


    public Map<String, PreparePlanDTO> prepareWithPlan(WorkPlan workPlan) {
        Map<String, PreparePlanDTO> map = new HashMap();
        workListRepository.getPerformanceList().forEach(performanceDTO -> {
            map.put(performanceDTO.getName(), PreparePlanDTO.create(performanceDTO));
        });
        workPlan.putInto(map);
        return map;
    }
}
