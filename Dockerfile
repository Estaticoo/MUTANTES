# Etapa de construcción
FROM alpine:latest AS build

# Actualiza repositorios e instala JDK17
RUN apk update && \
    apk add --no-cache openjdk17

# Crea un directorio de trabajo y copia solo los archivos necesarios
WORKDIR /app
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle
COPY src ./src

# Da permisos de ejecución al wrapper de Gradle y construye el jar
RUN chmod +x ./gradlew && ./gradlew bootJar --no-daemon

# Etapa de ejecución
FROM openjdk:17-alpine

# Exponer el puerto de la aplicación
EXPOSE 9000

# Copiar el jar generado desde la etapa de build
COPY --from=build /app/build/libs/*.jar /app/app.jar

# Comando de entrada para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
