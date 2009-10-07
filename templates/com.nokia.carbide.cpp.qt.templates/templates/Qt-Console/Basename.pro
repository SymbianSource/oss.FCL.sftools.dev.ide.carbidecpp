TEMPLATE = app
TARGET = $(baseName) 

$(modulesText) 

HEADERS   +=
SOURCES   += main.cpp
FORMS	  +=
RESOURCES +=

symbian: {
    TARGET.UID3 = $(uid3)

    SOURCES += $(baseName)_reg.rss
}
