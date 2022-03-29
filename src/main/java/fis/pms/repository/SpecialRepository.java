package fis.pms.repository;

import fis.pms.domain.Special;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

/**
 * 작성자: 고준영
 * 작성날짜: 2021/08/23
 * 작성내용: save, findOne, remove
 */

@Repository
@RequiredArgsConstructor
public class SpecialRepository {

    private final EntityManager em;

    public Long save(Special special) {
        em.persist(special);
        return special.getId();
    }

    public Special findOne(Long id) {
        return em.find(Special.class, id);
    }

    public Long remove(Special special) {
        Long id = special.getId();
        em.remove(special);
        return id;
    }
}
