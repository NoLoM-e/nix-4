#nix-4

1. use run.bat to run project

2. if it doesn't work in "unit_1_compile" derectory use commands:

	mkdir console\build
	mkdir console\build\classes
	javac -sourcepath console\src -d console\build\classes -cp console\libs\commons-lang3-3.11.jar;console\libs\commons-math3-3.6.1.jar console\src\com\company\Class2.java console\src\com\company\Main.java console\src\com\company\class1\Class1.java
	java -cp console\build\classes\;console\libs\commons-lang3-3.11.jar;console\libs\commons-math3-3.6.1.jar com.company.Main

	cd maven
	set M2_OPTS=-Xmx2G -Dfile.encoding=UTF-8
	set M2_HOME=apache-maven
	set PATH=%M2_HOME%\bin;%PATH%
	set CLASSPATH=
	echo Setting maven home to: %M2_HOME%
	mvn -v

	cd app
	mvn clean install
	java -jar target\app-1.0-SNAPSHOT.jar

	cd ..\..\

	cd ant
	set ANT_OPTS=-Xmx2G -Dfile.encoding=UTF-8
	set ANT_HOME=apache-ant
	set PATH=%ANT_HOME%\bin;%PATH%
	set CLASSPATH=
	ant -version

	ant clean
	ant compile
	ant jar
	ant run