package fis.pms.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Volume {
    @Id
    @GeneratedValue
    @Column(name = "v_id")
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "f_id")
    private Files files;

    @Column(length = 10)
    private String v_casecount;

    @Column(length = 10)
    private String v_casenum;

    private Integer v_num;

    @Column(length = 3)
    private String v_inhernum;

    @OneToMany(mappedBy = "volume", cascade = CascadeType.REMOVE)
    private List<Cases> caseList = new ArrayList<>();

    private String v_pageSaved;

    /*
     * 작성자: 한명수
     * 작성날짜: 2021/08/30
     * 작성내용: 생성자
     */

    private Volume(Files files) {
        this.files = files;
    }


    /*
     * 작성자: 한명수
     * 작성날짜: 2021/08/26
     * 작성내용: 연관관계 메서드 setFiles
     */

    //====연관관계 메서드====//
    public void setLinkFiles(Files files) {
        this.files = files;
        files.getVolumes().add(this);
    }


    /*
     * 작성자: 한명수
     * 작성날짜: 2021/08/30
     * 작성내용: 생성 메서드
     */

    //=====생성메서드=====//
    public static Volume createVolume(Files files, int v_num) {
        Volume volume = new Volume(files);
        volume.v_num = v_num;
        volume.v_pageSaved = "0";
        return volume;
    }

    /*
     * 작성자: 한명수
     * 작성날짜: 2021/08/30
     * 작성내용: updateCaseCount
     */
    //=====수정 메서드=====//

    public Volume resetCaseCount(int caseNum) {
        String v_casecount = Integer.toString(caseNum);
        this.v_casecount = v_casecount;
        this.v_casenum = v_casecount;
        return this;
    }


    /*
     * 작성자: 한명수
     * 작성날짜: 2021/09/01
     * 작성내용: reduceCaseCount
     */

    public void reduceCaseCount() {
        this.v_casecount = Integer.toString(Integer.valueOf(this.v_casecount) - 1);
    }

    /*
     * 작성자: 한명수
     * 작성날짜: 2021/09/01
     * 작성내용: resetCount
     */

    public void resetCount() {
        this.v_casecount = this.v_casenum;
    }

    public void updateCaseCount(List<Cases> caseList) {
        int caseCount = 0;
        for (Cases cases : caseList) {
            if (cases.getC_first().equals("1")) {
                caseCount++;
            }
        }
        this.v_casenum = Integer.toString(caseList.size());
        this.v_casecount = Integer.toString(caseCount);
    }

    public void updatePageSaved() {
        this.v_pageSaved = "1";
    }
}
