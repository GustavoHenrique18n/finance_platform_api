package com.finance.api.repository;

import com.finance.api.entity.IncomesType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomesTypeRepository extends JpaRepository<IncomesType,Long> { }
