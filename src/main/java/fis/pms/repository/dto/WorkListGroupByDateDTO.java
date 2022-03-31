package fis.pms.repository.dto;

import fis.pms.domain.fileEnum.F_process;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class WorkListGroupByDateDTO {
    String name;
    F_process process;
    LocalDate date;
    Long count;
}
