package fis.pms.repository.querymethod;

import com.querydsl.core.types.dsl.BooleanExpression;

import java.time.LocalDate;

import static fis.pms.domain.QWorkList.*;


public class WorkListQueryMethod {

    protected BooleanExpression dateGOE(LocalDate date){
        return date != null ? workList.date.goe(date) : null;
    }

    protected BooleanExpression dateLOE(LocalDate date){
        return date != null ? workList.date.loe(date) : null;
    }

}
