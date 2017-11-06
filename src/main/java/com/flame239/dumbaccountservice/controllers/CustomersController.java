package com.flame239.dumbaccountservice.controllers;

import com.flame239.dumbaccountservice.controllers.exceptions.AccountNotFoundException;
import com.flame239.dumbaccountservice.controllers.exceptions.CustomerNotFoundException;
import com.flame239.dumbaccountservice.entities.Account;
import com.flame239.dumbaccountservice.entities.Customer;
import com.flame239.dumbaccountservice.repositories.AccountsRepository;
import com.flame239.dumbaccountservice.repositories.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomersController {

    private static String SELF_ID = "@me";

    private final CustomersRepository customersRepository;
    private final AccountsRepository accountsRepository;

    @Autowired
    CustomersController(CustomersRepository customersRepository, AccountsRepository accountsRepository) {
        this.customersRepository = customersRepository;
        this.accountsRepository = accountsRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<Customer> getCustomers() {
        return customersRepository.findAll();
    }

    @RequestMapping(value = "/{id:\\d+|@me}", method = RequestMethod.GET)
    Customer getCustomer(@PathVariable String id) {
        return getCustomerById(id);
    }

    @RequestMapping(value = "/{id:\\d+|@me}/accounts", method = RequestMethod.GET)
    Collection<Account> getAccounts(@PathVariable String id) {
        return accountsRepository.findByCustomerId(getCustomerById(id).getId());
    }

    @RequestMapping(value = "/{id:\\d+|@me}/accounts/{accId:\\d+}", method = RequestMethod.GET)
    Account getAccount(@PathVariable String id, @PathVariable String accId) {
        return getAccountById(accId);
    }

    @RequestMapping(value = "/{id:\\d+|@me}/accounts", method = RequestMethod.POST)
    ResponseEntity<?> createAccount(@PathVariable String id, @Valid @RequestBody Account acc) {
        Customer customer = getCustomerById(id);
        acc.setCustomer(customer);
        Account savedAcc = accountsRepository.save(acc);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedAcc.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @RequestMapping(value = "/{id:\\d+|@me}/accounts/{accId:\\d+}", method = RequestMethod.PUT)
    ResponseEntity<?> updateAccount(@PathVariable String id, @PathVariable String accId,
            @Valid @RequestBody Account acc) {
        Account existingAcc = getAccountById(accId);
        acc.setId(existingAcc.getId());
        acc.setCustomer(existingAcc.getCustomer());
        accountsRepository.save(acc);

        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id:\\d+|@me}/accounts/{accId:\\d+}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteAccount(@PathVariable String id, @PathVariable String accId) {
        Account existingAcc = getAccountById(accId);

        accountsRepository.delete(existingAcc);
        return ResponseEntity.noContent().build();
    }

    // following methods throw exception, if no corresponding entity found

    private Customer getCustomerById(String id) {
        if (SELF_ID.equals(id)) {
            String login = SecurityContextHolder.getContext().getAuthentication().getName();
            Optional<Customer> customerO = customersRepository.findByLogin(login);
            return customerO.orElseThrow(() -> new CustomerNotFoundException(id));
        } else {
            Optional<Customer> customerO = customersRepository.findById(Long.valueOf(id));
            return customerO.orElseThrow(() -> new CustomerNotFoundException(id));
        }
    }

    private Account getAccountById(String id) {
        Optional<Account> accountO = accountsRepository.findById(Long.valueOf(id));
        return accountO.orElseThrow(() -> new AccountNotFoundException(id));
    }
}
