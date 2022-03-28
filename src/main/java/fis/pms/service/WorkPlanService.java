package fis.pms.service;

import fis.pms.domain.WorkPlan;
import fis.pms.repository.WorkPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class WorkPlanService {

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
        if(isEmpty()) return null;
        else return findAll().get(0);
    }

    public boolean isEmpty(){
        if(findAll().isEmpty()) return true;
        else return false;
    }
}
