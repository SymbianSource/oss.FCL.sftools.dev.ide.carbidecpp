/*
========================================================================
 Name		: ImageContainerView.cpp

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
#include <eikimage.h>
#include <eikenv.h>
#include <akncontext.h>
#include <akntitle.h>
#include <stringloader.h>
#include <eikbtgpc.h>
#include <$(baseName).rsg>
// ]]] end generated region [Generated System Includes]

// [[[ begin generated region: do not modify [Generated User Includes]
#include "$(baseName).hrh"
#include "ImageContainerView.h"
#include "SearchResults.hrh"
#include "ImageContainer.h"
// ]]] end generated region [Generated User Includes]
#include <akniconutils.h>
#include "YahooImageSearchAppUi.h"

// [[[ begin generated region: do not modify [Generated Constants]
// ]]] end generated region [Generated Constants]

/**
 * First phase of Symbian two-phase construction. Should not contain any
 * code that could leave.
 */
CImageContainerView::CImageContainerView()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	iImageContainer = NULL;
	// ]]] end generated region [Generated Contents]
	iBitmap = NULL;
	
	}
/** 
 * The view's destructor removes the container from the control
 * stack and destroys it.
 */
CImageContainerView::~CImageContainerView()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	delete iImageContainer;
	iImageContainer = NULL;
	// ]]] end generated region [Generated Contents]
	delete iBitmap;
	iImageTitle.Close();
	}

/**
 * Symbian two-phase constructor.
 * This creates an instance then calls the second-phase constructor
 * without leaving the instance on the cleanup stack.
 * @return new instance of CImageContainerView
 */
CImageContainerView* CImageContainerView::NewL()
	{
	CImageContainerView* self = CImageContainerView::NewLC();
	CleanupStack::Pop( self );
	return self;
	}

/**
 * Symbian two-phase constructor.
 * This creates an instance, pushes it on the cleanup stack,
 * then calls the second-phase constructor.
 * @return new instance of CImageContainerView
 */
CImageContainerView* CImageContainerView::NewLC()
	{
	CImageContainerView* self = new (ELeave) CImageContainerView();
	CleanupStack::PushL( self );
	self->ConstructL();
	return self;
	}


/**
 * Second-phase constructor for view.  
 * Initialize contents from resource.
 */ 
void CImageContainerView::ConstructL()
	{
	// [[[ begin generated region: do not modify [Generated Code]
	BaseConstructL( R_IMAGE_CONTAINER_IMAGE_CONTAINER_VIEW );
				
	// ]]] end generated region [Generated Code]
	
	// add your own initialization code here
	}
	
/**
 * @return The UID for this view
 */
TUid CImageContainerView::Id() const
	{
	return TUid::Uid( EImageContainerViewId );
	}

/**
 * Handle a command for this view (override)
 * @param aCommand command id to be handled
 */
void CImageContainerView::HandleCommandL( TInt aCommand )
	{   
	// [[[ begin generated region: do not modify [Generated Code]
	TBool commandHandled = EFalse;
	switch ( aCommand )
		{	// code to dispatch to the AknView's menu and CBA commands is generated here
	
		case EAknSoftkeyBack:
			commandHandled = HandleControlPaneRightSoftKeyPressed( aCommand );
			break;
		default:
			break;
		}
	
		
	if ( !commandHandled ) 
		{
	
		if ( aCommand == EAknSoftkeyBack )
			{
			AppUi()->HandleCommandL( EEikCmdExit );
			}
	
		}
	// ]]] end generated region [Generated Code]
	
	}
	
void CImageContainerView::SetImageL(const TDesC16& title, CFbsBitmap *aBitmap)
{
	iImageTitle.SetLength(0);
	iImageTitle.ReAllocL(title.Length());
	iImageTitle.Copy(title);
	iBitmap = aBitmap;
}

/**
 *
 * @param aPrevViewId 
 * @param aCustomMessageId 
 * @param aCustomMessage
 */
void CImageContainerView::DoActivateL(
		const TVwsViewId& /*aPrevViewId*/,
		TUid /*aCustomMessageId*/,
		const TDesC8& /*aCustomMessage*/ )
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	SetupStatusPaneL();
	
				
	if ( iImageContainer == NULL )
		{
		iImageContainer = CreateContainerL();
		iImageContainer->SetMopParent( this );
		AppUi()->AddToStackL( *this, iImageContainer );
		} 
	// ]]] end generated region [Generated Contents]
	
	if (iBitmap)
		{
		TSize bmSize = iBitmap->SizeInPixels();
		CEikImage *image = iImageContainer->Image();
		TSize controlSize = image->Size();
		image->SetPicture(iBitmap);
		image->SetPictureOwnedExternally(EFalse);
		iBitmap = NULL;
		
		CEikStatusPane *statusPane = StatusPane();
		TUid titlePaneUid;
		titlePaneUid.iUid = EEikStatusPaneUidTitle;
		CEikStatusPaneBase::TPaneCapabilities subPane =
			statusPane->PaneCapabilities(titlePaneUid);
		if (subPane.IsPresent() && subPane.IsAppOwned())
			{
			CAknTitlePane *titlePane = static_cast<CAknTitlePane*>(statusPane->ControlL(titlePaneUid));
			titlePane->SetTextL(iImageTitle);
			}
		}
	}

/**
 */
void CImageContainerView::DoDeactivate()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	CleanupStatusPane();
	
	if ( iImageContainer != NULL )
		{
		AppUi()->RemoveFromViewStack( *this, iImageContainer );
		delete iImageContainer;
		iImageContainer = NULL;
		}
	// ]]] end generated region [Generated Contents]
	
	}

// [[[ begin generated function: do not modify
void CImageContainerView::SetupStatusPaneL()
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
		iEikonEnv->CreateResourceReaderLC( reader, R_IMAGE_CONTAINER_TITLE_RESOURCE );
		title->SetFromResourceL( reader );
		CleanupStack::PopAndDestroy(); // reader internal state
		}
				
	}

// ]]] end generated function

// [[[ begin generated function: do not modify
void CImageContainerView::CleanupStatusPane()
	{
	}

// ]]] end generated function

/** 
 * Handle status pane size change for this view (override)
 */
void CImageContainerView::HandleStatusPaneSizeChange()
	{
	CAknView::HandleStatusPaneSizeChange();
	
	// this may fail, but we're not able to propagate exceptions here
	TInt result;
	TRAP( result, SetupStatusPaneL() ); 
	// [[[ begin generated region: do not modify [Generated Code]
	// ]]] end generated region [Generated Code]
	
	}
	
/** 
 * Handle the rightSoftKeyPressed event.
 * @return ETrue if the command was handled, EFalse if not
 */
TBool CImageContainerView::HandleControlPaneRightSoftKeyPressed( TInt aCommand )
	{
	CYahooImageSearchAppUi *appUi = static_cast<CYahooImageSearchAppUi*>(CCoeEnv::Static()->AppUi());
	appUi->ActivateLocalViewL(TUid::Uid(ESearchResultsViewId));
	return ETrue;
	}
				

/**
 *	Creates the top-level container for the view.  You may modify this method's
 *	contents and the CImageContainer::NewL() signature as needed to initialize the
 *	container, but the signature for this method is fixed.
 *	@return new initialized instance of CImageContainer
 */
CImageContainer* CImageContainerView::CreateContainerL()
	{
	return CImageContainer::NewL( ClientRect(), NULL, this );
	}
			
