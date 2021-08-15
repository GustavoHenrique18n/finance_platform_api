package com.finance.api.repository;

import com.finance.api.entity.Incomes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomesRepository extends JpaRepository<Incomes, Long> {

    @Query(value = "SELECT * , DATE_FORMAT(incomes.preview_date, \"%d/%c/%Y\") ,  DATE_FORMAT(incomes.confirmed_date, \"%Y\") FROM incomes WHERE incomes.user_id = ?1",
           nativeQuery = true
    )

    List<Incomes> userFinanceIncome(Number user);

}
