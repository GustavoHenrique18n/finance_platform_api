package com.finance.api.repository;

import com.finance.api.entity.Expenses;
import com.finance.api.entity.Incomes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpensesRepository extends JpaRepository<Expenses , Long> {

    @Query(value = "SELECT * FROM expenses WHERE expenses.user_id = ?1",
            nativeQuery = true
    )

    List<Expenses> userFinanceExpense(Number user);

}
