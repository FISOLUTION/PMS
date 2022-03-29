package fis.pms.repository;

import fis.pms.repository.dto.PerformanceDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import fis.pms.domain.WorkList;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class WorkListRepository {

    private final EntityManager em;

    public List<PerformanceDTO> getPerformanceList() {
        return em.createQuery("select new fis.pms.repository.dto.PerformanceDTO(count(worklist), process.f_process, process.id) " +
                "from Process process " +
                "left join WorkList worklist on process.f_process = worklist.f_process " +
                "group by process.f_process", PerformanceDTO.class)
                .getResultList();
    }

    public WorkList save(WorkList workList) {
        em.persist(workList);
        return workList;
    }

    public List<WorkList> findAll(){
        return em.createQuery("select wl from WorkList wl", WorkList.class)
                .getResultList();
    }
}
