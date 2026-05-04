# Guía de Despliegue — Portal Ciudadanía (Full Stack)

Este documento describe el despliegue completo del Portal Ciudadanía:
**CiudadaniaBack** + **CiudadaniaFront** + la base de datos **bd_publica** + el **ETL de sincronización**.

> El Portal Ciudadanía es un portal **público de consulta** que muestra información electoral obtenida de `bd_nacional_vote4tech` mediante sincronización ETL hacia `bd_publica`.

---

## Infraestructura del Sistema (Completa)

| Servicio              | VM Producción    | Puerto | Repositorio                          |
|-----------------------|------------------|--------|--------------------------------------|
| RegistraduriaFront    | `10.43.97.237`   | `8090` | `Vote4TechRegistraduriaFront`        |
| VotacionFront         | `10.43.97.237`   | `4201` | `Vote4TechVotacionFront`             |
| **CiudadaniaFront**   | `10.43.97.237`   | `4202` | `Vote4TechPortalCiudadaniaFront`     |
| RegistraduriaBack     | `10.43.100.131`  | `8080` | `Vote4TechRegistraduriaBack`         |
| VotacionBack          | `10.43.100.131`  | `8081` | `Vote4TechVotacionBack`              |
| **CiudadaniaBack**    | `10.43.100.131`  | `8084` | `Vote4TechPortalCiudadaniaBack`      |
| PostgreSQL            | `10.43.101.13`   | `5432` | —                                    |
| CouchDB               | `10.43.101.13`   | `5984` | —                                    |
| ETL (Spring Batch)    | `10.43.101.13`   | `8083` | `Vote4TechETLs`                      |

---

## Flujo de Datos

```
RegistraduriaBack
       │
       ▼
bd_nacional_vote4tech  ──[ETL /sincronizacion/ejecutar]──▶  bd_publica
                                                                  │
                                                                  ▼
                                                          CiudadaniaBack
                                                                  │
                                                                  ▼
                                                          CiudadaniaFront
```

---

## Orden de Despliegue (Producción)

```
1. VM BDs (10.43.101.13)  →  2. VM Backends (10.43.100.131)  →  3. VM Frontends (10.43.97.237)
```

---

## PASO 1 — VM de Bases de Datos (10.43.101.13)

### 1.1 Clonar el repositorio ETL

```bash
cd ~
git clone https://github.com/Zyntech-PUJ/Vote4TechETLs.git
cd Vote4TechETLs
```

Si ya existe:

```bash
cd ~/Vote4TechETLs
git pull
```

### 1.2 Levantar PostgreSQL, CouchDB y ETL (todo en un solo comando)

```bash
docker compose -f docker/docker-compose.db.prod.yml up -d --build
```

Este comando hace tres cosas:
- Levanta **PostgreSQL** con la base `bd_nacional_vote4tech`
- Ejecuta el script `docker/init/01-create-databases.sh` que **crea `bd_publica` automáticamente** (solo la primera vez)
- Levanta **CouchDB**
- Construye y levanta el **ETL** en el puerto 8083

> **Si el volumen de PostgreSQL ya existía** (despliegue previo), el script de init no se ejecuta de nuevo.
> En ese caso, crear `bd_publica` manualmente:
>
> ```bash
> docker exec -it vote4tech-postgres psql -U admin_db_nacional \
>   -c "CREATE DATABASE bd_publica OWNER admin_db_nacional;"
> ```

### 1.3 Verificar que todo arrancó correctamente

```bash
# PostgreSQL: deben aparecer bd_nacional_vote4tech y bd_publica
docker exec -it vote4tech-postgres psql -U admin_db_nacional -c "\l"

# CouchDB
curl -s http://localhost:5984/ | grep couchdb

# ETL
curl -s http://localhost:8083/actuator/health
# Respuesta esperada: {"status":"UP"}
```

### 1.4 Primera vez — Crear las bases de CouchDB

```bash
curl -X PUT http://admin:admin123@localhost:5984/votos_urna
curl -X PUT http://admin:admin123@localhost:5984/votos_domicilio
```

### 1.5 Ejecutar el ETL de sincronización (nacional → pública)

Este paso copia los datos de `bd_nacional_vote4tech` a `bd_publica` para que el Portal Ciudadanía los pueda leer.

> **Prerequisito**: el RegistraduriaBack debe haber cargado datos en `bd_nacional_vote4tech` primero.

```bash
curl -X POST http://localhost:8083/sincronizacion/ejecutar
```

Respuesta esperada:
```json
{"status": "COMPLETED", ...}
```

---

## PASO 2 — VM de Backends (10.43.100.131)

### 2.1 Clonar el repositorio CiudadaniaBack

```bash
cd ~
git clone https://github.com/Zyntech-PUJ/Vote4TechPortalCiudadaniaBack.git
cd Vote4TechPortalCiudadaniaBack
```

Si ya existe:

```bash
cd ~/Vote4TechPortalCiudadaniaBack
git pull
```

### 2.2 Levantar CiudadaniaBack

```bash
docker compose -f docker/docker-compose.prod.yml up -d --build
```

El backend queda corriendo en el **puerto 8084**.

### 2.3 Verificar que el backend responde

```bash
curl -s http://localhost:8084/eleccion/elecciones
# Retorna un JSON con la lista de elecciones (puede estar vacía si el ETL aún no corrió)
```

---

## PASO 3 — VM de Frontends (10.43.97.237)

### 3.1 Clonar el repositorio CiudadaniaFront

```bash
cd ~
git clone https://github.com/Zyntech-PUJ/Vote4TechPortalCiudadaniaFront.git
cd Vote4TechPortalCiudadaniaFront
```

Si ya existe:

```bash
cd ~/Vote4TechPortalCiudadaniaFront
git pull
```

> **Nota de estructura**: el proyecto Angular vive en el subdirectorio `Publico/`, pero el `Dockerfile` y los archivos Docker están en la **raíz del repo** (`Vote4TechPortalCiudadaniaFront/`). Todos los comandos Docker se ejecutan desde la raíz del repo, igual que los demás servicios.

### 3.2 Levantar CiudadaniaFront

```bash
docker compose -f docker/docker-compose.prod.yml up -d --build
```

El frontend queda corriendo en el **puerto 4202**.
Cloudflare Tunnel genera automáticamente una URL pública (Quick Tunnel).

### 3.3 Obtener la URL pública de Cloudflare

```bash
docker logs vote4tech-ciudadania-cloudflared 2>&1 | grep "trycloudflare.com"
```

Copiar esa URL. Ejemplo: `https://abc-xyz.trycloudflare.com`

### 3.4 Actualizar CORS en CiudadaniaBack con la URL de Cloudflare

En la VM de backends, editar `~/Vote4TechPortalCiudadaniaBack/docker/docker-compose.prod.yml` y reemplazar `CLOUDFLARE_URL`:

```yaml
CORS_ALLOWED_ORIGINS: "http://10.43.97.237:4202,https://abc-xyz.trycloudflare.com"
```

Luego reconstruir el backend:

```bash
cd ~/Vote4TechPortalCiudadaniaBack
docker compose -f docker/docker-compose.prod.yml up -d --build ciudadania-back
```

### 3.5 Verificar la aplicación

- **Red interna**: `http://10.43.97.237:4202`
- **URL pública**: la URL de Cloudflare obtenida en el paso 3.3

---

## Ejecución Local (Desarrollo)

Desde la raíz del workspace ("Arquitectura de Software"):

```bash
docker compose up -d
```

Servicios disponibles localmente:

| Servicio           | URL                       |
|--------------------|---------------------------|
| RegistraduriaFront | http://localhost:4200      |
| VotacionFront      | http://localhost:4201      |
| **CiudadaniaFront**| http://localhost:4202      |
| RegistraduriaBack  | http://localhost:8082      |
| VotacionBack       | http://localhost:8081      |
| **CiudadaniaBack** | http://localhost:8084      |

> En desarrollo local `ciudadania-back` usa la misma DB `vote4tech` (compartida con `registraduria-back`). Los datos de elecciones/candidatos estarán disponibles sin necesidad de ejecutar el ETL.

Reconstruir solo el portal ciudadanía:

```bash
docker compose up -d --build ciudadania-back ciudadania-front
```

---

## Endpoints del CiudadaniaBack

| Método | Endpoint                                | Descripción                           |
|--------|-----------------------------------------|---------------------------------------|
| GET    | `/eleccion/elecciones`                  | Lista todas las elecciones            |
| GET    | `/eleccion/{id}`                        | Obtiene una elección por ID           |
| GET    | `/candidato/candidatos`                 | Lista todos los candidatos            |
| GET    | `/candidato/{id}`                       | Obtiene un candidato por ID           |
| GET    | `/candidato/por-eleccion/{idEleccion}`  | Candidatos de una elección específica |
| GET    | `/electoral/consulta-sancion?cedula=X`  | Consulta sanciones por cédula         |
| GET    | `/electoral/consulta-jurado/{cedula}`   | Asignaciones de jurado por cédula     |
| GET    | `/electoral/jurados-eleccion/{id}`      | Todos los jurados de una elección     |

---

## Solución de Problemas

### `bd_publica` no existe al ejecutar el ETL

```bash
docker exec -it vote4tech-postgres psql -U admin_db_nacional \
  -c "CREATE DATABASE bd_publica OWNER admin_db_nacional;"
```

Luego volver a ejecutar:

```bash
curl -X POST http://localhost:8083/sincronizacion/ejecutar
```

### El ETL de sincronización falla con error de FK

Verificar que el RegistraduriaBack haya insertado datos en `bd_nacional_vote4tech` antes de ejecutar la sincronización. Las tablas fuente (`ciudadano`, `eleccion`, `candidato`, etc.) deben existir y tener registros.

### CiudadaniaFront muestra error 502/504 al consultar

Verificar que `ciudadania-back` esté corriendo en el puerto 8084:

```bash
docker ps | grep ciudadania
curl -s http://10.43.100.131:8084/eleccion/elecciones
```

### Error de CORS en el navegador

1. Obtener la URL de Cloudflare: `docker logs vote4tech-ciudadania-cloudflared 2>&1 | grep trycloudflare`
2. Actualizar `CORS_ALLOWED_ORIGINS` en `~/Vote4TechPortalCiudadaniaBack/docker/docker-compose.prod.yml`
3. Reconstruir: `docker compose -f docker/docker-compose.prod.yml up -d --build ciudadania-back`

### El contenedor del frontend no arranca (error de build)

Asegurarse de que el `Dockerfile` está en la **raíz del repo** `Vote4TechPortalCiudadaniaFront/`, no dentro de `Publico/`. Si existe un `Dockerfile` antiguo en `Publico/`, eliminarlo para evitar conflictos.


Este documento describe el despliegue completo del Portal Ciudadanía:
**CiudadaniaBack** + **CiudadaniaFront** + la base de datos **bd_publica** + el **ETL de sincronización**.

> El Portal Ciudadanía es un portal **público de consulta** que muestra información electoral obtenida de `bd_nacional_vote4tech` mediante sincronización ETL hacia `bd_publica`.

---

## Infraestructura del Sistema (Completa)

| Servicio              | VM Producción    | Puerto |
|-----------------------|------------------|--------|
| RegistraduriaFront    | `10.43.97.237`   | `8090` |
| VotacionFront         | `10.43.97.237`   | `4201` |
| **CiudadaniaFront**   | `10.43.97.237`   | `4202` |
| RegistraduriaBack     | `10.43.100.131`  | `8080` |
| VotacionBack          | `10.43.100.131`  | `8081` |
| **CiudadaniaBack**    | `10.43.100.131`  | `8084` |
| PostgreSQL            | `10.43.101.13`   | `5432` |
| CouchDB               | `10.43.101.13`   | `5984` |
| ETL (Spring Batch)    | `10.43.101.13`   | `8083` |

---

## Orden de Despliegue (Producción)

```
VM BDs (10.43.101.13) → VM Backends (10.43.100.131) → VM Frontends (10.43.97.237)
```

Dentro de cada VM, sigue el orden indicado en cada sección.

---

## Paso 1 — VM de Bases de Datos (10.43.101.13)

> Aquí se crean las dos DBs de PostgreSQL y se lanza el ETL.

### 1.1 Clonar/Actualizar el repositorio

```bash
# Solo la primera vez
git clone <repo-url> vote4tech
cd vote4tech/deploy

# Si ya existe, solo pull
cd vote4tech
git pull
```

### 1.2 Levantar PostgreSQL, CouchDB y ETL

```bash
cd deploy
docker compose -f docker-compose.db.yml up -d --build
```

Este comando:
- Crea `bd_nacional_vote4tech` (base principal)
- **Crea automáticamente `bd_publica`** gracias al script `deploy/init/01-create-databases.sh`
- Levanta CouchDB
- Construye y levanta el ETL en el puerto 8083

> **PRIMERA VEZ SOLAMENTE:** Si el volumen de PostgreSQL ya existe (de un despliegue previo), el script de init **no se ejecuta** de nuevo. En ese caso, crear `bd_publica` manualmente:
>
> ```bash
> docker exec -it vote4tech-postgres psql -U admin_db_nacional -c "CREATE DATABASE bd_publica OWNER admin_db_nacional;"
> ```

### 1.3 Verificar que todo arrancó

```bash
# PostgreSQL responde
docker exec -it vote4tech-postgres psql -U admin_db_nacional -c "\l"
# Debe aparecer tanto bd_nacional_vote4tech como bd_publica

# CouchDB responde
curl http://localhost:5984/

# ETL responde
curl http://localhost:8083/actuator/health
```

### 1.4 Primera vez — Crear bases de CouchDB

```bash
curl -X PUT http://admin:admin123@localhost:5984/votos_urna
curl -X PUT http://admin:admin123@localhost:5984/votos_domicilio
```

### 1.5 Ejecutar el ETL de sincronización (nacional → publica)

Este job copia los datos de `bd_nacional_vote4tech` a `bd_publica` para que el Portal Ciudadanía los pueda leer.

```bash
curl -X POST http://localhost:8083/sincronizacion/ejecutar
```

> Ejecutar este comando cada vez que se quiera actualizar los datos del portal público.
> También se puede ejecutar el ETL de votos (CouchDB → PostgreSQL):
> ```bash
> curl -X POST http://localhost:8083/etl/ejecutar
> ```

---

## Paso 2 — VM de Backends (10.43.100.131)

### 2.1 Desplegar CiudadaniaBack

```bash
cd vote4tech/Vote4TechPortalCiudadaniaBack
docker compose -f docker/docker-compose.prod.yml up -d --build
```

El backend queda corriendo en el **puerto 8084**.

### 2.2 Verificar que el backend responde

```bash
curl http://localhost:8084/eleccion/elecciones
# Debe retornar un JSON con la lista de elecciones (puede estar vacía si el ETL aún no corrió)
```

### 2.3 Actualizar CORS después de obtener URL de Cloudflare

Una vez levantado el frontend (Paso 3) y obtenida la URL del túnel Cloudflare, actualizar `CORS_ALLOWED_ORIGINS` en `docker/docker-compose.prod.yml`:

```yaml
CORS_ALLOWED_ORIGINS: "http://10.43.97.237:4202,https://TU-URL.trycloudflare.com"
```

Luego reconstruir el backend:

```bash
docker compose -f docker/docker-compose.prod.yml up -d --build ciudadania-back
```

---

## Paso 3 — VM de Frontends (10.43.97.237)

### 3.1 Desplegar CiudadaniaFront

```bash
cd vote4tech/Vote4TechPortalCiudadaniaFront/Publico
docker compose -f docker/docker-compose.prod.yml up -d --build
```

El frontend queda corriendo en el **puerto 4202**.
Cloudflare Tunnel expone una URL pública automáticamente (Quick Tunnel).

### 3.2 Obtener la URL pública de Cloudflare

```bash
docker logs vote4tech-ciudadania-cloudflared 2>&1 | grep "trycloudflare.com"
```

Copiar esa URL (ej: `https://abc123.trycloudflare.com`).

### 3.3 Actualizar CORS en CiudadaniaBack

Ver Paso 2.3 con la URL obtenida.

### 3.4 Verificar la aplicación

Abrir en el navegador:
- `http://10.43.97.237:4202` — acceso desde la red interna
- La URL de Cloudflare — acceso público

---

## Ejecución Local (Desarrollo)

Desde la carpeta raíz del workspace:

```bash
docker compose up -d
```

Esto levanta **todos los servicios** incluyendo CiudadaniaBack y CiudadaniaFront:

| Servicio           | URL local                     |
|--------------------|-------------------------------|
| RegistraduriaFront | http://localhost:4200          |
| VotacionFront      | http://localhost:4201          |
| **CiudadaniaFront**| http://localhost:4202          |
| RegistraduriaBack  | http://localhost:8082          |
| VotacionBack       | http://localhost:8081          |
| **CiudadaniaBack** | http://localhost:8084          |

> En desarrollo local, `ciudadania-back` apunta a la misma DB `vote4tech` (compartida con `registraduria-back`). Los datos de elecciones/candidatos estarán disponibles sin necesidad de ejecutar el ETL.

Para reconstruir solo el nuevo portal:

```bash
docker compose up -d --build ciudadania-back ciudadania-front
```

---

## Endpoints del CiudadaniaBack

| Método | Endpoint                                | Descripción                              |
|--------|-----------------------------------------|------------------------------------------|
| GET    | `/eleccion/elecciones`                  | Lista todas las elecciones               |
| GET    | `/eleccion/{id}`                        | Obtiene una elección por ID              |
| GET    | `/candidato/candidatos`                 | Lista todos los candidatos               |
| GET    | `/candidato/{id}`                       | Obtiene un candidato por ID              |
| GET    | `/candidato/por-eleccion/{idEleccion}`  | Candidatos de una elección específica    |
| GET    | `/electoral/consulta-sancion?cedula=X`  | Consulta sanciones por cédula            |
| GET    | `/electoral/consulta-jurado/{cedula}`   | Asignaciones de jurado por cédula        |
| GET    | `/electoral/jurados-eleccion/{id}`      | Todos los jurados de una elección        |

---

## Flujo de Datos

```
RegistraduriaBack  →  bd_nacional_vote4tech  →  ETL Sincronización  →  bd_publica  →  CiudadaniaBack  →  CiudadaniaFront
```

1. El **RegistraduriaBack** registra datos (candidatos, elecciones, jurados, ciudadanos) en `bd_nacional_vote4tech`.
2. El **ETL** (`POST /sincronizacion/ejecutar`) copia las tablas relevantes a `bd_publica`.
3. El **CiudadaniaBack** expone `bd_publica` via REST (solo lectura).
4. El **CiudadaniaFront** consume el CiudadaniaBack para mostrar la información pública.

---

## Solución de Problemas

### `bd_publica` no existe
```bash
docker exec -it vote4tech-postgres psql -U admin_db_nacional \
  -c "CREATE DATABASE bd_publica OWNER admin_db_nacional;"
```

### El ETL de sincronización falla con error de FK
Asegurarse de que el RegistraduriaBack haya insertado datos en `bd_nacional_vote4tech` antes de ejecutar la sincronización.

### CiudadaniaFront no puede llegar al backend (error 502/504)
Verificar que `ciudadania-back` esté corriendo en el puerto 8084:
```bash
docker ps | grep ciudadania
curl http://10.43.100.131:8084/eleccion/elecciones
```

### Error de CORS en el browser
1. Obtener la URL exacta de Cloudflare (ver Paso 3.2).
2. Actualizar `CORS_ALLOWED_ORIGINS` en `Vote4TechPortalCiudadaniaBack/docker/docker-compose.prod.yml`.
3. Reconstruir: `docker compose -f docker/docker-compose.prod.yml up -d --build ciudadania-back`.
