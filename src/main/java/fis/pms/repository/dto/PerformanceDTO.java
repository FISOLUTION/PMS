package fis.pms.repository.dto;

import fis.pms.domain.fileEnum.F_process;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PerformanceDTO {
    private Long count;
    private F_process f_process;
    private String name;
}
