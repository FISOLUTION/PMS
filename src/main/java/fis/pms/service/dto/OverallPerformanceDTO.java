package fis.pms.service.dto;

import fis.pms.repository.dto.PerformanceDTO;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class OverallPerformanceDTO {

    private Map<String, PerformanceDTO> data = new HashMap<>();

    public static List<PerformanceDTO> createOverall(List<PerformanceDTO> dto) {
        OverallPerformanceDTO overall = new OverallPerformanceDTO();
        dto.stream().forEach(performanceDTO -> {
            overall.data.put(performanceDTO.getName(), performanceDTO);
        });
        return overall.data.values().stream().collect(Collectors.toList());
    }
}