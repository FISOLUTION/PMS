package fis.pms.domain.fileEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;


@Converter
public class F_constructConverter implements AttributeConverter<F_construct, String>{


    @Override
    public String convertToDatabaseColumn(F_construct attribute) {
        return attribute !=null ? attribute.getConstruct() : null;
    }

    @Override
    public F_construct convertToEntityAttribute(String dbData) {
        try {
            return dbData != null ? Arrays.stream(F_construct.values())
                    .filter(type -> type.getConstruct().equals(dbData))
                    .findFirst()
                    .orElseThrow(IllegalArgumentException::new)
                    : null;
        } catch (Exception e){
            return null;
        }
    }
}
