package fis.pms.service.dto;

import fis.pms.repository.dto.PerformanceDTO;
import lombok.Data;

@Data
public class OverallPerformanceDTO {
    private PerformanceDTO preInfo;
    private PerformanceDTO export;
    private PerformanceDTO scan;
    private PerformanceDTO imgmodify;
    private PerformanceDTO input;
    private PerformanceDTO check;
    private PerformanceDTO upload;

}