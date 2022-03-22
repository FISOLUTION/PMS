package fis.pms.controller.dto;

import fis.pms.domain.fileEnum.F_kmethod;
import fis.pms.domain.fileEnum.F_kperiod;
import fis.pms.domain.fileEnum.F_kplace;
import fis.pms.domain.fileEnum.F_type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
* 작성자: 한명수
* 작성날짜: 2021/08/24
* 작성내용: IndexSaveLabelRequest
* */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndexSaveLabelRequest {
    private Long f_id;    // 철아이디
    private String f_name;  // 철이름
    private String f_volumeamount;  // 권호수
    private String f_typenum;   // 분류번호
    private String f_manager;   // 업무담당자
    private F_kperiod f_kperiod;   // 철보존기간
    private F_kmethod f_kmethod;   // 보존방법
    private F_kplace f_kplace;    // 보존장소
    private F_type f_type;  // 기록물형태
}
