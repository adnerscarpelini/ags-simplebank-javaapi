package com.ags.ags_simplebank_javaapi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false) // Garante que o número da conta é único
    private String accountNumber;

    @Column(nullable = false)
    private String ownerName;

    @Column(nullable = false)
    private BigDecimal balance;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference // Evita recursão infinita ao serializar para JSON
    private List<Transaction> transactions;
}