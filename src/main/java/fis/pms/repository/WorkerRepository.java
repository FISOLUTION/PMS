package fis.pms.repository;

import fis.pms.domain.Worker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class WorkerRepository {

    private final EntityManager em;

    public Optional<Worker> findByNickname(String nickname) {
        try {
            return Optional.ofNullable(em.createQuery("select worker from Worker worker where worker.nickname =:nickname", Worker.class)
                            .setParameter("nickname", nickname)
                            .getSingleResult());
        } catch (NoResultException noResultException) {
            return Optional.ofNullable(null);
        }
    }

    public Optional<Worker> findOne(Long id) {
        return Optional.ofNullable(em.find(Worker.class, id));
    }

    public void save(Worker worker) {
        em.persist(worker);
    }

    public Long remove(Long w_id) {
        em.createQuery("delete from Worker worker where worker.id =: w_id")
            .executeUpdate();
        return w_id;
    }

    public List<Worker> findAll() {
        return em.createQuery("select worker from Worker worker", Worker.class)
                .getResultList();
    }
}
