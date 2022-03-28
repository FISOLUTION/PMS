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

    @PostMapping("worker")
    public WorkerFormResponse saveWorker(@RequestBody @Valid WorkerFormRequest request) throws WorkerException {
        Worker worker = request.createWorker();
        workerService.saveWorker(worker);
        return WorkerFormResponse.createResponse(worker);
    }

    @PatchMapping("worker/{id}")
    public WorkerFormResponse updateWorker(@RequestBody @Valid WorkerFormRequest request, @PathVariable Long id) {
        Worker worker = workerService.updateWorker(id, request);
        return WorkerFormResponse.createResponse(worker);
    }

    // 작업자 정보 삭제 (성공)
    @DeleteMapping("worker/{id}")
    public Long deleteWorker(@PathVariable Long w_id) {
        return workerService.remove(w_id);
    }

    // 전체 작업자 조회 (성공)
    @GetMapping("worker")
    public Result searchWorker() {
        List<Worker> workers = workerService.findAll();
        List<WorkerSearchResponse> responses = workers.stream()
                .map(worker -> new WorkerSearchResponse(worker))
                .collect(Collectors.toList());
        return new Result<>(responses);
    }
}
