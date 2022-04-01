package fis.pms.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QF_location is a Querydsl query type for F_location
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QF_location extends BeanPath<F_location> {

    private static final long serialVersionUID = -1362490942L;

    public static final QF_location f_location = new QF_location("f_location");

    public final StringPath bun = createString("bun");

    public final StringPath chung = createString("chung");

    public final StringPath suga = createString("suga");

    public final StringPath yall = createString("yall");

    public QF_location(String variable) {
        super(F_location.class, forVariable(variable));
    }

    public QF_location(Path<? extends F_location> path) {
        super(path.getType(), path.getMetadata());
    }

    public QF_location(PathMetadata metadata) {
        super(F_location.class, metadata);
    }

}

