package fis.pms.controller.dto;

import fis.pms.domain.Authority;
import fis.pms.domain.WorkList;
import fis.pms.domain.Worker;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class WorkerSearchResponse {

    private Long id;
    private String nickname;
    private Authority authority;
    private String password;
    private String name;
    private String address;
    private String tel;

    public WorkerSearchResponse(Worker worker) {
        id = worker.getId();
        nickname = worker.getNickname();
        authority = worker.getAuthority();
        password = worker.getPassword();
        name = worker.getW_name();
        address = worker.getW_address();
        tel = worker.getW_tel();
    }
}
