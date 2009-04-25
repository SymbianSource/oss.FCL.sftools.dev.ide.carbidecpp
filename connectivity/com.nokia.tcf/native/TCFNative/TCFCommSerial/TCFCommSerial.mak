# Microsoft Developer Studio Generated NMAKE File, Based on TCFCommSerial.dsp
!IF "$(CFG)" == ""
CFG=TCFCommSerial - Win32 Debug
!MESSAGE No configuration specified. Defaulting to TCFCommSerial - Win32 Debug.
!ENDIF 

!IF "$(CFG)" != "TCFCommSerial - Win32 Release" && "$(CFG)" != "TCFCommSerial - Win32 Debug"
!MESSAGE Invalid configuration "$(CFG)" specified.
!MESSAGE You can specify a configuration when running NMAKE
!MESSAGE by defining the macro CFG on the command line. For example:
!MESSAGE 
!MESSAGE NMAKE /f "TCFCommSerial.mak" CFG="TCFCommSerial - Win32 Debug"
!MESSAGE 
!MESSAGE Possible choices for configuration are:
!MESSAGE 
!MESSAGE "TCFCommSerial - Win32 Release" (based on "Win32 (x86) Dynamic-Link Library")
!MESSAGE "TCFCommSerial - Win32 Debug" (based on "Win32 (x86) Dynamic-Link Library")
!MESSAGE 
!ERROR An invalid configuration is specified.
!ENDIF 

!IF "$(OS)" == "Windows_NT"
NULL=
!ELSE 
NULL=nul
!ENDIF 

!IF  "$(CFG)" == "TCFCommSerial - Win32 Release"

OUTDIR=.\Release
INTDIR=.\Release
# Begin Custom Macros
OutDir=.\Release
# End Custom Macros

ALL : "$(OUTDIR)\TCFCommSerial.dll"


CLEAN :
	-@erase "$(INTDIR)\BaseCom.obj"
	-@erase "$(INTDIR)\mutex.obj"
	-@erase "$(INTDIR)\RealSerialComm.obj"
	-@erase "$(INTDIR)\StdAfx.obj"
	-@erase "$(INTDIR)\TCDebugLog.obj"
	-@erase "$(INTDIR)\TCFCommSerial.obj"
	-@erase "$(INTDIR)\TCFCommSerial.pch"
	-@erase "$(INTDIR)\vc60.idb"
	-@erase "$(OUTDIR)\TCFCommSerial.dll"
	-@erase "$(OUTDIR)\TCFCommSerial.exp"
	-@erase "$(OUTDIR)\TCFCommSerial.lib"
	-@erase "$(OUTDIR)\TCFCommSerial.map"

"$(OUTDIR)" :
    if not exist "$(OUTDIR)/$(NULL)" mkdir "$(OUTDIR)"

CPP=cl.exe
CPP_PROJ=/nologo /Zp2 /MT /W3 /GX /O2 /I "..\TCFServer" /I "..\Common\Headers" /D "WIN32" /D "NDEBUG" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "TCFCOMMSERIAL_EXPORTS" /Fp"$(INTDIR)\TCFCommSerial.pch" /Yu"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /c 

.c{$(INTDIR)}.obj::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cpp{$(INTDIR)}.obj::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cxx{$(INTDIR)}.obj::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.c{$(INTDIR)}.sbr::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cpp{$(INTDIR)}.sbr::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cxx{$(INTDIR)}.sbr::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

MTL=midl.exe
MTL_PROJ=/nologo /D "NDEBUG" /mktyplib203 /win32 
RSC=rc.exe
BSC32=bscmake.exe
BSC32_FLAGS=/nologo /o"$(OUTDIR)\TCFCommSerial.bsc" 
BSC32_SBRS= \
	
LINK32=link.exe
LINK32_FLAGS=kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib /nologo /dll /incremental:no /pdb:"$(OUTDIR)\TCFCommSerial.pdb" /map:"$(INTDIR)\TCFCommSerial.map" /machine:I386 /out:"$(OUTDIR)\TCFCommSerial.dll" /implib:"$(OUTDIR)\TCFCommSerial.lib" 
LINK32_OBJS= \
	"$(INTDIR)\BaseCom.obj" \
	"$(INTDIR)\mutex.obj" \
	"$(INTDIR)\RealSerialComm.obj" \
	"$(INTDIR)\StdAfx.obj" \
	"$(INTDIR)\TCDebugLog.obj" \
	"$(INTDIR)\TCFCommSerial.obj"

"$(OUTDIR)\TCFCommSerial.dll" : "$(OUTDIR)" $(DEF_FILE) $(LINK32_OBJS)
    $(LINK32) @<<
  $(LINK32_FLAGS) $(LINK32_OBJS)
<<

SOURCE="$(InputPath)"
PostBuild_Desc=copy libs
DS_POSTBUILD_DEP=$(INTDIR)\postbld.dep

ALL : $(DS_POSTBUILD_DEP)

# Begin Custom Macros
OutDir=.\Release
# End Custom Macros

$(DS_POSTBUILD_DEP) : "$(OUTDIR)\TCFCommSerial.dll"
   copyBinaries Release
	echo Helper for Post-build step > "$(DS_POSTBUILD_DEP)"

!ELSEIF  "$(CFG)" == "TCFCommSerial - Win32 Debug"

OUTDIR=.\Debug
INTDIR=.\Debug
# Begin Custom Macros
OutDir=.\Debug
# End Custom Macros

ALL : "$(OUTDIR)\TCFCommSerial.dll"


CLEAN :
	-@erase "$(INTDIR)\BaseCom.obj"
	-@erase "$(INTDIR)\mutex.obj"
	-@erase "$(INTDIR)\RealSerialComm.obj"
	-@erase "$(INTDIR)\StdAfx.obj"
	-@erase "$(INTDIR)\TCDebugLog.obj"
	-@erase "$(INTDIR)\TCFCommSerial.obj"
	-@erase "$(INTDIR)\TCFCommSerial.pch"
	-@erase "$(INTDIR)\vc60.idb"
	-@erase "$(INTDIR)\vc60.pdb"
	-@erase "$(OUTDIR)\TCFCommSerial.dll"
	-@erase "$(OUTDIR)\TCFCommSerial.exp"
	-@erase "$(OUTDIR)\TCFCommSerial.ilk"
	-@erase "$(OUTDIR)\TCFCommSerial.lib"
	-@erase "$(OUTDIR)\TCFCommSerial.map"
	-@erase "$(OUTDIR)\TCFCommSerial.pdb"

"$(OUTDIR)" :
    if not exist "$(OUTDIR)/$(NULL)" mkdir "$(OUTDIR)"

CPP=cl.exe
CPP_PROJ=/nologo /Zp2 /MTd /W3 /Gm /GX /ZI /Od /I "..\TCFServer" /I "..\Common\Headers" /D "WIN32" /D "_DEBUG" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "TCFCOMMSERIAL_EXPORTS" /Fp"$(INTDIR)\TCFCommSerial.pch" /Yu"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /GZ /c 

.c{$(INTDIR)}.obj::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cpp{$(INTDIR)}.obj::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cxx{$(INTDIR)}.obj::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.c{$(INTDIR)}.sbr::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cpp{$(INTDIR)}.sbr::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cxx{$(INTDIR)}.sbr::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

MTL=midl.exe
MTL_PROJ=/nologo /D "_DEBUG" /mktyplib203 /win32 
RSC=rc.exe
BSC32=bscmake.exe
BSC32_FLAGS=/nologo /o"$(OUTDIR)\TCFCommSerial.bsc" 
BSC32_SBRS= \
	
LINK32=link.exe
LINK32_FLAGS=kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib /nologo /dll /incremental:yes /pdb:"$(OUTDIR)\TCFCommSerial.pdb" /map:"$(INTDIR)\TCFCommSerial.map" /debug /machine:I386 /out:"$(OUTDIR)\TCFCommSerial.dll" /implib:"$(OUTDIR)\TCFCommSerial.lib" /pdbtype:sept 
LINK32_OBJS= \
	"$(INTDIR)\BaseCom.obj" \
	"$(INTDIR)\mutex.obj" \
	"$(INTDIR)\RealSerialComm.obj" \
	"$(INTDIR)\StdAfx.obj" \
	"$(INTDIR)\TCDebugLog.obj" \
	"$(INTDIR)\TCFCommSerial.obj"

"$(OUTDIR)\TCFCommSerial.dll" : "$(OUTDIR)" $(DEF_FILE) $(LINK32_OBJS)
    $(LINK32) @<<
  $(LINK32_FLAGS) $(LINK32_OBJS)
<<

SOURCE="$(InputPath)"
PostBuild_Desc=copy libs
DS_POSTBUILD_DEP=$(INTDIR)\postbld.dep

ALL : $(DS_POSTBUILD_DEP)

# Begin Custom Macros
OutDir=.\Debug
# End Custom Macros

$(DS_POSTBUILD_DEP) : "$(OUTDIR)\TCFCommSerial.dll"
   copyBinaries Debug
	echo Helper for Post-build step > "$(DS_POSTBUILD_DEP)"

!ENDIF 


!IF "$(NO_EXTERNAL_DEPS)" != "1"
!IF EXISTS("TCFCommSerial.dep")
!INCLUDE "TCFCommSerial.dep"
!ELSE 
!MESSAGE Warning: cannot find "TCFCommSerial.dep"
!ENDIF 
!ENDIF 


!IF "$(CFG)" == "TCFCommSerial - Win32 Release" || "$(CFG)" == "TCFCommSerial - Win32 Debug"
SOURCE=..\TCFServer\BaseCom.cpp

"$(INTDIR)\BaseCom.obj" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFCommSerial.pch"
	$(CPP) $(CPP_PROJ) $(SOURCE)


SOURCE=..\Common\Source\mutex.cpp

"$(INTDIR)\mutex.obj" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFCommSerial.pch"
	$(CPP) $(CPP_PROJ) $(SOURCE)


SOURCE=.\RealSerialComm.cpp

"$(INTDIR)\RealSerialComm.obj" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFCommSerial.pch"


SOURCE=.\StdAfx.cpp

!IF  "$(CFG)" == "TCFCommSerial - Win32 Release"

CPP_SWITCHES=/nologo /Zp2 /MT /W3 /GX /O2 /I "..\TCFServer" /I "..\Common\Headers" /D "WIN32" /D "NDEBUG" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "TCFCOMMSERIAL_EXPORTS" /Fp"$(INTDIR)\TCFCommSerial.pch" /Yc"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /c 

"$(INTDIR)\StdAfx.obj"	"$(INTDIR)\TCFCommSerial.pch" : $(SOURCE) "$(INTDIR)"
	$(CPP) @<<
  $(CPP_SWITCHES) $(SOURCE)
<<


!ELSEIF  "$(CFG)" == "TCFCommSerial - Win32 Debug"

CPP_SWITCHES=/nologo /Zp2 /MTd /W3 /Gm /GX /ZI /Od /I "..\TCFServer" /I "..\Common\Headers" /D "WIN32" /D "_DEBUG" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "TCFCOMMSERIAL_EXPORTS" /Fp"$(INTDIR)\TCFCommSerial.pch" /Yc"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /GZ /c 

"$(INTDIR)\StdAfx.obj"	"$(INTDIR)\TCFCommSerial.pch" : $(SOURCE) "$(INTDIR)"
	$(CPP) @<<
  $(CPP_SWITCHES) $(SOURCE)
<<


!ENDIF 

SOURCE=..\Common\Source\TCDebugLog.cpp

"$(INTDIR)\TCDebugLog.obj" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFCommSerial.pch"
	$(CPP) $(CPP_PROJ) $(SOURCE)


SOURCE=.\TCFCommSerial.cpp

"$(INTDIR)\TCFCommSerial.obj" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFCommSerial.pch"



!ENDIF 

