# 📖 Organizador de Estudos Inteligente - API

## 🧭 Visão Geral do Projeto

Este projeto é uma **API RESTful completa desenvolvida em Java com Spring Boot**, projetada para ajudar estudantes a **gerenciar seus materiais de estudo**.

A principal funcionalidade é um **sistema inteligente que sugere o próximo tópico a ser estudado**, baseando-se no método de estudo intercalado (*interleaving*), que prioriza os tópicos com base na **prioridade da matéria** e no **tempo decorrido desde a última revisão**.

> Este projeto foi construído como um **case de estudo completo**, cobrindo desde a concepção e modelagem de dados até segurança, testes e containerização com Docker.

---

## 🚀 Stack de Tecnologias

Este projeto foi construído utilizando as seguintes tecnologias:

- **Backend:** Java 21, Spring Boot 3
- **Persistência:** Spring Data JPA, Hibernate
- **Banco de Dados:** PostgreSQL (Produção/Docker) e H2 (Testes)
- **Segurança:** Spring Security (Autenticação Basic Auth e Autorização por Roles)
- **Testes:** JUnit 5, Mockito, AssertJ, Spring Boot Test (Unidade e Integração)
- **DevOps:** Docker, Docker Compose
- **Build:** Maven
- **Utilitários:** Lombok

---

## ✨ Principais Funcionalidades

- ✅ **API REST Completa:** Operações CRUD (Criar, Ler, Atualizar, Deletar) para Matérias e Tópicos.
- ✅ **Algoritmo de Sugestão:** Endpoint que retorna o próximo tópico de estudo recomendado.
- ✅ **Segurança Robusta:** Endpoints protegidos com autenticação e autorização baseada em cargos (`USER`/`ADMIN`).
- ✅ **Validação de Dados:** Garante a integridade dos dados na entrada da API.
- ✅ **Tratamento de Erros:** Respostas de erro claras e padronizadas para o cliente.
- ✅ **Containerização:** Aplicação e banco de dados totalmente configurados para rodar com um único comando via Docker Compose.
- ✅ **Qualidade de Código:** Suíte de testes automatizados cobrindo a lógica de negócio e os fluxos de integração.

---

## ⚙️ Como Executar o Projeto Localmente

Graças ao **Docker**, executar este projeto é muito simples. Você só precisa ter o Docker e o Docker Compose instalados.

### 1. Clone o repositório:

```bash
git clone https://github.com/panucciGabriel/MinhaAgenda.git
```

### 2. Navegue até a pasta do projeto:

```bash
cd MinhaAgenda-main
```

### 3. Suba o ambiente com Docker Compose:

- **Atenção:** certifique-se de que as portas `8080` e `5432` **não estejam em uso** na sua máquina.

```bash
docker-compose up --build
```

- O comando **--build** garante que a imagem da aplicação seja construída a partir do **Dockerfile**.

#### ✅ Pronto! A API estará rodando e acessível em http://localhost:8080

---

## 📡 Endpoints da API

Todos os endpoints exigem autenticação

| Método HTTP | Endpoint                             | Descrição                                          | Autorização  |
| ----------- | ------------------------------------ | -------------------------------------------------- | ------------ |
| `POST`      | `/api/materias`                      | Cadastra uma nova matéria e seus tópicos           | `ROLE_USER`  |
| `GET`       | `/api/materias`                      | Lista todas as matérias e seus tópicos             | Autenticado  |
| `PUT`       | `/api/materias/{id}`                 | Atualiza uma matéria existente                     | `ROLE_USER`  |
| `DELETE`    | `/api/materias/{id}`                 | Deleta uma matéria                                 | `ROLE_ADMIN` |
| `POST`      | `/api/topicos/{id}/registrar-estudo` | Registra a data e hora atuais como o último estudo | Autenticado  |
| `GET`       | `/api/estudo/sugestao`               | Retorna o tópico mais urgente para estudar         | Autenticado  |

---

### Credenciais de Teste (em memória):

- *****Usuário comum:***** `user`/`12345`
- *****Administrador:***** `admin`/`adimin123`
---

## ✍️ Autor

Feito por *****Gabriel Panucci*****