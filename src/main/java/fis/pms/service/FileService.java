package fis.pms.service;

import fis.pms.controller.dto.ExportSaveRequest;
import fis.pms.controller.dto.ExportSaveResponse;
import fis.pms.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    public ExportSaveResponse updateExportInfo(ExportSaveRequest exportSaveRequest) {
        List<ExportSaveRequest.ExportList> E_lists = exportSaveRequest.getE_list();
        E_lists.stream()
                .forEach(files -> (fileRepository.findOne(files.getF_id())).updateFileExport(files.getB_num(), files.getF_db(), files.getF_scan(), files.getF_exportdate()));
        List<Long> id = E_lists.stream()
                .map(o -> o.getF_id())
                .collect(Collectors.toList());
        ExportSaveResponse exportSaveResponse = new ExportSaveResponse();
        exportSaveResponse.setF_id(id);
        return exportSaveResponse;
    }
}
