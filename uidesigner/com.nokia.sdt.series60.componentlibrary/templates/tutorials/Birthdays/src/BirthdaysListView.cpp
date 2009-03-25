/*
========================================================================
 Name		: BirthdaysListView.cpp

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
#include <aknsfld.h>
#include <aknutils.h>
#include <akncontext.h>
#include <akntitle.h>
#include <eikbtgpc.h>
#include <aknquerydialog.h>
#include <$(baseName).rsg>
// ]]] end generated region [Generated System Includes]

// [[[ begin generated region: do not modify [Generated User Includes]
#include "$(baseName).hrh"
#include "BirthdaysListView.h"
#include "BirthdaysList.hrh"
#include "BirthdayForm.hrh"
#include "BirthdaysList.h"
// ]]] end generated region [Generated User Includes]

#include "BirthdaysDocument.h"
#include "BirthdayForm.h"

// [[[ begin generated region: do not modify [Generated Constants]
// ]]] end generated region [Generated Constants]

/**
 * First phase of Symbian two-phase construction. Should not contain any
 * code that could leave.
 */
CBirthdaysListView::CBirthdaysListView()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	iBirthdaysList = NULL;
	// ]]] end generated region [Generated Contents]
	
	}
/** 
 * The view's destructor removes the container from the control
 * stack and destroys it.
 */
CBirthdaysListView::~CBirthdaysListView()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	delete iBirthdaysList;
	iBirthdaysList = NULL;
	// ]]] end generated region [Generated Contents]
	
	}

/**
 * Symbian two-phase constructor.
 * This creates an instance then calls the second-phase constructor
 * without leaving the instance on the cleanup stack.
 * @return new instance of CBirthdaysListView
 */
CBirthdaysListView* CBirthdaysListView::NewL()
	{
	CBirthdaysListView* self = CBirthdaysListView::NewLC();
	CleanupStack::Pop( self );
	return self;
	}

/**
 * Symbian two-phase constructor.
 * This creates an instance, pushes it on the cleanup stack,
 * then calls the second-phase constructor.
 * @return new instance of CBirthdaysListView
 */
CBirthdaysListView* CBirthdaysListView::NewLC()
	{
	CBirthdaysListView* self = new (ELeave) CBirthdaysListView();
	CleanupStack::PushL( self );
	self->ConstructL();
	return self;
	}


/**
 * Second-phase constructor for view.  
 * Initialize contents from resource.
 */ 
void CBirthdaysListView::ConstructL()
	{
	// [[[ begin generated region: do not modify [Generated Code]
	BaseConstructL( R_BIRTHDAYS_LIST_BIRTHDAYS_LIST_VIEW_2 );
				
	// ]]] end generated region [Generated Code]
	}
	
/**
 * @return The UID for this view
 */
TUid CBirthdaysListView::Id() const
	{
	return TUid::Uid( EBirthdaysListViewId );
	}

/**
 * Handle a command for this view (override)
 * @param aCommand command id to be handled
 */
void CBirthdaysListView::HandleCommandL( TInt aCommand )
	{   
	// [[[ begin generated region: do not modify [Generated Code]
	TBool commandHandled = EFalse;
	switch ( aCommand )
		{	// code to dispatch to the AknView's menu and CBA commands is generated here
		case EBirthdaysListViewAddMenuItemCommand:
			commandHandled = HandleAddMenuItemSelectedL( aCommand );
			break;
		case EBirthdaysListViewEditMenuItemCommand:
			commandHandled = HandleEditMenuItemSelectedL( aCommand );
			break;
		case EBirthdaysListViewDeleteMenuItemCommand:
			commandHandled = HandleDeleteMenuItemSelectedL( aCommand );
			break;
		default:
			break;
		}
	
	// markable list command handling
	if ( !commandHandled ) 
		{
		commandHandled = iBirthdaysList->HandleMarkableListCommandL( aCommand );
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
void CBirthdaysListView::DoActivateL(
		const TVwsViewId& /*aPrevViewId*/,
		TUid /*aCustomMessageId*/,
		const TDesC8& /*aCustomMessage*/ )
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	SetupStatusPaneL();
	
				
	if ( iBirthdaysList == NULL )
		{
		iBirthdaysList = CreateContainerL();
		iBirthdaysList->SetMopParent( this );
		AppUi()->AddToStackL( *this, iBirthdaysList );
		} 
	// ]]] end generated region [Generated Contents]

	iBirthdaysList->SetBirthdaysL( static_cast< CBirthdaysDocument* > 
		( AppUi()->Document() ) );
	
	}

/**
 */
void CBirthdaysListView::DoDeactivate()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	HandleBirthdaysListViewDeactivated();
	CleanupStatusPane();
	
	if ( iBirthdaysList != NULL )
		{
		AppUi()->RemoveFromViewStack( *this, iBirthdaysList );
		delete iBirthdaysList;
		iBirthdaysList = NULL;
		}
	// ]]] end generated region [Generated Contents]
	
	}

// [[[ begin generated function: do not modify
void CBirthdaysListView::SetupStatusPaneL()
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
		iEikonEnv->CreateResourceReaderLC( reader, R_BIRTHDAYS_LIST_TITLE_RESOURCE );
		title->SetFromResourceL( reader );
		CleanupStack::PopAndDestroy(); // reader internal state
		}
				
	}

// ]]] end generated function

// [[[ begin generated function: do not modify
void CBirthdaysListView::CleanupStatusPane()
	{
	}

// ]]] end generated function

/** 
 * Handle status pane size change for this view (override)
 */
void CBirthdaysListView::HandleStatusPaneSizeChange()
	{
	CAknView::HandleStatusPaneSizeChange();
	
	// this may fail, but we're not able to propagate exceptions here
	TInt result;
	TRAP( result, SetupStatusPaneL() ); 
	// [[[ begin generated region: do not modify [Generated Code]
	// ]]] end generated region [Generated Code]
	
	}
	
/** 
 * Handle the selected event.
 * @param aCommand the command id invoked
 * @return ETrue if the command was handled, EFalse if not
 */
TBool CBirthdaysListView::HandleAddMenuItemSelectedL( TInt aCommand )
	{
	iBirthdaysList->RunAddBirthdayL();
	return ETrue;
	}
				
/** 
 * Handle the selected event.
 * @param aCommand the command id invoked
 * @return ETrue if the command was handled, EFalse if not
 */
TBool CBirthdaysListView::HandleDeleteMenuItemSelectedL( TInt aCommand )
	{
	if ( iBirthdaysList->ListBox()->SelectionIndexes()->Count() > 0
		&& RunRemoveConfQueryL( NULL ) )
		{
		iBirthdaysList->DeleteBirthdaysL();
		}
	return ETrue;
	}
				
// [[[ begin generated function: do not modify
/**
 * Show the popup dialog for removeConfQuery
 * @param aOverrideText optional override text
 * @return EAknSoftkeyYes (left soft key id) or 0
 */
TInt CBirthdaysListView::RunRemoveConfQueryL( const TDesC* aOverrideText )
	{
				
	CAknQueryDialog* queryDialog = CAknQueryDialog::NewL();	
	
	if ( aOverrideText != NULL )
		{
		queryDialog->SetPromptL( *aOverrideText );
		}
	return queryDialog->ExecuteLD( R_BIRTHDAYS_LIST_REMOVE_CONF_QUERY );
	}
// ]]] end generated function

/** 
 * Edit the currently selected birthday.
 * @param aCommand the command id invoked
 * @return ETrue if the command was handled, EFalse if not
 */
TBool CBirthdaysListView::HandleEditMenuItemSelectedL( TInt aCommand )
	{
	iBirthdaysList->RunEditBirthdayL();
	return ETrue;
	}
				
/** 
 * Handle the deactivated event.
 */
void CBirthdaysListView::HandleBirthdaysListViewDeactivated()
	{
	// save away the list of birthdays
	TInt err;
	TRAP( err, iBirthdaysList->Birthdays()->SaveL() );
	}
				

/**
 *	Creates the top-level container for the view.  You may modify this method's
 *	contents and the CBirthdaysList::NewL() signature as needed to initialize the
 *	container, but the signature for this method is fixed.
 *	@return new initialized instance of CBirthdaysList
 */
CBirthdaysList* CBirthdaysListView::CreateContainerL()
	{
	return CBirthdaysList::NewL( ClientRect(), NULL, this );
	}
			
