# Etapa 1: Build do app com Maven
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

# Copia arquivos de configuração e código-fonte
COPY pom.xml .
COPY src ./src

# Faz o build sem rodar testes
RUN mvn clean package -DskipTests

# Etapa 2: Executar app em uma imagem menor
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copia o jar gerado na etapa de build
COPY --from=builder /app/target/*.jar app.jar

# Render injeta a porta na env $PORT
ENV PORT=8080
EXPOSE 8080

# Comando para rodar o Spring Boot
CMD ["java", "-jar", "app.jar"]
