# ----------- FASE DE BUILD -----------
FROM gradle:8.7-jdk17 AS build           # Gradle ≥ 8 + JDK 17
WORKDIR /workspace

# Copiamos solo lo necesario:
COPY --chown=gradle:gradle . .

# Compila y genera el fat-jar
RUN ./gradlew clean bootJar --no-daemon

# ----------- FASE DE RUNTIME ---------
FROM eclipse-temurin:17-jre-alpine       # JRE liviano
WORKDIR /app

# Copiamos “el” jar que Gradle acaba de generar
COPY --from=build /workspace/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]