package fis.pms.service.dto;

import fis.pms.domain.Worker;
import fis.pms.domain.fileEnum.F_process;
import fis.pms.repository.dto.WorkListGroupByWorkerAndProcessDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@Builder
public class WorkerPerformanceDTO {
    private Long id;
    private String name;
    private Map<F_process, WorkerWorkListDTO> work = new HashMap<>();

    public WorkerPerformanceDTO(Worker worker){
        id = worker.getId();
        name = worker.getW_name();
    }

    public void addMap(Map<F_process, WorkerWorkListDTO> processSet) {
        work = processSet;
        for(F_process process : F_process.values()){
            if(!work.containsKey(process)){
                work.put(process, new WorkerWorkListDTO(0L));
            }
        }
    }

    public void switchNullMapToDefault(){
        if(work == null){
            work = new HashMap<>();
            for(F_process process : F_process.values()){
                work.put(process, new WorkerWorkListDTO(0L));
            }
        }
    }
}
