package fis.pms.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ImportOfficeJsonDto {
    List<OfficeExcel> data;
}
