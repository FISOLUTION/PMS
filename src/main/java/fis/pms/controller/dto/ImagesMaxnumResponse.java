package fis.pms.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImagesMaxnumResponse {

    List<ImagesNum> imagesNumList;
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static public class ImagesNum{
        private String label;
        private Long maxnum;
    }
}
