package fis.pms.domain;

import fis.pms.exception.OfficeException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
public class Office {
    @Id
    @Column(length = 7, name = "o_code")
    private String o_code;

    @Column(name = "o_name", length = 100)
    private String o_name;

    @Column(name = "o_del", length = 1)
    private String o_del;

//    @OneToMany(mappedBy = "office")
//    private List<Box> boxList;

    @OneToMany(mappedBy = "office")
    private List<Files> fileList;

//    public Office(OfficeExcel o) {
//        this.o_code = o.getO_code();
//        this.o_name = o.getO_name();
//        this.o_del = o.getO_del();
//        fileList = null;
//    }

    public void updateOffice(String o_code, String o_name, String o_del) {
        this.o_code = o_code;
        this.o_name = o_name;
        this.o_del = o_del;
    }

    public boolean checkName(String name) {
        if(!o_name.equals(name)) return false;
        else return true;
    }
}
