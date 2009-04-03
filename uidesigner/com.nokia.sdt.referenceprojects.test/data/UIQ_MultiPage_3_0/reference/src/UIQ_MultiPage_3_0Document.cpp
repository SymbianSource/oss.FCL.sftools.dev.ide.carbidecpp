// [[[ begin generated region: do not modify [Generated System Includes]
#include <s32strm.h>
#include <QikApplication.h>

// ]]] end generated region [Generated System Includes]

// [[[ begin generated region: do not modify [Generated User Includes]
#include "UIQ_MultiPage_3_0AppUi.h" 

#include "UIQ_MultiPage_3_0Document.h"
// ]]] end generated region [Generated User Includes]

// [[[ begin generated region: do not modify [Generated Constants]
// ]]] end generated region [Generated Constants]

		
/**
 * Creates and constructs the document. This is called by
 * CUIQ_MultiPage_3_0DocumentApplication::CreateDocumentL() which 
 * in turn is called by the UI framework.
 *
 * @param aApp Reference to the application
 * @return Instance of the document class.
 */ 
CUIQ_MultiPage_3_0Document* CUIQ_MultiPage_3_0Document::NewL( CQikApplication& aApp )
	{
	CUIQ_MultiPage_3_0Document* self = new (ELeave) CUIQ_MultiPage_3_0Document(aApp);
	CleanupStack::PushL(self);
	self->ConstructL();
	CleanupStack::Pop(self);
	return self;
	}

		
/**
 * The constructor of the document class just passes the
 * supplied reference to the constructor initialization list.
 *
 * @param aApp Reference to the application
 */ 
CUIQ_MultiPage_3_0Document::CUIQ_MultiPage_3_0Document( CQikApplication& aApp )
	: CQikDocument(aApp)
	{
	        
	}

		
/**
 * 2nd stage construction of the model.
 * All code that shall be called in initializing phase and might leave shall be
 * added here.
 */ 
void CUIQ_MultiPage_3_0Document::ConstructL()
	{
	                
	}

		
/**
 * This is called by the UI framework as soon as the document has been created.
 * It creates an instance of the ApplicationUI. The Application UI class is an
 * instance of a CEikAppUi derived class.
 *
 * @return Instance of CEikAppUi
 */
CEikAppUi* CUIQ_MultiPage_3_0Document::CreateAppUiL()
	{
	return new (ELeave) CUIQ_MultiPage_3_0AppUi;
	}

		
/**
 * This is the class destructor
 */
CUIQ_MultiPage_3_0Document::~CUIQ_MultiPage_3_0Document()
	{
	
	}


