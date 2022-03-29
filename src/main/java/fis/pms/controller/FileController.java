package fis.pms.controller;


import fis.pms.controller.dto.*;
import fis.pms.controller.dto.filedto.*;
import fis.pms.domain.Files;
import fis.pms.exception.ExcelException;
import fis.pms.exception.FilesException;
import fis.pms.exception.OfficeException;
import fis.pms.repository.search.FindIndexDetailInfo;
import fis.pms.service.FileService;
import fis.pms.service.dto.FindIndexPreinfo;
import fis.pms.service.dto.PreInfoFileInfo;
import fis.pms.service.excelService.ExcelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;
    private final ExcelService excelService;

    /**
     * @implNote 기관별 등록된 철의 갯수를 반환합니다. manage 작업할 때 쓰입니다
     * @return 기관별 등록된 철의 갯수
     * @author 현승구
     */
    @GetMapping("/file/registrationStatus")
    public Result registrationStatus(){
        return new Result(fileService.getRegistration());
    }

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
     * 작성내용: 사전조사 단계 철 레이블 범위 검색
     */
    @GetMapping("/file/export/label")
    public List<ExportSearchLabelResponse> searchPreInfoByLabelCode(@RequestParam(value = "slabel", required = false) String slabel,
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
    public List<ExportSearchResponse> searchExportByDate(
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
    public List<ExportSearchResponse> searchExportByBox(@RequestParam(value = "sbox", required = false) String sbox,
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
    public List<IndexSearchResponse> SearchIndexByOffice(@PathVariable String o_code,
                                                          @RequestParam(value = "box", required = false) String f_bnum,
                                                          @RequestParam(value = "label", required = false) String f_labelcode) {

        FindIndexPreinfo findIndexPreinfo = FindIndexPreinfo.createFindIndexPreinfo(o_code, f_bnum, f_labelcode);
        return fileService.searchFilesByPreInfo(findIndexPreinfo).stream()
                .map(IndexSearchResponse::createIndexSearchResponse)
                .collect(Collectors.toList());
    }

    /**
    *   작성날짜: 2022/03/29 1:13 PM
    *   작성자: 이승범
    *   작성내용: 철 색인 작업
    */
    @PostMapping("/file/index")
    public IndexSaveLabelResponse SaveIndex(@RequestBody IndexSaveLabelRequest indexSaveLabelRequest) {
        return fileService.saveFilesAndVolume(indexSaveLabelRequest);
    }

    /**
    *   작성날짜: 2022/03/29 1:22 PM
    *   작성자: 이승범
    *   작성내용: 철 항목 검색 api
    */
    @GetMapping("/file/index")
    public List<IndexSearchLabelResponse> SearchLabel(@RequestParam(value = "f_name", required = false) String f_name,
                                                                    @RequestParam(value = "syear", required = false) String syear,
                                                                    @RequestParam(value = "eyear", required = false) String eyear) {
        FindIndexDetailInfo findIndexDetailInfo = new FindIndexDetailInfo();
        findIndexDetailInfo.setF_name(f_name);
        findIndexDetailInfo.setF_pyear(syear);
        findIndexDetailInfo.setF_eyear(eyear);

        return fileService.searchFilesByDetailInfo(findIndexDetailInfo).stream()
                .map(IndexSearchLabelResponse::createIndexSearchLabelResponse)
                .collect(Collectors.toList());
    }

    /**
    *   작성날짜: 2022/03/29 2:07 PM
    *   작성자: 이승범
    *   작성내용: 색인 단계에서 철 삭제
    */
    @DeleteMapping("/file/index/{f_id}")
    public Long deleteIndex(@PathVariable Long f_id) {
        return fileService.deleteIndex(f_id);
    }

    /**
     *
     * @param preInfoFileInfo
     * @return 철의 id 값 리턴합니다.
     * @throws OfficeException 기관의 유효성 검사
     * @throws FilesException 중복된 레이블코드 존재하는지 검사
     * @author 현승구
     */
    @PostMapping("/file/preInfo")
    public PreinfoFileSaveResponse joinPreInfo(@RequestBody @Validated PreInfoFileInfo preInfoFileInfo) throws OfficeException, FilesException {
        Long id = fileService.preInfoFile(preInfoFileInfo);
        return new PreinfoFileSaveResponse(id);
    }

    /**
     * @param preInfoFileUpdateInfo
     * @return 수정한 철의 Id 값 반환
     * @throws OfficeException - 기관의 유효성 검사
     * @throws FilesException - 중복된 레이블코드 존재여부 검사
     * @author 현승구
     */
    //사전정보 수정 철기준
    @PatchMapping("/file/preInfo")
    public PreinfoFileSaveResponse modifyPreInfo(@RequestBody PreInfoFileUpdateInfo preInfoFileUpdateInfo) throws OfficeException, FilesException {
        //해당되는 f_id 찾아서 update
        fileService.updatePreInfo(preInfoFileUpdateInfo);
        return null;
    }

    /**
     * @author 현승구
     * @param request
     * @return 삭제된 철의 id
     * @throws FilesException 삭제할 철이 존재하지 않으면 throw 합니다
     * @implNote 철을 삭제합니다
     */
    @DeleteMapping("/file/preInfo")
    public PreinfoFileDelResponse deletePreInfo(@RequestBody PreInfoFileDelrequest request) throws FilesException {
        PreinfoFileDelResponse response = new PreinfoFileDelResponse();
        request.getF_id().stream().forEach(id -> {
            fileService.remove(id);
            request.getF_id().add(id);
        });
        return response;
    }

    /**
     *
     * @param searchDTO
     * @return file 검색 결과 반환 (이름, 박스, 기관 코드, 시작년도, 철 이름으로 검색)
     * @throws OfficeException - 기관코드 없으면
     */
    @GetMapping("/preInfo/file")
    public List<PreInfoFileSearchResponse> searchResponse(@ModelAttribute PreInfoFileSearchDTO searchDTO) throws OfficeException {
        return fileService.findPreInfoFile(searchDTO).stream()
                .map(files -> PreInfoFileSearchResponse.createResponse(files))
                .collect(Collectors.toList());
    }

    /**
     *
     * @param excelFile
     * @return 엑셀 분석한 파일의 값을 리턴
     * @throws ExcelException 액셀파일 읽어드리는데에 발생하는 오류
     * @throws NoSuchMethodException 엑셀 파일 읽어드릴때 reflection 사용의 오류
     * @throws OfficeException 기관에 대한 유효성 예외
     * @throws FilesException 레이블이 이미 존재하면 예외 발생
     */
    @PostMapping("preinfo/excel")
    public Result<List> excelUpdate(@RequestParam MultipartFile excelFile) throws ExcelException, NoSuchMethodException {
        List<PreInfoFileInfo> preInfoFileInfoList = excelService.excelToJson(excelFile, ExcelUpdateDTO.class).stream()
                .map(data -> new PreInfoFileInfo((ExcelUpdateDTO)data))
                .collect(Collectors.toList());
        preInfoFileInfoList.forEach(preInfoFileInfo -> {
            fileService.preInfoFile(preInfoFileInfo);
        });

        return new Result<>(preInfoFileInfoList);
    }

    /**
     * @return preInfo 완료 혹은 preInfo가 된 목록들에 대한 철들을 모두 보냅니다.
     * @param response
     * @throws IOException
     */
    @GetMapping("preinfo/excel")
    public void excelFile(HttpServletResponse response) throws IOException {
        List<Files> files = fileService.findAll();
        List<ExcelUpdateDTO> dataList = new ArrayList<>();
        files.stream().forEach(data -> {
            ExcelUpdateDTO excelUpdateDTO = new ExcelUpdateDTO(data);
            dataList.add(excelUpdateDTO);
        });

        // 컨텐츠 타입과 파일명 지정
        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename=preInfo.xlsx");

        // Excel File Output
        Workbook workbook = excelService.dbToExcel(dataList, ExcelUpdateDTO.class);
        workbook.write(response.getOutputStream());
        workbook.close();
    }

}

