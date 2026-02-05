# Build stage
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy parent POM first
COPY ../pom.xml ../pom.xml

# Copy module POM and source
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy the JAR from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port
EXPOSE 8083

# Health check configuration
HEALTHCHECK --interval=10s --timeout=5s --retries=10 --start-period=20s \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8083/actuator/health || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
