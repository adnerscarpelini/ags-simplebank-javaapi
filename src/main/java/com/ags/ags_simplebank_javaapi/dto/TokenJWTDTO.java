package com.ags.ags_simplebank_javaapi.dto;

// Criamos dentro do DTO, pois a pasta Model é o que usamos para Banco de Dados apenas

/**
 * DTO (Data Transfer Object) para encapsular o token JWT na resposta do login.
 * Usamos 'record' para uma declaração concisa e imutável.
 */
public record TokenJWTDTO(String token) {
}