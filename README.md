# HEAL ASU mHealth OAuth2 API
This is a Level 2 REST API. This is an authorization server for the HEAL ASU mHealth System. The authorization server is an OAuth2 implementation. The API has the following endpoints

1. Register endpoint
2. Token endpoint.
3. Validate endpoint.

This project has been customized from the Apache Oltu implementation of https://github.com/hasanozgan/apache-oltu-oauth2-provider-demo

# Required Dependencies :
```
1. Maven
2. Java 8
3. Servlet Container (Tomcat/Jboss).
4. Mysql DB
```

# Loding Data / Seeding Data to Mysql DB.
Import intial data to Mysql DB from the sql folder of the project.
```
command: create database promis_authorization_server

command: mysql -u<username> -p promis_authorization_server < <location-of-dump-file>

ex : mysql -uroot -p promis_authorization_server < ~/dump.sql
```

It is a maven java web application project. To build the project run the command from the location of *pom file*
```
mvn clean install
```

After successfully building the project. The war file can be found in the **target directory**.
