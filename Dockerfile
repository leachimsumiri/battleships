FROM openjdk:11-jdk
LABEL maintainer="michaelirimus@gmail.com"
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} target/battleships-1.0-SNAPSHOT.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/target/battleships-1.0-SNAPSHOT.jar"]