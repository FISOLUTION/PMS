package fis.pms.repository.search;
import lombok.Getter;
import lombok.Setter;


/*
 * 작성자: 원보라
 * 작성날짜: 2021/08/27
 * 작성내용: FindExportByDate
 */

@Getter
@Setter
public class FindExportByDate {
    private String first_date;
    private String last_date;
}
