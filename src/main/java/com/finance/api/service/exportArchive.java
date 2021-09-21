package com.finance.api.service;

import com.finance.api.entity.Expenses;
import com.finance.api.entity.Incomes;
import com.finance.api.entity.Report;
import com.finance.api.entity.Users;
import com.finance.api.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
public class exportArchive {
    HSSFRow row;
    private final ReportRepository reportRepository;

    public void exportArchive(Report report , List<Incomes> incomes , List<Expenses> expenses , HttpServletResponse response , Users user){
        HSSFWorkbook workBook = new HSSFWorkbook();
        CellStyle style = workBook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        if(report.getWithExpenses() && report.getWithIncomes()){

            HSSFSheet sheetIncome = workBook.createSheet("receitas");
            HSSFSheet sheetExpense = workBook.createSheet("despesas");


            row = sheetIncome.createRow(0);
            Cell nameCellIncome = row.createCell(0);
            Cell previewDateCellIncome = row.createCell(1);
            Cell previewValueCellIncome = row.createCell(2);
            Cell confirmedDateCellIncome = row.createCell(3);
            Cell confirmedValueCellIncome = row.createCell(4);
            nameCellIncome.setCellStyle(style);
            previewDateCellIncome.setCellStyle(style);
            previewValueCellIncome.setCellStyle(style);
            confirmedDateCellIncome.setCellStyle(style);
            confirmedValueCellIncome.setCellStyle(style);

            row = sheetExpense.createRow(0);
            Cell nameCellExpense = row.createCell(0);
            Cell previewDateCellExpense = row.createCell(1);
            Cell previewValueCellExpense = row.createCell(2);
            Cell confirmedDateCellExpense = row.createCell(3);
            Cell confirmedValueCellExpense = row.createCell(4);
            nameCellExpense.setCellStyle(style);
            previewDateCellExpense.setCellStyle(style);
            previewValueCellExpense.setCellStyle(style);
            confirmedDateCellExpense.setCellStyle(style);
            confirmedValueCellExpense.setCellStyle(style);

            nameCellIncome.setCellValue("referencia");
            previewDateCellIncome.setCellValue("data prevista de recebimento");
            previewValueCellIncome.setCellValue("valor previsto para recebimento");
            confirmedDateCellIncome.setCellValue("data recebimento");
            confirmedValueCellIncome.setCellValue("valor pago");

            nameCellExpense.setCellValue("referencia");
            previewDateCellExpense.setCellValue("data prevista de pagamento");
            previewValueCellExpense.setCellValue("valor previsto para pagamento");
            confirmedDateCellExpense.setCellValue("data pagamento");
            confirmedValueCellExpense.setCellValue("valor pago");

            for(int i = 0; i < incomes.size(); i++){
                row = sheetIncome.createRow(i+1);
                for(int c = 0; c < 4 ; c++){
                    Cell cell = row.createCell(c);
                    cell.setCellStyle(style);
                    if(cell.getColumnIndex() == 0){
                        Incomes income = incomes.get(i);
                        cell.setCellValue(income.getNameIncome());

                    }else if(cell.getColumnIndex() == 1){
                        Incomes income = incomes.get(i);
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        String formattedDate = income.getPreviewDate().format(formatter);
                        cell.setCellValue(formattedDate);
                    }else if(cell.getColumnIndex() == 2){
                        Incomes income = incomes.get(i);
                        cell.setCellValue("R$ "+income.getPreviewValue());

                    }else if(cell.getColumnIndex() == 3){
                        Incomes income = incomes.get(i);
                        if(income.getConfirmedDate() != null){
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            String formattedDate = income.getConfirmedDate().format(formatter);
                            cell.setCellValue(formattedDate);
                        }

                    }else if(cell.getColumnIndex() == 4){
                        Incomes income = incomes.get(i);
                        cell.setCellValue("R$ "+ income.getConfirmedValue());

                    }
                }
            }

            for(int i = 0; i < expenses.size(); i++){
                row = sheetExpense.createRow(i+1);
                for(int c = 0; c < 4 ; c++){
                    Cell cell = row.createCell(c);
                    cell.setCellStyle(style);
                    if(cell.getColumnIndex() == 0){
                        Expenses expense = expenses.get(i);
                        cell.setCellValue(expense.getNameExpense());

                    }else if(cell.getColumnIndex() == 1){

                        Expenses expense = expenses.get(i);
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        String formattedDate = expense.getPreviewDate().format(formatter);
                        cell.setCellValue(formattedDate);

                    }else if(cell.getColumnIndex() == 2){
                        Expenses expense = expenses.get(i);
                        cell.setCellValue("R$ "+expense.getPreviewValue());

                    }else if(cell.getColumnIndex() == 3){
                        Expenses expense = expenses.get(i);
                        if(expense.getConfirmedDate() != null){
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            String formattedDate = expense.getConfirmedDate().format(formatter);
                            cell.setCellValue(formattedDate);
                        }
                    }else if(cell.getColumnIndex() == 4){
                        Expenses expense = expenses.get(i);
                        cell.setCellValue("R$ "+ expense.getConfirmedValue());

                    }
                }
            }

            for(int i = 0 ; i < 6 ; i ++){
                sheetIncome.autoSizeColumn(i);
                sheetExpense.autoSizeColumn(i);
            }

            try{
                FileOutputStream out = new FileOutputStream(new File(report.getNameReport()+".xlsx"));
                workBook.write(out);
                out.close();
                System.out.println("arquivo gerada");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(report.getWithIncomes()){
            HSSFSheet sheetIncome = workBook.createSheet("receitas");

            row = sheetIncome.createRow(0);
            Cell nameCellIncome = row.createCell(0);
            Cell previewDateCellIncome = row.createCell(1);
            Cell previewValueCellIncome = row.createCell(2);
            Cell confirmedDateCellIncome = row.createCell(3);
            Cell confirmedValueCellIncome = row.createCell(4);
            nameCellIncome.setCellStyle(style);
            previewDateCellIncome.setCellStyle(style);
            previewValueCellIncome.setCellStyle(style);
            confirmedDateCellIncome.setCellStyle(style);
            confirmedValueCellIncome.setCellStyle(style);

            nameCellIncome.setCellValue("referencia");
            previewDateCellIncome.setCellValue("data prevista de recebimento");
            previewValueCellIncome.setCellValue("valor previsto para recebimento");
            confirmedDateCellIncome.setCellValue("data recebimento");
            confirmedValueCellIncome.setCellValue("valor pago");


            for(int i = 0; i < incomes.size(); i++){
                row = sheetIncome.createRow(i+1);
                for(int c = 0; c < 4 ; c++){
                    Cell cell = row.createCell(c);
                    cell.setCellStyle(style);
                    if(cell.getColumnIndex() == 0){
                        Incomes income = incomes.get(i);
                        cell.setCellValue(income.getNameIncome());

                    }else if(cell.getColumnIndex() == 1){
                        Incomes income = incomes.get(i);
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        String formattedDate = income.getPreviewDate().format(formatter);
                        cell.setCellValue(formattedDate);
                    }else if(cell.getColumnIndex() == 2){
                        Incomes income = incomes.get(i);
                        cell.setCellValue("R$ "+income.getPreviewValue());

                    }else if(cell.getColumnIndex() == 3){
                        Incomes income = incomes.get(i);
                        if(income.getConfirmedDate() != null){
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            String formattedDate = income.getConfirmedDate().format(formatter);
                            cell.setCellValue(formattedDate);
                        }

                    }else if(cell.getColumnIndex() == 4){
                        Incomes income = incomes.get(i);
                        cell.setCellValue("R$ "+ income.getConfirmedValue());

                    }
                }
            }

            for(int i = 0 ; i < 6 ; i ++){
                sheetIncome.autoSizeColumn(i);
            }

            try{
                FileOutputStream out = new FileOutputStream(new File(report.getNameReport()+".xlsx"));
                workBook.write(out);
                out.close();
                System.out.println("arquivo gerada");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(report.getWithExpenses()){
            HSSFSheet sheetExpense = workBook.createSheet("despesas");

            row = sheetExpense.createRow(0);
            Cell nameCellExpense = row.createCell(0);
            Cell previewDateCellExpense = row.createCell(1);
            Cell previewValueCellExpense = row.createCell(2);
            Cell confirmedDateCellExpense = row.createCell(3);
            Cell confirmedValueCellExpense = row.createCell(4);
            nameCellExpense.setCellStyle(style);
            previewDateCellExpense.setCellStyle(style);
            previewValueCellExpense.setCellStyle(style);
            confirmedDateCellExpense.setCellStyle(style);
            confirmedValueCellExpense.setCellStyle(style);


            nameCellExpense.setCellValue("referencia");
            previewDateCellExpense.setCellValue("data prevista de pagamento");
            previewValueCellExpense.setCellValue("valor previsto para pagamento");
            confirmedDateCellExpense.setCellValue("data pagamento");
            confirmedValueCellExpense.setCellValue("valor pago");


            for(int i = 0; i < expenses.size(); i++){
                row = sheetExpense.createRow(i+1);
                for(int c = 0; c < 4 ; c++){
                    Cell cell = row.createCell(c);
                    cell.setCellStyle(style);
                    if(cell.getColumnIndex() == 0){
                        Expenses expense = expenses.get(i);
                        cell.setCellValue(expense.getNameExpense());

                    }else if(cell.getColumnIndex() == 1){

                        Expenses expense = expenses.get(i);
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        String formattedDate = expense.getPreviewDate().format(formatter);
                        cell.setCellValue(formattedDate);

                    }else if(cell.getColumnIndex() == 2){
                        Expenses expense = expenses.get(i);
                        cell.setCellValue("R$ "+expense.getPreviewValue());

                    }else if(cell.getColumnIndex() == 3){
                        Expenses expense = expenses.get(i);
                        if(expense.getConfirmedDate() != null){
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            String formattedDate = expense.getConfirmedDate().format(formatter);
                            cell.setCellValue(formattedDate);
                        }
                    }else if(cell.getColumnIndex() == 4){
                        Expenses expense = expenses.get(i);
                        cell.setCellValue("R$ "+ expense.getConfirmedValue());

                    }
                }
            }

            for(int i = 0 ; i < 6 ; i ++){
                sheetExpense.autoSizeColumn(i);
            }

            try{
                File fileName = new File(System.currentTimeMillis() + report.getNameReport() + ".xlsx");
                FileOutputStream out = new FileOutputStream("/home/gustavo/Downloads/api/exportsArchives/"+ fileName);
                report.setFilename(fileName.getName());
                report.setType(".xlsx");
                report.setUser(user);
                reportRepository.save(report);

                workBook.write(out);

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment; filename=" +fileName.getName());
                response.setHeader("Content-Transfer-Encoding", "binary");

                try {
                    BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
                    FileInputStream fis = new FileInputStream("/home/gustavo/Downloads/api/exportsArchives/"+fileName.getName());
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

                out.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
