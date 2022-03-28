package fis.pms.repository;

import fis.pms.domain.Office;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

/*
* 작성자 : 현승구
* 작성일자 : 2021/08/23
* 작성내용 : save, findOne, remove
*/

@Repository
@RequiredArgsConstructor
public class OfficeRepository {
    //스프링 빈 자동 등록
    private final EntityManager em;

    public String save(Office office){
        em.persist(office);
        return office.getO_code();
    }

    public Optional<Office> findOne(String code){
        return Optional.ofNullable(em.find(Office.class, code));
    }

    public String remove(Office office){
        String code = office.getO_code();
        em.remove(office);
        return code;
    }

    public List<Office> findAll() {
        return em.createQuery("select o from Office o", Office.class)
                .getResultList();
    }

    public List<Office> findByName(String name) {
        return em.createQuery("select o from Office o where o.o_name like : name", Office.class)
                .setParameter("name", "%"+name+"%")
                .getResultList();
    }

    public List<Office> findByCode(String code) {
        return em.createQuery("select o from Office o where o.o_code like :code", Office.class)
                .setParameter("code", "%"+code+"%")
                .getResultList();
    }

}



