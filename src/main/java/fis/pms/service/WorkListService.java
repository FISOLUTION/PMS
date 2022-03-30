package fis.pms.service;

import fis.pms.domain.WorkPlan;
import fis.pms.repository.WorkListRepository;
import fis.pms.repository.dto.PerformanceDTO;
import fis.pms.service.dto.OverallPerformanceDTO;
import fis.pms.service.dto.PreparePlanDTO;
import fis.pms.service.dto.WorkListOverallGroupByDateDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

    public Map<LocalDate, WorkListOverallGroupByDateDTO> getWorkPerformancePeriod(LocalDate startDate, LocalDate endDate) {
        Map<LocalDate, WorkListOverallGroupByDateDTO> map = new HashMap<>();
        workListRepository.findWorkListByDate(startDate, endDate)
                .forEach(dto -> {
                    if(!map.containsKey(dto.getDate())){
                        map.put(dto.getDate(), new WorkListOverallGroupByDateDTO());
                    }
                    WorkListOverallGroupByDateDTO workListOverallGroupByDateDTO = map.get(dto.getDate());
                    workListOverallGroupByDateDTO.matchPerformanceProcess(dto);
                });

        map.forEach((date, workListOverallGroupByDateDTO) -> {
            workListOverallGroupByDateDTO.makeNullCountToZero();
        });
        return map;
    }
}
