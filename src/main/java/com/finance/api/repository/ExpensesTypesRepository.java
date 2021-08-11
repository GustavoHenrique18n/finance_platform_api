package com.finance.api.repository;

import com.finance.api.entity.ExpensesType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpensesTypesRepository extends JpaRepository<ExpensesType,Long> {}
