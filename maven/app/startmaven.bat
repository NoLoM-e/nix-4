@echo off
echo Running maven project...
call mvn clean install
java -jar target\app-1.0-SNAPSHOT.jar