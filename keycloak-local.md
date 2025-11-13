# 🚀 Setup Local - Aplicação Cardappio com Keycloak

Guia passo a passo para subir a aplicação completa localmente com autenticação Keycloak.

---

## 📋 Pré-requisitos

Antes de começar, certifique-se de ter instalado:

- **Docker** e **Docker Compose** (versão 3.8+)
- **Node.js** (versão 18+) e **npm**
- **Java 17+** e **Maven** (ou use o `mvnw` incluído no projeto)
- **Git**

---

## 🗂️ Estrutura do Projeto

```
projeto/
├── docker-compose-keycloak/    # Configuração do Keycloak
├── docker-compose/              # Configuração do PostgreSQL principal
├── server/cardappio/            # Backend Spring Boot
└── web/                         # Frontend Angular
```

---

## 🐳 Passo 1: Subir o Keycloak

### 1.1 Acesse a pasta do Keycloak

```bash
cd docker-compose-keycloak
```

### 1.2 Suba os containers

```bash
docker compose up -d
```

Isso irá subir:
- **PostgreSQL Keycloak** na porta `5433`
- **Keycloak** na porta `8090`

### 1.3 Verifique se os containers estão rodando

```bash
docker compose ps
```

Você deve ver algo como:
```
NAME                  STATUS              PORTS
postgres_keycloak     Up                  0.0.0.0:5433->5432/tcp
keycloak              Up                  0.0.0.0:8090->8080/tcp
```

### 1.4 Acesse o Keycloak Admin Console

Abra o navegador e acesse:
```
http://localhost:8090
```

**Credenciais padrão:**
- **Usuário:** `admin`
- **Senha:** `admin`

---

## 🔑 Passo 2: Configurar o Realm no Keycloak

### 2.1 Criar o Realm

1. No console do Keycloak, clique em **"Create Realm"** (canto superior esquerdo)
2. Nome do Realm: `cardappio-app`
3. Clique em **"Create"**

### 2.2 Criar o Client

1. No menu lateral, vá em **"Clients"**
2. Clique em **"Create client"**
3. Preencha:
   - **Client ID:** `frontend-app`
   - **Client Protocol:** `openid-connect`
4. Clique em **"Next"**
5. Em **"Capability config"**:
   - Marque: `Standard flow`, `Direct access grants`
6. Clique em **"Next"**
7. Em **"Login settings"**:
   - **Valid redirect URIs:** `http://localhost:4200/*`
   - **Web origins:** `http://localhost:4200`
8. Clique em **"Save"**

### 2.3 Criar um usuário de teste

1. No menu lateral, vá em **"Users"**
2. Clique em **"Add user"**
3. Preencha:
   - **Username:** `teste`
   - **Email:** `teste@cardappio.com`
   - **First name:** `Usuário`
   - **Last name:** `Teste`
4. Clique em **"Create"**
5. Na aba **"Credentials"**:
   - Clique em **"Set password"**
   - Digite a senha: `teste123`
   - Desmarque **"Temporary"**
   - Clique em **"Save"**

---

## 🗄️ Passo 3: Subir o PostgreSQL Principal

### 3.1 Acesse a pasta do docker-compose

```bash
cd ../docker-compose
```

### 3.2 Suba o container do PostgreSQL

```bash
docker compose up -d
```

Isso irá subir o **PostgreSQL principal** na porta `5432` para a aplicação.

### 3.3 Verifique se está rodando

```bash
docker compose ps
```

---

## ⚙️ Passo 4: Configurar e Rodar o Backend

### 4.1 Acesse a pasta do backend

```bash
cd ../server/cardappio
```

### 4.2 Configure as variáveis de ambiente

Copie o arquivo `.env.example` para `.env`:

```bash
cp .env.example .env
```

Edite o arquivo `.env` e configure:

```env
# Database
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/cardappio
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=postgres

# Keycloak
KEYCLOAK_SERVER_URL=http://localhost:8090
KEYCLOAK_REALM=cardappio-app
KEYCLOAK_CLIENT_ID=frontend-app
KEYCLOAK_CLIENT_SECRET=
KEYCLOAK_AUTH_URL=http://localhost:8090/realms/cardappio-app

# CORS
CORS_ALLOWED_ORIGINS=http://localhost:4200

# Profile
SPRING_PROFILES_ACTIVE=dev
```

### 4.3 Compile e rode a aplicação

**Opção 1: Usando Maven instalado**
```bash
mvn clean install
mvn spring-boot:run
```

**Opção 2: Usando Maven Wrapper (recomendado)**
```bash
# Linux/Mac
./mvnw clean install
./mvnw spring-boot:run

# Windows
mvnw.cmd clean install
mvnw.cmd spring-boot:run
```

O backend estará rodando em: `http://localhost:8080`

---

## 🌐 Passo 5: Configurar e Rodar o Frontend

### 5.1 Acesse a pasta do frontend

```bash
cd ../../web
```

### 5.2 Instale as dependências

```bash
npm install
```

### 5.3 Verifique a configuração do Keycloak

O arquivo `src/environments/environment.ts` deve estar assim:

```typescript
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080/api',
  keycloak: {
    url: 'http://localhost:8090',
    realm: 'cardappio-app',
    clientId: 'frontend-app'
  }
};
```

### 5.4 Rode a aplicação

```bash
npm start
```

ou

```bash
ng serve
```

O frontend estará disponível em: `http://localhost:4200`

---

## ✅ Passo 6: Testar a Aplicação

### 6.1 Acesse a aplicação

Abra o navegador em: `http://localhost:4200`

### 6.2 Faça login

Use as credenciais do usuário criado:
- **Usuário:** `teste`
- **Senha:** `teste123`

---

## 🛠️ Comandos Úteis

### Ver logs do Keycloak
```bash
cd docker-compose-keycloak
docker compose logs -f keycloak
```

### Ver logs do PostgreSQL
```bash
cd docker-compose
docker compose logs -f postgres
```

### Parar todos os containers
```bash
# Keycloak
cd docker-compose-keycloak
docker compose down

# PostgreSQL principal
cd ../docker-compose
docker compose down
```

### Limpar volumes (CUIDADO: apaga dados!)
```bash
docker compose down -v
```

---

## 🐛 Problemas Comuns

### 1. **Erro: "Connection refused" ao conectar no Keycloak**

**Solução:**
- Verifique se o container do Keycloak está rodando: `docker compose ps`
- Aguarde alguns segundos após subir o container (pode levar até 30s para inicializar)
- Verifique os logs: `docker compose logs -f keycloak`

### 2. **Erro: "Porta já em uso"**

**Solução:**
- Verifique se há outro serviço usando as portas:
  - `5432` - PostgreSQL principal
  - `5433` - PostgreSQL Keycloak
  - `8090` - Keycloak
  - `8080` - Backend
  - `4200` - Frontend
- Pare o serviço conflitante ou altere a porta no `docker-compose.yml`

### 3. **Backend não conecta no Keycloak**

**Solução:**
- Verifique as variáveis no arquivo `.env`
- Certifique-se de que o Realm `cardappio-app` foi criado
- Verifique se o Client `frontend-app` está configurado corretamente

### 4. **Frontend não autentica**

**Solução:**
- Verifique se as URLs de redirect estão configuradas no Client do Keycloak
- Limpe o cache do navegador e sessão storage
- Verifique o console do navegador para erros de CORS

### 5. **Erro ao rodar mvnw: "Permission denied"**

**Solução (Linux/Mac):**
```bash
chmod +x mvnw
./mvnw clean install
```

---

## 📚 Recursos Adicionais

- **Keycloak Admin Console:** http://localhost:8090
- **Backend API:** http://localhost:8080
- **Frontend:** http://localhost:4200
- **PostgreSQL Principal:** localhost:5432
- **PostgreSQL Keycloak:** localhost:5433

---

## 📝 Checklist de Setup

- [ ] Docker e Docker Compose instalados
- [ ] Node.js e npm instalados
- [ ] Java 17+ instalado
- [ ] Containers do Keycloak rodando
- [ ] Realm `cardappio-app` criado no Keycloak
- [ ] Client `frontend-app` configurado
- [ ] Usuário de teste criado
- [ ] PostgreSQL principal rodando
- [ ] Backend configurado e rodando
- [ ] Frontend instalado e rodando
- [ ] Login funcionando na aplicação

---

**✨ Pronto! Agora você tem o ambiente completo rodando localmente.**
