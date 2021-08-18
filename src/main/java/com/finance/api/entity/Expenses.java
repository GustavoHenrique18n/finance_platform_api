package com.finance.api.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter @Setter
@Entity
@Table(name="expenses")
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude="id")
public class Expenses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id" , nullable = false)
    private Long id;

    @Column(name="name" , nullable = false)
    private String nameExpense;

    @Column(name="preview_date")
    @JsonFormat(pattern = "dd/MM/yyyy" , shape = JsonFormat.Shape.STRING)
    private LocalDate previewDate;

    @Column(name="preview_value")
    private Integer previewValue;

    @Column(name="confirmed_date")
    @JsonFormat(pattern = "dd/MM/yyyy" , shape = JsonFormat.Shape.STRING)
    private LocalDate confirmedDate;

    @Column(name="confirmed_value" )
    private Integer confirmedValue;

    @ManyToOne
    @JoinColumn(name = "expense_type_id" , nullable = false)
    private ExpensesType categorieExpense;

    @ManyToOne
    @JoinColumn(name = "user_id" , nullable = false)
    private Users user;

}
