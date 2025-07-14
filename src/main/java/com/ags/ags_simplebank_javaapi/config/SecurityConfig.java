package com.ags.ags_simplebank_javaapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Este Bean configura as regras de segurança HTTP.
     *
     * @param http o objeto HttpSecurity para configurar.
     * @return o filtro de segurança construído.
     * @throws Exception em caso de erro na configuração.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Inicia a configuração das regras de autorização de requisições
                .authorizeHttpRequests(authorize -> authorize
                        // Exige que qualquer requisição (anyRequest) seja autenticada (authenticated)
                        .anyRequest().authenticated()
                )
                // Habilita a autenticação HTTP Basic (envio de usuário e senha no cabeçalho)
                .httpBasic(Customizer.withDefaults())
                // Desabilita a proteção CSRF, que é comum para APIs REST stateless
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

    /**
     * Este Bean define os usuários que podem acessar a aplicação.
     * Para este projeto de prática, estamos criando um usuário em memória.
     *
     * @return um serviço com os detalhes do usuário.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        // Cria um usuário com nome "adner.scarpelini", senha "123456" e perfil "USER"
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("adner.scarpelini")
                .password("123456")
                .roles("USER")
                .build();

        // Retorna um gerenciador que mantém os usuários em memória.
        return new InMemoryUserDetailsManager(user);
    }
}