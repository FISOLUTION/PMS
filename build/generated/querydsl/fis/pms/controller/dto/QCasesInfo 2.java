package fis.pms.controller.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * fis.pms.controller.dto.QCasesInfo is a Querydsl Projection type for CasesInfo
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QCasesInfo extends ConstructorExpression<CasesInfo> {

    private static final long serialVersionUID = -1467270074L;

    public QCasesInfo(com.querydsl.core.types.Expression<Long> v_id, com.querydsl.core.types.Expression<Long> c_id, com.querydsl.core.types.Expression<String> c_spage, com.querydsl.core.types.Expression<String> c_epage, com.querydsl.core.types.Expression<String> c_page, com.querydsl.core.types.Expression<String> c_class, com.querydsl.core.types.Expression<String> c_dodate, com.querydsl.core.types.Expression<String> c_pdate, com.querydsl.core.types.Expression<String> c_departmentname, com.querydsl.core.types.Expression<String> c_oldnum, com.querydsl.core.types.Expression<fis.pms.domain.caseEnum.C_kperiod> c_kperiod, com.querydsl.core.types.Expression<String> c_title, com.querydsl.core.types.Expression<String> c_drafter, com.querydsl.core.types.Expression<String> c_approver, com.querydsl.core.types.Expression<String> c_receiver, com.querydsl.core.types.Expression<fis.pms.domain.caseEnum.C_edoc> c_edoc, com.querydsl.core.types.Expression<String> c_openable, com.querydsl.core.types.Expression<String> c_hidden) {
        super(CasesInfo.class, new Class<?>[]{long.class, long.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, fis.pms.domain.caseEnum.C_kperiod.class, String.class, String.class, String.class, String.class, fis.pms.domain.caseEnum.C_edoc.class, String.class, String.class}, v_id, c_id, c_spage, c_epage, c_page, c_class, c_dodate, c_pdate, c_departmentname, c_oldnum, c_kperiod, c_title, c_drafter, c_approver, c_receiver, c_edoc, c_openable, c_hidden);
    }

}

