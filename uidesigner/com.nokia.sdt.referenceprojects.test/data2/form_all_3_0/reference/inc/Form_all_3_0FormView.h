#ifndef FORM_ALL_3_0FORMVIEW_H
#define FORM_ALL_3_0FORMVIEW_H

// [[[ begin generated region: do not modify [Generated Includes]
#include <aknview.h>
// ]]] end generated region [Generated Includes]


// [[[ begin [Event Handler Includes]
// ]]] end [Event Handler Includes]

// [[[ begin generated region: do not modify [Generated Constants]
// ]]] end generated region [Generated Constants]

// [[[ begin generated region: do not modify [Generated Forward Declarations]
class CForm_all_3_0Form;
// ]]] end generated region [Generated Forward Declarations]

/**
 * Avkon view class for form_all_3_0FormView. It is register with the view server
 * by the AppUi. It owns the container control.
 * @class	Cform_all_3_0FormView form_all_3_0FormView.h
 */						
			
class Cform_all_3_0FormView : public CAknView
	{
	
	
	// [[[ begin [Public Section]
public:
	// constructors and destructor
	Cform_all_3_0FormView();
	static Cform_all_3_0FormView* NewL();
	static Cform_all_3_0FormView* NewLC();        
	void ConstructL();
	virtual ~Cform_all_3_0FormView();
						
	// from base class CAknView
	TUid Id() const;
	void HandleCommandL( TInt aCommand );
	
	// [[[ begin generated region: do not modify [Generated Methods]
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
	void HandleForm_all_3_0FormViewDeactivated();
	TBool HandleForm_all_3_0FormViewCommandL( TInt aCommand );
	void HandleForm_all_3_0FormViewStatusPaneSizeChange();
	void HandleForm_all_3_0FormViewActivatedL();
	// ]]] end [User Handlers]
	
	// ]]] end [Protected Section]
	
	
	// [[[ begin [Private Section]
private:
	void SetupStatusPaneL();
	void CleanupStatusPane();
	
	// [[[ begin generated region: do not modify [Generated Instance Variables]
	CForm_all_3_0Form* iForm_all_3_0Form;
	// ]]] end generated region [Generated Instance Variables]
	
	// [[[ begin generated region: do not modify [Generated Methods]
	// ]]] end generated region [Generated Methods]
	
	// ]]] end [Private Section]
	
	};

#endif // FORM_ALL_3_0FORMVIEW_H
