# --- ETAPA 1: BUILD (Construcción) ---
FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /app

# 1. Copiamos el pom.xml y descargamos dependencias
COPY pom.xml .
RUN mvn dependency:go-offline

# 2. Copiamos el código fuente
COPY src ./src

# 3. Empaquetamos el .jar (saltando tests)
# RUN mvn clean package -DskipTests
RUN mvn clean package -DskipTests -Dmaven.test.skip=true

# --- ETAPA 2: RUNTIME (Ejecución) ---
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Copiamos el jar generado. 
COPY --from=build /app/target/*.jar statistics-microservice.jar

# Exponemos el puerto 8083 
EXPOSE 8083

# Comando de arranque
ENTRYPOINT ["java", "-jar", "statistics-microservice.jar"]