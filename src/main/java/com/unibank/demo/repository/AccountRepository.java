package com.unibank.demo.repository;

import com.unibank.demo.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findAccountByAccountNumber(Long accountNumber);

    List<Account> findAccountsByUserPin(String pin);
}
