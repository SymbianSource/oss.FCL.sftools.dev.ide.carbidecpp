// [[[ begin generated region: do not modify [Generated System Includes]
#include <eikstart.h>			
// ]]] end generated region [Generated System Includes]

// [[[ begin generated region: do not modify [Generated User Includes]
#include "UIQ_MultiPage_3_0ApplicationExternalInterface.h"			

#include "UIQ_MultiPage_3_0Application.h"
#include "UIQ_MultiPage_3_0Document.h"
// ]]] end generated region [Generated User Includes]

// [[[ begin generated region: do not modify [Generated Constants]
// ]]] end generated region [Generated Constants]


/**
 * The function is called by the UI framework to ask for the
 * application's UID. The returned value is defined by the
 * constant KUidCUIQ_MultiPage_3_0ApplicationApp and must match the second value
 * defined in the project definition file.
 *
 * @return Application's UID
 */
TUid CUIQ_MultiPage_3_0Application::AppDllUid() const
	{
	return KUidUIQ_MultiPage_3_0App;        
	}


/**
 * This function is called by the UI framework at application start-up.
 *
 * @return Instance of the document class.
 */				
CApaDocument* CUIQ_MultiPage_3_0Application::CreateDocumentL()
	{
	return CUIQ_MultiPage_3_0Document::NewL(*this);
	}


/**
 * The function is called by the framework immediately after it has started the
 * application's EXE. It is called by the framework and is expected to have
 * exactly this prototype.
 *
 * @return Instance of the application class.
 */
				
CApaApplication* CUIQ_MultiPage_3_0Application::NewApplication()
	{
	return new CUIQ_MultiPage_3_0Application();
	}


/**
 * E32Main() contains the program's start up code, the entry point for an EXE.
 */
GLDEF_C TInt E32Main()
	{
	return EikStart::RunApplication(CUIQ_MultiPage_3_0Application::NewApplication);
	}		

