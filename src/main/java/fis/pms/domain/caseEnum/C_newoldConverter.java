package fis.pms.domain.caseEnum;


import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

@Converter
public class C_newoldConverter implements AttributeConverter<C_newold, String> {


    @Override
    public String convertToDatabaseColumn(C_newold attribute) {
        return attribute !=null ? attribute.getNewold() : null;
    }

    @Override
    public C_newold convertToEntityAttribute(String dbData) {
        return dbData != null ?Arrays.stream(C_newold.values())
                .filter(type -> type.getNewold().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                : null;
    }
}
