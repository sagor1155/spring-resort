FROM openjdk:17-ea-jdk-oracle
MAINTAINER team9
WORKDIR /app
EXPOSE 8080
COPY ./target/resort-management-system.jar /app
CMD ["java", "-jar", "resort-management-system.jar"]