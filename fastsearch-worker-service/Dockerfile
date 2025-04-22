# Build stage
FROM maven:latest AS builder

WORKDIR /usr/local/app
COPY . .
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:23

WORKDIR /usr/local/app
COPY --from=builder /usr/local/app/target/*.jar app.jar
RUN useradd -m user
USER user

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
