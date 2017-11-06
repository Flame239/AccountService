package com.flame239.dumbaccountservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flame239.dumbaccountservice.entities.converters.CustomerStatusConverter;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class Customer {
    protected Customer() {
    }

    @JsonIgnore
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Account> accounts;

    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private long balance;
    @Convert(converter = CustomerStatusConverter.class)
    @Column(nullable = false)
    private CustomerStatus status;
    @Column(unique = true, nullable = false)
    private String login;
    @Column(nullable = false)
    private String password;
}
