#Start with a base image containing Java runtime
FROM openjdk:17-jdk-slim as build


# Add the application's jar to the container
COPY target/gatewayserver-0.0.1-SNAPSHOT.jar gatewayserver-0.0.1-SNAPSHOT.jar

#execute the application
ENTRYPOINT ["java","-jar","/gatewayserver-0.0.1-SNAPSHOT.jar"]