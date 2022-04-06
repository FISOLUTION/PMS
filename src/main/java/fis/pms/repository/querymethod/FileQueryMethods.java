package fis.pms.repository.querymethod;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DatePath;
import fis.pms.domain.Office;
import fis.pms.domain.fileEnum.F_process;

import java.time.LocalDate;

import static fis.pms.domain.QFiles.files;
import static fis.pms.domain.QWorkList.workList;


public class FileQueryMethods {

    protected BooleanExpression uploadinclude(Boolean upload) {
        if(upload){
            return null;
        }
        else{
            return files.f_process.loe(F_process.UPLOAD);
        }
    }

    protected BooleanExpression checkinclude(Boolean check) {
        if(check){
            return files.f_process.goe(F_process.CHECK);
        }
        else{
            return files.f_process.goe(F_process.UPLOAD);
        }
    }


    /*
        원보라 제작
     */

    protected BooleanExpression first_b_numGoe(String first_b_num) {
        if (first_b_num == null) {
            return null;
        }
        return files.b_num.goe(first_b_num);
    }

    protected BooleanExpression last_b_numLoe(String last_b_num) {
        if (last_b_num == null) {
            return null;
        }
        return files.b_num.loe(last_b_num);
    }

    protected BooleanExpression first_DateGoe(LocalDate date) {
        if (date == null) {
            return null;
        }
        return workList.date.goe(date);
    }

    protected BooleanExpression last_DateLoe(LocalDate date) {
        if (date == null) {
            return null;
        }
        return workList.date.loe(date);
    }

    protected BooleanExpression first_labelGoe(String first_label) {
        if (first_label == null) {
            return null;
        }
        return files.f_labelcode.goe(first_label);
    }

    protected BooleanExpression last_labelLoe(String last_label) {
        if (last_label == null) {
            return null;
        }
        return files.f_labelcode.loe(last_label);
    }

    protected BooleanExpression o_codeEq(Office office) {
        return office !=null ? files.office.eq(office) : null;
    }

    protected BooleanExpression f_labelcodeEq(String f_labelcode) {
        return f_labelcode !=null && f_labelcode != "" ? files.f_labelcode.eq(f_labelcode) : null;
    }

    protected BooleanExpression f_nameEq(String f_name) {
        return f_name !=null && f_name != "" ? files.f_name.contains(f_name) : null;
    }

    protected BooleanExpression f_pyearEq(String f_pyear) {
        return f_pyear !=null && f_pyear != "" ? files.f_pyear.eq(f_pyear) : null;
    }


    /*
        한명수 제작
     */

    protected BooleanExpression fNameLike(String f_name) {
        if (f_name == null) {
            return null;
        }
        return files.f_name.contains(f_name);
    }

    protected BooleanExpression fPyearLike(String f_pyear) {
        if (f_pyear == null) {
            return null;
        }
        return files.f_pyear.eq(f_pyear);
    }

    protected BooleanExpression fEyearLike(String f_eyear) {
        if (f_eyear == null) {
            return null;
        }

        return files.f_eyear.eq(f_eyear);
    }


    protected BooleanExpression fLabelCodeLike(String f_labelcode) {
        if (f_labelcode == null) {
            return null;
        }
        return files.f_labelcode.eq(f_labelcode);
    }

    protected BooleanExpression bNumLike(String b_num) {
        if (b_num == null || b_num == "") {
            return null;
        }
        return files.b_num.eq(b_num);
    }

    protected BooleanExpression officeLike(Office office) {
        if (office == null) {
            return null;
        }
        return files.office.eq(office);
    }

}
