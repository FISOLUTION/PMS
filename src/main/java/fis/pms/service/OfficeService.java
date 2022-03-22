package fis.pms.service;

import fis.pms.domain.Office;
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
}
