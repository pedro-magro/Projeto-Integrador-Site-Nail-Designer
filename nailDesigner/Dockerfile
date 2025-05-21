# Etapa 1: imagem base do Java (use Java 17, que é comum com Spring Boot)
FROM eclipse-temurin:17-jdk-alpine

# Etapa 2: define o diretório de trabalho dentro do container
WORKDIR /app

# Etapa 3: copia o jar gerado para dentro do container
COPY target/nailDesigner-0.0.1-SNAPSHOT.jar app.jar

# Etapa 4: expõe a porta 8080 (a que o Spring Boot usa por padrão)
EXPOSE 8080

# Etapa 5: define o comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]