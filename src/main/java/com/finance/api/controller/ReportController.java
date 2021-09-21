package com.finance.api.controller;

import com.finance.api.entity.Incomes;
import com.finance.api.entity.Report;
import com.finance.api.service.ReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
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
    @ResponseBody
    public void makeANewReport (@RequestBody Report report,HttpServletResponse response) {
        reportsService.makeANewReport(report , response);
    }

    @GetMapping(path = "/file/{fileName}")
    @ResponseBody
    public void downloadFile (@PathVariable(name = "fileName") String fileName, HttpServletResponse response) {
        reportsService.downloadFile(fileName,response);
    }
}
