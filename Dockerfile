FROM openjdk:21
WORKDIR /app

COPY target/StudyHub-0.0.1-SNAPSHOT.jar /app/StudyHub-0.0.1-SNAPSHOT.jar
EXPOSE 8080

ENTRYPOINT ["java","-jar","StudyHub-0.0.1-SNAPSHOT.jar"]