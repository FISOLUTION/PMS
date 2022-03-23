package fis.pms.controller.dto;

import fis.pms.domain.F_location;
import fis.pms.domain.Files;
import fis.pms.domain.Office;
import fis.pms.domain.fileEnum.F_construct;
import fis.pms.domain.fileEnum.F_kperiod;
import fis.pms.domain.fileEnum.F_kplace;
import fis.pms.domain.fileEnum.F_type;
import fis.pms.service.dto.PreInfoFileInfo;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class PreInfoFileUpdateInfo {
    private Long f_id;
    private String o_code;      // 기관코드
    private String o_name;
    @Length(max = 6)
    private String f_labelcode; // 레이블
    private String f_name;      // 철이름
    @Length(max = 4)
    private String f_pyear;     // 생산년도
    private F_kperiod f_kperiod;     // 보존기간
    private F_construct f_db;        // 구축여부
    private F_construct f_scan;      // 스캔여부
    @Length(max = 3)
    private String b_num;       // 박스번호
    private F_location f_location;  // 위치(서가, 층, 열, 번)
    private F_kplace f_kplace;  // 보존장소
    private F_type f_type;      // 문서종류
    private String f_typenum;   // 분류번호

}
