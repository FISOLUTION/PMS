package fis.pms.service.dto;
import fis.pms.repository.dto.WorkListGroupByFileDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class FileWorkInfo {
    private String workerName;
    private LocalDate date;

    public FileWorkInfo(WorkListGroupByFileDTO data){
        workerName = data.getWorkerName();
        date = data.getDate();
    }
}
