package fis.pms.controller;

import fis.pms.controller.dto.Result;
import fis.pms.domain.WorkPlan;
import fis.pms.service.WorkListService;
import fis.pms.service.WorkPlanService;
import fis.pms.service.dto.OverallPerformanceDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Date;

@RestController
@RequiredArgsConstructor
@Slf4j
public class WorkListController {
    /*
        계획 대비 실적
            |- 계획 대비 실적
            |- 기관별로 등록철수
            |- 공정별로 완료된 철의 갯수

        기관별 공정
            |- 일일 작업 조회 ( 날짜 별로 실행한 공정 갯수 출력 )

        일일 작업
            |- 작업자 별 일일 작업량 조회

        철별 이력
            |- 레이블 별로 공정 상황 체크 ( 레이블번호 + 기관정보 + 공정 완료 작업이 언제 되었는지 )
     */

    private final WorkListService workListService;
    private final WorkPlanService workPlanService;

    @GetMapping("workList/prepare")
    public Result preparePlanResult() {
        WorkPlan workPlan = workPlanService.findOne();
        return new Result(workListService.prepareWithPlan(workPlan));
    }

    @GetMapping("/workList/overall")
    public OverallPerformanceDTO overall() {
        return workListService.getOverallPerformance();
    }

    @GetMapping("/workList")
    public void findWorkListByDate(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                   @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate){
        workListService.getWorkPerformancePeriod(startDate, endDate);
    }
}
