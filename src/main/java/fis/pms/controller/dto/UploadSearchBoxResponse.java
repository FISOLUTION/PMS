package fis.pms.controller.dto;

import fis.pms.domain.F_location;
import fis.pms.domain.Files;
import fis.pms.domain.fileEnum.F_construct;
import fis.pms.domain.fileEnum.F_kperiod;
import fis.pms.domain.fileEnum.F_kplace;
import fis.pms.domain.fileEnum.F_type;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UploadSearchBoxResponse {
    private Long key;        // 철아이디
    private String Office;      // 기관코드
    private String f_labelcode;     // 레이블
    private String o_name;      // 기관이름
    private String f_name;      // 철이름
    private String f_pyear;     // 생산년도
    private F_kperiod f_kperiod;       // 보존기간
    private F_construct f_db;        // 구축여부
    private F_construct f_scan;      // 스캔여부
    private String b_num;       // 박스번호
    private F_location f_location;      // 위치(서가, 층, 열, 번)
    private F_kplace f_kplace;        // 보존장소
    private F_type f_type;      // 문서종류
    private String f_typenum;       // 분류번호
    private String check;       // 0 검수만, 1 미검수포함
    private String upload;      // 0업로드 포함 안함, 1 업로드 포함
    private String f_process;


    //dto와 file 변환기
    public static UploadSearchBoxResponse CreateUploadSearchBoxResponse(Files files){
        UploadSearchBoxResponse response = new UploadSearchBoxResponse();
        response.key = files.getF_id();
        response.Office = files.getOffice().getO_code();
        response.f_labelcode = files.getF_labelcode();
        response.o_name = files.getOffice().getO_name();
        response.f_name = files.getF_name();
        response.f_pyear = files.getF_pyear();
        response.f_kperiod = files.getF_kperiod();
        response.f_db = files.getF_db();
        response.f_scan = files.getF_scan();
        response.b_num = files.getB_num();
        response.f_location = files.getF_location();
        response.f_kplace = files.getF_kplace();
        response.f_type = files.getF_type();
        response.f_typenum = files.getF_typenum();
        response.check = files.getF_check();
        response.upload = files.getF_upload();
        response.f_process = files.getF_process().getProcess();
        return response;
    }
}
