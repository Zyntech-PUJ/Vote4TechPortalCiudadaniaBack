# ============================================================
# ETAPA 1: Build con Maven
# ============================================================
FROM maven:3.9-eclipse-temurin-21-alpine AS builder

WORKDIR /app

# Copia el pom.xml primero para cachear descarga de dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -q

# Copia el codigo fuente y empaqueta (sin tests para agilizar)
COPY src ./src
RUN mvn package -DskipTests -q

# ============================================================
# ETAPA 2: Runtime minimo con JRE 21
# ============================================================
FROM eclipse-temurin:21-jre-alpine AS production

WORKDIR /app

COPY --from=builder /app/target/portalciudadania-0.0.1-SNAPSHOT.jar app.jar

# Variables de entorno requeridas (se pasan al docker run o compose)
# DB_URL, DB_USER, DB_PASSWORD, CORS_ALLOWED_ORIGINS, PORT (opcional)
ENV PORT=8080

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
