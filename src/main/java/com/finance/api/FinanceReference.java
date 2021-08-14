package com.finance.api;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@ToString
public class FinanceReference {
    private Integer totalIncome;
    private Integer totalIncomePayment;
    private Integer totalExpense;
    private Integer totalExpensePayment;
}
