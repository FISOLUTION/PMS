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

    private Long preInfo;
    private Long export;
    private Long scan;
    private Long imgModify;
    @Column(name = "in")
    private Long input;
    @Column(name = "ch")
    private Long check;
    private Long upload;

    public void putInto(Map<String, PreparePlanDTO> map) {
        map.get("사전조사").setPlan(preInfo);
        map.get("문서반출").setPlan(export);
        map.get("스캔작업").setPlan(scan);
        map.get("이미지보정").setPlan(imgModify);
        map.get("색인입력").setPlan(input);
        map.get("색인검수").setPlan(check);
        map.get("업로드").setPlan(upload);
    }
}
