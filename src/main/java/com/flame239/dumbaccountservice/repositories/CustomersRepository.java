package com.flame239.dumbaccountservice.repositories;

import com.flame239.dumbaccountservice.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomersRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findById(Long id);
    Optional<Customer> findByLogin(String login);
}
