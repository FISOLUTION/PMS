package fis.pms.domain.caseEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

@Converter
public class C_detailtypeConverter implements AttributeConverter<C_detailtype, String> {
    @Override
    public String convertToDatabaseColumn(C_detailtype attribute) {
        return attribute != null ? attribute.getDetailtype() : null;
    }

    @Override
    public C_detailtype convertToEntityAttribute(String dbData) {
        return dbData != null ? Arrays.stream(C_detailtype.values())
                .filter(type -> type.getDetailtype().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                : null;
    }
}
