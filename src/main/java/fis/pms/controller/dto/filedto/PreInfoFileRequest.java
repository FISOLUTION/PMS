package fis.pms.controller.dto.filedto;

import fis.pms.domain.F_location;
import fis.pms.domain.Files;
import fis.pms.domain.fileEnum.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/*
 * 작성자: 원보라
 * 작성날짜: 2021/08/24
 * 작성내용: PreinfoFileSaveRequest
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreInfoFileRequest {
    private String o_code;      // 기관코드
    @Length(max = 6)
    private String f_labelcode; // 레이블
    private String o_name;      // 기관이름
    private String f_name;      // 철이름
    private Long f_id;
    @Length(max = 4)
    private String f_pyear;     // 생산년도
    private F_kperiod f_kperiod;     // 보존기간
    private F_construct f_db;        // 구축여부
    private F_construct f_scan;      // 스캔여부
    @Length(max = 3)
    private String b_num;       // 박스번호
    private F_location f_location;  // 위치(서가, 층, 열, 번)
    private F_kplace f_kplace;  // 보존장소
    private F_type f_type;      // 문서종류
    private String f_typenum;   // 분류번호

    public Files createFiles(){
        return new Files.FilesBuilder().build()
                .setOffice();
    }



    public PreInfoFileRequest(ExcelUpdateDTO excelUpdateDTO) {
        F_kperiodConverter f_kperiodConverter = new F_kperiodConverter();
        F_constructConverter f_constructConverter = new F_constructConverter();
        F_kplaceConverter f_kplaceConverter = new F_kplaceConverter();
        F_typeConverter f_typeConverter = new F_typeConverter();
        this.o_code = excelUpdateDTO.getO_code();
        this.f_labelcode = excelUpdateDTO.getF_labelcode();
        this.o_name = excelUpdateDTO.getO_name();
        this.f_name = excelUpdateDTO.getF_name();
        this.f_pyear = excelUpdateDTO.getF_pyear();
        this.f_kperiod = f_kperiodConverter.convertToEntityAttribute(excelUpdateDTO.getF_kperiod());
        this.f_db = f_constructConverter.convertToEntityAttribute(excelUpdateDTO.getF_db());
        this.f_scan = f_constructConverter.convertToEntityAttribute(excelUpdateDTO.getF_scan());
        this.b_num = excelUpdateDTO.getB_num();
        F_location f_location = new F_location(excelUpdateDTO.getChung(),excelUpdateDTO.getSuga(),excelUpdateDTO.getYall(),excelUpdateDTO.getBun());
        this.f_location = f_location;
        this.f_kplace = f_kplaceConverter.convertToEntityAttribute(excelUpdateDTO.getF_kplace());
        this.f_type = f_typeConverter.convertToEntityAttribute(excelUpdateDTO.getF_type());
        this.f_typenum = excelUpdateDTO.getF_typenum();
    }
}
