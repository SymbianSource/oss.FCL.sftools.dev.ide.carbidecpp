TEMPLATE = app
TARGET = $(baseName) 

$(modulesText) 

HEADERS   += $(className).h
SOURCES   += $(baseName)_reg.rss \
    main.cpp \
    $(className).cpp
FORMS	  += $(baseName).ui
RESOURCES +=

symbian:TARGET.UID3 = $(uid3)
