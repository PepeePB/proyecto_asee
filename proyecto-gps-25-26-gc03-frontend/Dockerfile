# Usamos Nginx Alpine (muy ligero)
FROM nginx:alpine

# Borramos la web por defecto de Nginx
RUN rm -rf /usr/share/nginx/html/*

# Copiamos tus archivos. Gracias al .dockerignore no copiará node_modules
COPY . /usr/share/nginx/html

# Internamente, Nginx escuchará en el puerto 80 (el estándar)
EXPOSE 80