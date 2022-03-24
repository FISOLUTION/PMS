package fis.pms.domain;

import fis.pms.domain.fileEnum.F_process;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class WorkList {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Files files;

    @ManyToOne(fetch = FetchType.LAZY)
    private Worker worker;

    private F_process f_process;

    private LocalDate date;

    public static WorkList createWorkList(Files files, F_process f_process){
        WorkList workList = new WorkList();
        workList.f_process = f_process;
        workList.date = LocalDate.now();
        workList.mappingFile(files);
        return workList;
    }
    /**
    *   작성날짜: 2022/03/24 10:53 AM
    *   작성자: 이승범
    *   작성내용: 철과의 연관관계 매서드
    */
    public void mappingFile(Files files) {
        this.files = files;
        files.getWorkList().add(this);
    }
}
