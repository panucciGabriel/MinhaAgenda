# Estágio 1: Build da aplicação com Maven
# Usamos uma imagem que já tem o JDK e o Maven instalados para compilar nosso projeto.
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Define o diretório de trabalho dentro do container.
WORKDIR /app

# Copia o pom.xml primeiro para aproveitar o cache de dependências do Docker.
# Se o pom.xml não mudar, o Docker não baixa as dependências de novo.
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o resto do código-fonte do projeto.
COPY src ./src

# Executa o build do Maven, que vai compilar o código e gerar o arquivo .jar em /app/target/
# O -DskipTests pula a execução dos nossos testes automatizados durante o build do Docker.
RUN mvn package -DskipTests


# Estágio 2: Execução da aplicação
# Agora, usamos uma imagem base muito menor, que contém apenas o Java para RODAR, não para compilar.
FROM eclipse-temurin:21-jre-jammy

# Define o diretório de trabalho.
WORKDIR /app

# Copia apenas o arquivo .jar gerado no estágio de build para a nossa imagem final.
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta 8080, que é a porta que nossa aplicação Spring usa.
EXPOSE 8080

# Comando que será executado quando o container iniciar.
ENTRYPOINT ["java", "-jar", "app.jar"]