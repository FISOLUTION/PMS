package fis.pms.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryProjection;
import fis.pms.domain.caseEnum.C_edoc;
import fis.pms.domain.caseEnum.C_kperiod;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CasesInfo {

    @JsonIgnore
    private Long v_id;
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

    @QueryProjection
    public CasesInfo(Long v_id, Long c_id, String c_spage, String c_epage, String c_page, String c_class, String c_dodate, String c_pdate, String c_departmentname, String c_oldnum, C_kperiod c_kperiod, String c_title, String c_drafter, String c_approver, String c_receiver, C_edoc c_edoc, String c_openable, String c_hidden) {
        this.v_id = v_id;
        this.c_id = c_id;
        this.c_spage = c_spage;
        this.c_epage = c_epage;
        this.c_page = c_page;
        this.c_class = c_class;
        this.c_dodate = c_dodate;
        this.c_pdate = c_pdate;
        this.c_departmentname = c_departmentname;
        this.c_oldnum = c_oldnum;
        this.c_kperiod = c_kperiod;
        this.c_title = c_title;
        this.c_drafter = c_drafter;
        this.c_approver = c_approver;
        this.c_receiver = c_receiver;
        this.c_edoc = c_edoc;
        this.c_openable = c_openable;
        this.c_hidden = c_hidden;
    }
}
