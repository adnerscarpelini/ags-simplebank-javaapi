package com.ags.ags_simplebank_javaapi.repository;

import com.ags.ags_simplebank_javaapi.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}