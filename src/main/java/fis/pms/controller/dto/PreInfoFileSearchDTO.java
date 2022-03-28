package fis.pms.controller.dto;


import fis.pms.domain.Office;
import lombok.Getter;
import lombok.Setter;

/*
 * 작성자: 원보라
 * 작성날짜: 2021/08/26
 * 작성내용: FindPreinfoBySearch
 */

@Getter
@Setter
public class PreInfoFileSearchDTO {
    private String o_code;
    private String f_labelcode;
    private String f_name;
    private String f_pyear;
    private String bNum;
}
