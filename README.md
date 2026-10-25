# BoloSystem — Desktop e Web

Sistema de gerenciamento de uma loja de bolos, desenvolvido em Java, com interface desktop Swing e aplicação web utilizando Spring Boot, API REST, HTML, CSS e JavaScript. Utiliza MySQL para armazenamento dos dados.

## Tecnologias

- Java 21+
- Maven
- Spring Boot e API REST
- Swing
- HTML, CSS e JavaScript
- MySQL 8+

## Configuração

1. Abra o projeto no NetBeans selecionando a pasta que contém `pom.xml`.
2. Configure o banco de dados executando `database/bolosystem.sql` no MySQL Workbench.
3. Copie `application-local.properties.example` para `application-local.properties` e informe as credenciais do MySQL.
4. Não publique o arquivo com suas credenciais no GitHub.

## Executar a aplicação web

No terminal, dentro da pasta do projeto, execute:

```bash
mvnw.cmd spring-boot:run
```

Acesse: http://localhost:8080

## Testes

Execute os testes automatizados com:

```bash
mvnw.cmd test
```

## Funcionalidades

- Gerenciamento de produtos e clientes.
- Cadastro e processamento de pedidos.
- API REST para operações do sistema.
- Integração com banco de dados MySQL.
- Interface desktop e interface web.

## Observação

As aplicações desktop e web utilizam o mesmo banco de dados. Consulte a pasta `docs` para orientações adicionais de execução e configuração.
