package fis.pms.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindIndexPreinfo {
    private String o_code;
    private String b_num;
    private String f_labelcode;

    public static FindIndexPreinfo createFindIndexPreinfo(String o_code, String b_num, String f_labelcode) {
        FindIndexPreinfo findIndexPreinfo = new FindIndexPreinfo();
        findIndexPreinfo.o_code = o_code;
        findIndexPreinfo.b_num = b_num;
        findIndexPreinfo.f_labelcode = f_labelcode;
        return findIndexPreinfo;
    }
}
