package fis.pms.repository.dto;

import fis.pms.domain.fileEnum.F_process;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class WorkListGroupByWorkerAndProcessDTO {
    private Long workerId;
    private F_process process;
    private Long count;
}
