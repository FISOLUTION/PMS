package fis.pms.service;

import fis.pms.controller.dto.IndexSaveLabelRequest;
import fis.pms.controller.dto.IndexSaveLabelResponse;
import fis.pms.controller.dto.PreInfoFileUpdateInfo;
import fis.pms.domain.*;
import fis.pms.domain.fileEnum.F_process;
import fis.pms.exception.FilesException;
import fis.pms.exception.OfficeException;
import fis.pms.repository.CasesRepository;
import fis.pms.repository.FileRepository;
import fis.pms.controller.dto.PreInfoFileSearchDTO;
import fis.pms.repository.VolumeRepository;
import fis.pms.repository.WorkListRepository;
import fis.pms.service.dto.ExportInfo;
import fis.pms.service.dto.FindIndexPreinfo;
import fis.pms.service.dto.PreInfoFileInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class FileService {

    private final FileRepository fileRepository;
    private final OfficeService officeService;
    private final WorkListRepository workListRepository;
    private final VolumeRepository volumeRepository;
    private final CasesRepository casesRepository;


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
        file.makePreInfo();
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

        Files files = fileRepository.findOne(indexSaveLabelRequest.getF_id()).get();      //file 찾아오기.

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
        files = fileRepository.findOne(indexSaveLabelRequest.getF_id()).get();
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
