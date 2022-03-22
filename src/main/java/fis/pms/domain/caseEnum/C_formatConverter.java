package fis.pms.domain.caseEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

@Converter
public class C_formatConverter implements AttributeConverter<C_format, String> {
    @Override
    public String convertToDatabaseColumn(C_format attribute) {
        return attribute != null ? attribute.getFormat() : null;
    }

    @Override
    public C_format convertToEntityAttribute(String dbData) {
        return dbData != null ? Arrays.stream(C_format.values())
                .filter(type -> type.getFormat().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                : null;
    }
}
