/*
========================================================================
 Name		: SearchResultsView.cpp

 Description: 

 Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
 All rights reserved.
 This component and the accompanying materials are made available
 under the terms of the License "Eclipse Public License v1.0"
 which accompanies this distribution, and is available
 at the URL "http://www.eclipse.org/legal/epl-v10.html".

 Contributors:
 Nokia Corporation - initial contribution.
========================================================================
*/
// [[[ begin generated region: do not modify [Generated System Includes]
#include <aknviewappui.h>
#include <eikmenub.h>
#include <avkon.hrh>
#include <barsread.h>
#include <stringloader.h>
#include <aknlists.h>
#include <eikenv.h>
#include <akniconarray.h>
#include <eikclbd.h>
#include <akncontext.h>
#include <akntitle.h>
#include <eikbtgpc.h>
#include <gdi.h>
#include <eikedwin.h>
#include <aknquerydialog.h>
#include <aknnotewrappers.h>
#include <$(baseName).rsg>
// ]]] end generated region [Generated System Includes]

// [[[ begin generated region: do not modify [Generated User Includes]
#include "$(baseName).hrh"
#include "SearchResultsView.h"
#include "SearchResults.hrh"
#include "SearchResults.h"
// ]]] end generated region [Generated User Includes]


// [[[ begin generated region: do not modify [Generated Constants]
// ]]] end generated region [Generated Constants]

const int MAX_SEARCH_STRING_LENGTH = 100;

/**
 * First phase of Symbian two-phase construction. Should not contain any
 * code that could leave.
 */
CSearchResultsView::CSearchResultsView()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	iSearchResults = NULL;
	iWebClient = NULL;
	// ]]] end generated region [Generated Contents]
	iSearchModel = NULL;
	iSelectedItem = -1;
	}
/** 
 * The view's destructor removes the container from the control
 * stack and destroys it.
 */
CSearchResultsView::~CSearchResultsView()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	delete iSearchResults;
	iSearchResults = NULL;
	delete iWebClient;
	iWebClient = NULL;
	TRAPD( err_WaitDialog, RemoveWaitDialogL() );
	// ]]] end generated region [Generated Contents]
	
	delete iSearchModel;
	delete iImageDisplayer;
	iImageBuffer.Close();
	}

/**
 * Symbian two-phase constructor.
 * This creates an instance then calls the second-phase constructor
 * without leaving the instance on the cleanup stack.
 * @return new instance of CSearchResultsView
 */
CSearchResultsView* CSearchResultsView::NewL()
	{
	CSearchResultsView* self = CSearchResultsView::NewLC();
	CleanupStack::Pop( self );
	return self;
	}

/**
 * Symbian two-phase constructor.
 * This creates an instance, pushes it on the cleanup stack,
 * then calls the second-phase constructor.
 * @return new instance of CSearchResultsView
 */
CSearchResultsView* CSearchResultsView::NewLC()
	{
	CSearchResultsView* self = new (ELeave) CSearchResultsView();
	CleanupStack::PushL( self );
	self->ConstructL();
	return self;
	}


/**
 * Second-phase constructor for view.  
 * Initialize contents from resource.
 */ 
void CSearchResultsView::ConstructL()
	{
	// [[[ begin generated region: do not modify [Generated Code]
	BaseConstructL( R_SEARCH_RESULTS_SEARCH_RESULTS_VIEW );
				
	iWebClient = CWebClientEngine::NewL( *this );
	// ]]] end generated region [Generated Code]
	
	iSearchModel = new (ELeave) CYahooSearchModel;
	iSearchModel->ConstructL();
	
	iImageDisplayer = new (ELeave) CImageDisplayer;
	iImageDisplayer->ConstructL();
	}
	
/**
 * @return The UID for this view
 */
TUid CSearchResultsView::Id() const
	{
	return TUid::Uid( ESearchResultsViewId );
	}

/**
 * Handle a command for this view (override)
 * @param aCommand command id to be handled
 */
void CSearchResultsView::HandleCommandL( TInt aCommand )
	{   
	// [[[ begin generated region: do not modify [Generated Code]
	TBool commandHandled = EFalse;
	switch ( aCommand )
		{	// code to dispatch to the AknView's menu and CBA commands is generated here
		case ESearchResultsViewNew_searchMenuItemCommand:
			commandHandled = HandleNew_searchMenuItemSelectedL( aCommand );
			break;
		case ESearchResultsViewInfoMenuItemCommand:
			commandHandled = HandleInfoMenuItemSelectedL( aCommand );
			break;
		default:
			break;
		}
	
		
	if ( !commandHandled ) 
		{
	
		if ( aCommand == EAknSoftkeyExit )
			{
			AppUi()->HandleCommandL( EEikCmdExit );
			}
	
		}
	// ]]] end generated region [Generated Code]
	
	}

/**
 *
 * @param aPrevViewId 
 * @param aCustomMessageId 
 * @param aCustomMessage
 */
void CSearchResultsView::DoActivateL(
		const TVwsViewId& /*aPrevViewId*/,
		TUid /*aCustomMessageId*/,
		const TDesC8& /*aCustomMessage*/ )
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	SetupStatusPaneL();
	
				
	if ( iSearchResults == NULL )
		{
		iSearchResults = CreateContainerL();
		iSearchResults->SetMopParent( this );
		AppUi()->AddToStackL( *this, iSearchResults );
		} 
	// ]]] end generated region [Generated Contents]
		PopulateSearchResultsL();
		if (iSelectedItem >= 0)
			iSearchResults->ListBox()->SetCurrentItemIndex(iSelectedItem);
	}

/**
 */
void CSearchResultsView::DoDeactivate()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	CleanupStatusPane();
	
	if ( iSearchResults != NULL )
		{
		AppUi()->RemoveFromViewStack( *this, iSearchResults );
		delete iSearchResults;
		iSearchResults = NULL;
		}
	// ]]] end generated region [Generated Contents]
	
	}

// [[[ begin generated function: do not modify
void CSearchResultsView::SetupStatusPaneL()
	{
	// reset the context pane
	TUid contextPaneUid = TUid::Uid( EEikStatusPaneUidContext );
	CEikStatusPaneBase::TPaneCapabilities subPaneContext = 
		StatusPane()->PaneCapabilities( contextPaneUid );
	if ( subPaneContext.IsPresent() && subPaneContext.IsAppOwned() )
		{
		CAknContextPane* context = static_cast< CAknContextPane* > ( 
			StatusPane()->ControlL( contextPaneUid ) );
		context->SetPictureToDefaultL();
		}
	
	// setup the title pane
	TUid titlePaneUid = TUid::Uid( EEikStatusPaneUidTitle );
	CEikStatusPaneBase::TPaneCapabilities subPaneTitle = 
		StatusPane()->PaneCapabilities( titlePaneUid );
	if ( subPaneTitle.IsPresent() && subPaneTitle.IsAppOwned() )
		{
		CAknTitlePane* title = static_cast< CAknTitlePane* >( 
			StatusPane()->ControlL( titlePaneUid ) );
		TResourceReader reader;
		iEikonEnv->CreateResourceReaderLC( reader, R_SEARCH_RESULTS_TITLE_RESOURCE );
		title->SetFromResourceL( reader );
		CleanupStack::PopAndDestroy(); // reader internal state
		}
				
	}

// ]]] end generated function

// [[[ begin generated function: do not modify
void CSearchResultsView::CleanupStatusPane()
	{
	}

// ]]] end generated function

/** 
 * Handle status pane size change for this view (override)
 */
void CSearchResultsView::HandleStatusPaneSizeChange()
	{
	CAknView::HandleStatusPaneSizeChange();
	
	// this may fail, but we're not able to propagate exceptions here
	TInt result;
	TRAP( result, SetupStatusPaneL() ); 
	// [[[ begin generated region: do not modify [Generated Code]
	// ]]] end generated region [Generated Code]
	
	}
	
/**
 * ClientOpenFailed( CWebClientEngine& anEngine )
 * An error occurred opening the HTTP session. The calling code
 * will leave after this method returns.
 * @param anError: the error code
 * Most common error; no access point configured, and session creation
 * leaves with KErrNotFound.
 */			
void CSearchResultsView::ClientOpenSessionFailedL( CWebClientEngine& anEngine, TInt anError )
	{
	// [[[ begin generated region: do not modify [Generated Code]
	// ]]] end generated region [Generated Code]
	}
/**
 * ClientConnecting()
 * Called to notify that a connection was initiated
 */			
void CSearchResultsView::ClientConnectingL( CWebClientEngine& anEngine )
	{
	// [[[ begin generated region: do not modify [Generated Code]
	// ]]] end generated region [Generated Code]
	}
/**
 * ClientHeaderReceived()
 * Called when HTTP header is received.
 * @param aHeaderData: Header field name and value
 */			
void CSearchResultsView::ClientHeaderReceivedL( CWebClientEngine& anEngine, const TDesC& aHeaderData )
	{
	// [[[ begin generated region: do not modify [Generated Code]
	// ]]] end generated region [Generated Code]
	}
/**
 * ClientBodyReceived()
 * Called when a part of the HTTP body is received.
 * @param aBodyData:  Part of the body data received. (e.g. part of
 *					the received HTML page)
 */			
void CSearchResultsView::ClientBodyReceivedL( CWebClientEngine& anEngine, const TDesC8& aBodyData )
	{
	// [[[ begin generated region: do not modify [Generated Code]
	HandleWebClientBodyReceivedL( anEngine, aBodyData );
	// ]]] end generated region [Generated Code]
	}
/**
 * ClientConnectionCanceled()
 * Called to notify that a connection attempt has been canceled
 */			
void CSearchResultsView::ClientConnectionCanceledL( CWebClientEngine& anEngine )
	{
	// [[[ begin generated region: do not modify [Generated Code]
	// ]]] end generated region [Generated Code]
	}
/**
 * ClientResponseComplete
 * Called to notify that a transaction's response is complete.
 * See TTransactionEvent::EResponseComplete
 */			
void CSearchResultsView::ClientResponseCompleteL( CWebClientEngine& anEngine )
	{
	// [[[ begin generated region: do not modify [Generated Code]
	// ]]] end generated region [Generated Code]
	}
/**
 * ClientTransactionSucceeded()
 * Called to notify that a transaction completed successfully
 * See TTransactionEvent::ESucceeded
 */			
void CSearchResultsView::ClientTransactionSucceededL( CWebClientEngine& anEngine )
	{
	// [[[ begin generated region: do not modify [Generated Code]
	
	RemoveWaitDialogL();
	HandleWebClientTransactionSucceededL( anEngine );
	// ]]] end generated region [Generated Code]
	
	}
/** 
 * ClientTransactionFailed()
 * Catch-all for failure. 
 * See TTransactionEvent::EFailed
 */			
void CSearchResultsView::ClientTransactionFailedL( CWebClientEngine& anEngine )
	{
	// [[[ begin generated region: do not modify [Generated Code]
	
	RemoveWaitDialogL();
	HandleWebClientTransactionFailedL( anEngine );
	// ]]] end generated region [Generated Code]
	
	}
/**
 * ClientUnknownEvent
 * Called to notify that an unknown HTTP event has
 * been received.
 * @param aStatus: the iStatus field of the event
 * See THTTPEvent::iStatus
 */			
void CSearchResultsView::ClientUnknownEventL( CWebClientEngine& anEngine, TInt aStatus )
	{
	// [[[ begin generated region: do not modify [Generated Code]
	// ]]] end generated region [Generated Code]
	}
/**
 * ClientRunError()
 * Called when a error occurs in the handling of a transaction event.
 * @param anError: the error code
 */			
void CSearchResultsView::ClientRunErrorL( CWebClientEngine& anEngine, TInt anError )
	{
	// [[[ begin generated region: do not modify [Generated Code]
	
	RemoveWaitDialogL();
	// ]]] end generated region [Generated Code]
	
	}
 /**
 * ClientGetCredentialsL()
 * Called when authentication has been requested by the server.
 * Return EFalse for no authentication or e.g. the user cancels
 * an input dialog. Otherwise return the user name and password 
 * as out parameters along with an ETrue result.
 * @param aUri: the current URI
 * @param aRealm: the realm associated with the request
 * @param aUserName: the returned user name
 * @param aPassword: the returned password
 */		
TBool CSearchResultsView::ClientGetCredentialsL( CWebClientEngine& anEngine, const TUriC8& aUri, const TDesC8& aRealm, TDes& aUsername, TDes& aPassword )
	{
	// [[[ begin generated region: do not modify [Generated Code]
	return EFalse;
	// ]]] end generated region [Generated Code]
	
	}
// [[[ begin generated function: do not modify
			
/**
 * Initiate the HTTP GET transaction. Progress and data will
 * be provided through the MWebClientObserver callback methods.
 * If configured in the UI Designer, the wait dialog is executed
 * before returning.
 * @param aUrl: if non-null this is used as the URL to fetch. Otherwise
 * the URL configured in the UI Designer is used.
 */	
void CSearchResultsView::IssueHTTPGetL( const TDesC8* aUrl )
	{

	ExecuteWaitDialogLD();

	if ( aUrl != NULL )
		{
		iWebClient->IssueHTTPGetL( *aUrl );
		}
	else 
		{
		_LIT8( url, "" );
		iWebClient->IssueHTTPGetL( url );
		}

	}
// ]]] end generated function

// [[[ begin generated function: do not modify
/**
 * Execute the wait dialog for waitDialog. This routine returns
 * while the dialog is showing. It will be closed and destroyed when
 * RemoveWaitDialogL() or the user selects the Cancel soft key.
 * @param aOverrideText optional override text. When null the text configured
 * in the UI Designer is used.
 */
void CSearchResultsView::ExecuteWaitDialogLD( const TDesC* aOverrideText )
	{
	iWaitDialog = new ( ELeave ) CAknWaitDialog( 
			reinterpret_cast< CEikDialog** >( &iWaitDialog ), EFalse );
	if ( aOverrideText != NULL )
		{
		iWaitDialog->SetTextL( *aOverrideText );
		}
	iWaitDialog->ExecuteLD( R_SEARCH_RESULTS_WAIT_DIALOG );
	iWaitDialogCallback = new ( ELeave ) CProgressDialogCallback( 
		this, iWaitDialog, &CSearchResultsView::HandleWaitDialog1CanceledL );
	iWaitDialog->SetCallback( iWaitDialogCallback );
	}
// ]]] end generated function

// [[[ begin generated function: do not modify
/**
 * Close and dispose of the wait dialog for waitDialog
 */
void CSearchResultsView::RemoveWaitDialogL()
	{
	if ( iWaitDialog != NULL )
		{
		iWaitDialog->SetCallback( NULL );
		iWaitDialog->ProcessFinishedL();    // deletes the dialog
		iWaitDialog = NULL;
		}
	delete iWaitDialogCallback;
	iWaitDialogCallback = NULL;
	
	}
// ]]] end generated function

/** 
 * Handle the canceled event.
 */
void CSearchResultsView::HandleWaitDialog1CanceledL( CAknProgressDialog* /* aDialog */ )
	{
	iWebClient->CancelTransactionL(); 
	}
				
/** 
 * Handle the bodyReceived event.
 */
void CSearchResultsView::HandleWebClientBodyReceivedL( CWebClientEngine& /*anEngine*/, const TDesC8& aBodyData )
	{
		TInt err;
		if (requestType == SEARCH_REQUEST)
		{	
			iSearchModel->AppendL(aBodyData);
		}
		else if (requestType == IMAGE_REQUEST)
		{
			AppendImageDataL(aBodyData);
		}
	}
	
void CSearchResultsView::AppendImageDataL(const TDesC8& data)
{
	int newLength = iImageBuffer.Length() + data.Length();
	if (iImageBuffer.MaxLength() < newLength)
	{
		iImageBuffer.ReAllocL(newLength);
	}
	iImageBuffer.Append(data);
}
				
/** 
 * Handle the transactionFailed event.
 */
void CSearchResultsView::HandleWebClientTransactionFailedL( CWebClientEngine& /*anEngine*/ )
	{
	// [[[ begin generated region: do not modify [Generated Code]
	// ]]] end generated region [Generated Code]
	}
				
/** 
 * Handle the transactionSucceeded event.
 */
void CSearchResultsView::HandleWebClientTransactionSucceededL( CWebClientEngine& /*anEngine*/ )
{
	if (requestType == SEARCH_REQUEST)
	{
		iSelectedItem = -1;
		iSearchModel->ParseL();
		PopulateSearchResultsL();
	}
	else if (requestType == IMAGE_REQUEST)
	{
		TYahooImageInfo imageInfo;
		iSearchModel->GetImageInfo(iSelectedItem, imageInfo);
		iImageDisplayer->DisplayImageL(imageInfo.title, iImageBuffer);
		iImageBuffer.SetLength(0);
		iImageBuffer.ReAlloc(0);
	}
}

void CSearchResultsView::PopulateSearchResultsL()
{
	iSearchResults->ResetListL();
	iSearchResults->ListBox()->SetCurrentItemIndex(0);
	for (int i = 0; i < iSearchModel->GetImageCount(); i++)
	{
		TYahooImageInfo info;
		iSearchModel->GetImageInfo(i, info);
		TBuf<256> buf;
		iSearchResults->CreateListBoxItemL(buf, info.title, info.url);
		iSearchResults->AddListBoxItemL(iSearchResults->ListBox(), buf);
	}
	iSearchResults->ListBox()->DrawDeferred();
}
		
	
/** 
 * Handle the selected event.
 * @param aCommand the command id invoked
 * @return ETrue if the command was handled, EFalse if not
 */
TBool CSearchResultsView::HandleNew_searchMenuItemSelectedL( TInt aCommand )
	{
	TBuf<MAX_SEARCH_STRING_LENGTH> searchString;
	if (RunSearchPromptL(searchString, ETrue, NULL) == EAknSoftkeyOk) 
	{
		TBuf8<MAX_SEARCH_STRING_LENGTH> searchString8;
		searchString8.Copy(searchString);
		
		// replace spaces with '+' in search string
		int foundPos;
		_LIT8(plusStr, "+");
		while ((foundPos = searchString8.Locate(' ')) != KErrNotFound)
		{
			searchString8.Replace(foundPos, plusStr().Length(), plusStr);
		}
		
		_LIT8(SEARCH_URL, "http://api.search.yahoo.com/ImageSearchService/V1/imageSearch?appid=carbide.yahoo&query=%S&results=10");
		RBuf8 urlBuf;
		urlBuf.CreateL(512);
		urlBuf.CleanupClosePushL();
		urlBuf.Format(SEARCH_URL, &searchString8);
		requestType = SEARCH_REQUEST;
		IssueHTTPGetL(&urlBuf);
		CleanupStack::PopAndDestroy();
	}
	return ETrue;
	}
				
// [[[ begin generated function: do not modify
/**
 * Show the popup dialog for searchPrompt
 * @param aData in-out TDes data
 * @param aUseDefaults TBool use designer default editor data if ETrue
 * @param aOverridePrompt optional override prompt text
 * @return EAknSoftkeyOk (left soft key id) or 0
 */
TInt CSearchResultsView::RunSearchPromptL( 
		TDes& aData, 
		TBool aUseDefaults, 
		const TDesC* aOverridePrompt )
	{
	if ( aUseDefaults )
		{
		HBufC* text = StringLoader::LoadLC( R_SEARCH_RESULTS_EDIT1 );
		aData.Copy( *text );
		CleanupStack::PopAndDestroy( text );
		}
				
	CAknTextQueryDialog* queryDialog = CAknTextQueryDialog::NewL( aData );	
	
	if ( aOverridePrompt != NULL )
		{
		CleanupStack::PushL( queryDialog );
		queryDialog->SetPromptL( *aOverridePrompt );
		CleanupStack::Pop(); // queryDialog
		}
	
	return queryDialog->ExecuteLD( R_SEARCH_RESULTS_SEARCH_PROMPT );
	}
// ]]] end generated function

void CSearchResultsView::ShowSelectedImageL()
{
	int currItem = iSearchResults->ListBox()->CurrentItemIndex();
	if (currItem >= 0)
	{
		iSelectedItem = currItem;
		TYahooImageInfo imageInfo;
		iSearchModel->GetImageInfo(currItem, imageInfo);
		
		if (imageInfo.thumbnailUrl.Length() > 0)
		{
			RBuf8 urlBuf;
			urlBuf.CreateL(imageInfo.thumbnailUrl.Length());
			urlBuf.Copy(imageInfo.thumbnailUrl);
			urlBuf.CleanupClosePushL();
			
			requestType = IMAGE_REQUEST;
			IssueHTTPGetL(&urlBuf);
			
			CleanupStack::PopAndDestroy();
		}
	}	
}
/** 
 * Handle the selected event.
 * @param aCommand the command id invoked
 * @return ETrue if the command was handled, EFalse if not
 */
TBool CSearchResultsView::HandleInfoMenuItemSelectedL( TInt aCommand )
	{
	RunAttributionL();
	return ETrue;
	}
				
// [[[ begin generated function: do not modify
/**
 * Show the popup note for attribution
 * @param aOverrideText optional override text
 */
void CSearchResultsView::RunAttributionL( const TDesC* aOverrideText )
	{
	CAknConfirmationNote* note = new ( ELeave ) CAknConfirmationNote();
	if ( aOverrideText == NULL )
		{
		HBufC* noteText = StringLoader::LoadLC( R_SEARCH_RESULTS_ATTRIBUTION );
		note->ExecuteLD( *noteText );
		CleanupStack::PopAndDestroy( noteText );
		}
	else
		{
		note->ExecuteLD( *aOverrideText );
		}
	}
// ]]] end generated function


/**
 *	Creates the top-level container for the view.  You may modify this method's
 *	contents and the CSearchResults::NewL() signature as needed to initialize the
 *	container, but the signature for this method is fixed.
 *	@return new initialized instance of CSearchResults
 */
CSearchResults* CSearchResultsView::CreateContainerL()
	{
	return CSearchResults::NewL( ClientRect(), NULL, this );
	}
			
