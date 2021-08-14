package com.finance.api.controller;

import com.finance.api.entity.Incomes;
import com.finance.api.entity.Report;
import com.finance.api.service.ReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "relatorios")
public class ReportController {
    private final ReportsService reportsService;

    @Autowired
    public ReportController(ReportsService reportsService) {
        this.reportsService = reportsService;
    }

    @GetMapping(path = "/{id}")
    public void getAllReports (@PathVariable(name = "id") Long id) {
        reportsService.getAllreports(id);
    }

    @PostMapping(path="/{id}")
    public void createReport (@RequestParam(name="criar") Boolean create,
                              @PathVariable(name = "id") Long id,
                              @RequestBody Report report
                             ){
        reportsService.createNewReport(id, create , report);
    }
}