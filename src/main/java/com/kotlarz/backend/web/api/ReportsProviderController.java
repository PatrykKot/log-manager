package com.kotlarz.backend.web.api;

import com.kotlarz.backend.service.logs.ReportService;
import com.kotlarz.backend.web.dto.NewReportDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/reports")
public class ReportsProviderController {
    @Autowired
    private ReportService reportService;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public void createNewReport(@RequestBody NewReportDto newReport) {
        reportService.create(newReport);
    }
}
