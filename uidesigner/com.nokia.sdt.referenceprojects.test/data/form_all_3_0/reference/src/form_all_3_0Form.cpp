// [[[ begin generated region: do not modify [Generated System Includes]
#include <avkon.hrh>
#include <avkon.rsg>
#include <eikmenup.h>
#include <aknappui.h>
#include <eikcmobs.h>
#include <barsread.h>
#include <stringloader.h>
#include <aknnumedwin.h>
#include <eikenv.h>
#include <gdi.h>
#include <eikmfne.h>
#include <eikfpne.h>
#include <aknnumseced.h>
#include <eikseced.h>
#include <eikedwin.h>
#include <form_all_3_0.rsg>
// ]]] end generated region [Generated System Includes]

// [[[ begin generated region: do not modify [Generated User Includes]
#include "form_all_3_0Form.h"
#include "form_all_3_0.hrh"
#include "form_all_3_0Form.hrh"
// ]]] end generated region [Generated User Includes]

// [[[ begin generated region: do not modify [Generated Constants]
_LIT( KIntegerEditor1TextValue, "5" );
_LIT( KFloatingPointEditor1TextValue, "2.3" );
// ]]] end generated region [Generated Constants]

/**
 * Construct the CForm_all_3_0Form instance
 * @param aCommandObserver command observer
 */ 
CForm_all_3_0Form::CForm_all_3_0Form( MEikCommandObserver* aCommandObserver )
	{
	iCommandObserver = aCommandObserver;
	// [[[ begin generated region: do not modify [Generated Contents]
	iIntegerEditor1 = NULL;
	iDateEditor1 = NULL;
	iDurationEditor1 = NULL;
	iFixedPointEditor1 = NULL;
	iFloatingPointEditor1 = NULL;
	iNumEditor1 = NULL;
	iNumSecret1 = NULL;
	iRangeEditor1 = NULL;
	iSecret1 = NULL;
	iEdit1 = NULL;
	iTimeEditor1 = NULL;
	iTimeOffsetEditor1 = NULL;
	iTimeAndDateEditor1 = NULL;
	// ]]] end generated region [Generated Contents]
	
	}

/**
 *  Creates an instance and initializes it.
 *  Instance is not left on cleanup stack.
 * @param aCommandObserver command observer
 * @return initialized instance of CForm_all_3_0Form
 */
CForm_all_3_0Form* CForm_all_3_0Form::NewL( MEikCommandObserver* aCommandObserver )
	{
	CForm_all_3_0Form* self = CForm_all_3_0Form::NewLC( aCommandObserver );
	CleanupStack::Pop( self );
	return self;
	}

/**
 *  Creates an instance and initializes it.
 *  Instance is left on cleanup stack.
 * @param aCommandObserver command observer
 * @return new instance of CForm_all_3_0Form
 */
CForm_all_3_0Form* CForm_all_3_0Form::NewLC( MEikCommandObserver* aCommandObserver )
	{
	CForm_all_3_0Form* self = new ( ELeave ) CForm_all_3_0Form( aCommandObserver );
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
TKeyResponse CForm_all_3_0Form::OfferKeyEventL( 
		const TKeyEvent& aKeyEvent, 
		TEventCode aType )
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	if ( HandleForm_all_3_0FormOfferKeyEventL( aKeyEvent, aType ) == EKeyWasConsumed )
		{
		return EKeyWasConsumed;
		}
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
CForm_all_3_0Form::~CForm_all_3_0Form()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	iControlEventDispatch.Close();		
	iEdwinEventDispatch.Close();		
	// ]]] end generated region [Generated Contents]
	
	}

/**
 * Called to handle "Save" menu item. Displays save query.
 * @return TBool ETrue if the form data is to be saved, EFalse otherwise
 */
TBool CForm_all_3_0Form::QuerySaveChangesL()
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
TBool CForm_all_3_0Form::SaveFormDataL()
	{
	// TODO save values
	return ETrue;
	}

/**
 * Called from QuerySaveChangesL when changes made to the form are discarded.
 */
void CForm_all_3_0Form::DoNotSaveFormDataL()
	{
	LoadFromDataL();
	}

/**
 * Called from DoNotSaveFormDataL when changes are cancelled.
 * Called from PreLayoutDynInitL to load initial values if needed.
 */
void CForm_all_3_0Form::LoadFromDataL()
	{
	// TODO load saved values	

	}

/**
 * Initialize controls and settings before a Form is laid out.  (override)
 */
void CForm_all_3_0Form::PreLayoutDynInitL()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	iIntegerEditor1 = static_cast< CAknIntegerEdwin* >( 
		ControlOrNull( EForm_all_3_0FormViewIntegerEditor1 ) );
	iIntegerEditor1->SetValueL( 5 );
	iIntegerEditor1->SetTextL( &KIntegerEditor1TextValue );
	AddControlEventHandlerL( 
			iIntegerEditor1, 
			EEventStateChanged, 
			&CForm_all_3_0Form::HandleIntegerEditor1StateChangedL );
	AddControlEventHandlerL( 
			iIntegerEditor1, 
			EEventRequestFocus, 
			&CForm_all_3_0Form::HandleIntegerEditor1RequestingFocusL );
	AddControlEventHandlerL( 
			iIntegerEditor1, 
			EEventRequestExit, 
			&CForm_all_3_0Form::HandleIntegerEditor1RequestingExitL );
	AddControlEventHandlerL( 
			iIntegerEditor1, 
			EEventRequestCancel, 
			&CForm_all_3_0Form::HandleIntegerEditor1RequestingCancelL );
	AddControlEventHandlerL( 
			iIntegerEditor1, 
			EEventInteractionRefused, 
			&CForm_all_3_0Form::HandleIntegerEditor1InteractionRefusedL );
	AddControlEventHandlerL( 
			iIntegerEditor1, 
			EEventPrepareFocusTransition, 
			&CForm_all_3_0Form::HandleIntegerEditor1PrepareFocusTransitionL );
	iIntegerEditor1->AddEdwinObserverL( this );
	AddEdwinEventHandlerL( 
			iIntegerEditor1, 
			EEventFormatChanged, 
			&CForm_all_3_0Form::HandleIntegerEditor1FormatChangedL );
	AddEdwinEventHandlerL( 
			iIntegerEditor1, 
			EEventNavigation, 
			&CForm_all_3_0Form::HandleIntegerEditor1NavigationL );
	AddEdwinEventHandlerL( 
			iIntegerEditor1, 
			EEventTextUpdate, 
			&CForm_all_3_0Form::HandleIntegerEditor1TextUpdatedL );
	iDateEditor1 = static_cast< CEikDateEditor* >( 
		ControlOrNull( EForm_all_3_0FormViewDateEditor1 ) );
	iDateEditor1->SetDate( TTime( TDateTime( 2006, EJanuary, 0, 0, 0, 0, 0 ) ) );
	AddControlEventHandlerL( 
			iDateEditor1, 
			EEventStateChanged, 
			&CForm_all_3_0Form::HandleDateEditor1StateChangedL );
	AddControlEventHandlerL( 
			iDateEditor1, 
			EEventRequestFocus, 
			&CForm_all_3_0Form::HandleDateEditor1RequestingFocusL );
	AddControlEventHandlerL( 
			iDateEditor1, 
			EEventRequestExit, 
			&CForm_all_3_0Form::HandleDateEditor1RequestingExitL );
	AddControlEventHandlerL( 
			iDateEditor1, 
			EEventRequestCancel, 
			&CForm_all_3_0Form::HandleDateEditor1RequestingCancelL );
	AddControlEventHandlerL( 
			iDateEditor1, 
			EEventInteractionRefused, 
			&CForm_all_3_0Form::HandleDateEditor1InteractionRefusedL );
	AddControlEventHandlerL( 
			iDateEditor1, 
			EEventPrepareFocusTransition, 
			&CForm_all_3_0Form::HandleDateEditor1PrepareFocusTransitionL );
	iDurationEditor1 = static_cast< CEikDurationEditor* >( 
		ControlOrNull( EForm_all_3_0FormViewDurationEditor1 ) );
	iDurationEditor1->SetDuration( TTimeIntervalSeconds( 361 ) );
	AddControlEventHandlerL( 
			iDurationEditor1, 
			EEventStateChanged, 
			&CForm_all_3_0Form::HandleDurationEditor1StateChangedL );
	AddControlEventHandlerL( 
			iDurationEditor1, 
			EEventRequestFocus, 
			&CForm_all_3_0Form::HandleDurationEditor1RequestingFocusL );
	AddControlEventHandlerL( 
			iDurationEditor1, 
			EEventRequestExit, 
			&CForm_all_3_0Form::HandleDurationEditor1RequestingExitL );
	AddControlEventHandlerL( 
			iDurationEditor1, 
			EEventRequestCancel, 
			&CForm_all_3_0Form::HandleDurationEditor1RequestingCancelL );
	AddControlEventHandlerL( 
			iDurationEditor1, 
			EEventInteractionRefused, 
			&CForm_all_3_0Form::HandleDurationEditor1InteractionRefusedL );
	AddControlEventHandlerL( 
			iDurationEditor1, 
			EEventPrepareFocusTransition, 
			&CForm_all_3_0Form::HandleDurationEditor1PrepareFocusTransitionL );
	iFixedPointEditor1 = static_cast< CEikFixedPointEditor* >( 
		ControlOrNull( EForm_all_3_0FormViewFixedPointEditor1 ) );
		{
		TInt value = 1234;
		iFixedPointEditor1->SetValueL( &value );
		}
	AddControlEventHandlerL( 
			iFixedPointEditor1, 
			EEventStateChanged, 
			&CForm_all_3_0Form::HandleFixedPointEditor1StateChangedL );
	AddControlEventHandlerL( 
			iFixedPointEditor1, 
			EEventRequestFocus, 
			&CForm_all_3_0Form::HandleFixedPointEditor1RequestingFocusL );
	AddControlEventHandlerL( 
			iFixedPointEditor1, 
			EEventRequestExit, 
			&CForm_all_3_0Form::HandleFixedPointEditor1RequestingExitL );
	AddControlEventHandlerL( 
			iFixedPointEditor1, 
			EEventRequestCancel, 
			&CForm_all_3_0Form::HandleFixedPointEditor1RequestingCancelL );
	AddControlEventHandlerL( 
			iFixedPointEditor1, 
			EEventInteractionRefused, 
			&CForm_all_3_0Form::HandleFixedPointEditor1InteractionRefusedL );
	AddControlEventHandlerL( 
			iFixedPointEditor1, 
			EEventPrepareFocusTransition, 
			&CForm_all_3_0Form::HandleFixedPointEditor1PrepareFocusTransitionL );
	iFixedPointEditor1->AddEdwinObserverL( this );
	AddEdwinEventHandlerL( 
			iFixedPointEditor1, 
			EEventFormatChanged, 
			&CForm_all_3_0Form::HandleFixedPointEditor1FormatChangedL );
	AddEdwinEventHandlerL( 
			iFixedPointEditor1, 
			EEventNavigation, 
			&CForm_all_3_0Form::HandleFixedPointEditor1NavigationL );
	AddEdwinEventHandlerL( 
			iFixedPointEditor1, 
			EEventTextUpdate, 
			&CForm_all_3_0Form::HandleFixedPointEditor1TextUpdatedL );
	iFloatingPointEditor1 = static_cast< CEikFloatingPointEditor* >( 
		ControlOrNull( EForm_all_3_0FormViewFloatingPointEditor1 ) );
		{
		TReal value = 2.3;
		iFloatingPointEditor1->SetValueL( &value );
		iFloatingPointEditor1->SetTextL( &KFloatingPointEditor1TextValue );
		}
	AddControlEventHandlerL( 
			iFloatingPointEditor1, 
			EEventStateChanged, 
			&CForm_all_3_0Form::HandleFloatingPointEditor1StateChangedL );
	AddControlEventHandlerL( 
			iFloatingPointEditor1, 
			EEventRequestFocus, 
			&CForm_all_3_0Form::HandleFloatingPointEditor1RequestingFocusL );
	AddControlEventHandlerL( 
			iFloatingPointEditor1, 
			EEventRequestExit, 
			&CForm_all_3_0Form::HandleFloatingPointEditor1RequestingExitL );
	AddControlEventHandlerL( 
			iFloatingPointEditor1, 
			EEventRequestCancel, 
			&CForm_all_3_0Form::HandleFloatingPointEditor1RequestingCancelL );
	AddControlEventHandlerL( 
			iFloatingPointEditor1, 
			EEventInteractionRefused, 
			&CForm_all_3_0Form::HandleFloatingPointEditor1InteractionRefusedL );
	AddControlEventHandlerL( 
			iFloatingPointEditor1, 
			EEventPrepareFocusTransition, 
			&CForm_all_3_0Form::HandleFloatingPointEditor1PrepareFocusTransitionL );
	iFloatingPointEditor1->AddEdwinObserverL( this );
	AddEdwinEventHandlerL( 
			iFloatingPointEditor1, 
			EEventFormatChanged, 
			&CForm_all_3_0Form::HandleFloatingPointEditor1FormatChangedL );
	AddEdwinEventHandlerL( 
			iFloatingPointEditor1, 
			EEventNavigation, 
			&CForm_all_3_0Form::HandleFloatingPointEditor1NavigationL );
	AddEdwinEventHandlerL( 
			iFloatingPointEditor1, 
			EEventTextUpdate, 
			&CForm_all_3_0Form::HandleFloatingPointEditor1TextUpdatedL );
	iNumEditor1 = static_cast< CEikNumberEditor* >( 
		ControlOrNull( EForm_all_3_0FormViewNumEditor1 ) );
	iNumEditor1->SetNumber( 6 );
	AddControlEventHandlerL( 
			iNumEditor1, 
			EEventStateChanged, 
			&CForm_all_3_0Form::HandleNumEditor1StateChangedL );
	AddControlEventHandlerL( 
			iNumEditor1, 
			EEventRequestFocus, 
			&CForm_all_3_0Form::HandleNumEditor1RequestingFocusL );
	AddControlEventHandlerL( 
			iNumEditor1, 
			EEventRequestExit, 
			&CForm_all_3_0Form::HandleNumEditor1RequestingExitL );
	AddControlEventHandlerL( 
			iNumEditor1, 
			EEventRequestCancel, 
			&CForm_all_3_0Form::HandleNumEditor1RequestingCancelL );
	AddControlEventHandlerL( 
			iNumEditor1, 
			EEventInteractionRefused, 
			&CForm_all_3_0Form::HandleNumEditor1InteractionRefusedL );
	AddControlEventHandlerL( 
			iNumEditor1, 
			EEventPrepareFocusTransition, 
			&CForm_all_3_0Form::HandleNumEditor1PrepareFocusTransitionL );
	iNumSecret1 = static_cast< CAknNumericSecretEditor* >( 
		ControlOrNull( EForm_all_3_0FormViewNumSecret1 ) );
		{
		HBufC* text = StringLoader::LoadLC( R_FORM_ALL_3_0_FORM_NUM_SECRET1 );
		iNumSecret1->SetText( *text );
		CleanupStack::PopAndDestroy( text );
		}
	AddControlEventHandlerL( 
			iNumSecret1, 
			EEventStateChanged, 
			&CForm_all_3_0Form::HandleNumSecret1StateChangedL );
	AddControlEventHandlerL( 
			iNumSecret1, 
			EEventRequestFocus, 
			&CForm_all_3_0Form::HandleNumSecret1RequestingFocusL );
	AddControlEventHandlerL( 
			iNumSecret1, 
			EEventRequestExit, 
			&CForm_all_3_0Form::HandleNumSecret1RequestingExitL );
	AddControlEventHandlerL( 
			iNumSecret1, 
			EEventRequestCancel, 
			&CForm_all_3_0Form::HandleNumSecret1RequestingCancelL );
	AddControlEventHandlerL( 
			iNumSecret1, 
			EEventInteractionRefused, 
			&CForm_all_3_0Form::HandleNumSecret1InteractionRefusedL );
	AddControlEventHandlerL( 
			iNumSecret1, 
			EEventPrepareFocusTransition, 
			&CForm_all_3_0Form::HandleNumSecret1PrepareFocusTransitionL );
	iRangeEditor1 = static_cast< CEikRangeEditor* >( 
		ControlOrNull( EForm_all_3_0FormViewRangeEditor1 ) );
		{
		SEikRange range = { 5, 1 };
		iRangeEditor1->SetRange( range );
		}
	AddControlEventHandlerL( 
			iRangeEditor1, 
			EEventStateChanged, 
			&CForm_all_3_0Form::HandleRangeEditor1StateChangedL );
	AddControlEventHandlerL( 
			iRangeEditor1, 
			EEventRequestFocus, 
			&CForm_all_3_0Form::HandleRangeEditor1RequestingFocusL );
	AddControlEventHandlerL( 
			iRangeEditor1, 
			EEventRequestExit, 
			&CForm_all_3_0Form::HandleRangeEditor1RequestingExitL );
	AddControlEventHandlerL( 
			iRangeEditor1, 
			EEventRequestCancel, 
			&CForm_all_3_0Form::HandleRangeEditor1RequestingCancelL );
	AddControlEventHandlerL( 
			iRangeEditor1, 
			EEventInteractionRefused, 
			&CForm_all_3_0Form::HandleRangeEditor1InteractionRefusedL );
	AddControlEventHandlerL( 
			iRangeEditor1, 
			EEventPrepareFocusTransition, 
			&CForm_all_3_0Form::HandleRangeEditor1PrepareFocusTransitionL );
	iSecret1 = static_cast< CEikSecretEditor* >( 
		ControlOrNull( EForm_all_3_0FormViewSecret1 ) );
		{
		HBufC* text = StringLoader::LoadLC( R_FORM_ALL_3_0_FORM_SECRET1 );
		iSecret1->SetText( *text );
		CleanupStack::PopAndDestroy( text );
		}
	AddControlEventHandlerL( 
			iSecret1, 
			EEventStateChanged, 
			&CForm_all_3_0Form::HandleSecret1StateChangedL );
	AddControlEventHandlerL( 
			iSecret1, 
			EEventRequestFocus, 
			&CForm_all_3_0Form::HandleSecret1RequestingFocusL );
	AddControlEventHandlerL( 
			iSecret1, 
			EEventRequestExit, 
			&CForm_all_3_0Form::HandleSecret1RequestingExitL );
	AddControlEventHandlerL( 
			iSecret1, 
			EEventRequestCancel, 
			&CForm_all_3_0Form::HandleSecret1RequestingCancelL );
	AddControlEventHandlerL( 
			iSecret1, 
			EEventInteractionRefused, 
			&CForm_all_3_0Form::HandleSecret1InteractionRefusedL );
	AddControlEventHandlerL( 
			iSecret1, 
			EEventPrepareFocusTransition, 
			&CForm_all_3_0Form::HandleSecret1PrepareFocusTransitionL );
	iEdit1 = static_cast< CEikEdwin* >( 
		ControlOrNull( EForm_all_3_0FormViewEdit1 ) );
		{
		HBufC* text = StringLoader::LoadLC( R_FORM_ALL_3_0_FORM_EDIT1 );
		iEdit1->SetTextL( text );
		CleanupStack::PopAndDestroy( text );
		}
	AddControlEventHandlerL( 
			iEdit1, 
			EEventStateChanged, 
			&CForm_all_3_0Form::HandleEdit1StateChangedL );
	AddControlEventHandlerL( 
			iEdit1, 
			EEventRequestFocus, 
			&CForm_all_3_0Form::HandleEdit1RequestingFocusL );
	AddControlEventHandlerL( 
			iEdit1, 
			EEventRequestExit, 
			&CForm_all_3_0Form::HandleEdit1RequestingExitL );
	AddControlEventHandlerL( 
			iEdit1, 
			EEventRequestCancel, 
			&CForm_all_3_0Form::HandleEdit1RequestingCancelL );
	AddControlEventHandlerL( 
			iEdit1, 
			EEventInteractionRefused, 
			&CForm_all_3_0Form::HandleEdit1InteractionRefusedL );
	AddControlEventHandlerL( 
			iEdit1, 
			EEventPrepareFocusTransition, 
			&CForm_all_3_0Form::HandleEdit1PrepareFocusTransitionL );
	iEdit1->AddEdwinObserverL( this );
	AddEdwinEventHandlerL( 
			iEdit1, 
			EEventFormatChanged, 
			&CForm_all_3_0Form::HandleEdit1FormatChangedL );
	AddEdwinEventHandlerL( 
			iEdit1, 
			EEventNavigation, 
			&CForm_all_3_0Form::HandleEdit1NavigationL );
	AddEdwinEventHandlerL( 
			iEdit1, 
			EEventTextUpdate, 
			&CForm_all_3_0Form::HandleEdit1TextUpdatedL );
	iTimeEditor1 = static_cast< CEikTimeEditor* >( 
		ControlOrNull( EForm_all_3_0FormViewTimeEditor1 ) );
	iTimeEditor1->SetTime( TTime( TDateTime( 0, EJanuary, 0, 0, 0, 1, 0 ) ) );
	AddControlEventHandlerL( 
			iTimeEditor1, 
			EEventStateChanged, 
			&CForm_all_3_0Form::HandleTimeEditor1StateChangedL );
	AddControlEventHandlerL( 
			iTimeEditor1, 
			EEventRequestFocus, 
			&CForm_all_3_0Form::HandleTimeEditor1RequestingFocusL );
	AddControlEventHandlerL( 
			iTimeEditor1, 
			EEventRequestExit, 
			&CForm_all_3_0Form::HandleTimeEditor1RequestingExitL );
	AddControlEventHandlerL( 
			iTimeEditor1, 
			EEventRequestCancel, 
			&CForm_all_3_0Form::HandleTimeEditor1RequestingCancelL );
	AddControlEventHandlerL( 
			iTimeEditor1, 
			EEventInteractionRefused, 
			&CForm_all_3_0Form::HandleTimeEditor1InteractionRefusedL );
	AddControlEventHandlerL( 
			iTimeEditor1, 
			EEventPrepareFocusTransition, 
			&CForm_all_3_0Form::HandleTimeEditor1PrepareFocusTransitionL );
	iTimeOffsetEditor1 = static_cast< CEikTimeOffsetEditor* >( 
		ControlOrNull( EForm_all_3_0FormViewTimeOffsetEditor1 ) );
	iTimeOffsetEditor1->SetTimeOffset( TTimeIntervalSeconds( 500 ) );
	AddControlEventHandlerL( 
			iTimeOffsetEditor1, 
			EEventStateChanged, 
			&CForm_all_3_0Form::HandleTimeOffsetEditor1StateChangedL );
	AddControlEventHandlerL( 
			iTimeOffsetEditor1, 
			EEventRequestFocus, 
			&CForm_all_3_0Form::HandleTimeOffsetEditor1RequestingFocusL );
	AddControlEventHandlerL( 
			iTimeOffsetEditor1, 
			EEventRequestExit, 
			&CForm_all_3_0Form::HandleTimeOffsetEditor1RequestingExitL );
	AddControlEventHandlerL( 
			iTimeOffsetEditor1, 
			EEventRequestCancel, 
			&CForm_all_3_0Form::HandleTimeOffsetEditor1RequestingCancelL );
	AddControlEventHandlerL( 
			iTimeOffsetEditor1, 
			EEventInteractionRefused, 
			&CForm_all_3_0Form::HandleTimeOffsetEditor1InteractionRefusedL );
	AddControlEventHandlerL( 
			iTimeOffsetEditor1, 
			EEventPrepareFocusTransition, 
			&CForm_all_3_0Form::HandleTimeOffsetEditor1PrepareFocusTransitionL );
	iTimeAndDateEditor1 = static_cast< CEikTimeAndDateEditor* >( 
		ControlOrNull( EForm_all_3_0FormViewTimeAndDateEditor1 ) );
	iTimeAndDateEditor1->SetTimeAndDate( TTime( TDateTime( 
			2006, EJanuary, 0, 
			0, 0, 50, 0 ) ) );
	AddControlEventHandlerL( 
			iTimeAndDateEditor1, 
			EEventStateChanged, 
			&CForm_all_3_0Form::HandleTimeAndDateEditor1StateChangedL );
	AddControlEventHandlerL( 
			iTimeAndDateEditor1, 
			EEventRequestFocus, 
			&CForm_all_3_0Form::HandleTimeAndDateEditor1RequestingFocusL );
	AddControlEventHandlerL( 
			iTimeAndDateEditor1, 
			EEventRequestExit, 
			&CForm_all_3_0Form::HandleTimeAndDateEditor1RequestingExitL );
	AddControlEventHandlerL( 
			iTimeAndDateEditor1, 
			EEventRequestCancel, 
			&CForm_all_3_0Form::HandleTimeAndDateEditor1RequestingCancelL );
	AddControlEventHandlerL( 
			iTimeAndDateEditor1, 
			EEventInteractionRefused, 
			&CForm_all_3_0Form::HandleTimeAndDateEditor1InteractionRefusedL );
	AddControlEventHandlerL( 
			iTimeAndDateEditor1, 
			EEventPrepareFocusTransition, 
			&CForm_all_3_0Form::HandleTimeAndDateEditor1PrepareFocusTransitionL );
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
void CForm_all_3_0Form::DynInitMenuPaneL( TInt aResourceId, CEikMenuPane* aMenuPane )
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
	
	HandleForm_all_3_0FormDynInitMenuPaneL( aResourceId, aMenuPane );
	
	// ]]] end generated region [Generated Code]
	
	}
				
/**
 * Handle a button press and tell whether it closes the dialog.  (override)  
 * @param aButtonId the identifier for the button (avkon.hrh)
 */
TBool CForm_all_3_0Form::OkToExitL( TInt aButtonId )
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

/** 
 * Handle focus transition this dialog (override)
 */
void CForm_all_3_0Form::PrepareForFocusTransitionL()
	{
	CAknForm::PrepareForFocusTransitionL();
	// [[[ begin generated region: do not modify [Generated Contents]
	HandleForm_all_3_0FormPrepareForFocusTransitionL();
	// ]]] end generated region [Generated Contents]
	
	}
				
/** 
 * Handle the prepareForFocusTransition event.
 */
void CForm_all_3_0Form::HandleForm_all_3_0FormPrepareForFocusTransitionL()
	{
	// TODO: implement prepareForFocusTransition event handler
	}
				
/** 
 * Handle page changed notifications this dialog (override)
 */
void CForm_all_3_0Form::PageChangedL( TInt aPageId )
	{
	CAknForm::PageChangedL( aPageId );
	// [[[ begin generated region: do not modify [Generated Contents]
	HandleForm_all_3_0FormPageChangedL( aPageId );
	// ]]] end generated region [Generated Contents]
	
	}
				
/** 
 * Handle the pageChanged event.
 */
void CForm_all_3_0Form::HandleForm_all_3_0FormPageChangedL( TInt aPageId )
	{
	// TODO: implement pageChanged event handler
	}
				
/** 
 * Handle line change notifications for this dialog (override)
 */
void CForm_all_3_0Form::LineChangedL( TInt aControlId )
	{
	CAknForm::LineChangedL( aControlId );
	// [[[ begin generated region: do not modify [Generated Contents]
	HandleForm_all_3_0FormLineChangedL( aControlId );
	// ]]] end generated region [Generated Contents]
	
	}
				
/** 
 * Handle the lineChanged event.
 */
void CForm_all_3_0Form::HandleForm_all_3_0FormLineChangedL( TInt aControlId )
	{
	// TODO: implement lineChanged event handler
	}
				
/** 
 * Handle commands for this dialog. (override)
 */
void CForm_all_3_0Form::ProcessCommandL( TInt aCommandId )
	{
	CAknForm::ProcessCommandL( aCommandId );
	// [[[ begin generated region: do not modify [Generated Contents]
	HandleForm_all_3_0FormProcessCommandL( aCommandId );
	// ]]] end generated region [Generated Contents]
	
	}
				
/** 
 * Handle the processCommand event.
 */
void CForm_all_3_0Form::HandleForm_all_3_0FormProcessCommandL( TInt aCommandId )
	{
	// TODO: implement processCommand event handler
	}
				
/** 
 * Handle special keys during menu selection for this dialog. (override)
 */
void CForm_all_3_0Form::OfferKeyToAppL( 
		const TKeyEvent& aKeyEvent, 
		TEventCode aType )
	{
	CAknForm::OfferKeyToAppL( aKeyEvent, aType );
	// [[[ begin generated region: do not modify [Generated Contents]
	HandleForm_all_3_0FormOfferKeyToAppL( aKeyEvent, aType );
	// ]]] end generated region [Generated Contents]
	
	}
				
/** 
 * Handle the offerKeyToApp event.
 */
void CForm_all_3_0Form::HandleForm_all_3_0FormOfferKeyToAppL( 
		const TKeyEvent& aKeyEvent, 
		TEventCode aType )
	{
	// TODO: implement offerKeyToApp event handler
	}
				
/** 
 * Handle focus change (override)
 */
void CForm_all_3_0Form::FocusChanged( TDrawNow aDrawNow )
	{
	CAknForm::FocusChanged( aDrawNow );
	// [[[ begin generated region: do not modify [Generated Contents]
	HandleForm_all_3_0FormFocusChanged( aDrawNow );
	// ]]] end generated region [Generated Contents]
	
	}
				
/** 
 * Handle the focusChanged event.
 */
void CForm_all_3_0Form::HandleForm_all_3_0FormFocusChanged( TDrawNow aDrawNow )
	{
	// TODO: implement focusChanged event handler
	}
				
/** 
 * Handle size change (override)
 */
void CForm_all_3_0Form::SizeChanged()
	{
	CAknForm::SizeChanged();
	// [[[ begin generated region: do not modify [Generated Contents]
	HandleForm_all_3_0FormSizeChanged();
	// ]]] end generated region [Generated Contents]
	
	}
				
/** 
 * Handle the sizeChanged event.
 */
void CForm_all_3_0Form::HandleForm_all_3_0FormSizeChanged()
	{
	// TODO: implement sizeChanged event handler
	}
				
/** 
 * Handle position change (override)
 */
void CForm_all_3_0Form::PositionChanged()
	{
	CAknForm::PositionChanged();
	// [[[ begin generated region: do not modify [Generated Contents]
	HandleForm_all_3_0FormPositionChanged();
	// ]]] end generated region [Generated Contents]
	
	}
				
/** 
 * Handle the positionChanged event.
 */
void CForm_all_3_0Form::HandleForm_all_3_0FormPositionChanged()
	{
	// TODO: implement positionChanged event handler
	}
				
/** 
 * Draw container contents (override)
 */
void CForm_all_3_0Form::Draw( const TRect& aRect ) const
	{
	CAknForm::Draw( aRect );
	// [[[ begin generated region: do not modify [Generated Contents]
	HandleForm_all_3_0FormDraw( aRect );
	// ]]] end generated region [Generated Contents]
	
	}
				
/** 
 * Handle the draw event.
 */
void CForm_all_3_0Form::HandleForm_all_3_0FormDraw( const TRect& aRect ) const
	{
	// TODO: implement draw event handler
	}
				
/** 
 * Handle the offerKeyEvent event.
 */
TKeyResponse CForm_all_3_0Form::HandleForm_all_3_0FormOfferKeyEventL( 
		const TKeyEvent& aKeyEvent, 
		TEventCode aType )
	{
	// TODO: implement offerKeyEvent event handler
	return EKeyWasNotConsumed;
	}
				
/** 
 * Handle focus change (override)
 */
void CForm_all_3_0Form::PrepareForFocusLossL()
	{
	CAknForm::PrepareForFocusLossL();
	// [[[ begin generated region: do not modify [Generated Contents]
	HandleForm_all_3_0FormPrepareForFocusLossL();
	// ]]] end generated region [Generated Contents]
	
	}
				
/** 
 * Handle the prepareForFocusLoss event.
 */
void CForm_all_3_0Form::HandleForm_all_3_0FormPrepareForFocusLossL()
	{
	// TODO: implement prepareForFocusLoss event handler
	}
				
/** 
 * Handle focus change (override)
 */
void CForm_all_3_0Form::PrepareForFocusGainL()
	{
	CAknForm::PrepareForFocusGainL();
	// [[[ begin generated region: do not modify [Generated Contents]
	HandleForm_all_3_0FormPrepareForFocusGainL();
	// ]]] end generated region [Generated Contents]
	
	}
				
/** 
 * Handle the prepareForFocusGain event.
 */
void CForm_all_3_0Form::HandleForm_all_3_0FormPrepareForFocusGainL()
	{
	// TODO: implement prepareForFocusGain event handler
	}
				
/** 
 * Handle global resource changes, such as scalable UI or skin events (override)
 */
void CForm_all_3_0Form::HandleResourceChange( TInt aType )
	{
	CAknForm::HandleResourceChange( aType );
	// [[[ begin generated region: do not modify [Generated Contents]
	HandleForm_all_3_0FormResourceChanged( aType );
	// ]]] end generated region [Generated Contents]
	
	}
				
/** 
 * Handle the resourceChanged event.
 */
void CForm_all_3_0Form::HandleForm_all_3_0FormResourceChanged( TInt aType )
	{
	// TODO: implement resourceChanged event handler
	}
				
/** 
 * Handle the dynInitMenuPane event.
 */
void CForm_all_3_0Form::HandleForm_all_3_0FormDynInitMenuPaneL( TInt aResourceId, CEikMenuPane* aMenuPane )
	{
	// TODO: implement dynInitMenuPane event handler
	}
				
/** 
 * Override of the HandleControlEventL virtual function
 */
void CForm_all_3_0Form::HandleControlEventL( 
		CCoeControl* aControl, 
		TCoeEvent anEventType )
	{
	CAknForm::HandleControlEventL( aControl, anEventType );

	for (int i = 0; i < iControlEventDispatch.Count(); i++)
		{
		const TControlEventDispatch& currEntry = iControlEventDispatch[i];
		if ( currEntry.src == aControl && currEntry.event == anEventType )
			{
			( this->*currEntry.handler )( aControl, anEventType );
			break;
			}
		}
	}
/** 
 * Helper function to register MCoeControlObserver event handlers
 */
void CForm_all_3_0Form::AddControlEventHandlerL( 
		CCoeControl* aControl, 
		TCoeEvent anEvent, 
		ControlEventHandler aHandler )
	{
	TControlEventDispatch entry;
	entry.src = aControl;
	entry.event = anEvent;
	entry.handler = aHandler;
	TInt err = iControlEventDispatch.Append( entry );
	User::LeaveIfError( err );
	}
			
/** 
 * Handle the EEventStateChanged event.
 */
void CForm_all_3_0Form::HandleIntegerEditor1StateChangedL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement stateChanged event handler
	}
				
/** 
 * Handle the EEventRequestFocus event.
 */
void CForm_all_3_0Form::HandleIntegerEditor1RequestingFocusL( CCoeControl* /* aControl */, TCoeEvent /* anEvent */ )
	{
	// TODO: implement requestingFocus event handler
	}
				
/** 
 * Handle the EEventRequestExit event.
 */
void CForm_all_3_0Form::HandleIntegerEditor1RequestingExitL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement requestingExit event handler
	}
				
/** 
 * Handle the EEventRequestCancel event.
 */
void CForm_all_3_0Form::HandleIntegerEditor1RequestingCancelL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement requestingCancel event handler
	}
				
/** 
 * Handle the EEventInteractionRefused event.
 */
void CForm_all_3_0Form::HandleIntegerEditor1InteractionRefusedL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement interactionRefused event handler
	}
				
/** 
 * Handle the EEventPrepareFocusTransition event.
 */
void CForm_all_3_0Form::HandleIntegerEditor1PrepareFocusTransitionL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement prepareFocusTransition event handler
	}
				
/** 
 * Override of the HandleEdwinEventL virtual function
 */
void CForm_all_3_0Form::HandleEdwinEventL( 
		CEikEdwin* anEdwin, 
		TEdwinEvent anEventType )
	{
	for (int i = 0; i < iEdwinEventDispatch.Count(); i++)
		{
		const TEdwinEventDispatch& currEntry = iEdwinEventDispatch[i];
		if ( currEntry.src == anEdwin && currEntry.event == anEventType )
			{
			( this->*currEntry.handler )( anEdwin, anEventType );
			break;
			}
		}
	}
/** 
 * Helper function to register MEikEdwinObserver event handlers
 */
void CForm_all_3_0Form::AddEdwinEventHandlerL( 
		CEikEdwin* anEdwin, 
		TEdwinEvent anEvent, 
		EdwinEventHandler aHandler )
	{
	TEdwinEventDispatch entry;
	entry.src = anEdwin;
	entry.event = anEvent;
	entry.handler = aHandler;
	TInt err = iEdwinEventDispatch.Append( entry );
	User::LeaveIfError( err );
	}
/** 
 * Handle the EEventFormatChanged event.
 */
void CForm_all_3_0Form::HandleIntegerEditor1FormatChangedL( 
		CEikEdwin* /* anEdwin */, 
		TEdwinEvent /* anEventType */ )
	{
	// TODO: implement formatChanged event handler
	}
				
/** 
 * Handle the EEventNavigation event.
 */
void CForm_all_3_0Form::HandleIntegerEditor1NavigationL( 
		CEikEdwin* /* anEdwin */, 
		TEdwinEvent /* anEventType */ )
	{
	// TODO: implement navigation event handler
	}
				
/** 
 * Handle the EEventTextUpdate event.
 */
void CForm_all_3_0Form::HandleIntegerEditor1TextUpdatedL( 
		CEikEdwin* /* anEdwin */, 
		TEdwinEvent /* anEventType */ )
	{
	// TODO: implement textUpdated (FEP only) event handler
	}
				
/** 
 * Handle the EEventStateChanged event.
 */
void CForm_all_3_0Form::HandleDateEditor1StateChangedL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement stateChanged event handler
	}
				
/** 
 * Handle the EEventRequestFocus event.
 */
void CForm_all_3_0Form::HandleDateEditor1RequestingFocusL( CCoeControl* /* aControl */, TCoeEvent /* anEvent */ )
	{
	// TODO: implement requestingFocus event handler
	}
				
/** 
 * Handle the EEventRequestExit event.
 */
void CForm_all_3_0Form::HandleDateEditor1RequestingExitL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement requestingExit event handler
	}
				
/** 
 * Handle the EEventRequestCancel event.
 */
void CForm_all_3_0Form::HandleDateEditor1RequestingCancelL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement requestingCancel event handler
	}
				
/** 
 * Handle the EEventInteractionRefused event.
 */
void CForm_all_3_0Form::HandleDateEditor1InteractionRefusedL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement interactionRefused event handler
	}
				
/** 
 * Handle the EEventPrepareFocusTransition event.
 */
void CForm_all_3_0Form::HandleDateEditor1PrepareFocusTransitionL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement prepareFocusTransition event handler
	}
				
/** 
 * Handle the EEventStateChanged event.
 */
void CForm_all_3_0Form::HandleDurationEditor1StateChangedL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement stateChanged event handler
	}
				
/** 
 * Handle the EEventRequestFocus event.
 */
void CForm_all_3_0Form::HandleDurationEditor1RequestingFocusL( CCoeControl* /* aControl */, TCoeEvent /* anEvent */ )
	{
	// TODO: implement requestingFocus event handler
	}
				
/** 
 * Handle the EEventRequestExit event.
 */
void CForm_all_3_0Form::HandleDurationEditor1RequestingExitL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement requestingExit event handler
	}
				
/** 
 * Handle the EEventRequestCancel event.
 */
void CForm_all_3_0Form::HandleDurationEditor1RequestingCancelL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement requestingCancel event handler
	}
				
/** 
 * Handle the EEventInteractionRefused event.
 */
void CForm_all_3_0Form::HandleDurationEditor1InteractionRefusedL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement interactionRefused event handler
	}
				
/** 
 * Handle the EEventPrepareFocusTransition event.
 */
void CForm_all_3_0Form::HandleDurationEditor1PrepareFocusTransitionL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement prepareFocusTransition event handler
	}
				
/** 
 * Handle the EEventStateChanged event.
 */
void CForm_all_3_0Form::HandleFixedPointEditor1StateChangedL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement stateChanged event handler
	}
				
/** 
 * Handle the EEventRequestFocus event.
 */
void CForm_all_3_0Form::HandleFixedPointEditor1RequestingFocusL( CCoeControl* /* aControl */, TCoeEvent /* anEvent */ )
	{
	// TODO: implement requestingFocus event handler
	}
				
/** 
 * Handle the EEventRequestExit event.
 */
void CForm_all_3_0Form::HandleFixedPointEditor1RequestingExitL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement requestingExit event handler
	}
				
/** 
 * Handle the EEventRequestCancel event.
 */
void CForm_all_3_0Form::HandleFixedPointEditor1RequestingCancelL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement requestingCancel event handler
	}
				
/** 
 * Handle the EEventInteractionRefused event.
 */
void CForm_all_3_0Form::HandleFixedPointEditor1InteractionRefusedL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement interactionRefused event handler
	}
				
/** 
 * Handle the EEventPrepareFocusTransition event.
 */
void CForm_all_3_0Form::HandleFixedPointEditor1PrepareFocusTransitionL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement prepareFocusTransition event handler
	}
				
/** 
 * Handle the EEventFormatChanged event.
 */
void CForm_all_3_0Form::HandleFixedPointEditor1FormatChangedL( 
		CEikEdwin* /* anEdwin */, 
		TEdwinEvent /* anEventType */ )
	{
	// TODO: implement formatChanged event handler
	}
				
/** 
 * Handle the EEventNavigation event.
 */
void CForm_all_3_0Form::HandleFixedPointEditor1NavigationL( 
		CEikEdwin* /* anEdwin */, 
		TEdwinEvent /* anEventType */ )
	{
	// TODO: implement navigation event handler
	}
				
/** 
 * Handle the EEventTextUpdate event.
 */
void CForm_all_3_0Form::HandleFixedPointEditor1TextUpdatedL( 
		CEikEdwin* /* anEdwin */, 
		TEdwinEvent /* anEventType */ )
	{
	// TODO: implement textUpdated (FEP only) event handler
	}
				
/** 
 * Handle the EEventStateChanged event.
 */
void CForm_all_3_0Form::HandleFloatingPointEditor1StateChangedL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement stateChanged event handler
	}
				
/** 
 * Handle the EEventRequestFocus event.
 */
void CForm_all_3_0Form::HandleFloatingPointEditor1RequestingFocusL( CCoeControl* /* aControl */, TCoeEvent /* anEvent */ )
	{
	// TODO: implement requestingFocus event handler
	}
				
/** 
 * Handle the EEventRequestExit event.
 */
void CForm_all_3_0Form::HandleFloatingPointEditor1RequestingExitL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement requestingExit event handler
	}
				
/** 
 * Handle the EEventRequestCancel event.
 */
void CForm_all_3_0Form::HandleFloatingPointEditor1RequestingCancelL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement requestingCancel event handler
	}
				
/** 
 * Handle the EEventInteractionRefused event.
 */
void CForm_all_3_0Form::HandleFloatingPointEditor1InteractionRefusedL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement interactionRefused event handler
	}
				
/** 
 * Handle the EEventPrepareFocusTransition event.
 */
void CForm_all_3_0Form::HandleFloatingPointEditor1PrepareFocusTransitionL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement prepareFocusTransition event handler
	}
				
/** 
 * Handle the EEventFormatChanged event.
 */
void CForm_all_3_0Form::HandleFloatingPointEditor1FormatChangedL( 
		CEikEdwin* /* anEdwin */, 
		TEdwinEvent /* anEventType */ )
	{
	// TODO: implement formatChanged event handler
	}
				
/** 
 * Handle the EEventNavigation event.
 */
void CForm_all_3_0Form::HandleFloatingPointEditor1NavigationL( 
		CEikEdwin* /* anEdwin */, 
		TEdwinEvent /* anEventType */ )
	{
	// TODO: implement navigation event handler
	}
				
/** 
 * Handle the EEventTextUpdate event.
 */
void CForm_all_3_0Form::HandleFloatingPointEditor1TextUpdatedL( 
		CEikEdwin* /* anEdwin */, 
		TEdwinEvent /* anEventType */ )
	{
	// TODO: implement textUpdated (FEP only) event handler
	}
				
/** 
 * Handle the EEventStateChanged event.
 */
void CForm_all_3_0Form::HandleNumEditor1StateChangedL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement stateChanged event handler
	}
				
/** 
 * Handle the EEventRequestFocus event.
 */
void CForm_all_3_0Form::HandleNumEditor1RequestingFocusL( CCoeControl* /* aControl */, TCoeEvent /* anEvent */ )
	{
	// TODO: implement requestingFocus event handler
	}
				
/** 
 * Handle the EEventRequestExit event.
 */
void CForm_all_3_0Form::HandleNumEditor1RequestingExitL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement requestingExit event handler
	}
				
/** 
 * Handle the EEventRequestCancel event.
 */
void CForm_all_3_0Form::HandleNumEditor1RequestingCancelL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement requestingCancel event handler
	}
				
/** 
 * Handle the EEventInteractionRefused event.
 */
void CForm_all_3_0Form::HandleNumEditor1InteractionRefusedL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement interactionRefused event handler
	}
				
/** 
 * Handle the EEventPrepareFocusTransition event.
 */
void CForm_all_3_0Form::HandleNumEditor1PrepareFocusTransitionL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement prepareFocusTransition event handler
	}
				
/** 
 * Handle the EEventStateChanged event.
 */
void CForm_all_3_0Form::HandleNumSecret1StateChangedL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement stateChanged event handler
	}
				
/** 
 * Handle the EEventRequestFocus event.
 */
void CForm_all_3_0Form::HandleNumSecret1RequestingFocusL( CCoeControl* /* aControl */, TCoeEvent /* anEvent */ )
	{
	// TODO: implement requestingFocus event handler
	}
				
/** 
 * Handle the EEventRequestExit event.
 */
void CForm_all_3_0Form::HandleNumSecret1RequestingExitL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement requestingExit event handler
	}
				
/** 
 * Handle the EEventRequestCancel event.
 */
void CForm_all_3_0Form::HandleNumSecret1RequestingCancelL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement requestingCancel event handler
	}
				
/** 
 * Handle the EEventInteractionRefused event.
 */
void CForm_all_3_0Form::HandleNumSecret1InteractionRefusedL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement interactionRefused event handler
	}
				
/** 
 * Handle the EEventPrepareFocusTransition event.
 */
void CForm_all_3_0Form::HandleNumSecret1PrepareFocusTransitionL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement prepareFocusTransition event handler
	}
				
/** 
 * Handle the EEventStateChanged event.
 */
void CForm_all_3_0Form::HandleRangeEditor1StateChangedL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement stateChanged event handler
	}
				
/** 
 * Handle the EEventRequestFocus event.
 */
void CForm_all_3_0Form::HandleRangeEditor1RequestingFocusL( CCoeControl* /* aControl */, TCoeEvent /* anEvent */ )
	{
	// TODO: implement requestingFocus event handler
	}
				
/** 
 * Handle the EEventRequestExit event.
 */
void CForm_all_3_0Form::HandleRangeEditor1RequestingExitL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement requestingExit event handler
	}
				
/** 
 * Handle the EEventRequestCancel event.
 */
void CForm_all_3_0Form::HandleRangeEditor1RequestingCancelL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement requestingCancel event handler
	}
				
/** 
 * Handle the EEventInteractionRefused event.
 */
void CForm_all_3_0Form::HandleRangeEditor1InteractionRefusedL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement interactionRefused event handler
	}
				
/** 
 * Handle the EEventPrepareFocusTransition event.
 */
void CForm_all_3_0Form::HandleRangeEditor1PrepareFocusTransitionL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement prepareFocusTransition event handler
	}
				
/** 
 * Handle the EEventStateChanged event.
 */
void CForm_all_3_0Form::HandleSecret1StateChangedL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement stateChanged event handler
	}
				
/** 
 * Handle the EEventRequestFocus event.
 */
void CForm_all_3_0Form::HandleSecret1RequestingFocusL( CCoeControl* /* aControl */, TCoeEvent /* anEvent */ )
	{
	// TODO: implement requestingFocus event handler
	}
				
/** 
 * Handle the EEventRequestExit event.
 */
void CForm_all_3_0Form::HandleSecret1RequestingExitL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement requestingExit event handler
	}
				
/** 
 * Handle the EEventRequestCancel event.
 */
void CForm_all_3_0Form::HandleSecret1RequestingCancelL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement requestingCancel event handler
	}
				
/** 
 * Handle the EEventInteractionRefused event.
 */
void CForm_all_3_0Form::HandleSecret1InteractionRefusedL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement interactionRefused event handler
	}
				
/** 
 * Handle the EEventPrepareFocusTransition event.
 */
void CForm_all_3_0Form::HandleSecret1PrepareFocusTransitionL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement prepareFocusTransition event handler
	}
				
/** 
 * Handle the EEventStateChanged event.
 */
void CForm_all_3_0Form::HandleEdit1StateChangedL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement stateChanged event handler
	}
				
/** 
 * Handle the EEventRequestFocus event.
 */
void CForm_all_3_0Form::HandleEdit1RequestingFocusL( CCoeControl* /* aControl */, TCoeEvent /* anEvent */ )
	{
	// TODO: implement requestingFocus event handler
	}
				
/** 
 * Handle the EEventRequestExit event.
 */
void CForm_all_3_0Form::HandleEdit1RequestingExitL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement requestingExit event handler
	}
				
/** 
 * Handle the EEventRequestCancel event.
 */
void CForm_all_3_0Form::HandleEdit1RequestingCancelL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement requestingCancel event handler
	}
				
/** 
 * Handle the EEventInteractionRefused event.
 */
void CForm_all_3_0Form::HandleEdit1InteractionRefusedL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement interactionRefused event handler
	}
				
/** 
 * Handle the EEventPrepareFocusTransition event.
 */
void CForm_all_3_0Form::HandleEdit1PrepareFocusTransitionL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement prepareFocusTransition event handler
	}
				
/** 
 * Handle the EEventFormatChanged event.
 */
void CForm_all_3_0Form::HandleEdit1FormatChangedL( 
		CEikEdwin* /* anEdwin */, 
		TEdwinEvent /* anEventType */ )
	{
	// TODO: implement formatChanged event handler
	}
				
/** 
 * Handle the EEventNavigation event.
 */
void CForm_all_3_0Form::HandleEdit1NavigationL( 
		CEikEdwin* /* anEdwin */, 
		TEdwinEvent /* anEventType */ )
	{
	// TODO: implement navigation event handler
	}
				
/** 
 * Handle the EEventTextUpdate event.
 */
void CForm_all_3_0Form::HandleEdit1TextUpdatedL( 
		CEikEdwin* /* anEdwin */, 
		TEdwinEvent /* anEventType */ )
	{
	// TODO: implement textUpdated (FEP only) event handler
	}
				
/** 
 * Handle the EEventStateChanged event.
 */
void CForm_all_3_0Form::HandleTimeEditor1StateChangedL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement stateChanged event handler
	}
				
/** 
 * Handle the EEventRequestFocus event.
 */
void CForm_all_3_0Form::HandleTimeEditor1RequestingFocusL( CCoeControl* /* aControl */, TCoeEvent /* anEvent */ )
	{
	// TODO: implement requestingFocus event handler
	}
				
/** 
 * Handle the EEventRequestExit event.
 */
void CForm_all_3_0Form::HandleTimeEditor1RequestingExitL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement requestingExit event handler
	}
				
/** 
 * Handle the EEventRequestCancel event.
 */
void CForm_all_3_0Form::HandleTimeEditor1RequestingCancelL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement requestingCancel event handler
	}
				
/** 
 * Handle the EEventInteractionRefused event.
 */
void CForm_all_3_0Form::HandleTimeEditor1InteractionRefusedL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement interactionRefused event handler
	}
				
/** 
 * Handle the EEventPrepareFocusTransition event.
 */
void CForm_all_3_0Form::HandleTimeEditor1PrepareFocusTransitionL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement prepareFocusTransition event handler
	}
				
/** 
 * Handle the EEventStateChanged event.
 */
void CForm_all_3_0Form::HandleTimeOffsetEditor1StateChangedL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement stateChanged event handler
	}
				
/** 
 * Handle the EEventRequestFocus event.
 */
void CForm_all_3_0Form::HandleTimeOffsetEditor1RequestingFocusL( CCoeControl* /* aControl */, TCoeEvent /* anEvent */ )
	{
	// TODO: implement requestingFocus event handler
	}
				
/** 
 * Handle the EEventRequestExit event.
 */
void CForm_all_3_0Form::HandleTimeOffsetEditor1RequestingExitL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement requestingExit event handler
	}
				
/** 
 * Handle the EEventRequestCancel event.
 */
void CForm_all_3_0Form::HandleTimeOffsetEditor1RequestingCancelL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement requestingCancel event handler
	}
				
/** 
 * Handle the EEventInteractionRefused event.
 */
void CForm_all_3_0Form::HandleTimeOffsetEditor1InteractionRefusedL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement interactionRefused event handler
	}
				
/** 
 * Handle the EEventPrepareFocusTransition event.
 */
void CForm_all_3_0Form::HandleTimeOffsetEditor1PrepareFocusTransitionL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement prepareFocusTransition event handler
	}
				
/** 
 * Handle the EEventStateChanged event.
 */
void CForm_all_3_0Form::HandleTimeAndDateEditor1StateChangedL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement stateChanged event handler
	}
				
/** 
 * Handle the EEventRequestFocus event.
 */
void CForm_all_3_0Form::HandleTimeAndDateEditor1RequestingFocusL( CCoeControl* /* aControl */, TCoeEvent /* anEvent */ )
	{
	// TODO: implement requestingFocus event handler
	}
				
/** 
 * Handle the EEventRequestExit event.
 */
void CForm_all_3_0Form::HandleTimeAndDateEditor1RequestingExitL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement requestingExit event handler
	}
				
/** 
 * Handle the EEventRequestCancel event.
 */
void CForm_all_3_0Form::HandleTimeAndDateEditor1RequestingCancelL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement requestingCancel event handler
	}
				
/** 
 * Handle the EEventInteractionRefused event.
 */
void CForm_all_3_0Form::HandleTimeAndDateEditor1InteractionRefusedL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement interactionRefused event handler
	}
				
/** 
 * Handle the EEventPrepareFocusTransition event.
 */
void CForm_all_3_0Form::HandleTimeAndDateEditor1PrepareFocusTransitionL( 
		CCoeControl* /* aControl */, 
		TCoeEvent /* anEvent */ )
	{
	// TODO: implement prepareFocusTransition event handler
	}
				
