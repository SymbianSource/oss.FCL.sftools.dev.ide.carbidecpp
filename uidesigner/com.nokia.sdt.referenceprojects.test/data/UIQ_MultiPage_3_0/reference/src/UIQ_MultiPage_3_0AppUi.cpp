// [[[ begin generated region: do not modify [Generated System Includes]
// ]]] end generated region [Generated System Includes]

// [[[ begin generated region: do not modify [Generated User Includes]
#include "UIQ_MultiPage_3_0AppUi.h"
#include "UIQ_MultiPage_3_0ApplicationExternalInterface.h"
#include "UIQ_MultiPage_3_0MultiPageView.h"
// ]]] end generated region [Generated User Includes]

// [[[ begin generated region: do not modify [Generated Constants]
// ]]] end generated region [Generated Constants]


/**
 * 2nd stage construction of the App UI.
 * Create view and add it to the framework.
 * The framework will take over the ownership.
 */				
void CUIQ_MultiPage_3_0AppUi::ConstructL()
	{
	// Calls ConstructL that initiate the standard values.
	CQikAppUi::ConstructL();
	
	
	// [[[ begin generated region: do not modify [Generated Contents]
	// Create the multi page view and add it to the framework as the parent view.
	// The parent view is the logical view that is normally activated when a go
	// back command is issued. KNullViewId will activate the system default view. 
	TVwsViewId parentView( KUidUIQ_MultiPage_3_0App, KUidUIQ_MultiPage_3_0MultiPageView );
	CUIQ_MultiPage_3_0MultiPageView* UIQ_MultiPage_3_0MultiPageView = CUIQ_MultiPage_3_0MultiPageView::NewLC(
		*this,
		KNullViewId );
	AddViewL( *UIQ_MultiPage_3_0MultiPageView );
	CleanupStack::Pop( UIQ_MultiPage_3_0MultiPageView );
	// ]]] end generated region [Generated Contents]
	
	}


/**
 * C++ constructor
 */				
CUIQ_MultiPage_3_0AppUi::CUIQ_MultiPage_3_0AppUi()
	{
	
	}


/**
 * C++ destructor
 */				
CUIQ_MultiPage_3_0AppUi::~CUIQ_MultiPage_3_0AppUi()
	{
	        
	}


