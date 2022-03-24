package fis.pms.repository;

import fis.pms.domain.Office;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

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

    public Office findOne(String code){
        return em.find(Office.class, code);
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


    /**
     * 작성자: 고준영
     * 작성날짜: 2021/08/25
     * 작성내용: findRecordByLabel 철별이력조회
     */
    public List<Office> findRecordByLabel() {
        return em.createQuery("select o from Office o join fetch o.fileList f join fetch f.cases", Office.class)
                .getResultList();
    }

    /**
     * 작성자: 고준영
     * 작성날짜: 2021/09/02
     * 작성내용: find Office By Code, Name
     */
    public List<Office> findByCode(String code) {
        return em.createQuery("select o from Office o where o.o_code like :code", Office.class)
                .setParameter("code", "%"+code+"%")
                .getResultList();
    }
    public List<Office> findByName(String name) {
        return em.createQuery("select o from Office o where o.o_name like : name", Office.class)
                .setParameter("name", "%"+name+"%")
                .getResultList();
    }

    /**
     * 작성자: 고준영
     * 작성날짜: 2021/10/07 ~~~
     * 작성내용: plan page의 등록된 과 리스트 항목에 해당하는 값을 찾기 위해
     */
    public List<Office> findAllwithFile() {
        return em.createQuery("select o from Office o join fetch o.fileList f", Office.class)
                .getResultList();
//        where file.office = 1번;
//        sum
//        for List<Files> . map(o -> o.getF_volumeamount)
    }
}



