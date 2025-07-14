package com.ags.ags_simplebank_javaapi.dto;

// Criamos dentro do DTO, pois a pasta Model é o que usamos para Banco de Dados apenas
// Usamos 'record' do Java 17+ para criar DTOs imutáveis de forma concisa.
// Ele já cria construtor, getters, equals, hashCode e toString.
public record LoginDTO(String login, String password) {
}