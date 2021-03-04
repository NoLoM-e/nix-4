echo Creating build folders...
mkdir console\build
mkdir console\build\classes
echo Compiling java project...
javac -sourcepath console\src -d console\build\classes -cp console\libs\commons-lang3-3.11.jar;console\libs\commons-math3-3.6.1.jar console\src\com\company\Class2.java console\src\com\company\Main.java console\src\com\company\class1\Class1.java
echo Staring java project...
java -cp console\build\classes\;console\libs\commons-lang3-3.11.jar;console\libs\commons-math3-3.6.1.jar com.company.Main
echo -------------------------------------------------------------------------------------------------
pause
cd maven\app
call mvn clean install
java -jar target\app-1.0-SNAPSHOT.jar
cd ..\..\
echo -------------------------------------------------------------------------------------------------
cd ant
call setant.bat
call ant -version
call ant clean
call ant compile
call ant jar
call ant run
pause