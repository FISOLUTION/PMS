package fis.pms.repository.dto;

import fis.pms.domain.fileEnum.F_process;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class WorkListGroupByFileDTO {
    private String labelCode;
    private String fileName;
    private String OfficeName;
    private F_process process;
    private String workerName;
    private LocalDate date;
}
