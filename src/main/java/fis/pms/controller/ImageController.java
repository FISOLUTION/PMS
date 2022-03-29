package fis.pms.controller;

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

    // 이미지 저장
    @PostMapping("/images/{state}")
    public Long saveImage(@ModelAttribute SaveImageRequest request, @PathVariable String state) throws IOException {
        Long imageCnt = imageService.storeImages(request, state);
        return imageCnt;
    }

    // 이미지 원본 download
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
