# --- ETAPA 1: BUILD (Construcción) ---
FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /app

# 1. Dependencias
COPY pom.xml .
RUN mvn dependency:go-offline

# 2. Código
COPY src ./src

# 3. Empaquetar
RUN mvn clean package -DskipTests -Dmaven.test.skip=true

# --- ETAPA 2: RUNTIME (Ejecución) ---
FROM eclipse-temurin:17-jre-jammy

# 1. Instalamos las herramientas del sistema (Python3 y FFmpeg son obligatorios)
RUN apt-get update && apt-get install -y \
    python3 \
    ffmpeg \
    curl \
    && rm -rf /var/lib/apt/lists/*

# 2. Descargamos yt-dlp oficial en una ruta del sistema
RUN curl -L https://github.com/yt-dlp/yt-dlp/releases/latest/download/yt-dlp -o /usr/local/bin/yt-dlp
RUN chmod a+rx /usr/local/bin/yt-dlp


# 1. Creamos la carpeta "falsa" del entorno virtual
RUN mkdir -p /root/.musicfly_venv/bin

# 2. Creamos un enlace simbólico (acceso directo) 
RUN ln -s /usr/local/bin/yt-dlp /root/.musicfly_venv/bin/yt-dlp

# 3. Hacemos lo mismo para pip y python
RUN ln -s /usr/bin/python3 /root/.musicfly_venv/bin/python
# (Creamos un archivo pip falso que no hace nada o enlazamos a true, 
#  solo para que no falle si verifica que existe el archivo)
RUN touch /root/.musicfly_venv/bin/pip && chmod +x /root/.musicfly_venv/bin/pip

# ==============================================================

# 4. Carpeta de uploads con permisos
RUN mkdir -p /app/uploads && chmod 777 /app/uploads

WORKDIR /app

COPY --from=build /app/target/*.jar content-microservice.jar

EXPOSE 8001

ENTRYPOINT ["java", "-jar", "content-microservice.jar"]