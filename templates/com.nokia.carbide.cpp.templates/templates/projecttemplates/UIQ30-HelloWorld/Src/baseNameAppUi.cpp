/*
============================================================================
 Name		: C$(baseName)AppUi from $(baseName)AppUi.h
 Author	  : $(author)
 Version	 :
 Copyright   : $(copyright)
 Description : C$(baseName)AppUi implementation
============================================================================
*/

#include "$(baseName)AppUi.h"
#include "$(baseName)View.h"

/**
2nd stage construction of the App UI.
Create view and add it to the framework.
The framework will take over the ownership.
*/
void C$(baseName)AppUi::ConstructL()
	{
	// Calls ConstructL that initiate the standard values.
	CQikAppUi::ConstructL();

	// Create the view and add it to the framework
	C$(baseName)View* appView = C$(baseName)View::NewLC(*this);
	AddViewL(*appView);
	CleanupStack::Pop(appView);
	}
