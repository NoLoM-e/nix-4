#nix-4

1. use run.bat to run project

2. if it doesn't work in "unit_2_algorithmic_tasks" directory use commands:

    mkdir build
    mkdir build\classes
    javac -encoding UTF8 -sourcepath src -d build\classes src\com\company\Main.java
    java -cp build\classes\ com.company.Main