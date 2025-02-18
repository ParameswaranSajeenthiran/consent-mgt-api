#!/bin/bash

# Define variables
WAR_FILE="target/api#fs#consent.war"
TOMCAT_WEBAPPS="/var/lib/tomcat9/webapps"
TOMCAT_BIN="/path/to/tomcat/bin"

# Build the project
echo "Building the project..."
mvn clean package

# Check if build was successful
if [ $? -eq 0 ]; then
    echo "Build successful. Deploying..."
else
    echo "Build failed. Exiting."
    exit 1
fi

# Copy the .war file to Tomcat
sudo cp $WAR_FILE $TOMCAT_WEBAPPS/

# Restart Tomcat
sudo systemctl restart tomcat9

echo "Deployment complete!"
