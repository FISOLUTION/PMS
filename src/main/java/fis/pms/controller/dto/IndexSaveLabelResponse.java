package fis.pms.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/*
 * 작성자: 한명수
 * 작성날짜: 2021/08/14
 * 작성내용: IndexSaveLabelResponse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndexSaveLabelResponse {
    private List<Long> v_id;        // list로 반환
    private Long f_id;              // 저장된 철 id 반환	//성공
    private String err_code;		//실패
}
