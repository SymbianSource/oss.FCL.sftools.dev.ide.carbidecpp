#ifndef EMPTY_CONTAINER_3_0APPLICATION_H
#define EMPTY_CONTAINER_3_0APPLICATION_H

// [[[ begin generated region: do not modify [Generated Includes]
#include <aknapp.h>
// ]]] end generated region [Generated Includes]

// [[[ begin generated region: do not modify [Generated Constants]
const TUid KUidempty_container_3_0Application = { 0xE481C0E3 };
// ]]] end generated region [Generated Constants]

/**
 *
 * @class	Cempty_container_3_0Application empty_container_3_0Application.h
 * @brief	A CAknApplication-derived class is required by the S60 application 
 *          framework. It is subclassed to create the application's document 
 *          object.
 */
class Cempty_container_3_0Application : public CAknApplication
	{
private:
	TUid AppDllUid() const;
	CApaDocument* CreateDocumentL();
	
	};
			
#endif // EMPTY_CONTAINER_3_0APPLICATION_H		
