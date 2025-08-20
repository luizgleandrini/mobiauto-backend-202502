# Mobiauto Backend - Sistema de Gestão de Revendas Automotivas

## 📋 Introdução

O **Mobiauto Backend** é uma aplicação desenvolvida em Java com Spring Boot que implementa um sistema completo de gestão para revendas automotivas. O sistema permite o gerenciamento de usuários, revendas e oportunidades de vendas, seguindo os princípios da Arquitetura Limpa (Clean Architecture) e oferecendo APIs RESTful documentadas com Swagger.

### Principais Funcionalidades

- 🔐 **Autenticação e Autorização**: Sistema de login baseado em HTTP Basic Auth com diferentes níveis de acesso
- 👥 **Gestão de Usuários**: CRUD completo com validação de dados e controle de permissões
- 🏢 **Gestão de Revendas**: Cadastro e gerenciamento de concessionárias
- 💼 **Gestão de Oportunidades**: Controle do ciclo de vendas desde o primeiro contato até a conclusão
- 🔍 **Sistema de Busca**: Filtros avançados por email, loja, papel e outros critérios
- 📊 **Documentação Automática**: Interface Swagger para testes e documentação da API

## 🏗️ Visão Geral da Arquitetura

### Pré-requisitos

- **Java 21**
- **Maven 3.8+**
- **Docker** e **Docker Compose**
- **Git**

### Componentes da Arquitetura

O projeto segue os princípios da **Clean Architecture**, organizando o código em camadas bem definidas:

```
mobiauto/
├── core/                    # Domínio e Casos de Uso
│   ├── domains/
│   │   ├── users/          # Domínio de Usuários
│   │   ├── revendas/       # Domínio de Revendas
│   │   └── opportunities/  # Domínio de Oportunidades
│   └── exceptions/         # Exceções de Negócio
├── infra/                  # Infraestrutura (Banco de Dados)
│   └── providers/          # Implementações dos Gateways
├── entrypoint/            # Camada de Apresentação (Controllers)
│   └── domains/           # Endpoints REST por domínio
└── configuration/         # Configurações do Spring
    └── config/           # Beans e Configurações de Segurança
```

#### Responsabilidades das Camadas

- **Core**: Contém as regras de negócio, entidades e casos de uso
- **Infra**: Implementa a persistência de dados com MongoDB
- **Entrypoint**: Expõe APIs REST e trata requisições HTTP
- **Configuration**: Centraliza configurações do Spring e segurança

### Stack Tecnológica

- **Framework**: Spring Boot 3.3.x
- **Banco de Dados**: MongoDB
- **Documentação**: Swagger/OpenAPI 3
- **Testes**: JUnit 5, Mockito, AssertJ
- **Build**: Maven (Multi-módulo)
- **Containerização**: Docker & Docker Compose

## ⚙️ Configuração e Execução

### Execução com Docker (Recomendado)

1. **Clone o repositório**:
```bash
git clone https://github.com/seu-usuario/mobiauto-backend-202502.git
cd mobiauto-backend-202502
```

2. **Execute com Docker Compose**:
```bash
docker-compose up -d
```

Isso irá inicializar:
- **MongoDB** na porta `27017`
- **Mongo Express** na porta `8081` (interface web do MongoDB)
- **Aplicação Mobiauto** na porta `8080`

3. **Verifique se os serviços estão rodando**:
```bash
docker-compose ps
```

### Acesso à Aplicação

- **API Swagger**: http://localhost:8080/swagger-ui.html
- **Mongo Express**: http://localhost:8081 (admin/admin)
- **API Base URL**: http://localhost:8080/v1

## 🔐 Autenticação e Primeiros Passos

### Usuários Pré-configurados

O sistema possui usuários hardcoded para facilitar os testes iniciais:

| Usuário | Senha | Papel | Descrição |
|---------|--------|-------|-----------|
| `admin` | `admin123` | ADMIN | Acesso total ao sistema |

### Como Usar o Sistema

#### 1. Primeiro Acesso
1. Acesse o Swagger: http://localhost:8080/swagger-ui.html
2. Clique em **"Authorize"** no canto superior direito
3. Use as credenciais do admin: `admin` / `admin123`

#### 2. Criando Novos Usuários
```bash
POST /v1/users
{
  "name": "João Silva",
  "email": "joao.silva@email.com",
  "phone": "11999999999",
  "password": "senha123",
  "role": "MANAGER",
  "storeId": "loja123"
}
```

#### 3. Criando Revendas
```bash
POST /v1/dealerships
{
  "cnpj": "12345678000190", # é necessário um CNPJ válido recomenda-se uso de um cnpj generator, e o formado deve ser apenas números
  "socialName": "Revenda Silva LTDA"
}
```

#### 4. Gerenciando Oportunidades
```bash
POST /v1/opportunities
{
  "customerName": "Maria Santos",
  "customerEmail": "maria@email.com",
  "customerPhone": "11888888888",
  "vehicleBrand": "Toyota",
  "vehicleModel": "Corolla",
  "vehicleVersion": "XEi 2.0",
  "yearModel": 2024,
  "storeId": "loja123"
}
```

#### 5. Buscando e Atualizando Dados

**Listar usuários com filtros:**
```bash
GET /v1/users?email=joao.silva@email.com&role=MANAGER&page=0&size=10
```

**Atualizar dados (use o ID obtido nas listagens):**
```bash
#Os ids devem ser recuperados a partir do get
PUT /v1/users/{userId}
PUT /v1/dealerships/{dealershipId}
PUT /v1/opportunities/{opportunityId}
```

### Hierarquia de Permissões

- **ADMIN**: Acesso total, pode gerenciar qualquer recurso

## 🧪 Testes

### Executando Testes

```bash
# Testes unitários
mvn test

# Testes com relatório de cobertura
mvn clean test jacoco:report
```

### Cobertura de Testes

Atualmente, os testes unitários foram implementados focando nos **casos de uso (use cases)** devido ao tempo disponível. Esta abordagem garante que a lógica de negócio principal esteja bem testada.

**Testes Implementados:**
- ✅ Casos de uso de criação (Users, Dealerships, Opportunities)
- ✅ Casos de uso de listagem com paginação
- ✅ Validações de negócio e permissões
- ✅ Tratamento de exceções

## 🔧 Solução de Problemas

### Problemas Comuns

#### 1. Erro de Conexão com MongoDB
```bash
# Verifique se o MongoDB está rodando
docker-compose ps mongo

# Reinicie se necessário
docker-compose restart mongo
```

#### 2. Porta 8080 Ocupada
```bash
# Encontre o processo usando a porta
netstat -ano | findstr :8080

# Ou altere a porta no docker-compose.yml
ports:
  - "8081:8080"  # Nova porta externa
```

#### 3. Erro de Autenticação
- Verifique se está usando HTTP Basic Auth
- Confirme usuário/senha (case-sensitive)
- Certifique-se de que o usuário tem permissão para a operação

#### 4. Problemas com Docker
```bash
# Limpar containers e volumes
docker-compose down -v
docker-compose up -d --build

# Ver logs da aplicação
docker-compose logs mobiauto-backend
```

### Logs e Debugging

```bash
# Ver logs em tempo real
docker-compose logs -f mobiauto-backend

# Ver logs específicos do MongoDB
docker-compose logs mongo
```
