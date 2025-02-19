USER 10014
FROM tomcat:9.0-jdk11
ENV test_two='test_value'
# Create a new user with UID 10014
# RUN addgroup -g 10014 choreo && \
#     adduser  --disabled-password  --no-create-home --uid 10014 --ingroup choreo choreouser


RUN cp -r $CATALINA_HOME/webapps.dist/* $CATALINA_HOME/webapps
COPY consent.war /usr/local/tomcat/webapps/
EXPOSE 8080
CMD ["/usr/local/tomcat/bin/catalina.sh", "run"]

