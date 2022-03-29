package fis.pms.controller;

import fis.pms.controller.dto.*;
import fis.pms.service.dto.FindIndexPreinfo;
import fis.pms.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    /**
     * 작성날짜: 2022/03/22 5:47 PM
     * 작성자: 이승범
     * 작성내용: 철 리스트 반출 등록
     */
    @PatchMapping("/file/export")
    public ExportFilesResponse exportFiles(@Validated @RequestBody ExportFilesRequest exportFilesRequest) {
        List<Long> collect = exportFilesRequest.getExportInfoList().stream()
                .map(fileService::exportFile)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return new ExportFilesResponse(collect);
    }

    /**
     * 작성날짜: 2022/03/23 11:38 AM
     * 작성자: 이승범
     * 작성내용: 반출된 철 레이블 범위 검색
     */
    @GetMapping("/file/export/label")
    public List<ExportSearchLabelResponse> searchFilesByLabelCode(@RequestParam(value = "slabel", required = false) String slabel,
                                                                  @RequestParam(value = "elabel", required = false) String elabel) {

        return fileService.searchFilesByLabelCode(slabel, elabel).stream()
                .map(ExportSearchLabelResponse::createExportSearchLabelResponse)
                .collect(Collectors.toList());
    }

    /**
     * 작성날짜: 2022/03/23 12:59 PM
     * 작성자: 이승범
     * 작성내용: 반출된 철 날짜 범위 검색
     */
    @GetMapping("/file/export/date")
    public List<ExportSearchResponse> searchFilesByDate(
            @RequestParam( required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate sdate,
            @RequestParam( required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate edate) {

        return fileService.searchFilesByDate(sdate, edate).stream()
                .map(ExportSearchResponse::createExportSearchResponse)
                .collect(Collectors.toList());
    }

    /**
     * 작성날짜: 2022/03/23 1:20 PM
     * 작성자: 이승범
     * 작성내용: 반출된 철 박스 범위 검색
     */
    @GetMapping("/file/export/box")
    public List<ExportSearchResponse> searchFilesByBox(@RequestParam(value = "sbox", required = false) String sbox,
                                                       @RequestParam(value = "ebox", required = false) String ebox) {

        return fileService.searchFilesByBox(sbox, ebox).stream()
                .map(ExportSearchResponse::createExportSearchResponse)
                .collect(Collectors.toList());
    }

    /**
    *   작성날짜: 2022/03/25 4:57 PM
    *   작성자: 이승범
    *   작성내용: 색인 작업할(스캔이 끝난) 철 목록 불러오기
    */
    @GetMapping("/file/index/{o_code}")
    public List<IndexSearchResponse> indexSearchResponses(@PathVariable String o_code,
                                                          @RequestParam(value = "box", required = false) String f_bnum,
                                                          @RequestParam(value = "label", required = false) String f_labelcode) {

        FindIndexPreinfo findIndexPreinfo = FindIndexPreinfo.createFindIndexPreinfo(o_code, f_bnum, f_labelcode);
        return fileService.searchFilesByPreInfo(findIndexPreinfo).stream()
                .map(IndexSearchResponse::createIndexSearchResponse)
                .collect(Collectors.toList());
    }

    @PostMapping("/file/index")
    public IndexSaveLabelResponse indexSaveLabelResponse(@RequestBody IndexSaveLabelRequest indexSaveLabelRequest) {
        return fileService.saveFilesAndVolume(indexSaveLabelRequest);
    }

}

