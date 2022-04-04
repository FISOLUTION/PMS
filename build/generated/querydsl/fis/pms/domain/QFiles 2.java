package fis.pms.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFiles is a Querydsl query type for Files
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFiles extends EntityPathBase<Files> {

    private static final long serialVersionUID = 543879075L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFiles files = new QFiles("files");

    public final StringPath b_num = createString("b_num");

    public final ListPath<Cases, QCases> cases = this.<Cases, QCases>createList("cases", Cases.class, QCases.class, PathInits.DIRECT2);

    public final StringPath f_check = createString("f_check");

    public final StringPath f_complete = createString("f_complete");

    public final EnumPath<fis.pms.domain.fileEnum.F_construct> f_db = createEnum("f_db", fis.pms.domain.fileEnum.F_construct.class);

    public final StringPath f_efilenum = createString("f_efilenum");

    public final StringPath f_eyear = createString("f_eyear");

    public final NumberPath<Long> f_id = createNumber("f_id", Long.class);

    public final EnumPath<fis.pms.domain.fileEnum.F_inheritance> f_inheritance = createEnum("f_inheritance", fis.pms.domain.fileEnum.F_inheritance.class);

    public final StringPath f_inherlabelcode = createString("f_inherlabelcode");

    public final StringPath f_inheroffice = createString("f_inheroffice");

    public final StringPath f_inherpyear = createString("f_inherpyear");

    public final StringPath f_inherunitcode = createString("f_inherunitcode");

    public final EnumPath<fis.pms.domain.fileEnum.F_kmethod> f_kmethod = createEnum("f_kmethod", fis.pms.domain.fileEnum.F_kmethod.class);

    public final EnumPath<fis.pms.domain.fileEnum.F_kperiod> f_kperiod = createEnum("f_kperiod", fis.pms.domain.fileEnum.F_kperiod.class);

    public final EnumPath<fis.pms.domain.fileEnum.F_kplace> f_kplace = createEnum("f_kplace", fis.pms.domain.fileEnum.F_kplace.class);

    public final StringPath f_labelcode = createString("f_labelcode");

    public final QF_location f_location;

    public final StringPath f_manager = createString("f_manager");

    public final EnumPath<fis.pms.domain.fileEnum.F_modify> f_modify = createEnum("f_modify", fis.pms.domain.fileEnum.F_modify.class);

    public final StringPath f_name = createString("f_name");

    public final EnumPath<fis.pms.domain.fileEnum.F_newold> f_newold = createEnum("f_newold", fis.pms.domain.fileEnum.F_newold.class);

    public final StringPath f_oldfileclassifynum = createString("f_oldfileclassifynum");

    public final StringPath f_page = createString("f_page");

    public final StringPath f_placeenddate = createString("f_placeenddate");

    public final StringPath f_placereason = createString("f_placereason");

    public final EnumPath<fis.pms.domain.fileEnum.F_process> f_process = createEnum("f_process", fis.pms.domain.fileEnum.F_process.class);

    public final StringPath f_pyear = createString("f_pyear");

    public final StringPath f_regnum = createString("f_regnum");

    public final EnumPath<fis.pms.domain.fileEnum.F_construct> f_scan = createEnum("f_scan", fis.pms.domain.fileEnum.F_construct.class);

    public final StringPath f_smallfunc = createString("f_smallfunc");

    public final StringPath f_subname = createString("f_subname");

    public final StringPath f_summary = createString("f_summary");

    public final StringPath f_syear = createString("f_syear");

    public final EnumPath<fis.pms.domain.fileEnum.F_type> f_type = createEnum("f_type", fis.pms.domain.fileEnum.F_type.class);

    public final StringPath f_typenum = createString("f_typenum");

    public final StringPath f_unitcode = createString("f_unitcode");

    public final StringPath f_upload = createString("f_upload");

    public final StringPath f_volumeamount = createString("f_volumeamount");

    public final StringPath f_volumecount = createString("f_volumecount");

    public final StringPath f_volumeSaved = createString("f_volumeSaved");

    public final NumberPath<Long> images = createNumber("images", Long.class);

    public final QOffice office;

    public final ListPath<Special, QSpecial> specials = this.<Special, QSpecial>createList("specials", Special.class, QSpecial.class, PathInits.DIRECT2);

    public final ListPath<Volume, QVolume> volumes = this.<Volume, QVolume>createList("volumes", Volume.class, QVolume.class, PathInits.DIRECT2);

    public final ListPath<WorkList, QWorkList> workList = this.<WorkList, QWorkList>createList("workList", WorkList.class, QWorkList.class, PathInits.DIRECT2);

    public QFiles(String variable) {
        this(Files.class, forVariable(variable), INITS);
    }

    public QFiles(Path<? extends Files> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFiles(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFiles(PathMetadata metadata, PathInits inits) {
        this(Files.class, metadata, inits);
    }

    public QFiles(Class<? extends Files> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.f_location = inits.isInitialized("f_location") ? new QF_location(forProperty("f_location")) : null;
        this.office = inits.isInitialized("office") ? new QOffice(forProperty("office")) : null;
    }

}

