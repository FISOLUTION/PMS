package fis.pms.controller.dto;

import fis.pms.domain.Cases;
import fis.pms.domain.Files;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailDto {
    private String o_code; // 기관 코드
    private String o_name; // 생산 기관명
    private String c_class; // 등록 구분
    private String c_pdate; //생산등록일자 12
    private String c_pnum; //생산등록번호 13
    private String c_oldnum; //구기록물 문서번호 30
    private String c_attachnum; //분리등록번호 2 (무조건 00)
    private String c_title; //건 제목 500
    private String c_subtitle; //기타제목 200무
    private String c_page; //쪽수 6
    private String c_approver; //결재권자 (직급) 40
    private String c_reviewer; //검토자 100
    private String c_helper; //협조자 100
    private String c_drafter; //기안자 40
    private String c_signdate; //결재일자 8
    private String c_dodate; //시행일자 8
    private String c_receiver; //수(발)신자 100
    private String c_distrinum; //문서과 배부번호 6
    private String c_pofficenum; //생산기관 등록번호 30
    private String c_attachamount; //첨부수 4
    private String c_attachtype; //첨부물 종류 2 (01.오디오, 02.복합, 03.이미지(default), 04.문서, 05.비디오, 99.기타)
    private String c_attachetc; //첨부물 기타 20 (if c_attachtype == 99)
    private String c_lang; //언어 3
    private String c_edoc; // 전자기록물 여부 (1.전자기록물, 2.비전자기록물)
    private String c_groupnum; //분류번호 28 (처리기관코드 7 + 단위업무코드 8 + 생산년도 4 +기록물철등록일련번호(레이블번호) 6 + 권호수 3)
    private String c_specialdoc; //특수기록물 5 (Y와 N으로 해당 값 체크, 1대통령관련 2비밀 3개별관리 4저작권보호 5특수규격기록물, if 전부 해당 없음 => NNNNN)
    private String c_openable; //공개여부 9 (테이블 명세서 참고,,,)
    private String c_hidden; //공개제한부분표시 100
    private String c_opendate; //공개시기 100
    private String c_kperiod; //보존기간 2 (01.1년, 03.3년, 05.5년, 10.10년, 20.20년, 30.준영구, 40.영구)
    private String c_videosummary; //시청각 내용요약 500
    private String c_type; //시청각 기록물 형태 50
    private String c_newold; //기록물 구분 1 (1.신기록물, 2.구기록물)
    private String c_modify; //수정여부 1 (0.해당없음, 1.수정함)
    private String c_companion; //반려여부 1 (0.해당없음, 1.반려)
    private String c_summary; //건 내용요약 500
    private String c_format; //포맷 1 (1.문서, 2.오디오, 3.복합, 4.이미지(default), 5.비디오, 9.기타)
    private String c_formatetc; //포맷기타 20 (if c_format == 9)
    private String c_storage; // 저장매체 2 (01, 02, 03, ...)

    public static DetailDto createDetailDto(Cases cases, Files files) {
        DetailDto dto = new DetailDto();
        dto.o_code = files.getOffice().getO_code();
        dto.o_name = files.getOffice().getO_name();
        dto.c_class = cases.getC_class();
        dto.c_pdate = cases.getC_pdate();
        dto.c_pnum = cases.getC_pnum();
        dto.c_oldnum = cases.getC_oldnum();
        dto.c_attachnum = cases.getC_attachnum();
        dto.c_title = cases.getC_title();
        dto.c_subtitle = cases.getC_subtitle();
        dto.c_page = cases.getC_page();
        dto.c_approver = cases.getC_approver();
        dto.c_reviewer = cases.getC_reviewer();
        dto.c_helper = cases.getC_helper();
        dto.c_drafter = cases.getC_drafter();
        dto.c_signdate = cases.getC_signdate();
        dto.c_dodate = cases.getC_dodate();
        dto.c_receiver = cases.getC_receiver();
        dto.c_distrinum = cases.getC_distrinum();
        dto.c_pofficenum = cases.getC_pofficenum();
        dto.c_attachamount = cases.getC_attachamount();
        dto.c_attachtype = cases.getC_attachtype().getAttachtype();
        dto.c_attachetc = cases.getC_attachetc();
        dto.c_lang = cases.getC_lang();
        dto.c_edoc = cases.getC_edoc().getEdoc();
        dto.c_groupnum = cases.getC_groupnum();
        dto.c_specialdoc = cases.getC_specialdoc();
        dto.c_openable = cases.getC_openable();
        dto.c_hidden = cases.getC_hidden();
        dto.c_opendate = cases.getC_opendate();
        dto.c_kperiod = cases.getC_kperiod().getKperiod();
        dto.c_videosummary = cases.getC_videosummary();
        dto.c_type = cases.getC_type();
        dto.c_newold = "2";
        dto.c_modify = "0";
        dto.c_companion = "0";
        dto.c_summary = cases.getC_summary();
        dto.c_format = cases.getC_format().getFormat();
        dto.c_formatetc = cases.getC_formatetc();
        dto.c_storage = cases.getC_storage().getStorage();
        return dto;
    }

    public DetailDto(Cases cases, Files files) {

    }
}
