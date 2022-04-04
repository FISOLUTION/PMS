package fis.pms.controller;

import fis.pms.controller.dto.Result;
import fis.pms.controller.dto.WorkerFormRequest;
import fis.pms.controller.dto.WorkerFormResponse;
import fis.pms.controller.dto.WorkerSearchResponse;
import fis.pms.domain.Worker;
import fis.pms.exception.WorkerException;
import fis.pms.service.WorkerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class WorkerController {

    private final WorkerService workerService;

    /**
     * @author 현승구
     * @param request
     * @return
     * @throws WorkerException
     */
    @PostMapping("/worker/signup")
    public WorkerFormResponse saveWorker(@RequestBody @Valid WorkerFormRequest request) throws WorkerException {
        Worker worker = request.createWorker();
        workerService.saveWorker(worker);
        return WorkerFormResponse.createResponse(worker);
    }

    /**
     * @author 현승구
     * @param request 업데이트 할 정보 객체
     * @param id 업데이트할 작업자 id
     * @return 개인정보 제외한 값 반환
     * @throws WorkerException
     */
    @PatchMapping("/worker/{id}")
    public WorkerFormResponse updateWorker(@RequestBody @Valid WorkerFormRequest request, @PathVariable Long id) throws WorkerException {
        Worker worker = workerService.updateWorker(id, request);
        return WorkerFormResponse.createResponse(worker);
    }

    /**
     * @author 현승구
     * @param w_id 작업자 id값
     * @return DB에서 특정 id 를 가진 Worker를 삭제합니다
     */
    @DeleteMapping("/worker/{id}")
    public Long deleteWorker(@PathVariable Long w_id) {
        return workerService.remove(w_id);
    }

    /**
     * @author 현승구
     * @return 전체 작업자 목록을 반환합니다
     */
    @GetMapping("/worker")
    public Result searchWorker() {
        List<Worker> workers = workerService.findAll();
        List<WorkerSearchResponse> responses = workers.stream()
                .map(worker -> new WorkerSearchResponse(worker))
                .collect(Collectors.toList());
        return new Result<>(responses);
    }
}
