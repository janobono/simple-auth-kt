FROM maven:3-eclipse-temurin-17 as builder

WORKDIR app

COPY pom.xml .
COPY src ./src

RUN mvn clean install -DskipTests

FROM maven:3-eclipse-temurin-17 as finisher

WORKDIR app

COPY --from=builder app/target/*.jar app.jar

RUN java -Djarmode=layertools -jar app.jar extract

FROM eclipse-temurin:17-jre as production

WORKDIR /app

RUN addgroup --gid 1000 app; \
    adduser --disabled-password --gecos "" --home "$(pwd)" --ingroup app --no-create-home --uid 1000 app

RUN chown -R app:app /app

USER app

EXPOSE 8080

COPY --from=finisher app/dependencies/ ./
COPY --from=finisher app/spring-boot-loader/ ./
COPY --from=finisher app/snapshot-dependencies/ ./
COPY --from=finisher app/application/ ./

ENTRYPOINT ["java","org.springframework.boot.loader.JarLauncher"]
