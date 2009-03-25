#ifndef FORM_3_0APPLICATION_H
#define FORM_3_0APPLICATION_H

// [[[ begin generated region: do not modify [Generated Includes]
#include <aknapp.h>
// ]]] end generated region [Generated Includes]

// [[[ begin generated region: do not modify [Generated Constants]
const TUid KUidform_3_0Application = { 0xE1BA414D };
// ]]] end generated region [Generated Constants]

/**
 *
 * @class	Cform_3_0Application form_3_0Application.h
 * @brief	A CAknApplication-derived class is required by the S60 application 
 *          framework. It is subclassed to create the application's document 
 *          object.
 */
class Cform_3_0Application : public CAknApplication
	{
private:
	TUid AppDllUid() const;
	CApaDocument* CreateDocumentL();
	
	};
			
#endif // FORM_3_0APPLICATION_H		
