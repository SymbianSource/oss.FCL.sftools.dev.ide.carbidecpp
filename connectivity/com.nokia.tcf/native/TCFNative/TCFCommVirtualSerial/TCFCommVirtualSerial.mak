# Microsoft Developer Studio Generated NMAKE File, Based on TCFCommVirtualSerial.dsp
!IF "$(CFG)" == ""
CFG=TCFCommVirtualSerial - Win32 Debug
!MESSAGE No configuration specified. Defaulting to TCFCommVirtualSerial - Win32 Debug.
!ENDIF 

!IF "$(CFG)" != "TCFCommVirtualSerial - Win32 Release" && "$(CFG)" != "TCFCommVirtualSerial - Win32 Debug"
!MESSAGE Invalid configuration "$(CFG)" specified.
!MESSAGE You can specify a configuration when running NMAKE
!MESSAGE by defining the macro CFG on the command line. For example:
!MESSAGE 
!MESSAGE NMAKE /f "TCFCommVirtualSerial.mak" CFG="TCFCommVirtualSerial - Win32 Debug"
!MESSAGE 
!MESSAGE Possible choices for configuration are:
!MESSAGE 
!MESSAGE "TCFCommVirtualSerial - Win32 Release" (based on "Win32 (x86) Dynamic-Link Library")
!MESSAGE "TCFCommVirtualSerial - Win32 Debug" (based on "Win32 (x86) Dynamic-Link Library")
!MESSAGE 
!ERROR An invalid configuration is specified.
!ENDIF 

!IF "$(OS)" == "Windows_NT"
NULL=
!ELSE 
NULL=nul
!ENDIF 

CPP=cl.exe
MTL=midl.exe
RSC=rc.exe

!IF  "$(CFG)" == "TCFCommVirtualSerial - Win32 Release"

OUTDIR=.\Release
INTDIR=.\Release
# Begin Custom Macros
OutDir=.\Release
# End Custom Macros

ALL : "$(OUTDIR)\TCFCommVirtualSerial.dll"


CLEAN :
	-@erase "$(INTDIR)\BaseCom.obj"
	-@erase "$(INTDIR)\mutex.obj"
	-@erase "$(INTDIR)\RealSerialComm.obj"
	-@erase "$(INTDIR)\StdAfx.obj"
	-@erase "$(INTDIR)\TCDebugLog.obj"
	-@erase "$(INTDIR)\TCFCommVirtualSerial.obj"
	-@erase "$(INTDIR)\TCFCommVirtualSerial.pch"
	-@erase "$(INTDIR)\vc60.idb"
	-@erase "$(INTDIR)\VirtualSerialComm.obj"
	-@erase "$(OUTDIR)\TCFCommVirtualSerial.dll"
	-@erase "$(OUTDIR)\TCFCommVirtualSerial.exp"
	-@erase "$(OUTDIR)\TCFCommVirtualSerial.lib"
	-@erase "$(OUTDIR)\TCFCommVirtualSerial.map"

"$(OUTDIR)" :
    if not exist "$(OUTDIR)/$(NULL)" mkdir "$(OUTDIR)"

CPP_PROJ=/nologo /Zp2 /MT /W3 /GX /O2 /I "..\TCFCommSerial" /I "..\Common\Headers" /I "..\TCFServer" /D "WIN32" /D "NDEBUG" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "TCFCOMMVIRTUALSERIAL_EXPORTS" /Fp"$(INTDIR)\TCFCommVirtualSerial.pch" /Yu"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /c 
MTL_PROJ=/nologo /D "NDEBUG" /mktyplib203 /win32 
BSC32=bscmake.exe
BSC32_FLAGS=/nologo /o"$(OUTDIR)\TCFCommVirtualSerial.bsc" 
BSC32_SBRS= \
	
LINK32=link.exe
LINK32_FLAGS=kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib /nologo /dll /incremental:no /pdb:"$(OUTDIR)\TCFCommVirtualSerial.pdb" /map:"$(INTDIR)\TCFCommVirtualSerial.map" /machine:I386 /out:"$(OUTDIR)\TCFCommVirtualSerial.dll" /implib:"$(OUTDIR)\TCFCommVirtualSerial.lib" 
LINK32_OBJS= \
	"$(INTDIR)\BaseCom.obj" \
	"$(INTDIR)\mutex.obj" \
	"$(INTDIR)\RealSerialComm.obj" \
	"$(INTDIR)\StdAfx.obj" \
	"$(INTDIR)\TCDebugLog.obj" \
	"$(INTDIR)\TCFCommVirtualSerial.obj" \
	"$(INTDIR)\VirtualSerialComm.obj"

"$(OUTDIR)\TCFCommVirtualSerial.dll" : "$(OUTDIR)" $(DEF_FILE) $(LINK32_OBJS)
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

$(DS_POSTBUILD_DEP) : "$(OUTDIR)\TCFCommVirtualSerial.dll"
   copyBinaries Release
	echo Helper for Post-build step > "$(DS_POSTBUILD_DEP)"

!ELSEIF  "$(CFG)" == "TCFCommVirtualSerial - Win32 Debug"

OUTDIR=.\Debug
INTDIR=.\Debug
# Begin Custom Macros
OutDir=.\Debug
# End Custom Macros

ALL : "$(OUTDIR)\TCFCommVirtualSerial.dll"


CLEAN :
	-@erase "$(INTDIR)\BaseCom.obj"
	-@erase "$(INTDIR)\mutex.obj"
	-@erase "$(INTDIR)\RealSerialComm.obj"
	-@erase "$(INTDIR)\StdAfx.obj"
	-@erase "$(INTDIR)\TCDebugLog.obj"
	-@erase "$(INTDIR)\TCFCommVirtualSerial.obj"
	-@erase "$(INTDIR)\TCFCommVirtualSerial.pch"
	-@erase "$(INTDIR)\vc60.idb"
	-@erase "$(INTDIR)\vc60.pdb"
	-@erase "$(INTDIR)\VirtualSerialComm.obj"
	-@erase "$(OUTDIR)\TCFCommVirtualSerial.dll"
	-@erase "$(OUTDIR)\TCFCommVirtualSerial.exp"
	-@erase "$(OUTDIR)\TCFCommVirtualSerial.ilk"
	-@erase "$(OUTDIR)\TCFCommVirtualSerial.lib"
	-@erase "$(OUTDIR)\TCFCommVirtualSerial.map"
	-@erase "$(OUTDIR)\TCFCommVirtualSerial.pdb"

"$(OUTDIR)" :
    if not exist "$(OUTDIR)/$(NULL)" mkdir "$(OUTDIR)"

CPP_PROJ=/nologo /Zp2 /MTd /W3 /Gm /GX /ZI /Od /I "..\TCFCommSerial" /I "..\Common\Headers" /I "..\TCFServer" /D "WIN32" /D "_DEBUG" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "TCFCOMMVIRTUALSERIAL_EXPORTS" /Fp"$(INTDIR)\TCFCommVirtualSerial.pch" /Yu"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /GZ /c 
MTL_PROJ=/nologo /D "_DEBUG" /mktyplib203 /win32 
BSC32=bscmake.exe
BSC32_FLAGS=/nologo /o"$(OUTDIR)\TCFCommVirtualSerial.bsc" 
BSC32_SBRS= \
	
LINK32=link.exe
LINK32_FLAGS=kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib /nologo /dll /incremental:yes /pdb:"$(OUTDIR)\TCFCommVirtualSerial.pdb" /map:"$(INTDIR)\TCFCommVirtualSerial.map" /debug /machine:I386 /out:"$(OUTDIR)\TCFCommVirtualSerial.dll" /implib:"$(OUTDIR)\TCFCommVirtualSerial.lib" /pdbtype:sept 
LINK32_OBJS= \
	"$(INTDIR)\BaseCom.obj" \
	"$(INTDIR)\mutex.obj" \
	"$(INTDIR)\RealSerialComm.obj" \
	"$(INTDIR)\StdAfx.obj" \
	"$(INTDIR)\TCDebugLog.obj" \
	"$(INTDIR)\TCFCommVirtualSerial.obj" \
	"$(INTDIR)\VirtualSerialComm.obj"

"$(OUTDIR)\TCFCommVirtualSerial.dll" : "$(OUTDIR)" $(DEF_FILE) $(LINK32_OBJS)
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

$(DS_POSTBUILD_DEP) : "$(OUTDIR)\TCFCommVirtualSerial.dll"
   copyBinaries Debug
	echo Helper for Post-build step > "$(DS_POSTBUILD_DEP)"

!ENDIF 

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


!IF "$(NO_EXTERNAL_DEPS)" != "1"
!IF EXISTS("TCFCommVirtualSerial.dep")
!INCLUDE "TCFCommVirtualSerial.dep"
!ELSE 
!MESSAGE Warning: cannot find "TCFCommVirtualSerial.dep"
!ENDIF 
!ENDIF 


!IF "$(CFG)" == "TCFCommVirtualSerial - Win32 Release" || "$(CFG)" == "TCFCommVirtualSerial - Win32 Debug"
SOURCE=..\TCFServer\BaseCom.cpp

"$(INTDIR)\BaseCom.obj" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFCommVirtualSerial.pch"
	$(CPP) $(CPP_PROJ) $(SOURCE)


SOURCE=..\Common\Source\mutex.cpp

"$(INTDIR)\mutex.obj" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFCommVirtualSerial.pch"
	$(CPP) $(CPP_PROJ) $(SOURCE)


SOURCE=..\TCFCommSerial\RealSerialComm.cpp

"$(INTDIR)\RealSerialComm.obj" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFCommVirtualSerial.pch"
	$(CPP) $(CPP_PROJ) $(SOURCE)


SOURCE=.\StdAfx.cpp

!IF  "$(CFG)" == "TCFCommVirtualSerial - Win32 Release"

CPP_SWITCHES=/nologo /Zp2 /MT /W3 /GX /O2 /I "..\TCFCommSerial" /I "..\Common\Headers" /I "..\TCFServer" /D "WIN32" /D "NDEBUG" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "TCFCOMMVIRTUALSERIAL_EXPORTS" /Fp"$(INTDIR)\TCFCommVirtualSerial.pch" /Yc"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /c 

"$(INTDIR)\StdAfx.obj"	"$(INTDIR)\TCFCommVirtualSerial.pch" : $(SOURCE) "$(INTDIR)"
	$(CPP) @<<
  $(CPP_SWITCHES) $(SOURCE)
<<


!ELSEIF  "$(CFG)" == "TCFCommVirtualSerial - Win32 Debug"

CPP_SWITCHES=/nologo /Zp2 /MTd /W3 /Gm /GX /ZI /Od /I "..\TCFCommSerial" /I "..\Common\Headers" /I "..\TCFServer" /D "WIN32" /D "_DEBUG" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "TCFCOMMVIRTUALSERIAL_EXPORTS" /Fp"$(INTDIR)\TCFCommVirtualSerial.pch" /Yc"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /GZ /c 

"$(INTDIR)\StdAfx.obj"	"$(INTDIR)\TCFCommVirtualSerial.pch" : $(SOURCE) "$(INTDIR)"
	$(CPP) @<<
  $(CPP_SWITCHES) $(SOURCE)
<<


!ENDIF 

SOURCE=..\Common\Source\TCDebugLog.cpp

"$(INTDIR)\TCDebugLog.obj" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFCommVirtualSerial.pch"
	$(CPP) $(CPP_PROJ) $(SOURCE)


SOURCE=.\TCFCommVirtualSerial.cpp

"$(INTDIR)\TCFCommVirtualSerial.obj" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFCommVirtualSerial.pch"


SOURCE=.\VirtualSerialComm.cpp

"$(INTDIR)\VirtualSerialComm.obj" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFCommVirtualSerial.pch"



!ENDIF 

