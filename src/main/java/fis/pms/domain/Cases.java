package fis.pms.domain;

import fis.pms.controller.dto.IndexSaveCaseRequest;
import fis.pms.domain.caseEnum.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cases {

    public Cases(int caseNum, Volume volume, Files files, String c_spage, String c_epage, String c_page) {
        this.c_first = "1";
        this.volume = volume;
        this.files = files;
        this.c_spage = c_spage;
        this.c_epage = c_epage;
        this.c_page = c_page;
        this.c_num = caseNum;
        volume.getCaseList().add(this);
        files.getCases().add(this);
    }

    @Id @GeneratedValue
    @Column(name = "c_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "v_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Volume volume;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_id")
    private Files files;

    @OneToMany(mappedBy = "case1", cascade = CascadeType.REMOVE)
    private List<Special> specials = new ArrayList<>();

    // 2022-03-08 이승범 : case 순서유지를 위한 권에 속한 각 건들의 번호 부여
    private Integer c_num;

    //@NotBlank
    @Column(length = 6)
    private String c_code; //건 일련번호 6 (not unique)

    //@NotBlank
    @Column(length = 1)
    private String c_first; //최초 작성인지, 수정 작성인지 구분 & 완료되었음을 알림 1?

    //@NotBlank
    @Column(length = 1)
    private String c_class; //등록 구분 1

    //@NotBlank
    @Column(length = 12)
    private String c_pdate; //생산등록일자 12

    //@NotBlank
    @Column(length = 13)
    private String c_pnum; //생산등록번호 13

    //@NotBlank
    @Column(columnDefinition="varchar(2) default '00'")
    private String c_attachnum; //분리등록번호 2 (무조건 00)

    //@NotBlank
    @Column(length = 500)
    private String c_title; //건 제목 500

    //@NotBlank
    @Column(length = 6)
    private String c_spage; //첫 페이지 6

    //@NotBlank
    @Column(length = 6)
    private String c_epage; //끝 페이지 6

    //@NotBlank
    @Column(length = 6)
    private String c_page; //쪽수 6

    //@NotBlank
    @Column(length = 8)
    private String c_signdate; //결재일자 8

    //@NotBlank
    @Convert(converter= C_edocConverter.class)
    @Column(columnDefinition="varchar(1) default '2'")
    private C_edoc c_edoc; // 전자기록물 여부 (1.전자기록물, 2.비전자기록물)

    //@NotBlank
    @Column(length = 28)
    private String c_groupnum; //분류번호 28 (처리기관코드 7 + 단위업무코드 8 + 생산년도 4 +기록물철등록일련번호(레이블번호) 6 + 권호수 3)

    //@NotBlank
    @Column(length = 100)
    private String c_opendate; //공개시기 100

    //@NotBlank
    @Convert(converter= C_kperiodConverter.class)
    @Column(length = 2)
    private C_kperiod c_kperiod; //보존기간 2 (01.1년, 03.3년, 05.5년, 10.10년, 20.20년, 30.준영구, 40.영구)

    //@NotBlank
    @Convert(converter= C_newoldConverter.class)
    @Column(columnDefinition="varchar(1) default '2'")
    private C_newold c_newold; //기록물 구분 1 (1.신기록물, 2.구기록물)

    //@NotBlank
    @Convert(converter= C_modifyConverter.class)
    @Column(columnDefinition = "varchar(1) default '0' ")
    private C_modify c_modify; //수정여부 1 (0.해당없음, 1.수정함)

    //@NotBlank
    @Convert(converter= C_companionConverter.class)
    @Column(columnDefinition = "varchar(1) default '0' ")
    private C_companion c_companion; //반려여부 1 (0.해당없음, 1.반려)

    //@NotBlank
    @Column(length = 10)
    private String c_complete;    //건을 저장하면 개수가 올라감, 총쪽수와 같은값이면 file의 f_complete 에 timestamp찍힘


    //@NotBlank
    @Column(length = 10)
    private String c_check;    //검수완료된 개수가 총 쪽수와 수가 같아지면 file의 f_check에 timestamp찍힘


    //@NotBlank
    @Column(length = 50)
    private String c_departmentname;    //부서명//(기관명+부서명)=100글자 올라가야하지만 여기는 부서만 적는다.


    @Column(length = 30)
    private String c_oldnum; //구기록물 문서번호 30

    @Column(length = 200)
    private String c_subtitle; //기타제목 200

    @Column(length = 40)
    private String c_approver; //결재권자 (직급) 40

    @Column(length = 100)
    private String c_reviewer; //검토자 100

    @Column(length = 100)
    private String c_helper; //협조자 100

    @Column(length = 40)
    private String c_drafter; //기안자 40

    @Column(length = 8)
    private String c_dodate; //시행일자 8

    @Column(length = 100)
    private String c_receiver; //수(발)신자 100

    @Column(length = 6)
    private String c_distrinum; //문서과 배부번호 6

    @Column(length = 30)
    private String c_pofficenum; //생산기관 등록번호 30

    @Column(length = 4)
    private String c_attachamount; //첨부수 4

    @Convert(converter= C_attachtypeConverter.class)
    @Column(columnDefinition = "varchar(2) default '03' ")
    private C_attachtype c_attachtype; //첨부물 종류 2 (01.오디오, 02.복합, 03.이미지(default), 04.문서, 05.비디오, 99.기타)

    @Column(length = 20)
    private String c_attachetc; //첨부물 기타 20 (if c_attachtype == 99)

    @Column(length = 3)
    private String c_lang; //언어 3

    @Column(length = 5)
    private String c_specialdoc; //특수기록물 5 (Y와 N으로 해당 값 체크, 1대통령관련 2비밀 3개별관리 4저작권보호 5특수규격기록물, if 전부 해당 없음 => NNNNN)

    @Column(length = 9)
    private String c_openable; //공개여부 9 (테이블 명세서 참고,,,)

    @Column(length = 100)
    private String c_hidden; //공개제한부분표시 100

    @Column(length = 500)
    private String c_videosummary; //시청각 내용요약 500

    @Column(length = 50)
    private String c_type; //시청각 기록물 형태 50

    @Column(length = 500)
    private String c_summary; //건 내용요약 500

    @Convert(converter= C_formatConverter.class)
    @Column(columnDefinition = "varchar(1) default '4' ")
    private C_format c_format; //포맷 1 (1.문서, 2.오디오, 3.복합, 4.이미지(default), 5.비디오, 9.기타)

    @Column(length = 20)
    private String c_formatetc; //포맷기타 20 (if c_format == 9)

    @Convert(converter= C_storageConverter.class)
    @Column(length = 2)
    private C_storage c_storage; //저장매체 2 (01.CD-R, 02.DAT, 03.DVD, 04.플로피디스크, 05.하드디스크, 06.JAZ 드라이브, 07.마이크로필름, 08.종이, 09.비디오테이프, 10.ZIP 드라이브, 99.기타)

    @Column(length = 20)
    private String c_storageetc; //저장매체 기타 20 (if c_storage == 99)

    @Convert(converter= C_detailtypeConverter.class)
    @Column(length = 2)
    private C_detailtype c_detailtype; //건 세부유형 2 (11.졸업대장, 21.생활기록부, 22.인사카드)

    //===연관 관계 메서드===//
    public void setVolume(Volume volume) {
        this.volume = volume;
        volume.getCaseList().add(this);
    }

    public void setFiles(Files files) {
        this.files = files;
        files.getCases().add(this);
    }

    public static Cases createCases(int caseNum, Volume volume, Files files, String c_spage, String c_epage){
        String c_page = Integer.toString(Integer.valueOf(c_epage) - Integer.valueOf(c_spage) + 1);
        Cases cases = new Cases(caseNum, volume, files, c_spage, c_epage, c_page);
        return cases;
    }

    public void updatePage(String c_spage, String c_epage) {
        String c_page = Integer.toString(Integer.valueOf(c_epage)-Integer.valueOf(c_spage) + 1);
        this.c_spage = c_spage;
        this.c_epage = c_epage;
        this.c_page = c_page;
    }

    public Cases updateCases(IndexSaveCaseRequest indexSaveCaseRequest){
        this.c_spage = indexSaveCaseRequest.getC_spage();           // 첫 페이지
        this.c_epage = indexSaveCaseRequest.getC_epage();           // 끝 페이지
        this.c_page = indexSaveCaseRequest.getC_page();             // 쪽수
        this.c_class = indexSaveCaseRequest.getC_class();           // 등록구분
        this.c_dodate = indexSaveCaseRequest.getC_dodate();         // 시행일자
        this.c_pdate = indexSaveCaseRequest.getC_pdate();           // 생산등록일자
        this.c_departmentname = indexSaveCaseRequest.getC_departmentname();        // 부서명
        this.c_oldnum = indexSaveCaseRequest.getC_oldnum();         // 문서번호
        this.c_kperiod = indexSaveCaseRequest.getC_kperiod();       // 보존기간
        this.c_title = indexSaveCaseRequest.getC_title();           // 건 제목
        this.c_drafter = indexSaveCaseRequest.getC_drafter();       // 기안자
        this.c_approver = indexSaveCaseRequest.getC_approver();     // 결재권자
        this.c_receiver = indexSaveCaseRequest.getC_receiver();     // 수발신자
        this.c_edoc = indexSaveCaseRequest.getC_edoc();             // 전자기록물여부
        this.c_openable = indexSaveCaseRequest.getC_openable();     // 공개여부
        this.c_hidden = indexSaveCaseRequest.getC_hidden();         // 공개제한부분표시
        return this;
    }


    public void resetCount(){
        this.c_first = "1";
    }

    public void reduceFirst(){
        this.c_first = "0";
    }


}