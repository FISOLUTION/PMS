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
    private double rate;

    public static PreparePlanDTO create(PerformanceDTO dto){
        return PreparePlanDTO.builder()
                .f_process(dto.getF_process())
                .name(dto.getName())
                .count(dto.getCount())
                .build();
    }

    public PreparePlanDTO putPlan(Long plan){
        this.plan = plan;
        return this;
    }

    public void calculateRate(){
        rate = (double)count / plan * 100;
    }
}
