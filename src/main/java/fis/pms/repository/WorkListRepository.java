package fis.pms.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import fis.pms.domain.QProcess;
import fis.pms.domain.QWorkList;
import fis.pms.repository.dto.PerformanceDTO;
import fis.pms.repository.dto.WorkListGroupByDateDTO;
import fis.pms.repository.querymethod.WorkListQueryMethod;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import fis.pms.domain.WorkList;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static fis.pms.domain.QFiles.files;
import static fis.pms.domain.QProcess.process;
import static fis.pms.domain.QWorkList.workList;

@Repository
@RequiredArgsConstructor
@Slf4j
public class WorkListRepository extends WorkListQueryMethod {

    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;

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

    public List<WorkListGroupByDateDTO> findWorkListByDate(LocalDate startDate, LocalDate endDate) {
        return jpaQueryFactory
                .select(Projections.constructor(WorkListGroupByDateDTO.class, process.id, workList.f_process, workList.date, workList.count()))
                .from(workList)
                .leftJoin(process)
                .on(process.f_process.eq(workList.f_process))
                .where(dateGOE(startDate), dateLOE(startDate))
                .groupBy(workList.date, workList.f_process)
                .fetch();
    }
}
