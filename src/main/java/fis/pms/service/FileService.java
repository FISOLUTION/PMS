package fis.pms.service;

import fis.pms.domain.Files;
import fis.pms.repository.FileRepository;
import fis.pms.service.dto.ExportInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    /**
    *   작성날짜: 2022/03/23 11:34 AM
    *   작성자: 이승범
    *   작성내용: 철 단건 반출 등록
    */
    public Long exportFile(ExportInfo exportInfo) {
        fileRepository.findOne(exportInfo.getF_id()).exportFile(exportInfo);
        return exportInfo.getF_id();
    }

    /**
    *   작성날짜: 2022/03/23 12:13 PM
    *   작성자: 이승범
    *   작성내용: 레이블 범위로 철 정보를 가져오기
    */
    public List<Files> searchFileByLabelCode(String slabel, String elabel){
        return fileRepository.findByLabelRange(slabel, elabel);
    }
}
