/*
========================================================================
 Name		: SearchResultsView.h

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
#ifndef SEARCHRESULTSVIEW_H
#define SEARCHRESULTSVIEW_H

// [[[ begin generated region: do not modify [Generated Includes]
#include <aknview.h>
#include <aknwaitdialog.h>
// ]]] end generated region [Generated Includes]
#include "YahooSearchModel.h"
#include "ImageDisplayer.h"

// [[[ begin [Event Handler Includes]
#include "WebClientEngine.h"
// ]]] end [Event Handler Includes]

// [[[ begin generated region: do not modify [Generated Forward Declarations]
class CSearchResults;
// ]]] end generated region [Generated Forward Declarations]

// [[[ begin generated region: do not modify [Generated Constants]
// ]]] end generated region [Generated Constants]

/**
 * Avkon view class for SearchResultsView. It is register with the view server
 * by the AppUi. It owns the container control.
 * @class	CSearchResultsView SearchResultsView.h
 */
class CSearchResultsView : public CAknView
	,MWebClientObserver	{
public:
	// constructors and destructor
	CSearchResultsView();
	static CSearchResultsView* NewL();
	static CSearchResultsView* NewLC();		
	void ConstructL();
	virtual ~CSearchResultsView();

public:
	// from base class CAknView
	TUid Id() const;
	void HandleCommandL( TInt aCommand );

protected:
	// from base class CAknView
	void DoActivateL(
		const TVwsViewId& aPrevViewId,
		TUid aCustomMessageId,
		const TDesC8& aCustomMessage);
	void DoDeactivate();
	void HandleStatusPaneSizeChange();
	
private:
	void SetupStatusPaneL();
	void CleanupStatusPane();
	
	void AppendImageDataL(const TDesC8& data);
	void PopulateSearchResultsL();
	
	// [[[ begin [Public Section]
public:
	// [[[ begin generated region: do not modify [Generated Methods]
	CSearchResults* CreateContainerL();
	void IssueHTTPGetL( const TDesC8* aUrl = NULL );
	void ExecuteWaitDialogLD( const TDesC* aOverrideText = NULL );
	void RemoveWaitDialogL();
	static TInt RunSearchPromptL( 
			TDes& aData, 
			TBool aUseDefaults = ETrue, 
			const TDesC* aOverridePrompt = NULL );
	static void RunAttributionL( const TDesC* aOverrideText = NULL );
	// ]]] end generated region [Generated Methods]
	
	void ShowSelectedImageL();	
	
	// ]]] end [Public Section]
				
	// [[[ begin [Protected Section]
protected: 
	// [[[ begin [Overridden Methods]
	
	// ]]] end [Overridden Methods]
	// Implementations of MWebClientObserver methods
	virtual void ClientOpenSessionFailedL( CWebClientEngine& anEngine , TInt anError );
	virtual void ClientConnectingL(  CWebClientEngine& anEngine );
	virtual void ClientHeaderReceivedL( CWebClientEngine& anEngine, const TDesC& aHeaderData );
	virtual void ClientBodyReceivedL( CWebClientEngine& anEngine, const TDesC8& aBodyData );
	virtual void ClientConnectionCanceledL( CWebClientEngine& anEngine );
	virtual void ClientResponseCompleteL( CWebClientEngine& anEngine );
	virtual void ClientTransactionSucceededL( CWebClientEngine& anEngine );
	virtual void ClientTransactionFailedL( CWebClientEngine& anEngine );
	virtual void ClientUnknownEventL( CWebClientEngine& anEngine, TInt aStatus );
	virtual void ClientRunErrorL( CWebClientEngine& anEngine, TInt anError);
	virtual TBool ClientGetCredentialsL( CWebClientEngine& anEngine, const TUriC8& aUri, const TDesC8& aRealm, TDes& aUsername, TDes& aPassword );
	
	// [[[ begin [User Handlers]
protected: 
	void HandleWaitDialog1CanceledL( CAknProgressDialog *aDialog );
	void HandleWebClientBodyReceivedL( CWebClientEngine& anEngine, const TDesC8& aBodyData );
	void HandleWebClientTransactionFailedL( CWebClientEngine& anEngine );
	void HandleWebClientTransactionSucceededL( CWebClientEngine& anEngine );
	TBool HandleNew_searchMenuItemSelectedL( TInt aCommand );
	TBool HandleInfoMenuItemSelectedL( TInt aCommand );
	// ]]] end [User Handlers]
	

	// ]]] end [Protected Section]

	// [[[ begin [Private Section]
private:
	// [[[ begin generated region: do not modify [Generated Instance Variables]
	CSearchResults* iSearchResults;
	CWebClientEngine* iWebClient;
	CAknWaitDialog* iWaitDialog;
	class CProgressDialogCallback;
	CProgressDialogCallback* iWaitDialogCallback;
	// ]]] end generated region [Generated Instance Variables]

	// [[[ begin generated region: do not modify [Generated Methods]
	// ]]] end generated region [Generated Methods]
	
	// ]]] end [Private Section]
	
	enum RequestType {
		SEARCH_REQUEST,
		IMAGE_REQUEST		
	};
	RequestType requestType;
	CYahooSearchModel *iSearchModel;
	RBuf8 iImageBuffer;
	CImageDisplayer *iImageDisplayer;
	int iSelectedItem;
	
	// [[[ begin [MProgressDialogCallback support]
private: 
	typedef void (CSearchResultsView::*ProgressDialogEventHandler)( CAknProgressDialog *aProgressDialog );
	
	/**
	 * This is a helper class for progress/wait dialog callbacks. It routes the dialog's
	 * cancel notification to the handler function for the cancel event.
	 */
	class CProgressDialogCallback : public CBase, public MProgressDialogCallback
		{ 
		public:
			CProgressDialogCallback( CSearchResultsView *aHandlerObj, CAknProgressDialog *aDialog, ProgressDialogEventHandler aHandler)
				: handlerObj(aHandlerObj), dialog(aDialog), handler(aHandler) {}
				
			void DialogDismissedL (TInt aButtonId) { (handlerObj->*handler)( dialog );}
		private:
			CSearchResultsView *handlerObj;
			CAknProgressDialog *dialog;
			ProgressDialogEventHandler handler;
		};
		
	// ]]] end [MProgressDialogCallback support]
	
	};

#endif // SEARCHRESULTSVIEW_H			
