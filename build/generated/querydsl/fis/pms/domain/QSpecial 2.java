package fis.pms.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSpecial is a Querydsl query type for Special
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSpecial extends EntityPathBase<Special> {

    private static final long serialVersionUID = 1823267557L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSpecial special = new QSpecial("special");

    public final QCases case1;

    public final QFiles files;

    public final NumberPath<Long> Id = createNumber("Id", Long.class);

    public final StringPath s_filecase = createString("s_filecase");

    public final StringPath s_list1 = createString("s_list1");

    public final StringPath s_list2 = createString("s_list2");

    public final StringPath s_list3 = createString("s_list3");

    public final StringPath s_num = createString("s_num");

    public QSpecial(String variable) {
        this(Special.class, forVariable(variable), INITS);
    }

    public QSpecial(Path<? extends Special> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSpecial(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSpecial(PathMetadata metadata, PathInits inits) {
        this(Special.class, metadata, inits);
    }

    public QSpecial(Class<? extends Special> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.case1 = inits.isInitialized("case1") ? new QCases(forProperty("case1"), inits.get("case1")) : null;
        this.files = inits.isInitialized("files") ? new QFiles(forProperty("files"), inits.get("files")) : null;
    }

}

