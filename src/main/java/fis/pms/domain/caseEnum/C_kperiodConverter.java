package fis.pms.domain.caseEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

@Converter
public class C_kperiodConverter implements AttributeConverter<C_kperiod, String> {
    @Override
    public String convertToDatabaseColumn(C_kperiod attribute) {
        return attribute != null ? attribute.getKperiod() : null;
    }

    @Override
    public C_kperiod convertToEntityAttribute(String dbData) {
        return dbData != null ?Arrays.stream(C_kperiod.values())
                .filter(type -> type.getKperiod().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                : null;
    }
}
