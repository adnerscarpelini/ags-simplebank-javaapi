package com.ags.ags_simplebank_javaapi;

import com.ags.ags_simplebank_javaapi.model.Account;
import com.ags.ags_simplebank_javaapi.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

@SpringBootApplication
public class AgsSimplebankJavaapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgsSimplebankJavaapiApplication.class, args);
    }

    // Este código será executado assim que a aplicação iniciar
    // Crio uma conta padrão para a instância de testes, assim não preciso criar um cadastro de usuários....
    @Bean
    CommandLineRunner initDatabase(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            Account account = new Account();
            account.setAccountNumber("12345-6");
            account.setOwnerName("Adner Giovanni Scarpelini");
            account.setBalance(new BigDecimal("1000.00"));
            // SALVANDO A SENHA JÁ CODIFICADA
            account.setPassword(passwordEncoder.encode("S&nh@"));
            accountRepository.save(account);
        };
    }
}