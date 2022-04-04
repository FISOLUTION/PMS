package fis.pms.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWorker is a Querydsl query type for Worker
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWorker extends EntityPathBase<Worker> {

    private static final long serialVersionUID = 172803026L;

    public static final QWorker worker = new QWorker("worker");

    public final EnumPath<Authority> authority = createEnum("authority", Authority.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final StringPath w_address = createString("w_address");

    public final StringPath w_name = createString("w_name");

    public final StringPath w_tel = createString("w_tel");

    public final ListPath<WorkList, QWorkList> workList = this.<WorkList, QWorkList>createList("workList", WorkList.class, QWorkList.class, PathInits.DIRECT2);

    public QWorker(String variable) {
        super(Worker.class, forVariable(variable));
    }

    public QWorker(Path<? extends Worker> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWorker(PathMetadata metadata) {
        super(Worker.class, metadata);
    }

}

