package com.ags.ags_simplebank_javaapi.repository;

import com.ags.ags_simplebank_javaapi.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Repository: acesso ao banco de dados (sem lógica de negócio)./
//No Spring, o Repository (junto com o JPA) é o que mais se aproxima do Entity Framework (EF Core) no .NET.
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}