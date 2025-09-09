## Comandos Docker Compose para PostgreSQL

### Subir todos os containers
```bash
docker-compose up -d
```

### Subir apenas o PostgreSQL
```bash
docker-compose up -d postgres
```

### Parar Container do PostgreSQL
```bash
docker-compose stop postgres
```

### Remover Container do PostgreSQL
```bash
docker-compose down
```

### Ver Logs do PostgreSQL
```bash
docker-compose logs -f postgres
```