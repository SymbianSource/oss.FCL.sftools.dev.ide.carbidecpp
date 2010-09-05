TEMPLATE = app
TARGET = $(baseName) 

$(modulesText) 

HEADERS   +=
SOURCES   += main.cpp \
    $(baseName)_reg.rss
FORMS	  +=
RESOURCES +=

symbian: {
	TARGET.UID3 = $(uid3)
	MMP_RULES += DEBUGGABLE_UDEBONLY 
}
