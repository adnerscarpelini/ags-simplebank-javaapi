package com.ags.ags_simplebank_javaapi.config;

import com.ags.ags_simplebank_javaapi.repository.AccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthenticationService implements UserDetailsService {

    private final AccountRepository accountRepository;
    // O PasswordEncoder não é mais necessário aqui. Podemos removê-lo.

    public AuthenticationService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var account = accountRepository.findByAccountNumber(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // retorna os dados do usuário que está salvo no nosso banco de dados
        //simplesmente retornamos a conta, pois ela já é um UserDetails
        return accountRepository.findByAccountNumber(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

    }
}