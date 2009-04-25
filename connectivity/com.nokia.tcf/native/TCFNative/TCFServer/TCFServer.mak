# Microsoft Developer Studio Generated NMAKE File, Based on TCFServer.dsp
!IF "$(CFG)" == ""
CFG=TCFServer - Win32 Debug
!MESSAGE No configuration specified. Defaulting to TCFServer - Win32 Debug.
!ENDIF 

!IF "$(CFG)" != "TCFServer - Win32 Release" && "$(CFG)" != "TCFServer - Win32 Debug"
!MESSAGE Invalid configuration "$(CFG)" specified.
!MESSAGE You can specify a configuration when running NMAKE
!MESSAGE by defining the macro CFG on the command line. For example:
!MESSAGE 
!MESSAGE NMAKE /f "TCFServer.mak" CFG="TCFServer - Win32 Debug"
!MESSAGE 
!MESSAGE Possible choices for configuration are:
!MESSAGE 
!MESSAGE "TCFServer - Win32 Release" (based on "Win32 (x86) Console Application")
!MESSAGE "TCFServer - Win32 Debug" (based on "Win32 (x86) Console Application")
!MESSAGE 
!ERROR An invalid configuration is specified.
!ENDIF 

!IF "$(OS)" == "Windows_NT"
NULL=
!ELSE 
NULL=nul
!ENDIF 

!IF  "$(CFG)" == "TCFServer - Win32 Release"

OUTDIR=.\Release
INTDIR=.\Release
# Begin Custom Macros
OutDir=.\Release
# End Custom Macros

ALL : "$(OUTDIR)\TCFServer.exe"


CLEAN :
	-@erase "$(INTDIR)\Client.obj"
	-@erase "$(INTDIR)\CommRegistryItem.obj"
	-@erase "$(INTDIR)\Connection.obj"
	-@erase "$(INTDIR)\ConnectionImpl.obj"
	-@erase "$(INTDIR)\ErrorMonitorData.obj"
	-@erase "$(INTDIR)\InputStream.obj"
	-@erase "$(INTDIR)\MessageFile.obj"
	-@erase "$(INTDIR)\mutex.obj"
	-@erase "$(INTDIR)\ProtocolRegistryItem.obj"
	-@erase "$(INTDIR)\Registry.obj"
	-@erase "$(INTDIR)\RegistryImpl.obj"
	-@erase "$(INTDIR)\resource.res"
	-@erase "$(INTDIR)\ServerClient.obj"
	-@erase "$(INTDIR)\ServerManager.obj"
	-@erase "$(INTDIR)\shareddata.obj"
	-@erase "$(INTDIR)\StdAfx.obj"
	-@erase "$(INTDIR)\TCDebugLog.obj"
	-@erase "$(INTDIR)\TCFServer.obj"
	-@erase "$(INTDIR)\TCFServer.pch"
	-@erase "$(INTDIR)\vc60.idb"
	-@erase "$(OUTDIR)\TCFServer.exe"

"$(OUTDIR)" :
    if not exist "$(OUTDIR)/$(NULL)" mkdir "$(OUTDIR)"

CPP=cl.exe
CPP_PROJ=/nologo /Zp2 /MT /W3 /GX /O2 /I "..\Common\Headers" /I "..\Common\Source" /D "WIN32" /D "NDEBUG" /D "_CONSOLE" /D "_MBCS" /Fp"$(INTDIR)\TCFServer.pch" /Yu"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /c 

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

RSC=rc.exe
RSC_PROJ=/l 0x409 /fo"$(INTDIR)\resource.res" /d "NDEBUG" 
BSC32=bscmake.exe
BSC32_FLAGS=/nologo /o"$(OUTDIR)\TCFServer.bsc" 
BSC32_SBRS= \
	
LINK32=link.exe
LINK32_FLAGS=kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib ws2_32.lib shlwapi.lib /nologo /subsystem:console /incremental:no /pdb:"$(OUTDIR)\TCFServer.pdb" /machine:I386 /out:"$(OUTDIR)\TCFServer.exe" 
LINK32_OBJS= \
	"$(INTDIR)\Client.obj" \
	"$(INTDIR)\CommRegistryItem.obj" \
	"$(INTDIR)\Connection.obj" \
	"$(INTDIR)\ConnectionImpl.obj" \
	"$(INTDIR)\ErrorMonitorData.obj" \
	"$(INTDIR)\InputStream.obj" \
	"$(INTDIR)\MessageFile.obj" \
	"$(INTDIR)\mutex.obj" \
	"$(INTDIR)\ProtocolRegistryItem.obj" \
	"$(INTDIR)\Registry.obj" \
	"$(INTDIR)\RegistryImpl.obj" \
	"$(INTDIR)\ServerClient.obj" \
	"$(INTDIR)\ServerManager.obj" \
	"$(INTDIR)\shareddata.obj" \
	"$(INTDIR)\StdAfx.obj" \
	"$(INTDIR)\TCDebugLog.obj" \
	"$(INTDIR)\TCFServer.obj" \
	"$(INTDIR)\resource.res"

"$(OUTDIR)\TCFServer.exe" : "$(OUTDIR)" $(DEF_FILE) $(LINK32_OBJS)
    $(LINK32) @<<
  $(LINK32_FLAGS) $(LINK32_OBJS)
<<

SOURCE="$(InputPath)"
PostBuild_Desc=copy binary
DS_POSTBUILD_DEP=$(INTDIR)\postbld.dep

ALL : $(DS_POSTBUILD_DEP)

# Begin Custom Macros
OutDir=.\Release
# End Custom Macros

$(DS_POSTBUILD_DEP) : "$(OUTDIR)\TCFServer.exe"
   copybinaries Release
	echo Helper for Post-build step > "$(DS_POSTBUILD_DEP)"

!ELSEIF  "$(CFG)" == "TCFServer - Win32 Debug"

OUTDIR=.\Debug
INTDIR=.\Debug
# Begin Custom Macros
OutDir=.\Debug
# End Custom Macros

ALL : "$(OUTDIR)\TCFServer.exe" "$(OUTDIR)\TCFServer.bsc"


CLEAN :
	-@erase "$(INTDIR)\Client.obj"
	-@erase "$(INTDIR)\Client.sbr"
	-@erase "$(INTDIR)\CommRegistryItem.obj"
	-@erase "$(INTDIR)\CommRegistryItem.sbr"
	-@erase "$(INTDIR)\Connection.obj"
	-@erase "$(INTDIR)\Connection.sbr"
	-@erase "$(INTDIR)\ConnectionImpl.obj"
	-@erase "$(INTDIR)\ConnectionImpl.sbr"
	-@erase "$(INTDIR)\ErrorMonitorData.obj"
	-@erase "$(INTDIR)\ErrorMonitorData.sbr"
	-@erase "$(INTDIR)\InputStream.obj"
	-@erase "$(INTDIR)\InputStream.sbr"
	-@erase "$(INTDIR)\MessageFile.obj"
	-@erase "$(INTDIR)\MessageFile.sbr"
	-@erase "$(INTDIR)\mutex.obj"
	-@erase "$(INTDIR)\mutex.sbr"
	-@erase "$(INTDIR)\ProtocolRegistryItem.obj"
	-@erase "$(INTDIR)\ProtocolRegistryItem.sbr"
	-@erase "$(INTDIR)\Registry.obj"
	-@erase "$(INTDIR)\Registry.sbr"
	-@erase "$(INTDIR)\RegistryImpl.obj"
	-@erase "$(INTDIR)\RegistryImpl.sbr"
	-@erase "$(INTDIR)\resource.res"
	-@erase "$(INTDIR)\ServerClient.obj"
	-@erase "$(INTDIR)\ServerClient.sbr"
	-@erase "$(INTDIR)\ServerManager.obj"
	-@erase "$(INTDIR)\ServerManager.sbr"
	-@erase "$(INTDIR)\shareddata.obj"
	-@erase "$(INTDIR)\shareddata.sbr"
	-@erase "$(INTDIR)\StdAfx.obj"
	-@erase "$(INTDIR)\StdAfx.sbr"
	-@erase "$(INTDIR)\TCDebugLog.obj"
	-@erase "$(INTDIR)\TCDebugLog.sbr"
	-@erase "$(INTDIR)\TCFServer.obj"
	-@erase "$(INTDIR)\TCFServer.pch"
	-@erase "$(INTDIR)\TCFServer.sbr"
	-@erase "$(INTDIR)\vc60.idb"
	-@erase "$(INTDIR)\vc60.pdb"
	-@erase "$(OUTDIR)\TCFServer.bsc"
	-@erase "$(OUTDIR)\TCFServer.exe"
	-@erase "$(OUTDIR)\TCFServer.ilk"
	-@erase "$(OUTDIR)\TCFServer.pdb"

"$(OUTDIR)" :
    if not exist "$(OUTDIR)/$(NULL)" mkdir "$(OUTDIR)"

CPP=cl.exe
CPP_PROJ=/nologo /Zp2 /MTd /W3 /Gm /GX /ZI /Od /I "..\Common\Headers" /I "..\Common\Source" /D "WIN32" /D "_DEBUG" /D "_CONSOLE" /D "_MBCS" /FR"$(INTDIR)\\" /Fp"$(INTDIR)\TCFServer.pch" /Yu"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /GZ /c 

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

RSC=rc.exe
RSC_PROJ=/l 0x409 /fo"$(INTDIR)\resource.res" /d "_DEBUG" 
BSC32=bscmake.exe
BSC32_FLAGS=/nologo /o"$(OUTDIR)\TCFServer.bsc" 
BSC32_SBRS= \
	"$(INTDIR)\Client.sbr" \
	"$(INTDIR)\CommRegistryItem.sbr" \
	"$(INTDIR)\Connection.sbr" \
	"$(INTDIR)\ConnectionImpl.sbr" \
	"$(INTDIR)\ErrorMonitorData.sbr" \
	"$(INTDIR)\InputStream.sbr" \
	"$(INTDIR)\MessageFile.sbr" \
	"$(INTDIR)\mutex.sbr" \
	"$(INTDIR)\ProtocolRegistryItem.sbr" \
	"$(INTDIR)\Registry.sbr" \
	"$(INTDIR)\RegistryImpl.sbr" \
	"$(INTDIR)\ServerClient.sbr" \
	"$(INTDIR)\ServerManager.sbr" \
	"$(INTDIR)\shareddata.sbr" \
	"$(INTDIR)\StdAfx.sbr" \
	"$(INTDIR)\TCDebugLog.sbr" \
	"$(INTDIR)\TCFServer.sbr"

"$(OUTDIR)\TCFServer.bsc" : "$(OUTDIR)" $(BSC32_SBRS)
    $(BSC32) @<<
  $(BSC32_FLAGS) $(BSC32_SBRS)
<<

LINK32=link.exe
LINK32_FLAGS=kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib ws2_32.lib shlwapi.lib /nologo /subsystem:console /incremental:yes /pdb:"$(OUTDIR)\TCFServer.pdb" /debug /machine:I386 /out:"$(OUTDIR)\TCFServer.exe" /pdbtype:sept 
LINK32_OBJS= \
	"$(INTDIR)\Client.obj" \
	"$(INTDIR)\CommRegistryItem.obj" \
	"$(INTDIR)\Connection.obj" \
	"$(INTDIR)\ConnectionImpl.obj" \
	"$(INTDIR)\ErrorMonitorData.obj" \
	"$(INTDIR)\InputStream.obj" \
	"$(INTDIR)\MessageFile.obj" \
	"$(INTDIR)\mutex.obj" \
	"$(INTDIR)\ProtocolRegistryItem.obj" \
	"$(INTDIR)\Registry.obj" \
	"$(INTDIR)\RegistryImpl.obj" \
	"$(INTDIR)\ServerClient.obj" \
	"$(INTDIR)\ServerManager.obj" \
	"$(INTDIR)\shareddata.obj" \
	"$(INTDIR)\StdAfx.obj" \
	"$(INTDIR)\TCDebugLog.obj" \
	"$(INTDIR)\TCFServer.obj" \
	"$(INTDIR)\resource.res"

"$(OUTDIR)\TCFServer.exe" : "$(OUTDIR)" $(DEF_FILE) $(LINK32_OBJS)
    $(LINK32) @<<
  $(LINK32_FLAGS) $(LINK32_OBJS)
<<

SOURCE="$(InputPath)"
PostBuild_Desc=copy binary
DS_POSTBUILD_DEP=$(INTDIR)\postbld.dep

ALL : $(DS_POSTBUILD_DEP)

# Begin Custom Macros
OutDir=.\Debug
# End Custom Macros

$(DS_POSTBUILD_DEP) : "$(OUTDIR)\TCFServer.exe" "$(OUTDIR)\TCFServer.bsc"
   copybinaries Debug
	echo Helper for Post-build step > "$(DS_POSTBUILD_DEP)"

!ENDIF 


!IF "$(NO_EXTERNAL_DEPS)" != "1"
!IF EXISTS("TCFServer.dep")
!INCLUDE "TCFServer.dep"
!ELSE 
!MESSAGE Warning: cannot find "TCFServer.dep"
!ENDIF 
!ENDIF 


!IF "$(CFG)" == "TCFServer - Win32 Release" || "$(CFG)" == "TCFServer - Win32 Debug"
SOURCE=.\Client.cpp

!IF  "$(CFG)" == "TCFServer - Win32 Release"


"$(INTDIR)\Client.obj" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFServer.pch"


!ELSEIF  "$(CFG)" == "TCFServer - Win32 Debug"


"$(INTDIR)\Client.obj"	"$(INTDIR)\Client.sbr" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFServer.pch"


!ENDIF 

SOURCE=.\CommRegistryItem.cpp

!IF  "$(CFG)" == "TCFServer - Win32 Release"


"$(INTDIR)\CommRegistryItem.obj" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFServer.pch"


!ELSEIF  "$(CFG)" == "TCFServer - Win32 Debug"


"$(INTDIR)\CommRegistryItem.obj"	"$(INTDIR)\CommRegistryItem.sbr" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFServer.pch"


!ENDIF 

SOURCE=.\Connection.cpp

!IF  "$(CFG)" == "TCFServer - Win32 Release"


"$(INTDIR)\Connection.obj" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFServer.pch"


!ELSEIF  "$(CFG)" == "TCFServer - Win32 Debug"


"$(INTDIR)\Connection.obj"	"$(INTDIR)\Connection.sbr" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFServer.pch"


!ENDIF 

SOURCE=.\ConnectionImpl.cpp

!IF  "$(CFG)" == "TCFServer - Win32 Release"


"$(INTDIR)\ConnectionImpl.obj" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFServer.pch"


!ELSEIF  "$(CFG)" == "TCFServer - Win32 Debug"


"$(INTDIR)\ConnectionImpl.obj"	"$(INTDIR)\ConnectionImpl.sbr" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFServer.pch"


!ENDIF 

SOURCE=..\Common\Source\ErrorMonitorData.cpp

!IF  "$(CFG)" == "TCFServer - Win32 Release"


"$(INTDIR)\ErrorMonitorData.obj" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFServer.pch"
	$(CPP) $(CPP_PROJ) $(SOURCE)


!ELSEIF  "$(CFG)" == "TCFServer - Win32 Debug"


"$(INTDIR)\ErrorMonitorData.obj"	"$(INTDIR)\ErrorMonitorData.sbr" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFServer.pch"
	$(CPP) $(CPP_PROJ) $(SOURCE)


!ENDIF 

SOURCE=..\Common\Source\InputStream.cpp

!IF  "$(CFG)" == "TCFServer - Win32 Release"


"$(INTDIR)\InputStream.obj" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFServer.pch"
	$(CPP) $(CPP_PROJ) $(SOURCE)


!ELSEIF  "$(CFG)" == "TCFServer - Win32 Debug"


"$(INTDIR)\InputStream.obj"	"$(INTDIR)\InputStream.sbr" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFServer.pch"
	$(CPP) $(CPP_PROJ) $(SOURCE)


!ENDIF 

SOURCE=.\MessageFile.cpp

!IF  "$(CFG)" == "TCFServer - Win32 Release"


"$(INTDIR)\MessageFile.obj" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFServer.pch"


!ELSEIF  "$(CFG)" == "TCFServer - Win32 Debug"


"$(INTDIR)\MessageFile.obj"	"$(INTDIR)\MessageFile.sbr" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFServer.pch"


!ENDIF 

SOURCE=..\Common\Source\mutex.cpp

!IF  "$(CFG)" == "TCFServer - Win32 Release"


"$(INTDIR)\mutex.obj" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFServer.pch"
	$(CPP) $(CPP_PROJ) $(SOURCE)


!ELSEIF  "$(CFG)" == "TCFServer - Win32 Debug"


"$(INTDIR)\mutex.obj"	"$(INTDIR)\mutex.sbr" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFServer.pch"
	$(CPP) $(CPP_PROJ) $(SOURCE)


!ENDIF 

SOURCE=.\ProtocolRegistryItem.cpp

!IF  "$(CFG)" == "TCFServer - Win32 Release"


"$(INTDIR)\ProtocolRegistryItem.obj" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFServer.pch"


!ELSEIF  "$(CFG)" == "TCFServer - Win32 Debug"


"$(INTDIR)\ProtocolRegistryItem.obj"	"$(INTDIR)\ProtocolRegistryItem.sbr" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFServer.pch"


!ENDIF 

SOURCE=.\Registry.cpp

!IF  "$(CFG)" == "TCFServer - Win32 Release"


"$(INTDIR)\Registry.obj" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFServer.pch"


!ELSEIF  "$(CFG)" == "TCFServer - Win32 Debug"


"$(INTDIR)\Registry.obj"	"$(INTDIR)\Registry.sbr" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFServer.pch"


!ENDIF 

SOURCE=.\RegistryImpl.cpp

!IF  "$(CFG)" == "TCFServer - Win32 Release"


"$(INTDIR)\RegistryImpl.obj" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFServer.pch"


!ELSEIF  "$(CFG)" == "TCFServer - Win32 Debug"


"$(INTDIR)\RegistryImpl.obj"	"$(INTDIR)\RegistryImpl.sbr" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFServer.pch"


!ENDIF 

SOURCE=..\Common\Source\ServerClient.cpp

!IF  "$(CFG)" == "TCFServer - Win32 Release"


"$(INTDIR)\ServerClient.obj" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFServer.pch"
	$(CPP) $(CPP_PROJ) $(SOURCE)


!ELSEIF  "$(CFG)" == "TCFServer - Win32 Debug"


"$(INTDIR)\ServerClient.obj"	"$(INTDIR)\ServerClient.sbr" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFServer.pch"
	$(CPP) $(CPP_PROJ) $(SOURCE)


!ENDIF 

SOURCE=.\ServerManager.cpp

!IF  "$(CFG)" == "TCFServer - Win32 Release"


"$(INTDIR)\ServerManager.obj" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFServer.pch"


!ELSEIF  "$(CFG)" == "TCFServer - Win32 Debug"


"$(INTDIR)\ServerManager.obj"	"$(INTDIR)\ServerManager.sbr" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFServer.pch"


!ENDIF 

SOURCE=..\Common\Source\shareddata.cpp

!IF  "$(CFG)" == "TCFServer - Win32 Release"


"$(INTDIR)\shareddata.obj" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFServer.pch"
	$(CPP) $(CPP_PROJ) $(SOURCE)


!ELSEIF  "$(CFG)" == "TCFServer - Win32 Debug"


"$(INTDIR)\shareddata.obj"	"$(INTDIR)\shareddata.sbr" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFServer.pch"
	$(CPP) $(CPP_PROJ) $(SOURCE)


!ENDIF 

SOURCE=.\StdAfx.cpp

!IF  "$(CFG)" == "TCFServer - Win32 Release"

CPP_SWITCHES=/nologo /Zp2 /MT /W3 /GX /O2 /I "..\Common\Headers" /I "..\Common\Source" /D "WIN32" /D "NDEBUG" /D "_CONSOLE" /D "_MBCS" /Fp"$(INTDIR)\TCFServer.pch" /Yc"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /c 

"$(INTDIR)\StdAfx.obj"	"$(INTDIR)\TCFServer.pch" : $(SOURCE) "$(INTDIR)"
	$(CPP) @<<
  $(CPP_SWITCHES) $(SOURCE)
<<


!ELSEIF  "$(CFG)" == "TCFServer - Win32 Debug"

CPP_SWITCHES=/nologo /Zp2 /MTd /W3 /Gm /GX /ZI /Od /I "..\Common\Headers" /I "..\Common\Source" /D "WIN32" /D "_DEBUG" /D "_CONSOLE" /D "_MBCS" /FR"$(INTDIR)\\" /Fp"$(INTDIR)\TCFServer.pch" /Yc"stdafx.h" /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /GZ /c 

"$(INTDIR)\StdAfx.obj"	"$(INTDIR)\StdAfx.sbr"	"$(INTDIR)\TCFServer.pch" : $(SOURCE) "$(INTDIR)"
	$(CPP) @<<
  $(CPP_SWITCHES) $(SOURCE)
<<


!ENDIF 

SOURCE=..\Common\Source\TCDebugLog.cpp

!IF  "$(CFG)" == "TCFServer - Win32 Release"


"$(INTDIR)\TCDebugLog.obj" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFServer.pch"
	$(CPP) $(CPP_PROJ) $(SOURCE)


!ELSEIF  "$(CFG)" == "TCFServer - Win32 Debug"


"$(INTDIR)\TCDebugLog.obj"	"$(INTDIR)\TCDebugLog.sbr" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFServer.pch"
	$(CPP) $(CPP_PROJ) $(SOURCE)


!ENDIF 

SOURCE=.\TCFServer.cpp

!IF  "$(CFG)" == "TCFServer - Win32 Release"


"$(INTDIR)\TCFServer.obj" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFServer.pch"


!ELSEIF  "$(CFG)" == "TCFServer - Win32 Debug"


"$(INTDIR)\TCFServer.obj"	"$(INTDIR)\TCFServer.sbr" : $(SOURCE) "$(INTDIR)" "$(INTDIR)\TCFServer.pch"


!ENDIF 

SOURCE=.\resource.rc

"$(INTDIR)\resource.res" : $(SOURCE) "$(INTDIR)"
	$(RSC) $(RSC_PROJ) $(SOURCE)



!ENDIF 

