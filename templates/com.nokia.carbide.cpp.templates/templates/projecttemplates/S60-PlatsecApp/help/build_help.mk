# ============================================================================
#  Name	 : build_help.mk
#  Part of  : $(baseName)
# ============================================================================
#  Name	 : build_help.mk
#  Part of  : $(baseName)
#
#  Description: This make file will build the application help file (.hlp)
# 
# ============================================================================

do_nothing :
	@rem do_nothing

# build the help from the MAKMAKE step so the header file generated
# will be found by cpp.exe when calculating the dependency information
# in the mmp makefiles.

MAKMAKE : $(baseName)_$(uid3).hlp
$(baseName)_$(uid3).hlp : $(baseName).xml $(baseName).cshlp Custom.xml
	$(cshlpcmp) $(baseName).cshlp
ifeq (WINSCW,$(findstring WINSCW, $(PLATFORM)))
	md $(EPOCROOT)epoc32\$(PLATFORM)\c\resource\help
	copy $(baseName)_$(uid3).hlp $(EPOCROOT)epoc32\$(PLATFORM)\c\resource\help
endif

BLD : do_nothing

CLEAN :
	del $(baseName)_$(uid3).hlp
	del $(baseName)_$(uid3).hlp.hrh

LIB : do_nothing

CLEANLIB : do_nothing

RESOURCE : do_nothing
		
FREEZE : do_nothing

SAVESPACE : do_nothing

RELEASABLES :
	@echo $(baseName)_$(uid3).hlp

FINAL : do_nothing
