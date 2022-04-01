package fis.pms.controller.dto;

import fis.pms.domain.Files;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchScanResponse {

    private String f_labelcode;     // 레이블
    private String f_name;          // 철이름
    private String b_num;           // 박스번호
    private String o_code;          // 기관코드
    private String o_name;          // 기관명

    public static SearchScanResponse createSearchScanResponse(Files file) {
        SearchScanResponse searchScanResponse = new SearchScanResponse();
        searchScanResponse.f_labelcode=file.getF_labelcode();
        searchScanResponse.f_name= file.getF_name();
        searchScanResponse.b_num= file.getB_num();
        searchScanResponse.o_code=file.getOffice().getO_code();
        searchScanResponse.o_name=file.getOffice().getO_name();
        return searchScanResponse;
    }
}
