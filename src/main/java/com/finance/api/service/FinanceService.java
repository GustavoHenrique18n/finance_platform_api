package com.finance.api.service;

import com.finance.api.FinanceReference;
import com.finance.api.entity.Expenses;
import com.finance.api.entity.Incomes;
import com.finance.api.entity.IncomesType;
import com.finance.api.repository.ExpensesRepository;
import com.finance.api.repository.ExpensesTypesRepository;
import com.finance.api.repository.IncomesRepository;
import com.finance.api.repository.IncomesTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

public class FinanceService {
    private final IncomesRepository incomesRepository;
    private final ExpensesRepository expensesRepository;
    private final ExpensesTypesRepository expensesTypesRepository;
    private final IncomesTypeRepository incomesTypeRepository;

    SimpleDateFormat formarter = new SimpleDateFormat("dd-MM-yyyy");

    @Autowired
    public FinanceService(IncomesRepository incomesRepository,
                          ExpensesRepository expensesRepository,
                          ExpensesTypesRepository expensesTypesRepository,
                          IncomesTypeRepository incomesTypeRepository
    ) {
        this.incomesRepository = incomesRepository;
        this.expensesRepository = expensesRepository;
        this.expensesTypesRepository = expensesTypesRepository;
        this.incomesTypeRepository = incomesTypeRepository;
    }

    public FinanceReference getTotalOfFinance(Long id) {
        FinanceReference financeReference = new FinanceReference(0,0,0,0);
        Integer totalIncome = financeReference.getTotalIncome();
        Integer totalIncomePayment = financeReference.getTotalIncomePayment();
        Integer totalExpenses = financeReference.getTotalExpense();
        Integer totalExpensePayment = financeReference.getTotalExpensePayment();

        List<Incomes> income = incomesRepository.userFinanceIncome(id);
        List<Expenses> expenses = expensesRepository.userFinanceExpense(id);

        for(Incomes i : income) {
            totalIncome += i.getPreviewValue();
            financeReference.setTotalIncome(totalIncome);
            if(i.getConfirmedValue() != null){
                totalIncomePayment += i.getConfirmedValue();
                financeReference.setTotalIncomePayment(totalIncomePayment);
            }
        }

        for(Expenses i : expenses) {
            totalExpenses += i.getPreviewValue();
            financeReference.setTotalExpense(totalExpenses);
            if(i.getConfirmedValue() != null){
                totalExpensePayment += i.getConfirmedValue();
                financeReference.setTotalExpensePayment(totalExpensePayment);
            }
        }
        return financeReference;
    }

    public List<Incomes> getIncomes(Long id) {
        List<Incomes> income = incomesRepository.userFinanceIncome(id);
        for(Incomes i : income) {
            if(i.getConfirmedDate() != null) {
                String formatedDateConfirmed = formarter.format(i.getConfirmedDate());
                LocalDate convertStringToDate = LocalDate.parse(formatedDateConfirmed);
                i.setConfirmedDate(convertStringToDate);
            }
                String formatedPreview = formarter.format(i.getPreviewDate());
                LocalDate convertStringToDate = LocalDate.parse(formatedPreview);
                i.setPreviewDate(convertStringToDate);
          }
        return income;
    }

    public List<Expenses> getExpenses(Long id) {
        return expensesRepository.userFinanceExpense(id);
    }

    public void createNewIncome(Long id, String create, Incomes income, IncomesType newCategorie) {
        if(create == "conta") {

        }else if (create == "categoria") {

        }
    }

    public void createNewExpense(Long id, String create, Expenses expense, IncomesType newCategorie) {
    }
}
