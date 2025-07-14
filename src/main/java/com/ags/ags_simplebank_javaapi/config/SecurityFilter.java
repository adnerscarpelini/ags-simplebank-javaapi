package com.ags.ags_simplebank_javaapi.config;

import com.ags.ags_simplebank_javaapi.repository.AccountRepository;
import com.ags.ags_simplebank_javaapi.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component // Anotamos como um componente do Spring para podermos injetá-lo
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final AccountRepository accountRepository;

    public SecurityFilter(TokenService tokenService, AccountRepository accountRepository) {
        this.tokenService = tokenService;
        this.accountRepository = accountRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. Recupera o token do cabeçalho da requisição
        var token = this.recoverToken(request);

        if (token != null) {
            // 2. Se houver um token, valida-o
            var accountNumber = tokenService.validateToken(token);

            // 3. Busca o usuário (Account) no banco de dados
            UserDetails user = accountRepository.findByAccountNumber(accountNumber).orElse(null);

            if (user != null) {
                // 4. Se o usuário for encontrado, nós o autenticamos manualmente para esta requisição
                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                // 5. Salvamos o objeto de autenticação no contexto de segurança do Spring
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // 6. Continua a cadeia de filtros para que a requisição chegue ao controller
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        // O token vem no formato "Bearer <token>", então removemos o prefixo
        return authHeader.replace("Bearer ", "");
    }
}