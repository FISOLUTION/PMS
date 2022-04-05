package fis.pms.domain;

import fis.pms.controller.dto.WorkerFormRequest;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Worker {
    @Id @GeneratedValue
    @Column(name = "w_id")
    private Long id;

    @OneToMany(mappedBy = "worker")
    private List<WorkList> workList = new ArrayList<>();

    private String nickname;

    //작업자 권한
    @Enumerated(EnumType.STRING) @Column(name = "w_authority")
    private Authority authority;

    //작업자 패스워드
    @Column(name = "w_pwd") @NotBlank
    private String password;

    //작업자 이름
    @NotBlank
    private String w_name;

    //작업자 주소
    private String w_address;

    //작업자 전화번호
    private String w_tel;

    @Builder
    public Worker(Long id, String nickname, Authority authority, String password, String w_name, String w_address, String w_tel) {
        this.id = id;
        this.nickname = nickname;
        this.authority = authority;
        this.password = password;
        this.w_name = w_name;
        this.w_address = w_address;
        this.w_tel = w_tel;
    }

    public void updateWorker(WorkerFormRequest request) {
        nickname = request.getNickname();
        authority = request.getW_authority();
        password = request.getPassword();
        w_name = request.getW_name();
        w_address = request.getW_address();
        w_tel = request.getW_tel();
    }
}
