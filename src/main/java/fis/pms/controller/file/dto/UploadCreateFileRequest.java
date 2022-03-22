package fis.pms.controller.file.dto;

import lombok.Data;

import java.util.List;

@Data
public class UploadCreateFileRequest {
    //java에서의 배열은 나중에 초기화가 가능하다.
    private List<Long> f_id;
}
