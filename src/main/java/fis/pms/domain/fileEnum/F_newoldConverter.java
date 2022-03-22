package fis.pms.domain.fileEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;


/*
 * 작성자: 원보라
 * 작성날짜: 2021/08/26
 * 작성내용: F_newoldConverter
 */
@Converter
public class F_newoldConverter implements AttributeConverter<F_newold, String>{


    @Override
    public String convertToDatabaseColumn(F_newold attribute) {
        return attribute !=null ? attribute.getNewold() : null;
    }

    @Override
    public F_newold convertToEntityAttribute(String dbData) {
        return dbData != null ?Arrays.stream(F_newold.values())
                .filter(type -> type.getNewold().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                : null;
    }
}

