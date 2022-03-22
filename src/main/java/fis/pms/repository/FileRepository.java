/*
 * 작성자 : 현승구
 * 작성일자 : 2021/08/23
 * 작성내용 : save, findOne, remove
 */

package fis.pms.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import fis.pms.controller.dto.UploadSearchBoxRequest;
import fis.pms.domain.Files;
import fis.pms.domain.Office;
import fis.pms.domain.QFiles;
import fis.pms.domain.QOffice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static fis.pms.domain.QFiles.files;
import static fis.pms.domain.QOffice.office;

@Repository
@RequiredArgsConstructor
public class FileRepository extends FileQueryMethods {
    //스프링 빈 자동 등록
    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;

    public void save(Files files) {
        em.persist(files);
    }

    public Files findOne(Long id) {
        return em.find(Files.class, id);
    }

    public Long remove(Files files) {
        Long Id = files.getF_id();
        em.remove(files);
        return Id;
    }

    /**
     * 작성자: 고준영
     * 작성날짜: 2021/08/26
     * 작성내용: findRecordByLabel 철별이력조회
     */
    public List<Files> findRecordByLabel() {
        return em.createQuery("select f from Files f join fetch f.office o", Files.class)
                .getResultList();
    }

   // 2022-03-02 이승범 : fetchJoin를 이용한 쿼리 최적화
    public List<Files> findByOcodeBoxNumLabel(String o_code, String b_num, String f_labelcode){
        return jpaQueryFactory
                .selectFrom(files)
                .join(files.office, office).fetchJoin()
                .where(office.o_code.eq(o_code) ,fLabelCodeLike(f_labelcode), bNumLike(b_num))
                .fetch();
    }

    /*
     * 작성자: 한명수
     * 작성날짜: 2021/08/24
     * 작성내용: searchFilesByDetailInfo에 대한 query 작성
     */
    // 2022-03-04 이승범 : or 조건 연산자를 사용하려면 BooleanBuilder를 사용해야 한다.
    public List<Files> findByFnameFpyearFeyear(String f_name, String f_pyear, String f_eyear) {
        // JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if(f_name!=null){
            booleanBuilder.or(fNameLike(f_name));
        }
        if (f_pyear != null) {
            booleanBuilder.or(fPyearLike(f_pyear));
        }
        if (f_eyear != null) {
            booleanBuilder.or(fEyearLike(f_eyear));
        }
        return jpaQueryFactory
                .select(files)
                .from(files)
                .where(booleanBuilder)
                .fetch();
    }


    /*
     * 작성자: 원보라
     * 작성날짜: 2021/08/24
     * 작성내용: findAll,findByLabel
     */
    public List<Files> findAll() {
        return em.createQuery("select f from Files f", Files.class)
                .getResultList();
    }

    public List<Files> findAllWithImages(){
        return em.createQuery("select f from Files f where f.images is not null", Files.class)
                .getResultList();
    }

//    public List<Files> findByLabel(String f_labelcode) {
//        return em.createQuery("select f from Files f where f.f_labelcode = :f_labelcode", Files.class)
//                .setParameter("f_labelcode", f_labelcode)
//                .getResultList();
//    }

    public List<Files> findByLabel(String f_labelcode) {
        return jpaQueryFactory
                .selectFrom(files)
                .where(files.f_labelcode.eq(f_labelcode))
                .fetch();

    }

    /*
     * 작성자: 원보라
     * 작성날짜: 2021/08/26
     * 작성내용: findByOcodeLabelFnamePyear
     */

    public List<Files> findByOcodeLabelFnamePyear(Office office, String f_labelcode, String f_name, String f_pyear) {
        // JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
        return jpaQueryFactory
                .selectFrom(files)
                .where(o_codeEq(office), f_labelcodeEq(f_labelcode), f_nameEq(f_name), f_pyearEq(f_pyear))
                .fetch();
    }

    /*
     * 작성자: 원보라
     * 작성날짜: 2021/08/26
     * 작성내용: findByLabelRange
     */
    public List<Files> findByLabelRange(String first_label, String last_label) {
        // JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        return jpaQueryFactory
                .selectFrom(files)
                .where(first_labelGoe(first_label),
                        last_labelLoe(last_label),
                        files.f_exportdate.eq("none"))
                .fetch();
    }

    /*
     * 작성자: 원보라
     * 작성날짜: 2021/08/27
     * 작성내용: FindExportByDate
     */
    public List<Files> findByDateRange(String first_f_exportdate, String last_f_exportdate) {
        //JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        return jpaQueryFactory
                .selectFrom(files)
                .leftJoin(files.office, office).fetchJoin()
                .where(first_DateGoe(first_f_exportdate),
                        last_DateLoe(last_f_exportdate),
                        files.f_exportdate.ne("none"))
                .fetch();
    }

    /*
     * 작성자: 원보라
     * 작성날짜: 2021/08/27
     * 작성내용: findByBoxRange
     */
    public List<Files> findByBoxRange(String first_b_num, String last_b_num) {
        return jpaQueryFactory
                .selectFrom(files)
                .leftJoin(files.office, office).fetchJoin()
                .where(first_b_numGoe(first_b_num),
                        last_b_numLoe(last_b_num),
                        files.f_exportdate.ne("none"))
                .fetch();
    }

    /*
     * 작성자: 현승구
     * 작성날짜: 2021/08/27
     * 작성내용: 쿼리
     */
    public List<Files> findUploadSearchOnly(UploadSearchBoxRequest request) {
        // 업로드 포함 미검수 포함
        return jpaQueryFactory
                .selectFrom(files)
                .where(files.office.o_code.eq(request.getOffice_code()),
                        checkinclude(request.getCheck_include()),
                        uploadinclude(request.getCheck_include()),
                        first_b_numGoe(request.getS_box()),
                        last_b_numLoe(request.getE_box()))
                .limit(1000)
                .fetch();
    }

    /**
     * 작성자: 고준영
     * 작성날짜: 2021/10/13
     * 작성내용: 과별 공정 리스트
     */
    public List<Files> findAllWithOffice() {
        return em.createQuery("select f from Files f join fetch f.office o", Files.class)
                .getResultList();
    }

    public void resetAll() {
        em.createQuery("delete from Files")
                .executeUpdate();
    }
}
