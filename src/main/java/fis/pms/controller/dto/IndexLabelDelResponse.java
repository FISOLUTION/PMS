package fis.pms.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 작성자: 한명수
 * 작성날짜: 2021/08/24
 * 작성내용: IndexLabelDelResponse
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndexLabelDelResponse {
    private Long f_id;  // 철아이디	//삭제된 철 아이디
    private String err_code; 	//실패
}
