# Agende Nail 🌐

Sistema completo de agendamento para salões de design de unhas, com autenticação baseada em perfis (CLIENTE, FUNCIONARIO, ADMIN), controle de serviços e gerenciamento de agendamentos.

---

## 💡 Funcionalidades Principais

* Cadastro e login de usuários com roles diferenciadas
* Gerenciamento de usuários, serviços e agendamentos
* Dashboard integrada com múltiplas funcionalidades
* Envio de e-mail automático para clientes e funcionários após agendamento
* Segurança com Spring Security e BCrypt
* Integração com Thymeleaf para renderização das views
* Upload de imagens por serviço (ainda como URLs)

---

## ⚙️ Tecnologias Utilizadas

* **Spring Boot 3**
* **Spring Security**
* **Spring Data JPA**
* **MySQL**
* **Thymeleaf**
* **Maven**
* **Docker**
* **Java Mail Sender**

---

## 🏢 Páginas / Roles

* `CLIENTE`

  * Ver e editar perfil
  * Agendar serviços
  * Visualizar seus agendamentos

* `FUNCIONARIO`

  * Ver perfil e serviços atribuídos
  * Gerenciar disponibilidade
  * Visualizar apenas seus agendamentos

* `ADMIN`

  * Gerenciar todos os usuários
  * Gerenciar serviços e agendamentos

---

## 🚀 Como executar

### Via Docker

1. Clone o repositório:

   ```bash
   git clone https://github.com/seuusuario/agende-nail.git
   cd agende-nail
   ```

2. Rode o container:

   ```bash
   docker-compose up --build
   ```

3. Acesse em:

   ```
   http://localhost:8080
   ```

> ✉️ Certifique-se de que a porta 8080 está livre.

---

## 📂 Estrutura de Pacotes

```text
br.com.nailDesigner
├── controller
├── config
├── dtos
├── entities
├── enums
├── repositories
├── services
└── views (templates Thymeleaf)
```

---

## 🛋️ Cadastro de Usuário

O cadastro é feito via tela `/cadastro` e cria um usuário com role `CLIENTE` por padrão.

---

## 📨 Envio de E-mails

* Após confirmar um agendamento:

  * Cliente recebe a lista completa de serviços agendados.
  * Cada funcionário recebe apenas os serviços atribuídos a ele.

> Configurado com `JavaMailSender` no `application.properties`.

---

## 📊 Dashboard Unificada

Cada role tem acesso a diferentes funcionalidades na mesma página (`/dashboard`), usando botões e menus controlados por Thymeleaf:

```html
<th:block th:if="${#authorization.expression('hasRole(''ADMIN'')')}">
    <a th:href="@{/admin/usuarios}">Gerenciar Usuários</a>
</th:block>
```

---

## 🌐 Imagens nos Serviços

Atualmente, imagens estão sendo tratadas como `List<String>` com URLs. Para hospedar localmente:

1. Criar endpoint para upload.
2. Armazenar em pasta do projeto ou em nuvem.
3. Salvar path da imagem como `String` no banco.

---

## 🚧 Desenvolvimento

> Após alterar código:

```bash
mvn clean install
```

Para Docker:

```bash
docker-compose down
docker-compose up --build
```

---

## ✉️ Contato

Equipe:

* Vinícius Rodrigues Xavier
* Pedro Henrique Magro
* Felipe Augusto Soares

---

## ✅ Status do Projeto

> 100% funcional com autenticação, CRUDs e envio de e-mail.

Melhorias futuras:

* Upload real de imagens
* Testes automatizados
* Responsividade para mobile

---

## 💼 Licença

Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.
