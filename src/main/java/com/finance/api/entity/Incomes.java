package com.finance.api.entity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter @Setter
@Entity
@Table(name="incomes")
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude="id")
public class Incomes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id" , nullable = false)
    private Long id;

    @Column(name="name" , nullable = false)
    private String nameIncome;

    @Column(name="preview_date")
    private LocalDate previewDate;

    @Column(name="preview_value")
    private Integer previewValue;

    @Column(name="confirmed_date")
    private LocalDate confirmedDate;

    @Column(name="confirmed_value")
    private Integer confirmedValue;

    @ManyToOne
    @JoinColumn(name = "income_type_id" , nullable = false)
    private IncomesType categorieIncome;

    @ManyToOne
    @JoinColumn(name = "user_id" , nullable = false)
    private Users user;

}
