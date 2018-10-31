# Start with a base image containing Java runtime
FROM openjdk:8-jdk-alpine

# Add Maintainer Info
LABEL maintainer="fallon.lance@gmail.com"

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Add the application's jar to the container
ADD nfl-draft-notes-app-1.0.jar nfldraftmanager.jar

# Run the jar file 
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/nfldraftmanager.jar"]