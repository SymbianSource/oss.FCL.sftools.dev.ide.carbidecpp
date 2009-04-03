/*
============================================================================
 Name		: $(baseName)Document.cpp
 Author	  : $(author)
 Copyright   : $(copyright)
 Description : C$(baseName)Document implementation
============================================================================
*/


// INCLUDE FILES
#include "$(baseName)AppUi.h"
#include "$(baseName)Document.h"

// ============================ MEMBER FUNCTIONS ===============================

// -----------------------------------------------------------------------------
// C$(baseName)Document::NewL()
// Two-phased constructor.
// -----------------------------------------------------------------------------
//
C$(baseName)Document* C$(baseName)Document::NewL( CEikApplication& aApp )
	{
	C$(baseName)Document* self = NewLC( aApp );
	CleanupStack::Pop( self );
	return self;
	}

// -----------------------------------------------------------------------------
// C$(baseName)Document::NewLC()
// Two-phased constructor.
// -----------------------------------------------------------------------------
//
C$(baseName)Document* C$(baseName)Document::NewLC( CEikApplication& aApp )
	{
	C$(baseName)Document* self =
		new ( ELeave ) C$(baseName)Document( aApp );

	CleanupStack::PushL( self );
	self->ConstructL();
	return self;
	}

// -----------------------------------------------------------------------------
// C$(baseName)Document::ConstructL()
// Symbian 2nd phase constructor can leave.
// -----------------------------------------------------------------------------
//
void C$(baseName)Document::ConstructL()
	{
	// No implementation required
	}

// -----------------------------------------------------------------------------
// C$(baseName)Document::C$(baseName)Document()
// C++ default constructor can NOT contain any code, that might leave.
// -----------------------------------------------------------------------------
//
C$(baseName)Document::C$(baseName)Document( CEikApplication& aApp )
	: CAknDocument( aApp )
	{
	// No implementation required
	}

// ---------------------------------------------------------------------------
// C$(baseName)Document::~C$(baseName)Document()
// Destructor.
// ---------------------------------------------------------------------------
//
C$(baseName)Document::~C$(baseName)Document()
	{
	// No implementation required
	}

// ---------------------------------------------------------------------------
// C$(baseName)Document::CreateAppUiL()
// Constructs CreateAppUi.
// ---------------------------------------------------------------------------
//
CEikAppUi* C$(baseName)Document::CreateAppUiL()
	{
	// Create the application user interface, and return a pointer to it;
	// the framework takes ownership of this object
	return new ( ELeave )C$(baseName)AppUi;
	}

// End of File
