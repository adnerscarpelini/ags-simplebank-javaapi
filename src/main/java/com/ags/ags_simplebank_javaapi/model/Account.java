// Account.java
package com.ags.ags_simplebank_javaapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Entity
@Data
public class Account implements UserDetails { // Implemente a interface UserDetails

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String accountNumber;

    @Column(nullable = false)
    private String ownerName;

    @Column(nullable = false)
    private BigDecimal balance;

    @JsonIgnore // Ignoramos a senha na serialização para não expô-la na API
    private String password;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Transaction> transactions = new java.util.ArrayList<>();


    // MÉTODOS DA INTERFACE USERDETAILS
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Define as "permissões/roles" do usuário. Para este exemplo, todos são "ROLE_USER".
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        // O "username" para o Spring Security será o número da conta.
        return this.accountNumber;
    }

    // Métodos que indicam o estado da conta. Para nosso exemplo, todos retornam true.
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}