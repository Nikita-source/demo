package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "customers")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId(mutable = true)
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phonenumber;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String secondname;

    @Column(nullable = false)
    private String lastname;

    @OneToMany(mappedBy = "customer")
    private Set<OrderEntity> orders;
}
