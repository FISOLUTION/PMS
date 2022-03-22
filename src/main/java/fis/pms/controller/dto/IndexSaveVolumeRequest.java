package fis.pms.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/*
* 작성자: 한명수
* 작성날짜: 2021/08/24
* 작성내용: IndexSaveVolumeRequest
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndexSaveVolumeRequest {
    private Long f_id; //철 아이디
    private Long v_id;

    private List<PageInfo> v_info; //철에 포함된 권당 건들의 쪽수 정보

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static public class PageInfo {
        private String startPage;
        private String endPage;
    }
}
