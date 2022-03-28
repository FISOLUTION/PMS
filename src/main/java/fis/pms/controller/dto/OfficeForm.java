package fis.pms.controller.dto;

import fis.pms.domain.Office;
import fis.pms.service.excelService.ExcelColumn;
import lombok.Data;

@Data
public class OfficeForm {
    @ExcelColumn(name = "기관코드")
    private String o_code;
    @ExcelColumn(name = "전체기관명")
    private String o_name;
    @ExcelColumn(name = "폐지구분")
    private String o_del;

    public Office createOffice(){
        return Office.builder()
                .o_code(o_code)
                .o_del(o_del)
                .o_name(o_name)
                .build();
    }
}
