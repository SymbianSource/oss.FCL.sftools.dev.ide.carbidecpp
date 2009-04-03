/*
============================================================================
 Name		: $(baseName).cpp
 Author	  : $(author)
 Copyright   : $(copyright)
 Description : Main application class
============================================================================
*/

// INCLUDE FILES
#include <eikstart.h>
#include "$(baseName)Application.h"


LOCAL_C CApaApplication* NewApplication()
	{
	return new C$(baseName)Application;
	}

GLDEF_C TInt E32Main()
	{
	return EikStart::RunApplication( NewApplication );
	}

