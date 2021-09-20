package com.finance.api.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter @Setter
@Entity
@Table(name="reports")
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude="id")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id" , nullable = false)
    private Long id;

    @Column(name="get_with_incomes" , nullable = false)
    private Boolean withIncomes;

    @Column(name="get_with_expenses" , nullable = false)
    private Boolean withExpenses;

    @Column(name="name_report")
    private String nameReport;

    @Column(name="start_date" , nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy" , shape = JsonFormat.Shape.STRING)
    private LocalDate startDate;

    @Column(name = "final_date" , nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy" , shape = JsonFormat.Shape.STRING)
    private LocalDate finalDate;

    @ManyToOne
    @JoinColumn(name = "user_id" , nullable = false)
    @JsonBackReference(value="userReports")
    private Users user;

}
