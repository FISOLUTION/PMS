package fis.pms.controller;

import fis.pms.controller.dto.IndexSaveCaseRequest;
import fis.pms.controller.dto.IndexSaveCaseResponse;
import fis.pms.service.CaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CaseController {

    private final CaseService caseService;

    @PatchMapping("/index/save")
    public IndexSaveCaseResponse indexSaveCaseRequest(@RequestBody IndexSaveCaseRequest indexSaveCaseRequest) {
        return caseService.saveCases(indexSaveCaseRequest);
    }
}
