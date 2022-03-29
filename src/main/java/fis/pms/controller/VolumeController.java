package fis.pms.controller;

import fis.pms.controller.dto.IndexSaveVolumeRequest;
import fis.pms.controller.dto.IndexSaveVolumeResponse;
import fis.pms.controller.dto.VolumesInfo;
import fis.pms.service.VolumeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class VolumeController {

    private final VolumeService volumeService;

    @GetMapping("/volume/index")
    public List<VolumesInfo> getVolumesInfo(@RequestParam Long f_id){
        return volumeService.getVolumesInfo(f_id);
    }

    @PostMapping("/index/volume")
    public IndexSaveVolumeResponse indexSaveVolumeResponse(@RequestBody IndexSaveVolumeRequest indexSaveVolumeRequest) {
        return volumeService.saveCasesPages(indexSaveVolumeRequest);
    }
}
