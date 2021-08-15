package com.finance.api.repository;

import com.finance.api.entity.Expenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpensesRepository extends JpaRepository<Expenses , Long> {

    @Query(value = "SELECT * , DATE_FORMAT(expenses.preview_date, \"%d/%c/%Y\") ,  DATE_FORMAT(expenses.confirmed_date, \"%Y\") FROM expenses WHERE expenses.user_id = ?1",
            nativeQuery = true
    )

    List<Expenses> userFinanceExpense(Number user);

}
