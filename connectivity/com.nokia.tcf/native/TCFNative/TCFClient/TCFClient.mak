# Microsoft Developer Studio Generated NMAKE File, Based on TCFClient.dsp
!IF "$(CFG)" == ""
CFG=TCFClient - Win32 Debug
!MESSAGE No configuration specified. Defaulting to TCFClient - Win32 Debug.
!ENDIF 

!IF "$(CFG)" != "TCFClient - Win32 Release" && "$(CFG)" != "TCFClient - Win32 Debug"
!MESSAGE Invalid configuration "$(CFG)" specified.
!MESSAGE You can specify a configuration when running NMAKE
!MESSAGE by defining the macro CFG on the command line. For example:
!MESSAGE 
!MESSAGE NMAKE /f "TCFClient.mak" CFG="TCFClient - Win32 Debug"
!MESSAGE 
!MESSAGE Possible choices for configuration are:
!MESSAGE 
!MESSAGE "TCFClient - Win32 Release" (based on "Win32 (x86) Dynamic-Link Library")
!MESSAGE "TCFClient - Win32 Debug" (based on "Win32 (x86) Dynamic-Link Library")
!MESSAGE 
!ERROR An invalid configuration is specified.
!ENDIF 

!IF "$(OS)" == "Windows_NT"
NULL=
!ELSE 
NULL=nul
!ENDIF 

!IF  "$(CFG)" == "TCFClient - Win32 Release"

OUTDIR=.\Release
INTDIR=.\Release
# Begin Custom Macros
OutDir=.\Release
# End Custom Macros

ALL : "$(OUTDIR)\TCFClient.dll"


CLEAN :
	-@erase "$(INTDIR)\ClientManager.obj"
	-@erase "$(INTDIR)\ErrorMonitorData.obj"
	-@erase "$(INTDIR)\InputStream.obj"
	-@erase "$(INTDIR)\mutex.obj"
	-@erase "$(INTDIR)\resource.res"
	-@erase "$(INTDIR)\ServerClient.obj"
	-@erase "$(INTDIR)\shareddata.obj"
	-@erase "$(INTDIR)\StdAfx.obj"
	-@erase "$(INTDIR)\TCAPIConnectionJni.obj"
	-@erase "$(INTDIR)\TCDebugLog.obj"
	-@erase "$(INTDIR)\TCFClient.obj"
	-@erase "$(INTDIR)\TCFClient.pch"
	-@erase "$(INTDIR)\TCFCppApi.obj"
	-@erase "$(INTDIR)\vc60.idb"
	-@erase "$(OUTDIR)\TCFClient.dll"
	-@erase "$(OUTDIR)\TCFClient.exp"
	-@erase "$(OUTDIR)\TCFClient.lib"
	-@erase "$(OUTDIR)\TCFClient.map"

"$(OUTDIR)" :
    if not exist "$(OUTDIR)/$(NULL)" mkdir "$(OUTDIR)"

CPP=cl.exe
CPP_PROJ=/nologo /Zp2 /MT /W3 /GX /O2 /I "..\Common\Headers" /I ".\jdk1.5.0_10\include" /I ".\jdk1.5.0_10\include\win32" /D "NDEBUG" /D "WIN32" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "TCFCLIENT_EXPORTS" /D "_PSAPI_PRESENT" /Fp"$(INTDIR)\TCFClient.pch" /Yu"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /c 

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
RSC_PROJ=/l 0x409 /fo"$(INTDIR)\resource.res" /d "NDEBUG" 
BSC32=bscmake.exe
BSC32_FLAGS=/nologo /o"$(OUTDIR)\TCFClient.bsc" 
BSC32_SBRS= \
	
LINK32=link.exe
LINK32_FLAGS=kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib shlwapi.lib psapi.lib /nologo /dll /incremental:no /pdb:"$(OUTDIR)\TCFClient.pdb" /map:"$(INTDIR)\TCFClient.map" /machine:I386 /out:"$(OUTDIR)\TCFClient.dll" /implib:"$(OUTDIR)\TCFClient.lib" 
LINK32_OBJS= \
	"$(INTDIR)\ClientManager.obj" \
	"$(INTDIR)\ErrorMonitorData.obj" \
	"$(INTDIR)\InputStream.obj" \
	"$(INTDIR)\mutex.obj" \
	"$(INTDIR)\ServerClient.obj" \
	"$(INTDIR)\shareddata.obj" \
	"$(INTDIR)\StdAfx.obj" \
	"$(INTDIR)\TCAPIConnectionJni.obj" \
	"$(INTDIR)\TCDebugLog.obj" \
	"$(INTDIR)\TCFClient.obj" \
	"$(INTDIR)\TCFCppApi.obj" \
	"$(INTDIR)\resource.res"

"$(OUTDIR)\TCFClient.dll" : "$(OUTDIR)" $(DEF_FILE) $(LINK32_OBJS)
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

$(DS_POSTBUILD_DEP) : "$(OUTDIR)\TCFClient.dll"
   copybinaries Release
	echo Helper for Post-build step > "$(DS_POSTBUILD_DEP)"

!ELSEIF  "$(CFG)" == "TCFClient - Win32 Debug"

OUTDIR=.\Debug
INTDIR=.\Debug
# Begin Custom Macros
OutDir=.\Debug
# End Custom Macros

ALL : "$(OUTDIR)\TCFClient.dll" "$(OUTDIR)\TCFClient.bsc"


CLEAN :
	-@erase "$(INTDIR)\ClientManager.obj"
	-@erase "$(INTDIR)\ClientManager.sbr"
	-@erase "$(INTDIR)\ErrorMonitorData.obj"
	-@erase "$(INTDIR)\ErrorMonitorData.sbr"
	-@erase "$(INTDIR)\InputStream.obj"
	-@erase "$(INTDIR)\InputStream.sbr"
	-@erase "$(INTDIR)\mutex.obj"
	-@erase "$(INTDIR)\mutex.sbr"
	-@erase "$(INTDIR)\resource.res"
	-@erase "$(INTDIR)\ServerClient.obj"
	-@erase "$(INTDIR)\ServerClient.sbr"
	-@erase "$(INTDIR)\shareddata.obj"
	-@erase "$(INTDIR)\shareddata.sbr"
	-@erase "$(INTDIR)\StdAfx.obj"
	-@erase "$(INTDIR)\StdAfx.sbr"
	-@erase "$(INTDIR)\TCAPIConnectionJni.obj"
	-@erase "$(INTDIR)\TCAPIConnectionJni.sbr"
	-@erase "$(INTDIR)\TCDebugLog.obj"
	-@erase "$(INTDIR)\TCDebugLog.sbr"
	-@erase "$(INTDIR)\TCFClient.obj"
	-@erase "$(INTDIR)\TCFClient.pch"
	-@erase "$(INTDIR)\TCFClient.sbr"
	-@erase "$(INTDIR)\TCFCppApi.obj"
	-@erase "$(INTDIR)\TCFCppApi.sbr"
	-@erase "$(INTDIR)\vc60.idb"
	-@erase "$(INTDIR)\vc60.pdb"
	-@erase "$(OUTDIR)\TCFClient.bsc"
	-@erase "$(OUTDIR)\TCFClient.dll"
	-@erase "$(OUTDIR)\TCFClient.exp"
	-@erase "$(OUTDIR)\TCFClient.ilk"
	-@erase "$(OUTDIR)\TCFClient.lib"
	-@erase "$(OUTDIR)\TCFClient.map"
	-@erase "$(OUTDIR)\TCFClient.pdb"

"$(OUTDIR)" :
    if not exist "$(OUTDIR)/$(NULL)" mkdir "$(OUTDIR)"

CPP=cl.exe
CPP_PROJ=/nologo /Zp2 /MTd /W3 /Gm /GX /ZI /Od /I "..\Common\Headers" /I ".\jdk1.5.0_10\include" /I ".\jdk1.5.0_10\include\win32" /D "_DEBUG" /D "WIN32" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "TCFCLIENT_EXPORTS" /D "_PSAPI_PRESENT" /FR"$(INTDIR)\\" /Fp"$(INTDIR)\TCFClient.pch" /Yu"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /GZ /c 

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
RSC_PROJ=/l 0x409 /fo"$(INTDIR)\resource.res" /d "_DEBUG" 
BSC32=bscmake.exe
BSC32_FLAGS=/nologo /o"$(OUTDIR)\TCFClient.bsc" 
BSC32_SBRS= \
	"$(INTDIR)\ClientManager.sbr" \
	"$(INTDIR)\ErrorMonitorData.sbr" \
	"$(INTDIR)\InputStream.sbr" \
	"$(INTDIR)\mutex.sbr" \
	"$(INTDIR)\ServerClient.sbr" \
	"$(INTDIR)\shareddata.sbr" \
	"$(INTDIR)\StdAfx.sbr" \
	"$(INTDIR)\TCAPIConnectionJni.sbr" \
	"$(INTDIR)\TCDebugLog.sbr" \
	"$(INTDIR)\TCFClient.sbr" \
	"$(INTDIR)\TCFCppApi.sbr"

"$(OUTDIR)\TCFClient.bsc" : "$(OUTDIR)" $(BSC32_SBRS)
    $(BSC32) @<<
  $(BSC32_FLAGS) $(BSC32_SBRS)
<<

LINK32=link.exe
LINK32_FLAGS=kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib shlwapi.lib psapi.lib /nologo /dll /incremental:yes /pdb:"$(OUTDIR)\TCFClient.pdb" /map:"$(INTDIR)\TCFClient.map" /debug /machine:I386 /out:"$(OUTDIR)\TCFClient.dll" /implib:"$(OUTDIR)\TCFClient.lib" /pdbtype:sept 
LINK32_OBJS= \
	"$(INTDIR)\ClientManager.obj" \
	"$(INTDIR)\ErrorMonitorData.obj" \
	"$(INTDIR)\InputStream.obj" \
	"$(INTDIR)\mutex.obj" \
	"$(INTDIR)\ServerClient.obj" \
	"$(INTDIR)\shareddata.obj" \
	"$(INTDIR)\StdAfx.obj" \
	"$(INTDIR)\TCAPIConnectionJni.obj" \
	"$(INTDIR)\TCDebugLog.obj" \
	"$(INTDIR)\TCFClient.obj" \
	"$(INTDIR)\TCFCppApi.obj" \
	"$(INTDIR)\resource.res"

"$(OUTDIR)\TCFClient.dll" : "$(OUTDIR)" $(DEF_FILE) $(LINK32_OBJS)
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

$(DS_POSTBUILD_DEP) : "$(OUTDIR)\TCFClient.dll" "$(OUTDIR)\TCFClient.bsc"
   copybinaries Debug
	echo Helper for Post-build step > "$(DS_POSTBUILD_DEP)"

!ENDIF 


!IF "$(NO_EXTERNAL_DEPS)" != "1"
!IF EXISTS("TCFClient.dep")
!INCLUDE "TCFClient.dep"
!ELSE 
!MESSAGE Warning: cannot find "TCFClient.dep"
!ENDIF 
!ENDIF 


!IF "$(CFG)" == "TCFClient - Win32 Release" || "$(CFG)" == "TCFClient - Win32 Debug"
SOURCE=.\ClientManager.cpp

!IF  "$(CFG)" == "TCFClient - Win32 Release"

CPP_SWITCHES=/nologo /Zp2 /MT /W3 /GX /O2 /I "..\Common\Headers" /I ".\jdk1.5.0_10\include" /I ".\jdk1.5.0_10\include\win32" /D "NDEBUG" /D "WIN32" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "TCFCLIENT_EXPORTS" /D "_PSAPI_PRESENT" /Fp"$(INTDIR)\TCFClient.pch" /Yu"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /c 

"$(INTDIR)\ClientManager.obj" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFClient.pch"
	$(CPP) @<<
  $(CPP_SWITCHES) $(SOURCE)
<<


!ELSEIF  "$(CFG)" == "TCFClient - Win32 Debug"

CPP_SWITCHES=/nologo /Zp2 /MTd /W3 /Gm /GX /ZI /Od /I "..\Common\Headers" /I ".\jdk1.5.0_10\include" /I ".\jdk1.5.0_10\include\win32" /D "_DEBUG" /D "WIN32" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "TCFCLIENT_EXPORTS" /D "_PSAPI_PRESENT" /FR"$(INTDIR)\\" /Fp"$(INTDIR)\TCFClient.pch" /Yu"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /GZ /c 

"$(INTDIR)\ClientManager.obj"	"$(INTDIR)\ClientManager.sbr" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFClient.pch"
	$(CPP) @<<
  $(CPP_SWITCHES) $(SOURCE)
<<


!ENDIF 

SOURCE=..\Common\Source\ErrorMonitorData.cpp

!IF  "$(CFG)" == "TCFClient - Win32 Release"

CPP_SWITCHES=/nologo /Zp2 /MT /W3 /GX /O2 /I "..\Common\Headers" /I ".\jdk1.5.0_10\include" /I ".\jdk1.5.0_10\include\win32" /D "NDEBUG" /D "WIN32" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "TCFCLIENT_EXPORTS" /D "_PSAPI_PRESENT" /Fp"$(INTDIR)\TCFClient.pch" /Yu"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /c 

"$(INTDIR)\ErrorMonitorData.obj" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFClient.pch"
	$(CPP) @<<
  $(CPP_SWITCHES) $(SOURCE)
<<


!ELSEIF  "$(CFG)" == "TCFClient - Win32 Debug"

CPP_SWITCHES=/nologo /Zp2 /MTd /W3 /Gm /GX /ZI /Od /I "..\Common\Headers" /I ".\jdk1.5.0_10\include" /I ".\jdk1.5.0_10\include\win32" /D "_DEBUG" /D "WIN32" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "TCFCLIENT_EXPORTS" /D "_PSAPI_PRESENT" /FR"$(INTDIR)\\" /Fp"$(INTDIR)\TCFClient.pch" /Yu"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /GZ /c 

"$(INTDIR)\ErrorMonitorData.obj"	"$(INTDIR)\ErrorMonitorData.sbr" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFClient.pch"
	$(CPP) @<<
  $(CPP_SWITCHES) $(SOURCE)
<<


!ENDIF 

SOURCE=..\Common\Source\InputStream.cpp

!IF  "$(CFG)" == "TCFClient - Win32 Release"

CPP_SWITCHES=/nologo /Zp2 /MT /W3 /GX /O2 /I "..\Common\Headers" /I ".\jdk1.5.0_10\include" /I ".\jdk1.5.0_10\include\win32" /D "NDEBUG" /D "WIN32" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "TCFCLIENT_EXPORTS" /D "_PSAPI_PRESENT" /Fp"$(INTDIR)\TCFClient.pch" /Yu"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /c 

"$(INTDIR)\InputStream.obj" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFClient.pch"
	$(CPP) @<<
  $(CPP_SWITCHES) $(SOURCE)
<<


!ELSEIF  "$(CFG)" == "TCFClient - Win32 Debug"

CPP_SWITCHES=/nologo /Zp2 /MTd /W3 /Gm /GX /ZI /Od /I "..\Common\Headers" /I ".\jdk1.5.0_10\include" /I ".\jdk1.5.0_10\include\win32" /D "_DEBUG" /D "WIN32" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "TCFCLIENT_EXPORTS" /D "_PSAPI_PRESENT" /FR"$(INTDIR)\\" /Fp"$(INTDIR)\TCFClient.pch" /Yu"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /GZ /c 

"$(INTDIR)\InputStream.obj"	"$(INTDIR)\InputStream.sbr" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFClient.pch"
	$(CPP) @<<
  $(CPP_SWITCHES) $(SOURCE)
<<


!ENDIF 

SOURCE=..\Common\Source\mutex.cpp

!IF  "$(CFG)" == "TCFClient - Win32 Release"

CPP_SWITCHES=/nologo /Zp2 /MT /W3 /GX /O2 /I "..\Common\Headers" /I ".\jdk1.5.0_10\include" /I ".\jdk1.5.0_10\include\win32" /D "NDEBUG" /D "WIN32" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "TCFCLIENT_EXPORTS" /D "_PSAPI_PRESENT" /Fp"$(INTDIR)\TCFClient.pch" /Yu"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /c 

"$(INTDIR)\mutex.obj" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFClient.pch"
	$(CPP) @<<
  $(CPP_SWITCHES) $(SOURCE)
<<


!ELSEIF  "$(CFG)" == "TCFClient - Win32 Debug"

CPP_SWITCHES=/nologo /Zp2 /MTd /W3 /Gm /GX /ZI /Od /I "..\Common\Headers" /I ".\jdk1.5.0_10\include" /I ".\jdk1.5.0_10\include\win32" /D "_DEBUG" /D "WIN32" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "TCFCLIENT_EXPORTS" /D "_PSAPI_PRESENT" /FR"$(INTDIR)\\" /Fp"$(INTDIR)\TCFClient.pch" /Yu"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /GZ /c 

"$(INTDIR)\mutex.obj"	"$(INTDIR)\mutex.sbr" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFClient.pch"
	$(CPP) @<<
  $(CPP_SWITCHES) $(SOURCE)
<<


!ENDIF 

SOURCE=..\Common\Source\ServerClient.cpp

!IF  "$(CFG)" == "TCFClient - Win32 Release"

CPP_SWITCHES=/nologo /Zp2 /MT /W3 /GX /O2 /I "..\Common\Headers" /I ".\jdk1.5.0_10\include" /I ".\jdk1.5.0_10\include\win32" /D "NDEBUG" /D "WIN32" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "TCFCLIENT_EXPORTS" /D "_PSAPI_PRESENT" /Fp"$(INTDIR)\TCFClient.pch" /Yu"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /c 

"$(INTDIR)\ServerClient.obj" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFClient.pch"
	$(CPP) @<<
  $(CPP_SWITCHES) $(SOURCE)
<<


!ELSEIF  "$(CFG)" == "TCFClient - Win32 Debug"

CPP_SWITCHES=/nologo /Zp2 /MTd /W3 /Gm /GX /ZI /Od /I "..\Common\Headers" /I ".\jdk1.5.0_10\include" /I ".\jdk1.5.0_10\include\win32" /D "_DEBUG" /D "WIN32" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "TCFCLIENT_EXPORTS" /D "_PSAPI_PRESENT" /FR"$(INTDIR)\\" /Fp"$(INTDIR)\TCFClient.pch" /Yu"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /GZ /c 

"$(INTDIR)\ServerClient.obj"	"$(INTDIR)\ServerClient.sbr" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFClient.pch"
	$(CPP) @<<
  $(CPP_SWITCHES) $(SOURCE)
<<


!ENDIF 

SOURCE=..\Common\Source\shareddata.cpp

!IF  "$(CFG)" == "TCFClient - Win32 Release"

CPP_SWITCHES=/nologo /Zp2 /MT /W3 /GX /O2 /I "..\Common\Headers" /I ".\jdk1.5.0_10\include" /I ".\jdk1.5.0_10\include\win32" /D "NDEBUG" /D "WIN32" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "TCFCLIENT_EXPORTS" /D "_PSAPI_PRESENT" /Fp"$(INTDIR)\TCFClient.pch" /Yu"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /c 

"$(INTDIR)\shareddata.obj" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFClient.pch"
	$(CPP) @<<
  $(CPP_SWITCHES) $(SOURCE)
<<


!ELSEIF  "$(CFG)" == "TCFClient - Win32 Debug"

CPP_SWITCHES=/nologo /Zp2 /MTd /W3 /Gm /GX /ZI /Od /I "..\Common\Headers" /I ".\jdk1.5.0_10\include" /I ".\jdk1.5.0_10\include\win32" /D "_DEBUG" /D "WIN32" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "TCFCLIENT_EXPORTS" /D "_PSAPI_PRESENT" /FR"$(INTDIR)\\" /Fp"$(INTDIR)\TCFClient.pch" /Yu"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /GZ /c 

"$(INTDIR)\shareddata.obj"	"$(INTDIR)\shareddata.sbr" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFClient.pch"
	$(CPP) @<<
  $(CPP_SWITCHES) $(SOURCE)
<<


!ENDIF 

SOURCE=.\StdAfx.cpp

!IF  "$(CFG)" == "TCFClient - Win32 Release"

CPP_SWITCHES=/nologo /Zp2 /MT /W3 /GX /O2 /I "..\Common\Headers" /I ".\jdk1.5.0_10\include" /I ".\jdk1.5.0_10\include\win32" /D "NDEBUG" /D "WIN32" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "TCFCLIENT_EXPORTS" /D "_PSAPI_PRESENT" /Fp"$(INTDIR)\TCFClient.pch" /Yc"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /c 

"$(INTDIR)\StdAfx.obj"	"$(INTDIR)\TCFClient.pch" : $(SOURCE) "$(INTDIR)"
	$(CPP) @<<
  $(CPP_SWITCHES) $(SOURCE)
<<


!ELSEIF  "$(CFG)" == "TCFClient - Win32 Debug"

CPP_SWITCHES=/nologo /Zp2 /MTd /W3 /Gm /GX /ZI /Od /I "..\Common\Headers" /I ".\jdk1.5.0_10\include" /I ".\jdk1.5.0_10\include\win32" /D "_DEBUG" /D "WIN32" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "TCFCLIENT_EXPORTS" /D "_PSAPI_PRESENT" /FR"$(INTDIR)\\" /Fp"$(INTDIR)\TCFClient.pch" /Yc"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /GZ /c 

"$(INTDIR)\StdAfx.obj"	"$(INTDIR)\StdAfx.sbr"	"$(INTDIR)\TCFClient.pch" : $(SOURCE) "$(INTDIR)"
	$(CPP) @<<
  $(CPP_SWITCHES) $(SOURCE)
<<


!ENDIF 

SOURCE=.\TCAPIConnectionJni.cpp

!IF  "$(CFG)" == "TCFClient - Win32 Release"

CPP_SWITCHES=/nologo /Zp2 /MT /W3 /GX /O2 /I "..\Common\Headers" /I ".\jdk1.5.0_10\include" /I ".\jdk1.5.0_10\include\win32" /D "NDEBUG" /D "WIN32" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "TCFCLIENT_EXPORTS" /D "_PSAPI_PRESENT" /Fp"$(INTDIR)\TCFClient.pch" /Yu"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /c 

"$(INTDIR)\TCAPIConnectionJni.obj" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFClient.pch"
	$(CPP) @<<
  $(CPP_SWITCHES) $(SOURCE)
<<


!ELSEIF  "$(CFG)" == "TCFClient - Win32 Debug"

CPP_SWITCHES=/nologo /Zp2 /MTd /W3 /Gm /GX /ZI /Od /I "..\Common\Headers" /I ".\jdk1.5.0_10\include" /I ".\jdk1.5.0_10\include\win32" /D "_DEBUG" /D "WIN32" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "TCFCLIENT_EXPORTS" /D "_PSAPI_PRESENT" /FR"$(INTDIR)\\" /Fp"$(INTDIR)\TCFClient.pch" /Yu"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /GZ /c 

"$(INTDIR)\TCAPIConnectionJni.obj"	"$(INTDIR)\TCAPIConnectionJni.sbr" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFClient.pch"
	$(CPP) @<<
  $(CPP_SWITCHES) $(SOURCE)
<<


!ENDIF 

SOURCE=..\Common\Source\TCDebugLog.cpp

!IF  "$(CFG)" == "TCFClient - Win32 Release"

CPP_SWITCHES=/nologo /Zp2 /MT /W3 /GX /O2 /I "..\Common\Headers" /I ".\jdk1.5.0_10\include" /I ".\jdk1.5.0_10\include\win32" /D "NDEBUG" /D "WIN32" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "TCFCLIENT_EXPORTS" /D "_PSAPI_PRESENT" /Fp"$(INTDIR)\TCFClient.pch" /Yu"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /c 

"$(INTDIR)\TCDebugLog.obj" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFClient.pch"
	$(CPP) @<<
  $(CPP_SWITCHES) $(SOURCE)
<<


!ELSEIF  "$(CFG)" == "TCFClient - Win32 Debug"

CPP_SWITCHES=/nologo /Zp2 /MTd /W3 /Gm /GX /ZI /Od /I "..\Common\Headers" /I ".\jdk1.5.0_10\include" /I ".\jdk1.5.0_10\include\win32" /D "_DEBUG" /D "WIN32" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "TCFCLIENT_EXPORTS" /D "_PSAPI_PRESENT" /FR"$(INTDIR)\\" /Fp"$(INTDIR)\TCFClient.pch" /Yu"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /GZ /c 

"$(INTDIR)\TCDebugLog.obj"	"$(INTDIR)\TCDebugLog.sbr" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFClient.pch"
	$(CPP) @<<
  $(CPP_SWITCHES) $(SOURCE)
<<


!ENDIF 

SOURCE=.\TCFClient.cpp

!IF  "$(CFG)" == "TCFClient - Win32 Release"

CPP_SWITCHES=/nologo /Zp2 /MT /W3 /GX /O2 /I "..\Common\Headers" /I ".\jdk1.5.0_10\include" /I ".\jdk1.5.0_10\include\win32" /D "NDEBUG" /D "WIN32" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "TCFCLIENT_EXPORTS" /D "_PSAPI_PRESENT" /Fp"$(INTDIR)\TCFClient.pch" /Yu"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /c 

"$(INTDIR)\TCFClient.obj" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFClient.pch"
	$(CPP) @<<
  $(CPP_SWITCHES) $(SOURCE)
<<


!ELSEIF  "$(CFG)" == "TCFClient - Win32 Debug"

CPP_SWITCHES=/nologo /Zp2 /MTd /W3 /Gm /GX /ZI /Od /I "..\Common\Headers" /I ".\jdk1.5.0_10\include" /I ".\jdk1.5.0_10\include\win32" /D "_DEBUG" /D "WIN32" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "TCFCLIENT_EXPORTS" /D "_PSAPI_PRESENT" /FR"$(INTDIR)\\" /Fp"$(INTDIR)\TCFClient.pch" /Yu"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /GZ /c 

"$(INTDIR)\TCFClient.obj"	"$(INTDIR)\TCFClient.sbr" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFClient.pch"
	$(CPP) @<<
  $(CPP_SWITCHES) $(SOURCE)
<<


!ENDIF 

SOURCE=.\TCFCppApi.cpp

!IF  "$(CFG)" == "TCFClient - Win32 Release"

CPP_SWITCHES=/nologo /Zp2 /MT /W3 /GX /O2 /I "..\Common\Headers" /I ".\jdk1.5.0_10\include" /I ".\jdk1.5.0_10\include\win32" /D "NDEBUG" /D "WIN32" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "TCFCLIENT_EXPORTS" /D "_PSAPI_PRESENT" /Fp"$(INTDIR)\TCFClient.pch" /Yu"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /c 

"$(INTDIR)\TCFCppApi.obj" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFClient.pch"
	$(CPP) @<<
  $(CPP_SWITCHES) $(SOURCE)
<<


!ELSEIF  "$(CFG)" == "TCFClient - Win32 Debug"

CPP_SWITCHES=/nologo /Zp2 /MTd /W3 /Gm /GX /ZI /Od /I "..\Common\Headers" /I ".\jdk1.5.0_10\include" /I ".\jdk1.5.0_10\include\win32" /D "_DEBUG" /D "WIN32" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "TCFCLIENT_EXPORTS" /D "_PSAPI_PRESENT" /FR"$(INTDIR)\\" /Fp"$(INTDIR)\TCFClient.pch" /Yu"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /GZ /c 

"$(INTDIR)\TCFCppApi.obj"	"$(INTDIR)\TCFCppApi.sbr" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFClient.pch"
	$(CPP) @<<
  $(CPP_SWITCHES) $(SOURCE)
<<


!ENDIF 

SOURCE=.\resource.rc

"$(INTDIR)\resource.res" : $(SOURCE) "$(INTDIR)"
	$(RSC) $(RSC_PROJ) $(SOURCE)



!ENDIF 

