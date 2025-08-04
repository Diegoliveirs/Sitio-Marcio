
FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /build
COPY pom.xml .                     # Copia o pom.xml para baixar as dependências primeiro
COPY src ./src                    # Copia o código-fonte da aplicação
RUN mvn clean package -DskipTests # Compila o projeto e gera o .jar


FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /build/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
