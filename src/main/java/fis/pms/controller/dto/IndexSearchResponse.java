package fis.pms.controller.dto;

import fis.pms.domain.Files;
import fis.pms.domain.fileEnum.F_kmethod;
import fis.pms.domain.fileEnum.F_kperiod;
import fis.pms.domain.fileEnum.F_kplace;
import fis.pms.domain.fileEnum.F_type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
* 작성자: 한명수
* 작성날짜: 2021/08/24
* 작성내용: IndexSearchResponse
* */


@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndexSearchResponse {
    private String o_name;  // 생산기관명
    private Long f_id;    // 철아이디
    private String f_labelcode; // 레이블
    private String f_name;  // 철이름
    private String f_pyear; // 생산년도
    private String b_num;   // 박스번호
    private String f_complete;  // 철의 모든건 입력 완료 여부
    private String f_check; // 철의 모든건 검수완료 여부
    private String f_volumeamount;  // 철 안에 있는 권의 개수
    private String f_typenum;   // 분류번호
    private String f_manager;   // 업무 담당자
    private F_kperiod f_kperiod;   // 보존기간
    private F_kmethod f_kmethod;   // 보존 방법
    private F_kplace f_kplace;    // 보존장소
    private F_type f_type;  // 기록물형태(문서종류)

    public static IndexSearchResponse createIndexSearchResponse(Files files){
        IndexSearchResponse indexSearchResponse = new IndexSearchResponse();
        indexSearchResponse.o_name = files.getOffice().getO_name();
        indexSearchResponse.f_id = files.getF_id();
        indexSearchResponse.f_labelcode = files.getF_labelcode();
        indexSearchResponse.f_name = files.getF_name();
        indexSearchResponse.f_pyear = files.getF_pyear();
        indexSearchResponse.b_num = files.getB_num();
        indexSearchResponse.f_complete = files.getF_complete();
        indexSearchResponse.f_check = files.getF_check();
        indexSearchResponse.f_volumeamount = files.getF_volumeamount();
        indexSearchResponse.f_type = files.getF_type();
        indexSearchResponse.f_manager = files.getF_manager();
        indexSearchResponse.f_kperiod = files.getF_kperiod();
        indexSearchResponse.f_kmethod = files.getF_kmethod();
        indexSearchResponse.f_kplace = files.getF_kplace();
        indexSearchResponse.f_type = files.getF_type();
        return indexSearchResponse;
    }
}
