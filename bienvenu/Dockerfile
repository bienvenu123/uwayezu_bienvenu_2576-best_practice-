# Use the official Tomcat 8.5 base image
FROM tomcat:8.5-alpine

# Maintainer of the image
LABEL maintainer="uwayezubienv@gmail.com"

# Add the WAR file to the webapps directory in Tomcat
COPY /dist/bienvenu.war /usr/local/tomcat/webapps/

# Expose port 8080 (default port for Tomcat)
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]