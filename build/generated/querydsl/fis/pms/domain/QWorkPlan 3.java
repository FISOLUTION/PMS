package fis.pms.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QWorkPlan is a Querydsl query type for WorkPlan
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWorkPlan extends EntityPathBase<WorkPlan> {

    private static final long serialVersionUID = -1440644818L;

    public static final QWorkPlan workPlan = new QWorkPlan("workPlan");

    public final NumberPath<Long> check = createNumber("check", Long.class);

    public final NumberPath<Long> export = createNumber("export", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> imgModify = createNumber("imgModify", Long.class);

    public final NumberPath<Long> input = createNumber("input", Long.class);

    public final NumberPath<Long> preInfo = createNumber("preInfo", Long.class);

    public final NumberPath<Long> scan = createNumber("scan", Long.class);

    public final NumberPath<Long> upload = createNumber("upload", Long.class);

    public QWorkPlan(String variable) {
        super(WorkPlan.class, forVariable(variable));
    }

    public QWorkPlan(Path<? extends WorkPlan> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWorkPlan(PathMetadata metadata) {
        super(WorkPlan.class, metadata);
    }

}

