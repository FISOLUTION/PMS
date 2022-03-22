package fis.pms.domain.fileEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;


/*
 * 작성자: 원보라
 * 작성날짜: 2021/08/26
 * 작성내용: F_kplaceConverter
 */
@Converter
public class F_kplaceConverter implements AttributeConverter<F_kplace, String>{


    @Override
    public String convertToDatabaseColumn(F_kplace attribute) {
        return attribute !=null ? attribute.getKplace() : null;

    }

    @Override
    public F_kplace convertToEntityAttribute(String dbData) {
        try {
            return dbData != null ? Arrays.stream(F_kplace.values())
                    .filter(type -> type.getKplace().equals(dbData))
                    .findFirst()
                    .orElseThrow(IllegalArgumentException::new)
                    : null;
        } catch (Exception e){
            return null;
        }
    }
}
