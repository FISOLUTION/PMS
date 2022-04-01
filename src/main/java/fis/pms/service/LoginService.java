package fis.pms.service;

import fis.pms.controller.dto.logindto.LoginRequest;
import fis.pms.controller.dto.logindto.LoginResponse;
import fis.pms.domain.Worker;
import fis.pms.exception.WorkerException;
import fis.pms.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LoginService {

    private final WorkerRepository workerRepository;

    public Worker login(LoginRequest loginRequest) {
        Worker worker = workerRepository.findByNickname(loginRequest.getNickname())
                .orElseThrow(() -> new WorkerException("존재하지 않는 닉네임 입니다. 닉네임을 다시 한번 확인해 주세요"));
        if (worker.getPassword().equals(loginRequest.getPassword())) {
            return worker;
        }
        return null;
    }

    public LoginResponse workerInfo(Long workerId) {
        Worker worker = workerRepository.findOne(workerId)
                .orElseThrow(() -> new WorkerException("해당 유저가 존재하지 않습니다"));
        if (worker == null) {
            throw new IllegalStateException("잘못된 세션 정보");
        }
        return new LoginResponse(worker.getId(), worker.getW_name(), worker.getAuthority());
    }
}
