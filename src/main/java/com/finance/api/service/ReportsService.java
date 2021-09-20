package com.finance.api.service;

import com.finance.api.LoggedUser;
import com.finance.api.entity.Expenses;
import com.finance.api.entity.Incomes;
import com.finance.api.entity.Report;
import com.finance.api.entity.Users;
import com.finance.api.exception.authRequestException;
import com.finance.api.repository.ExpensesRepository;
import com.finance.api.repository.IncomesRepository;
import com.finance.api.repository.ReportRepository;
import com.finance.api.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportsService {
    private final ReportRepository reportRepository;
    private final UsersRepository usersRepository;
    private final IncomesRepository incomesRepository;
    private final ExpensesRepository expensesRepository;

    public List<Report> getAllreports(Long id) {
        Long loggedUserId = LoggedUser.convertStringtoLong(LoggedUser.getUserLoggedInId());

        if(loggedUserId.equals(id)){
            return reportRepository.findAllByUserId(loggedUserId);
        }

        throw new authRequestException("acesso negado por favor informe suas credenciais");
    }

    public void makeANewReport(Report report) {
        Long loggedUserId = LoggedUser.convertStringtoLong(LoggedUser.getUserLoggedInId());
        Users UserloggedOnce = usersRepository.findUserById(loggedUserId);
        List<Incomes> incomes = incomesRepository.userFinanceIncome(loggedUserId);
        List<Expenses> expenses = expensesRepository.userFinanceExpense(loggedUserId);
        List<Report> reports = reportRepository.findAllByUserId(loggedUserId);

        List<Report> reportsFiltered = reports.stream().filter(c -> c.getNameReport().equals(report.getNameReport())).collect(Collectors.toList());

        if(reportsFiltered.size() > 0){
            exportArchive export = new exportArchive();
            export.exportArchive(report , incomes , expenses);
        }else {
            report.setUser(UserloggedOnce);
            reportRepository.save(report);
        }
    }
}
