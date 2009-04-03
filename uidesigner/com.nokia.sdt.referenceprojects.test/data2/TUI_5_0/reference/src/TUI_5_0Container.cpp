// [[[ begin generated region: do not modify [Generated System Includes]
#include <aknviewappui.h>
#include <eikappui.h>
#include <TUI_5_0.rsg>
// ]]] end generated region [Generated System Includes]

// [[[ begin generated region: do not modify [Generated User Includes]
#include "TUI_5_0Container.h"
#include "TUI_5_0ContainerView.h"
#include "TUI_5_0.hrh"
#include "TUI_5_0Container.hrh"
// ]]] end generated region [Generated User Includes]

// [[[ begin generated region: do not modify [Generated Constants]
// ]]] end generated region [Generated Constants]

/**
 * First phase of Symbian two-phase construction. Should not 
 * contain any code that could leave.
 */
CTUI_5_0Container::CTUI_5_0Container()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	iChoiceList1 = NULL;
	iButton3 = NULL;
	// ]]] end generated region [Generated Contents]
	
	}
/** 
 * Destroy child controls.
 */
CTUI_5_0Container::~CTUI_5_0Container()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	delete iChoiceList1;
	iChoiceList1 = NULL;
		
	delete iButton3;
	iButton3 = NULL;
	// ]]] end generated region [Generated Contents]
	
	}
				
/**
 * Construct the control (first phase).
 *  Creates an instance and initializes it.
 *  Instance is not left on cleanup stack.
 * @param aRect bounding rectangle
 * @param aParent owning parent, or NULL
 * @param aCommandObserver command observer
 * @return initialized instance of CTUI_5_0Container
 */
CTUI_5_0Container* CTUI_5_0Container::NewL( 
		const TRect& aRect, 
		const CCoeControl* aParent, 
		MEikCommandObserver* aCommandObserver )
	{
	CTUI_5_0Container* self = CTUI_5_0Container::NewLC( 
			aRect, 
			aParent, 
			aCommandObserver );
	CleanupStack::Pop( self );
	return self;
	}

/**
 * Construct the control (first phase).
 *  Creates an instance and initializes it.
 *  Instance is left on cleanup stack.
 * @param aRect The rectangle for this window
 * @param aParent owning parent, or NULL
 * @param aCommandObserver command observer
 * @return new instance of CTUI_5_0Container
 */
CTUI_5_0Container* CTUI_5_0Container::NewLC( 
		const TRect& aRect, 
		const CCoeControl* aParent, 
		MEikCommandObserver* aCommandObserver )
	{
	CTUI_5_0Container* self = new ( ELeave ) CTUI_5_0Container();
	CleanupStack::PushL( self );
	self->ConstructL( aRect, aParent, aCommandObserver );
	return self;
	}
			
/**
 * Construct the control (second phase).
 *  Creates a window to contain the controls and activates it.
 * @param aRect bounding rectangle
 * @param aCommandObserver command observer
 * @param aParent owning parent, or NULL
 */ 
void CTUI_5_0Container::ConstructL( 
		const TRect& aRect, 
		const CCoeControl* aParent, 
		MEikCommandObserver* aCommandObserver )
	{
	if ( aParent == NULL )
	    {
		CreateWindowL();
	    }
	else
	    {
	    SetContainerWindowL( *aParent );
	    }
	iFocusControl = NULL;
	iCommandObserver = aCommandObserver;
	InitializeControlsL();
	SetRect( aRect );
	ActivateL();
	// [[[ begin generated region: do not modify [Post-ActivateL initializations]
	// ]]] end generated region [Post-ActivateL initializations]
	
	}
			
/**
* Return the number of controls in the container (override)
* @return count
*/
TInt CTUI_5_0Container::CountComponentControls() const
	{
	return ( int ) ELastControl;
	}
				
/**
* Get the control with the given index (override)
* @param aIndex Control index [0...n) (limited by #CountComponentControls)
* @return Pointer to control
*/
CCoeControl* CTUI_5_0Container::ComponentControl( TInt aIndex ) const
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	switch ( aIndex )
		{
		case EChoiceList1:
			return iChoiceList1;
		case EButton3:
			return iButton3;
		}
	// ]]] end generated region [Generated Contents]
	
	// handle any user controls here...
	
	return NULL;
	}
				
/**
 *	Handle resizing of the container. This implementation will lay out
 *  full-sized controls like list boxes for any screen size, and will layout
 *  labels, editors, etc. to the size they were given in the UI designer.
 *  This code will need to be modified to adjust arbitrary controls to
 *  any screen size.
 */				
void CTUI_5_0Container::SizeChanged()
	{
	CCoeControl::SizeChanged();
	LayoutControls();
	// [[[ begin generated region: do not modify [Generated Contents]
			
	// ]]] end generated region [Generated Contents]
	
	}
				
// [[[ begin generated function: do not modify
/**
 * Layout components as specified in the UI Designer
 */
void CTUI_5_0Container::LayoutControls()
	{
	}
// ]]] end generated function

/**
 *	Handle key events.
 */				
TKeyResponse CTUI_5_0Container::OfferKeyEventL( 
		const TKeyEvent& aKeyEvent, 
		TEventCode aType )
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	
	// ]]] end generated region [Generated Contents]
	
	if ( iFocusControl != NULL
		&& iFocusControl->OfferKeyEventL( aKeyEvent, aType ) == EKeyWasConsumed )
		{
		return EKeyWasConsumed;
		}
	return CCoeControl::OfferKeyEventL( aKeyEvent, aType );
	}
				
// [[[ begin generated function: do not modify
/**
 *	Initialize each control upon creation.
 */				
void CTUI_5_0Container::InitializeControlsL()
	{
	// Set up iChoiceList1
	CDesCArray* choiceList1Array = new ( ELeave )CDesC16ArrayFlat( 1 );
	
	iChoiceList1 = CAknChoiceList::NewL( this, choiceList1Array ); // transfers ownership
	iChoiceList1->SetFlags( CAknChoiceList::EAknChoiceListWithCurrentSelection | 
									 CAknChoiceList::EAknChoiceListPositionBottom );
			
	iChoiceList1->SetContainerWindowL( *this );
	iChoiceList1->SetObserver( ( MCoeControlObserver* ) iCommandObserver );
	iChoiceList1->SetRect ( TRect ( 
								TPoint ( 21, 6 ) , 
								TSize ( 177, 24 ) )  
								 ) ; 
	iChoiceList1->SetItemsL( R_TUI_5_0_CONTAINER_CHOICE_LIST1 ); // read array from resources
	iChoiceList1->SetSelectedIndex( 0 );
	
	
	iButton3 = static_cast< CAknButton* >
					( EikControlFactory::CreateByTypeL( EAknCtButton ).iControl );
	iButton3->ConstructFromResourceL ( R_TUI_5_0_CONTAINER_BUTTON3 );
	
	iButton3->SetContainerWindowL ( *this );
	iButton3->SetRect ( TRect ( 
								TPoint ( 26, 45 ) , 
								TSize ( 91, 33 ) )  
								 ) ; 
	iButton3->SetObserver( ( MCoeControlObserver* ) iCommandObserver ); 
	
	
	
	
	
	
	
	}
// ]]] end generated function

/** 
 * Handle global resource changes, such as scalable UI or skin events (override)
 */
void CTUI_5_0Container::HandleResourceChange( TInt aType )
	{
	CCoeControl::HandleResourceChange( aType );
	SetRect( iAvkonViewAppUi->View( TUid::Uid( ETUI_5_0ContainerViewId ) )->ClientRect() );
	// [[[ begin generated region: do not modify [Generated Contents]
	// ]]] end generated region [Generated Contents]
	
	}
				
/**
 *	Draw container contents.
 */				
void CTUI_5_0Container::Draw( const TRect& aRect ) const
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	CWindowGc& gc = SystemGc();
	gc.Clear( aRect );
	
	// ]]] end generated region [Generated Contents]
	
	}
				
