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
    private Long f_id;              // 저장된 철 id 반환
    private boolean complete;		// 색인 입력 or 검수 작업 완료 여부

    public static IndexSaveLabelResponse createIndexSaveLabelResponse(List<Long> v_id, Long f_id) {
        IndexSaveLabelResponse indexSaveLabelResponse = new IndexSaveLabelResponse();
        indexSaveLabelResponse.v_id = v_id;
        indexSaveLabelResponse.f_id = f_id;
        return indexSaveLabelResponse;
    }
}
