package fis.pms.controller.dto;

import fis.pms.domain.caseEnum.C_kperiod;
import fis.pms.domain.fileEnum.F_type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
* 작성자: 한명수
* 작성날짜: 2021/08/24
* 작성내용: IndexSearchCaseResponse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndexSearchCaseResponse {
    private String f_labelcode;     // 철 레이블 번호
    private String f_volumeamount;  // 권호수
    private String c_pdate;     // 생산등록일자
    private String c_dodate;    // 시행일자
    private String c_title;     // 건 제목
    private String c_spage;     // 첫 페이지
    private String c_epage;     // 끝 페이지
    private String c_page;      //페이지(쪽수)
    private String c_oldnum;    //문서번호(구기록물 문서번호)
    private C_kperiod c_kperiod;   // 보존기간
    private String o_code;  // 기관코드
    private String c_departmentname;  // 생산기관명
    private F_type f_type;  // 기록물형태
    private String c_class;     // 등록구분
}
