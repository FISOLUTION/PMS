package fis.pms.controller.dto;

import fis.pms.service.dto.ExportInfo;
import lombok.*;

import java.util.List;

/**
*   작성날짜: 2022/03/23 11:46 AM
*   작성자: 이승범
*   작성내용: 철 반출 requestDto
*/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExportFilesRequest {
    private List<ExportInfo> exportInfoList;
}
