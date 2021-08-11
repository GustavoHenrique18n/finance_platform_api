package com.finance.api.entity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter @Setter
@Entity
@Table(name="incomes_types")
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude="id")
public class IncomesType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id" , nullable = false)
    private Long id;

    @Column(name="is_active" , nullable = false)
    private Boolean isActive;

    @Column(name="name_type" , nullable = false)
    private String categorieName;

    @OneToMany(mappedBy = "categorieIncome")
    private List<Incomes> incomes;
}
