package fis.pms.service;

import fis.pms.domain.Files;
import fis.pms.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public void mkdir(Files files) {

    }
}
