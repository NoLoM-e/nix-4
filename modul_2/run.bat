@echo off
call mvn clean install
call java -Dfile.encoding=UTF-8 -jar target\modul_2.jar