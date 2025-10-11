FROM gradle:8.11-jdk21

WORKDIR /app

COPY /app .

RUN ./gradlew --no-daemon clean build

ENV JAVA_OPTS="-Xmx512M -Xms512M"
EXPOSE 7070

CMD ["java", "-jar", "build/libs/app-1.0-SNAPSHOT-all.jar"]