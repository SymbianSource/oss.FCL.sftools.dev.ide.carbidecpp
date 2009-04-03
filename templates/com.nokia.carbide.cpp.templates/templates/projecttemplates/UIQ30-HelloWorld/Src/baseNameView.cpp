/*
============================================================================
 Name		: C$(baseName)View from $(baseName)View.h
 Author	  : $(author)
 Version	 :
 Copyright   : $(copyright)
 Description : C$(baseName)View implementation
============================================================================
*/

#include <QikCommand.h>
#include <$(baseName).rsg>
#include <eiklabel.h> // CEikLabel

#include "$(baseName)AppUi.h"
#include "$(baseName)View.h"
#include "$(baseName).hrh"
#include "$(baseName)Globals.h"

/**
Creates and constructs the view.

@param aAppUi Reference to the AppUi
@return Pointer to a C$(baseName)View object
*/
C$(baseName)View* C$(baseName)View::NewLC(CQikAppUi& aAppUi)
	{
	C$(baseName)View* self = new(ELeave) C$(baseName)View(aAppUi);
	CleanupStack::PushL(self);
	self->ConstructL();
	return self;
	}

/**
Constructor for the view.
Passes the application UI reference to the construction of the super class.

KNullViewId should normally be passed as parent view for the applications
default view. The parent view is the logical view that is normally activated
when a go back command is issued. KNullViewId will activate the system
default view.

@param aAppUi Reference to the application UI
*/
C$(baseName)View::C$(baseName)View(CQikAppUi& aAppUi)
	: CQikViewBase(aAppUi, KNullViewId)
	{
	}

/**
Destructor for the view
*/
C$(baseName)View::~C$(baseName)View()
	{
	}

/**
2nd stage construction of the App UI.
*/
void C$(baseName)View::ConstructL()
	{
	// Calls ConstructL that initialises the standard values.
	// This should always be called in the concrete view implementations.
	CQikViewBase::ConstructL();
	}

/**
Inherited from CQikViewBase and called upon by the UI Framework.
It creates the view from resource.
*/
void C$(baseName)View::ViewConstructL()
	{
	// Loads information about the UI configurations this view supports
	// together with definition of each view.
	ViewConstructFromResourceL(R_UI_CONFIGURATIONS);
	}

/**
Returns the view Id

@return Returns the Uid of the view
*/
TVwsViewId C$(baseName)View::ViewId()const
	{
	return TVwsViewId(KUid$(baseName)App, KUid$(baseName)View);
	}

/*
Handles all commands in the view
Called by the UI framework when a command has been issued.
The command Ids are defined in the .hrh file.

@param aCommand The command to be executed
@see CQikViewBase::HandleCommandL
*/
void C$(baseName)View::HandleCommandL(CQikCommand& aCommand)
	{
	switch(aCommand.Id())
		{
		// Just issue simple info messages to show that
		// the commands have been selected

		case E$(baseName)InfoPrint1Cmd:
			{
			// Shows an infoprint
			iEikonEnv->InfoMsg(R_INFOPRINT1_TEXT);
			break;
			}
		case E$(baseName)InfoPrint2Cmd:
			{
			// Shows an infoprint
			iEikonEnv->InfoMsg(R_INFOPRINT2_TEXT);
			break;
			}
		case E$(baseName)InfoPrint3Cmd:
			{
			// Shows an infoprint
			iEikonEnv->InfoMsg(R_INFOPRINT3_TEXT);
			break;
			}
		// Go back and exit command will be passed to the CQikViewBase to handle.
		default:
			CQikViewBase::HandleCommandL(aCommand);
			break;
		}
	}
