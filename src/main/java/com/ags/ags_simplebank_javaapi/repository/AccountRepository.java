package com.ags.ags_simplebank_javaapi.repository;

import com.ags.ags_simplebank_javaapi.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    // Método customizado para buscar uma conta pelo número dela
    Optional<Account> findByAccountNumber(String accountNumber);
}