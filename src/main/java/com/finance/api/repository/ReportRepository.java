package com.finance.api.repository;

import com.finance.api.entity.Incomes;
import com.finance.api.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report,Long> {

    @Query(value = "SELECT * , DATE_FORMAT(reports.start_date, \"%d/%c/%Y\") ,  DATE_FORMAT(reports.final_date,\"%d/%c/%Y\") FROM reports WHERE reports.user_id = ?1",
            nativeQuery = true
    )

    List<Report> findAllByUserId(Number user);
}
