package fis.pms.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import fis.pms.controller.dto.UploadSearchBoxRequest;
import fis.pms.domain.Files;
import fis.pms.domain.Office;
import fis.pms.domain.fileEnum.F_process;
import fis.pms.repository.dto.RegisterStatusDTO;
import fis.pms.repository.querymethod.FileQueryMethods;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static fis.pms.domain.QFiles.files;
import static fis.pms.domain.QOffice.office;
import static fis.pms.domain.QWorkList.workList;

@Repository
@RequiredArgsConstructor
public class FileRepository extends FileQueryMethods {

    //스프링 빈 자동 등록
    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;

    public Long save(Files files) {
        em.persist(files);
        return files.getF_id();
    }

    public Optional<Files> findOne(Long id) {
        return Optional.ofNullable(em.find(Files.class, id));
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
        return em.createQuery("select f from Files f join fetch f.office ", Files.class)
                .getResultList();
    }

    public List<Files> findAllWithImages(){
        return em.createQuery("select f from Files f where f.images is not null", Files.class)
                .getResultList();
    }


    public Optional<Files> findByLabel(String f_labelcode) {
        return Optional.ofNullable(jpaQueryFactory
                .selectFrom(files)
                .where(files.f_labelcode.eq(f_labelcode))
                .fetchOne());
    }

    /*
     * 작성자: 원보라
     * 작성날짜: 2021/08/26
     * 작성내용: findByOcodeLabelFnamePyear
     */

    public List<Files> preInfoSearch(Office office, String label, String name, String sYear, String bNum) {
        return jpaQueryFactory
                .selectFrom(files)
                .join(files.office)
                .fetchJoin()
                .where(o_codeEq(office), f_labelcodeEq(label), f_nameEq(name), f_pyearEq(sYear), bNumLike(bNum))
                .fetch();
    }

    /**
    *   작성날짜: 2022/03/23 12:13 PM
    *   작성자: 이승범
    *   작성내용: 레이블코드를 이용한 동적쿼리
    */
    public List<Files> findByLabelRange(String first_label, String last_label) {
        return jpaQueryFactory
                .selectFrom(files)
                .where(first_labelGoe(first_label),
                        last_labelLoe(last_label),
                        files.f_process.lt(F_process.EXPORT))
                .fetch();
    }

    /**
    *   작성날짜: 2022/03/23 5:28 PM
    *   작성자: 이승범
    *   작성내용: 날짜별 동적쿼리
    */
    public List<Files> findByDateRange(LocalDate sdate, LocalDate edate) {
        return jpaQueryFactory
                .selectFrom(files).distinct()
                .leftJoin(files.office, office).fetchJoin()
                .join(files.workList, workList)
                .where(first_DateGoe(sdate),
                        last_DateLoe(edate),
                        files.f_process.goe(F_process.EXPORT))
                .fetch();
    }

    /**
    *   작성날짜: 2022/03/23 5:29 PM
    *   작성자: 이승범
    *   작성내용: 박스번호 별 동적쿼리
    */
    public List<Files> findByBoxRange(String first_b_num, String last_b_num) {
        return jpaQueryFactory
                .selectFrom(files).distinct()
                .leftJoin(files.office, office).fetchJoin()
                .where(first_b_numGoe(first_b_num),
                        last_b_numLoe(last_b_num),
                        files.f_process.goe(F_process.EXPORT))
                .fetch();
    }

    public Files findOneWithOffice(Long fileId) {
        return em.createQuery("select f " +
                        "from Files f " +
                        "join fetch f.office " +
                        "where f.f_id=:fileId", Files.class)
                .setParameter("fileId", fileId)
                .getSingleResult();
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

    public List<Files> findByIdsWithOffice(List<Long> fileIds) {
        return em.createQuery("select f " +
                        "from Files f " +
                        "join fetch f.office " +
                        "where f.f_id = :fileIds", Files.class)
                .setParameter("fileIds", fileIds)
                .getResultList();
    }

    public List<Files> findByUnchecked() {
        return em.createQuery("select f " +
                        "from Files f " +
                        "where f.f_process=:export", Files.class)
                .setParameter("export", F_process.EXPORT)
                .getResultList();
    }

    public void updateScan(List<Long> willCheckFileIds) {
        em.createQuery("update Files f set f.f_process=:scan where f.f_id in :ids")
                .setParameter("ids", willCheckFileIds)
                .executeUpdate();
        em.flush();
        em.clear();
    }

    public List<Files> findByScanWithOffice() {
        return em.createQuery("select f " +
                        "from Files f " +
                        "join fetch f.office " +
                        "where f.f_process = :scan", Files.class)
                .setParameter("scan", F_process.SCAN)
                .getResultList();
    }

    public List<RegisterStatusDTO> findRegistStatus() {
        return em.createQuery("select new fis.pms.repository.dto.RegisterStatusDTO(file.office.o_code, file.office.o_name, count(file)) " +
                "from Files file join file.office " +
                "group by file.office.o_code", RegisterStatusDTO.class)
                .getResultList();
    }
}
