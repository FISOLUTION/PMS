package fis.pms.controller;

import fis.pms.controller.dto.ExportFilesRequest;
import fis.pms.controller.dto.ExportFilesResponse;
import fis.pms.controller.dto.ExportSearchLabelResponse;
import fis.pms.controller.dto.ExportSearchResponse;
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
    *   작성내용: 반출된 철 레이블 범위 검색
    */
    @GetMapping("/export/search/label")
    public ExportSearchLabelResponse searchFilesByLabelCode(@RequestParam(value = "slabel", required = false) String slabel,
                                                            @RequestParam(value = "elabel", required = false) String elabel){

        return new ExportSearchLabelResponse(fileService.searchFileByLabelCode(slabel, elabel).stream()
                .map(file -> new ExportSearchLabelResponse.ExportSearchLabelInfo(file.getF_id(), file.getF_labelcode(),
                        file.getF_name(), file.getF_pyear(), file.getF_kperiod(), file.getF_db(), file.getF_scan(),
                        file.getB_num()))
                .collect(Collectors.toList()));
    }

    /**
    *   작성날짜: 2022/03/23 12:59 PM
    *   작성자: 이승범
    *   작성내용: 반출된 철 날짜 범위 검색
    */
    @GetMapping("/export/search/date")
    public ExportSearchResponse searchFilesByDate(@RequestParam(value = "sdate", required = false) String sdate,
                                              @RequestParam(value = "edate", required = false) String edate) {

       return new ExportSearchResponse(fileService.searchFilesByDate(sdate, edate).stream()
               .map(file -> new ExportSearchResponse.ExportSearchInfo(file.getF_id(), file.getOffice().getO_code(),
                       file.getF_labelcode(), file.getOffice().getO_name(), file.getF_name(), file.getF_pyear(),
                       file.getF_kperiod(), file.getF_db(), file.getF_scan(), file.getB_num(), file.getF_location(),
                       file.getF_kplace(), file.getF_type(), file.getF_typenum()))
               .collect(Collectors.toList()));
    }


}

