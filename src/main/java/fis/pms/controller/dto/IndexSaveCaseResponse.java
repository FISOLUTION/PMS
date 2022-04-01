package fis.pms.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class IndexSaveCaseResponse {
    private Long c_id;
    private boolean complete;   //색인 입력 or 검수 작업 완료 여부

    public IndexSaveCaseResponse() {
        this.complete = false;
    }
}
