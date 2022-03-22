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
* 작성내용: IndexSearchLabelResponse
* */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndexSearchLabelResponse {
    private Long f_id; // 철아이디
    private String f_labelcode;  // 철번호
    private String f_volumeamount;   // 권호수
    private String f_name;   // 철이름
    private String f_pyear;  // 철생산년도
    private String f_eyear;  // 종료년도
    private F_kperiod f_kperiod;    // 철보존기간
    private String o_code;   // 기관코드
    private String o_name;   // 생산기관명
    private F_type f_type;   // 기록물형태
    private F_kmethod f_kmethod;    // 보존방법
    private F_kplace f_kplace; // 보존장소
    private String f_manager;    // 업무담당자
}
