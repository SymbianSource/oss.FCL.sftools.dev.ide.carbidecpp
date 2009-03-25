// [[[ begin generated region: do not modify [Generated User Includes]
#include "lists_3_0Document.h"
#include "lists_3_0AppUi.h"
// ]]] end generated region [Generated User Includes]

/**
 * @brief Constructs the document class for the application.
 * @param anApplication the application instance
 */
Clists_3_0Document::Clists_3_0Document( CEikApplication& anApplication )
	: CAknDocument( anApplication )
	{
	}

/**
 * @brief Completes the second phase of Symbian object construction. 
 * Put initialization code that could leave here.  
 */ 
void Clists_3_0Document::ConstructL()
	{
	}
	
/**
 * Symbian OS two-phase constructor.
 *
 * Creates an instance of Clists_3_0Document, constructs it, and
 * returns it.
 *
 * @param aApp the application instance
 * @return the new Clists_3_0Document
 */
Clists_3_0Document* Clists_3_0Document::NewL( CEikApplication& aApp )
	{
	Clists_3_0Document* self = new ( ELeave ) Clists_3_0Document( aApp );
	CleanupStack::PushL( self );
	self->ConstructL();
	CleanupStack::Pop( self );
	return self;
	}

/**
 * @brief Creates the application UI object for this document.
 * @return the new instance
 */	
CEikAppUi* Clists_3_0Document::CreateAppUiL()
	{
	return new ( ELeave ) Clists_3_0AppUi;
	}
				
