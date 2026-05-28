FROM eclipse-temurin:21-jdk-jammy AS builder

WORKDIR /build

COPY mvnw mvnw.cmd pom.xml ./
COPY .mvn .mvn
COPY src src

RUN chmod +x mvnw \
  && ./mvnw -B package -DskipTests

FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

ENV TZ=America/Fortaleza

COPY --from=builder /build/target/*.jar ./app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "./app.jar"]
