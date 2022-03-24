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
import java.util.List;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final WorkListRepository workListRepository;

    @Value("${image.path.origin}")
    private String originPath;

    @Value("${image.path.modify}")
    private String modifyPath;

    /**
     * 작성날짜: 2022/03/23 11:34 AM
     * 작성자: 이승범
     * 작성내용: 철 단건 반출 등록
     */
    public Long exportFile(ExportInfo exportInfo) {

        Files findFile = fileRepository.findOneWithOffice(exportInfo.getF_id());

        // 기관코드로 이미지 저장 디렉토리 생성
        makeOfficeDir(findFile.getOffice().getO_code());

        // file export 처리
        findFile.exportFile(exportInfo);
        WorkList workList = WorkList.createWorkList(findFile, F_process.EXPORT);
        workListRepository.save(workList);

        return exportInfo.getF_id();
    }

    /**
    *   작성날짜: 2022/03/24 10:16 AM
    *   작성자: 이승범
    *   작성내용: 이미지 저장을 위한 기관코드 디렉토리 생성
    */
    private void makeOfficeDir(String o_code) {
        String originOfficePath = originPath + o_code + '/';
        String modifyOfficePath = modifyPath + o_code + '/';
        File originDirectory = new File(originOfficePath);
        if (!originDirectory.exists()) {
            originDirectory.mkdir();
        }
        File modifyDirectory = new File(modifyOfficePath);
        if (!modifyDirectory.exists()) {
            modifyDirectory.mkdir();
        }
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
    public List<Files> searchFilesByDate(String sdate, String edate) {
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

