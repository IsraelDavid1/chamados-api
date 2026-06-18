# Chamados API

API REST para gerenciamento de chamados de Service Desk, desenvolvida em Java e Spring Boot. Responsável pela autenticação, controle de usuários, gerenciamento de chamados e integração com banco de dados PostgreSQL.

---

## 💻 Sobre o projeto

O **Chamados API** é o backend da plataforma Service Desk, criado para centralizar o gerenciamento de chamados técnicos dentro de uma organização.

A aplicação fornece uma API REST segura para autenticação de usuários, criação e gerenciamento de chamados, controle de permissões e persistência de dados.

O projeto foi desenvolvido seguindo boas práticas de desenvolvimento seguro, conteinerização com Docker e integração contínua utilizando GitHub Actions.

---

## 🚀 Tecnologias

As principais tecnologias utilizadas no projeto são:

* Java 21
* Spring Boot 3
* Spring Security
* JWT Authentication
* Spring Data JPA
* PostgreSQL
* Maven
* Docker
* Docker Compose
* GitHub Actions
* Trivy
* OWASP Dependency Check

---

## 📦 Como baixar e executar o projeto

### Clonar o repositório

```bash
git clone https://github.com/IsraelDavid1/chamados-api.git
```

```bash
cd chamados-api
```

### Configurar variáveis de ambiente

Crie um arquivo:

```bash
.env
```

Exemplo:

```env
DB_HOST=postgres
DB_PORT=5432
DB_NAME=servicedesk
DB_USER=postgres
DB_PASSWORD=postgres
JWT_SECRET=sua_chave_secreta
```

### Executar com Docker Compose

```bash
docker-compose up -d
```

### Verificar containers

```bash
docker ps
```

A API ficará disponível em:

```text
http://localhost:8080
```

---

## 🛠️ Funcionalidades

* Autenticação via JWT
* Cadastro de usuários
* Controle de permissões
* Criação de chamados
* Edição de chamados
* Atualização de status
* Encerramento de chamados
* Consulta de chamados
* Persistência em PostgreSQL
* Logs de auditoria
* API REST documentada

---

## 🤝 Como contribuir

1. Faça um Fork do projeto
2. Crie uma branch para sua feature

```bash
git checkout -b feature/minha-feature
```

3. Realize suas alterações

4. Faça commit das mudanças

```bash
git commit -m "feat: nova funcionalidade"
```

5. Envie para seu fork

```bash
git push origin feature/minha-feature
```

6. Abra um Pull Request

---

## 📝 Licença

Este projeto está licenciado sob a licença MIT.

---

## 👤 Autor

**Israel David**

GitHub:
https://github.com/IsraelDavid1
