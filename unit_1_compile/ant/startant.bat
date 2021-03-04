@echo off
echo Running ant project...
call ant -version
call ant clean
call ant compile
call ant jar
call ant run