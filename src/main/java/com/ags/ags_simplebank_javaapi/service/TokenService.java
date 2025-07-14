package com.ags.ags_simplebank_javaapi.service;

import com.ags.ags_simplebank_javaapi.model.Account; // Vamos usar Account como nosso "usuário"
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    // injetar um "segredo" automaticamente no nosso arquivo de propriedades.
    // Isso é muito mais seguro do que deixar a chave no código.
    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(UserDetails userDetails) {
        try {
            // Define o algoritmo de assinatura usando o segredo
            Algorithm algorithm = Algorithm.HMAC256(secret);

            String token = JWT.create()
                    .withIssuer("ags-simplebank-javaapi") // Quem emitiu o token
                    .withSubject(userDetails.getUsername()) // O "dono" do token
                    .withExpiresAt(generateExpirationDate()) // Data de expiração
                    .sign(algorithm); // Assina o token
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token", exception);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("ags-simplebank-javaapi")
                    .build()
                    .verify(token) // Verifica a assinatura e a validade
                    .getSubject(); // Se for válido, retorna o "dono" (o número da conta)
        } catch (JWTVerificationException exception) {
            return ""; // Retorna vazio se o token for inválido
        }
    }

    private Instant generateExpirationDate() {
        // Token expira em 2 horas
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}