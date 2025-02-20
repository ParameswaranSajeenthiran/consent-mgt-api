# Use an official Maven image to build the WAR
FROM maven:3.6.3-jdk-8 AS builder

# Set working directory
WORKDIR /app

# Copy project files
COPY . .

# Build the WAR file
RUN mvn install -DskipTests

# # https://security.alpinelinux.org/vuln/CVE-2021-46848
# RUN apk add --upgrade libtasn1-progs
#
# # https://security.alpinelinux.org/vuln/CVE-2022-37434
# RUN apk update && apk upgrade zlib


# Create a new user with UID 10014
RUN addgroup -g 10014 choreo && \
    adduser  --disabled-password  --no-create-home --uid 10014 --ingroup choreo choreouser

USER 10014
FROM tomcat:9.0-jdk11
ENV CONTEXT_URL="https://your-storage-bucket/context.xml"

RUN cp -r $CATALINA_HOME/webapps.dist/* $CATALINA_HOME/webapps
COPY --from=builder /app/financial-services-accelerator/internal-webapps/org.wso2.financial.services.accelerator.consent.mgt.endpoint/target/*.war /usr/local/tomcat/webapps/consent.war
EXPOSE 8080
CMD ["/usr/local/tomcat/bin/catalina.sh", "run"]
