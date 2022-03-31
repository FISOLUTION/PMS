package fis.pms.domain.fileEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

@Converter
public class F_kperiodConverter implements AttributeConverter<F_kperiod, String>{

    @Override
    public String convertToDatabaseColumn(F_kperiod attribute) {
        return attribute !=null ? attribute.getKperiod() : null;

    }

    @Override
    public F_kperiod convertToEntityAttribute(String dbData) {
        try {
            return dbData != null ? Arrays.stream(F_kperiod.values())
                    .filter(type -> type.getKperiod().equals(dbData))
                    .findFirst()
                    .orElseThrow(IllegalArgumentException::new)
                    : null;
        } catch (Exception e){
            return null;
        }
    }
}

