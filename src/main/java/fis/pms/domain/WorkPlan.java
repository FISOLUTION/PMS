package fis.pms.domain;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
public class WorkPlan {
    @Id
    @GeneratedValue
    private Long id;

    //사전 조사
    private Long p_search;
    // 문서 반출
    private Long p_export;
    // 스캔 작업
    private Long p_scan;
    // 이미지 보정
    private Long p_image;
    // 색인입력
    private Long p_index;
    // 색인보정
    private Long p_indexch;
    // 업로드
    private Long p_uploading;
}
