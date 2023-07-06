#Start with a base image containing Java runtime
FROM openjdk:17-jdk-slim as build


# Add the application's jar to the container
COPY target/usermanagement-0.0.1-SNAPSHOT.jar usermanagement-0.0.1-SNAPSHOT.jar

#execute the application
ENTRYPOINT ["java","-jar","/usermanagement-0.0.1-SNAPSHOT.jar"]