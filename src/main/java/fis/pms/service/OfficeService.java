package fis.pms.service;

import fis.pms.domain.Office;
import fis.pms.exception.OfficeException;
import fis.pms.repository.OfficeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OfficeService {

    private final OfficeRepository officeRepository;

    /**
     * @author 현승구
     * @param code
     * @return
     */
    public Office findOffice(String code) {
        return Optional.ofNullable(officeRepository.findOne(code))
                .orElseThrow(()-> new OfficeException(code, "해당 기관 코드 존재하지 않습니다"));
    }

    /**
     *
     * @param code
     * @param name
     * @return
     */
    public boolean validateOffice(String code, String name) {
        Office office = findOffice(code);
        return office.checkName(name);
    }

}
