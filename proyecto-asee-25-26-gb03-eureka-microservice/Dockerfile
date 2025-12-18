# --- ETAPA 1: BUILD (Maven) ---
FROM maven:3.8.5-openjdk-17 AS build

# Creamos una carpeta de trabajo dentro del contenedor
WORKDIR /app

# 1. Copiamos el pom.xml y descargamos las librerías
# Hacemos esto antes de copiar el código para aprovechar la memoria caché de Docker
COPY pom.xml .
RUN mvn dependency:go-offline

# 2. Copiamos todo tu código fuente (la carpeta src)
COPY src ./src

# 3. Empaquetamos el proyecto (creamos el .jar) saltando los tests para ir rápido
# RUN mvn clean package -DskipTests
RUN mvn clean package -DskipTests -Dmaven.test.skip=true

# --- ETAPA 2: EJECUCIÓN (El entorno final ligero) ---
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

#  Instalamos curl para el healthcheck ---
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Copiamos solo el archivo .jar que fabricamos en la etapa 1
# El asterisco *.jar coge el archivo generado sea cual sea su nombre largo
COPY --from=build /app/target/*.jar eureka-server.jar

# Informamos a Docker que este contenedor usará el puerto 8761 (el que pusiste en properties)
EXPOSE 8761

# Comando para que arranque la aplicación
ENTRYPOINT ["java", "-jar", "eureka-server.jar"]