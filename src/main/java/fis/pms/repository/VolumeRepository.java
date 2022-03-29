package fis.pms.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import fis.pms.controller.dto.QVolumesInfo;
import fis.pms.controller.dto.VolumesInfo;
import fis.pms.domain.Files;
import fis.pms.domain.QFiles;
import fis.pms.domain.QVolume;
import fis.pms.domain.Volume;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static fis.pms.domain.QFiles.files;
import static fis.pms.domain.QVolume.volume;

/*
 * 작성자 : 현승구
 * 작성일자 : 2021/08/23
 * 작성내용 : save, findOne, remove
 */

@Repository
@RequiredArgsConstructor
public class VolumeRepository {
    //스프링 빈 자동 등록
    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;

    public Long save(Volume volume){
        em.persist(volume);
        return volume.getId();
    }

    public Volume findOne(Long id){
        return em.find(Volume.class, id);
    }

    public Long remove(Volume volume){
        Long Id = volume.getId();
        em.remove(volume);
        return Id;
    }

    /*
     * 작성자: 한명수
     * 작성날짜: 2021/08/30
     * 작성내용: findByFiles
     */
    public List<Volume> findByFiles(Files files){
        return jpaQueryFactory
                .selectFrom(volume)
                .where(volume.files.eq(files))
                .orderBy(volume.v_num.asc())
                .fetch();
    }

    // 2022-03-03 이승범 : 파일 권호수 수정시 줄어든 권호수만큼 volume삭제
    public void deleteByVolumeAmountOfFiles(Files files, int volumeAmount){
        em.createQuery("delete " +
                        "from Volume v " +
                        "where v.files = :files " +
                        "and v.v_num > :volumeAmount")
                .setParameter("volumeAmount", volumeAmount)
                .setParameter("files", files)
                .executeUpdate();
        em.flush();
        em.clear();
    }

    public List<VolumesInfo> findByFilesWithFiles(Long filesId) {
        return jpaQueryFactory
                .select(new QVolumesInfo(volume.id, files.f_labelcode, volume.v_num, files.f_name, files.f_pyear, files.f_eyear, files.f_kperiod))
                .from(volume)
                .join(volume.files, files)
                .fetch();
    }
}