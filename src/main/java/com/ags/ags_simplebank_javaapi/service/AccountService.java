package com.ags.ags_simplebank_javaapi.service;

import com.ags.ags_simplebank_javaapi.model.Account;
import com.ags.ags_simplebank_javaapi.model.Transaction;
import com.ags.ags_simplebank_javaapi.model.TransactionType;
import com.ags.ags_simplebank_javaapi.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

//Service: onde fica a lógica de negócio (ex: validar saldo antes de criar uma transação).
//O service fica no meio do caminho do Repository (Acesso a dados) e Controllers da API

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account getAccountDetails(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new EntityNotFoundException("Account not found: " + accountNumber));
    }

    public List<Transaction> getAccountTransactions(String accountNumber) {
        Account account = getAccountDetails(accountNumber);
        return account.getTransactions();
    }

    @Transactional // Garante que toda a operação seja atômica (ou tudo funciona ou nada é salvo)
    public Transaction createTransaction(String accountNumber, Transaction transactionRequest) {
        Account account = getAccountDetails(accountNumber);

        BigDecimal amount = transactionRequest.getAmount();

        if (transactionRequest.getType() == TransactionType.DEBIT) {
            // Regra de negócio: não permitir saldo negativo
            if (account.getBalance().compareTo(amount) < 0) {
                throw new IllegalStateException("Insufficient funds for debit operation.");
            }
            account.setBalance(account.getBalance().subtract(amount));
        } else if (transactionRequest.getType() == TransactionType.CREDIT) {
            account.setBalance(account.getBalance().add(amount));
        }

        // Preenche os dados da transação antes de salvar
        transactionRequest.setAccount(account);
        transactionRequest.setTimestamp(LocalDateTime.now());

        // Ao salvar a conta, a transação também é salva por causa do 'cascade'
        accountRepository.save(account);

        // Retorna a última transação adicionada
        return account.getTransactions().get(account.getTransactions().size() - 1);
    }
}