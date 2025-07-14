# API Bancária Simples com Spring Boot (ags-simplebank-javaapi)

![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)
![Security](https://img.shields.io/badge/Security-JWT-blueviolet)
![Maven](https://img.shields.io/badge/Build-Maven-red)

API REST que simula operações de uma conta corrente, construída com Java e Spring Boot como parte de um projeto de estudo para aprofundar conhecimentos e como preparação para uma entrevista técnica.

## 📜 Sobre o Projeto

Este projeto foi desenvolvido com o objetivo de aplicar e demonstrar na prática conceitos essenciais do desenvolvimento backend com o ecossistema Spring. A API implementa um CRUD básico para contas e transações, com uma camada de segurança moderna utilizando JSON Web Tokens (JWT).

O foco foi construir uma aplicação robusta, seguindo boas práticas de arquitetura em camadas, injeção de dependência, e design de APIs RESTful.

## ✨ Demonstração da API

O GIF abaixo demonstra o fluxo de autenticação para obter um token JWT e, em seguida, o uso desse token para acessar um endpoint protegido e realizar uma consulta.

![Demonstração da API](https://raw.githubusercontent.com/adnerscarpelini/ags-simplebank-javaapi/master/images/demo.gif)

## 🚀 Tecnologias Utilizadas

As principais tecnologias e bibliotecas usadas neste projeto foram:

- **Java 17**: Versão da linguagem Java.
- **Spring Boot**: Framework principal para criação da aplicação de forma rápida e configurável.
- **Spring Web**: Para a construção dos endpoints RESTful.
- **Spring Security**: Para implementar o fluxo de autenticação e autorização.
- **Spring Data JPA**: Para a camada de persistência de dados de forma abstraída.
- **H2 Database**: Banco de dados em memória, ideal para desenvolvimento e testes rápidos.
- **Maven**: Ferramenta de gerenciamento de dependências e build do projeto.
- **Lombok**: Para reduzir código boilerplate (getters, setters, construtores).
- **java-jwt (Auth0)**: Biblioteca para a criação e validação dos tokens JWT.

### 🛠️ Estrutura do Projeto

O código foi organizado em pacotes para separar as responsabilidades de cada parte da aplicação:

- **config**: Contém todas as classes de configuração do Spring, como a de segurança (`SecurityConfig`, `SecurityFilter`) e o serviço de autenticação.
- **controller**: Camada da API. Responsável por expor os endpoints REST e receber as requisições dos clientes.
- **dto**: Data Transfer Objects. São classes simples usadas para transportar dados entre o cliente e a API de forma segura e otimizada.
- **model**: As entidades JPA que representam as tabelas do banco de dados, como `Account` e `Transaction`.
- **repository**: Interfaces do Spring Data JPA que definem os métodos de acesso ao banco de dados.
- **service**: Onde fica a lógica de negócio da aplicação, orquestrando as operações entre os controllers e os repositórios.

## ⚙️ Como Executar

**Pré-requisitos:**

- Java 17 ou superior
- Maven 3.8 ou superior

```bash
# 1. Clone o repositório
git clone [<https://github.com/adnerscarpelini/ags-simplebank-javaapi.git>](<https://github.com/adnerscarpelini/ags-simplebank-javaapi.git>)

# 2. Navegue até a pasta do projeto
cd ags-simplebank-javaapi

# 3. Execute a aplicação com o Maven
mvn spring-boot:run
```

Alternativamente, você pode importar o projeto em sua IDE (como o IntelliJ) e executar a classe principal `AgsSimplebankJavaapiApplication`.

A API estará disponível em `http://localhost:8080`.

## 🧪 Endpoints da API

A aplicação inicializa com uma conta padrão para facilitar os testes:

- **Número da Conta (login):** `12345-6`
- **Senha:** `S&nh@`

---

### Autenticação

### Obter Token de Autenticação

- **Método:** `POST`
- **URL:** `/login`
- **Autenticação:** Nenhuma
- **Corpo da Requisição (Body):**
    
    ```json
    {
      "login": "12345-6",
      "password": "S&nh@"
    }
    ```
    
- **Resposta de Sucesso (200 OK):**JSON
    
    ```json
    {
      "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
    }
    ```
    

---

### Endpoints Protegidos

*Para todas as requisições abaixo, é necessário enviar o token JWT no cabeçalho `Authorization`.*

- **Header:** `Authorization: Bearer <seu_token>`

### Consultar Dados da Conta

- **Método:** `GET`
- **URL:** `/accounts/12345-6`

### Consultar Lançamentos da Conta

- **Método:** `GET`
- **URL:** `/accounts/12345-6/transactions`

### Realizar um Lançamento (Transação)

- **Método:** `POST`
- **URL:** `/accounts/12345-6/transactions`
- **Corpo da Requisição (Body):**JSON
    
    `{
      "type": "CREDIT",
      "amount": 500.00,
      "description": "Depósito via PIX"
    }`
    

## 🏁 Começando um Projeto como Este

Este projeto foi gerado utilizando o [**Spring Initializr**](https://start.spring.io/), uma ferramenta fantástica que cria a estrutura base de um projeto Spring Boot.

As dependências selecionadas no início foram:

- **Spring Web**
- **Spring Data JPA**
- **H2 Database**
- **Spring Security**
- **Lombok**

A dependência para JWT foi adicionada manualmente ao arquivo `pom.xml`.
