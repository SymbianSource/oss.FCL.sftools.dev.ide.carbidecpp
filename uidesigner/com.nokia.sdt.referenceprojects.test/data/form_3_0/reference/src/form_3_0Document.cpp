// [[[ begin generated region: do not modify [Generated User Includes]
#include "form_3_0Document.h"
#include "form_3_0AppUi.h"
// ]]] end generated region [Generated User Includes]

/**
 * @brief Constructs the document class for the application.
 * @param anApplication the application instance
 */
Cform_3_0Document::Cform_3_0Document( CEikApplication& anApplication )
	: CAknDocument( anApplication )
	{
	}

/**
 * @brief Completes the second phase of Symbian object construction. 
 * Put initialization code that could leave here.  
 */ 
void Cform_3_0Document::ConstructL()
	{
	}
	
/**
 * Symbian OS two-phase constructor.
 *
 * Creates an instance of Cform_3_0Document, constructs it, and
 * returns it.
 *
 * @param aApp the application instance
 * @return the new Cform_3_0Document
 */
Cform_3_0Document* Cform_3_0Document::NewL( CEikApplication& aApp )
	{
	Cform_3_0Document* self = new ( ELeave ) Cform_3_0Document( aApp );
	CleanupStack::PushL( self );
	self->ConstructL();
	CleanupStack::Pop( self );
	return self;
	}

/**
 * @brief Creates the application UI object for this document.
 * @return the new instance
 */	
CEikAppUi* Cform_3_0Document::CreateAppUiL()
	{
	return new ( ELeave ) Cform_3_0AppUi;
	}
				
