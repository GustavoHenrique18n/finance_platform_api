package com.finance.api.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter @Setter
@Entity
@Table(name="users")
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude="id")
public class Users {
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
    private List<Incomes> incomes;

    @OneToMany(mappedBy = "user")
    private List<Expenses> expenses;

    @OneToMany(mappedBy = "user")
    private List<Report> reports;
}
