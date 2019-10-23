FROM openjdk:8

WORKDIR /app

COPY ./mm-web/target/mm-web-0.0.1-SNAPSHOT.jar /app

EXPOSE 8081

# Run the jar file
ENTRYPOINT ["java", "-jar", "/app/mm-web-0.0.1-SNAPSHOT.jar"]


# RUN mvn  package -DskipTests=true
