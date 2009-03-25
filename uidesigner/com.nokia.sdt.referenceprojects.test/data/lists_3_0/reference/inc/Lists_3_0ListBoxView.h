#ifndef LISTS_3_0LISTBOXVIEW_H
#define LISTS_3_0LISTBOXVIEW_H

// [[[ begin generated region: do not modify [Generated Includes]
#include <aknview.h>
#include <akniconutils.h>
#include <gulicon.h>
// ]]] end generated region [Generated Includes]


// [[[ begin [Event Handler Includes]
// ]]] end [Event Handler Includes]

// [[[ begin generated region: do not modify [Generated Constants]
// ]]] end generated region [Generated Constants]

// [[[ begin generated region: do not modify [Generated Forward Declarations]
class CLists_3_0ListBox;
// ]]] end generated region [Generated Forward Declarations]

/**
 * Avkon view class for lists_3_0ListBoxView. It is register with the view server
 * by the AppUi. It owns the container control.
 * @class	Clists_3_0ListBoxView lists_3_0ListBoxView.h
 */						
			
class Clists_3_0ListBoxView : public CAknView
	{
	
	
	// [[[ begin [Public Section]
public:
	// constructors and destructor
	Clists_3_0ListBoxView();
	static Clists_3_0ListBoxView* NewL();
	static Clists_3_0ListBoxView* NewLC();        
	void ConstructL();
	virtual ~Clists_3_0ListBoxView();
						
	// from base class CAknView
	TUid Id() const;
	void HandleCommandL( TInt aCommand );
	
	// [[[ begin generated region: do not modify [Generated Methods]
	CLists_3_0ListBox* CreateContainerL();
	static void CreateListQuery1ItemL( 
			TDes& aBuffer,
			
			TInt aIconIndex,
			const TDesC& aMainText,
			const TDesC& aSecondaryText );
	static void CreateListQuery1ResourceArrayItemL( 
			TDes& aBuffer, 
			TInt aResourceId, TInt aIconIndex );
	static CDesCArray* InitializeListQuery1LC();
	static CArrayPtr< CGulIcon >* SetupListQuery1IconsLC();
	static CGulIcon* LoadAndScaleIconL(
			const TDesC& aFileName,
			TInt aBitmapId,
			TInt aMaskId,
			TSize* aSize,
			TScaleMode aScaleMode );
	static TInt RunListQuery1L( 
			const TDesC* aOverrideText = NULL, 
			CDesCArray* aOverrideItemArray = NULL,
			CArrayPtr< CGulIcon >* aOverrideIconArray = NULL );
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
	CLists_3_0ListBox* iLists_3_0ListBox;
	// ]]] end generated region [Generated Instance Variables]
	
	// [[[ begin generated region: do not modify [Generated Methods]
	// ]]] end generated region [Generated Methods]
	
	// ]]] end [Private Section]
	
	enum TListQuery1Images
		{
		// [[[ begin generated region: do not modify [Generated Enums]
		EListQuery1Lists_3_0List_iconIndex = 0,
		EListQuery1FirstUserImageIndex
		
		// ]]] end generated region [Generated Enums]
		
		};
	};

#endif // LISTS_3_0LISTBOXVIEW_H
