# Stage 1: Build stage
FROM maven:3.9.8-eclipse-temurin-21 AS builder
WORKDIR /app

# Cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source and build package
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Runtime stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Create a non-root user
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copy jar from builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose port (configured in application.properties: 8081)
EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]
