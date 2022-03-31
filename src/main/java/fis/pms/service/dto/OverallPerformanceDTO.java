package fis.pms.service.dto;

import fis.pms.repository.dto.PerformanceDTO;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class OverallPerformanceDTO {

    private Map<String, PerformanceDTO> data = new HashMap<>();

    public static OverallPerformanceDTO createOverall(List<PerformanceDTO> dto) {
        OverallPerformanceDTO overall = new OverallPerformanceDTO();
        dto.stream().forEach(performanceDTO -> {
            overall.data.put(performanceDTO.getName(), performanceDTO);
        });
        return overall;
    }
}