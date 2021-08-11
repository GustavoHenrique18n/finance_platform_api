package com.finance.api.repository;

import com.finance.api.entity.Incomes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomesRepository extends JpaRepository<Incomes, Long> {}
