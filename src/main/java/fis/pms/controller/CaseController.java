package fis.pms.controller;

import fis.pms.configuator.argumentResolver.Login;
import fis.pms.controller.dto.IndexSaveCaseRequest;
import fis.pms.controller.dto.IndexSaveCaseResponse;
import fis.pms.controller.dto.IndexSearchCaseResponse;
import fis.pms.domain.fileEnum.F_process;
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
    @PatchMapping("/case/index/input")
    public IndexSaveCaseResponse saveIndex(@RequestBody IndexSaveCaseRequest indexSaveCaseRequest, @Login Long workerId) {
        return caseService.saveCases(indexSaveCaseRequest, workerId, F_process.INPUT);
    }

    @PatchMapping("/case/index/check")
    public IndexSaveCaseResponse checkIndex(@RequestBody IndexSaveCaseRequest indexSaveCaseRequest, @Login Long workerId) {
        return caseService.saveCases(indexSaveCaseRequest, workerId, F_process.CHECK);
    }

    /**
    *   작성날짜: 2022/04/01 2:09 PM
    *   작성자: 이승범
    *   작성내용: 건 검색 api
    */
    @GetMapping("/case/index")
    public List<IndexSearchCaseResponse> SearchIndexCase(@RequestParam(value = "docnum", required = false) String c_oldnum,
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
