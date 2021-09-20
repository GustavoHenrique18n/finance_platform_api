package com.finance.api.controller;

import com.finance.api.FinanceReference;
import com.finance.api.entity.Expenses;
import com.finance.api.entity.ExpensesType;
import com.finance.api.entity.Incomes;
import com.finance.api.entity.IncomesType;
import com.finance.api.service.FinanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "perfil")
@RequiredArgsConstructor
public class FinancesController {

    private final FinanceService financeService;

    @GetMapping(path = "/{id}")
    public FinanceReference getTotalOfFinance (@PathVariable(name = "id") Long id) {
        return financeService.getTotalOfFinance(id);
    }

    @GetMapping(path="contasareceber/{id}")
    public List<Incomes> myIncomes (@PathVariable(name = "id") Long id){
        return financeService.getIncomes(id);
    }

    @PostMapping(path="contasareceber")
    public void createIncome (@RequestBody Incomes income){
          financeService.createNewIncome( income);
    }

    @PutMapping(path="contasareceber")
    public void editValueIncome (@RequestBody Incomes income , @RequestParam(name = "edit") Long id ) {
            financeService.editAIncome( id , income);
    }

    @PostMapping(path="contasareceber/criarnovacategoriareceita")
    public IncomesType createNewIncomeCategorie (@RequestBody IncomesType newCategorie){
        return financeService.createNewIncomeCategorie(newCategorie);
    }

    @GetMapping(path="contasapagar/{id}")
    public List<Expenses> myExpenses (@PathVariable(name = "id") Long id){
        return financeService.getExpenses(id);
    }

    @PostMapping(path="contasapagar")
    public void createExpense (@RequestBody Expenses expense){
        financeService.createNewExpense(expense);
    }

    @PostMapping(path="contasapagar/criarnovacategoriadespesa")
    public ExpensesType createNewExpenseCategorie (@RequestBody ExpensesType newCategorie){
        return financeService.createNewExpenseCategorie(newCategorie);
    }

    @PutMapping(path="contasapagar")
    public void editValueExpense (@RequestBody Expenses expenses , @RequestParam(name = "edit") Long id ) {
        financeService.editAExpense( id , expenses);
    }

    @PutMapping(path="contasareceber/atualizarvalores")
    public void updateIncomesValues (@RequestBody Incomes incomes , @RequestParam(name = "pagar") Long id ) {
        financeService.updateIncomeValues( id , incomes);
    }

    @PutMapping(path="contasapagar/pagarconta")
    public void payOneExpense (@RequestBody Expenses expenses , @RequestParam(name = "pagar") Long id ) {
        financeService.payOneExpense( id , expenses);
    }

    @DeleteMapping(path="contasareceber/deletarconta/{id}")
    public void deleteIncome (@PathVariable(name = "id") Long id ) {
        financeService.deleteIncome(id);
    }

    @DeleteMapping(path="contasapagar/deletarconta/{id}")
    public void deleteExpense (@PathVariable(name = "id") Long id ) {
        financeService.deleteExpense(id);
    }

    @DeleteMapping(path="contasapagar/deletarcontas")
    public void deleteMoreThanOneExpense (@RequestBody List<Expenses> expenses) {
        financeService.deleteMoreThanOneExpense(expenses);
    }

    @DeleteMapping(path="contasareceber/deletarcontas")
    public void deleteMoreThanOneIncomes (@RequestBody List<Incomes> incomes) {
        financeService.deleteMoreThanOneIncomes(incomes);
    }

    @GetMapping(path="filtrocontas")
    public List<Incomes> filter () {
        return financeService.filterDateByUser();
    }
}