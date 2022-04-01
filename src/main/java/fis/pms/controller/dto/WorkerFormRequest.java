package fis.pms.controller.dto;

import fis.pms.domain.Authority;
import fis.pms.domain.Worker;
import lombok.Data;

@Data
public class WorkerFormRequest {
    private Authority w_authority;      // 작업자 권한 (WORKER, ADMIN)
    private String w_name;              // 작업자 이름
    private String w_tel;               // 작업자 전화번호
    private String w_address;           // 작업자 주소
    private String nickname;
    private String password;

    public Worker createWorker(){
        return Worker.builder()
                .authority(w_authority)
                .nickname(nickname)
                .w_name(w_name)
                .password(password)
                .w_address(w_address)
                .w_tel(w_tel)
                .build();
    }
}
