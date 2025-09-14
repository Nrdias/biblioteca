# Sistema de Biblioteca - API REST

## Descrição
API REST para gerenciamento de biblioteca com sistema de autenticação e autorização em três níveis:
- **T (Todos)**: Endpoints públicos acessíveis a qualquer usuário
- **A (Autenticado)**: Endpoints que requerem autenticação básica
- **B (Bibliotecário)**: Endpoints exclusivos para usuários com perfil de bibliotecário

## Tecnologias Utilizadas
- Spring Boot 3.5.5
- Spring Security
- Spring Data JDBC
- PostgreSQL
- Maven
- Java 21
- dotenv-java (para carregamento de arquivos .env)

## Endpoints Disponíveis

### 📚 Endpoints Públicos (T - Todos)
- `GET /api/welcome` - Mensagem de boas vindas e lista de endpoints
- `POST /api/usuarios/cadastrar` - Cadastrar novo usuário

### 🔐 Endpoints Autenticados (A - Usuários Logados)
- `GET /api/livros` - Consultar todos os livros do acervo
- `GET /api/livros/{id}` - Consultar livro por ID
- `GET /api/livros/autor/{autor}` - Consultar livros por autor
- `GET /api/livros/ano/{ano}` - Consultar livros por ano

### 👥 Endpoints de Bibliotecário (B - Apenas Bibliotecários)
- `POST /api/livros/cadastrar` - Cadastrar novo livro

## Configuração e Execução

### 1. Configuração do Banco de Dados
O projeto usa variáveis de ambiente para configuração, permitindo flexibilidade entre diferentes ambientes.

#### Configuração com arquivo .env
Crie um arquivo `.env` na raiz do projeto com suas configurações:

```env
# Configuração do Banco de Dados
DATABASE_URL=jdbc:postgresql://seu-host:5432/sua-database
DATABASE_USERNAME=seu-usuario
DATABASE_PASSWORD=sua-senha
DATABASE_DRIVER=org.postgresql.Driver

# Configuração da Aplicação
APP_NAME=biblioteca
SERVER_PORT=8080

# Configuração JPA
JPA_DDL_AUTO=none
JPA_SHOW_SQL=false

# Configuração de Log
LOG_LEVEL_SECURITY=DEBUG
LOG_LEVEL_APP=INFO
```

> **💡 Dica:** O arquivo `.env` é automaticamente carregado na inicialização da aplicação. Se não existir, a aplicação usará as configurações padrão.

#### Configuração sem arquivo .env
Alternativamente, defina as variáveis de ambiente diretamente no sistema:

```bash
export DATABASE_URL="jdbc:postgresql://localhost:5432/biblioteca"
export DATABASE_USERNAME="postgres"
export DATABASE_PASSWORD="password"
```

### 2. Executar a Aplicação

#### Primeira execução (instalar dependências)
```bash
./mvnw clean install
```

#### Executar a aplicação
```bash
# Via Maven (recomendado para desenvolvimento)
./mvnw spring-boot:run

# Ou compilar e executar
./mvnw clean package
java -jar target/biblioteca-0.0.1-SNAPSHOT.jar
```

> **🔧 Nota:** Na primeira execução, o Maven baixará as dependências necessárias, incluindo o suporte para arquivos `.env`.

A aplicação será executada em: `http://localhost:8080` (ou na porta definida em `SERVER_PORT`)

## Variáveis de Ambiente

### Configurações Disponíveis
| Variável | Descrição | Valor Padrão |
|----------|-----------|--------------|
| `DATABASE_URL` | URL de conexão com o banco | `jdbc:postgresql://localhost:5432/biblioteca` |
| `DATABASE_USERNAME` | Usuário do banco de dados | `postgres` |
| `DATABASE_PASSWORD` | Senha do banco de dados | `password` |
| `DATABASE_DRIVER` | Driver JDBC | `org.postgresql.Driver` |
| `APP_NAME` | Nome da aplicação | `biblioteca` |
| `SERVER_PORT` | Porta do servidor | `8080` |
| `JPA_DDL_AUTO` | Modo DDL do Hibernate | `none` |
| `JPA_SHOW_SQL` | Mostrar SQL no log | `false` |
| `LOG_LEVEL_SECURITY` | Log do Spring Security | `DEBUG` |
| `LOG_LEVEL_APP` | Log da aplicação | `INFO` |

### ⚠️ Importante
- O arquivo `.env` deve estar na **raiz do projeto** (mesmo diretório que `pom.xml`)
- **Nunca** commite o arquivo `.env` no controle de versão (já está no `.gitignore`)
- Para produção, use variáveis de ambiente do sistema operacional

## Usuários de Teste

### Bibliotecários (podem acessar todos os endpoints):
- **Username**: `admin` | **Password**: `123456`
- **Username**: `bibliotecario` | **Password**: `123456`

### Usuários Comuns (podem acessar endpoints de consulta):
- **Username**: `usuario1` | **Password**: `123456`
- **Username**: `leitor` | **Password**: `123456`

## Exemplos de Uso

### 1. Mensagem de Boas Vindas (Público)
```bash
curl -X GET http://localhost:8080/api/welcome
```

### 2. Cadastrar Usuário (Público)
```bash
curl -X POST http://localhost:8080/api/usuarios/cadastrar \
  -H "Content-Type: application/json" \
  -d '{
    "username": "novouser",
    "password": "senha123",
    "email": "user@email.com",
    "perfil": "USER"
  }'
```

### 3. Consultar Todos os Livros (Autenticado)
```bash
curl -X GET http://localhost:8080/api/livros \
  -u "usuario1:123456"
```

### 4. Buscar Livros por Autor (Autenticado)
```bash
curl -X GET http://localhost:8080/api/livros/autor/Machado \
  -u "usuario1:123456"
```

### 5. Buscar Livros por Ano (Autenticado)
```bash
curl -X GET http://localhost:8080/api/livros/ano/1899 \
  -u "usuario1:123456"
```

### 6. Cadastrar Novo Livro (Bibliotecário)
```bash
curl -X POST http://localhost:8080/api/livros/cadastrar \
  -H "Content-Type: application/json" \
  -u "admin:123456" \
  -d '{
    "titulo": "Novo Livro",
    "autor": "Autor Exemplo",
    "ano": 2023
  }'
```

## Estrutura do Projeto

```
src/main/java/com/projarq/biblioteca/
├── BibliotecaApplication.java          # Classe principal
├── config/
│   └── SecurityConfig.java            # Configuração de segurança
├── controllers/
│   ├── LivroController.java           # Endpoints de livros
│   ├── UsuarioController.java         # Endpoints de usuários
│   └── WelcomeController.java         # Endpoint de boas vindas
├── entities/
│   ├── Livro.java                     # Entidade Livro
│   └── Usuario.java                   # Entidade Usuário
├── mappers/
│   ├── LivroRowMapper.java            # Mapper para Livro
│   └── UsuarioRowMapper.java          # Mapper para Usuário
├── repositories/
│   ├── AcervoRepository.java          # Repository de Livros
│   ├── IAcervoRepository.java         # Interface do Repository de Livros
│   ├── IUsuarioRepository.java        # Interface do Repository de Usuários
│   └── UsuarioRepository.java         # Repository de Usuários
└── services/
    ├── CustomUserDetailsService.java  # Serviço de autenticação
    └── UsuarioService.java            # Serviço de usuários
```

## Perfis de Usuário

### USER
- Pode consultar livros (todos, por autor, por ano)
- Pode acessar informações do próprio perfil

### BIBLIOTECARIO
- Todas as permissões do USER
- Pode cadastrar novos livros
- Pode gerenciar o acervo

## Segurança

- Senhas são criptografadas usando BCrypt
- Autenticação via HTTP Basic Auth
- Autorização baseada em roles (ROLE_USER, ROLE_BIBLIOTECARIO)
- Endpoints públicos não requerem autenticação

## Dados de Teste

A aplicação inicializa com:
- **10 livros** de exemplo da literatura brasileira
- **4 usuários** de teste (2 bibliotecários, 2 usuários comuns)

## Códigos de Resposta HTTP

- `200 OK` - Sucesso
- `201 Created` - Recurso criado com sucesso
- `400 Bad Request` - Dados inválidos
- `401 Unauthorized` - Não autenticado
- `403 Forbidden` - Não autorizado (sem permissão)
- `404 Not Found` - Recurso não encontrado
- `409 Conflict` - Conflito (ex: usuário já existe)
- `500 Internal Server Error` - Erro interno
