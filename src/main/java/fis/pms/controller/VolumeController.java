package fis.pms.controller;

import fis.pms.configuator.argumentResolver.Login;
import fis.pms.controller.dto.IndexSaveVolumeRequest;
import fis.pms.controller.dto.IndexSaveVolumeResponse;
import fis.pms.controller.dto.VolumesInfo;
import fis.pms.domain.fileEnum.F_process;
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

    @GetMapping("/volume/index/input")
    public List<VolumesInfo> getInputVolumesInfo(@RequestParam Long f_id) {
        return volumeService.getInputVolumesInfo(f_id);
    }

    @GetMapping("/volume/index/check")
    public List<VolumesInfo> getCheckVolumesInfo(@RequestParam Long f_id) {
        return volumeService.getCheckVolumesInfo(f_id);
    }

    @PostMapping("/volume/index/input")
    public IndexSaveVolumeResponse indexSaveVolumeResponse(@RequestBody IndexSaveVolumeRequest indexSaveVolumeRequest, @Login Long workerId) {
        return volumeService.saveCasesPages(indexSaveVolumeRequest, workerId, F_process.INPUT);
    }

    @PostMapping("/volume/index/check")
    public IndexSaveVolumeResponse indexCheckVolumeResponse(@RequestBody IndexSaveVolumeRequest indexSaveVolumeRequest, @Login Long workerId) {
        return volumeService.saveCasesPages(indexSaveVolumeRequest, workerId, F_process.CHECK);
    }
}
