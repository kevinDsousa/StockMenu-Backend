# Deploy — Stock Menu API

## GitHub Actions

- Workflow: `.github/workflows/build.yml`
- Branch: `develop`
- Imagem: `ghcr.io/<owner>/stockmenu-api:latest`

### Secrets (environment `develop`)

| Secret | Descrição |
|--------|-----------|
| `DOKPLOY_DEV_DOMAIN` | Host do Dokploy |
| `DOKPLOY_DEV_KEY` | API key |
| `DOKPLOY_APPLICATION_ID` | ID da app no Dokploy |

### Variáveis no Dokploy (runtime)

`DB_URL`, `DB_USER`, `DB_PASSWORD`, `TOKEN_SECRET`, `MINIO_*`, `SPRING_PROFILES_ACTIVE=prod`, etc.

## Java

O projeto usa **Java 21** (LTS), compatível com Nixpacks/Dokploy (`NIXPACKS_JDK_VERSION=21` em `nixpacks.toml`).

No Dokploy, preferir **Dockerfile** na raiz em vez de Nixpacks auto-detect, ou garantir a variável `NIXPACKS_JDK_VERSION=21` no ambiente.

## Build local

```bash
docker build -t stockmenu-api:local .
```
