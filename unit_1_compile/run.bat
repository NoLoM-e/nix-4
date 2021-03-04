@echo off
call console\start.bat
echo -------------------------------------------------------------------------------------------------
pause
cd maven\app
call startmaven.bat
cd ..\..\
echo -------------------------------------------------------------------------------------------------
pause
cd ant
call setant.bat
call startant.bat
pause