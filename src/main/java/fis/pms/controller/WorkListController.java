package fis.pms.controller;

import fis.pms.domain.WorkPlan;
import fis.pms.service.WorkListService;
import fis.pms.service.WorkPlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/prepare")
    public void preparePlanResult(){
        WorkPlan workPlan = workPlanService.findOne();
        workListService.getOverallPerformance();

    }

}
