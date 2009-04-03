#ifndef UIQ_MULTIPAGE_3_0DOCUMENT_H
#define UIQ_MULTIPAGE_3_0DOCUMENT_H

// [[[ begin generated region: do not modify [Generated System Includes]
#include <QikDocument.h>
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
 * This class represents the document in UIQ_MultiPage_3_0 application,
 * For an UIQ application to work properly the document class need to be
 * derived from CQikDocument.
 */      
			
class CUIQ_MultiPage_3_0Document : public CQikDocument
	// [[[ begin generated region: do not modify [Generated Content]
	// ]]] end generated region [Generated Content]
	
	{
	
	
	// [[[ begin [Public Section]
public:
	
	// [[[ begin generated region: do not modify [Generated Types]
	// ]]] end generated region [Generated Types]
	
	// [[[ begin generated region: do not modify [Generated Methods]
	// ]]] end generated region [Generated Methods]
	
	static CUIQ_MultiPage_3_0Document* NewL( CQikApplication& aApp );
	~CUIQ_MultiPage_3_0Document();
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
	
	CUIQ_MultiPage_3_0Document( CQikApplication& aApp );
	void ConstructL();
	CEikAppUi* CreateAppUiL();
	// ]]] end [Private Section]
	
	};

#endif // UIQ_MULTIPAGE_3_0DOCUMENT_H
