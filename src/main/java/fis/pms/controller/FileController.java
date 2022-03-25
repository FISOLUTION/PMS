package fis.pms.controller;

import fis.pms.controller.dto.*;
import fis.pms.domain.Files;
import fis.pms.domain.WorkList;
import fis.pms.repository.WorkListRepository;
import fis.pms.service.FileService;
import fis.pms.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;
    private final ImageService imageService;

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

    @GetMapping("/file/scan")
    public List<SearchScanResponse> searchScanFiles(){
        List<Files> scanFiles = imageService.searchFilesByScan();
        return scanFiles.stream()
                .map(SearchScanResponse::createSearchScanResponse)
                .collect(Collectors.toList());
    }

}

