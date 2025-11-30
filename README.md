# Automanager

Microserviço Spring Boot para gerenciamento de clientes e usuários, com autenticação JWT.

- Aplicação: [`com.autobots.automanager.AutomanagerApplication`](atviv-autobots-microservico-spring/automanager/src/main/java/com/autobots/automanager/AutomanagerApplication.java)
- Configurações de segurança: [`com.autobots.automanager.configuracao.Seguranca`](atviv-autobots-microservico-spring/automanager/src/main/java/com/autobots/automanager/configuracao/Seguranca.java), [`com.autobots.automanager.configuracao.SenhaConfig`](atviv-autobots-microservico-spring/automanager/src/main/java/com/autobots/automanager/configuracao/SenhaConfig.java)
- JWT: [`com.autobots.automanager.jwt.GeradorJwt`](atviv-autobots-microservico-spring/automanager/src/main/java/com/autobots/automanager/jwt/GeradorJwt.java), [`com.autobots.automanager.jwt.ProvedorJwt`](atviv-autobots-microservico-spring/automanager/src/main/java/com/autobots/automanager/jwt/ProvedorJwt.java), [`com.autobots.automanager.jwt.ValidadorJwt`](atviv-autobots-microservico-spring/automanager/src/main/java/com/autobots/automanager/jwt/ValidadorJwt.java)
- Adaptadores de usuário: [`com.autobots.automanager.adaptadores.UserDetailsServiceImpl`](atviv-autobots-microservico-spring/automanager/src/main/java/com/autobots/automanager/adaptadores/UserDetailsServiceImpl.java), [`com.autobots.automanager.adaptadores.UserDetailsImpl`](atviv-autobots-microservico-spring/automanager/src/main/java/com/autobots/automanager/adaptadores/UserDetailsImpl.java)
- Controladores: [`com.autobots.automanager.controles.AuthController`](atviv-autobots-microservico-spring/automanager/src/main/java/com/autobots/automanager/controles/AuthController.java), [`com.autobots.automanager.controles.ControleCliente`](atviv-autobots-microservico-spring/automanager/src/main/java/com/autobots/automanager/controles/ControleCliente.java), [`com.autobots.automanager.controles.ControleUsuario`](atviv-autobots-microservico-spring/automanager/src/main/java/com/autobots/automanager/controles/ControleUsuario.java), [`com.autobots.automanager.controles.PingController`](atviv-autobots-microservico-spring/automanager/src/main/java/com/autobots/automanager/controles/PingController.java)
- Entidades: [`com.autobots.automanager.entidades.Cliente`](atviv-autobots-microservico-spring/automanager/src/main/java/com/autobots/automanager/entidades/Cliente.java), [`com.autobots.automanager.entidades.Usuario`](atviv-autobots-microservico-spring/automanager/src/main/java/com/autobots/automanager/entidades/Usuario.java), [`com.autobots.automanager.entidades.Credencial`](atviv-autobots-microservico-spring/automanager/src/main/java/com/autobots/automanager/entidades/Credencial.java)
- Repositórios: [`com.autobots.automanager.repositorios.RepositorioCliente`](atviv-autobots-microservico-spring/automanager/src/main/java/com/autobots/automanager/repositorios/RepositorioCliente.java), [`com.autobots.automanager.repositorios.RepositorioUsuario`](atviv-autobots-microservico-spring/automanager/src/main/java/com/autobots/automanager/repositorios/RepositorioUsuario.java)
- Filtros: [`com.autobots.automanager.filtros.AutenticadorJwt`](atviv-autobots-microservico-spring/automanager/src/main/java/com/autobots/automanager/filtros/AutenticadorJwt.java), [`com.autobots.automanager.filtros.Autorizador`](atviv-autobots-microservico-spring/automanager/src/main/java/com/autobots/automanager/filtros/Autorizador.java), [`com.autobots.automanager.filtros.ValidadorCabecalho`](atviv-autobots-microservico-spring/automanager/src/main/java/com/autobots/automanager/filtros/ValidadorCabecalho.java)

## Requisitos
- Java 17
- Maven 3.8+
- Spring Boot 2.6.3 (via [pom.xml](atviv-autobots-microservico-spring/automanager/pom.xml))

## Configuração
- Propriedades: [src/main/resources/application.properties](atviv-autobots-microservico-spring/automanager/src/main/resources/application.properties)
- Banco H2 em memória (dev), já configurado no [pom.xml](atviv-autobots-microservico-spring/automanager/pom.xml).

## Build
- Terminal do VS Code:
  - `./mvnw clean package` (Linux/Mac)
  - `mvnw.cmd clean package` (Windows)

Artefato: `target/automanager-0.0.1-SNAPSHOT.jar.original`

## Execução
- VS Code Output/Terminal:
  - `./mvnw spring-boot:run` ou `mvnw.cmd spring-boot:run`
- Aplicação principal: [`com.autobots.automanager.AutomanagerApplication`](atviv-autobots-microservico-spring/automanager/src/main/java/com/autobots/automanager/AutomanagerApplication.java)

## Autenticação
- Fluxo JWT:
  - Login em [`AuthController`](atviv-autobots-microservico-spring/automanager/src/main/java/com/autobots/automanager/controles/AuthController.java) retorna token.
  - Filtros [`AutenticadorJwt`](atviv-autobots-microservico-spring/automanager/src/main/java/com/autobots/automanager/filtros/AutenticadorJwt.java) e [`Autorizador`](atviv-autobots-microservico-spring/automanager/src/main/java/com/autobots/automanager/filtros/Autorizador.java) validam requisições.
  - Usar header `Authorization: Bearer <token>`.

## Endpoints
- Saúde: [`PingController`](atviv-autobots-microservico-spring/automanager/src/main/java/com/autobots/automanager/controles/PingController.java) — verificação simples.
- Clientes: [`ControleCliente`](atviv-autobots-microservico-spring/automanager/src/main/java/com/autobots/automanager/controles/ControleCliente.java) — CRUD de clientes.
- Usuários: [`ControleUsuario`](atviv-autobots-microservico-spring/automanager/src/main/java/com/autobots/automanager/controles/ControleUsuario.java) — gerenciamento de usuários.
- Autenticação: [`AuthController`](atviv-autobots-microservico-spring/automanager/src/main/java/com/autobots/automanager/controles/AuthController.java) — emissão de JWT.

Observação: verificar paths e métodos diretamente nos controladores para detalhes de rotas.

## Testes
- Executar: `./mvnw test` ou `mvnw.cmd test`
- Classe de testes: [`com.autobots.automanager.AutomanagerApplicationTests`](atviv-autobots-microservico-spring/automanager/src/test/java/com/autobots/automanager/AutomanagerApplicationTests.java)

## Estrutura
- Código fonte: [src/main/java](atviv-autobots-microservico-spring/automanager/src/main/java)
- Recursos: [src/main/resources](atviv-autobots-microservico-spring/automanager/src/main/resources)
- Build: [target](atviv-autobots-microservico-spring/automanager/target)
