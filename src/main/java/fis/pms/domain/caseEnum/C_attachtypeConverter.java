package fis.pms.domain.caseEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

@Converter
public class C_attachtypeConverter implements AttributeConverter<C_attachtype, String> {
    @Override
    public String convertToDatabaseColumn(C_attachtype attribute) {
        return attribute != null ? attribute.getAttachtype() : null;
    }

    @Override
    public C_attachtype convertToEntityAttribute(String dbData) {
        return dbData != null ? Arrays.stream(C_attachtype.values())
                .filter(type -> type.getAttachtype().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                : null;
    }
}
