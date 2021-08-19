package com.finance.api.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private String nameExpense;

    @Column(name="get_with_expenses" , nullable = false)
    private LocalDate previewDate;

    @Column(name="name_report")
    private Integer previewValue;

    @Column(name="start_date" , nullable = false)
    private LocalDate startDate;

    @Column(name = "final_date" , nullable = false)
    private LocalDate finalDate;

    @ManyToOne
    @JoinColumn(name = "user_id" , nullable = false)
    @JsonBackReference(value="userReports")
    private Users user;

}
