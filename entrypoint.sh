#!/bin/bash
set -e
export TRIVY_DISABLE_VEX_NOTICE=true

# Ensure /usr/local/tomcat/webapps exists
mkdir -p /usr/local/tomcat/webapps

# Copy the WAR file if it doesn't already exist
if [ ! -f "/usr/local/tomcat/webapps/consent.war" ]; then
    echo "Deploying ROOT.war..."
    cp /usr/local/tomcat/consent.war /usr/local/tomcat/webapps/consent.war
fi

# Start Tomcat
exec /usr/local/tomcat/bin/catalina.sh run