package fis.pms.domain.caseEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

@Converter
public class C_storageConverter implements AttributeConverter<C_storage, String> {
    @Override
    public String convertToDatabaseColumn(C_storage attribute) {
        return attribute != null ? attribute.getStorage() : null;
    }

    @Override
    public C_storage convertToEntityAttribute(String dbData) {
        return dbData != null ? Arrays.stream(C_storage.values())
                .filter(type -> type.getStorage().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                : null;
    }
}
