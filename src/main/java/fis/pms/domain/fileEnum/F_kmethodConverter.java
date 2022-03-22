package fis.pms.domain.fileEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;


/*
 * 작성자: 원보라
 * 작성날짜: 2021/08/26
 * 작성내용: F_kmethodConverter
 */
@Converter
public class F_kmethodConverter implements AttributeConverter<F_kmethod, String>{


    @Override
    public String convertToDatabaseColumn(F_kmethod attribute) {
        return attribute !=null ? attribute.getKmethod() : null;
    }

    @Override
    public F_kmethod convertToEntityAttribute(String dbData) {
        return dbData != null ? Arrays.stream(F_kmethod.values())
                .filter(type -> type.getKmethod().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                : null;
    }
}

