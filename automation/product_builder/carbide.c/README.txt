How to Build Carbide.c++

On Windows XP

Install Merurial
Install Java jdk-1_5_0_15-windows-i586-p.exe
Install Cygwin in C:\cygwin (must also have cygwin\bin2)
Install Carbide.c ADT in %TOP%\ADT

edit build_carbide.bat
    set TOP
    set WORKSPACE
    set HG_SFL_HOST
    set HG_EPL_HOST
    set HG_REVISION
    set HG_USERNAME
    set HG_PASSWORD

run build_carbide.bat
