package fis.pms.service;

import fis.pms.controller.dto.PreInfoFileUpdateInfo;
import fis.pms.domain.Files;
import fis.pms.domain.Office;
import fis.pms.exception.FilesException;
import fis.pms.exception.OfficeException;
import fis.pms.repository.FileRepository;
import fis.pms.controller.dto.PreInfoFileSearchDTO;
import fis.pms.service.dto.PreInfoFileInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class FileService {

    private final FileRepository fileRepository;
    private final OfficeService officeService;

    /**
     * @author 현승구
     * @param labelCode 철의 고유한 값
     * @return empty 이면 true 반환
     * @implNote 철을 레이블 코드로 검색하여 존재 여부 확인
     */
    public boolean isEmpty(String labelCode){
        return fileRepository.findByLabel(labelCode).isEmpty();
    }

    public Long save(Files files) throws FilesException {
        if(isEmpty(files.getF_labelcode())) throw new FilesException("중복된 레이블 코드가 있습니다");
        return fileRepository.save(files);
    }

    /**
     * @author 현승구
     * @param preInfoFileInfo
     * @implNote file을 사전 조사하기 위한 서비스 로직입니다.
     * <p>
     * 1. office 에 대한 유효성 검사하를 합니다
     * <p>
     * 2. 레이블 코드가 이미 존재하는 지에 대한 유효성 검사를 진행합니다.
     * @throws OfficeException 기관에 대한 유효성 예외
     * @throws FilesException 레이블이 이미 존재하면 예외 발생
     * @return 사전 조사한 철의 id를 반환합니다
     */
    public Long preInfoFile(PreInfoFileInfo preInfoFileInfo) throws FilesException, OfficeException {
        Office office = officeService.findById(preInfoFileInfo.getO_code());
        if(!officeService.validateOffice(office.getO_code(), office.getO_name())) throw new OfficeException("해당 기관코드와 기관이름이 맞지 않습니다");
        // dto -> Entity
        Files file = preInfoFileInfo.createFiles(office);
        return save(file);
    }

    /**
     * @Author: 현승구
     * @param dto 수정가능한 사전조사 정보를 담은 DTO입니다
     * @return 철의 id 값
     * @implNote
     * file을 사전 조사하기 위한 서비스 로직입니다.
     * <p>
     * 1. office 에 대한 유효성 검사하를 합니다
     *  <p>
     *  2. 레이블 코드가 이미 존재하는 지에 대한 유효성 검사를 진행합니다.
     *  @throws OfficeException 기관에 대한 유효성 예외
     *  @throws FilesException 레이블이 이미 존재하면 예외 발생
     * @author 현승구
     */
    public Long updatePreInfo(PreInfoFileUpdateInfo dto) throws OfficeException, FilesException {
        // 기관 유효성 검사
        Office office = officeService.findById(dto.getO_code());
        if(!officeService.validateOffice(office.getO_code(), office.getO_name())) throw new OfficeException("해당 기관코드와 기관이름이 맞지 않습니다");
        // dto -> Entity
        Files file = fileRepository.findOne(dto.getF_id())
                .orElseThrow(() -> new FilesException("존재하는 철이 아닙니다"));
        // 후에 modelmapper 라던지 통해서 데이터 재가공 필요할 수도 있음
        file.preInfoUpdate(office, dto);
        return file.getF_id();
    }

    public Optional<Files> findOne(Long id){
        return fileRepository.findOne(id);
    }

    public Long remove(Long id) throws FilesException {
        Optional<Files> file = findOne(id);
        return fileRepository.remove(file.orElseThrow( ()-> new FilesException(id, "존재하지 않는 철입니다")));
    }

    public List<Files> findPreInfoFile(PreInfoFileSearchDTO searchDTO) throws OfficeException {
        Office office = officeService.findById(searchDTO.getO_code());
        return fileRepository.preInfoSearch(office, searchDTO.getF_labelcode(), searchDTO.getF_name(), searchDTO.getF_pyear(), searchDTO.getBNum());
    }

    public List<Files> findAll() {
        return fileRepository.findAll();
    }
}
