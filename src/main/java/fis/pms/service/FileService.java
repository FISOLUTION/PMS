package fis.pms.service;

import fis.pms.controller.dto.IndexSaveLabelRequest;
import fis.pms.controller.dto.IndexSaveLabelResponse;
import fis.pms.domain.Cases;
import fis.pms.domain.Files;
import fis.pms.domain.Volume;
import fis.pms.domain.WorkList;
import fis.pms.domain.fileEnum.F_process;
import fis.pms.repository.CasesRepository;
import fis.pms.repository.FileRepository;
import fis.pms.repository.VolumeRepository;
import fis.pms.repository.WorkListRepository;
import fis.pms.service.dto.FindIndexPreinfo;
import fis.pms.service.dto.ExportInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final WorkListRepository workListRepository;
    private final VolumeRepository volumeRepository;
    private final CasesRepository casesRepository;

    /**
     * 작성날짜: 2022/03/23 11:34 AM
     * 작성자: 이승범
     * 작성내용: 철 단건 반출 등록
     */
    public Long exportFile(ExportInfo exportInfo) {

        Files findFile = fileRepository.findOneWithOffice(exportInfo.getF_id());

        if (findFile.getF_process().equals(F_process.PREINFO)) {

            // file export 처리
            findFile.exportFile(exportInfo);
            WorkList workList = WorkList.createWorkList(findFile, F_process.EXPORT);
            workListRepository.save(workList);

            return exportInfo.getF_id();
        }
        return null;
    }

    /**
     * 작성날짜: 2022/03/23 12:13 PM
     * 작성자: 이승범
     * 작성내용: 반출된 철 레이블 범위 검색
     */
    public List<Files> searchFilesByLabelCode(String slabel, String elabel) {
        return fileRepository.findByLabelRange(slabel, elabel);
    }

    /**
     * 작성날짜: 2022/03/23 1:06 PM
     * 작성자: 이승범
     * 작성내용: 반출된 철 날짜 범위 검색
     */
    public List<Files> searchFilesByDate(LocalDate sdate, LocalDate edate) {
        return fileRepository.findByDateRange(sdate, edate);
    }

    /**
     * 작성날짜: 2022/03/23 1:26 PM
     * 작성자: 이승범
     * 작성내용: 반출된 철 박스 범위 검색
     */
    public List<Files> searchFilesByBox(String sbox, String ebox) {
        return fileRepository.findByBoxRange(sbox, ebox);
    }

    /**
    *   작성날짜: 2022/03/25 5:09 PM
    *   작성자: 이승범
    *   작성내용: 색인 작업할 철 목록 가져오기
    */
    public List<Files> searchFilesByPreInfo(FindIndexPreinfo findIndexPreinfo) {
        //Files 테이블에서 o_code, b_num, f_labelcode로 검색
        List<Files> findList = fileRepository.findByOcodeBoxNumLabel(
                findIndexPreinfo.getO_code(),
                findIndexPreinfo.getB_num(),
                findIndexPreinfo.getF_labelcode()
        );
        return findList;
    }

    /**
    *   작성날짜: 2022/03/25 5:49 PM
    *   작성자: 이승범
    *   작성내용: 철 색인 작업
    */
    public IndexSaveLabelResponse saveFilesAndVolume(IndexSaveLabelRequest indexSaveLabelRequest) {
        int reqVolumeAmount = Integer.parseInt(indexSaveLabelRequest.getF_volumeamount());   //총 권호수 만큼 카운터 생성

        Files files = fileRepository.findOne(indexSaveLabelRequest.getF_id());      //file 찾아오기.

        IndexSaveLabelResponse indexSaveLabelResponse = new IndexSaveLabelResponse();

        // 색인 작업에서 최초로 철 정보를 저장할 경우
        if (files.getF_volumeSaved().compareTo("0") == 0) {
            //총 권호수 만큼 권 테이블에 생성
            for (int i = 1; i <= reqVolumeAmount; i++) {
                Volume volume = Volume.createVolume(files, i);
                volumeRepository.save(volume);
            }
        } // 철 정보 수정시 권호수가 줄었으면 그만큼 volume 삭제
        else if (reqVolumeAmount < Integer.parseInt(files.getF_volumeamount())) {
            volumeRepository.deleteByVolumeAmountOfFiles(files, reqVolumeAmount);
        } // 철 정보 수정시 권호수가 늘었으면 그만큼 volume 추가
        else {
            for (int i = Integer.parseInt(files.getF_volumeamount()) + 1; i <= reqVolumeAmount; i++) {
                Volume volume = Volume.createVolume(files, i);
                volumeRepository.save(volume);
            }
        }

        // 권호수 수정시 달라진 volumeCount 확인
        int volumeCount = 0;
        List<Volume> volumes = volumeRepository.findByFiles(files);
        for (Volume volume : volumes) {
            String caseCount = Optional.ofNullable(volume.getV_casecount()).orElse("-1");
            if(caseCount.equals("0")){
                volumeCount++;
            }
        }

        //file 정보 업데이트
        files = fileRepository.findOne(indexSaveLabelRequest.getF_id());
        files.updateFileIndex(indexSaveLabelRequest, volumeCount);
        checkVolumeCount(files);

        List<Long> result = volumes.stream()
                .map(Volume::getId)
                .collect(Collectors.toList());

        //dto 값 세팅
        indexSaveLabelResponse.setF_id(files.getF_id());
        indexSaveLabelResponse.setV_id(result);
        return indexSaveLabelResponse;
    }

    // 2022-03-11 이승범 : 철에 있는 권들의 정보가 모두 입력되었는지 확인
    public void checkVolumeCount(Files findFile) {
        if (findFile.getF_volumecount().compareTo("0") == 0) {
            findFile.updateProcess();
            List<Cases> findCasesList = casesRepository.findByFiles(findFile);
            for (Cases cases : findCasesList) {
                cases.resetCount();
            }
            List<Volume> findVolumeList = volumeRepository.findByFiles(findFile);
            for (Volume volume : findVolumeList) {
                volume.resetCount();
            }
        }
    }


}

