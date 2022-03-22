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

    public WorkList createWorkList(Files files, Worker worker, F_process f_process){
        WorkList workList = new WorkList();
        workList.files = files;
        workList.worker = worker;
        workList.f_process = f_process;
        workList.date = LocalDate.now();
        return workList;
    }
}
