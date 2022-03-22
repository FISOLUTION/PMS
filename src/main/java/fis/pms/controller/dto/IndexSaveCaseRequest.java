package fis.pms.controller.dto;

import fis.pms.domain.caseEnum.C_edoc;
import fis.pms.domain.caseEnum.C_kperiod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/*
* 작성자: 한명수
* 작성날짜: 2021/08/24
* 작성내용: IndexSaveCaseRequest
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndexSaveCaseRequest {
    private Long c_id;
    private String c_spage;                 // 첫 페이지
    private String c_epage;                 // 끝 페이지
    private String c_page;                  // 쪽수
    private String c_class;                 // 등록구분
    private String c_dodate;                // 시행일자
    private String c_pdate;                 // 생산등록일자
    private String c_departmentname;        // 부서명
    private String c_oldnum;                // 문서번호
    private C_kperiod c_kperiod;            // 보존기간
    private String c_title;                 // 건 제목
    private String c_drafter;               // 기안자
    private String c_approver;              // 결재권자
    private String c_receiver;              // 수발신자
    private C_edoc c_edoc;                  // 전자기록물여부
    private String c_openable;              // 공개여부
    private String c_hidden;                // 공개제한부분표시
}
