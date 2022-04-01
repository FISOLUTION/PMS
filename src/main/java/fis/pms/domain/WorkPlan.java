package fis.pms.domain;

import fis.pms.service.dto.PreparePlanDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Map;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class WorkPlan {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "pre")
    private Long preInfo;
    @Column(name = "ex")
    private Long export;
    @Column(name = "sc")
    private Long scan;
    private Long imgModify;
    @Column(name = "inp")
    private Long input;
    @Column(name = "ch")
    private Long check;
    @Column(name = "up")
    private Long upload;

    public void putInto(Map<String, PreparePlanDTO> map) {
        map.get("사전조사").putPlan(preInfo).calculateRate();
        map.get("문서반출").putPlan(export).calculateRate();
        map.get("스캔작업").putPlan(scan).calculateRate();
        map.get("이미지보정").putPlan(imgModify).calculateRate();
        map.get("색인입력").putPlan(input).calculateRate();
        map.get("색인검수").putPlan(check).calculateRate();
        map.get("업로드").putPlan(upload).calculateRate();
    }
}
