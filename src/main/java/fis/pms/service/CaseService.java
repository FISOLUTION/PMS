package fis.pms.service;

import fis.pms.controller.dto.IndexSaveCaseRequest;
import fis.pms.controller.dto.IndexSaveCaseResponse;
import fis.pms.controller.dto.IndexSearchCaseResponse;
import fis.pms.domain.Cases;
import fis.pms.domain.Files;
import fis.pms.domain.Volume;
import fis.pms.domain.fileEnum.F_process;
import fis.pms.exception.ProcessOrderException;
import fis.pms.repository.CasesRepository;
import fis.pms.repository.VolumeRepository;
import fis.pms.repository.search.FindIndexCaseInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CaseService {

    private final CasesRepository casesRepository;
    private final WorkListService workListService;
    private final VolumeRepository volumeRepository;

    /**
    *   작성날짜: 2022/03/29 1:39 PM
    *   작성자: 이승범
    *   작성내용: 건의 색인 정보 입력
    */
    public IndexSaveCaseResponse saveCases(IndexSaveCaseRequest indexSaveCaseRequest, Long workerId, F_process f_process) {
        //건 테이블 저장
        //건 튜플 first 1인지 체크 후 그에따른 로직 실행
        //상위 테이블에 -1 후 0인지 체크
        //철 테이블 count가 0이되면 f_process 체크 , f_process가 INPUT 이면 check로 바꾼 후 f_complete 타임스탬프 찍기, CHECK 이면 UPLOAD 로 바꾼 후 f_check에 타임스템프 찍기
        //철 테이블 volumecount, 권테이블 casecount 초기화

        // f_process(현재 작업)은 직전 단계를 끝내야 가능
        Cases findCases = casesRepository.findOneWithFilesVolume(indexSaveCaseRequest.getC_id());
        Files findFile = findCases.getFiles();
        Volume findVolume = findCases.getVolume();

        if (findFile.getF_process().getNext().compareTo(f_process) < 0) {
            throw new ProcessOrderException("아직 이전 단계의 공정이 끝나지 않았습니다.");
        }

        // 색인 입력이 완료된 후에는 색인 입력 불가능. 검수로만 색인 수정 가능
        if (findFile.getF_process().compareTo(F_process.INPUT) >= 0 && f_process == F_process.INPUT) {
            throw new ProcessOrderException("색인 입력이 완료된 철 입니다. 검수를 이용해 수정해 주세요");
        }

        IndexSaveCaseResponse indexSaveCaseResponse = new IndexSaveCaseResponse();

        if (findCases.getC_first().equals("1")) {
            findCases.reduceFirst();
            findVolume.reduceCaseCount();
            if (checkCaseCount(findFile, findVolume, workerId)) {
                indexSaveCaseResponse.setComplete(true);
            }
        }

        findCases.updateCases(indexSaveCaseRequest);
        indexSaveCaseResponse.setC_id(findCases.getId());
        return indexSaveCaseResponse;
    }

    /**
    *   작성날짜: 2022/03/29 1:40 PM
    *   작성자: 이승범
    *   작성내용: 건 검색 api
    */
    public List<Cases> searchCasesByCasesInfo(FindIndexCaseInfo findIndexCaseInfo) {
        return casesRepository.findByOldNumTitleReceiverWithFiles(
                findIndexCaseInfo.getC_oldnum(), findIndexCaseInfo.getC_title(), findIndexCaseInfo.getC_receiver());
    }

    /**
     * 작성날짜: 2022/03/29 1:29 PM
     * 작성자: 이승범
     * 작성내용: 권의 casecount 0이면 해당 권의 색인 or 검수 작업 완료
     */
    public boolean checkCaseCount(Files findFile, Volume findVolume, Long workerId) {
        if (findVolume.getV_casecount().compareTo("0") == 0) {
            findFile.reduceVolumeCount();
            if (checkVolumeCount(findFile, workerId))
                return true;
        }
        return false;
    }

    /**
     * 작성날짜: 2022/03/29 1:54 PM
     * 작성자: 이승범
     * 작성내용: 철의 volumecount가 0이 되면 해당 철의 색인 or 검수 작업 완료
     */
    public boolean checkVolumeCount(Files findFile, Long workerId) {
        // 해당 철이 갖고있는 권의 작업이 모두 끝났을경우 해당 철 작업 완료 처리
        if (findFile.getF_volumecount().compareTo("0") == 0) {
            F_process f_process = findFile.getF_process() == F_process.INPUT ? F_process.CHECK : F_process.INPUT;
            workListService.reflectWorkList(findFile, workerId, f_process);
            findFile.updateProcess();
            List<Cases> findCasesList = casesRepository.findByFiles(findFile);

            for (Cases cases : findCasesList) {
                cases.resetCount();
            }
            List<Volume> findVolumeList = volumeRepository.findByFiles(findFile);
            for (Volume volume : findVolumeList) {
                volume.resetCount();
            }
            return true;
        }
        return false;
    }

    public void updateForUpload(){

    }
}
