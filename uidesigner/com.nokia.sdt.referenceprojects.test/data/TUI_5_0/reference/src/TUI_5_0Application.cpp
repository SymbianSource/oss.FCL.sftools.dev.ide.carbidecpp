// [[[ begin generated region: do not modify [Generated System Includes]
// ]]] end generated region [Generated System Includes]

// [[[ begin generated region: do not modify [Generated Includes]
#include "TUI_5_0Application.h"
#include "TUI_5_0Document.h"
#ifdef EKA2
#include <eikstart.h>
#endif
// ]]] end generated region [Generated Includes]

/**
 * @brief Returns the application's UID (override from CApaApplication::AppDllUid())
 * @return UID for this application (KUidTUI_5_0Application)
 */
TUid CTUI_5_0Application::AppDllUid() const
	{
	return KUidTUI_5_0Application;
	}

/**
 * @brief Creates the application's document (override from CApaApplication::CreateDocumentL())
 * @return Pointer to the created document object (CTUI_5_0Document)
 */
CApaDocument* CTUI_5_0Application::CreateDocumentL()
	{
	return CTUI_5_0Document::NewL( *this );
	}

#ifdef EKA2

/**
 *	@brief Called by the application framework to construct the application object
 *  @return The application (CTUI_5_0Application)
 */	
LOCAL_C CApaApplication* NewApplication()
	{
	return new CTUI_5_0Application;
	}

/**
* @brief This standard export is the entry point for all Series 60 applications
* @return error code
 */	
GLDEF_C TInt E32Main()
	{
	return EikStart::RunApplication( NewApplication );
	}
	
#else 	// Series 60 2.x main DLL program code

/**
* @brief This standard export constructs the application object.
* @return The application (CTUI_5_0Application)
*/
EXPORT_C CApaApplication* NewApplication()
	{
	return new CTUI_5_0Application;
	}

/**
* @brief This standard export is the entry point for all Series 60 applications
* @return error code
*/
GLDEF_C TInt E32Dll(TDllReason /*reason*/)
	{
	return KErrNone;
	}

#endif // EKA2
