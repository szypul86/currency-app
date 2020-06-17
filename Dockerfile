FROM openjdk:11.0.7-jdk
EXPOSE 8080
ADD /target/currency-app-0.0.1-SNAPSHOT.jar currency-app-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","currency-app-0.0.1-SNAPSHOT.jar"]
