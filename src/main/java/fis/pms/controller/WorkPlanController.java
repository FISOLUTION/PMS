package fis.pms.controller;

import fis.pms.domain.WorkPlan;
import fis.pms.service.WorkPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WorkPlanController {

    private final WorkPlanService workPlanService;

    @PostMapping("workplan")
    public WorkPlan save(@RequestBody WorkPlan workPlan){
        return workPlanService.save(workPlan);
    }

    @GetMapping("workplan")
    public WorkPlan get(){
        return workPlanService.findOne();
    }
}
