// [[[ begin generated region: do not modify [Generated User Includes]
#include "TUI_5_0Document.h"
#include "TUI_5_0AppUi.h"
// ]]] end generated region [Generated User Includes]

/**
 * @brief Constructs the document class for the application.
 * @param anApplication the application instance
 */
CTUI_5_0Document::CTUI_5_0Document( CEikApplication& anApplication )
	: CAknDocument( anApplication )
	{
	}

/**
 * @brief Completes the second phase of Symbian object construction. 
 * Put initialization code that could leave here.  
 */ 
void CTUI_5_0Document::ConstructL()
	{
	}
	
/**
 * Symbian OS two-phase constructor.
 *
 * Creates an instance of CTUI_5_0Document, constructs it, and
 * returns it.
 *
 * @param aApp the application instance
 * @return the new CTUI_5_0Document
 */
CTUI_5_0Document* CTUI_5_0Document::NewL( CEikApplication& aApp )
	{
	CTUI_5_0Document* self = new ( ELeave ) CTUI_5_0Document( aApp );
	CleanupStack::PushL( self );
	self->ConstructL();
	CleanupStack::Pop( self );
	return self;
	}

/**
 * @brief Creates the application UI object for this document.
 * @return the new instance
 */	
CEikAppUi* CTUI_5_0Document::CreateAppUiL()
	{
	return new ( ELeave ) CTUI_5_0AppUi;
	}
				
