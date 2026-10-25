# BoloSystem — Desktop Swing + Web Spring Boot

Projeto consolidado a partir dos arquivos BoloSystem disponibilizados nesta conversa. Mantém as classes de modelo, DAO, serviço e telas Swing recuperadas, e adiciona camada web Spring Boot, API REST e front-end HTML/CSS/JavaScript no mesmo projeto Maven.

## Requisitos
- JDK 21 ou superior (também pode ser aberto com JDK 24).
- NetBeans com suporte a Maven.
- MySQL 8+ em execução.

## Abrir no NetBeans
1. Extraia o ZIP.
2. Use **File > Open Project** e selecione a pasta que contém `pom.xml`.
3. Aguarde o Maven carregar as dependências. Não abra a pasta `src` como projeto.

## Configurar MySQL
1. Abra o MySQL Workbench e execute `database/bolosystem.sql`.
2. Copie `src/main/resources/application-local.properties.example` para `src/main/resources/application-local.properties`.
3. No arquivo copiado, informe usuário e senha do MySQL. Ajuste URL/porta se necessário.
4. Não envie `application-local.properties` ao GitHub; ele já está protegido pelo `.gitignore`.

O desktop JDBC e o backend web leem a mesma configuração local. As variáveis `DB_URL`, `DB_USER` e `DB_PASSWORD` têm prioridade sobre o arquivo local.

## Executar a aplicação web
Antes, siga `docs/COMO-EXECUTAR-LOCALHOST.md` para configurar as credenciais do MySQL.

No terminal aberto na pasta do projeto, no Windows:
```bat
mvnw.cmd spring-boot:run
```
Depois abra **http://localhost:8080**. O front-end chama a API REST no mesmo servidor.

## Testes
```bat
mvnw.cmd test
```
`PedidoServiceTest` cobre regras de validação e cálculo sem precisar de banco. Execute os testes no seu computador para confirmar o ambiente local.

## Endpoints
- `GET /api/produtos`, `GET /api/produtos/{id}`, `POST /api/produtos`, `PUT /api/produtos/{id}`, `DELETE /api/produtos/{id}`
- `GET /api/clientes`, `POST /api/clientes`, `PUT /api/clientes/{id}`, `DELETE /api/clientes/{id}`
- `POST /api/pedidos`

## Observações
O desktop e a web usam o mesmo banco e as classes DAO compartilhadas, mas são interfaces distintas. Se o banco `bolosystem` já existir, compare o esquema antes de executar o SQL; `CREATE TABLE IF NOT EXISTS` não migra tabelas antigas. O login legado armazena/compara senha em texto puro e não deve ser exposto em produção sem refatoração de segurança.
