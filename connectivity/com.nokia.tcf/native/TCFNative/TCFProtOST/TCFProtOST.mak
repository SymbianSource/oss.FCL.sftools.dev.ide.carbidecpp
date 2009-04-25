# Microsoft Developer Studio Generated NMAKE File, Based on TCFProtOST.dsp
!IF "$(CFG)" == ""
CFG=TCFProtOST - Win32 Debug
!MESSAGE No configuration specified. Defaulting to TCFProtOST - Win32 Debug.
!ENDIF 

!IF "$(CFG)" != "TCFProtOST - Win32 Release" && "$(CFG)" != "TCFProtOST - Win32 Debug"
!MESSAGE Invalid configuration "$(CFG)" specified.
!MESSAGE You can specify a configuration when running NMAKE
!MESSAGE by defining the macro CFG on the command line. For example:
!MESSAGE 
!MESSAGE NMAKE /f "TCFProtOST.mak" CFG="TCFProtOST - Win32 Debug"
!MESSAGE 
!MESSAGE Possible choices for configuration are:
!MESSAGE 
!MESSAGE "TCFProtOST - Win32 Release" (based on "Win32 (x86) Dynamic-Link Library")
!MESSAGE "TCFProtOST - Win32 Debug" (based on "Win32 (x86) Dynamic-Link Library")
!MESSAGE 
!ERROR An invalid configuration is specified.
!ENDIF 

!IF "$(OS)" == "Windows_NT"
NULL=
!ELSE 
NULL=nul
!ENDIF 

!IF  "$(CFG)" == "TCFProtOST - Win32 Release"

OUTDIR=.\Release
INTDIR=.\Release
# Begin Custom Macros
OutDir=.\Release
# End Custom Macros

ALL : "$(OUTDIR)\TCFProtOST.dll"


CLEAN :
	-@erase "$(INTDIR)\BaseProtocol.obj"
	-@erase "$(INTDIR)\OSTProtocol.obj"
	-@erase "$(INTDIR)\StdAfx.obj"
	-@erase "$(INTDIR)\TCFProtOST.obj"
	-@erase "$(INTDIR)\TCFProtOST.pch"
	-@erase "$(INTDIR)\vc60.idb"
	-@erase "$(OUTDIR)\TCFProtOST.dll"
	-@erase "$(OUTDIR)\TCFProtOST.exp"
	-@erase "$(OUTDIR)\TCFProtOST.lib"
	-@erase "$(OUTDIR)\TCFProtOST.map"

"$(OUTDIR)" :
    if not exist "$(OUTDIR)/$(NULL)" mkdir "$(OUTDIR)"

CPP=cl.exe
CPP_PROJ=/nologo /Zp2 /MT /W3 /GX /O2 /I "..\TCFServer" /D "WIN32" /D "NDEBUG" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "TCFPROTOST_EXPORTS" /Fp"$(INTDIR)\TCFProtOST.pch" /Yu"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /c 

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
BSC32_FLAGS=/nologo /o"$(OUTDIR)\TCFProtOST.bsc" 
BSC32_SBRS= \
	
LINK32=link.exe
LINK32_FLAGS=kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib /nologo /dll /incremental:no /pdb:"$(OUTDIR)\TCFProtOST.pdb" /map:"$(INTDIR)\TCFProtOST.map" /machine:I386 /out:"$(OUTDIR)\TCFProtOST.dll" /implib:"$(OUTDIR)\TCFProtOST.lib" 
LINK32_OBJS= \
	"$(INTDIR)\BaseProtocol.obj" \
	"$(INTDIR)\OSTProtocol.obj" \
	"$(INTDIR)\StdAfx.obj" \
	"$(INTDIR)\TCFProtOST.obj"

"$(OUTDIR)\TCFProtOST.dll" : "$(OUTDIR)" $(DEF_FILE) $(LINK32_OBJS)
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

$(DS_POSTBUILD_DEP) : "$(OUTDIR)\TCFProtOST.dll"
   copyBinaries Release
	echo Helper for Post-build step > "$(DS_POSTBUILD_DEP)"

!ELSEIF  "$(CFG)" == "TCFProtOST - Win32 Debug"

OUTDIR=.\Debug
INTDIR=.\Debug
# Begin Custom Macros
OutDir=.\Debug
# End Custom Macros

ALL : "$(OUTDIR)\TCFProtOST.dll"


CLEAN :
	-@erase "$(INTDIR)\BaseProtocol.obj"
	-@erase "$(INTDIR)\OSTProtocol.obj"
	-@erase "$(INTDIR)\StdAfx.obj"
	-@erase "$(INTDIR)\TCFProtOST.obj"
	-@erase "$(INTDIR)\TCFProtOST.pch"
	-@erase "$(INTDIR)\vc60.idb"
	-@erase "$(INTDIR)\vc60.pdb"
	-@erase "$(OUTDIR)\TCFProtOST.dll"
	-@erase "$(OUTDIR)\TCFProtOST.exp"
	-@erase "$(OUTDIR)\TCFProtOST.ilk"
	-@erase "$(OUTDIR)\TCFProtOST.lib"
	-@erase "$(OUTDIR)\TCFProtOST.map"
	-@erase "$(OUTDIR)\TCFProtOST.pdb"

"$(OUTDIR)" :
    if not exist "$(OUTDIR)/$(NULL)" mkdir "$(OUTDIR)"

CPP=cl.exe
CPP_PROJ=/nologo /Zp2 /MTd /W3 /Gm /GX /ZI /Od /I "..\TCFServer" /D "WIN32" /D "_DEBUG" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "TCFPROTOST_EXPORTS" /Fp"$(INTDIR)\TCFProtOST.pch" /Yu"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /GZ /c 

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
BSC32_FLAGS=/nologo /o"$(OUTDIR)\TCFProtOST.bsc" 
BSC32_SBRS= \
	
LINK32=link.exe
LINK32_FLAGS=kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib /nologo /dll /incremental:yes /pdb:"$(OUTDIR)\TCFProtOST.pdb" /map:"$(INTDIR)\TCFProtOST.map" /debug /machine:I386 /out:"$(OUTDIR)\TCFProtOST.dll" /implib:"$(OUTDIR)\TCFProtOST.lib" /pdbtype:sept 
LINK32_OBJS= \
	"$(INTDIR)\BaseProtocol.obj" \
	"$(INTDIR)\OSTProtocol.obj" \
	"$(INTDIR)\StdAfx.obj" \
	"$(INTDIR)\TCFProtOST.obj"

"$(OUTDIR)\TCFProtOST.dll" : "$(OUTDIR)" $(DEF_FILE) $(LINK32_OBJS)
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

$(DS_POSTBUILD_DEP) : "$(OUTDIR)\TCFProtOST.dll"
   copyBinaries Debug
	echo Helper for Post-build step > "$(DS_POSTBUILD_DEP)"

!ENDIF 


!IF "$(NO_EXTERNAL_DEPS)" != "1"
!IF EXISTS("TCFProtOST.dep")
!INCLUDE "TCFProtOST.dep"
!ELSE 
!MESSAGE Warning: cannot find "TCFProtOST.dep"
!ENDIF 
!ENDIF 


!IF "$(CFG)" == "TCFProtOST - Win32 Release" || "$(CFG)" == "TCFProtOST - Win32 Debug"
SOURCE=..\TCFServer\BaseProtocol.cpp

"$(INTDIR)\BaseProtocol.obj" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFProtOST.pch"
	$(CPP) $(CPP_PROJ) $(SOURCE)


SOURCE=.\OSTProtocol.cpp

"$(INTDIR)\OSTProtocol.obj" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFProtOST.pch"


SOURCE=.\StdAfx.cpp

!IF  "$(CFG)" == "TCFProtOST - Win32 Release"

CPP_SWITCHES=/nologo /Zp2 /MT /W3 /GX /O2 /I "..\TCFServer" /D "WIN32" /D "NDEBUG" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "TCFPROTOST_EXPORTS" /Fp"$(INTDIR)\TCFProtOST.pch" /Yc"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /c 

"$(INTDIR)\StdAfx.obj"	"$(INTDIR)\TCFProtOST.pch" : $(SOURCE) "$(INTDIR)"
	$(CPP) @<<
  $(CPP_SWITCHES) $(SOURCE)
<<


!ELSEIF  "$(CFG)" == "TCFProtOST - Win32 Debug"

CPP_SWITCHES=/nologo /Zp2 /MTd /W3 /Gm /GX /ZI /Od /I "..\TCFServer" /D "WIN32" /D "_DEBUG" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "TCFPROTOST_EXPORTS" /Fp"$(INTDIR)\TCFProtOST.pch" /Yc"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /GZ /c 

"$(INTDIR)\StdAfx.obj"	"$(INTDIR)\TCFProtOST.pch" : $(SOURCE) "$(INTDIR)"
	$(CPP) @<<
  $(CPP_SWITCHES) $(SOURCE)
<<


!ENDIF 

SOURCE=.\TCFProtOST.cpp

"$(INTDIR)\TCFProtOST.obj" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFProtOST.pch"



!ENDIF 

