# Microsoft Developer Studio Generated NMAKE File, Based on GetTRKVersion.dsp
!IF "$(CFG)" == ""
CFG=GetTRKVersion - Win32 Debug
!MESSAGE No configuration specified. Defaulting to GetTRKVersion - Win32 Debug.
!ENDIF 

!IF "$(CFG)" != "GetTRKVersion - Win32 Release" && "$(CFG)" != "GetTRKVersion - Win32 Debug"
!MESSAGE Invalid configuration "$(CFG)" specified.
!MESSAGE You can specify a configuration when running NMAKE
!MESSAGE by defining the macro CFG on the command line. For example:
!MESSAGE 
!MESSAGE NMAKE /f "GetTRKVersion.mak" CFG="GetTRKVersion - Win32 Debug"
!MESSAGE 
!MESSAGE Possible choices for configuration are:
!MESSAGE 
!MESSAGE "GetTRKVersion - Win32 Release" (based on "Win32 (x86) Dynamic-Link Library")
!MESSAGE "GetTRKVersion - Win32 Debug" (based on "Win32 (x86) Dynamic-Link Library")
!MESSAGE 
!ERROR An invalid configuration is specified.
!ENDIF 

!IF "$(OS)" == "Windows_NT"
NULL=
!ELSE 
NULL=nul
!ENDIF 

!IF  "$(CFG)" == "GetTRKVersion - Win32 Release"

OUTDIR=.\..\..\os\win32\x86
INTDIR=.\Release
# Begin Custom Macros
OutDir=.\..\..\os\win32\x86
# End Custom Macros

ALL : "$(OUTDIR)\GetTRKVersion.dll"


CLEAN :
	-@erase "$(INTDIR)\GetTRKVersion.obj"
	-@erase "$(INTDIR)\GetTRKVersion.pch"
	-@erase "$(INTDIR)\StdAfx.obj"
	-@erase "$(INTDIR)\vc60.idb"
	-@erase "$(OUTDIR)\GetTRKVersion.dll"
	-@erase "$(OUTDIR)\GetTRKVersion.exp"
	-@erase "$(OUTDIR)\GetTRKVersion.lib"

"$(OUTDIR)" :
    if not exist "$(OUTDIR)/$(NULL)" mkdir "$(OUTDIR)"

"$(INTDIR)" :
    if not exist "$(INTDIR)/$(NULL)" mkdir "$(INTDIR)"

CPP=cl.exe
CPP_PROJ=/nologo /MT /W3 /GX /O2 /I "$(JAVA_HOME)\include" /I "$(JAVA_HOME)\include\win32" /D "WIN32" /D "NDEBUG" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "GETTRKVERSION_EXPORTS" /Fp"$(INTDIR)\GetTRKVersion.pch" /Yu"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /c 

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
BSC32_FLAGS=/nologo /o"$(OUTDIR)\GetTRKVersion.bsc" 
BSC32_SBRS= \
	
LINK32=link.exe
LINK32_FLAGS=kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib /nologo /dll /incremental:no /pdb:"$(OUTDIR)\GetTRKVersion.pdb" /machine:I386 /out:"$(OUTDIR)\GetTRKVersion.dll" /implib:"$(OUTDIR)\GetTRKVersion.lib" 
LINK32_OBJS= \
	"$(INTDIR)\GetTRKVersion.obj" \
	"$(INTDIR)\StdAfx.obj"

"$(OUTDIR)\GetTRKVersion.dll" : "$(OUTDIR)" $(DEF_FILE) $(LINK32_OBJS)
    $(LINK32) @<<
  $(LINK32_FLAGS) $(LINK32_OBJS)
<<

!ELSEIF  "$(CFG)" == "GetTRKVersion - Win32 Debug"

OUTDIR=.\Debug
INTDIR=.\Debug
# Begin Custom Macros
OutDir=.\Debug
# End Custom Macros

ALL : "$(OUTDIR)\GetTRKVersion.dll" "$(OUTDIR)\GetTRKVersion.bsc"


CLEAN :
	-@erase "$(INTDIR)\GetTRKVersion.obj"
	-@erase "$(INTDIR)\GetTRKVersion.pch"
	-@erase "$(INTDIR)\GetTRKVersion.sbr"
	-@erase "$(INTDIR)\StdAfx.obj"
	-@erase "$(INTDIR)\StdAfx.sbr"
	-@erase "$(INTDIR)\vc60.idb"
	-@erase "$(INTDIR)\vc60.pdb"
	-@erase "$(OUTDIR)\GetTRKVersion.bsc"
	-@erase "$(OUTDIR)\GetTRKVersion.dll"
	-@erase "$(OUTDIR)\GetTRKVersion.exp"
	-@erase "$(OUTDIR)\GetTRKVersion.ilk"
	-@erase "$(OUTDIR)\GetTRKVersion.lib"
	-@erase "$(OUTDIR)\GetTRKVersion.pdb"

"$(OUTDIR)" :
    if not exist "$(OUTDIR)/$(NULL)" mkdir "$(OUTDIR)"

CPP=cl.exe
CPP_PROJ=/nologo /MTd /W3 /Gm /GX /ZI /Od /I "$(JAVA_HOME)\include" /I "$(JAVA_HOME)\include\win32" /D "WIN32" /D "_DEBUG" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "GETTRKVERSION_EXPORTS" /FR"$(INTDIR)\\" /Fp"$(INTDIR)\GetTRKVersion.pch" /Yu"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /GZ /c 

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
BSC32_FLAGS=/nologo /o"$(OUTDIR)\GetTRKVersion.bsc" 
BSC32_SBRS= \
	"$(INTDIR)\GetTRKVersion.sbr" \
	"$(INTDIR)\StdAfx.sbr"

"$(OUTDIR)\GetTRKVersion.bsc" : "$(OUTDIR)" $(BSC32_SBRS)
    $(BSC32) @<<
  $(BSC32_FLAGS) $(BSC32_SBRS)
<<

LINK32=link.exe
LINK32_FLAGS=kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib /nologo /dll /incremental:yes /pdb:"$(OUTDIR)\GetTRKVersion.pdb" /debug /machine:I386 /out:"$(OUTDIR)\GetTRKVersion.dll" /implib:"$(OUTDIR)\GetTRKVersion.lib" /pdbtype:sept 
LINK32_OBJS= \
	"$(INTDIR)\GetTRKVersion.obj" \
	"$(INTDIR)\StdAfx.obj"

"$(OUTDIR)\GetTRKVersion.dll" : "$(OUTDIR)" $(DEF_FILE) $(LINK32_OBJS)
    $(LINK32) @<<
  $(LINK32_FLAGS) $(LINK32_OBJS)
<<

!ENDIF 


!IF "$(NO_EXTERNAL_DEPS)" != "1"
!IF EXISTS("GetTRKVersion.dep")
!INCLUDE "GetTRKVersion.dep"
!ELSE 
!MESSAGE Warning: cannot find "GetTRKVersion.dep"
!ENDIF 
!ENDIF 


!IF "$(CFG)" == "GetTRKVersion - Win32 Release" || "$(CFG)" == "GetTRKVersion - Win32 Debug"
SOURCE=.\GetTRKVersion.cpp

!IF  "$(CFG)" == "GetTRKVersion - Win32 Release"


"$(INTDIR)\GetTRKVersion.obj" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\GetTRKVersion.pch"


!ELSEIF  "$(CFG)" == "GetTRKVersion - Win32 Debug"


"$(INTDIR)\GetTRKVersion.obj"	"$(INTDIR)\GetTRKVersion.sbr" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\GetTRKVersion.pch"


!ENDIF 

SOURCE=.\StdAfx.cpp

!IF  "$(CFG)" == "GetTRKVersion - Win32 Release"

CPP_SWITCHES=/nologo /MT /W3 /GX /O2 /I "$(JAVA_HOME)\include" /I "$(JAVA_HOME)\include\win32" /D "WIN32" /D "NDEBUG" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "GETTRKVERSION_EXPORTS" /Fp"$(INTDIR)\GetTRKVersion.pch" /Yc"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /c 

"$(INTDIR)\StdAfx.obj"	"$(INTDIR)\GetTRKVersion.pch" : $(SOURCE) "$(INTDIR)"
	$(CPP) @<<
  $(CPP_SWITCHES) $(SOURCE)
<<


!ELSEIF  "$(CFG)" == "GetTRKVersion - Win32 Debug"

CPP_SWITCHES=/nologo /MTd /W3 /Gm /GX /ZI /Od /I "$(JAVA_HOME)\include" /I "$(JAVA_HOME)\include\win32" /D "WIN32" /D "_DEBUG" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "GETTRKVERSION_EXPORTS" /FR"$(INTDIR)\\" /Fp"$(INTDIR)\GetTRKVersion.pch" /Yc"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /GZ /c 

"$(INTDIR)\StdAfx.obj"	"$(INTDIR)\StdAfx.sbr"	"$(INTDIR)\GetTRKVersion.pch" : $(SOURCE) "$(INTDIR)"
	$(CPP) @<<
  $(CPP_SWITCHES) $(SOURCE)
<<


!ENDIF 


!ENDIF 

