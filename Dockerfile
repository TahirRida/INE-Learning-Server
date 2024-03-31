# Stage 1: Build stage
FROM maven:3.8.3-openjdk-11 AS build
WORKDIR /app
COPY . /app
RUN mvn clean package -DskipTests

# Stage 2: Production stage
FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=build /app/target/Backend-0.0.1-SNAPSHOT.jar /app/Backend-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD ["java", "-jar", "Backend-0.0.1-SNAPSHOT.jar"]

