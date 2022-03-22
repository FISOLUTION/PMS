package fis.pms.domain.caseEnum;


import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

@Converter
public class C_modifyConverter implements AttributeConverter<C_modify, String> {


    @Override
    public String convertToDatabaseColumn(C_modify attribute) {
        return attribute !=null ? attribute.getModify() : null;
    }

    @Override
    public C_modify convertToEntityAttribute(String dbData) {
        return dbData != null ? Arrays.stream(C_modify.values())
                .filter(type -> type.getModify().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                : null;
    }
}
