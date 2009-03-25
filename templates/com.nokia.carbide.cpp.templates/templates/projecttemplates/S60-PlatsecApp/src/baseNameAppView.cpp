/*
============================================================================
 Name		: $(baseName)AppView.cpp
 Author	  : $(author)
 Copyright   : $(copyright)
 Description : Application view implementation
============================================================================
*/

// INCLUDE FILES
#include <coemain.h>
#include "$(baseName)AppView.h"

// ============================ MEMBER FUNCTIONS ===============================

// -----------------------------------------------------------------------------
// C$(baseName)AppView::NewL()
// Two-phased constructor.
// -----------------------------------------------------------------------------
//
C$(baseName)AppView* C$(baseName)AppView::NewL( const TRect& aRect )
	{
	C$(baseName)AppView* self = C$(baseName)AppView::NewLC( aRect );
	CleanupStack::Pop( self );
	return self;
	}

// -----------------------------------------------------------------------------
// C$(baseName)AppView::NewLC()
// Two-phased constructor.
// -----------------------------------------------------------------------------
//
C$(baseName)AppView* C$(baseName)AppView::NewLC( const TRect& aRect )
	{
	C$(baseName)AppView* self = new ( ELeave ) C$(baseName)AppView;
	CleanupStack::PushL( self );
	self->ConstructL( aRect );
	return self;
	}

// -----------------------------------------------------------------------------
// C$(baseName)AppView::ConstructL()
// Symbian 2nd phase constructor can leave.
// -----------------------------------------------------------------------------
//
void C$(baseName)AppView::ConstructL( const TRect& aRect )
	{
	// Create a window for this application view
	CreateWindowL();

	// Set the windows size
	SetRect( aRect );

	// Activate the window, which makes it ready to be drawn
	ActivateL();
	}

// -----------------------------------------------------------------------------
// C$(baseName)AppView::C$(baseName)AppView()
// C++ default constructor can NOT contain any code, that might leave.
// -----------------------------------------------------------------------------
//
C$(baseName)AppView::C$(baseName)AppView()
	{
	// No implementation required
	}


// -----------------------------------------------------------------------------
// C$(baseName)AppView::~C$(baseName)AppView()
// Destructor.
// -----------------------------------------------------------------------------
//
C$(baseName)AppView::~C$(baseName)AppView()
	{
	// No implementation required
	}


// -----------------------------------------------------------------------------
// C$(baseName)AppView::Draw()
// Draws the display.
// -----------------------------------------------------------------------------
//
void C$(baseName)AppView::Draw( const TRect& /*aRect*/ ) const
	{
	// Get the standard graphics context
	CWindowGc& gc = SystemGc();

	// Gets the control's extent
	TRect drawRect( Rect());

	// Clears the screen
	gc.Clear( drawRect );
	
	}

// -----------------------------------------------------------------------------
// C$(baseName)AppView::SizeChanged()
// Called by framework when the view size is changed.
// -----------------------------------------------------------------------------
//
void C$(baseName)AppView::SizeChanged()
	{  
	DrawNow();
	}
// End of File
