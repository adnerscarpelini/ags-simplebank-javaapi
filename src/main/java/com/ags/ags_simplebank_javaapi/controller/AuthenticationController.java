package com.ags.ags_simplebank_javaapi.controller;

import com.ags.ags_simplebank_javaapi.dto.LoginDTO;
import com.ags.ags_simplebank_javaapi.dto.TokenJWTDTO;
import com.ags.ags_simplebank_javaapi.model.Account;
import com.ags.ags_simplebank_javaapi.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    private final AuthenticationManager manager;
    private final TokenService tokenService;

    public AuthenticationController(AuthenticationManager manager, TokenService tokenService) {
        this.manager = manager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<TokenJWTDTO> login(@RequestBody LoginDTO loginDTO) {
        // 1. Cria um objeto de autenticação com as credenciais recebidas
        var authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.login(), loginDTO.password());

        // 2. O Spring Security usa o AuthenticationManager para validar as credenciais
        // Ele vai chamar nosso UserDetailsService (que definimos na SecurityConfig)
        Authentication authentication = manager.authenticate(authenticationToken);

        // 3. Se a autenticação for bem-sucedida, o usuário autenticado está em 'authentication.getPrincipal()'
        // Nós geramos o token JWT para este usuário
        var userDetails = (
                UserDetails) authentication.getPrincipal();
        String tokenJWT = tokenService.generateToken(userDetails);

        // 4. Retornamos o token em um DTO
        return ResponseEntity.ok(new TokenJWTDTO(tokenJWT));
    }
}