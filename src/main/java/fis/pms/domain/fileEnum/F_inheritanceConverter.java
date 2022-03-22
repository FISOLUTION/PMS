package fis.pms.domain.fileEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;


/*
 * 작성자: 원보라
 * 작성날짜: 2021/08/26
 * 작성내용: F_inheritanceConverter
 */
@Converter
public class F_inheritanceConverter implements AttributeConverter<F_inheritance, String>{


    @Override
    public String convertToDatabaseColumn(F_inheritance attribute) {
        return attribute !=null ? attribute.getInheritance() : null;
    }

    @Override
    public F_inheritance convertToEntityAttribute(String dbData) {
        return dbData != null ? Arrays.stream(F_inheritance.values())
                .filter(type -> type.getInheritance().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                : null;
    }
}
