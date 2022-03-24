package fis.pms.repository;

import fis.pms.domain.WorkList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class WorkListRepository {

    private final EntityManager em;

    public WorkList save(WorkList workList) {
        em.persist(workList);
        return workList;
    }
}
