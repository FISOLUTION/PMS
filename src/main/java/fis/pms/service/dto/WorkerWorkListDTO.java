package fis.pms.service.dto;

import fis.pms.repository.dto.WorkListGroupByWorkerAndProcessDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class WorkerWorkListDTO {
    private Long count;

    public WorkerWorkListDTO(WorkListGroupByWorkerAndProcessDTO data){
        count = data.getCount();
    }
}
