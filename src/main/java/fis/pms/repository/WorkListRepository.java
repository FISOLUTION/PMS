package fis.pms.repository;

import fis.pms.domain.WorkList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

/**
*   작성날짜: 2022/03/24 11:56 AM
*   작성자: 이승범
*   작성내용: workListRepository 생성
*/
@Repository
@RequiredArgsConstructor
public class WorkListRepository {

    private final EntityManager em;

    public WorkList save(WorkList workList) {
        em.persist(workList);
        return workList;
    }

    public List<WorkList> findAll(){
        return em.createQuery("select wl from WorkList wl", WorkList.class)
                .getResultList();
    }
}
