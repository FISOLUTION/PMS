package fis.pms.controller.dto.filedto;

import lombok.Data;

@Data
public class UploadCreateFileResponse {
    //오류있는 f_id 목록만 반환.
    private Long[] f_id;
}
