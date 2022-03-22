package fis.pms.service;

import fis.pms.controller.login.dto.LoginRequest;
import fis.pms.controller.login.dto.LoginResponse;
import fis.pms.domain.Worker;
import fis.pms.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LoginService {

    private final WorkerRepository workerRepository;

    public Worker login(LoginRequest loginRequest){
        Worker worker = workerRepository.findnickname(loginRequest.getNickname());
        if(worker.getPassword().equals(loginRequest.getPassword())){
            return worker;
        }
        return null;
    }

    public LoginResponse workerInfo(Long workerId){
        Worker worker = workerRepository.findOne(workerId);
        if (worker == null) {
            throw new IllegalStateException("잘못된 세션 정보");
        }
        return new LoginResponse(worker.getId(), worker.getW_name(), worker.getAuthority());
    }
}
