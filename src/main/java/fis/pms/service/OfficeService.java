package fis.pms.service;

import fis.pms.domain.Office;
import fis.pms.exception.OfficeException;
import fis.pms.repository.OfficeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OfficeService {

    private final OfficeRepository officeRepository;

    public Office findOffice(String o_Code) {
        return officeRepository.findOne(o_Code);
    }

    public boolean validateOffice(String code, String name) {
        Office office = findOffice(code);
        return office.checkName(name);
    }

}
