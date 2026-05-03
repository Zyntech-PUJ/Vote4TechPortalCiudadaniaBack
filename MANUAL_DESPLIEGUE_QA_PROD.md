# Manual de Despliegue - Vote4Tech Portal Ciudadania (Produccion y QA)

Este manual cubre el despliegue de frontend y backend en VMs separadas para Produccion y QA.

## 1. Infraestructura

### Produccion

| Componente   | Host           | Puerto |
|--------------|----------------|--------|
| Frontend     | `10.43.97.237` | `80`   |
| Backend      | `10.43.100.131`| `8082` |
| Base de datos| `10.43.101.13` | `5432` |

### QA

| Componente   | Host          | Puerto |
|--------------|---------------|--------|
| Frontend     | `10.43.97.232`| `80`   |
| Backend      | `10.43.99.3`  | `8082` |
| Base de datos| `10.43.98.254`| `5433` |

## 2. Requisitos

### Backend VM

- Java 17 o superior
- Maven
- Acceso de red al host de PostgreSQL
- Puerto 8082 libre

### Frontend VM

- Docker y Docker Compose
- Puerto 80 libre

## 3. Backend - Despliegue Produccion

Conectarse al backend de Produccion:

```bash
ssh estudiante@10.43.100.131
```

Entrar al proyecto y compilar:

```bash
cd ~/Vote4TechPortalCiudadaniaBack
mvn clean compile
```

Exportar variables (Produccion):

```bash
export PORT=8082
export DB_URL='jdbc:postgresql://10.43.101.13:5432/bd_publica'
export DB_USER='admin_db_nacional'
export DB_PASSWORD='12345'
export CORS_ALLOWED_ORIGINS='http://10.43.97.237'
export LOG_LEVEL='INFO'
```

Levantar backend:

```bash
mvn spring-boot:run
```

Verificar:

```bash
curl http://localhost:8082/eleccion/elecciones
```

## 4. Backend - Despliegue QA

Conectarse al backend de QA:

```bash
ssh estudiante@10.43.99.3
```

Entrar al proyecto y compilar:

```bash
cd ~/Vote4TechPortalCiudadaniaBack
mvn clean compile
```

Exportar variables (QA) - bloque solicitado:

```bash
export PORT=8082
export DB_URL='jdbc:postgresql://10.43.98.254:5433/bd_publica'
export DB_USER='admin_db_nacional'
export DB_PASSWORD='12345'
export CORS_ALLOWED_ORIGINS='http://10.43.97.232'
export LOG_LEVEL='INFO'
```

Levantar backend:

```bash
mvn spring-boot:run
```

Verificar:

```bash
curl http://localhost:8082/eleccion/elecciones
```

## 5. Frontend - Despliegue Produccion

Conectarse al frontend de Produccion:

```bash
ssh estudiante@10.43.97.237
```

Ir al proyecto:

```bash
cd ~/Vote4TechPortalCiudadaniaFront/Publico
```

Configurar proxy de Nginx hacia backend de Produccion (`10.43.100.131:8082`):

```nginx
proxy_pass http://10.43.100.131:8082/;
```

Reconstruir y levantar contenedores:

```bash
docker compose -f docker/docker-compose.prod.yml up -d --build
```

Verificar contenedores:

```bash
docker ps
```

## 6. Frontend - Despliegue QA

Conectarse al frontend de QA:

```bash
ssh estudiante@10.43.97.232
```

Ir al proyecto:

```bash
cd ~/Vote4TechPortalCiudadaniaFront/Publico
```

Configurar proxy de Nginx hacia backend de QA (`10.43.99.3:8082`):

```nginx
proxy_pass http://10.43.99.3:8082/;
```

Reconstruir y levantar contenedores:

```bash
docker compose -f docker/docker-compose.prod.yml up -d --build
```

Verificar:

```bash
curl http://localhost/api/eleccion/elecciones
```

## 7. Publicacion externa con Cloudflare Quick Tunnel

En la VM de frontend (Produccion o QA), obtener URL publica:

```bash
docker logs vote4tech-cloudflared 2>&1 | grep trycloudflare
```

Si se usara el dominio temporal para CORS, actualizar variable en backend y reiniciar:

```bash
export CORS_ALLOWED_ORIGINS='https://<tu-url>.trycloudflare.com,http://<ip-frontend>'
mvn spring-boot:run
```

## 8. Actualizacion de despliegue por cambios de codigo

### Backend (Produccion o QA)

```bash
cd ~/Vote4TechPortalCiudadaniaBack
git pull
mvn clean compile
export PORT=8082
# export DB_URL/DB_USER/DB_PASSWORD/CORS/LOG segun ambiente
mvn spring-boot:run
```

### Frontend (Produccion o QA)

```bash
cd ~/Vote4TechPortalCiudadaniaFront
git pull
cd Publico
docker compose -f docker/docker-compose.prod.yml up -d --build
```

## 9. Troubleshooting rapido

### Error de conexion del frontend al backend

- Revisar `proxy_pass` en `Publico/docker/nginx.conf`
- Asegurar que backend este en `:8082`
- Reconstruir frontend con `--build`

### Puerto ocupado en backend

```bash
sudo ss -tlnp | grep 8082
sudo lsof -t -i:8082 | xargs -r kill -9
```

### El backend no inicia por datos de prueba

Si en algun ambiente se habilito carga de seed-data, deshabilitar:

```bash
mvn spring-boot:run -Dspring-boot.run.arguments=--app.seed-data.enabled=false
```
