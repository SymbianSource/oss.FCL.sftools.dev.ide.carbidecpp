# ==============================================================================
# Generated by qmake (2.01a) (Qt 4.4.4) on: Tue Jan 13 10:32:50 2009
# This file is generated by qmake and should not be modified by the
# user.
#  Name        : qt_build_tools.mk
#  Part of     : 
#  Description : This file is used for creating .cpp files using moc tool,
#  creating .cpp files using uic tool and creating .cpp files using rcc tool.
#  Version     : 
#
# ==============================================================================

ifeq (WINS,$(findstring WINS, $(PLATFORM)))
ZDIR=\S60\devices\S60_3rd_FP2_SDK_v1.1\epoc32\release\$(PLATFORM)\$(CFG)\Z
else
ZDIR=\S60\devices\S60_3rd_FP2_SDK_v1.1\epoc32\data\z
endif

TARGET = test
DEFINES	 = -DSYMBIAN -DUNICODE -DQT_KEYPAD_NAVIGATION -DQT_GUI_LIB -DQT_CORE_LIB
INCPATH	 =  -I"..\..\..\..\..\..\..\..\Qt\qt-embedded-s60-commercial-src-4.4.4-snapshot-20081204\mkspecs\default"  -I"..\..\..\..\..\..\..\..\Qt\qt-embedded-s60-commercial-src-4.4.4-snapshot-20081204\mkspecs\default\tmp"  -I"..\..\..\..\..\..\..\..\Qt\qt-embedded-s60-commercial-src-4.4.4-snapshot-20081204\include\QtCore"  -I"..\..\..\..\..\..\..\..\Qt\qt-embedded-s60-commercial-src-4.4.4-snapshot-20081204\include\QtCore\tmp"  -I"..\..\..\..\..\..\..\..\Qt\qt-embedded-s60-commercial-src-4.4.4-snapshot-20081204\include\QtGui"  -I"..\..\..\..\..\..\..\..\Qt\qt-embedded-s60-commercial-src-4.4.4-snapshot-20081204\include\QtGui\tmp"  -I"..\..\..\..\..\..\..\..\Qt\qt-embedded-s60-commercial-src-4.4.4-snapshot-20081204\include"  -I"..\..\..\..\..\..\..\..\Qt\qt-embedded-s60-commercial-src-4.4.4-snapshot-20081204\include\tmp"  -I"..\..\..\..\..\..\..\..\S60\devices\S60_3rd_FP2_SDK_v1.1\epoc32\include"  -I"..\..\..\..\..\..\..\..\S60\devices\S60_3rd_FP2_SDK_v1.1\epoc32\include\stdapis"  -I"..\..\..\..\..\..\..\..\S60\devices\S60_3rd_FP2_SDK_v1.1\epoc32\include\stdapis\sys"  -I"."  -I"tmp" 

do_nothing :
	@rem do_nothing

MAKMAKE : create_temps 


BLD : do_nothing

CLEAN : 
	 -@ if EXIST  "..\..\..\..\..\..\..\..\Qt\qt-embedded-s60-commercial-src-4.4.4-snapshot-20081204\mkspecs\default\tmp" rmdir  /S /Q  "..\..\..\..\..\..\..\..\Qt\qt-embedded-s60-commercial-src-4.4.4-snapshot-20081204\mkspecs\default\tmp" 
	 -@ if EXIST  "..\..\..\..\..\..\..\..\Qt\qt-embedded-s60-commercial-src-4.4.4-snapshot-20081204\mkspecs\default\tmp\tmp" rmdir  /S /Q  "..\..\..\..\..\..\..\..\Qt\qt-embedded-s60-commercial-src-4.4.4-snapshot-20081204\mkspecs\default\tmp\tmp" 
	 -@ if EXIST  "..\..\..\..\..\..\..\..\Qt\qt-embedded-s60-commercial-src-4.4.4-snapshot-20081204\include\QtCore\tmp" rmdir  /S /Q  "..\..\..\..\..\..\..\..\Qt\qt-embedded-s60-commercial-src-4.4.4-snapshot-20081204\include\QtCore\tmp" 
	 -@ if EXIST  "..\..\..\..\..\..\..\..\Qt\qt-embedded-s60-commercial-src-4.4.4-snapshot-20081204\include\QtCore\tmp\tmp" rmdir  /S /Q  "..\..\..\..\..\..\..\..\Qt\qt-embedded-s60-commercial-src-4.4.4-snapshot-20081204\include\QtCore\tmp\tmp" 
	 -@ if EXIST  "..\..\..\..\..\..\..\..\Qt\qt-embedded-s60-commercial-src-4.4.4-snapshot-20081204\include\QtGui\tmp" rmdir  /S /Q  "..\..\..\..\..\..\..\..\Qt\qt-embedded-s60-commercial-src-4.4.4-snapshot-20081204\include\QtGui\tmp" 
	 -@ if EXIST  "..\..\..\..\..\..\..\..\Qt\qt-embedded-s60-commercial-src-4.4.4-snapshot-20081204\include\QtGui\tmp\tmp" rmdir  /S /Q  "..\..\..\..\..\..\..\..\Qt\qt-embedded-s60-commercial-src-4.4.4-snapshot-20081204\include\QtGui\tmp\tmp" 
	 -@ if EXIST  "..\..\..\..\..\..\..\..\Qt\qt-embedded-s60-commercial-src-4.4.4-snapshot-20081204\include\tmp" rmdir  /S /Q  "..\..\..\..\..\..\..\..\Qt\qt-embedded-s60-commercial-src-4.4.4-snapshot-20081204\include\tmp" 
	 -@ if EXIST  "..\..\..\..\..\..\..\..\Qt\qt-embedded-s60-commercial-src-4.4.4-snapshot-20081204\include\tmp\tmp" rmdir  /S /Q  "..\..\..\..\..\..\..\..\Qt\qt-embedded-s60-commercial-src-4.4.4-snapshot-20081204\include\tmp\tmp" 
	 -@ if EXIST  "..\..\..\..\..\..\..\..\S60\devices\S60_3rd_FP2_SDK_v1.1\epoc32\include\tmp" rmdir  /S /Q  "..\..\..\..\..\..\..\..\S60\devices\S60_3rd_FP2_SDK_v1.1\epoc32\include\tmp" 
	 -@ if EXIST  "..\..\..\..\..\..\..\..\S60\devices\S60_3rd_FP2_SDK_v1.1\epoc32\include\stdapis\tmp" rmdir  /S /Q  "..\..\..\..\..\..\..\..\S60\devices\S60_3rd_FP2_SDK_v1.1\epoc32\include\stdapis\tmp" 
	 -@ if EXIST  "..\..\..\..\..\..\..\..\S60\devices\S60_3rd_FP2_SDK_v1.1\epoc32\include\stdapis\sys\tmp" rmdir  /S /Q  "..\..\..\..\..\..\..\..\S60\devices\S60_3rd_FP2_SDK_v1.1\epoc32\include\stdapis\sys\tmp" 
	 -@ if EXIST  ".\tmp" rmdir  /S /Q  ".\tmp" 
	 -@ if EXIST  "tmp\tmp" rmdir  /S /Q  "tmp\tmp" 

LIB : do_nothing

CLEANLIB : do_nothing

RESOURCE : do_nothing

FREEZE : do_nothing

SAVESPACE : do_nothing

RELEASABLES :

FINAL : do_nothing
create_temps:
	-@ if NOT EXIST "..\..\..\..\..\..\..\..\Qt\qt-embedded-s60-commercial-src-4.4.4-snapshot-20081204\mkspecs\default\tmp" mkdir "..\..\..\..\..\..\..\..\Qt\qt-embedded-s60-commercial-src-4.4.4-snapshot-20081204\mkspecs\default\tmp" 
	-@ if NOT EXIST "..\..\..\..\..\..\..\..\Qt\qt-embedded-s60-commercial-src-4.4.4-snapshot-20081204\mkspecs\default\tmp\tmp" mkdir "..\..\..\..\..\..\..\..\Qt\qt-embedded-s60-commercial-src-4.4.4-snapshot-20081204\mkspecs\default\tmp\tmp" 
	-@ if NOT EXIST "..\..\..\..\..\..\..\..\Qt\qt-embedded-s60-commercial-src-4.4.4-snapshot-20081204\include\QtCore\tmp" mkdir "..\..\..\..\..\..\..\..\Qt\qt-embedded-s60-commercial-src-4.4.4-snapshot-20081204\include\QtCore\tmp" 
	-@ if NOT EXIST "..\..\..\..\..\..\..\..\Qt\qt-embedded-s60-commercial-src-4.4.4-snapshot-20081204\include\QtCore\tmp\tmp" mkdir "..\..\..\..\..\..\..\..\Qt\qt-embedded-s60-commercial-src-4.4.4-snapshot-20081204\include\QtCore\tmp\tmp" 
	-@ if NOT EXIST "..\..\..\..\..\..\..\..\Qt\qt-embedded-s60-commercial-src-4.4.4-snapshot-20081204\include\QtGui\tmp" mkdir "..\..\..\..\..\..\..\..\Qt\qt-embedded-s60-commercial-src-4.4.4-snapshot-20081204\include\QtGui\tmp" 
	-@ if NOT EXIST "..\..\..\..\..\..\..\..\Qt\qt-embedded-s60-commercial-src-4.4.4-snapshot-20081204\include\QtGui\tmp\tmp" mkdir "..\..\..\..\..\..\..\..\Qt\qt-embedded-s60-commercial-src-4.4.4-snapshot-20081204\include\QtGui\tmp\tmp" 
	-@ if NOT EXIST "..\..\..\..\..\..\..\..\Qt\qt-embedded-s60-commercial-src-4.4.4-snapshot-20081204\include\tmp" mkdir "..\..\..\..\..\..\..\..\Qt\qt-embedded-s60-commercial-src-4.4.4-snapshot-20081204\include\tmp" 
	-@ if NOT EXIST "..\..\..\..\..\..\..\..\Qt\qt-embedded-s60-commercial-src-4.4.4-snapshot-20081204\include\tmp\tmp" mkdir "..\..\..\..\..\..\..\..\Qt\qt-embedded-s60-commercial-src-4.4.4-snapshot-20081204\include\tmp\tmp" 
	-@ if NOT EXIST "..\..\..\..\..\..\..\..\S60\devices\S60_3rd_FP2_SDK_v1.1\epoc32\include\tmp" mkdir "..\..\..\..\..\..\..\..\S60\devices\S60_3rd_FP2_SDK_v1.1\epoc32\include\tmp" 
	-@ if NOT EXIST "..\..\..\..\..\..\..\..\S60\devices\S60_3rd_FP2_SDK_v1.1\epoc32\include\stdapis\tmp" mkdir "..\..\..\..\..\..\..\..\S60\devices\S60_3rd_FP2_SDK_v1.1\epoc32\include\stdapis\tmp" 
	-@ if NOT EXIST "..\..\..\..\..\..\..\..\S60\devices\S60_3rd_FP2_SDK_v1.1\epoc32\include\stdapis\sys\tmp" mkdir "..\..\..\..\..\..\..\..\S60\devices\S60_3rd_FP2_SDK_v1.1\epoc32\include\stdapis\sys\tmp" 
	-@ if NOT EXIST ".\tmp" mkdir ".\tmp" 
	-@ if NOT EXIST "tmp\tmp" mkdir "tmp\tmp" 

