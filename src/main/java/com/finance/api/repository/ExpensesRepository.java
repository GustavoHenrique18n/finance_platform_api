package com.finance.api.repository;

import com.finance.api.entity.Expenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpensesRepository extends JpaRepository<Expenses , Long> {}
