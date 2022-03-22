package fis.pms.domain.fileEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;


/*
 * 작성자: 원보라
 * 작성날짜: 2021/08/26
 * 작성내용: F_modifyConverter
 */
@Converter
public class F_modifyConverter implements AttributeConverter<F_modify, String>{


    @Override
    public String convertToDatabaseColumn(F_modify attribute) {
        return attribute !=null ? attribute.getModify() : null;
    }

    @Override
    public F_modify convertToEntityAttribute(String dbData) {
        return dbData != null ? Arrays.stream(F_modify.values())
                .filter(type -> type.getModify().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                : null;
    }
}

