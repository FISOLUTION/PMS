package fis.pms.service;

import fis.pms.controller.dto.ImagesMaxnumResponse;
import fis.pms.controller.dto.SaveImageRequest;
import fis.pms.domain.Files;
import fis.pms.domain.fileEnum.F_process;
import fis.pms.exception.FilesException;
import fis.pms.exception.ProcessOrderException;
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
 * 작성날짜: 2022/03/25 2:11 PM
 * 작성자: 이승범
 * 작성내용: 이미지 관련 로직을 위한 service
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ImageService {

    private final FileRepository fileRepository;
    private final WorkListService workListService;

    @Value("${image.path.origin}")
    private String originPath;

    @Value("${image.path.modify}")
    private String modifyPath;

    /**
    *   작성날짜: 2022/03/31 4:41 PM
    *   작성자: 이승범
    *   작성내용: 원본 이미지 저장
    */
    public Long storeOriginImages(SaveImageRequest request, Long workerId) throws IOException {

        Files findFile = fileRepository.findOne(request.getFileId())
                .orElseThrow(() -> new FilesException("존재하지 않는 파일 철입니다."));

        // export 전 단계에서는 scan 작업 불가능
        if (findFile.getF_process().getNext().compareTo(F_process.SCAN) < 0)
            throw new ProcessOrderException("아직 이전 단계의 공정이 끝나지 않았습니다.");

        // 철의 이미지들이 저장될 디렉토리 생성(overwrite)
        String path = getFullPath(findFile.getF_id(), "origin");
        mkdir(path);

        // request에 담겨있는 이미지들을 생성한 디렉토리에 저장
        long imageCnt = imgTransfer(request, path);

        workListService.reflectWorkList(findFile, workerId, F_process.SCAN);

        findFile.originImageUpload(imageCnt);

        // 철의 이미지 개수 반환
        return imageCnt;
    }


    /**
    *   작성날짜: 2022/03/31 4:42 PM
    *   작성자: 이승범
    *   작성내용: 보정된 이미지 저장
    */
    public Long storeModifyImages(SaveImageRequest request, Long workerId) throws IOException {

        Files findFile = fileRepository.findOne(request.getFileId())
                .orElseThrow(() -> new FilesException("존재하지 않는 파일 철입니다."));

        // scan 전 단계에서는 imgModify 작업 불가능
        if (findFile.getF_process().getNext().compareTo(F_process.IMGMODIFY) < 0)
            throw new ProcessOrderException("아직 이전 단계의 공정이 끝나지 않았습니다.");

        if (findFile.getImages() != request.getImages().size())
            throw new IllegalArgumentException("보정 이미지 개수가 원본 이미지 개수와 다름");

        // 철의 이미지들이 저장될 디렉토리 생성(overwrite)
        String path = getFullPath(findFile.getF_id(), "modify");
        mkdir(path);

        // request에 담겨있는 이미지들을 생성한 디렉토리에 저장
        long imageCnt = imgTransfer(request, path);

        workListService.reflectWorkList(findFile, workerId, F_process.IMGMODIFY);

        findFile.modifyImageUpload();

        // 철의 이미지 개수 반환
        return imageCnt;
    }

    private long imgTransfer(SaveImageRequest request, String path) throws IOException {
        long imageCnt = 0;
        for (MultipartFile multipartFile : request.getImages()) {
            if (!multipartFile.isEmpty()) {
                // 이미지 저장
                multipartFile.transferTo(new File(path + (++imageCnt)));
            }
        }
        return imageCnt;
    }

    public String getFullPath(Long fileId, String state) {
        if (state.equals("origin")) {
            return originPath + fileId + '/';
        } else if (state.equals("modify")) {
            return modifyPath + fileId + '/';
        }
        throw new IllegalArgumentException("origin or modify 필요");
    }

    private void mkdir(String path) {
        File directory = new File(path);
        while (directory.exists()) {
            File[] subFiles = directory.listFiles();
            for (int i = 0; i < subFiles.length; i++) {
                subFiles[i].delete();
            }
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
                .map(files -> new ImagesMaxnumResponse.ImagesNum(String.valueOf(files.getF_id()), files.getF_name(), files.getImages()))
                .collect(Collectors.toList());
        return new ImagesMaxnumResponse(collect);
    }

}
