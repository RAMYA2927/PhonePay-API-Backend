# Use Maven image for building
FROM maven:3-openjdk-17 AS build

WORKDIR /app

# Copy pom.xml first for better Docker layer caching
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests -B

# Use Eclipse Temurin runtime image (more reliable)
FROM eclipse-temurin:17-jre-alpine

# Install curl for health checks
RUN apk add --no-cache curl

WORKDIR /app

# Copy the built jar
COPY --from=build /app/target/phonepay-*.jar app.jar

# Expose port
EXPOSE 9091

# Add health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:9091/actuator/health || exit 1

# Run the application with PORT from environment
CMD ["sh", "-c", "java -jar app.jar --server.port=${PORT:-9091}"]
