package fis.pms.controller.dto;

import fis.pms.domain.Files;
import fis.pms.domain.fileEnum.F_construct;
import fis.pms.domain.fileEnum.F_kperiod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 작성날짜: 2022/03/23 12:41 PM
 * 작성자: 이승범
 * 작성내용: 반출된 철을 레이블 범위로 가져올때의 responseDto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExportSearchLabelResponse {

    private Long f_id;              // 철아이디
    private String f_labelcode;     // 레이블
    private String f_name;          // 철이름
    private String f_pyear;         // 생산년도
    private F_kperiod f_kperiod;    // 보존기간
    private F_construct f_db;       // 구축여부
    private F_construct f_scan;     // 스캔여부
    private String b_num;           // 박스번호

    public static ExportSearchLabelResponse createExportSearchLabelResponse(Files files) {
        ExportSearchLabelResponse exportSearchLabelResponse = new ExportSearchLabelResponse();
        exportSearchLabelResponse.f_id = files.getF_id();
        exportSearchLabelResponse.f_labelcode = files.getF_labelcode();
        exportSearchLabelResponse.f_name = files.getF_name();
        exportSearchLabelResponse.f_pyear = files.getF_pyear();
        exportSearchLabelResponse.f_kperiod = files.getF_kperiod();
        exportSearchLabelResponse.f_db = files.getF_db();
        exportSearchLabelResponse.f_scan = files.getF_scan();
        exportSearchLabelResponse.b_num = files.getB_num();
        return exportSearchLabelResponse;
    }
}
