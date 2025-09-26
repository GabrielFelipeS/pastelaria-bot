# Base com Java
FROM eclipse-temurin:21-alpine

# Diretório de trabalho
WORKDIR /app

# Copia o JAR do bot para dentro do container
COPY target/pastelariaBot-*.jar bot.jar
COPY .env .env

# Comando padrão para rodar o bot
CMD ["java", "-cp", "bot.jar", "br.com.pastelaria.Main"]
