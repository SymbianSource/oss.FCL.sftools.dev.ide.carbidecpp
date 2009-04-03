#ifndef TUI_5_0CONTAINERVIEW_H
#define TUI_5_0CONTAINERVIEW_H

// [[[ begin generated region: do not modify [Generated Includes]
#include <aknview.h>
#include <aknutils.h>
#include <akntoolbar.h>
// ]]] end generated region [Generated Includes]


// [[[ begin [Event Handler Includes]
#include <AknToolbarObserver.h>
// ]]] end [Event Handler Includes]

// [[[ begin generated region: do not modify [Generated Constants]
// ]]] end generated region [Generated Constants]

// [[[ begin generated region: do not modify [Generated Forward Declarations]
class CTUI_5_0Container;
class CAknPreviewPopUpController;
class CPreviewPopUp1;
// ]]] end generated region [Generated Forward Declarations]

/**
 * Avkon view class for TUI_5_0ContainerView. It is register with the view server
 * by the AppUi. It owns the container control.
 * @class	CTUI_5_0ContainerView TUI_5_0ContainerView.h
 */						
			
class CTUI_5_0ContainerView : public CAknView
	,MAknToolbarObserver	{
	
	
	// [[[ begin [Public Section]
public:
	// constructors and destructor
	CTUI_5_0ContainerView();
	static CTUI_5_0ContainerView* NewL();
	static CTUI_5_0ContainerView* NewLC();        
	void ConstructL();
	virtual ~CTUI_5_0ContainerView();
						
	// from base class CAknView
	TUid Id() const;
	void HandleCommandL( TInt aCommand );
	
	// [[[ begin generated region: do not modify [Generated Methods]
	CTUI_5_0Container* CreateContainerL();
	void ShowPreviewPopUp1L( 
			const TRect& aHighlightRect,
			const TDesC* aOverrideHeadingText );
	void ShowPreviewPopUp1L( 
			const TPoint& aPoint,
			const TDesC* aOverrideHeadingText );
	// ]]] end generated region [Generated Methods]
	
	void DynInitToolbarL( 
			TInt aResourceId,
			CAknToolbar* aToolBar );
	void OfferToolbarEventL( TInt aCommandId );
	// ]]] end [Public Section]
	
	
	// [[[ begin [Protected Section]
protected:
	// from base class CAknView
	void DoActivateL(
		const TVwsViewId& aPrevViewId,
		TUid aCustomMessageId,
		const TDesC8& aCustomMessage );
	void DoDeactivate();
	void HandleStatusPaneSizeChange();
	
	// [[[ begin generated region: do not modify [Overridden Methods]
	// ]]] end generated region [Overridden Methods]
	
	
	// [[[ begin [User Handlers]
	// ]]] end [User Handlers]
	
	// ]]] end [Protected Section]
	
	
	// [[[ begin [Private Section]
private:
	void SetupStatusPaneL();
	void CleanupStatusPane();
	
	// [[[ begin generated region: do not modify [Generated Instance Variables]
	CTUI_5_0Container* iTUI_5_0Container;
	CAknPreviewPopUpController* iPreviewPopUp1Controller;
	CPreviewPopUp1* iPreviewPopUp1Content;
	// ]]] end generated region [Generated Instance Variables]
	
	// [[[ begin generated region: do not modify [Generated Methods]
	// ]]] end generated region [Generated Methods]
	
	// ]]] end [Private Section]
	
	};

#endif // TUI_5_0CONTAINERVIEW_H
