package fis.pms.controller;

import fis.pms.configuator.argumentResolver.Login;
import fis.pms.controller.dto.ImagesMaxnumResponse;
import fis.pms.controller.dto.SaveImageRequest;
import fis.pms.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;


@Slf4j
@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    // 원본 이미지 upload
    @PostMapping("/images/origin")
    public Long saveOriginImage(@ModelAttribute SaveImageRequest request, @Login Long workerId) throws IOException {
        Long imageCnt = imageService.storeOriginImages(request, workerId);
        return imageCnt;
    }

    @PostMapping("/images/modify")
    public Long saveModifyImage(@ModelAttribute SaveImageRequest request, @Login Long workerId) throws IOException {
        Long imageCnt = imageService.storeModifyImages(request, workerId);
        return imageCnt;
    }

    // 이미지 download
    @GetMapping("/images/{state}/{fileId}/{imageNum}")
    public Resource downloadImage(@PathVariable String state, @PathVariable Long fileId, @PathVariable String imageNum) throws MalformedURLException {
        if(state.equals("origin")){
            return new UrlResource("file:" + imageService.getFullPath(fileId, "origin") + imageNum);
        } else if (state.equals("modify")) {
            return new UrlResource("file:" + imageService.getFullPath(fileId, "modify") + imageNum);
        }
        return null;
    }

    // 저장된 이미지의 갯수 반환
    @GetMapping("/images/maxnum")
    public ImagesMaxnumResponse imagesMaxNum(){
        return imageService.imagesMaxNum();
    }

}
