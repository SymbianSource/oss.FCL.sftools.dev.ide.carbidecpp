# Microsoft Developer Studio Generated NMAKE File, Based on TCFCommTCP.dsp
!IF "$(CFG)" == ""
CFG=TCFCommTCP - Win32 Debug
!MESSAGE No configuration specified. Defaulting to TCFCommTCP - Win32 Debug.
!ENDIF 

!IF "$(CFG)" != "TCFCommTCP - Win32 Release" && "$(CFG)" != "TCFCommTCP - Win32 Debug"
!MESSAGE Invalid configuration "$(CFG)" specified.
!MESSAGE You can specify a configuration when running NMAKE
!MESSAGE by defining the macro CFG on the command line. For example:
!MESSAGE 
!MESSAGE NMAKE /f "TCFCommTCP.mak" CFG="TCFCommTCP - Win32 Debug"
!MESSAGE 
!MESSAGE Possible choices for configuration are:
!MESSAGE 
!MESSAGE "TCFCommTCP - Win32 Release" (based on "Win32 (x86) Dynamic-Link Library")
!MESSAGE "TCFCommTCP - Win32 Debug" (based on "Win32 (x86) Dynamic-Link Library")
!MESSAGE 
!ERROR An invalid configuration is specified.
!ENDIF 

!IF "$(OS)" == "Windows_NT"
NULL=
!ELSE 
NULL=nul
!ENDIF 

!IF  "$(CFG)" == "TCFCommTCP - Win32 Release"

OUTDIR=.\Release
INTDIR=.\Release
# Begin Custom Macros
OutDir=.\Release
# End Custom Macros

ALL : "$(OUTDIR)\TCFCommTCP.dll"


CLEAN :
	-@erase "$(INTDIR)\BaseCom.obj"
	-@erase "$(INTDIR)\mutex.obj"
	-@erase "$(INTDIR)\StdAfx.obj"
	-@erase "$(INTDIR)\TCDebugLog.obj"
	-@erase "$(INTDIR)\TCFCommTCP.obj"
	-@erase "$(INTDIR)\TCFCommTCP.pch"
	-@erase "$(INTDIR)\TcpComm.obj"
	-@erase "$(INTDIR)\vc60.idb"
	-@erase "$(OUTDIR)\TCFCommTCP.dll"
	-@erase "$(OUTDIR)\TCFCommTCP.exp"
	-@erase "$(OUTDIR)\TCFCommTCP.lib"
	-@erase "$(OUTDIR)\TCFCommTCP.map"

"$(OUTDIR)" :
    if not exist "$(OUTDIR)/$(NULL)" mkdir "$(OUTDIR)"

CPP=cl.exe
CPP_PROJ=/nologo /Zp2 /MT /W3 /GX /O2 /I "..\TCFServer" /I "..\Common\Headers" /D "WIN32" /D "NDEBUG" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "TCFCOMMTCP_EXPORTS" /Fp"$(INTDIR)\TCFCommTCP.pch" /Yu"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /c 

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
BSC32_FLAGS=/nologo /o"$(OUTDIR)\TCFCommTCP.bsc" 
BSC32_SBRS= \
	
LINK32=link.exe
LINK32_FLAGS=kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib ws2_32.lib shlwapi.lib /nologo /dll /incremental:no /pdb:"$(OUTDIR)\TCFCommTCP.pdb" /map:"$(INTDIR)\TCFCommTCP.map" /machine:I386 /out:"$(OUTDIR)\TCFCommTCP.dll" /implib:"$(OUTDIR)\TCFCommTCP.lib" 
LINK32_OBJS= \
	"$(INTDIR)\BaseCom.obj" \
	"$(INTDIR)\mutex.obj" \
	"$(INTDIR)\StdAfx.obj" \
	"$(INTDIR)\TCDebugLog.obj" \
	"$(INTDIR)\TCFCommTCP.obj" \
	"$(INTDIR)\TcpComm.obj"

"$(OUTDIR)\TCFCommTCP.dll" : "$(OUTDIR)" $(DEF_FILE) $(LINK32_OBJS)
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

$(DS_POSTBUILD_DEP) : "$(OUTDIR)\TCFCommTCP.dll"
   copybinaries Release
	echo Helper for Post-build step > "$(DS_POSTBUILD_DEP)"

!ELSEIF  "$(CFG)" == "TCFCommTCP - Win32 Debug"

OUTDIR=.\Debug
INTDIR=.\Debug
# Begin Custom Macros
OutDir=.\Debug
# End Custom Macros

ALL : "$(OUTDIR)\TCFCommTCP.dll" "$(OUTDIR)\TCFCommTCP.bsc"


CLEAN :
	-@erase "$(INTDIR)\BaseCom.obj"
	-@erase "$(INTDIR)\BaseCom.sbr"
	-@erase "$(INTDIR)\mutex.obj"
	-@erase "$(INTDIR)\mutex.sbr"
	-@erase "$(INTDIR)\StdAfx.obj"
	-@erase "$(INTDIR)\StdAfx.sbr"
	-@erase "$(INTDIR)\TCDebugLog.obj"
	-@erase "$(INTDIR)\TCDebugLog.sbr"
	-@erase "$(INTDIR)\TCFCommTCP.obj"
	-@erase "$(INTDIR)\TCFCommTCP.pch"
	-@erase "$(INTDIR)\TCFCommTCP.sbr"
	-@erase "$(INTDIR)\TcpComm.obj"
	-@erase "$(INTDIR)\TcpComm.sbr"
	-@erase "$(INTDIR)\vc60.idb"
	-@erase "$(INTDIR)\vc60.pdb"
	-@erase "$(OUTDIR)\TCFCommTCP.bsc"
	-@erase "$(OUTDIR)\TCFCommTCP.dll"
	-@erase "$(OUTDIR)\TCFCommTCP.exp"
	-@erase "$(OUTDIR)\TCFCommTCP.ilk"
	-@erase "$(OUTDIR)\TCFCommTCP.lib"
	-@erase "$(OUTDIR)\TCFCommTCP.map"
	-@erase "$(OUTDIR)\TCFCommTCP.pdb"

"$(OUTDIR)" :
    if not exist "$(OUTDIR)/$(NULL)" mkdir "$(OUTDIR)"

CPP=cl.exe
CPP_PROJ=/nologo /Zp2 /MTd /W3 /Gm /GX /ZI /Od /I "..\TCFServer" /I "..\Common\Headers" /D "WIN32" /D "_DEBUG" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "TCFCOMMTCP_EXPORTS" /FR"$(INTDIR)\\" /Fp"$(INTDIR)\TCFCommTCP.pch" /Yu"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /GZ /c 

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
BSC32_FLAGS=/nologo /o"$(OUTDIR)\TCFCommTCP.bsc" 
BSC32_SBRS= \
	"$(INTDIR)\BaseCom.sbr" \
	"$(INTDIR)\mutex.sbr" \
	"$(INTDIR)\StdAfx.sbr" \
	"$(INTDIR)\TCDebugLog.sbr" \
	"$(INTDIR)\TCFCommTCP.sbr" \
	"$(INTDIR)\TcpComm.sbr"

"$(OUTDIR)\TCFCommTCP.bsc" : "$(OUTDIR)" $(BSC32_SBRS)
    $(BSC32) @<<
  $(BSC32_FLAGS) $(BSC32_SBRS)
<<

LINK32=link.exe
LINK32_FLAGS=kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib ws2_32.lib shlwapi.lib /nologo /dll /incremental:yes /pdb:"$(OUTDIR)\TCFCommTCP.pdb" /map:"$(INTDIR)\TCFCommTCP.map" /debug /machine:I386 /out:"$(OUTDIR)\TCFCommTCP.dll" /implib:"$(OUTDIR)\TCFCommTCP.lib" /pdbtype:sept 
LINK32_OBJS= \
	"$(INTDIR)\BaseCom.obj" \
	"$(INTDIR)\mutex.obj" \
	"$(INTDIR)\StdAfx.obj" \
	"$(INTDIR)\TCDebugLog.obj" \
	"$(INTDIR)\TCFCommTCP.obj" \
	"$(INTDIR)\TcpComm.obj"

"$(OUTDIR)\TCFCommTCP.dll" : "$(OUTDIR)" $(DEF_FILE) $(LINK32_OBJS)
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

$(DS_POSTBUILD_DEP) : "$(OUTDIR)\TCFCommTCP.dll" "$(OUTDIR)\TCFCommTCP.bsc"
   copybinaries Debug
	echo Helper for Post-build step > "$(DS_POSTBUILD_DEP)"

!ENDIF 


!IF "$(NO_EXTERNAL_DEPS)" != "1"
!IF EXISTS("TCFCommTCP.dep")
!INCLUDE "TCFCommTCP.dep"
!ELSE 
!MESSAGE Warning: cannot find "TCFCommTCP.dep"
!ENDIF 
!ENDIF 


!IF "$(CFG)" == "TCFCommTCP - Win32 Release" || "$(CFG)" == "TCFCommTCP - Win32 Debug"
SOURCE=..\TCFServer\BaseCom.cpp

!IF  "$(CFG)" == "TCFCommTCP - Win32 Release"


"$(INTDIR)\BaseCom.obj" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFCommTCP.pch"
	$(CPP) $(CPP_PROJ) $(SOURCE)


!ELSEIF  "$(CFG)" == "TCFCommTCP - Win32 Debug"


"$(INTDIR)\BaseCom.obj"	"$(INTDIR)\BaseCom.sbr" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFCommTCP.pch"
	$(CPP) $(CPP_PROJ) $(SOURCE)


!ENDIF 

SOURCE=..\Common\Source\mutex.cpp

!IF  "$(CFG)" == "TCFCommTCP - Win32 Release"


"$(INTDIR)\mutex.obj" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFCommTCP.pch"
	$(CPP) $(CPP_PROJ) $(SOURCE)


!ELSEIF  "$(CFG)" == "TCFCommTCP - Win32 Debug"


"$(INTDIR)\mutex.obj"	"$(INTDIR)\mutex.sbr" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFCommTCP.pch"
	$(CPP) $(CPP_PROJ) $(SOURCE)


!ENDIF 

SOURCE=.\StdAfx.cpp

!IF  "$(CFG)" == "TCFCommTCP - Win32 Release"

CPP_SWITCHES=/nologo /Zp2 /MT /W3 /GX /O2 /I "..\TCFServer" /I "..\Common\Headers" /D "WIN32" /D "NDEBUG" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "TCFCOMMTCP_EXPORTS" /Fp"$(INTDIR)\TCFCommTCP.pch" /Yc"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /c 

"$(INTDIR)\StdAfx.obj"	"$(INTDIR)\TCFCommTCP.pch" : $(SOURCE) "$(INTDIR)"
	$(CPP) @<<
  $(CPP_SWITCHES) $(SOURCE)
<<


!ELSEIF  "$(CFG)" == "TCFCommTCP - Win32 Debug"

CPP_SWITCHES=/nologo /Zp2 /MTd /W3 /Gm /GX /ZI /Od /I "..\TCFServer" /I "..\Common\Headers" /D "WIN32" /D "_DEBUG" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "TCFCOMMTCP_EXPORTS" /FR"$(INTDIR)\\" /Fp"$(INTDIR)\TCFCommTCP.pch" /Yc"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /GZ /c 

"$(INTDIR)\StdAfx.obj"	"$(INTDIR)\StdAfx.sbr"	"$(INTDIR)\TCFCommTCP.pch" : $(SOURCE) "$(INTDIR)"
	$(CPP) @<<
  $(CPP_SWITCHES) $(SOURCE)
<<


!ENDIF 

SOURCE=..\Common\Source\TCDebugLog.cpp

!IF  "$(CFG)" == "TCFCommTCP - Win32 Release"


"$(INTDIR)\TCDebugLog.obj" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFCommTCP.pch"
	$(CPP) $(CPP_PROJ) $(SOURCE)


!ELSEIF  "$(CFG)" == "TCFCommTCP - Win32 Debug"


"$(INTDIR)\TCDebugLog.obj"	"$(INTDIR)\TCDebugLog.sbr" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFCommTCP.pch"
	$(CPP) $(CPP_PROJ) $(SOURCE)


!ENDIF 

SOURCE=.\TCFCommTCP.cpp

!IF  "$(CFG)" == "TCFCommTCP - Win32 Release"


"$(INTDIR)\TCFCommTCP.obj" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFCommTCP.pch"


!ELSEIF  "$(CFG)" == "TCFCommTCP - Win32 Debug"


"$(INTDIR)\TCFCommTCP.obj"	"$(INTDIR)\TCFCommTCP.sbr" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFCommTCP.pch"


!ENDIF 

SOURCE=.\TcpComm.cpp

!IF  "$(CFG)" == "TCFCommTCP - Win32 Release"


"$(INTDIR)\TcpComm.obj" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFCommTCP.pch"


!ELSEIF  "$(CFG)" == "TCFCommTCP - Win32 Debug"


"$(INTDIR)\TcpComm.obj"	"$(INTDIR)\TcpComm.sbr" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFCommTCP.pch"


!ENDIF 


!ENDIF 

