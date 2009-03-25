#ifndef UIQ_MULTIPAGE_3_0APPLICATION_H
#define UIQ_MULTIPAGE_3_0APPLICATION_H

// [[[ begin generated region: do not modify [Generated System Includes]
#include <QikApplication.h>
// ]]] end generated region [Generated System Includes]


// [[[ begin [Event Handler Includes]
// ]]] end [Event Handler Includes]

// [[[ begin generated region: do not modify [Generated User Includes]
// ]]] end generated region [Generated User Includes]

// [[[ begin generated region: do not modify [Generated Typedefs]
// ]]] end generated region [Generated Typedefs]

// [[[ begin generated region: do not modify [Generated Constants]
// ]]] end generated region [Generated Constants]

// [[[ begin generated region: do not modify [Generated Forward Declarations]
// ]]] end generated region [Generated Forward Declarations]



/**
 * This class is the entry point to the application.
 */
      
			
class CUIQ_MultiPage_3_0Application : public CQikApplication
	// [[[ begin generated region: do not modify [Generated Content]
	// ]]] end generated region [Generated Content]
	
	{
	
	
	// [[[ begin [Public Section]
public:
	
	// [[[ begin generated region: do not modify [Generated Types]
	// ]]] end generated region [Generated Types]
	
	// [[[ begin generated region: do not modify [Generated Methods]
	// ]]] end generated region [Generated Methods]
	
	static CApaApplication* NewApplication();
	// ]]] end [Public Section]
	
	
	// [[[ begin [Protected Section]
protected:
	
	// [[[ begin generated region: do not modify [Overridden Methods]
	// ]]] end generated region [Overridden Methods]
	
	
	// [[[ begin [User Handlers]
	// ]]] end [User Handlers]
	
	// ]]] end [Protected Section]
	
	
	// [[[ begin [Private Section]
private:
	
	// [[[ begin generated region: do not modify [Generated Types]
	// ]]] end generated region [Generated Types]
	
	// [[[ begin generated region: do not modify [Generated Instance Variables]
	// ]]] end generated region [Generated Instance Variables]
	
	// [[[ begin generated region: do not modify [Generated Owned Methods]
	// ]]] end generated region [Generated Owned Methods]
	
	
	// [[[ begin [Generated Not Owned Methods]
	// ]]] end [Generated Not Owned Methods]
	
	TUid AppDllUid() const;
	CApaDocument* CreateDocumentL();
	// ]]] end [Private Section]
	
	};

#endif // UIQ_MULTIPAGE_3_0APPLICATION_H
