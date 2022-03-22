package fis.pms.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 작성자: 고준영
 * 작성날짜: 2021/08/24
 * 작성내용: Search Office Response
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchOfficeResponse {

    private String code;  // 기관코드
    private String name;  // 기관명
    private String o_del; // 기관 폐지 여부(?)

    private String err_code; //실패시, 에러코드

}
