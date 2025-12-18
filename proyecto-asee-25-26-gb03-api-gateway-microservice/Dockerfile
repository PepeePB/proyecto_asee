# --- ETAPA 1: BUILD (Maven) ---
FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /app

# 1. Copiamos pom.xml y descargamos dependencias
COPY pom.xml .
RUN mvn dependency:go-offline

# 2. Copiamos el código fuente
COPY src ./src

# 3. Empaquetamos (saltando tests)
# RUN mvn clean package -DskipTests
RUN mvn clean package -DskipTests -Dmaven.test.skip=true

# --- ETAPA 2: RUNTIME (Java 17 Slim) ---
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Copiamos el JAR generado
COPY --from=build /app/target/*.jar api-gateway.jar

# Exponemos el puerto 8080
EXPOSE 8080

# Arrancamos
ENTRYPOINT ["java", "-jar", "api-gateway.jar"]