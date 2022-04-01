package fis.pms.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWorkList is a Querydsl query type for WorkList
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWorkList extends EntityPathBase<WorkList> {

    private static final long serialVersionUID = -1440766301L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWorkList workList = new QWorkList("workList");

    public final DatePath<java.time.LocalDate> date = createDate("date", java.time.LocalDate.class);

    public final EnumPath<fis.pms.domain.fileEnum.F_process> f_process = createEnum("f_process", fis.pms.domain.fileEnum.F_process.class);

    public final QFiles files;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QWorker worker;

    public QWorkList(String variable) {
        this(WorkList.class, forVariable(variable), INITS);
    }

    public QWorkList(Path<? extends WorkList> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWorkList(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWorkList(PathMetadata metadata, PathInits inits) {
        this(WorkList.class, metadata, inits);
    }

    public QWorkList(Class<? extends WorkList> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.files = inits.isInitialized("files") ? new QFiles(forProperty("files"), inits.get("files")) : null;
        this.worker = inits.isInitialized("worker") ? new QWorker(forProperty("worker")) : null;
    }

}

