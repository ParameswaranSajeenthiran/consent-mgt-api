# Use an official Maven image to build the WAR
FROM maven:3.9.9-eclipse-temurin-17 AS builder

# Set working directory
WORKDIR /app

# Copy project files
COPY . .

# Build the WAR file
RUN mvn clean package -DskipTests

# Create a new user with UID 10014
# RUN addgroup -g 10014 choreo && \
#     adduser  --disabled-password  --no-create-home --uid 10014 --ingroup choreo choreouser

# USER 10014
FROM tomcat:9.0-jdk11

# RUN cp -r $CATALINA_HOME/webapps.dist/* $CATALINA_HOME/webapps
COPY --from=builder /app/financial-services-accelerator/internal-webapps/org.wso2.financial.services.accelerator.consent.mgt.endpoint/target/consent.war /usr/local/tomcat/consent.war
# Copy the startup script
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh
EXPOSE 8080
# USER 10014
ENTRYPOINT ["/entrypoint.sh"]