package fis.pms.service.currentWorking;

import fis.pms.domain.Files;
import fis.pms.domain.fileEnum.F_process;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 *   현재 작업중인 labelcode 에 대한 정보를 담을 객체 db를 사용하지 않으며
 *   멀티스레드 환경에 맞추어 ConcurrentHashMap을 사용했다.
 */
@Component
@RequiredArgsConstructor
public class CurrentWorkingLabelCode {
    private final Map<String, WorkingInfo> CurrentWork = new ConcurrentHashMap<>();

    public Optional<Object> isWorking(String labelCode){
        return Optional.ofNullable(CurrentWork.get(labelCode));
    }

    public Optional<Object> isWorking(Files files){
        return Optional.ofNullable(CurrentWork.get(files.getF_labelcode()));
    }

    public boolean setLabelWork(String labelCode, Long w_id, F_process f_process){
        if(isWorking(labelCode).get().equals(null)){
            CurrentWork.put(labelCode, new WorkingInfo(w_id, f_process));
            return true;
        }
        return false;
    }

    public boolean setLabelWork(Files file, Long w_id, F_process f_process){
        if(isWorking(file).get().equals(null)){
            CurrentWork.put(file.getF_labelcode(), new WorkingInfo(w_id, f_process));
            return true;
        }
        return false;
    }

    public void detachWork(String labelCode){
        if(isWorking(labelCode).equals(null)){
            return;
        }
        CurrentWork.remove(labelCode);
    }

    public void detachWork(Files file){
        if(isWorking(file).equals(null)){
            return;
        }
        CurrentWork.remove(file.getF_labelcode());
    }
}
