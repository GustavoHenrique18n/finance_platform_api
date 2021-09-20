package com.finance.api.controller;

import com.finance.api.entity.Incomes;
import com.finance.api.entity.Report;
import com.finance.api.service.ReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "perfil")
public class ReportController {
    private final ReportsService reportsService;

    @Autowired
    public ReportController(ReportsService reportsService) {
        this.reportsService = reportsService;
    }

    @GetMapping(path = "/relatorios/{id}")
    public List<Report> getAllReports (@PathVariable(name = "id") Long id) {
        return reportsService.getAllreports(id);
    }

    @PostMapping(path = "/relatorios/gerarrelatorio")
    public void makeANewReport (@RequestBody Report report) {
        reportsService.makeANewReport(report);
    }
}
