package fis.pms.service;

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
import java.util.ArrayList;
import java.util.List;

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

    public List<Files> searchFilesByScan(){
        checkHaveOriginImages();
        return fileRepository.findByScanWithOffice();
    }

    public void checkHaveOriginImages() {
        List<Files> uncheckedFileList = fileRepository.findByUnchecked();
        List<Long> willCheckFileList = new ArrayList<>();
        uncheckedFileList.forEach(file->{
            if(checkHaveImage(getOriginFullPath(file))){
                willCheckFileList.add(file.getF_id());
            }
        });
        fileRepository.updateScan(willCheckFileList);
    }

    private Boolean checkHaveImage(String path) {
        File directory = new File(path);
        return directory.listFiles() != null;
    }

    public void delSubFiles(String path) {
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


    public String getModifyOfficePath(Files file) {
        return modifyPath + file.getOffice().getO_code() + '/';
    }

    public String getOriginOfficePath(Files file) {
        return originPath + file.getOffice().getO_code() + '/';
    }

    public String getModifyFullPath(Files file) {
        return modifyPath + file.getOffice().getO_code() + '/' + file.getF_labelcode() + '/';
    }

    public String getOriginFullPath(Files file) {
        return originPath + file.getOffice().getO_code() + '/' + file.getF_labelcode() + '/';
    }

}
