package fis.pms.controller;

import fis.pms.configuator.argumentResolver.Login;
import fis.pms.controller.dto.logindto.LoginRequest;
import fis.pms.controller.dto.logindto.LoginResponse;
import fis.pms.domain.Worker;
import fis.pms.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {

        // 아이디 비번 확인
        Worker worker = loginService.login(loginRequest);
        if (worker == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }

        // 세션이 있으면 해당 세션 반환, 없으면 신규 생성
        HttpSession session = request.getSession();
        session.setAttribute("loginId", worker.getId());

        return new LoginResponse(worker.getId(), worker.getW_name(), worker.getAuthority());
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    @GetMapping("/userinfo")
    public LoginResponse workerInfo(@Login Long workerId) {
        LoginResponse loginResponse = loginService.workerInfo(workerId);
        return loginResponse;
    }
}
