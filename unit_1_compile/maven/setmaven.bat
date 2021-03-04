@echo off
echo Setting maven...
set M2_OPTS=-Xmx2G -Dfile.encoding=UTF-8
set M2_HOME=%~dp0apache-maven
set PATH=%M2_HOME%\bin;%PATH%
rem deleting CLASSPATH as a workaround for PLA-8702
set CLASSPATH=
echo Setting maven home to: %M2_HOME%
mvn -v