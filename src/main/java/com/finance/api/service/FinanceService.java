package com.finance.api.service;

import com.finance.api.FinanceReference;
import com.finance.api.entity.*;
import com.finance.api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

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

    public List<Incomes> getIncomes(Long id) {
        return incomesRepository.userFinanceIncome(id);
    }

    public List<Expenses> getExpenses(Long id) {
        return expensesRepository.userFinanceExpense(id);
    }


    public void createNewIncome(Long id, String create, Incomes income) {
        if(create == "conta") {
            Optional<Users> userId = usersRepository.findById(id);
            if(userId.isPresent()) {
                Users user = userId.get();
                income.setUser(user);
                incomesRepository.save(income);
            }
    }
}
    public void createNewExpense(Long id, String create, Expenses expense) {
        if (create == "conta") {
            Optional<Users> userId = usersRepository.findById(id);
            if (userId.isPresent()) {
                Users user = userId.get();
                expense.setUser(user);
                expensesRepository.save(expense);
            }
        }
    }

    public IncomesType createNewIncomeCategorie( String create, IncomesType newCategorie) {
        if(create == "categoria"){
            incomesTypeRepository.save(newCategorie);
        }
        return newCategorie;
    }

    public ExpensesType createNewExpenseCategorie(String create, ExpensesType newCategorie) {
        if(create == "categoria"){
            expensesTypesRepository.save(newCategorie);
        }
        return newCategorie;
    }
}
