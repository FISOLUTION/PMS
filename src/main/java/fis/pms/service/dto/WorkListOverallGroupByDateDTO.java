package fis.pms.service.dto;

import fis.pms.exception.WorkListException;
import fis.pms.repository.dto.WorkListGroupByDateDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Arrays;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkListOverallGroupByDateDTO {

    private LocalDate date;
    private Long preInfo;
    private Long export;
    private Long scan;
    private Long imgModify;
    private Long input;
    private Long check;
    private Long upload;

    /**
     * @implNote 날짜별, 공정별로 받아온 자료를 다시 날짜별로 종합하기 위해 WorkListOverallGroupByDTO 를 사용합니다 </p>
     * data.name 에 있는 값으로 어떤 공정의 내용인지 알고 필드 매핑합니다
     * @param data Repository의 dto
     * @author 현승구
     */
    public void matchPerformanceProcess(WorkListGroupByDateDTO data){
        date = data.getDate();
        String processName = data.getName();
        if(processName.equals("사전조사")){
            preInfo = data.getCount();
        } else if(processName.equals("문서반출")){
            export = data.getCount();
        } else if(processName.equals("스캔작업")){
            scan = data.getCount();
        } else if(processName.equals("이미지보정")){
            imgModify = data.getCount();
        } else if(processName.equals("색인입력")){
            input = data.getCount();
        } else if(processName.equals("색인검수")){
            check = data.getCount();
        } else if(processName.equals("업로드")){
            upload = data.getCount();
        }
    }

    public void makeNullCountToZero(){
        Field[] fields = WorkListOverallGroupByDateDTO.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Long value = (Long) field.get(this);
                if(value == null){
                    field.set(this, 0L);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new WorkListException("날짜별 공정 현황 데이터 수집중 내부 오류 발생");
            }
        }
    }
}
