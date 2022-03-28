package fis.pms.controller.dto.filedto;

import fis.pms.domain.F_location;
import fis.pms.domain.Files;
import fis.pms.domain.fileEnum.F_construct;
import fis.pms.domain.fileEnum.F_kperiod;
import fis.pms.domain.fileEnum.F_kplace;
import fis.pms.domain.fileEnum.F_type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 작성자: 원보라
 * 작성날짜: 2021/08/24
 * 작성내용: PreinfoFileSearchResponse
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PreInfoFileSearchResponse {
    private Long f_id;          // 철아이디
    private String o_code;      // 기관코드
    private String f_labelcode; // 레이블
    private String o_name;      // 기관이름
    private String f_name;      // 철이름
    private String f_pyear;     // 생산년도
    private F_kperiod f_kperiod;     // 보존기간
    private F_construct f_db;        // 구축여부
    private F_construct f_scan;      // 스캔여부
    private String b_num;       // 박스번호
    private F_location f_location;  // 위치(서가, 층, 열, 번)
    private F_kplace f_kplace;  // 보존장소
    private F_type f_type;      // 문서종류
    private String f_typenum;   // 분류번호


    public static PreInfoFileSearchResponse createResponse(Files files){
        return PreInfoFileSearchResponse.builder()
                .f_id(files.getF_id())
                .o_code(files.getOffice().getO_code())
                .f_labelcode(files.getF_labelcode())
                .o_name(files.getOffice().getO_name())
                .f_pyear(files.getF_pyear())
                .f_kperiod(files.getF_kperiod())
                .f_db(files.getF_db())
                .f_scan(files.getF_scan())
                .b_num(files.getB_num())
                .f_location(files.getF_location())
                .f_kplace(files.getF_kplace())
                .f_type(files.getF_type())
                .f_typenum(files.getF_typenum())
                .build();
    }
}
