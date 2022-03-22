package fis.pms.controller.file.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/*
 * 작성자: 원보라
 * 작성날짜: 2021/08/24
 * 작성내용: PreinfoFileDelResponse
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreinfoFileDelResponse {
    private List<Long> f_id = new ArrayList<>();          // 저장된 철 id 반환	//성공
    private String err_code;    //실패
}
