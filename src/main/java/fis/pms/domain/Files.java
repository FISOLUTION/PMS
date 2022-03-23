package fis.pms.domain;

import fis.pms.controller.dto.IndexSaveLabelRequest;
import fis.pms.controller.dto.filedto.PreinfoFileSaveRequest;
import fis.pms.domain.fileEnum.*;
import fis.pms.service.dto.ExportInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert //원보라 : 디폴트값은 ddl만 적용됨 insert 문에도 디폴트 적용하기 위함
public class Files {
    private Files(String f_labelcode, Office office, String b_num, String f_name, String f_pyear, F_kperiod f_kperiod, F_kplace f_kplace, F_construct f_db, F_construct f_scan, F_type f_type, F_location f_location, String f_typenum) {
        this.f_labelcode = f_labelcode;
        this.office = office;
        this.b_num = b_num;
        this.f_name = f_name;
        this.f_pyear = f_pyear;
        this.f_kperiod = f_kperiod;
        this.f_kplace = f_kplace;
        this.f_db = f_db;
        this.f_scan = f_scan;
        this.f_type = f_type;
        this.f_location = f_location;
        this.f_typenum = f_typenum;
        this.f_volumeSaved = "0";
        this.f_pageSaved = "0";
        this.f_process = F_process.PREINFO;
    }

    @Id
    @GeneratedValue //auto inc
    @Column(name = "f_id")    //철 아이디
    private Long f_id;

    //@NotBlank //적용되는지 확인하자
    @Column(length = 6)
    private String f_labelcode;    //철번호(레이블번호)

//0824 원보라 수정(box 이블 제거)
//    //@NotBlank
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "b_id")
//    private Box box;

    //@NotBlank
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "o_code")
    private Office office;

    //@NotBlank
    @OneToMany(mappedBy = "files", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Special> specials = new ArrayList<>();

    //@NotBlank
    @OneToMany(mappedBy = "files", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Volume> volumes = new ArrayList<>();

    //@NotBlank
    @OneToMany(mappedBy = "files", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Cases> cases = new ArrayList<>();

    @OneToMany(mappedBy = "files")
    private List<WorkList> workList = new ArrayList<>();

    //@NotBlank
    @Column(length = 3)
    private String b_num;   //박스번호

    //@NotBlank
    //@Enumerated(EnumType.STRING)
    @Convert(converter = F_processConverter.class)
    @Column(length = 2)
    private F_process f_process;   //현재진행중인 공정(색인 입력(12), 색인검수(13), 로딩(14), 업로드(15) 업로드완료(16))

    // 2022-02-25 이승범
    private Long images; // 해당 철의 이미지 갯수

    //@NotBlank
    @Column(length = 10)
    private String f_volumecount;  //(값이 0 이되면 f_process +1 한다)  검수는 철단위로 하게끔

    //@NotBlank
    @Column(length = 3)
    private String f_volumeamount;  //철의 총 권호수

    //@NotBlank
    @Column(length = 500)
    private String f_name;  //철이름

    //@NotBlank
    @Column(length = 4)
    private String f_pyear; //철생산년도

    //@NotBlank
    //@Enumerated(EnumType.STRING)
    @Convert(converter = F_kperiodConverter.class)
    @Column(length = 2)
    private F_kperiod f_kperiod;   //철보존기간 (01-1년, 03-3년, 05-5년, 10-10년, 20-20년, 25-30년, 30-준영구, 40-영구)

    //@NotBlank
    //@Enumerated(EnumType.STRING)
    @Convert(converter = F_kmethodConverter.class)
    @Column(length = 1)
    private F_kmethod f_kmethod;   //보존방법 (1-원본과 보존매체를 함께 보존하는 방법, 2-원본은 폐기하고 보존매체만 보존하는 방법, 3- 원본을 그대로 보존하는 방법)

    //@NotBlank
    //@Enumerated(EnumType.STRING)
    @Convert(converter = F_kplaceConverter.class)
    @Column(length = 1)
    private F_kplace f_kplace;    //보존장소 (1-기록관, 2-전문관리기관)

    //@NotBlank
    @Column(length = 4)
    private String f_syear; //시작년도 (없으면 생산년도와산 동일)

    //@NotBlank
    @Column(length = 4)
    private String f_eyear; //종료년도 (없으면 생산년도와 동일)

    //@NotBlank
    //@Enumerated(EnumType.STRING)
    @Convert(converter = F_constructConverter.class)
    @Column(length = 1)
    private F_construct f_db;    //디비 구축여부 (0구축 1비구축)


    //@NotBlank
    //@Enumerated(EnumType.STRING)
    @Convert(converter = F_constructConverter.class)
    @Column(length = 1)
    private F_construct f_scan;  //스캔 구축여부 (0구축 1비구축)

    //@NotBlank
    @Column(columnDefinition = "varchar(8) default '99999999'")
    private String f_unitcode;  //단위업무코드 (default ’99999999’)

    //@NotBlank
    //@Enumerated(EnumType.STRING)
    @Convert(converter = F_typeConverter.class)
    @Column(length = 1)
    private F_type f_type;  //기록물형태 (1.일반문서, 2.도면류, 3.사진-필름류 4. 녹음-동영상류, 5.카드류)

    //@NotBlank
    //@Enumerated(EnumType.STRING)
    @Convert(converter = F_newoldConverter.class)
    @Column(columnDefinition = "varchar(1) default '2'")
    private F_newold f_newold;    //기록물구분(1.신기록물, 2.구기록물)

    //@NotBlank
    //@Enumerated(EnumType.STRING)
    @Convert(converter = F_modifyConverter.class)
    @Column(length = 1)
    private F_modify f_modify;    //수정여부 (0.해당없음 1. 수정함)

    //@NotBlank
    @Column(length = 6)
    private String f_regnum;    //기록물등록건수

    //@NotBlank
    @Column(length = 6)
    private String f_page;  //기록물쪽수

    //@NotBlank
    @Column(length = 6)
    private String f_efilenum;  //전자파일갯수

    //@NotBlank
    //@Enumerated(EnumType.STRING)
    @Convert(converter = F_inheritanceConverter.class)
    @Column(columnDefinition = "varchar(1) default '0'")
    private F_inheritance f_inheritance;   //인수인계구분 (0.없음 1.인수 2.인계)

    //@NotBlank
    @Column(columnDefinition = "varchar(20) default 'none'")
    private String f_exportdate;    //반출날짜


    //@NotBlank
    @Column(columnDefinition = "varchar(30) default '0'")
    private String f_complete;    //미완료:0 | 완료:timestamp


    //@NotBlank
    @Column(columnDefinition = "varchar(30) default '0'")
    private String f_check;    //검수안함:0 | 완료:timestamp

    //@NotBlank
    @Column(columnDefinition = "varchar(30) default '0'")
    private String f_upload;    //미완료:0 | 완료:timestamp

    /*
     * 작성자: 한명수
     * 작성날짜: 2021/09/03
     * 작성내용: f_volumeSaved 추가
     */

    @Column(columnDefinition = "varchar(1) default '0'")
    private String f_volumeSaved;

    @Column
    private String f_pageSaved;


//========null 옵션 컬럼================

    @Column(length = 8)
    private String f_smallfunc; //소기능코드

    @Column(length = 200)
    private String f_subname;   //기록물철기타제목

    @Column(length = 8)
    private String f_placeenddate;  //비치종결일자

    @Column(length = 100)
    private String f_placereason;   //비치사유

    @Column(length = 40)
    private String f_manager;   //업무담당자

    @Column(length = 5)
    private String f_oldfileclassifynum;    //구기록물철분류번호

    @Column(length = 7)
    private String f_inheroffice;   //처리과기관코드

    @Column(length = 8)
    private String f_inherunitcode; //단위업무코드

    @Column(length = 4)
    private String f_inherpyear;    //생산년도

    @Column(length = 6)
    private String f_inherlabelcode;    //기록물철등록일련번호

    @Column(length = 1000)
    private String f_summary;   //내용요약

    @Embedded
    private F_location f_location;  //위치(서가, 층, 열, 번)

    @Column(length = 10)
    private String f_typenum;   //분류번호 (뭔지 모름)
    //업로드 완료여부 미완료 0 완료 timestamp


//    @Column(columnDefinition="DATETIME(0) default CURRENT_TIMESTAMP")
//    private OffsetDateTime dateTime;

//    // 엔티티가 생성되는 시점의 날짜 데이터를 기록하는 설정
//    @CreationTimestamp
//    private Timestamp reg_date;
//    // 엔티티가 업데이트되는 시점의 날짜 데이터를 기록하는 설정
//    @UpdateTimestamp
//    private Timestamp mod_date;

    //=======================생성 메서드(210827원보라)==========================//
    public static Files createFiles(PreinfoFileSaveRequest preinfoFileSaveRequest, Office office) {
        Files files = new Files(preinfoFileSaveRequest.getF_labelcode(), office, preinfoFileSaveRequest.getB_num(), preinfoFileSaveRequest.getF_name(), preinfoFileSaveRequest.getF_pyear(), preinfoFileSaveRequest.getF_kperiod(), preinfoFileSaveRequest.getF_kplace(), preinfoFileSaveRequest.getF_db(), preinfoFileSaveRequest.getF_scan(), preinfoFileSaveRequest.getF_type(), preinfoFileSaveRequest.getF_location(), preinfoFileSaveRequest.getF_typenum());
        return files;
    }


    /*
     * 작성자: 원보라
     * 작성날짜: 2021/08/25
     * 작성내용: 수정메서드 updateFileInfo, updateFileExport
     */


    //=======================수정 메서드==========================//
    public void updateFileinfo(Office office, String f_labelcode, String f_name, String f_pyear, F_kperiod f_kperiod, F_construct f_db, F_construct f_scan, String b_num, F_location f_location, F_kplace f_kplace, F_type f_type, String f_typenum) {
        this.office = office;                 // 기관
        this.f_labelcode = f_labelcode;       // 레이블
        this.f_name = f_name;                 // 철이름
        this.f_pyear = f_pyear;               // 생산년도
        this.f_kperiod = f_kperiod;           // 보존기간
        this.f_db = f_db;                     // 구축여부
        this.f_scan = f_scan;                 // 스캔여부
        this.b_num = b_num;                   // 박스번호
        this.f_location = f_location;         // 위치(서가, 층, 열, 번)
        this.f_kplace = f_kplace;             // 보존장소
        this.f_type = f_type;                 // 문서종류
        this.f_typenum = f_typenum;           // 분류번호
    }

    public void exportFile(ExportInfo exportInfo) {
        this.b_num = exportInfo.getB_num();                   // 박스번호
        this.f_db = exportInfo.getF_db();                     // 구축여부
        this.f_scan = exportInfo.getF_scan();                 // 스캔여부
        this.f_exportdate = exportInfo.getF_exportdate();     // 반출날짜
        this.f_process = F_process.EXPORT;
    }

    /*
     * 작성자: 한명수
     * 작성날짜: 2021/08/27
     * 작성내용: 수정 메서드 updateFileIndex
     */
    public void updateFileIndex(IndexSaveLabelRequest indexSaveLabelRequest, int volumeCount) {
        this.f_name = indexSaveLabelRequest.getF_name();
        this.f_volumeamount = indexSaveLabelRequest.getF_volumeamount();
        this.f_volumecount = Integer.toString(Integer.parseInt(indexSaveLabelRequest.getF_volumeamount()) - volumeCount);
        this.f_typenum = indexSaveLabelRequest.getF_typenum();
        this.f_manager = indexSaveLabelRequest.getF_manager();
        this.f_kperiod = indexSaveLabelRequest.getF_kperiod();
        this.f_kmethod = indexSaveLabelRequest.getF_kmethod();
        this.f_kplace = indexSaveLabelRequest.getF_kplace();
        this.f_type = indexSaveLabelRequest.getF_type();
        this.f_volumeSaved = "1";
    }

    /*
     * 작성자: 한명수
     * 작성날짜: 2021/09/01
     * 작성내용: reduceVolumeCount
     */

    public void reduceVolumeCount() {
        this.f_volumecount = Integer.toString(Integer.valueOf(this.f_volumecount) - 1);
    }

    /*
     * 작성자: 한명수
     * 작성날짜: 2021/09/01
     * 작성내용: updateProcess
     */

    public void updateProcess() {
        if (this.f_process == F_process.IMGMODIFY) {
            this.f_process = F_process.INPUT;
            LocalDate timestamp = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            this.f_complete = timestamp.format(formatter);
        } else if (this.f_process == F_process.INPUT) {
            this.f_process = F_process.CHECK;
            LocalDate timestamp = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            this.f_check = timestamp.format(formatter);
        }
        this.resetCount();
    }

    /*
     * 작성자: 한명수
     * 작성날짜: 2021/09/01
     * 작성내용: resetCount
     */

    public void resetCount() {
        this.f_volumecount = this.f_volumeamount;
    }
    /*
     * 작성자: 한명수
     * 작성날짜: 2021/08/26
     * 작성내용: 연관관계 메서드 setOffice
     */

    //====연관관계 메서드====//
    public void setLinkOffice(Office office) {
        this.office = office;
        office.getFileList().add(this);
    }

    public void Uploaded() {
        this.f_process = F_process.UPLOADED;
        Date date = new Date(System.currentTimeMillis());
        this.f_upload = date.toString();
    }

    public void updatePageSave() {
        this.f_pageSaved = "1";
    }

    @Override
    public String toString() {
        return "Files{" +
                "f_id=" + f_id +
                ", f_labelcode='" + f_labelcode + '\'' +
                ", b_num='" + b_num + '\'' +
                ", f_process=" + f_process +
                ", f_volumecount='" + f_volumecount + '\'' +
                ", f_volumeamount='" + f_volumeamount + '\'' +
                ", f_name='" + f_name + '\'' +
                ", f_pyear='" + f_pyear + '\'' +
                ", f_kperiod=" + f_kperiod +
                ", f_kmethod=" + f_kmethod +
                ", f_kplace=" + f_kplace +
                ", f_syear='" + f_syear + '\'' +
                ", f_eyear='" + f_eyear + '\'' +
                ", f_db=" + f_db +
                ", f_scan=" + f_scan +
                ", f_unitcode='" + f_unitcode + '\'' +
                ", f_type=" + f_type +
                ", f_newold=" + f_newold +
                ", f_modify=" + f_modify +
                ", f_regnum='" + f_regnum + '\'' +
                ", f_page='" + f_page + '\'' +
                ", f_efilenum='" + f_efilenum + '\'' +
                ", f_inheritance=" + f_inheritance +
                ", f_exportdate='" + f_exportdate + '\'' +
                ", f_complete='" + f_complete + '\'' +
                ", f_check='" + f_check + '\'' +
                ", f_upload='" + f_upload + '\'' +
                ", f_volumeSaved='" + f_volumeSaved + '\'' +
                ", f_pageSaved='" + f_pageSaved + '\'' +
                ", f_smallfunc='" + f_smallfunc + '\'' +
                ", f_subname='" + f_subname + '\'' +
                ", f_placeenddate='" + f_placeenddate + '\'' +
                ", f_placereason='" + f_placereason + '\'' +
                ", f_manager='" + f_manager + '\'' +
                ", f_oldfileclassifynum='" + f_oldfileclassifynum + '\'' +
                ", f_inheroffice='" + f_inheroffice + '\'' +
                ", f_inherunitcode='" + f_inherunitcode + '\'' +
                ", f_inherpyear='" + f_inherpyear + '\'' +
                ", f_inherlabelcode='" + f_inherlabelcode + '\'' +
                ", f_summary='" + f_summary + '\'' +
                ", f_location='" + f_location + '\'' +
                ", f_typenum='" + f_typenum + '\'' +
                '}';
    }

    // 2022-02-28 이미지 개수 파악을 위한 메서드
    public void imageUpload(Long imageNum, String state) {
        images = imageNum;
        if (state.equals("origin")) {
            f_process = F_process.SCAN;
        } else {
            f_process = F_process.IMGMODIFY;
        }
    }
}