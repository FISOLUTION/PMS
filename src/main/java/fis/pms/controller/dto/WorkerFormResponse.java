package fis.pms.controller.dto;

import fis.pms.domain.Authority;
import fis.pms.domain.Worker;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WorkerFormResponse {
    private Long id;
    private String w_name;              // 작업자 이름
    private String w_nickname;

    public static WorkerFormResponse createResponse(Worker worker){
        return new WorkerFormResponse(worker.getId(), worker.getW_name(), worker.getNickname());
    }
}
