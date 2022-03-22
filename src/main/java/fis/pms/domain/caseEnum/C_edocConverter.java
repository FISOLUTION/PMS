package fis.pms.domain.caseEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

/**
 * 작성자: 고준영
 * 작성날짜: 2021/08/31
 * 작성내용: C_edocConverter
 */
@Converter
public class C_edocConverter implements AttributeConverter<C_edoc, String> {


    @Override
    public String convertToDatabaseColumn(C_edoc attribute) {
        return attribute != null ? attribute.getEdoc() : null;
    }

    @Override
    public C_edoc convertToEntityAttribute(String dbData) {
        return dbData != null? Arrays.stream(C_edoc.values())
                .filter(type -> type.getEdoc().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                : null;
    }
}
