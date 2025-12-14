# Projeto Integrador DNC - Sistema de Gestão Acadêmica

Este projeto foi desenvolvido como parte do curso de Programação em Java da **Escola DNC**, ministrado pelo professor **Gustavo Mota Macedo**. O objetivo é demonstrar a aplicação dos conceitos aprendidos, desde a Programação Orientada a Objetos (POO) até a construção de uma API REST completa com Spring Boot.

## 🎯 Objetivo

Criar uma API REST em Java com Spring Boot para gerenciar **alunos** e **professores** de uma instituição de ensino, com persistência em banco de dados e integração com uma API externa (ViaCEP).

## 🧩 Funcionalidades

- **Cadastro e Consulta (CRUD completo):**
  - Registrar e consultar alunos e professores.
  - Campos obrigatórios: nome, CPF (único), email, telefone, endereço.
- **Endpoints REST:**
  - `GET /api/alunos` – Lista todos os alunos
  - `GET /api/alunos/{id}` – Busca aluno por ID
  - `POST /api/alunos` – Cria novo aluno
  - `PUT /api/alunos/{id}` – Atualiza aluno
  - `DELETE /api/alunos/{id}` – Remove aluno
  - Mesmo padrão para `/api/professores`.
- **Integração com API Externa:**
  - Ao cadastrar ou atualizar um aluno/professor, buscar dados de endereço via **ViaCEP**.
  - Armazenar dados de endereço complementares (bairro, cidade, estado).
- **Tratamento de Exceções:**
  - Exceções personalizadas para CPF duplicado, ID não encontrado, etc.
  - Retorno de códigos HTTP apropriados (400, 404, 500).

## 🛠️ Tecnologias e Ferramentas

- **Java 17+**
- **Spring Boot 3.x**
- **Spring Web**
- **Spring Data JPA**
- **Lombok**
- **H2 Database (em memória)**
- **Jackson (para processamento de JSON)**
- **RestTemplate (para integração com API externa)**
- **Gradle (gerenciamento de dependências)**

## 📁 Estrutura do Projeto

```
src/
└── main/
    └── java/
        └── com/
            └── dnc/
                └── gustavomota/
                    └── projeto_integrador_dnc/
                        ├── ProjetoIntegradorDncApplication.java
                        ├── controllers/        # Camada de controle (Endpoints REST)
                        ├── dto/              # Objetos de Transferência de Dados (ex: resposta da API ViaCEP)
                        ├── exception/        # Exceções personalizadas
                        ├── models/           # Entidades JPA (Usuario, Aluno, Professor, Endereco)
                        ├── repositories/     # Repositórios Spring Data JPA
                        └── services/         # Camada de serviço (Lógica de Negócio)
```

## 🔧 Configuração e Execução

1.  Clone este repositório:
    ```bash
    git clone https://github.com/gustavomotamacedo/projeto-integrador-dnc.git
    ```
2.  Navegue até o diretório do projeto.
3.  Compile e execute usando o Gradle:
    ```bash
    ./gradlew bootRun
    ```
    A aplicação iniciará na porta `8080`.
4.  O console do H2 Database estará disponível em `http://localhost:8080/h2-console` (configure a URL de conexão conforme `application.properties`).

## 🧪 Testes

O projeto contempla a implementação de testes básicos manuais que são padronizados para serem importados no Postman com o JSON em `API Escola - Alunos e Professores.postman_collection.json`.

## 📚 Referências

- [Desbravando Java e Orientação a Objetos - Rodrigo Turini](https://www.casadocodigo.com.br/products/livro-java-orientacao-objetos)
- [Padrões de Projetos - Gamma, Erich et al.](https://www.casadocodigo.com.br/products/livro-padroes-projetos)
- [Spring Boot - Fernando Boaglio](https://www.casadocodigo.com.br/products/livro-spring-boot)

## 👨‍🏫 Professor

- **Gustavo Mota Macedo**

## 📂 Repositório do Código Fonte

- [https://github.com/gustavomotamacedo/projeto-integrador-dnc](https://github.com/gustavomotamacedo/projeto-integrador-dnc)

---
*Este projeto faz parte do currículo do curso de Java da DNC.*
```
