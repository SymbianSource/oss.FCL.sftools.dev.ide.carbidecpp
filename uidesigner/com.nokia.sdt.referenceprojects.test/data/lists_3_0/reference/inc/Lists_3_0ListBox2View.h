#ifndef LISTS_3_0LISTBOX2VIEW_H
#define LISTS_3_0LISTBOX2VIEW_H

// [[[ begin generated region: do not modify [Generated Includes]
#include <aknview.h>
#include <eiklbv.h>
// ]]] end generated region [Generated Includes]


// [[[ begin [Event Handler Includes]
// ]]] end [Event Handler Includes]

// [[[ begin generated region: do not modify [Generated Constants]
// ]]] end generated region [Generated Constants]

// [[[ begin generated region: do not modify [Generated Forward Declarations]
class CLists_3_0ListBox2;
// ]]] end generated region [Generated Forward Declarations]

/**
 * Avkon view class for lists_3_0ListBox2View. It is register with the view server
 * by the AppUi. It owns the container control.
 * @class	Clists_3_0ListBox2View lists_3_0ListBox2View.h
 */						
			
class Clists_3_0ListBox2View : public CAknView
	{
	
	
	// [[[ begin [Public Section]
public:
	// constructors and destructor
	Clists_3_0ListBox2View();
	static Clists_3_0ListBox2View* NewL();
	static Clists_3_0ListBox2View* NewLC();        
	void ConstructL();
	virtual ~Clists_3_0ListBox2View();
						
	// from base class CAknView
	TUid Id() const;
	void HandleCommandL( TInt aCommand );
	
	// [[[ begin generated region: do not modify [Generated Methods]
	CLists_3_0ListBox2* CreateContainerL();
	static void CreateListMultiQuery1ItemL( 
			TDes& aBuffer, 
			TBool aSelected, 
			
			const TDesC& aMainText,
			const TDesC& aSecondaryText );
	static void CreateListMultiQuery1ResourceArrayItemL( 
			TDes& aBuffer, 
			TBool aSelected, 
			TInt aResourceId );
	static CDesCArray* InitializeListMultiQuery1LC();
	static CListBoxView::CSelectionIndexArray* RunListMultiQuery1LC(
			const TDesC* aOverrideText = NULL,
			CDesCArray* aOverrideItemArray = NULL );
	// ]]] end generated region [Generated Methods]
	
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
	CLists_3_0ListBox2* iLists_3_0ListBox2;
	// ]]] end generated region [Generated Instance Variables]
	
	// [[[ begin generated region: do not modify [Generated Methods]
	// ]]] end generated region [Generated Methods]
	
	// ]]] end [Private Section]
	
	};

#endif // LISTS_3_0LISTBOX2VIEW_H
