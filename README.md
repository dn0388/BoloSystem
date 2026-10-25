# BoloSystem — Sistema de Gerenciamento de Confeitaria

## Descrição do projeto

O BoloSystem é um sistema desenvolvido para auxiliar no gerenciamento de uma confeitaria, permitindo o cadastro de clientes e produtos, além do registro e gerenciamento de pedidos. O projeto possui uma aplicação desktop desenvolvida em Java Swing e uma aplicação web com Spring Boot, utilizando banco de dados MySQL para persistência das informações.

## Tecnologias utilizadas

- Java 21
- Maven
- Spring Boot
- Java Swing
- MySQL 8
- HTML, CSS e JavaScript
- JUnit 5

## Organização do projeto

O código está organizado em pacotes responsáveis pelas diferentes funções do sistema, incluindo modelos, acesso a dados (DAO), serviços de negócio, interfaces gráficas e controladores da aplicação web. Essa separação facilita a manutenção e contribui para a aplicação do princípio SOLID de Responsabilidade Única.

## Como executar o projeto

1. Instale o JDK 21, o Maven e o MySQL 8.
2. Abra o projeto no NetBeans ou em uma IDE compatível com projetos Maven.
3. Configure o banco de dados MySQL de acordo com as configurações utilizadas pela aplicação.
4. Verifique as credenciais e os parâmetros de conexão antes de executar o sistema.
5. Aguarde a resolução das dependências Maven e execute a classe principal correspondente à aplicação desejada.

A execução depende da configuração correta do ambiente e do banco de dados. Caso necessário, ajuste as configurações locais antes de iniciar a aplicação.

## Testes

O projeto contém testes automatizados com JUnit 5 e verificações de serviços e validações. Os relatórios de execução incluídos no projeto registram 12 testes executados, sem falhas ou erros no resultado apresentado.

## Princípios e padrões de projeto

Durante a refatoração, foram organizadas responsabilidades entre as camadas de interface, serviços e acesso a dados. O princípio SOLID de Responsabilidade Única orienta essa separação, enquanto o padrão DAO é utilizado para concentrar operações de acesso ao banco de dados.

## Repositório

Código-fonte disponível em:

https://github.com/dn0388/BoloSystem

## Considerações finais

O BoloSystem reúne funcionalidades desktop e web para o gerenciamento de uma confeitaria. A organização das classes, a separação das responsabilidades e a realização de testes contribuem para a manutenção e a evolução do sistema.
