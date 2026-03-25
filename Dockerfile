# Use Maven image for building
FROM maven:3.9-openjdk-17 AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Use OpenJDK runtime image
FROM openjdk:17-jdk-slim

WORKDIR /app
COPY --from=build /app/target/phonepay-0.0.1-SNAPSHOT.jar app.jar

# Expose port (Render uses PORT env var)
EXPOSE 9091

# Run the application with PORT from environment
CMD ["sh", "-c", "java -jar app.jar --server.port=${PORT:-9091}"]
