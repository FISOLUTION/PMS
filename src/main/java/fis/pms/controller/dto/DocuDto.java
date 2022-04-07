package fis.pms.controller.dto;

import fis.pms.domain.Cases;
import fis.pms.domain.Files;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class DocuDto {
    private String o_code; //처리과 코드
    private String o_name; //처리과 명
    private String f_unitcode; //단위업무코드
    private String f_pyear; //생산년도
    private String f_smallfunc; //소기능코드
    private String f_inherlabelcode; //기록물 철 등록 일련번호
    private String f_volumecount; //권호수
    private String f_name; // 철제목
    private String f_subname; // 기타제목
    private String f_type; // 기록물 형태
    private String f_syear; //시작년도
    private String f_eyear; // 종료년도
    private String f_kperiod; //보존기간
    private String f_kmethod; //보존방법
    private String f_place;
    private String f_placeenddate; // 비치 종결일자
    private String f_placereason; // 비치 사유
    private String f_manager; // 업무담당자
    private String f_oldfileclassifynum;    //구기록물철분류번호
    private String f_newold;    //기록물구분(1.신기록물, 2.구기록물)
    private String f_modify;    //수정여부 (0.해당없음 1. 수정함)
    private String f_regnum;    //기록물등록건수
    private String f_page;  //기록물쪽수
    private String f_efilenum;  //전자파일갯수
    private String f_inheritance;   //인수인계구분 (0.없음 1.인수 2.인계)
    private String f_inherunitcode; //단위업무코드

    public DocuDto(Files files){
        List<Cases> casesList = files.getCases();
        Long pages = 0L;
        for(Cases c : casesList){
            pages += Long.parseLong(c.getC_page());
        }
        this.o_code = files.getOffice().getO_code();
        this.o_name = files.getOffice().getO_name();
        this.f_unitcode = "99999999";
        this.f_pyear = files.getF_pyear();
        this.f_inherlabelcode = files.getF_inherlabelcode();
        this.f_volumecount = files.getF_volumecount();
        this.f_name = files.getF_name();
        this.f_type = files.getF_type().getType();
        this.f_syear = files.getF_pyear();
        this.f_eyear = files.getF_eyear();
        this.f_kperiod = files.getF_kperiod().getKperiod();
        this.f_kmethod = files.getF_kmethod().getKmethod();
        this.f_place = files.getF_kplace().getKplace();
        this.f_newold = files.getF_newold().getNewold();
        this.f_modify = "0";
        this.f_regnum = String.format("06d%",casesList.size());
        this.f_page = String.format("06d%",pages);
        this.f_efilenum = "000000";
        this.f_inheritance = "0";
    }
}

