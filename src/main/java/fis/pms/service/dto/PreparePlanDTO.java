package fis.pms.service.dto;

import fis.pms.domain.fileEnum.F_process;
import fis.pms.repository.dto.PerformanceDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PreparePlanDTO {
    private F_process f_process;
    private String name;
    private Long plan;
    private Long count;

    public static PreparePlanDTO create(PerformanceDTO dto){
        return PreparePlanDTO.builder()
                .f_process(dto.getF_process())
                .name(dto.getName())
                .count(dto.getCount())
                .build();
    }
}
