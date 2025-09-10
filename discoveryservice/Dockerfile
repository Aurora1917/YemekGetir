# Temel Java 21 görüntüsünü kullan
FROM openjdk:21-jdk-slim

# Konteynerin içinde healthcheck için gerekli olan curl paketini kur
RUN apt-get update && apt-get install -y curl

# JAR dosyasının adı
ARG JAR_FILE=target/discoveryservice-1.0.0-SNAPSHOT.jar

# JAR dosyasını konteynerin içine kopyala
COPY ${JAR_FILE} app.jar

# Uygulamayı çalıştır
ENTRYPOINT ["java","-jar","/app.jar"]