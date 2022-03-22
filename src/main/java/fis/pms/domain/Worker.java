package fis.pms.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/*
 * 수정자: 한명수
 * 수정날짜: 2021/08/26
 * 수정내용: @Setter 추가
 */
@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
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

    private String session_id;

    //작업자 주민등록 번호
    private String w_ssn;

    //작업자 주소
    private String w_address;

    //작업자 전화번호
    private String w_tel;

    //작업자 은행 이름
    private String w_bank;

    //작업자 계좌번호
    private String w_account;

    //작업자 일 시작 날짜
    private String w_sdate;

    private String w_edate;

    @OneToMany(mappedBy = "workerput")
    private List<Cases> caseList_put = new ArrayList<>();

    @OneToMany(mappedBy = "workercheck")
    private List<Cases> caseList_check = new ArrayList<>();

}
