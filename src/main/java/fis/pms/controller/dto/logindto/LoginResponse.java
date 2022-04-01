package fis.pms.controller.dto.logindto;

import fis.pms.domain.Authority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private Long workerId;
    private String w_name;
    private Authority authority;
}
