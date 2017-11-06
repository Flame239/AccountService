package com.flame239.dumbaccountservice.security;

import com.flame239.dumbaccountservice.entities.Account;
import com.flame239.dumbaccountservice.entities.Customer;
import com.flame239.dumbaccountservice.repositories.AccountsRepository;
import com.flame239.dumbaccountservice.repositories.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("securityService")
public class SecurityService {
    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    private AccountsRepository accountsRepository;

    // grants permission to resource owner and admin

    public boolean checkPermission(Authentication authentication, String id) {
        Optional<Customer> customerFromAuth = customersRepository.findByLogin(authentication.getName());
        return customerFromAuth.filter(customer -> isAdmin(customer) ||
                id.equals(Long.toString(customer.getId()))).isPresent();
    }

    public boolean checkPermission(Authentication authentication, String id, String accId) {
        Optional<Customer> customerFromAuthO = customersRepository.findByLogin(authentication.getName());
        Optional<Account> accountO = accountsRepository.findById(Long.valueOf(accId));
        if (!accountO.isPresent() || !customerFromAuthO.isPresent()) {
            return false;
        }
        Customer customer = customerFromAuthO.get();

        return isAdmin(customer) || (id.equals(Long.toString(customer.getId())) && belongsTo(customer, accountO.get()));
    }

    private static boolean isAdmin(Customer customer) {
        return customer.getLogin().equals("admin");
    }

    private static boolean belongsTo(Customer customer, Account acc) {
        return customer.getId() == acc.getCustomer().getId();
    }
}
