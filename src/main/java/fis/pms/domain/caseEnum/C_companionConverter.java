package fis.pms.domain.caseEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

@Converter
public class C_companionConverter implements AttributeConverter<C_companion, String> {
    @Override
    public String convertToDatabaseColumn(C_companion attribute) {
        return attribute != null ? attribute.getCompanion() : null;
    }

    @Override
    public C_companion convertToEntityAttribute(String dbData) {
        return dbData != null ? Arrays.stream(C_companion.values())
                .filter(type -> type.getCompanion().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                : null;
    }
}
