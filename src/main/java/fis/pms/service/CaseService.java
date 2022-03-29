package fis.pms.service;

import fis.pms.controller.dto.IndexSaveCaseRequest;
import fis.pms.controller.dto.IndexSaveCaseResponse;
import fis.pms.domain.Cases;
import fis.pms.domain.Files;
import fis.pms.domain.Volume;
import fis.pms.repository.CasesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CaseService {

    private final CasesRepository casesRepository;
    private final VolumeService volumeService;


    public IndexSaveCaseResponse saveCases(IndexSaveCaseRequest indexSaveCaseRequest) {
        //건 테이블 저장
        //건 튜플 first 1인지 체크 후 그에따른 로직 실행
        //상위 테이블에 -1 후 0인지 체크
        //철 테이블 count가 0이되면 f_process 체크 , f_process가 INPUT 이면 check로 바꾼 후 f_complete 타임스탬프 찍기, CHECK 이면 UPLOAD 로 바꾼 후 f_check에 타임스템프 찍기
        //철 테이블 volumecount, 권테이블 casecount 초기화

        Cases findCases = casesRepository.findOneWithFilesVolume(indexSaveCaseRequest.getC_id());
        Files findFile = findCases.getFiles();
        Volume findVolume = findCases.getVolume();

        if (findCases.getC_first().equals("1")) {
            findCases.reduceFirst();
            findVolume.reduceCaseCount();
            volumeService.checkCaseCount(findFile, findVolume);
        }
        findCases.updateCases(indexSaveCaseRequest);
        IndexSaveCaseResponse indexSaveCaseResponse = new IndexSaveCaseResponse();
        indexSaveCaseResponse.setC_id(findCases.getId());
        return indexSaveCaseResponse;
    }
}
