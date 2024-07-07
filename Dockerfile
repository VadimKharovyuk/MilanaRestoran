# Use the official OpenJDK image as a base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the jar file to the container
COPY target/MilanaRestoran-0.0.1-SNAPSHOT.jar /app/MilanaRestoran.jar


# Expose the port that the application will run on
EXPOSE 4040

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/MilanaRestoran.jar"]
