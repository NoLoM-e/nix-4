mkdir console\build
mkdir console\build\classes
mkdir console\build\jar
javac -sourcepath console\src -d console\build\classes -cp console\libs\commons-lang3-3.11.jar;console\libs\commons-math3-3.6.1.jar console\src\com\company\Class2.java console\src\com\company\Main.java console\src\com\company\class1\Class1.java
java -cp console\build\classes\;console\libs\commons-lang3-3.11.jar;console\libs\commons-math3-3.6.1.jar com.company.Main
pause



commons-lang3-3.11.jar

commons-math3-3.6.1.jar