echo off

rem This is a utility batch file to get stack information from the Carbide.c++.2.5.exe process.
rem In order to run it successfully, you will need to have a full JDK installed of version 1.6 or higher.
rem See http://java.sun.com/javase/downloads/index.jsp for download information.
rem If the Carbide process is found (and only one should be running) then stack information will be found in %OUTFILE%.


echo Executing jstack on Carbide.c++.exe...

set  JSTACK=c:\APPS\Java\jdk1.6.0_14\bin\jstack.exe
set  OUTFILE=.\stack.txt

if not exist %JSTACK% echo ERROR: jstack.exe cannot be found in %JSTACK%. jstack is provided under JDK 1.6 and higher, but is not distributed with Carbide. Please find the location of jstack and update the JSTACK variable in this batch file.  

if not exist %JSTACK% GOTO END


del %OUTFILE%

rem echo Carbide.c++.2.5.exe >  %outfile%

echo
echo Searching for Carbide.c++ process...

FOR /F "tokens=2" %%i IN ('tasklist /FI "IMAGENAME eq  Carbide.c++.2.5.exe"
/NH') DO @%JSTACK% %%i >  %OUTFILE%


echo
if exist %OUTFILE% echo Check for results in %OUTFILE%
if not exist %OUTFILE% echo ERROR: Results not written. Is Carbide.c++.2.5.exe running?

:END

pause

