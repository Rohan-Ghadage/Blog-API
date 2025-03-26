# Use OpenJDK as the base image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the built jar file into the container
COPY target/blog-app-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the application runs on
EXPOSE 5457

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
