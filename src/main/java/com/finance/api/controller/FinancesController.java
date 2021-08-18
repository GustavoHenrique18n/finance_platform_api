package com.finance.api.controller;

import com.finance.api.FinanceReference;
import com.finance.api.entity.Expenses;
import com.finance.api.entity.ExpensesType;
import com.finance.api.entity.Incomes;
import com.finance.api.entity.IncomesType;
import com.finance.api.service.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "profile")
public class FinancesController {

    private final FinanceService financeService;

    @Autowired
    public FinancesController(FinanceService financeService) {
        this.financeService = financeService;
    }

    @GetMapping(path = "/{id}")
    public FinanceReference getTotalOfFinance (@PathVariable(name = "id") Long id) {
        return financeService.getTotalOfFinance(id);
    }

    @GetMapping(path="contasareceber/{id}")
    public List<Incomes> myIncomes (@PathVariable(name = "id") Long id){
        return financeService.getIncomes(id);
    }

    @PostMapping(path="criarcontaareceber")
    public void createIncome (@RequestBody Incomes income){
          financeService.createNewIncome( income);
    }

    @PutMapping(path="contasareceber")
    public void editValueIncome (@RequestBody Incomes income , @RequestParam(name = "edit") Long id ) {
            financeService.editAIncome( id , income);
    }

    @PostMapping(path="criarnovacategoriareceita")
    public IncomesType createNewIncomeCategorie (@RequestBody IncomesType newCategorie){
        return financeService.createNewIncomeCategorie(newCategorie);
    }

    @GetMapping(path="contasapagar/{id}")
    public List<Expenses> myExpenses (@PathVariable(name = "id") Long id){
        return financeService.getExpenses(id);
    }

    @PostMapping(path="criarcontaapagar")
    public void createExpense (@RequestBody Expenses expense){
        financeService.createNewExpense(expense);
    }

    @PostMapping(path="criarnovacategoriadespesa")
    public ExpensesType createNewExpenseCategorie (@RequestBody ExpensesType newCategorie){
        return financeService.createNewExpenseCategorie(newCategorie);
    }

    @PutMapping(path="contasapagar")
    public void editValueExpense (@RequestBody Expenses expenses , @RequestParam(name = "edit") Long id ) {
        financeService.editAExpense( id , expenses);
    }

    @PutMapping(path="atualizarvalores")
    public void updateIncomesValues (@RequestBody Incomes incomes , @RequestParam(name = "pagar") Long id ) {
        financeService.updateIncomeValues( id , incomes);
    }

    @PutMapping(path="pagarconta")
    public void payOneExpense (@RequestBody Expenses expenses , @RequestParam(name = "pagar") Long id ) {
        financeService.payOneExpense( id , expenses);
    }

    @DeleteMapping(path="deletareceita/{id}")
    public void deleteIncome (@PathVariable(name = "id") Long id ) {
        financeService.deleteIncome(id);
    }

    @DeleteMapping(path="deletardespesa/{id}")
    public void deleteExpense (@PathVariable(name = "id") Long id ) {
        financeService.deleteExpense(id);
    }

    @DeleteMapping(path="deletardespesas")
    public void deleteMoreThanOneExpense (@RequestBody List<Expenses> expenses) {
        financeService.deleteMoreThanOneExpense(expenses);
    }

    @DeleteMapping(path="deletareceitas")
    public void deleteMoreThanOneIncomes (@RequestBody List<Incomes> incomes) {
        financeService.deleteMoreThanOneIncomes(incomes);
    }
}