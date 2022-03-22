package fis.pms.domain.fileEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;


/*
 * 작성자: 원보라
 * 작성날짜: 2021/08/26
 * 작성내용: F_processConverter
 */
@Converter
public class F_processConverter implements AttributeConverter<F_process, String>{


    @Override
    public String convertToDatabaseColumn(F_process attribute) {
        return attribute !=null ? attribute.getProcess() : null;
    }

    @Override
    public F_process convertToEntityAttribute(String dbData) {
        return dbData != null ? Arrays.stream(F_process.values())
                .filter(type -> type.getProcess().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                : null;
    }
}

