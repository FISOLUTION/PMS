package fis.pms.domain.fileEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;


/*
 * 작성자: 원보라
 * 작성날짜: 2021/08/26
 * 작성내용: F_typeConverter
 */
@Converter
public class F_typeConverter implements AttributeConverter<F_type, String>{


    @Override
    public String convertToDatabaseColumn(F_type attribute) {
        return attribute !=null ? attribute.getType() : null;
    }

    @Override
    public F_type convertToEntityAttribute(String dbData) {
        try {
            return dbData != null ? Arrays.stream(F_type.values())
                    .filter(type -> type.getType().equals(dbData))
                    .findFirst()
                    .orElseThrow(IllegalArgumentException::new)
                    : null;
        } catch (Exception e){
            return null;
        }
    }
}

