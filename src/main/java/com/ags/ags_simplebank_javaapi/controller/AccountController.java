package com.ags.ags_simplebank_javaapi.controller;

import com.ags.ags_simplebank_javaapi.model.Account;
import com.ags.ags_simplebank_javaapi.model.Transaction;
import com.ags.ags_simplebank_javaapi.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Classe de controler: responsável por apresentar os endpoints na API
//Chama o Services (Regra de Negócio) que chamará o Repository para acessar o banco de dados.
@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // GET para consultar os dados de uma conta específica
    @GetMapping("/{accountNumber}")
    public Account getAccountDetails(@PathVariable String accountNumber) {
        return accountService.getAccountDetails(accountNumber);
    }

    // GET para consultar os lançamentos de uma conta
    @GetMapping("/{accountNumber}/transactions")
    public List<Transaction> getAccountTransactions(@PathVariable String accountNumber) {
        return accountService.getAccountTransactions(accountNumber);
    }

    // POST para fazer um lançamento (transação)
    @PostMapping("/{accountNumber}/transactions")
    public ResponseEntity<Transaction> createTransaction(@PathVariable String accountNumber, @RequestBody Transaction transaction) {
        Transaction createdTransaction = accountService.createTransaction(accountNumber, transaction);
        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
    }
}