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
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
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

    public void makeANewReport(Report report , HttpServletResponse response) {
        Long loggedUserId = LoggedUser.convertStringtoLong(LoggedUser.getUserLoggedInId());
        Users UserloggedOnce = usersRepository.findUserById(loggedUserId);
        List<Incomes> incomes = incomesRepository.userFinanceIncome(loggedUserId);
        List<Expenses> expenses = expensesRepository.userFinanceExpense(loggedUserId);

        exportArchive export = new exportArchive(reportRepository);
        export.exportArchive(report , incomes , expenses, response , UserloggedOnce);

    }

    public void downloadFile(String fileName, HttpServletResponse response) {

        if (fileName.contains(".xlsx")) response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=" +fileName);
        response.setHeader("Content-Transfer-Encoding", "binary");
        try {
            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
            FileInputStream fis = new FileInputStream("/home/gustavo/Downloads/api/exportsArchives/"+fileName);
            int len;
            byte[] buf = new byte[1024];
            while((len = fis.read(buf)) > 0) {
                bos.write(buf,0,len);
            }
            bos.close();
            response.flushBuffer();
        }catch (IOException e){

            e.printStackTrace();
        }
    }
}
