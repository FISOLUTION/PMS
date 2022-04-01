package fis.pms.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCases is a Querydsl query type for Cases
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCases extends EntityPathBase<Cases> {

    private static final long serialVersionUID = 540876911L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCases cases = new QCases("cases");

    public final StringPath c_approver = createString("c_approver");

    public final StringPath c_attachamount = createString("c_attachamount");

    public final StringPath c_attachetc = createString("c_attachetc");

    public final StringPath c_attachnum = createString("c_attachnum");

    public final EnumPath<fis.pms.domain.caseEnum.C_attachtype> c_attachtype = createEnum("c_attachtype", fis.pms.domain.caseEnum.C_attachtype.class);

    public final StringPath c_check = createString("c_check");

    public final StringPath c_class = createString("c_class");

    public final StringPath c_code = createString("c_code");

    public final EnumPath<fis.pms.domain.caseEnum.C_companion> c_companion = createEnum("c_companion", fis.pms.domain.caseEnum.C_companion.class);

    public final StringPath c_complete = createString("c_complete");

    public final StringPath c_departmentname = createString("c_departmentname");

    public final EnumPath<fis.pms.domain.caseEnum.C_detailtype> c_detailtype = createEnum("c_detailtype", fis.pms.domain.caseEnum.C_detailtype.class);

    public final StringPath c_distrinum = createString("c_distrinum");

    public final StringPath c_dodate = createString("c_dodate");

    public final StringPath c_drafter = createString("c_drafter");

    public final EnumPath<fis.pms.domain.caseEnum.C_edoc> c_edoc = createEnum("c_edoc", fis.pms.domain.caseEnum.C_edoc.class);

    public final StringPath c_epage = createString("c_epage");

    public final StringPath c_first = createString("c_first");

    public final EnumPath<fis.pms.domain.caseEnum.C_format> c_format = createEnum("c_format", fis.pms.domain.caseEnum.C_format.class);

    public final StringPath c_formatetc = createString("c_formatetc");

    public final StringPath c_groupnum = createString("c_groupnum");

    public final StringPath c_helper = createString("c_helper");

    public final StringPath c_hidden = createString("c_hidden");

    public final EnumPath<fis.pms.domain.caseEnum.C_kperiod> c_kperiod = createEnum("c_kperiod", fis.pms.domain.caseEnum.C_kperiod.class);

    public final StringPath c_lang = createString("c_lang");

    public final EnumPath<fis.pms.domain.caseEnum.C_modify> c_modify = createEnum("c_modify", fis.pms.domain.caseEnum.C_modify.class);

    public final EnumPath<fis.pms.domain.caseEnum.C_newold> c_newold = createEnum("c_newold", fis.pms.domain.caseEnum.C_newold.class);

    public final NumberPath<Integer> c_num = createNumber("c_num", Integer.class);

    public final StringPath c_oldnum = createString("c_oldnum");

    public final StringPath c_openable = createString("c_openable");

    public final StringPath c_opendate = createString("c_opendate");

    public final StringPath c_page = createString("c_page");

    public final StringPath c_pdate = createString("c_pdate");

    public final StringPath c_pnum = createString("c_pnum");

    public final StringPath c_pofficenum = createString("c_pofficenum");

    public final StringPath c_receiver = createString("c_receiver");

    public final StringPath c_reviewer = createString("c_reviewer");

    public final StringPath c_signdate = createString("c_signdate");

    public final StringPath c_spage = createString("c_spage");

    public final StringPath c_specialdoc = createString("c_specialdoc");

    public final EnumPath<fis.pms.domain.caseEnum.C_storage> c_storage = createEnum("c_storage", fis.pms.domain.caseEnum.C_storage.class);

    public final StringPath c_storageetc = createString("c_storageetc");

    public final StringPath c_subtitle = createString("c_subtitle");

    public final StringPath c_summary = createString("c_summary");

    public final StringPath c_title = createString("c_title");

    public final StringPath c_type = createString("c_type");

    public final StringPath c_videosummary = createString("c_videosummary");

    public final QFiles files;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<Special, QSpecial> specials = this.<Special, QSpecial>createList("specials", Special.class, QSpecial.class, PathInits.DIRECT2);

    public final QVolume volume;

    public QCases(String variable) {
        this(Cases.class, forVariable(variable), INITS);
    }

    public QCases(Path<? extends Cases> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCases(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCases(PathMetadata metadata, PathInits inits) {
        this(Cases.class, metadata, inits);
    }

    public QCases(Class<? extends Cases> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.files = inits.isInitialized("files") ? new QFiles(forProperty("files"), inits.get("files")) : null;
        this.volume = inits.isInitialized("volume") ? new QVolume(forProperty("volume"), inits.get("volume")) : null;
    }

}

