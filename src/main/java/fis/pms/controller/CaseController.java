package fis.pms.controller;

import fis.pms.controller.dto.IndexSaveCaseRequest;
import fis.pms.controller.dto.IndexSaveCaseResponse;
import fis.pms.controller.dto.IndexSearchCaseResponse;
import fis.pms.repository.search.FindIndexCaseInfo;
import fis.pms.service.CaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CaseController {

    private final CaseService caseService;

    /**
    *   작성날짜: 2022/03/29 1:42 PM
    *   작성자: 이승범
    *   작성내용: 건 색인 입력 api
    */
    @PatchMapping("/index/save")
    public IndexSaveCaseResponse indexSaveCaseRequest(@RequestBody IndexSaveCaseRequest indexSaveCaseRequest) {
        return caseService.saveCases(indexSaveCaseRequest);
    }

    /**
    *   작성날짜: 2022/03/29 1:41 PM
    *   작성자: 이승범
    *   작성내용: 건 검색 api
    */
    @GetMapping("/index/case")
    public List<IndexSearchCaseResponse> indexSearchCaseResponse(@RequestParam(value = "docnum", required = false) String c_oldnum,
                                                                 @RequestParam(value = "c_name", required = false) String c_title,
                                                                 @RequestParam(value = "c_receiver", required = false) String c_receiver) {
        FindIndexCaseInfo findIndexCaseInfo = new FindIndexCaseInfo();
        findIndexCaseInfo.setC_oldnum(c_oldnum);
        findIndexCaseInfo.setC_title(c_title);
        findIndexCaseInfo.setC_receiver(c_receiver);
        return caseService.searchCasesByCasesInfo(findIndexCaseInfo).stream()
                .map(IndexSearchCaseResponse::createIndexSearchCaseResponse)
                .collect(Collectors.toList());
    }
}
