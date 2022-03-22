package fis.pms.controller.dto;

import fis.pms.domain.Files;
import fis.pms.domain.Office;
import fis.pms.domain.fileEnum.F_construct;
import fis.pms.domain.fileEnum.F_kperiod;
import fis.pms.domain.fileEnum.F_kplace;
import fis.pms.domain.fileEnum.F_type;
import fis.pms.service.excelService.ExcelColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExcelUpdateDTO {
    @ExcelColumn(name = "레이블")
    private String f_labelcode;    //철번호(레이블번호)
    @ExcelColumn(name = "보존기간")
    private String f_kperiod;   //철보존기간 (01-1년, 03-3년, 05-5년, 10-10년, 20-20년, 25-30년, 30-준영구, 40-영구)
    @ExcelColumn(name = "분류번호")
    private String f_typenum; // 분류번호
    @ExcelColumn(name = "생산년도")
    private String f_pyear; //철생산년도
    @ExcelColumn(name = "문서유형")
    private String f_type;  //기록물형태 (1.일반문서, 2.도면류, 3.사진-필름류 4. 녹음-동영상류, 5.카드류)
    @ExcelColumn(name = "문서명")
    private String f_name;  //철이름
    @ExcelColumn(name = "서가")
    private String suga;    //서가
    @ExcelColumn(name = "층")
    private String chung;   //층
    @ExcelColumn(name = "열")
    private String yall;    //열
    @ExcelColumn(name = "번")
    private String bun;     //번
    @ExcelColumn(name = "보존장소")
    private String f_kplace;    //보존장소 (1-기록관, 2-전문관리기관)
    @ExcelColumn(name = "박스번호")
    private String b_num;   //박스 번호
    @ExcelColumn(name = "처리과코드")
    private String o_code;  //처리과 코드
    @ExcelColumn(name = "처리과명")
    private String o_name;  // 처리과 명
    @ExcelColumn(name = "구축여부")
    private String f_db;    //디비 구축여부 (0구축 1비구축)
    @ExcelColumn(name = "스캔여부")
    private String f_scan; //스캔여부


    public ExcelUpdateDTO(Files files) {
        this.f_labelcode = Optional.ofNullable(files.getF_labelcode()).orElse("");
        this.f_typenum = Optional.ofNullable(files.getF_typenum()).orElse("");
        this.f_pyear = Optional.ofNullable(files.getF_pyear()).orElse("");
        this.f_name = Optional.ofNullable(files.getF_name()).orElse("");
        this.suga = Optional.ofNullable(files.getF_location().getSuga()).orElse("");
        this.chung = Optional.ofNullable(files.getF_location().getChung()).orElse("");
        this.yall = Optional.ofNullable(files.getF_location().getYall()).orElse("");
        this.bun = Optional.ofNullable(files.getF_location().getBun()).orElse("");
        this.b_num = Optional.ofNullable(files.getB_num()).orElse("");
        Office office = Optional.ofNullable(files.getOffice()).orElse(new Office("존재하지 않음", "존재하지 않음", "존재하지 않음", null));
        this.o_code = office.getO_code();
        this.o_name = office.getO_name();
        F_kperiod f_kperiod = Optional.ofNullable(files.getF_kperiod()).orElse(F_kperiod.NONE);
        F_construct f_constructDb = Optional.ofNullable(files.getF_db()).orElse(F_construct.NONE);
        F_construct f_constructScan = Optional.ofNullable(files.getF_scan()).orElse(F_construct.NONE);
        F_kplace f_kplace = Optional.ofNullable(files.getF_kplace()).orElse(F_kplace.NONE);
        F_type f_type = Optional.ofNullable(files.getF_type()).orElse(F_type.NONE);
        this.f_kperiod = f_kperiod.getKperiod();
        this.f_db = f_constructDb.getConstruct();
        this.f_scan = f_constructScan.getConstruct();
        this.f_kplace = f_kplace.getKplace();
        this.f_type = f_type.getType();
    }
}
