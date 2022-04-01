package fis.pms.repository;

import fis.pms.domain.WorkPlan;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class WorkPlanRepository {

    private final EntityManager em;


    public WorkPlan save(WorkPlan workPlan) {
        em.persist(workPlan);
        return workPlan;
    }

    public List<WorkPlan> findAll() {
        return em.createQuery("select plan from WorkPlan plan" , WorkPlan.class)
                .getResultList();
    }

    public void deleteAll() {
        em.createQuery("delete from WorkPlan")
                .executeUpdate();
    }
}
