/*
============================================================================
 Name		: C$(baseName)Application from $(baseName)Application.h
 Author	  : $(author)
 Version	 :
 Copyright   : $(copyright)
 Description : C$(baseName)Application implementation
============================================================================
*/

#include <eikstart.h>

#include "$(baseName)Application.h"
#include "$(baseName)Document.h"
#include "$(baseName)Globals.h" // contains the applications UID

/**
The function is called by the UI framework to ask for the
application's UID. The returned value is defined by the
constant KUid$(baseName)App and must match the second value
defined in the project definition file.
*/
TUid C$(baseName)Application::AppDllUid() const
	{
	return KUid$(baseName)App;
	}

/**
This function is called by the UI framework at application start-up.
It creates an instance of the document class.
*/
CApaDocument* C$(baseName)Application::CreateDocumentL()
	{
	return C$(baseName)Document::NewL(*this);
	}

/**
The function is called by the framework immediately after it has started the
application's EXE. It is called by the framework and is expected to have
exactly this prototype.

@return Instance of the application class.
*/
CApaApplication* NewApplication()
	{
	return new C$(baseName)Application;
	}

/**
E32Main() contains the program's start up code, the entry point for an EXE.
*/
GLDEF_C TInt E32Main()
	{
	return EikStart::RunApplication(NewApplication);
	}
