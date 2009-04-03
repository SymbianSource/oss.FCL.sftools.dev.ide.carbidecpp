// [[[ begin generated region: do not modify [Generated System Includes]
#include <eikcmobs.h>
#include <aknpreviewpopupcontroller.h>
#include <TUI_5_0.rsg>
#include <barsread.h>
#include <stringloader.h>
#include <eiklabel.h>
#include <eikenv.h>
// ]]] end generated region [Generated System Includes]

// [[[ begin generated region: do not modify [Generated User Includes]
#include "PreviewPopUp1.h"
// ]]] end generated region [Generated User Includes]

// [[[ begin generated region: do not modify [Generated Constants]
// ]]] end generated region [Generated Constants]

/**
 * First phase of Symbian two-phase construction. Should not 
 * contain any code that could leave.
 */
CPreviewPopUp1::CPreviewPopUp1()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	iLabel1 = NULL;
	// ]]] end generated region [Generated Contents]
	
	}

/** 
 * Destroy child controls.
 */
CPreviewPopUp1::~CPreviewPopUp1()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	delete iLabel1;
	iLabel1 = NULL;
	// ]]] end generated region [Generated Contents]
	
	}

/**
 * Construct the control (first phase).
 *  Creates an instance and initializes it.
 *  Instance is not left on cleanup stack.
 * @return initialized instance of CPreviewPopUp1
 */
CPreviewPopUp1* CPreviewPopUp1::NewL( MEikCommandObserver* aCommandObserver )
	{
	CPreviewPopUp1* self = CPreviewPopUp1::NewLC( aCommandObserver );
	CleanupStack::Pop( self );
	return self;
	}

/**
 * Construct the control (first phase).
 *  Creates an instance and initializes it.
 *  Instance is left on cleanup stack.
 * @return new instance of CPreviewPopUp1
 */
CPreviewPopUp1* CPreviewPopUp1::NewLC( MEikCommandObserver* aCommandObserver )
	{
	CPreviewPopUp1* self = new ( ELeave ) CPreviewPopUp1();
	CleanupStack::PushL( self );
	self->ConstructL( aCommandObserver );
	return self;
	}

/**
 * Construct the control (second phase).
 *  Creates a window to contain the controls and activates it.
 */ 
void CPreviewPopUp1::ConstructL( MEikCommandObserver* aCommandObserver )
	{
	iCommandObserver = aCommandObserver;
	CreateWindowL();
	InitializeControlsL();
	ActivateL();
	
	// [[[ begin generated region: do not modify [Generated Contents]
	// ]]] end generated region [Generated Contents]
	
	}

/**
* Return the number of controls in the container (override)
* @return count
*/
TInt CPreviewPopUp1::CountComponentControls() const
	{
	return ( int ) ELastControl;
	}

/**
* Get the control with the given index (override)
* @param aIndex Control index [0...n) (limited by #CountComponentControls)
* @return Pointer to control
*/
CCoeControl* CPreviewPopUp1::ComponentControl( TInt aIndex ) const
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	switch ( aIndex )
		{
		case ELabel1:
			return iLabel1;
		}
	// ]]] end generated region [Generated Contents]
	
	// handle any user controls here...
	
	return NULL;
	
	}

/**
* When asynchronous, called by the preview popup controller when 
* content building should be started.
*/
void CPreviewPopUp1::StartContentBuildingL()
	{
	iController->ContentReady();
	}

/**
* When asynchronous, called by the preview popup controller if ongoing
* content building operation should be cancelled.
*/
void CPreviewPopUp1::CancelContentBuilding()
	{
	}

/**
* Set the pop-up controller member.
*/
void CPreviewPopUp1::SetPopUpController( CAknPreviewPopUpController* aController )
	{
	iController = aController;
	}

/**
* Return the size of the pop-up.
* Modify this method to dynamically size the pop-up.
*/
TSize CPreviewPopUp1::MinimumSize()
	{
	TSize size;
	
	// [[[ begin generated region: do not modify [Generated Contents]
	size = TSize(117, 44);			
	// ]]] end generated region [Generated Contents]
	
	return size;
	
	}

/**
 *	Handle resizing of the container. This implementation will lay out
 *  labels, images, etc. to the size they were given in the UI designer.
 *  This code will need to be modified to adjust arbitrary controls to
 *  any screen size.
 */				
void CPreviewPopUp1::SizeChanged()
	{
	CCoeControl::SizeChanged();
	LayoutControls();
	
	// [[[ begin generated region: do not modify [Generated Contents]
	// ]]] end generated region [Generated Contents]
	
	}

/**
 *	Draw container contents.
 */				
void CPreviewPopUp1::Draw( const TRect& aRect ) const
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	CWindowGc& gc = SystemGc();
	gc.Clear( aRect );
	
	// ]]] end generated region [Generated Contents]
	
	}

// [[[ begin generated function: do not modify
/**
 *	Initialize each control upon creation.
 */				
void CPreviewPopUp1::InitializeControlsL()
	{
	iLabel1 = new ( ELeave ) CEikLabel;
	iLabel1->SetContainerWindowL( *this );
		{
		TResourceReader reader;
		iEikonEnv->CreateResourceReaderLC( reader, R_TUI_5_0_CONTAINER_LABEL1 );
		iLabel1->ConstructFromResourceL( reader );
		CleanupStack::PopAndDestroy(); // reader internal state
		}
	}

// ]]] end generated function

// [[[ begin generated function: do not modify
/**
 * Layout components as specified in the UI Designer
 */
void CPreviewPopUp1::LayoutControls()
	{
	iLabel1->SetExtent( TPoint( 20, 45 ), TSize( 93, 29 ) );
	}

// ]]] end generated function


