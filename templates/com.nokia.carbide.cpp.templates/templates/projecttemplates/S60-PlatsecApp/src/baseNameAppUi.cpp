/*
============================================================================
 Name		: $(baseName)AppUi.cpp
 Author	  : $(author)
 Copyright   : $(copyright)
 Description : C$(baseName)AppUi implementation
============================================================================
*/

// INCLUDE FILES
#include <avkon.hrh>
#include <aknmessagequerydialog.h>
#include <aknnotewrappers.h>
#include <stringloader.h>
#include <f32file.h>
#include <s32file.h>
#include <hlplch.h>

#include <$(baseName$lower)_$(uid3).rsg>

#ifdef _HELP_AVAILABLE_
#include "$(baseName)_$(uid3).hlp.hrh"
#endif
#include "$(baseName).hrh"
#include "$(baseName).pan"
#include "$(baseName)Application.h"
#include "$(baseName)AppUi.h"
#include "$(baseName)AppView.h"

_LIT( KFileName, "C:\\private\\$(uid3-WITHOUT_0X)\\$(baseName).txt" );
_LIT( KText, "$(message)");

// ============================ MEMBER FUNCTIONS ===============================


// -----------------------------------------------------------------------------
// C$(baseName)AppUi::ConstructL()
// Symbian 2nd phase constructor can leave.
// -----------------------------------------------------------------------------
//
void C$(baseName)AppUi::ConstructL()
	{
	// Initialise app UI with standard value.
	BaseConstructL(CAknAppUi::EAknEnableSkin);

	// Create view object
	iAppView = C$(baseName)AppView::NewL( ClientRect() );

	// Create a file to write the text to
	TInt err = CCoeEnv::Static()->FsSession().MkDirAll(KFileName);
	if ( (KErrNone != err) && (KErrAlreadyExists != err) )
		{
		return;
		}
	
	RFile file;
	err = file.Replace(CCoeEnv::Static()->FsSession(), KFileName, EFileWrite );
	CleanupClosePushL( file );
	if ( KErrNone != err )
		{
		CleanupStack::PopAndDestroy(1); // file
		return;
		}
	
	RFileWriteStream outputFileStream( file );
	CleanupClosePushL( outputFileStream );
	outputFileStream << KText;

	CleanupStack::PopAndDestroy(2); // outputFileStream, file

	}
// -----------------------------------------------------------------------------
// C$(baseName)AppUi::C$(baseName)AppUi()
// C++ default constructor can NOT contain any code, that might leave.
// -----------------------------------------------------------------------------
//
C$(baseName)AppUi::C$(baseName)AppUi()
	{
	// No implementation required
	}

// -----------------------------------------------------------------------------
// C$(baseName)AppUi::~C$(baseName)AppUi()
// Destructor.
// -----------------------------------------------------------------------------
//
C$(baseName)AppUi::~C$(baseName)AppUi()
	{
	if ( iAppView )
		{
		delete iAppView;
		iAppView = NULL;
		}

	}

// -----------------------------------------------------------------------------
// C$(baseName)AppUi::HandleCommandL()
// Takes care of command handling.
// -----------------------------------------------------------------------------
//
void C$(baseName)AppUi::HandleCommandL( TInt aCommand )
	{
	switch( aCommand )
		{
		case EEikCmdExit:
		case EAknSoftkeyExit:
			Exit();
			break;

		case ECommand1:
			{
			
			// Load a string from the resource file and display it
			HBufC* textResource = StringLoader::LoadLC( R_COMMAND1_TEXT );
			CAknInformationNote* informationNote;

			informationNote = new ( ELeave ) CAknInformationNote;

			// Show the information Note with
			// textResource loaded with StringLoader.
			informationNote->ExecuteLD( *textResource );

			// Pop HBuf from CleanUpStack and Destroy it.
			CleanupStack::PopAndDestroy( textResource );
			}
			break;
		case ECommand2:
			{
			RFile rFile;
			
			//Open file where the stream text is
			User::LeaveIfError(rFile.Open(CCoeEnv::Static()->FsSession(),KFileName, EFileStreamText));//EFileShareReadersOnly));// EFileStreamText));
			CleanupClosePushL(rFile);
			
			// copy stream from file to RFileStream object
			RFileReadStream inputFileStream(rFile);
			CleanupClosePushL(inputFileStream);
			
			// HBufC descriptor is created from the RFileStream object.
			HBufC* fileData = HBufC::NewLC(inputFileStream, 32);

			CAknInformationNote* informationNote;

			informationNote = new ( ELeave ) CAknInformationNote;
			// Show the information Note
			informationNote->ExecuteLD( *fileData);			
			
			// Pop loaded resources from the cleanup stack
			CleanupStack::PopAndDestroy(3); // filedata, inputFileStream, rFile
			}
			break;
		case EHelp:
			{
			
			CArrayFix<TCoeHelpContext>* buf = CCoeAppUi::AppHelpContextL();
			HlpLauncher::LaunchHelpApplicationL(iEikonEnv->WsSession(), buf);
			}
			break;
		case EAbout:
			{
			
			CAknMessageQueryDialog* dlg = new (ELeave)CAknMessageQueryDialog(); 
			dlg->PrepareLC(R_ABOUT_QUERY_DIALOG);
			HBufC* title = iEikonEnv->AllocReadResourceLC(R_ABOUT_DIALOG_TITLE);
			dlg->QueryHeading()->SetTextL(*title);
			CleanupStack::PopAndDestroy(); //title
			HBufC* msg = iEikonEnv->AllocReadResourceLC(R_ABOUT_DIALOG_TEXT);
			dlg->SetMessageTextL(*msg);
			CleanupStack::PopAndDestroy(); //msg
			dlg->RunLD(); 
			}
			break;
		default:
			Panic( E$(baseName)Ui );
			break;
		}
	}
// -----------------------------------------------------------------------------
//  Called by the framework when the application status pane
//  size is changed.  Passes the new client rectangle to the
//  AppView
// -----------------------------------------------------------------------------
//
void C$(baseName)AppUi::HandleStatusPaneSizeChange()
	{
	iAppView->SetRect( ClientRect() );
	} 

CArrayFix<TCoeHelpContext>* C$(baseName)AppUi::HelpContextL() const
	{
#warning "Please see comment about help and UID3..."
	// Note: Help will not work if the application uid3 is not in the
	// protected range.  The default uid3 range for projects created
	// from this template (0xE0000000 - 0xEFFFFFFF) are not in the protected range so that they
	// can be self signed and installed on the device during testing.
	// Once you get your official uid3 from Symbian Ltd. and find/replace
	// all occurrences of uid3 in your project, the context help will
	// work. Alternatively, a patch now exists for the versions of 
	// HTML help compiler in SDKs and can be found here along with an FAQ:
	// http://www3.symbian.com/faq.nsf/AllByDate/E9DF3257FD565A658025733900805EA2?OpenDocument
#ifdef _HELP_AVAILABLE_
    CArrayFixFlat<TCoeHelpContext>* array = new(ELeave)CArrayFixFlat<TCoeHelpContext>(1);
	CleanupStack::PushL(array);
	array->AppendL(TCoeHelpContext(KUid$(baseName)App, KGeneral_Information));
	CleanupStack::Pop(array);
	return array;
#else
    return NULL;
#endif
	}

// End of File
