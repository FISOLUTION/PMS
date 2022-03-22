package fis.pms.controller.dto;

import com.querydsl.core.annotations.QueryProjection;
import fis.pms.domain.fileEnum.F_kperiod;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class VolumesInfo {
    private Long v_id;
    private String f_labelcode;
    private Integer v_num;
    private String f_name;
    private String f_pyear;
    private String f_eyear;
    private F_kperiod f_kperiod;
    List<CasesInfo> casesInfoList;

    @QueryProjection
    public VolumesInfo(Long v_id, String f_labelcode, Integer v_num, String f_name, String f_pyear, String f_eyear, F_kperiod f_kperiod){
        this.v_id = v_id;
        this.f_labelcode = f_labelcode;
        this.v_num = v_num;
        this.f_name = f_name;
        this.f_pyear = f_pyear;
        this.f_eyear = f_eyear;
        this.f_kperiod = f_kperiod;
    }

}
