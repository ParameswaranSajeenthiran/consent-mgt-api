FROM tomcat:9.0-jdk11
ENV test_two='test_value'
COPY context.xml /usr/local/tomcat/conf/Catalina/localhost/context.xml

RUN cp -r $CATALINA_HOME/webapps.dist/* $CATALINA_HOME/webapps
COPY consent.war /usr/local/tomcat/webapps/
EXPOSE 8080
CMD ["/usr/local/tomcat/bin/catalina.sh", "run"]