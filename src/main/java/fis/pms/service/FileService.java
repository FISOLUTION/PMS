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
}

