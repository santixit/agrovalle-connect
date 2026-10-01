FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /workspace
COPY pom.xml .
COPY src ./src
RUN mvn --batch-mode --no-transfer-progress -DskipTests package

FROM eclipse-temurin:17-jre
WORKDIR /app
ENV PORT=8080
EXPOSE 8080
COPY --from=build /workspace/target/agrovalle-connect-0.0.1-SNAPSHOT.jar /app/agrovalle-connect.jar
ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT:-8080} -jar /app/agrovalle-connect.jar"]
