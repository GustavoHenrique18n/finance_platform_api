package com.finance.api.repository;

import com.finance.api.entity.Incomes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomesRepository extends JpaRepository<Incomes, Long> {

    @Query(value = "SELECT * FROM incomes WHERE incomes.user_id = ?1",
           nativeQuery = true
    )

    List<Incomes> userFinanceIncome(Number user);


}
