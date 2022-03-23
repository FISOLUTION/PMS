package fis.pms.controller;

import fis.pms.controller.dto.ExportFilesRequest;
import fis.pms.controller.dto.ExportFilesResponse;
import fis.pms.controller.dto.ExportSearchLabelResponse;
import fis.pms.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    /**
    *    작성날짜: 2022/03/22 5:47 PM
    *    작성자: 이승범
    *    작성내용: 철 리스트 반출 등록
    */
    @PatchMapping("/export/save")
    public ExportFilesResponse exportFiles(@RequestBody ExportFilesRequest exportFilesRequest) {
        List<Long> collect = exportFilesRequest.getExportInfoList().stream()
                .map(fileService::exportFile)
                .collect(Collectors.toList());
        return new ExportFilesResponse(collect);
    }

    /**
    *   작성날짜: 2022/03/23 11:38 AM
    *   작성자: 이승범
    *   작성내용: 레이블 범위 검색
    */
    @GetMapping("/export/search/label")
    public ExportSearchLabelResponse labelResponses(@RequestParam(value = "slabel", required = false) String slabel,
                                                    @RequestParam(value = "elabel", required = false) String elabel){

        return new ExportSearchLabelResponse(fileService.searchFileByLabelCode(slabel, elabel).stream()
                .map(files -> new ExportSearchLabelResponse.ExportSearchLabelInfo(files.getF_id(), files.getF_labelcode(),
                        files.getF_name(), files.getF_pyear(), files.getF_kperiod(), files.getF_db(), files.getF_scan(),
                        files.getB_num()))
                .collect(Collectors.toList()));
    }

}

