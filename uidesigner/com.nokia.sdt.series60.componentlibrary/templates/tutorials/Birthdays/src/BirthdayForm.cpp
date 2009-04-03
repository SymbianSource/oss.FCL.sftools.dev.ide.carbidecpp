/*
========================================================================
 Name		: BirthdayForm.cpp

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
#include <avkon.hrh>
#include <avkon.rsg>
#include <eikmenup.h>
#include <aknappui.h>
#include <eikcmobs.h>
#include <barsread.h>
#include <stringloader.h>
#include <gdi.h>
#include <eikedwin.h>
#include <eikenv.h>
#include <eikmfne.h>
#include <$(baseName).rsg>
// ]]] end generated region [Generated System Includes]

// [[[ begin generated region: do not modify [Generated User Includes]
#include "BirthdayForm.h"
#include "$(baseName).hrh"
#include "BirthdaysList.hrh"
#include "BirthdayForm.hrh"
// ]]] end generated region [Generated User Includes]
#include "TBirthday.h"

// [[[ begin generated region: do not modify [Generated Constants]
// ]]] end generated region [Generated Constants]

/**
 * Construct the CBirthdayForm instance
 * @param aCommandObserver command observer
 */ 
CBirthdayForm::CBirthdayForm( MEikCommandObserver* aCommandObserver )
	{
	iCommandObserver = aCommandObserver;
	// [[[ begin generated region: do not modify [Generated Contents]
	iName = NULL;
	iDate = NULL;
	iTodo = NULL;
	// ]]] end generated region [Generated Contents]
	
	iBirthdayChanged = EFalse;
	iBirthdayEmpty = ETrue;
	}

/**
 *  Creates an instance and initializes it.
 *  Instance is not left on cleanup stack.
 * @param aCommandObserver command observer
 * @return initialized instance of CBirthdayForm
 */
CBirthdayForm* CBirthdayForm::NewL( MEikCommandObserver* aCommandObserver )
	{
	CBirthdayForm* self = CBirthdayForm::NewLC( aCommandObserver );
	CleanupStack::Pop( self );
	return self;
	}

/**
 *  Creates an instance and initializes it.
 *  Instance is left on cleanup stack.
 * @param aCommandObserver command observer
 * @return new instance of CBirthdayForm
 */
CBirthdayForm* CBirthdayForm::NewLC( MEikCommandObserver* aCommandObserver )
	{
	CBirthdayForm* self = new (ELeave) CBirthdayForm( aCommandObserver );
	CleanupStack::PushL( self );
	self->ConstructL();
	return self;
	}
			
/** 
 * Handle key event (override)
 * @param aKeyEvent key event
 * @param aType event code
 * @return EKeyWasConsumed if the event was handled, else EKeyWasNotConsumed
 */
TKeyResponse CBirthdayForm::OfferKeyEventL( const TKeyEvent& aKeyEvent, TEventCode aType )
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	// ]]] end generated region [Generated Contents]
	
	
	return CAknForm::OfferKeyEventL( aKeyEvent, aType );
	}
				
/** 
 * Destroy any instance variables
 */
CBirthdayForm::~CBirthdayForm()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	// ]]] end generated region [Generated Contents]
	
	}

/**
 * Called to handle "Save" menu item. Displays save query.
 * @return TBool ETrue if the form data is to be saved, EFalse otherwise
 */
TBool CBirthdayForm::QuerySaveChangesL()
	{
	TBool isAnsYes( CAknForm::QuerySaveChangesL() );
	
	if ( isAnsYes )
		{
		SaveFormDataL();
		}
	else
		{
		DoNotSaveFormDataL();
		}

	return isAnsYes;
	}

/**
 * Called from QuerySaveChangesL when changes made to the form are to be saved.
 * @return TBool ETrue if the form data has been saved, EFalse otherwise
 */
TBool CBirthdayForm::SaveFormDataL()
	{
	if ( !iBirthday )
		return ETrue;	// should assert or panic here...
		
	TBuf< 500 > buf;
	
	iName->GetText( buf );
	iBirthday->SetName( buf );

	iBirthday->SetDate( iDate->Date() );

	iTodo->GetText( buf );
	iBirthday->SetTodo( buf );
	
	iBirthdayChanged = ETrue;
	
	return ETrue;
	}

/**
 * Called from QuerySaveChangesL when changes made to the form are discarded.
 */
void CBirthdayForm::DoNotSaveFormDataL()
	{
	LoadFromDataL();
	}

/**
 * Called from DoNotSaveFormDataL when changes are cancelled.
 */
void CBirthdayForm::LoadFromDataL()
	{
	if ( !iBirthday )
		return;	// should assert or panic here...

	iName->SetTextL( &iBirthday->Name() );
	iDate->SetDate( iBirthday->Date() );
	iTodo->SetTextL( &iBirthday->Todo() );
	
	iBirthdayChanged = EFalse;
	}

/**
 * Initialize controls and settings before a Form is laid out.  (override)
 */
void CBirthdayForm::PreLayoutDynInitL()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	iName = static_cast< CEikEdwin* >( 
		ControlOrNull( EBirthdaysAppUiName ) );
		{
		HBufC* text = StringLoader::LoadLC( R_BIRTHDAY_FORM_NAME );
		iName->SetTextL( text );
		CleanupStack::PopAndDestroy( text );
		}
	iDate = static_cast< CEikDateEditor* >( 
		ControlOrNull( EBirthdaysAppUiDate ) );
	iDate->SetDate( TTime( TDateTime( 1980, EJanuary, 0, 0, 0, 0, 0 ) ) );
	iTodo = static_cast< CEikEdwin* >( 
		ControlOrNull( EBirthdaysAppUiTodo ) );
		{
		HBufC* text = StringLoader::LoadLC( R_BIRTHDAY_FORM_TODO );
		iTodo->SetTextL( text );
		CleanupStack::PopAndDestroy( text );
		}
	// ]]] end generated region [Generated Contents]
	
	if ( !iBirthdayEmpty )
		LoadFromDataL();
	}
				
/**
 * Called when built-in menu is displayed.  (override)
 * CAknForm has a built-in menu. In view mode, the only menu item is "Edit" that switches the form to edit mode.
 * In edit mode, the items are "Add field" "Save" "Edit label" and "Delete field"
 * The code below removes all edit mode items except for "Save"
 * To use these items, remove the appropriate lines and override the appropriate CAknForm methods.
 * (E.g., to handle "Add field" remove the line for EAknFormCmdAdd and override CAknForm::AddItemL())
 */
void CBirthdayForm::DynInitMenuPaneL( TInt aResourceId, CEikMenuPane* aMenuPane )
	{
	CAknForm::DynInitMenuPaneL( aResourceId, aMenuPane );
	
	if ( aResourceId == R_AVKON_FORM_MENUPANE )
		{
		aMenuPane->SetItemDimmed( EAknFormCmdAdd, ETrue );
		aMenuPane->SetItemDimmed( EAknFormCmdLabel, ETrue );
		aMenuPane->SetItemDimmed( EAknFormCmdDelete, ETrue );

		/* 
		// sample code to add a custom item with command id 'myCommandId'
		TInt pos;
		if ( !aMenuPane->MenuItemExists( myCommandId, pos ) )
			{
			CEikMenuPaneItem::SData menuItem;
			menuItem.iCommandId = myCommandId;
			menuItem.iCascadeId = 0;	// set for submenus
			menuItem.iFlags = 0;		// e.g. EEikMenuItemDimmed
			menuItem.iText = KMyCommandIdText;
			menuItem.iExtraText = _L("");
			aMenuPane->AddMenuItemL( menuItem );
			}
		*/
		}
	// [[[ begin generated region: do not modify [Generated Code]
	
	// ]]] end generated region [Generated Code]
	
	}
				
/**
 * Handle a button press and tell whether it closes the dialog.  (override)  
 * @param aButtonId the identifier for the button (avkon.hrh)
 */
TBool CBirthdayForm::OkToExitL(TInt aButtonId)
	{
	// [[[ begin generated region: do not modify [Generated Code]
	
	// ]]] end generated region [Generated Code]
	
	return CAknForm::OkToExitL( aButtonId );
	}

