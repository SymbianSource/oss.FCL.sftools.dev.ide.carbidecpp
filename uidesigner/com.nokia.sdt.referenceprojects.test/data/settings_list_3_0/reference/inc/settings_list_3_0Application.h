#ifndef SETTINGS_LIST_3_0APPLICATION_H
#define SETTINGS_LIST_3_0APPLICATION_H

// [[[ begin generated region: do not modify [Generated Includes]
#include <aknapp.h>
// ]]] end generated region [Generated Includes]

// [[[ begin generated region: do not modify [Generated Constants]
const TUid KUidsettings_list_3_0Application = { 0xEEF51C45 };
// ]]] end generated region [Generated Constants]

/**
 *
 * @class	Csettings_list_3_0Application settings_list_3_0Application.h
 * @brief	A CAknApplication-derived class is required by the S60 application 
 *          framework. It is subclassed to create the application's document 
 *          object.
 */
class Csettings_list_3_0Application : public CAknApplication
	{
private:
	TUid AppDllUid() const;
	CApaDocument* CreateDocumentL();
	
	};
			
#endif // SETTINGS_LIST_3_0APPLICATION_H		
