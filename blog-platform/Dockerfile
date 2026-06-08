# ==========================================
# STAGE 1: Build & Compile the Application
# ==========================================
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app

# Copy configuration files from the current folder context
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw mvnw

RUN chmod +x mvnw
RUN ./mvnw -q -DskipTests dependency:go-offline

# Copy source and package
COPY src src
RUN ./mvnw -q -DskipTests package

# ==========================================
# STAGE 2: Secure, Lightweight Runtime
# ==========================================
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENV JAVA_OPTS="-XX:MaxRAMPercentage=75.0 -XX:+UseG1GC"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]