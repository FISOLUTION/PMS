package fis.pms.service;

import fis.pms.domain.Office;
import fis.pms.exception.OfficeException;
import fis.pms.repository.OfficeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OfficeService {

    private final OfficeRepository officeRepository;

    /**
     * @param office
     * @return 저장한 Office의 아이디값(code) 값을 반환 합니다
     */
    public String save(Office office) {
        if(!validateOfficeCode(office.getO_code())) throw new OfficeException("존재하는 기관이 있습니다");
        else return officeRepository.save(office);
    }

    /**
     * @author 현승구
     * @param code 기관코드(아이디)
     * @return 기관코드로 검색
     */
    public Office findById(String code) {
        return officeRepository.findOne(code)
                .orElseThrow(() -> new OfficeException(code, "해당 기관 코드 존재하지 않습니다"));
    }

    /**
     * @param code 기관코드
     * @param name 기관이름
     * @return
     * @author 현승구
     */
    public boolean validateOffice(String code, String name) {
        Office office = findById(code);
        return office.checkName(name);
    }

    /**
     * @param code
     * @return 기관코드 존재하면 false 반환 존재하지 않으면 true 반환
     * @author 현승구
     */
    public boolean validateOfficeCode(String code){
        if(officeRepository.findOne(code).isEmpty()) return true;
        else return false;
    }

    public List<Office> findByName(String o_name) {
        return officeRepository.findByName(o_name);
    }

    public List<Office> searchCode(String code) {
        return officeRepository.findByCode(code);
    }
}
