# syntax=docker/dockerfile:1

FROM eclipse-temurin:17-jdk-alpine as base
ENV LANG C.UTF-8

# The /app directory should act as the main application directory
WORKDIR /app

# To get the Maven wrapper and our pom.xml file into our image
# Copy local directories to the current local directory of our docker image (/app)
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Install dependencies into the image
RUN ./mvnw dependency:resolve

# Add our source code into the image
COPY src ./src


FROM base as development
# Tell Docker what command we want to run when our image is executed inside a container
CMD ["mvn", "clean", "install"]

FROM base as build
# RUN ./mvnw package
CMD ["./mvnw", "spring-boot:run", "-Dspring-boot.run.profiles=dev"]

# FROM eclipse-temurin:17-jre-jammy as production
# EXPOSE 8080
# COPY --from=build /app/target/onepost-*.jar /onepost.jar
# CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/onepost.jar", "--spring.profiles.active=prod"]