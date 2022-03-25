package fis.pms.service;

import fis.pms.controller.dto.ImagesMaxnumResponse;
import fis.pms.controller.dto.SaveImageRequest;
import fis.pms.domain.Files;
import fis.pms.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
*   작성날짜: 2022/03/25 2:11 PM
*   작성자: 이승범
*   작성내용: 이미지 관련 로직을 위한 service
*/
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ImageService {

    private final FileRepository fileRepository;

    @Value("${image.path.origin}")
    private String originPath;

    @Value("${image.path.modify}")
    private String modifyPath;

    public String getFullPath(Long fileId, String state) {
        if (state.equals("origin")) {
            return originPath + fileId + '/';
        }
        return modifyPath + fileId + '/';
    }

    @Transactional
    public Long storeImages(SaveImageRequest request, String state) throws IOException {

        // 등록된 철인지 검사
        Files findFile = fileRepository.findOne(request.getFileId());
        if (findFile == null)
            return null;

        // 철의 이미지들이 저장될 디렉토리 생성(overwrite)
        String path = getFullPath(request.getFileId(), state);
        mkdir(path);

        // request에 담겨있는 이미지들을 생성한 디렉토리에 저장
        long imageCnt = 0;
        for (MultipartFile multipartFile : request.getImages()) {
            if (!multipartFile.isEmpty()) {
                // 이미지 저장
                multipartFile.transferTo(new File(path + (++imageCnt)));
            }
        }
        findFile.imageUpload(imageCnt, state);

        // 철의 이미지 갯수 반환
        return imageCnt;
    }

    private void mkdir(String path) {
        File directory = new File(path);
        while (directory.exists()) {
            File[] subFiles = directory.listFiles();
            for (int i = 0; i < subFiles.length; i++) {
                subFiles[i].delete();
            }
            System.out.println(directory.length());
            if (subFiles.length == 0) {
                directory.delete();
            }
        }
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    public ImagesMaxnumResponse imagesMaxNum() {
        List<Files> filesList = fileRepository.findAllWithImages();
        List<ImagesMaxnumResponse.ImagesNum> collect = filesList.stream()
                .map(files -> new ImagesMaxnumResponse.ImagesNum(String.valueOf(files.getF_id()), files.getImages()))
                .collect(Collectors.toList());
        ImagesMaxnumResponse imagesMaxnumResponse = new ImagesMaxnumResponse(collect);
        return imagesMaxnumResponse;
    }

}
