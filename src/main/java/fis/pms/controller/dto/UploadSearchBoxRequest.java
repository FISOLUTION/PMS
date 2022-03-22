package fis.pms.controller.dto;

import lombok.Data;

@Data
public class UploadSearchBoxRequest {
    //검색 하고자하는 철의 기관 코드
    private String Office_code;
    //현재 레이블의 공정 상태
    private Boolean check_include;
    private Boolean upload_include;
    //박스 시작~끝 검색
    private String S_box;
    private String E_box;
}
