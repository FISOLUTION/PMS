package fis.pms.controller;

import fis.pms.controller.dto.Result;
import fis.pms.controller.dto.filedto.*;
import fis.pms.domain.Files;
import fis.pms.domain.Office;
import fis.pms.service.FileService;
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

    @PostMapping("/file/preInfo")
    public PreinfoFileSaveResponse saveFile(@RequestBody @Validated PreInfoFileRequest preInfoFileRequest, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            //throw new FormFormattException();
        }
        fileService.preInfoFile();
        return null;
    }

    //사전정보 수정 철기준
    @PatchMapping("/preinfo/file")
    public PreinfoFileSaveResponse modifyResponse(@RequestBody PreInfoFileRequest preInfoFileRequest) {
        //해당되는 f_id 찾아서 update
        return preInfoService.updatePreinfo(preInfoFileRequest);
    }

    //사전정보 철 삭제
    @DeleteMapping("/preinfo/file")
    public PreinfoFileDelResponse delResponse(@RequestBody PreInfoFileDelrequest preInfoFileDelrequest, BindingResult bindingResult) {
        Stream<Long> stream = Arrays.stream(preInfoFileDelrequest.getF_id());
        PreinfoFileDelResponse preinfoFileDelResponse = new PreinfoFileDelResponse();
        stream.forEach(f_id -> {
            try {
                preInfoService.removePreinfo(f_id);
                preinfoFileDelResponse.getF_id().add(f_id);
            } catch (NoSuchElementException noSuchElementException) {
                String exception_id = noSuchElementException.getMessage();
                Object[] args = {f_id};
                preinfoFileDelResponse.setErr_code(errorMessageBinder.bindMessage("delete.file.notexist", args));
            }
        });
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
