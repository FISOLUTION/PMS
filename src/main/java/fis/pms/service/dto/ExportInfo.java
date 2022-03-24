package fis.pms.service.dto;

import fis.pms.domain.fileEnum.F_construct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExportInfo {
    private Long f_id;              // 철아이디
    private String exportDate;      // 반출날짜
    private F_construct f_db;       // 구축여부
    private F_construct f_scan;     // 스캔여부
    private String b_num;           // 박스번호
}
