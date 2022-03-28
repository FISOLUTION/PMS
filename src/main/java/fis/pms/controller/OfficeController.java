package fis.pms.controller;

import fis.pms.controller.dto.ImportOfficeJsonDto;
import fis.pms.controller.dto.OfficeForm;
import fis.pms.controller.dto.Result;
import fis.pms.domain.Office;
import fis.pms.exception.ExcelException;
import fis.pms.exception.OfficeException;
import fis.pms.service.OfficeService;
import fis.pms.service.excelService.ExcelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OfficeController {

    private final OfficeService officeService;
    private final ExcelService excelService;

    /**
     * @author 현승구
     * @param excelFile
     * @return 엑셀을 분석한 기관 정보 리스트를 반환합니다
     * @throws ExcelException
     * @throws NoSuchMethodException
     * @throws OfficeException
     */
    @PostMapping("office/excel")
    public List<OfficeForm> importOfficeExcel(@RequestParam MultipartFile excelFile) throws ExcelException, NoSuchMethodException, OfficeException {
        List<OfficeForm> officeFormList = new ArrayList<>();
        excelService.excelToJson(excelFile, OfficeForm.class).stream()
                .map(form -> {
                            OfficeForm officeForm = (OfficeForm) form;
                            officeFormList.add(officeForm);
                            return officeForm.createOffice();
                        })
                .collect(Collectors.toList())
                .forEach(office -> {
                    officeService.save(office);
                });
        return officeFormList;
    }

    /**
     * @author 현승구
     * @param importOfficeJsonDto
     * @return json 리스트를 주면 모두 저장하기 때문에 저장한 기관 정보 리스트를 반환합니다
     * @throws OfficeException
     */
    @PostMapping("office/json")
    public List<OfficeForm> importOfficeJson(@RequestBody ImportOfficeJsonDto importOfficeJsonDto) throws OfficeException{
        List<OfficeForm> dataList = importOfficeJsonDto.getData();
        dataList.forEach(form -> {
            Office office = form.createOffice();
            officeService.save(office);
        });
        return dataList;
    }

    /**
     * @author 현승구
     * @param officeForm
     * @return 단건에 대한 저장입니다. 저장한 기관 정보를 줍니다
     * @throws OfficeException
     */
    @PostMapping("office")
    public OfficeForm importOfficeJson(@RequestBody OfficeForm officeForm) throws OfficeException{
        officeService.save(officeForm.createOffice());
        return officeForm;
    }

    /**
     * @author 현승구
     * @param o_name
     * @return 기관 검색 결과
     */
    @GetMapping("search/office")
    public Result searchOfficeByName(@RequestParam(required = false) String o_name) {
        List<Office> offices = officeService.findByName(o_name);
        return new Result(offices);
    }

    /**
     * @author 현승구
     * @param o_code
     * @return 기관 검색 결과
     */
    @GetMapping("search/office/{o_code}")
    public Result searchOfficeByCode(@PathVariable String o_code) {
        List<Office> offices = officeService.searchCode(o_code);
        return new Result(offices);
    }

}
