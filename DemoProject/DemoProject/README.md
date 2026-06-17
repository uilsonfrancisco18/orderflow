# 📦 OrderFlow — Backend API

Sistema de gerenciamento de pedidos desenvolvido com **Spring Boot** e **PostgreSQL**.

---

## 🛠️ Tecnologias

| Tecnologia | Versão |
|---|---|
| Java | 25 |
| Spring Boot | 3.5.14 |
| Spring Data JPA | — |
| PostgreSQL | — |
| Maven | Wrapper incluído |

---

## ⚙️ Configuração do Banco de Dados

O projeto utiliza PostgreSQL. Configure o banco antes de rodar a aplicação.

1. Crie um banco de dados chamado `OrderFlow` no PostgreSQL.
2. Edite o arquivo `src/main/resources/application.properties` com suas credenciais:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/OrderFlow
spring.datasource.username=postgres
spring.datasource.password=sua_senha

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

> As tabelas são criadas automaticamente pelo Hibernate (`ddl-auto=update`).

---

## ▶️ Como Rodar

```bash
# Clone o repositório
git clone https://github.com/uilsonfrancisco18/orderflow.git

# Acesse a pasta do projeto
cd orderflow/DemoProject/DemoProject

# Execute com Maven Wrapper
./mvnw spring-boot:run   # Linux/Mac
mvnw.cmd spring-boot:run # Windows
```

A aplicação sobe em: `http://localhost:8080`

---

## 📁 Estrutura do Projeto

```
src/main/java/com/orderflow/
├── controller/        # Endpoints REST
├── service/           # Regras de negócio
├── repository/        # Acesso ao banco de dados
├── model/             # Entidades JPA
├── enums/             # Enumerações de status
└── OrderFlowApplication.java
```

---

## 🗂️ Entidades

| Entidade | Tabela | Descrição |
|---|---|---|
| `Cliente` | `cliente` | Dados do cliente e endereço |
| `Produto` | `produto` | Catálogo de produtos |
| `Pedido` | `pedido` | Pedidos realizados |
| `Pagamento` | `pagamento` | Registros de pagamento |
| `Entrega` | `entrega` | Controle de envio e entrega |
| `HistoricoStatus` | `historico_status` | Histórico de status dos pedidos |
| `Contem` | — | Relação entre Pedido e Produto |

---

## 🔗 Endpoints

A API roda na base `http://localhost:8080`.

---

### 👤 Clientes — `/clientes`

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/clientes` | Cadastrar cliente |
| `GET` | `/clientes` | Listar todos os clientes |
| `GET` | `/clientes/{id}` | Buscar cliente por ID |
| `PUT` | `/clientes/{id}` | Atualizar cliente |
| `DELETE` | `/clientes/{id}` | Excluir cliente |

---

### 📦 Produtos — `/produtos`

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/produtos` | Cadastrar produto |
| `GET` | `/produtos` | Listar todos os produtos |
| `GET` | `/produtos/{id}` | Buscar produto por ID |
| `PUT` | `/produtos/{id}` | Atualizar produto |
| `DELETE` | `/produtos/{id}` | Excluir produto |

---

### 🛒 Pedidos — `/pedidos`

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/pedidos` | Criar pedido (status inicial: `PENDENTE`) |
| `GET` | `/pedidos` | Listar todos os pedidos |
| `GET` | `/pedidos/{id}` | Buscar pedido por ID |
| `PUT` | `/pedidos/{id}?novoStatus=STATUS` | Atualizar status do pedido |
| `DELETE` | `/pedidos/{id}` | Excluir pedido |

**Status disponíveis:** `PENDENTE` · `PAGO` · `EM_PREPARO` · `ENVIADO` · `ENTREGUE` · `CANCELADO`

---

### 💳 Pagamentos — `/pagamentos`

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/pagamentos` | Registrar pagamento (define status como `PAGO`) |
| `GET` | `/pagamentos` | Listar todos os pagamentos |
| `GET` | `/pagamentos/{id}` | Buscar pagamento por ID |
| `PUT` | `/pagamentos/{id}` | Atualizar pagamento |
| `DELETE` | `/pagamentos/{id}` | Excluir pagamento |

---

### 🚚 Entregas — `/entregas`

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/entregas` | Registrar envio (status: `ENVIADO`) |
| `GET` | `/entregas` | Listar todas as entregas |
| `GET` | `/entregas/{id}` | Buscar entrega por ID |
| `PUT` | `/entregas/{id}` | Atualizar dados da entrega |
| `PUT` | `/entregas/{id}/confirmar` | Confirmar entrega (status: `ENTREGUE`) |
| `DELETE` | `/entregas/{id}` | Excluir entrega |

---

### 📋 Histórico de Status — `/historico-status`

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/historico-status` | Registrar mudança de status |
| `GET` | `/historico-status` | Listar todo o histórico |
| `GET` | `/historico-status/{id}` | Buscar registro por ID |
| `PUT` | `/historico-status/{id}` | Atualizar registro |
| `DELETE` | `/historico-status/{id}` | Excluir registro |

---

### 🔗 Contem (Itens do Pedido) — `/contem`

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/contem` | Adicionar item ao pedido |
| `GET` | `/contem` | Listar todos os itens |
| `GET` | `/contem/{id}` | Buscar item por ID |
| `PUT` | `/contem/{id}` | Atualizar item |
| `DELETE` | `/contem/{id}` | Excluir item |

---

## 🔄 Fluxo Principal

```
Cliente faz Pedido
      ↓
Pagamento registrado → Pedido vai para EM_PREPARACAO
      ↓
Entrega registrada → Pedido vai para ENVIADO
      ↓
Entrega confirmada → Pedido vai para ENTREGUE
```

---

## 👨‍💻 Autores

Projeto desenvolvido para trabalho acadêmico.
