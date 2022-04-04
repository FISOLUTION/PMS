package fis.pms.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import fis.pms.controller.dto.QVolumesInfo;
import fis.pms.controller.dto.VolumesInfo;
import fis.pms.domain.Files;
import fis.pms.domain.QFiles;
import fis.pms.domain.QVolume;
import fis.pms.domain.Volume;
import fis.pms.domain.fileEnum.F_process;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static fis.pms.domain.QFiles.files;
import static fis.pms.domain.QVolume.volume;

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

    public Optional<Volume> findOne(Long id){
        return Optional.ofNullable(em.find(Volume.class, id));
    }

    public Long remove(Volume volume){
        Long Id = volume.getId();
        em.remove(volume);
        return Id;
    }

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

    public List<VolumesInfo> findInputByFilesWithFiles(Long filesId) {
        return jpaQueryFactory
                .select(new QVolumesInfo(volume.id, files.f_labelcode, volume.v_num, files.f_name, files.f_pyear, files.f_eyear, files.f_kperiod))
                .from(volume)
                .join(volume.files, files)
                .where(files.f_id.eq(filesId), files.f_process.eq(F_process.IMGMODIFY))
                .fetch();
    }
    public List<VolumesInfo> findCheckByFilesWithFiles(Long filesId) {
        return jpaQueryFactory
                .select(new QVolumesInfo(volume.id, files.f_labelcode, volume.v_num, files.f_name, files.f_pyear, files.f_eyear, files.f_kperiod))
                .from(volume)
                .join(volume.files, files)
                .where(files.f_id.eq(filesId), files.f_process.eq(F_process.INPUT))
                .fetch();
    }
}