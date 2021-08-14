package com.finance.api.controller;

import com.finance.api.FinanceReference;
import com.finance.api.entity.Expenses;
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

    @PostMapping(path="contasareceber/{id}")
    public void createIncome (@RequestParam(name="criar") String create,
                              @PathVariable(name = "id") Long id,
                              @RequestBody Incomes income,
                              @RequestBody IncomesType newCategorie
                             ){
        financeService.createNewIncome(id, create, income,newCategorie);
    }

    @GetMapping(path="contasapagar/{id}")
    public List<Expenses> myExpenses (@PathVariable(name = "id") Long id){
        return financeService.getExpenses(id);
    }

    @PostMapping(path="contasapagar/{id}")
    public void createExpense (@RequestParam(name="criar") String create,
                               @PathVariable(name = "id") Long id,
                               @RequestBody Expenses expense,
                               @RequestBody IncomesType newCategorie
                              ){
        financeService.createNewExpense(id, create, expense,newCategorie);
    }
}