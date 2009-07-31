@echo on

rem Run this script to build Carbide.c

set TOP=D:\work

rem Prerequisites
rem Install Merurial
rem Install Java jdk-1_5_0_15
rem Install Cygwin in C:\cygwin (must also have cygwin\bin2)
rem Install Carbide.c ADT in %TOP%\ADT

rem set WORKSPACE. The path to WORKSPACE must be short (about 30 characters)
set WORKSPACE=D:\work\build
if not exist %WORKSPACE% mkdir %WORKSPACE%

rem set Mercurial host
rem
rem Nokia Austin
rem set HG_SFL_HOST=dacvs002.americas.nokia.com/hg/Austin/MCL/sftools
rem set HG_EPL_HOST=dacvs002.americas.nokia.com/hg/Austin/MCL/sftools
rem
rem Nokia London
rem set HG_SFL_HOST=sylgbpwe0001.symbian.intra/hg/i_hg/sfl/MCL/sftools
rem set HG_EPL_HOST=sylgbpwe0001.symbian.intra/hg/i_hg/oss/MCL/sftools
rem
rem Symbian Foundation
set HG_SFL_HOST=https://developer.symbian.org/sfl/MCL/sftools
set HG_EPL_HOST=https://developer.symbian.org/oss/MCL/sftools

rem set Mercurial username and password
set HG_USERNAME=
set HG_PASSWORD=

rem set Mercurial revision
set HG_REVISION=RCL_2_1

chdir /D %TOP%
hg clone -r %HG_REVISION% http://%HG_USERNAME%:%HG_PASSWORD%@%HG_EPL_HOST%/dev/ide/carbidecpp/

chdir /D C:\cygwin\bin2
call cmd_call_bash.bat %TOP%/carbidecpp/automation/product_builder/carbide.c/build_carbide.sh --WORKSPACE:%WORKSPACE% --CARBIDE_VERSION_2DIGITS:2.1 --CARBIDE_VERSION_3DIGITS:2.1.1 --HG_SFL_HOST:%HG_SFL_HOST% --HG_EPL_HOST:%HG_EPL_HOST% --HG_REVISION:%HG_REVISION% --HG_USERNAME:%HG_USERNAME% --HG_PASSWORD:%HG_PASSWORD% --BASE_CARBIDE:%TOP%/ADT 
