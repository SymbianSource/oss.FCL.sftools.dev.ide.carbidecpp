#ifndef PREVIEWPOPUP1_H
#define PREVIEWPOPUP1_H

// [[[ begin generated region: do not modify [Generated System Includes]
#include <coecntrl.h>		
#include <aknpreviewpopupcontentprovider.h>
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
class MEikCommandObserver;		
class CAknPreviewPopUpController;
class CEikLabel;
// ]]] end generated region [Generated Forward Declarations]

/**
 * Content class for previewPopUp1.
 * @class	CPreviewPopUp1 PreviewPopUp1.h
 */
			
class CPreviewPopUp1 : public CCoeControl
	, public MAknPreviewPopUpContentProvider	{
	
	
	// [[[ begin [Public Section]
public:
	
	// [[[ begin generated region: do not modify [Generated Types]
	// ]]] end generated region [Generated Types]
	
	// [[[ begin generated region: do not modify [Generated Methods]
	// ]]] end generated region [Generated Methods]
	
	CPreviewPopUp1();
	virtual ~CPreviewPopUp1();
	static CPreviewPopUp1* NewL( MEikCommandObserver* aCommandObserver );
	static CPreviewPopUp1* NewLC( MEikCommandObserver* aCommandObserver );
	void ConstructL( MEikCommandObserver* aCommandObserver );
	TInt CountComponentControls() const;
	CCoeControl* ComponentControl( TInt aIndex ) const;
	void StartContentBuildingL();
	void CancelContentBuilding();
	void SetPopUpController( CAknPreviewPopUpController* aController );
	// ]]] end [Public Section]
	
	
	// [[[ begin [Protected Section]
protected:
	
	// [[[ begin generated region: do not modify [Overridden Methods]
	// ]]] end generated region [Overridden Methods]
	
	
	// [[[ begin [User Handlers]
	// ]]] end [User Handlers]
	
	TSize MinimumSize();
	void SizeChanged();
	// ]]] end [Protected Section]
	
	
	// [[[ begin [Private Section]
private:
	
	MEikCommandObserver* iCommandObserver;
	CAknPreviewPopUpController* iController;
	
	// [[[ begin generated region: do not modify [Generated Types]
	// ]]] end generated region [Generated Types]
	
	// [[[ begin generated region: do not modify [Generated Instance Variables]
	CEikLabel* iLabel1;
	// ]]] end generated region [Generated Instance Variables]
	
	// [[[ begin generated region: do not modify [Generated Methods]
	void InitializeControlsL();
	void LayoutControls();
	// ]]] end generated region [Generated Methods]
	
	void Draw( const TRect& aRect ) const;
	// ]]] end [Private Section]
	
public: 
	enum TControls
		{
		// [[[ begin generated region: do not modify [Generated Contents]
		ELabel1,
		
		// ]]] end generated region [Generated Contents]
		
		// add any user-defined entries here...
		
		ELastControl
		};
	};

#endif // PREVIEWPOPUP1_H
