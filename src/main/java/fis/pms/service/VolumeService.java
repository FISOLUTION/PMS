package fis.pms.service;

import fis.pms.controller.dto.CasesInfo;
import fis.pms.controller.dto.IndexSaveVolumeRequest;
import fis.pms.controller.dto.IndexSaveVolumeResponse;
import fis.pms.controller.dto.VolumesInfo;
import fis.pms.domain.Cases;
import fis.pms.domain.Files;
import fis.pms.domain.Volume;
import fis.pms.domain.fileEnum.F_process;
import fis.pms.exception.FilesException;
import fis.pms.exception.ProcessOrderException;
import fis.pms.exception.VolumeException;
import fis.pms.repository.CasesRepository;
import fis.pms.repository.FileRepository;
import fis.pms.repository.VolumeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class VolumeService {

    private final VolumeRepository volumeRepository;
    private final CasesRepository casesRepository;
    private final FileRepository fileRepository;
    private final FileService fileService;

    /**
     * 작성날짜: 2022/03/29 1:26 PM
     * 작성자: 이승범
     * 작성내용: 해당 철의 권 정보들과 각 권들의 건 정보들 가져오기
     */
    public List<VolumesInfo> getVolumesInfo(Long f_id) {
        List<VolumesInfo> result = volumeRepository.findByFilesWithFiles(f_id);
        List<Long> volumeIds = toVolumeIds(result);
        Map<Long, List<CasesInfo>> casesMap = casesRepository.findCasesMap(volumeIds);
        result.forEach(volumesInfo -> volumesInfo.setCasesInfoList(casesMap.get(volumesInfo.getV_id())));
        return result;
    }

    /**
     * 작성날짜: 2022/03/29 1:28 PM
     * 작성자: 이승범
     * 작성내용: 권들의 아이디를 반환하는 메서드
     */
    private List<Long> toVolumeIds(List<VolumesInfo> result) {
        return result.stream()
                .map(VolumesInfo::getV_id)
                .collect(Collectors.toList());
    }

    /**
     * 작성날짜: 2022/03/29 1:28 PM
     * 작성자: 이승범
     * 작성내용: 해당 권에 속해있는 건들의 페이지 정보를 토대로 건들 생성
     */
    public IndexSaveVolumeResponse saveCasesPages(IndexSaveVolumeRequest indexSaveVolumeRequest, Long workerId, F_process f_process) {

        Files findFiles = fileRepository.findOne(indexSaveVolumeRequest.getF_id())
                .orElseThrow(()->new FilesException("존재하지 않는 파일입니다."));

        // 색인 입력이 완료된 후에는 색인 입력 불가능. 검수로만 색인 수정 가능
        if (findFiles.getF_process().compareTo(F_process.INPUT) >= 0 && f_process == F_process.INPUT) {
            throw new ProcessOrderException("색인 입력이 완료된 철 입니다. 검수를 이용해 수정해 주세요");
        }

        if (findFiles.getF_process().getNext().compareTo(f_process) < 0)
            throw new ProcessOrderException("아직 이전 단계의 공정이 끝나지 않았습니다.");

        Volume updateVolume = volumeRepository.findOne(indexSaveVolumeRequest.getV_id())
                .orElseThrow(()->new VolumeException("존재하지 않는 권입니다."));

        List<IndexSaveVolumeRequest.PageInfo> pageList = indexSaveVolumeRequest.getV_info();
        IndexSaveVolumeResponse indexSaveVolumeResponse = new IndexSaveVolumeResponse();
        List<Long> result = new ArrayList<>();

        if (updateVolume.getV_pageSaved().compareTo("0") == 0) {
            updateVolume.resetCaseCount(pageList.size());
            for (int i = 0; i < Integer.parseInt(updateVolume.getV_casenum()); i++) {
                Cases cases = Cases.createCases(i, updateVolume, findFiles, pageList.get(i).getStartPage(), pageList.get(i).getEndPage());
                casesRepository.save(cases);
                result.add(cases.getId());
            }
        } else {
            List<Cases> casesList = casesRepository.findByVolume(indexSaveVolumeRequest.getV_id());
            if (pageList.size() > casesList.size()) {
                for (int j = 0; j < casesList.size(); j++) {
                    casesList.get(j).updatePage(pageList.get(j).getStartPage(), pageList.get(j).getEndPage());
                }
                for (int j = casesList.size(); j < pageList.size(); j++) {
                    Cases cases = Cases.createCases(j, updateVolume, findFiles, pageList.get(j).getStartPage(), pageList.get(j).getEndPage());
                    casesRepository.save(cases);
                    casesList.add(cases);
                }
            } // 수정된 권의 건수가 그 전보다 적을 경우 나머지 건들 삭제
            else if (pageList.size() < casesList.size()) {
                for (int j = 0; j < pageList.size(); j++) {
                    casesList.get(j).updatePage(pageList.get(j).getStartPage(), pageList.get(j).getEndPage());
                }
                List<Long> deletedCasesIdList = new ArrayList<>();
                for (int j = pageList.size(); j < casesList.size(); j++) {
                    deletedCasesIdList.add(casesList.get(j).getId());
                }
                casesRepository.deleteRemainCases(deletedCasesIdList);
                // 벌크 연산후 영속성 컨텍스트 다시 구성
                findFiles = fileRepository.findOne(indexSaveVolumeRequest.getF_id())
                        .orElseThrow(()->new FilesException("존재하지 않는 철입니다."));
                updateVolume = volumeRepository.findOne(indexSaveVolumeRequest.getV_id())
                        .orElseThrow(()->new VolumeException("존재하지 않는 권입니다."));
                casesList = casesRepository.findByVolume(indexSaveVolumeRequest.getV_id());
                // 현재 남아있는 건들의 정보들이 다 입력된 상태인가
            } // 수정된 권의 건수가 그 전과 동일할 경우 페이지들만 수정
            else {
                for (int j = 0; j < casesList.size(); j++) {
                    casesList.get(j).updatePage(pageList.get(j).getStartPage(), pageList.get(j).getEndPage());
                }
            }
            updateVolume.updateCaseCount(casesList);

            // 건 삭제시에 남은 건들의 작업이 완료된 경우 해당 권을 작업 완료 처리
            if(checkCaseCount(findFiles, updateVolume, workerId))
                indexSaveVolumeResponse.setComplete(true);

            result = casesList.stream().map(Cases::getId).collect(Collectors.toList());
        }
        updateVolume.updatePageSaved();
        indexSaveVolumeResponse.setC_id(result);
        return indexSaveVolumeResponse;
    }

    /**
     * 작성날짜: 2022/03/29 1:29 PM
     * 작성자: 이승범
     * 작성내용: 권의 casecount가 0이면 해당 권의 색인 or 검수 작업 완료
     */
    public boolean checkCaseCount(Files findFile, Volume findVolume, Long workerId) {
        if (findVolume.getV_casecount().compareTo("0") == 0) {
            findFile.reduceVolumeCount();
            if (fileService.checkVolumeCount(findFile, workerId))
                return true;
        }
        return false;
    }


}
