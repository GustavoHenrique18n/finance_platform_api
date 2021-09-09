package com.finance.api.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.finance.api.LoggedUser;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter @Setter
@Entity
@Table(name="users")
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude="id")
public class Users  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id" , nullable = false)
    private Long id;

    @Column(name="name" , nullable = false)
    private String name;

    @Column(name="name_company")
    private String nameCompany;

    @Column(name="password" , nullable = false)
    private String password;

    @Column(name="email" , nullable = false , unique = true)
    private String email;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference(value = "userIncome")
    private List<Incomes> incomes;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference(value="userExpense")
    private List<Expenses> expenses;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference(value="userReports")
    private List<Report> reports;

}
