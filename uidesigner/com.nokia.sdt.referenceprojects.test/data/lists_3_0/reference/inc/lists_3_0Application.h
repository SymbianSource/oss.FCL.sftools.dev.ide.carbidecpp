#ifndef LISTS_3_0APPLICATION_H
#define LISTS_3_0APPLICATION_H

// [[[ begin generated region: do not modify [Generated Includes]
#include <aknapp.h>
// ]]] end generated region [Generated Includes]

// [[[ begin generated region: do not modify [Generated Constants]
const TUid KUidlists_3_0Application = { 0xE88ED6FE };
// ]]] end generated region [Generated Constants]

/**
 *
 * @class	Clists_3_0Application lists_3_0Application.h
 * @brief	A CAknApplication-derived class is required by the S60 application 
 *          framework. It is subclassed to create the application's document 
 *          object.
 */
class Clists_3_0Application : public CAknApplication
	{
private:
	TUid AppDllUid() const;
	CApaDocument* CreateDocumentL();
	
	};
			
#endif // LISTS_3_0APPLICATION_H		
