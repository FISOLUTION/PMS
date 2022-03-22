package fis.pms.controller.dto;

import fis.pms.domain.fileEnum.F_construct;
import lombok.*;

import java.util.List;

/*
 * 작성자: 원보라
 * 작성날짜: 2021/08/24
 * 작성내용: ExportSaveRequest
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExportSaveRequest {

    private List<ExportList> e_list;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    static public class ExportList {
        private Long f_id;              // 철아이디
        private String f_exportdate;    // 반출날짜
        private F_construct f_db;       // 구축여부
        private F_construct f_scan;     // 스캔여부
        private String b_num;           // 박스번호
    }
}
