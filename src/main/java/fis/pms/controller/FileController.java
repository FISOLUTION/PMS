package fis.pms.controller;

import fis.pms.controller.dto.ExportSaveRequest;
import fis.pms.controller.dto.ExportSaveResponse;
import fis.pms.controller.dto.Result;
import fis.pms.controller.dto.filedto.*;
import fis.pms.domain.Files;
import fis.pms.service.FileService;
import fis.pms.service.excelService.ExcelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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

    /**
    *   작성날짜: 2022/03/22 5:47 PM
    *   작성자: 이승범
    *   작성내용: 철 반출 등록
    */
    @PatchMapping("/export/save")
    public ExportSaveResponse saveResponse(@RequestBody @Valid ExportSaveRequest exportSaveRequest) {
        return exportService.updateExportInfo(exportSaveRequest);
    }

}

