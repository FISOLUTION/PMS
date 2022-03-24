package fis.pms.service.dto;

import fis.pms.controller.dto.filedto.ExcelUpdateDTO;
import fis.pms.domain.F_location;
import fis.pms.domain.Files;
import fis.pms.domain.Office;
import fis.pms.domain.fileEnum.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 * @author hyeonseung-gu
 * @implNote FileService.preInfo() 를 이용하기 위해서 필요한 객체 입니다
 * Controller request 요청으로 사용 가능합니다
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreInfoFileInfo {
    private String o_code;      // 기관코드
    private String o_name;
    @Length(max = 6)
    private String f_labelcode; // 레이블
    private String f_name;      // 철이름
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

    public Files createFiles(Office office){
        return Files.builder()
                        .office(office)
                        .f_labelcode(f_labelcode)
                        .f_name(f_name)
                        .f_pyear(f_pyear)
                        .f_kperiod(f_kperiod)
                        .f_db(f_db)
                        .f_scan(f_scan)
                        .b_num(b_num)
                        .f_location(f_location)
                        .f_kplace(f_kplace)
                        .f_type(f_type)
                        .f_typenum(f_typenum)
                        .build();
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
