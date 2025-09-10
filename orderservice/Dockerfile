# Temel Java 21 görüntüsünü kullan
FROM openjdk:21-jdk-slim

# JAR dosyasının adı
ARG JAR_FILE=target/orderservice-0.0.1-SNAPSHOT.jar

# JAR dosyasını konteynerin içine kopyala
COPY ${JAR_FILE} app.jar

# Uygulamayı çalıştır
ENTRYPOINT ["java","-jar","/app.jar"]