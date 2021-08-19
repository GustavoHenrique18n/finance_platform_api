package com.finance.api.service;

import com.finance.api.FinanceReference;
import com.finance.api.entity.*;
import com.finance.api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class FinanceService {
    private final IncomesRepository incomesRepository;
    private final ExpensesRepository expensesRepository;
    private final ExpensesTypesRepository expensesTypesRepository;
    private final IncomesTypeRepository incomesTypeRepository;
    private final UsersRepository usersRepository;

    @Autowired
    public FinanceService(IncomesRepository incomesRepository,
                          ExpensesRepository expensesRepository,
                          ExpensesTypesRepository expensesTypesRepository,
                          IncomesTypeRepository incomesTypeRepository,
                          UsersRepository usersRepository) {
        this.incomesRepository = incomesRepository;
        this.expensesRepository = expensesRepository;
        this.expensesTypesRepository = expensesTypesRepository;
        this.incomesTypeRepository = incomesTypeRepository;
        this.usersRepository = usersRepository;
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

    public List<Incomes> getIncomes(Long id) {return incomesRepository.userFinanceIncome(id);}

    public List<Expenses> getExpenses(Long id) {
        return expensesRepository.userFinanceExpense(id);
    }

    public void createNewIncome( Incomes income) {incomesRepository.save(income);}

    public void createNewExpense(Expenses expense) {expensesRepository.save(expense);}

    public IncomesType createNewIncomeCategorie(  IncomesType newCategorie) {return incomesTypeRepository.save(newCategorie);}

    public ExpensesType createNewExpenseCategorie(ExpensesType newCategorie) {return expensesTypesRepository.save(newCategorie);}


    public void editAIncome(Long id, Incomes income) {
        Incomes incomesId = incomesRepository.findById(id)
                .orElseThrow(()-> new IllegalMonitorStateException("nao existe o id") );
        incomesId.setNameIncome(income.getNameIncome());
        incomesId.setPreviewDate(income.getPreviewDate());
        incomesId.setPreviewValue(income.getPreviewValue());
        incomesRepository.save(incomesId);
    }

    public void editAExpense(Long id, Expenses expenses) {
        Expenses expensesId = expensesRepository.findById(id)
                .orElseThrow(()-> new IllegalMonitorStateException("nao existe o id") );
        expensesId.setNameExpense(expenses.getNameExpense());
        expensesId.setPreviewDate(expenses.getPreviewDate());
        expensesId.setPreviewValue(expenses.getPreviewValue());
        expensesRepository.save(expensesId);

    }

    public void payOneExpense(Long id, Expenses expenses) {
        Expenses expensesId = expensesRepository.findById(id)
                .orElseThrow(()-> new IllegalMonitorStateException("nao existe o id") );
        Integer beforeConfirmedValue = expensesId.getConfirmedValue() == null ? 0 : expensesId.getConfirmedValue();

        Integer sumOfPayment = expenses.getConfirmedValue() + beforeConfirmedValue;
        expensesId.setConfirmedDate(expenses.getConfirmedDate());
        expensesId.setConfirmedValue(sumOfPayment);

        if(sumOfPayment <= expensesId.getPreviewValue() ){
            expensesRepository.save(expensesId);
        }
    }

    public void updateIncomeValues(Long id, Incomes incomes) {
        Incomes incomesId = incomesRepository.findById(id)
                .orElseThrow(()-> new IllegalMonitorStateException("nao existe o id") );
        Integer beforeConfirmedValue = incomesId.getConfirmedValue() == null ? 0 : incomesId.getConfirmedValue();

        Integer sumOfPayment = incomes.getConfirmedValue() + beforeConfirmedValue;
        incomesId.setConfirmedDate(incomes.getConfirmedDate());
        incomesId.setConfirmedValue(sumOfPayment);

        if(sumOfPayment <= incomesId.getPreviewValue() ){
            incomesRepository.save(incomesId);
        }
    }

    public void deleteIncome(Long id) {
        boolean Id = incomesRepository.existsById(id);
        if(!Id){
            throw new IllegalStateException("id nao encontrado");
        }

        incomesRepository.deleteById(id);
    }

    public void deleteExpense(Long id) {
        boolean Id = expensesRepository.existsById(id);
        if(!Id){
            throw new IllegalStateException("id nao encontrado");
        }

        expensesRepository.deleteById(id);
    }

    public void deleteMoreThanOneExpense(List<Expenses> expenses) {
        for (Expenses i : expenses){
            expensesRepository.deleteById(i.getId());
        }
    }

    public void deleteMoreThanOneIncomes(List<Incomes> incomes) {
        for (Incomes i : incomes){
            incomesRepository.deleteById(i.getId());
        }
    }
}
