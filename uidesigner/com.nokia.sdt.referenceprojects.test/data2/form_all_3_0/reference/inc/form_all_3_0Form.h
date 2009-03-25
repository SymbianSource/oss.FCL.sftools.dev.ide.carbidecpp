#ifndef FORM_ALL_3_0FORM_H
#define FORM_ALL_3_0FORM_H

// [[[ begin generated region: do not modify [Generated Includes]
#include <aknform.h>
// ]]] end generated region [Generated Includes]


// [[[ begin [Event Handler Includes]
#include <eikedwob.h>
// ]]] end [Event Handler Includes]

// [[[ begin generated region: do not modify [Generated Forward Declarations]
class MEikCommandObserver;		
class CAknIntegerEdwin;
class CEikDateEditor;
class CEikDurationEditor;
class CEikFixedPointEditor;
class CEikFloatingPointEditor;
class CEikNumberEditor;
class CAknNumericSecretEditor;
class CEikRangeEditor;
class CEikSecretEditor;
class CEikEdwin;
class CEikTimeEditor;
class CEikTimeOffsetEditor;
class CEikTimeAndDateEditor;
// ]]] end generated region [Generated Forward Declarations]

/**
 * @class	CForm_all_3_0Form form_all_3_0Form.h
 */
class CForm_all_3_0Form : public CAknForm
	,MEikEdwinObserver	{
public: // constructors and destructor

	CForm_all_3_0Form( MEikCommandObserver* aCommandObserver );
	static CForm_all_3_0Form* NewL( MEikCommandObserver* aCommandObserver );
	static CForm_all_3_0Form* NewLC( MEikCommandObserver* aCommandObserver );
	virtual ~CForm_all_3_0Form();
	
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
	CAknIntegerEdwin* iIntegerEditor1;
	CEikDateEditor* iDateEditor1;
	CEikDurationEditor* iDurationEditor1;
	CEikFixedPointEditor* iFixedPointEditor1;
	CEikFloatingPointEditor* iFloatingPointEditor1;
	CEikNumberEditor* iNumEditor1;
	CAknNumericSecretEditor* iNumSecret1;
	CEikRangeEditor* iRangeEditor1;
	CEikSecretEditor* iSecret1;
	CEikEdwin* iEdit1;
	CEikTimeEditor* iTimeEditor1;
	CEikTimeOffsetEditor* iTimeOffsetEditor1;
	CEikTimeAndDateEditor* iTimeAndDateEditor1;
	// ]]] end generated region [Generated Instance Variables]
	
	
	// [[[ begin [Overridden Methods]
protected: 
	void PrepareForFocusTransitionL();
	void PageChangedL( TInt aPageId );
	void LineChangedL( TInt aPageId );
	void ProcessCommandL( TInt aCommandId );
	void OfferKeyToAppL( const TKeyEvent& aKeyEvent, TEventCode aType );
	void FocusChanged( TDrawNow aDrawNow );
	void SizeChanged();
	void PositionChanged();
	void Draw( const TRect& aRect ) const;
	void PrepareForFocusLossL();
	void PrepareForFocusGainL();
	void HandleResourceChange( TInt aType );
	void HandleControlEventL( 
			CCoeControl* aControl, 
			TCoeEvent anEventType );
	void HandleEdwinEventL( 
			CEikEdwin* anEdwin, 
			TEdwinEvent anEventType );
	// ]]] end [Overridden Methods]
	
	
	// [[[ begin [User Handlers]
protected: 
	void HandleForm_all_3_0FormPrepareForFocusTransitionL();
	void HandleForm_all_3_0FormPageChangedL( TInt aPageId );
	void HandleForm_all_3_0FormLineChangedL( TInt aControlId );
	void HandleForm_all_3_0FormProcessCommandL( TInt aCommandId );
	void HandleForm_all_3_0FormOfferKeyToAppL( 
			const TKeyEvent& aKeyEvent, 
			TEventCode aType );
	void HandleForm_all_3_0FormFocusChanged( TDrawNow aDrawNow );
	void HandleForm_all_3_0FormSizeChanged();
	void HandleForm_all_3_0FormPositionChanged();
	void HandleForm_all_3_0FormDraw( const TRect& aRect ) const;
	TKeyResponse HandleForm_all_3_0FormOfferKeyEventL( 
			const TKeyEvent& aKeyEvent, 
			TEventCode aType );
	void HandleForm_all_3_0FormPrepareForFocusLossL();
	void HandleForm_all_3_0FormPrepareForFocusGainL();
	void HandleForm_all_3_0FormResourceChanged( TInt aType );
	void HandleForm_all_3_0FormDynInitMenuPaneL( TInt aResourceId, CEikMenuPane* aMenuPane );
	void HandleIntegerEditor1StateChangedL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleIntegerEditor1RequestingFocusL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleIntegerEditor1RequestingExitL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleIntegerEditor1RequestingCancelL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleIntegerEditor1InteractionRefusedL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleIntegerEditor1PrepareFocusTransitionL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleIntegerEditor1FormatChangedL( 
			CEikEdwin* anEdwin, 
			TEdwinEvent anEventType );
	void HandleIntegerEditor1NavigationL( 
			CEikEdwin* anEdwin, 
			TEdwinEvent anEventType );
	void HandleIntegerEditor1TextUpdatedL( 
			CEikEdwin* anEdwin, 
			TEdwinEvent anEventType );
	void HandleDateEditor1StateChangedL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleDateEditor1RequestingFocusL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleDateEditor1RequestingExitL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleDateEditor1RequestingCancelL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleDateEditor1InteractionRefusedL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleDateEditor1PrepareFocusTransitionL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleDurationEditor1StateChangedL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleDurationEditor1RequestingFocusL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleDurationEditor1RequestingExitL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleDurationEditor1RequestingCancelL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleDurationEditor1InteractionRefusedL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleDurationEditor1PrepareFocusTransitionL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleFixedPointEditor1StateChangedL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleFixedPointEditor1RequestingFocusL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleFixedPointEditor1RequestingExitL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleFixedPointEditor1RequestingCancelL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleFixedPointEditor1InteractionRefusedL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleFixedPointEditor1PrepareFocusTransitionL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleFixedPointEditor1FormatChangedL( 
			CEikEdwin* anEdwin, 
			TEdwinEvent anEventType );
	void HandleFixedPointEditor1NavigationL( 
			CEikEdwin* anEdwin, 
			TEdwinEvent anEventType );
	void HandleFixedPointEditor1TextUpdatedL( 
			CEikEdwin* anEdwin, 
			TEdwinEvent anEventType );
	void HandleFloatingPointEditor1StateChangedL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleFloatingPointEditor1RequestingFocusL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleFloatingPointEditor1RequestingExitL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleFloatingPointEditor1RequestingCancelL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleFloatingPointEditor1InteractionRefusedL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleFloatingPointEditor1PrepareFocusTransitionL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleFloatingPointEditor1FormatChangedL( 
			CEikEdwin* anEdwin, 
			TEdwinEvent anEventType );
	void HandleFloatingPointEditor1NavigationL( 
			CEikEdwin* anEdwin, 
			TEdwinEvent anEventType );
	void HandleFloatingPointEditor1TextUpdatedL( 
			CEikEdwin* anEdwin, 
			TEdwinEvent anEventType );
	void HandleNumEditor1StateChangedL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleNumEditor1RequestingFocusL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleNumEditor1RequestingExitL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleNumEditor1RequestingCancelL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleNumEditor1InteractionRefusedL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleNumEditor1PrepareFocusTransitionL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleNumSecret1StateChangedL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleNumSecret1RequestingFocusL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleNumSecret1RequestingExitL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleNumSecret1RequestingCancelL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleNumSecret1InteractionRefusedL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleNumSecret1PrepareFocusTransitionL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleRangeEditor1StateChangedL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleRangeEditor1RequestingFocusL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleRangeEditor1RequestingExitL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleRangeEditor1RequestingCancelL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleRangeEditor1InteractionRefusedL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleRangeEditor1PrepareFocusTransitionL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleSecret1StateChangedL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleSecret1RequestingFocusL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleSecret1RequestingExitL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleSecret1RequestingCancelL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleSecret1InteractionRefusedL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleSecret1PrepareFocusTransitionL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleEdit1StateChangedL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleEdit1RequestingFocusL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleEdit1RequestingExitL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleEdit1RequestingCancelL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleEdit1InteractionRefusedL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleEdit1PrepareFocusTransitionL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleEdit1FormatChangedL( 
			CEikEdwin* anEdwin, 
			TEdwinEvent anEventType );
	void HandleEdit1NavigationL( 
			CEikEdwin* anEdwin, 
			TEdwinEvent anEventType );
	void HandleEdit1TextUpdatedL( 
			CEikEdwin* anEdwin, 
			TEdwinEvent anEventType );
	void HandleTimeEditor1StateChangedL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleTimeEditor1RequestingFocusL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleTimeEditor1RequestingExitL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleTimeEditor1RequestingCancelL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleTimeEditor1InteractionRefusedL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleTimeEditor1PrepareFocusTransitionL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleTimeOffsetEditor1StateChangedL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleTimeOffsetEditor1RequestingFocusL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleTimeOffsetEditor1RequestingExitL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleTimeOffsetEditor1RequestingCancelL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleTimeOffsetEditor1InteractionRefusedL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleTimeOffsetEditor1PrepareFocusTransitionL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleTimeAndDateEditor1StateChangedL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleTimeAndDateEditor1RequestingFocusL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleTimeAndDateEditor1RequestingExitL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleTimeAndDateEditor1RequestingCancelL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleTimeAndDateEditor1InteractionRefusedL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	void HandleTimeAndDateEditor1PrepareFocusTransitionL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent );
	// ]]] end [User Handlers]
	
	
	// [[[ begin [MCoeControlObserver support]
private: 
	typedef void ( CForm_all_3_0Form::*ControlEventHandler )( 
			CCoeControl* aControl, TCoeEvent anEvent );
	
	void AddControlEventHandlerL( 
			CCoeControl* aControl, 
			TCoeEvent anEvent, 
			ControlEventHandler aHandler );
	
	class TControlEventDispatch 
		{
	public: 
		CCoeControl* src; 
		TCoeEvent event; 
		ControlEventHandler handler;
		};
		
	RArray< TControlEventDispatch > iControlEventDispatch;
	// ]]] end [MCoeControlObserver support]
	
	
	// [[[ begin [MEdwinObserver support]
private: 
	typedef void ( CForm_all_3_0Form::*EdwinEventHandler )( 
			CEikEdwin* anEdwin, 
			TEdwinEvent event );
	
	void AddEdwinEventHandlerL( 
			CEikEdwin* anEdwin, 
			TEdwinEvent anEvent, 
			EdwinEventHandler aHandler );
	
	class TEdwinEventDispatch 
		{
	public: 
		CEikEdwin* src; 
		TEdwinEvent event; 
		EdwinEventHandler handler;
		};
		
	RArray< TEdwinEventDispatch > iEdwinEventDispatch;
	// ]]] end [MEdwinObserver support]
	
	};

#endif // FORM_ALL_3_0FORM_H
