package com.finance.api.entity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter @Setter
@Entity
@Table(name="expenses_types")
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude="id")
public class ExpensesType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id" , nullable = false)
    private Long id;

    @Column(name="is_active" , nullable = false)
    private Boolean isActive;

    @Column(name="name_type" , nullable = false)
    private String categorieName;

    @OneToMany(mappedBy = "categorieExpense")
    @JsonManagedReference(value="categorieExpense")
    private List<Expenses> expenses;

}
