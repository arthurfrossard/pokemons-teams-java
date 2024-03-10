# Usa uma imagem base do OpenJDK para Java 17
FROM openjdk:17-jdk-alpine

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia o arquivo JAR da aplicação para o diretório de trabalho do contêiner
COPY target/pokemons-teams-0.0.1-SNAPSHOT.jar pokemons-teams.jar

# Expõe a porta 8080, que é a porta em que a aplicação Spring Boot será executada
EXPOSE 8080

# Comando para executar a aplicação quando o contêiner for iniciado
CMD ["java", "-jar", "pokemons-teams.jar"]
