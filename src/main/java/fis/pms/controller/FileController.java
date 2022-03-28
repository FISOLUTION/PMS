package fis.pms.controller;

import fis.pms.controller.dto.PreInfoFileUpdateInfo;
import fis.pms.controller.dto.Result;
import fis.pms.controller.dto.filedto.*;
import fis.pms.domain.Files;
import fis.pms.exception.ExcelException;
import fis.pms.exception.FilesException;
import fis.pms.exception.OfficeException;
import fis.pms.controller.dto.PreInfoFileSearchDTO;
import fis.pms.service.FileService;
import fis.pms.service.dto.PreInfoFileInfo;
import fis.pms.service.excelService.ExcelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@Slf4j
public class FileController {

    private final FileService fileService;
    private final ExcelService excelService;

    //사전정보 저장 철기준

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
