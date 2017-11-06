package com.flame239.dumbaccountservice.repositories;

import com.flame239.dumbaccountservice.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface AccountsRepository extends JpaRepository<Account, Long> {
    Optional<Account> findById(Long id);
    Collection<Account> findByCustomerId(long id);
}
