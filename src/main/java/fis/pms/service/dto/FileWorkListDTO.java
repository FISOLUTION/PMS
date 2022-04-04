package fis.pms.service.dto;

import fis.pms.domain.fileEnum.F_process;
import fis.pms.repository.dto.WorkListGroupByFileDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
@AllArgsConstructor
@Builder
public class FileWorkListDTO {

    private String labelCode;
    private String fileName;
    private String OfficeName;

    Map<F_process, FileWorkInfo> fileWorkInfo;

    public void completeDTO(){
        for(F_process process : F_process.values()){
            if(!fileWorkInfo.containsKey(process)){
                fileWorkInfo.put(process, new FileWorkInfo("미완료", null));
            }
        }
    }

}
