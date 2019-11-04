FROM maven:3.6.2-jdk-8 as target
WORKDIR /build

COPY ./pom.xml .
COPY ./mm-web/pom.xml ./mm-web/pom.xml
COPY ./mm-service/pom.xml ./mm-service/pom.xml
COPY ./mm-repo/pom.xml ./mm-repo/pom.xml
COPY ./mm-entity/pom.xml ./mm-entity/pom.xml
COPY ./mm-util/pom.xml ./mm-util/pom.xml

RUN mvn dependency:go-offline --fail-never

COPY ./mm-web/src ./mm-web/src
COPY ./mm-service/src ./mm-service/src
COPY ./mm-repo/src ./mm-repo/src
COPY ./mm-entity/src ./mm-entity/src
COPY ./mm-util/src ./mm-util/src


RUN mvn package -DskipTests=true

FROM openjdk:8-jdk-alpine
EXPOSE 8082
CMD exec java $JAVA_OPTS -jar /app/mm-web-0.0.1-SNAPSHOT.jar
COPY --from=target /build/mm-web/target/mm-web-0.0.1-SNAPSHOT.jar /app/mm-web-0.0.1-SNAPSHOT.jar



