package fis.pms.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterStatusDTO {
    private String o_code;
    private String o_name;
    private Long count;
}
