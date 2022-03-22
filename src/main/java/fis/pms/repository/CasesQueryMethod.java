package fis.pms.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import fis.pms.domain.QCases;

import static fis.pms.domain.QCases.cases;

public class CasesQueryMethod {
    protected BooleanExpression cOldNumLike(String c_oldnum) {
        if (c_oldnum == null) {
            return null;
        }
        return cases.c_oldnum.eq(c_oldnum);
    }

    protected BooleanExpression cTitleLike(String c_title) {
        if (c_title == null) {
            return null;
        }
        return cases.c_title.eq(c_title);
    }

    protected BooleanExpression cReceiverLike(String c_receiver) {
        if (c_receiver == null) {
            return null;
        }
        return cases.c_receiver.eq(c_receiver);
    }
}
