# --- ETAPA 1: BUILD (Compilación con Gradle) ---
# Usamos una imagen que ya tiene Gradle instalado y configurado
FROM gradle:8.5-jdk17 AS build


WORKDIR /app

# Copiamos solo los archivos de configuración primero (para caché)
COPY build.gradle settings.gradle ./



# 3. Copiamos el código fuente (incluyendo las claves RSA en resources)
COPY src ./src

# 4. Compilamos el JAR final (saltando los tests para ir rápido)
RUN gradle bootJar -x test --no-daemon

# --- ETAPA 2: RUNTIME (Ejecución ligera) ---
# Usamos una imagen JRE (solo para ejecutar, pesa menos)
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Copiamos el JAR generado en la etapa anterior.
# Gradle suele ponerlo en build/libs/ y añade la versión (-SNAPSHOT)
COPY --from=build /app/build/libs/*-SNAPSHOT.jar users-microservice.jar

# Exponemos el puerto 8082
EXPOSE 8082

# Arrancamos la aplicación
ENTRYPOINT ["java", "-jar", "users-microservice.jar"]