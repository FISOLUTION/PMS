package fis.pms.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVolume is a Querydsl query type for Volume
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVolume extends EntityPathBase<Volume> {

    private static final long serialVersionUID = 144004974L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVolume volume = new QVolume("volume");

    public final ListPath<Cases, QCases> caseList = this.<Cases, QCases>createList("caseList", Cases.class, QCases.class, PathInits.DIRECT2);

    public final QFiles files;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath v_casecount = createString("v_casecount");

    public final StringPath v_casenum = createString("v_casenum");

    public final StringPath v_inhernum = createString("v_inhernum");

    public final NumberPath<Integer> v_num = createNumber("v_num", Integer.class);

    public final StringPath v_pageSaved = createString("v_pageSaved");

    public QVolume(String variable) {
        this(Volume.class, forVariable(variable), INITS);
    }

    public QVolume(Path<? extends Volume> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVolume(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVolume(PathMetadata metadata, PathInits inits) {
        this(Volume.class, metadata, inits);
    }

    public QVolume(Class<? extends Volume> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.files = inits.isInitialized("files") ? new QFiles(forProperty("files"), inits.get("files")) : null;
    }

}

