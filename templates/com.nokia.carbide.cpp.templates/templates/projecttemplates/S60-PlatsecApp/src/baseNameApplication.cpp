/*
============================================================================
 Name		: $(baseName)Application.cpp
 Author	  : $(author)
 Copyright   : $(copyright)
 Description : Main application class
============================================================================
*/

// INCLUDE FILES
#include "$(baseName).hrh"
#include "$(baseName)Document.h"
#include "$(baseName)Application.h"

// ============================ MEMBER FUNCTIONS ===============================

// -----------------------------------------------------------------------------
// C$(baseName)Application::CreateDocumentL()
// Creates CApaDocument object
// -----------------------------------------------------------------------------
//
CApaDocument* C$(baseName)Application::CreateDocumentL()
	{
	// Create an $(baseName) document, and return a pointer to it
	return C$(baseName)Document::NewL(*this);
	}

// -----------------------------------------------------------------------------
// C$(baseName)Application::AppDllUid()
// Returns application UID
// -----------------------------------------------------------------------------
//
TUid C$(baseName)Application::AppDllUid() const
	{
	// Return the UID for the $(baseName) application
	return KUid$(baseName)App;
	}

// End of File
