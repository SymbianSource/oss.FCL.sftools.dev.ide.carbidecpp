#ifndef FORM_3_0FORM_H
#define FORM_3_0FORM_H

// [[[ begin generated region: do not modify [Generated Includes]
#include <aknform.h>
// ]]] end generated region [Generated Includes]


// [[[ begin [Event Handler Includes]
// ]]] end [Event Handler Includes]

// [[[ begin generated region: do not modify [Generated Forward Declarations]
class MEikCommandObserver;		
class CEikNumberEditor;
class CEikDurationEditor;
class CEikEdwin;
class CAknPopupFieldText;
// ]]] end generated region [Generated Forward Declarations]

/**
 * @class	CForm_3_0Form Form_3_0Form.h
 */
class CForm_3_0Form : public CAknForm
	{
public: // constructors and destructor

	CForm_3_0Form( MEikCommandObserver* aCommandObserver );
	static CForm_3_0Form* NewL( MEikCommandObserver* aCommandObserver );
	static CForm_3_0Form* NewLC( MEikCommandObserver* aCommandObserver );
	virtual ~CForm_3_0Form();
	
	// from CEikDialog
	TKeyResponse OfferKeyEventL( 
			const TKeyEvent& aKeyEvent, 
			TEventCode aType );
	
protected: 
	// from CAknDialog	
	void PreLayoutDynInitL();
	TBool OkToExitL( TInt aButtonId );

	// from CAknForm
	void DynInitMenuPaneL( TInt aResourceId, CEikMenuPane* aMenuPane );
	TBool QuerySaveChangesL();
	TBool SaveFormDataL(); 
	void DoNotSaveFormDataL();

private:
	void LoadFromDataL();
	MEikCommandObserver* iCommandObserver;
	// [[[ begin generated region: do not modify [Generated Methods]
public: 
	// ]]] end generated region [Generated Methods]
	
	// [[[ begin generated region: do not modify [Generated Type Declarations]
public: 
	// ]]] end generated region [Generated Type Declarations]
	
	// [[[ begin generated region: do not modify [Generated Instance Variables]
private: 
	CEikNumberEditor* iNumEditor1;
	CEikDurationEditor* iDurationEditor1;
	CEikEdwin* iEdit1;
	CAknPopupFieldText* iPopupFieldText1;
	// ]]] end generated region [Generated Instance Variables]
	
	
	// [[[ begin [Overridden Methods]
protected: 
	// ]]] end [Overridden Methods]
	
	
	// [[[ begin [User Handlers]
protected: 
	// ]]] end [User Handlers]
	
	};

#endif // FORM_3_0FORM_H
