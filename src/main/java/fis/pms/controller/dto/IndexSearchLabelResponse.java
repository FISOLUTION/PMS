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
* 작성내용: IndexSearchLabelResponse
* */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndexSearchLabelResponse {
    private Long f_id; // 철아이디
    private String f_labelcode;  // 철번호
    private String f_volumeamount;   // 권호수
    private String f_name;   // 철이름
    private String f_pyear;  // 철생산년도
    private String f_eyear;  // 종료년도
    private F_kperiod f_kperiod;    // 철보존기간
    private String o_code;   // 기관코드
    private String o_name;   // 생산기관명
    private F_type f_type;   // 기록물형태
    private F_kmethod f_kmethod;    // 보존방법
    private F_kplace f_kplace; // 보존장소
    private String f_manager;    // 업무담당자

    public static IndexSearchLabelResponse createIndexSearchLabelResponse(Files file) {
        IndexSearchLabelResponse indexSearchLabelResponse = new IndexSearchLabelResponse();
        indexSearchLabelResponse.f_id = file.getF_id();
        indexSearchLabelResponse.f_labelcode= file.getF_labelcode();
        indexSearchLabelResponse.f_volumeamount= file.getF_volumeamount();
        indexSearchLabelResponse.f_name = file.getF_name();
        indexSearchLabelResponse.f_pyear= file.getF_pyear();
        indexSearchLabelResponse.f_eyear= file.getF_eyear();
        indexSearchLabelResponse.f_kperiod=file.getF_kperiod();
        indexSearchLabelResponse.o_code=file.getOffice().getO_code();
        indexSearchLabelResponse.o_name=file.getOffice().getO_name();
        indexSearchLabelResponse.f_type=file.getF_type();
        indexSearchLabelResponse.f_kmethod=file.getF_kmethod();
        indexSearchLabelResponse.f_kplace=file.getF_kplace();
        indexSearchLabelResponse.f_manager=file.getF_manager();
        return indexSearchLabelResponse;
    }
}
