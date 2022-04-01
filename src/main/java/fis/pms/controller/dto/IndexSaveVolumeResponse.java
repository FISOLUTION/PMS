package fis.pms.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/*
 * 작성자: 한명수
 * 작성날짜: 2021/08/24
 * 작성내용: IndexSaveVolumeResponse
 */

@Data
@AllArgsConstructor
public class IndexSaveVolumeResponse {
    private List<Long> c_id;        //생성된 건 리스트로 보내줌
    private boolean complete;        //색인 입력 or 검수 작업 완료 여부

    public IndexSaveVolumeResponse() {
        this.complete = false;
    }
}
