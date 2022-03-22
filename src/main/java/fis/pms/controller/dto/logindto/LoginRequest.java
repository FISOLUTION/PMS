package fis.pms.controller.dto.logindto;

import lombok.Data;

@Data
public class LoginRequest {
    private String nickname;
    private String password;
}
