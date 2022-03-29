package fis.pms.service;

import fis.pms.domain.WorkPlan;
import fis.pms.exception.PlanException;
import fis.pms.repository.WorkPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class WorkPlanService {

    /**
     * @author 현승구
     * @implNote workPlan은 그냥 하나만 존재한다고 요구사항이 들어왔기 때문에 저장이 들어오게 되면 기존것들 삭제하고 다시 디비에 저장하는 식으로 작성
     */
    private final WorkPlanRepository workPlanRepository;

    public WorkPlan save(WorkPlan workPlan) {
        if(!isEmpty()){
            deleteAll();
        }
        return workPlanRepository.save(workPlan);
    }

    private void deleteAll() {
        workPlanRepository.deleteAll();
    }

    public List<WorkPlan> findAll(){
        return workPlanRepository.findAll();
    }

    public WorkPlan findOne(){
        if(isEmpty()) throw new PlanException("");
        else return findAll().get(0);
    }

    public boolean isEmpty(){
        if(findAll().isEmpty()) return true;
        else return false;
    }


}
