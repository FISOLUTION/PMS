package fis.pms.controller;

import fis.pms.controller.dto.PreInfoFileUpdateInfo;
import fis.pms.controller.dto.Result;
import fis.pms.controller.dto.filedto.*;
import fis.pms.domain.Files;
import fis.pms.domain.Office;
import fis.pms.exception.FilesException;
import fis.pms.exception.OfficeException;
import fis.pms.service.FileService;
import fis.pms.service.dto.PreInfoFileInfo;
import fis.pms.service.dto.PreInfoFileRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;


@RestController
@RequiredArgsConstructor
@Slf4j
public class FileController {

    private final FileService fileService;

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


    @DeleteMapping("/file/preInfo")
    public PreinfoFileDelResponse deletePreInfo(@RequestBody PreInfoFileDelrequest preInfoFileDelrequest) {

        preInfoFileDelrequest.getF_id().forEach(id -> {
            fileService.remove(id);
        })
        PreinfoFileDelResponse preinfoFileDelResponse = new PreinfoFileDelResponse();
        return preinfoFileDelResponse;
    }

    //사전정보 검색
    @GetMapping("/preinfo/file")
    public List<PreinfoFileSearchResponse> searchResponse(@ModelAttribute PreInfoSearchDTO preInfoSearchDTO,
                                                          BindingResult bindingResult) {
        FindPreinfoBySearch findPreinfoBySearch = new FindPreinfoBySearch();
        if (preInfoSearchDTO.getO_code() != null) {
            Office office = commonService.findOne(preInfoSearchDTO.getO_code());
            findPreinfoBySearch.setOffice(office);
        }
        findPreinfoBySearch.setF_labelcode(preInfoSearchDTO.getF_labelcode());
        findPreinfoBySearch.setF_name(preInfoSearchDTO.getF_name());
        findPreinfoBySearch.setF_pyear(preInfoSearchDTO.getF_pyear());

        return preInfoService.searchFileByPreinfo(findPreinfoBySearch);
    }

    @PostMapping("preinfo/excel")
    public Result<List> excelUpdate(HttpServletRequest request, HttpServletResponse response, @RequestParam("excelfile") MultipartFile file) throws IOException, NoSuchMethodException {

        List<ExcelUpdateDTO> dataList = new ArrayList<>();
        List<PreInfoFileRequest> daoList = new ArrayList<>();
        try {
            preInfoService.reset();
            excelService.excelToJson(file, ExcelUpdateDTO.class).forEach(data -> {
                dataList.add((ExcelUpdateDTO) data);
                daoList.add(new PreInfoFileRequest((ExcelUpdateDTO) data));
            });
            daoList.forEach(preInfoFileRequest -> {
                try {
                    preInfoService.savePreinfo(preInfoFileRequest);
                } catch (Exception e){

                }
            });
            return new Result<>(daoList);
        } catch (Exception exception){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            exception.printStackTrace();
            return null;
        }
    }

    @GetMapping("preinfo/excel")
    public void excelFile(HttpServletResponse response) throws IOException {
        List<Files> files = preInfoService.findAll();
        List<ExcelUpdateDTO> dataList = new ArrayList<>();
        files.stream().forEach(data -> {
            ExcelUpdateDTO excelUpdateDTO = new ExcelUpdateDTO(data);
            dataList.add(excelUpdateDTO);
        });

        // 컨텐츠 타입과 파일명 지정
        response.setContentType("ms-vnd/excel");
        //response.setHeader("Content-Disposition", "attachment;filename=example.xls");
        response.setHeader("Content-Disposition", "attachment;filename=preInfo.xlsx");

        // Excel File Output
        Workbook workbook = excelService.dbToExcel(dataList, ExcelUpdateDTO.class);
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
