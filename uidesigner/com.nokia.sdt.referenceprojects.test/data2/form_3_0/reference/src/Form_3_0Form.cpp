// [[[ begin generated region: do not modify [Generated System Includes]
#include <avkon.hrh>
#include <avkon.rsg>
#include <eikmenup.h>
#include <aknappui.h>
#include <eikcmobs.h>
#include <barsread.h>
#include <stringloader.h>
#include <gdi.h>
#include <eikmfne.h>
#include <eikenv.h>
#include <eikedwin.h>
#include <aknpopupfieldtext.h>
#include <form_3_0.rsg>
// ]]] end generated region [Generated System Includes]

// [[[ begin generated region: do not modify [Generated User Includes]
#include "Form_3_0Form.h"
#include "form_3_0.hrh"
#include "Form_3_0Form.hrh"
// ]]] end generated region [Generated User Includes]

// [[[ begin generated region: do not modify [Generated Constants]
// ]]] end generated region [Generated Constants]

/**
 * Construct the CForm_3_0Form instance
 * @param aCommandObserver command observer
 */ 
CForm_3_0Form::CForm_3_0Form( MEikCommandObserver* aCommandObserver )
	{
	iCommandObserver = aCommandObserver;
	// [[[ begin generated region: do not modify [Generated Contents]
	iNumEditor1 = NULL;
	iDurationEditor1 = NULL;
	iEdit1 = NULL;
	iPopupFieldText1 = NULL;
	// ]]] end generated region [Generated Contents]
	
	}

/**
 *  Creates an instance and initializes it.
 *  Instance is not left on cleanup stack.
 * @param aCommandObserver command observer
 * @return initialized instance of CForm_3_0Form
 */
CForm_3_0Form* CForm_3_0Form::NewL( MEikCommandObserver* aCommandObserver )
	{
	CForm_3_0Form* self = CForm_3_0Form::NewLC( aCommandObserver );
	CleanupStack::Pop( self );
	return self;
	}

/**
 *  Creates an instance and initializes it.
 *  Instance is left on cleanup stack.
 * @param aCommandObserver command observer
 * @return new instance of CForm_3_0Form
 */
CForm_3_0Form* CForm_3_0Form::NewLC( MEikCommandObserver* aCommandObserver )
	{
	CForm_3_0Form* self = new ( ELeave ) CForm_3_0Form( aCommandObserver );
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
TKeyResponse CForm_3_0Form::OfferKeyEventL( 
		const TKeyEvent& aKeyEvent, 
		TEventCode aType )
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	// ]]] end generated region [Generated Contents]
	
	if ( ( aKeyEvent.iCode == EKeyLeftArrow 
		|| aKeyEvent.iCode == EKeyRightArrow )
		&& !IsEditable() )
		{
		// allow the tab control to get the arrow keys
		return EKeyWasNotConsumed;
		}
	
	return CAknForm::OfferKeyEventL( aKeyEvent, aType );
	}
				
/** 
 * Destroy any instance variables
 */
CForm_3_0Form::~CForm_3_0Form()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	// ]]] end generated region [Generated Contents]
	
	}

/**
 * Called to handle "Save" menu item. Displays save query.
 * @return TBool ETrue if the form data is to be saved, EFalse otherwise
 */
TBool CForm_3_0Form::QuerySaveChangesL()
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
TBool CForm_3_0Form::SaveFormDataL()
	{
	// TODO save values
	return ETrue;
	}

/**
 * Called from QuerySaveChangesL when changes made to the form are discarded.
 */
void CForm_3_0Form::DoNotSaveFormDataL()
	{
	LoadFromDataL();
	}

/**
 * Called from DoNotSaveFormDataL when changes are cancelled.
 * Called from PreLayoutDynInitL to load initial values if needed.
 */
void CForm_3_0Form::LoadFromDataL()
	{
	// TODO load saved values	

	}

/**
 * Initialize controls and settings before a Form is laid out.  (override)
 */
void CForm_3_0Form::PreLayoutDynInitL()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	iNumEditor1 = static_cast< CEikNumberEditor* >( 
		ControlOrNull( EForm_3_0FormViewNumEditor1 ) );
	iNumEditor1->SetNumber( 0 );
	iDurationEditor1 = static_cast< CEikDurationEditor* >( 
		ControlOrNull( EForm_3_0FormViewDurationEditor1 ) );
	iDurationEditor1->SetDuration( TTimeIntervalSeconds( 0 ) );
	iEdit1 = static_cast< CEikEdwin* >( 
		ControlOrNull( EForm_3_0FormViewEdit1 ) );
		{
		HBufC* text = StringLoader::LoadLC( R_FORM_3_0_FORM_EDIT1 );
		iEdit1->SetTextL( text );
		CleanupStack::PopAndDestroy( text );
		}
	iPopupFieldText1 = static_cast< CAknPopupFieldText* >( 
		ControlOrNull( EForm_3_0FormViewPopupFieldText1 ) );
	iPopupFieldText1->SetFont( iEikonEnv->NormalFont() );
	// ]]] end generated region [Generated Contents]
	
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
void CForm_3_0Form::DynInitMenuPaneL( TInt aResourceId, CEikMenuPane* aMenuPane )
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
TBool CForm_3_0Form::OkToExitL( TInt aButtonId )
	{
	// [[[ begin generated region: do not modify [Generated Code]
	
	if ( aButtonId == EAknSoftkeyBack )
		{
		if ( CAknForm::OkToExitL( EAknSoftkeyBack ) )
			{
			iAvkonAppUi->ProcessCommandL( EEikCmdExit );
			}
		else
			{
			return EFalse;
			}
		}
	// ]]] end generated region [Generated Code]
	
	// The 'exit application on close' property automatically adds code to exit the 
	// application when the right soft key is pressed. If this property is set to false, 
	// and AddToStackL has been called on the Form object (e.g., it was not shown as a dialog), 
	// code must be added manually to avoid a panic when exiting the application.
	return CAknForm::OkToExitL( aButtonId );
	}

