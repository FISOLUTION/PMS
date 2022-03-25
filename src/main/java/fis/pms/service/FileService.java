package fis.pms.service;

import fis.pms.domain.Files;
import fis.pms.domain.WorkList;
import fis.pms.domain.fileEnum.F_process;
import fis.pms.repository.FileRepository;
import fis.pms.repository.WorkListRepository;
import fis.pms.service.dto.ExportInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final WorkListRepository workListRepository;
    private final ImageService imageService;

    /**
     * 작성날짜: 2022/03/23 11:34 AM
     * 작성자: 이승범
     * 작성내용: 철 단건 반출 등록
     */
    public Long exportFile(ExportInfo exportInfo) {

        Files findFile = fileRepository.findOneWithOffice(exportInfo.getF_id());

        if (findFile.getF_process().equals(F_process.PREINFO)) {

            // 소속 기관코드 디렉토리가 없다면 생성
            imageService.mkdir(imageService.getOriginOfficePath(findFile));
            imageService.mkdir(imageService.getModifyOfficePath(findFile));

            // 소속기관 디렉토리 안에 해당 철의 이미지를 저장하기위한 디레토리 생성
            imageService.mkdir(imageService.getOriginFullPath(findFile));
            imageService.mkdir(imageService.getModifyFullPath(findFile));

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
    public List<Files> searchFileByLabelCode(String slabel, String elabel) {
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
}

