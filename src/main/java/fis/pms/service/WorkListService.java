package fis.pms.service;

import fis.pms.domain.WorkPlan;
import fis.pms.domain.fileEnum.F_process;
import fis.pms.repository.WorkListRepository;
import fis.pms.repository.WorkerRepository;
import fis.pms.repository.dto.PerformanceDTO;
import fis.pms.repository.dto.WorkListGroupByFileDTO;
import fis.pms.repository.dto.WorkListGroupByWorkerAndProcessDTO;
import fis.pms.service.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class WorkListService {

    private final WorkListRepository workListRepository;
    private final WorkerRepository workerRepository;

    /**
     *
     * @return
     */
    public OverallPerformanceDTO getOverallPerformance() {
        List<PerformanceDTO> dto = workListRepository.getPerformanceList();
        return OverallPerformanceDTO.createOverall(dto);
    }

    /**
     *
     * @param workPlan
     * @return
     */
    public Map<String, PreparePlanDTO> prepareWithPlan(WorkPlan workPlan) {
        Map<String, PreparePlanDTO> map = new HashMap<>();
        workListRepository.getPerformanceList().forEach(performanceDTO -> {
            map.put(performanceDTO.getName(), PreparePlanDTO.create(performanceDTO));
        });
        workPlan.putInto(map);
        return map;
    }

    /**
     *
     * @param startDate
     * @param endDate
     * @return
     */
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

    /**
     *
     * @param date
     * @return
     */
    public Map<Long, WorkerPerformanceDTO> getWorkPerformanceWorker(LocalDate date) {
        Map<Long, WorkerPerformanceDTO> result = workerRepository.findAll().stream()
                .map(WorkerPerformanceDTO::new)
                .collect(Collectors.toMap(workerPerformanceDTO -> workerPerformanceDTO.getId(), workerPerformanceDTO -> workerPerformanceDTO));

        Map<Long, List<WorkListGroupByWorkerAndProcessDTO>> workListGroup = workListRepository.workListGroupByWorkerAndProcess(date).stream()
                .collect(Collectors.groupingBy(WorkListGroupByWorkerAndProcessDTO::getWorkerId));

        workListGroup.forEach((aLong, workListGroupByWorkerAndProcessDTOS) -> {
            Map<F_process, WorkerWorkListDTO> processSet = workListGroupByWorkerAndProcessDTOS.stream()
                    .collect(Collectors.toMap(WorkListGroupByWorkerAndProcessDTO::getProcess, WorkerWorkListDTO::new));
            result.get(aLong).addMap(processSet);
        });

        result.forEach((aLong, workerPerformanceDTO) -> {
            workerPerformanceDTO.switchNullMapToDefault();
        });

        return result;
    }

    /**
     *
     * @return
     */
    public Map<String, FileWorkListDTO> getFilesWorkList() {
        Map<String, List<WorkListGroupByFileDTO>> fileProcessMap = workListRepository.WorkListGroupByFile().stream()
                .collect(Collectors.groupingBy(workListGroupByFileDTO -> workListGroupByFileDTO.getLabelCode()));

        Map<String, FileWorkListDTO> resultList = new HashMap<>();

        fileProcessMap.forEach((labelCode, workListGroupByFileDTOS) -> {
            WorkListGroupByFileDTO temp = workListGroupByFileDTOS.get(0);
            FileWorkListDTO result = FileWorkListDTO.builder().fileName(temp.getFileName()).OfficeName(temp.getOfficeName()).build();

            result.setFileWorkInfo(workListGroupByFileDTOS.stream().collect(Collectors.toMap(
                    workListGroupByFileDTO -> workListGroupByFileDTO.getProcess(),
                    workListGroupByFileDTO -> new FileWorkInfo(workListGroupByFileDTO))));

            result.completeDTO();

            resultList.put(temp.getLabelCode(), result);
        });

        return resultList;
    }
}
