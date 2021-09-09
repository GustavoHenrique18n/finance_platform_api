package com.finance.api.service;

import com.finance.api.FinanceReference;
import com.finance.api.LoggedUser;
import com.finance.api.entity.*;
import com.finance.api.exception.ApiRequestExceptionId;
import com.finance.api.exception.authRequestException;
import com.finance.api.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class FinanceService {
    private final IncomesRepository incomesRepository;
    private final ExpensesRepository expensesRepository;
    private final ExpensesTypesRepository expensesTypesRepository;
    private final IncomesTypeRepository incomesTypeRepository;
    private final UsersRepository usersRepository;

    public FinanceReference getTotalOfFinance(Long id) {
        User loggedUser = LoggedUser.getUserLogged();
        Optional<Users> UserloggedOnce = usersRepository.findUserByEmail(loggedUser.getUsername());
        if(UserloggedOnce.isPresent() ){
            if(UserloggedOnce.get().getId().equals(id)){
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
            throw new authRequestException("acesso negado por favor informe suas credenciais");
        }
        throw new ApiRequestExceptionId("id nao encontrado");
    }

    public List<Incomes> getIncomes(Long id) {
        User loggedUser = LoggedUser.getUserLogged();
        Optional<Users> UserloggedOnce = usersRepository.findUserByEmail(loggedUser.getUsername());
        if(UserloggedOnce.isPresent()) {
            if(UserloggedOnce.get().getId().equals(id)){
                return incomesRepository.userFinanceIncome(id);
            }
            throw new authRequestException("acesso negado por favor informe suas credenciais");
        }
        throw new ApiRequestExceptionId("id nao encontrado");
    }

    public List<Expenses> getExpenses(Long id) {
        User loggedUser = LoggedUser.getUserLogged();
        Optional<Users> UserloggedOnce = usersRepository.findUserByEmail(loggedUser.getUsername());
        if (UserloggedOnce.isPresent()) {
            if (UserloggedOnce.get().getId().equals(id)) {
                return expensesRepository.userFinanceExpense(id);
            }
            throw new authRequestException("acesso negado por favor informe suas credenciais");
        }
        throw new ApiRequestExceptionId("id nao encontrado");
    }

    public void createNewIncome( Incomes income) {
        User loggedUser = LoggedUser.getUserLogged();
        Optional<Users> UserloggedOnce = usersRepository.findUserByEmail(loggedUser.getUsername());

        if(UserloggedOnce.isPresent()){
            Users user = UserloggedOnce.get();
            income.setUser(user);
            incomesRepository.save(income);
        }

    }

    public void createNewExpense(Expenses expense) {
        User loggedUser = LoggedUser.getUserLogged();
        Optional<Users> UserloggedOnce = usersRepository.findUserByEmail(loggedUser.getUsername());

        if(UserloggedOnce.isPresent()) {
            Users user = UserloggedOnce.get();
            expense.setUser(user);
            expensesRepository.save(expense);
        }
    }

    public IncomesType createNewIncomeCategorie(  IncomesType newCategorie) {return incomesTypeRepository.save(newCategorie);}

    public ExpensesType createNewExpenseCategorie(ExpensesType newCategorie) {return expensesTypesRepository.save(newCategorie);}


    public void editAIncome(Long id, Incomes income) {
        Incomes incomesId = incomesRepository.findById(id)
                .orElseThrow(()->  new ApiRequestExceptionId("id nao encontrado") );
        incomesId.setNameIncome(income.getNameIncome());
        incomesId.setPreviewDate(income.getPreviewDate());
        incomesId.setPreviewValue(income.getPreviewValue());
        incomesRepository.save(incomesId);
    }

    public void editAExpense(Long id, Expenses expenses) {
        Expenses expensesId = expensesRepository.findById(id)
                .orElseThrow(()->  new ApiRequestExceptionId("id nao encontrado") );
        expensesId.setNameExpense(expenses.getNameExpense());
        expensesId.setPreviewDate(expenses.getPreviewDate());
        expensesId.setPreviewValue(expenses.getPreviewValue());
        expensesRepository.save(expensesId);

    }

    public void payOneExpense(Long id, Expenses expenses) {
        Expenses expensesId = expensesRepository.findById(id)
                .orElseThrow(()->  new ApiRequestExceptionId("id nao encontrado") );
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
                .orElseThrow(()->  new ApiRequestExceptionId("id nao encontrado") );
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
            throw  new ApiRequestExceptionId("id nao encontrado");
        }

        incomesRepository.deleteById(id);
    }

    public void deleteExpense(Long id) {
        boolean Id = expensesRepository.existsById(id);
        if(!Id){
            throw  new ApiRequestExceptionId("id nao encontrado");
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
