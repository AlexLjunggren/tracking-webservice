FROM tomcat:9-jdk8

COPY target/tracking-webservice-*.war $CATALINA_HOME/webapps/ROOT.war

CMD ["catalina.sh", "run"]
