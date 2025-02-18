# Use an official Maven image to build the WAR
FROM maven:3.9.5-eclipse-temurin-17 AS builder

# Set working directory
WORKDIR /app

# Copy project files
COPY . .

# Build the WAR file
RUN mvn clean package -DskipTests

FROM tomcat:9.0-jdk11
RUN cp -r $CATALINA_HOME/webapps.dist/* $CATALINA_HOME/webapps
COPY --from=builder /app/financial-services-accelerator/internal-webapps/org.wso2.financial.services.accelerator.consent.mgt.endpoint/target/*.war /usr/local/tomcat/webapps/consent.war
EXPOSE 8080
CMD ["/usr/local/tomcat/bin/catalina.sh", "run"]
