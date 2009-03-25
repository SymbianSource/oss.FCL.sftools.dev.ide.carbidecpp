#ifndef TUI_5_0APPLICATION_H
#define TUI_5_0APPLICATION_H

// [[[ begin generated region: do not modify [Generated Includes]
#include <aknapp.h>
// ]]] end generated region [Generated Includes]

// [[[ begin generated region: do not modify [Generated Constants]
const TUid KUidTUI_5_0Application = { 0xE03C1A0F };
// ]]] end generated region [Generated Constants]

/**
 *
 * @class	CTUI_5_0Application TUI_5_0Application.h
 * @brief	A CAknApplication-derived class is required by the S60 application 
 *          framework. It is subclassed to create the application's document 
 *          object.
 */
class CTUI_5_0Application : public CAknApplication
	{
private:
	TUid AppDllUid() const;
	CApaDocument* CreateDocumentL();
	
	};
			
#endif // TUI_5_0APPLICATION_H		
