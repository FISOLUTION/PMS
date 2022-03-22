package fis.pms.domain;

import fis.pms.domain.fileEnum.F_process;
import fis.pms.domain.fileEnum.F_processConverter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Process {

    @Id
    String id;

    @Convert(converter= F_processConverter.class)
    F_process f_process;

    public Process(F_process f_process) {
        this.f_process = f_process;
    }
}
