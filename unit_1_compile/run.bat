@echo off
call console\start.bat
echo -------------------------------------------------------------------------------------------------
pause
cd maven
call setmaven.bat
cd app
call startmaven.bat
cd ..\..\
echo -------------------------------------------------------------------------------------------------
pause
cd ant
call setant.bat
call startant.bat
pause