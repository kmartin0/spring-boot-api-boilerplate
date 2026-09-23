# syntax=docker/dockerfile:1

# Stage 1: Compile and build Spring Boot application
FROM eclipse-temurin:8-jdk AS build

# Set the working directory for the build stage
WORKDIR /workspace/app

# Copy Maven wrapper, project configuration, and source code to the working directory
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

# Build the application and extract the Spring Boot jar contents
RUN chmod +x mvnw && ./mvnw clean install -DskipTests
RUN mkdir -p target/dependency && cd target/dependency && jar -xf ../*.jar

# Stage 2: Run Spring Boot application
FROM eclipse-temurin:8-jre

# Create a temporary volume used by Spring Boot
VOLUME /tmp

# Copy the extracted Spring Boot application from the build stage
COPY --from=build /workspace/app/target/dependency/BOOT-INF/lib /app/lib
COPY --from=build /workspace/app/target/dependency/META-INF /app/META-INF
COPY --from=build /workspace/app/target/dependency/BOOT-INF/classes /app

# Expose the application port and start the Spring Boot application
EXPOSE 8080
ENTRYPOINT ["java", "-cp", "/app:/app/lib/*", "com.boilerplate.boilerplateapi.SpringBootBoilerplateApiApplication"]