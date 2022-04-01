package fis.pms.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Special {
    @Id @GeneratedValue
    @Column(name = "s_id")
    private Long Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_id")
    private Files files;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "c_id")
    private Cases case1;

    @Column(length = 1)
    private String s_filecase;

    @Column(length = 3)
    private String s_num;

    @Column(length = 100)
    private String s_list1;

    @Column(length = 100)
    private String s_list2;

    @Column(length = 100)
    private String s_list3;

    /*
     * 작성자: 한명수
     * 작성날짜: 2021/08/26
     * 작성내용: 연관관계 메서드 setFiles, setCases
     */

    //====연관관계 메서드====//
    public void setFiles(Files files) {
        this.files = files;
        files.getSpecials().add(this);
    }

    public void setCases(Cases cases) {
        this.case1 = cases;
        cases.getSpecials().add(this);
    }
}
