// [[[ begin generated region: do not modify [Generated System Includes]
#include <QikCommand.h>
#include <UIQ_MultiPage_3_0.rsg>
#include <eikenv.h>
#include <uikon.hrh>
#include <eikcmbut.h>
#include <eikbutb.h>
#include <guldef.h>
#include <gulbordr.h>
#include <eikchlst.h>
#include <QikColorSelector.h>
#include <eikcmbox.h>
#include <eikimage.h>
#include <gulalign.h>
#include <barsread.h>
#include <eiklabel.h>
#include <eikchkbx.h>
#include <QikListBox.h>
#include <eikprogi.h>
#include <QikVertOptionButtonList.h>
#include <QikDateEditor.h>
#include <QikFloatingPointEditor.h>
#include <QikNumberEditor.h>
#include <eikedwin.h>
#include <txtrich.h>
#include <eikrted.h>
#include <eikgted.h>
#include <QikTimeEditor.h>
// ]]] end generated region [Generated System Includes]

// [[[ begin generated region: do not modify [Generated User Includes]
#include "UIQ_MultiPage_3_0MultiPageView.h"
#include "UIQ_MultiPage_3_0AppUi.h" 
#include "UIQ_MultiPage_3_0ApplicationExternalInterface.h" 
#include "UIQ_MultiPage_3_0MultiPageView.hrh"
// ]]] end generated region [Generated User Includes]

// [[[ begin generated region: do not modify [Generated Constants]
// ]]] end generated region [Generated Constants]

/**
 * Constructor for the view.
 * Passes the application UI reference to the construction of the super class.
 * 
 * KNullViewId should normally be passed as parent view for the applications 
 * default view. The parent view is the logical view that is normally activated 
 * when a go back command is issued. KNullViewId will activate the system 
 * default view. 
 * 
 * @param aAppUi Reference to the application UI
 * @param aParentViewId UID of the parent view 
 */
CUIQ_MultiPage_3_0MultiPageView::CUIQ_MultiPage_3_0MultiPageView( 
		CQikAppUi& aAppUi,
		const TVwsViewId aParentViewId )
	: CUIQ_MultiPage_3_0MultiPageViewBase(aAppUi, aParentViewId)

	{
	
	}

/**
 * Destructor for the view
 */
CUIQ_MultiPage_3_0MultiPageView::~CUIQ_MultiPage_3_0MultiPageView()
	{
	iControlEventDispatch.Reset();
	iCommandEventDispatch.Reset();					
	
	
	// [[[ begin generated region: do not modify [Generated Contents]
	
	iBitmapButton1 = NULL;
	
	iChoiceList1 = NULL;
	
	iColorSelector1 = NULL;
	
	iComboBox1 = NULL;
	
	iCommandButton1 = NULL;
	
	iImage1 = NULL;
	
	iLabel1 = NULL;
	
	iLabeledCheckBox1 = NULL;
	iListBoxEventDispatch.Reset();
	
	iListBox1 = NULL;
	
	iProgressInfo1 = NULL;
	
	iTextButton1 = NULL;
	
	iVertOptionButtonList1 = NULL;
	
	iDateEditor1 = NULL;
	
	iFloatingPointEditor1 = NULL;
	
	iNumberEditor1 = NULL;
	
	iPlainTextEditor1 = NULL;
	
	iRichTextEditor1 = NULL;
	
	iTimeEditor1 = NULL;
	
	iColorSelector2 = NULL;
	
	iLabel2 = NULL;
	
	iImage2 = NULL;
	
	iNumberEditor2 = NULL;
	
	iFloatingPointEditor2 = NULL;
	// ]]] end generated region [Generated Contents]
	
	}

/**
 * Creates and constructs the view.
 * 
 * @param aAppUi Reference to the AppUi
 * @param aParentViewId UID of the parent view 
 * @return Pointer to a CUIQ_MultiPage_3_0MultiPageView object
 */
CUIQ_MultiPage_3_0MultiPageView* CUIQ_MultiPage_3_0MultiPageView::NewLC( 
		CQikAppUi& aAppUi,
		const TVwsViewId aParentViewId )
	{
	CUIQ_MultiPage_3_0MultiPageView* self = new (ELeave) CUIQ_MultiPage_3_0MultiPageView(
			aAppUi,
			aParentViewId);
	CleanupStack::PushL(self);
	self->ConstructL();
	return self;
	}

/**
 * 2nd stage construction of the view.
 */
void CUIQ_MultiPage_3_0MultiPageView::ConstructL()
	{
	// Calls ConstructL that initialises the standard values. 
	// This should always be called in the concrete view implementations.
	BaseConstructL();
	}

/**
 * Inherited from CUIQ_MultiPage_3_0MultiPageViewBase and called upon by the UI Framework. 
 * It creates the view from resource.
 */
void CUIQ_MultiPage_3_0MultiPageView::ViewConstructL()
	{
	// [[[ begin generated region: do not modify [Pre-ViewConstructFromResourceL]
	// ]]] end generated region [Pre-ViewConstructFromResourceL]
	
	// [[[ begin generated region: do not modify [Generated Contents]
	// Loads information about the UI configurations this view supports
	// together with definition of each view.
	ViewConstructFromResourceL(R_UIQ_MULTI_PAGE_3_0_MULTI_PAGE_VIEW_UIQ_MULTI_PAGE_3_0_MULTI_PAGE_VIEW,
		R_UIQ_MULTI_PAGE_3_0_MULTI_PAGE_VIEW_CONTROL_COLLECTION);
	
	InitializeControlsL();
	// ]]] end generated region [Generated Contents]
	
	}

/**
 * Returns the view Id
 *
 * @return Returns UID of the view
 */
TVwsViewId CUIQ_MultiPage_3_0MultiPageView::ViewId() const
	{
	return TVwsViewId(KUidUIQ_MultiPage_3_0App, KUidUIQ_MultiPage_3_0MultiPageView);
	}

/**
 * Handles all commands in the view.
 * Called by the UI framework when a command has been issued.
 * The command Ids are defined in the .hrh file.
 * 
 * @param aCommand The command to be executed
 * @see CUIQ_MultiPage_3_0MultiPageViewBase::HandleCommandL
 */
void CUIQ_MultiPage_3_0MultiPageView::HandleCommandL( CQikCommand& aCommand )
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	for (int i = 0; i < iCommandEventDispatch.Count(); i++)
		{
		const TCommandEventDispatch& currEntry = iCommandEventDispatch[i];
		if ( currEntry.id == aCommand.Id() )
			{
			( this->*currEntry.handler )( aCommand );
			break;
			}
		}
	CUIQ_MultiPage_3_0MultiPageViewBase::HandleCommandL(aCommand);	
	
				
	// ]]] end generated region [Generated Contents]
	
	}

/**
 * Handles an event from an observed control.
 * 
 * @param aControl The control that sent the event.
 * @param aEventType The event type.
 */
void CUIQ_MultiPage_3_0MultiPageView::HandleControlEventL( 
		CCoeControl* aControl,
		TCoeEvent aEventType )
	{
	CUIQ_MultiPage_3_0MultiPageViewBase::HandleControlEventL( aControl, aEventType );
	
	// [[[ begin generated region: do not modify [Generated Contents]
	// ]]] end generated region [Generated Contents]
	
		for (int i = 0; i < iControlEventDispatch.Count(); i++)
			{
			const TControlEventDispatch& currEntry = iControlEventDispatch[i];
			if ( currEntry.src == aControl && currEntry.event == aEventType )
				{
				( this->*currEntry.handler )( aControl, aEventType );
				break;
				}
			}
							
	
	}

/**
 *	Initialize each control upon creation.
 */				
void CUIQ_MultiPage_3_0MultiPageView::InitializeControlsL()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	iControlEventDispatch.Reset();
	iCommandEventDispatch.Reset();					
				
	
	// [[[ begin [Recover Control From Control Collection]
	iBitmapButton1 = LocateControlByUniqueHandle<CEikBitmapButton>( EUIQ_MultiPage_3_0MultiPageViewControl );
	if ( iBitmapButton1 != NULL )
		{
		iBitmapButton1->SetObserver( this );
		}
	
	iChoiceList1 = LocateControlByUniqueHandle<CEikChoiceList>( EUIQ_MultiPage_3_0MultiPageViewControl2 );
	if ( iChoiceList1 != NULL )
		{
		iChoiceList1->SetObserver( this );
		}
	
	iColorSelector1 = LocateControlByUniqueHandle<CQikColorSelector>( EUIQ_MultiPage_3_0MultiPageViewControl3 );
	if ( iColorSelector1 != NULL )
		{
		iColorSelector1->SetObserver( this );
		}
	
	iComboBox1 = LocateControlByUniqueHandle<CEikComboBox>( EUIQ_MultiPage_3_0MultiPageViewControl4 );
	if ( iComboBox1 != NULL )
		{
		iComboBox1->SetObserver( this );
		}
	
	iCommandButton1 = LocateControlByUniqueHandle<CEikCommandButton>( EUIQ_MultiPage_3_0MultiPageViewControl5 );
	if ( iCommandButton1 != NULL )
		{
		iCommandButton1->SetObserver( this );
		}
	
	iImage1 = LocateControlByUniqueHandle<CEikImage>( EUIQ_MultiPage_3_0MultiPageViewControl6 );
	if ( iImage1 != NULL )
		{
		iImage1->SetObserver( this );
		}
	
	iLabel1 = LocateControlByUniqueHandle<CEikLabel>( EUIQ_MultiPage_3_0MultiPageViewControl7 );
	if ( iLabel1 != NULL )
		{
		iLabel1->SetObserver( this );
		}
	
	iLabeledCheckBox1 = LocateControlByUniqueHandle<CEikLabeledCheckBox>( EUIQ_MultiPage_3_0MultiPageViewControl8 );
	if ( iLabeledCheckBox1 != NULL )
		{
		iLabeledCheckBox1->SetObserver( this );
		}
	
	iListBoxEventDispatch.Reset();
	iListBox1 = LocateControlByUniqueHandle<CQikListBox>( EUIQ_MultiPage_3_0MultiPageViewControl9 );
	if ( iListBox1 != NULL )
		{
		iListBox1->SetObserver( this );
		}
	
	iProgressInfo1 = LocateControlByUniqueHandle<CEikProgressInfo>( EUIQ_MultiPage_3_0MultiPageViewControl10 );
	if ( iProgressInfo1 != NULL )
		{
		iProgressInfo1->SetObserver( this );
		}
	
		
		iProgressInfo1 -> SetAndDraw( 0 );
		
	iTextButton1 = LocateControlByUniqueHandle<CEikTextButton>( EUIQ_MultiPage_3_0MultiPageViewControl11 );
	if ( iTextButton1 != NULL )
		{
		iTextButton1->SetObserver( this );
		}
	
	iVertOptionButtonList1 = LocateControlByUniqueHandle<CQikVertOptionButtonList>( EUIQ_MultiPage_3_0MultiPageViewControl12 );
	if ( iVertOptionButtonList1 != NULL )
		{
		iVertOptionButtonList1->SetObserver( this );
		}
	
	iDateEditor1 = LocateControlByUniqueHandle<CQikDateEditor>( EUIQ_MultiPage_3_0MultiPageViewControl13 );
	if ( iDateEditor1 != NULL )
		{
		iDateEditor1->SetObserver( this );
		}
	
	iFloatingPointEditor1 = LocateControlByUniqueHandle<CQikFloatingPointEditor>( EUIQ_MultiPage_3_0MultiPageViewControl14 );
	if ( iFloatingPointEditor1 != NULL )
		{
		iFloatingPointEditor1->SetObserver( this );
		}
	
	iNumberEditor1 = LocateControlByUniqueHandle<CQikNumberEditor>( EUIQ_MultiPage_3_0MultiPageViewControl15 );
	if ( iNumberEditor1 != NULL )
		{
		iNumberEditor1->SetObserver( this );
		}
	
	iPlainTextEditor1 = LocateControlByUniqueHandle<CEikEdwin>( EUIQ_MultiPage_3_0MultiPageViewControl16 );
	if ( iPlainTextEditor1 != NULL )
		{
		iPlainTextEditor1->SetObserver( this );
		}
	
	iRichTextEditor1 = LocateControlByUniqueHandle<CEikRichTextEditor>( EUIQ_MultiPage_3_0MultiPageViewControl17 );
	if ( iRichTextEditor1 != NULL )
		{
		iRichTextEditor1->SetObserver( this );
		}
	
	iTimeEditor1 = LocateControlByUniqueHandle<CQikTimeEditor>( EUIQ_MultiPage_3_0MultiPageViewControl18 );
	if ( iTimeEditor1 != NULL )
		{
		iTimeEditor1->SetObserver( this );
		}
	
	iColorSelector2 = LocateControlByUniqueHandle<CQikColorSelector>( EUIQ_MultiPage_3_0MultiPageViewControl19 );
	if ( iColorSelector2 != NULL )
		{
		iColorSelector2->SetObserver( this );
		}
	
	iLabel2 = LocateControlByUniqueHandle<CEikLabel>( EUIQ_MultiPage_3_0MultiPageViewControl20 );
	if ( iLabel2 != NULL )
		{
		iLabel2->SetObserver( this );
		}
	
	iImage2 = LocateControlByUniqueHandle<CEikImage>( EUIQ_MultiPage_3_0MultiPageViewControl21 );
	if ( iImage2 != NULL )
		{
		iImage2->SetObserver( this );
		}
	
	iNumberEditor2 = LocateControlByUniqueHandle<CQikNumberEditor>( EUIQ_MultiPage_3_0MultiPageViewControl22 );
	if ( iNumberEditor2 != NULL )
		{
		iNumberEditor2->SetObserver( this );
		}
	
	iFloatingPointEditor2 = LocateControlByUniqueHandle<CQikFloatingPointEditor>( EUIQ_MultiPage_3_0MultiPageViewControl23 );
	if ( iFloatingPointEditor2 != NULL )
		{
		iFloatingPointEditor2->SetObserver( this );
		}
	
	// ]]] end [Recover Control From Control Collection]
	
	
	// [[[ begin [Control setup]
		if ( iComboBox1 != NULL ) {		
		HBufC* activeText = iEikonEnv->AllocReadResourceLC( R_UIQ_MULTI_PAGE_3_0_MULTI_PAGE_VIEW_COMBO_BOX1 );
		iComboBox1->SetTextL( activeText );
		CleanupStack::PopAndDestroy( activeText );
		}
				    
		
		if ( iListBox1 != NULL )
			{
			iListBox1->SetListBoxObserver( this );
			}
		
		if ( iDateEditor1 != NULL )
		{
			iDateEditor1->SetTimeL( TTime( TDateTime( 2000, EJanuary, 0, 0, 0, 0, 0 ) ) );
		}
		if ( iFloatingPointEditor1 != NULL )
		{
			iFloatingPointEditor1->SetValueL( 0 );
		}
		if ( iNumberEditor1 != NULL )
		{
			iNumberEditor1->SetValueL( 0 );
		}
		if ( iPlainTextEditor1 != NULL ) {		
		HBufC* text = iEikonEnv->AllocReadResourceLC( R_UIQ_MULTI_PAGE_3_0_MULTI_PAGE_VIEW_PLAIN_TEXT_EDITOR1 );
		iPlainTextEditor1->SetTextL( text );
		CleanupStack::PopAndDestroy( text );
		}
		if ( iPlainTextEditor1 != NULL )
			{
			iPlainTextEditor1->SetEdwinObserver( this );
			}
		
		if ( iRichTextEditor1 != NULL )
			{
			HBufC* text = iEikonEnv->AllocReadResourceLC( R_UIQ_MULTI_PAGE_3_0_MULTI_PAGE_VIEW_RICH_TEXT_EDITOR1 );
			iRichTextEditor1->SetTextL( text );
			CleanupStack::PopAndDestroy( text );
			}
		
		if ( iRichTextEditor1 != NULL )
			{
			iRichTextEditor1->SetEdwinObserver( this );
			}
		
		if ( iTimeEditor1 != NULL )
		{
			iTimeEditor1->SetTimeL( TTime( TDateTime( 0, EJanuary, 0, 0, 0, 0, 0 ) ) );
		}
		if ( iNumberEditor2 != NULL )
		{
			iNumberEditor2->SetValueL( 0 );
		}
		if ( iFloatingPointEditor2 != NULL )
		{
			iFloatingPointEditor2->SetValueL( 0 );
		}
	// ]]] end [Control setup]
	
	// ]]] end generated region [Generated Contents]
	
	}

/** 
 * Helper function to register MCoeControlObserver event handlers
 *
 * @param aControl The control that sent the event
 * @param aEvent The event
 * @param aHandler The Handler
 */
void CUIQ_MultiPage_3_0MultiPageView::AddControlEventHandlerL( 
		CCoeControl* aControl,
		TCoeEvent aEvent,
		ControlEventHandler aHandler )
	{
	// [[[ begin generated region: do not modify [Generated Contents]
		TControlEventDispatch entry;
		entry.src = aControl;
		entry.event = aEvent;
		entry.handler = aHandler;
		iControlEventDispatch.AppendL( entry );
	
				
	// ]]] end generated region [Generated Contents]
	
	}

/** 
 * Helper function to register command event handlers
 *
 * @param aId Command ID
 * @param aHandler The Handler
 */
void CUIQ_MultiPage_3_0MultiPageView::AddCommandEventHandlerL( 
		TInt aId,
		CommandEventHandler aHandler )
	{
	// [[[ begin generated region: do not modify [Generated Contents]
		TCommandEventDispatch entry;
		entry.id = aId;
		entry.handler = aHandler;
		iCommandEventDispatch.AppendL( entry );
	
				
	// ]]] end generated region [Generated Contents]
	
	}

/**
 * Handles list box events.
 * 
 * This function is invoked by CQikListBox to
 * notify the observer of list box events.
 * 
 * @param	aListBox	The originating list box.
 * @param	aEventType	A code for the event. Further information may be obtained by accessing the list box itself.
 * @param	aItemIndex	The item index, if applicable, else EQikListBoxParamNotApplicable.
 * @param	aSlotId		The item slot id the event was generated in, if applicable, else EQikListBoxParamNotApplicable.
 */
void CUIQ_MultiPage_3_0MultiPageView::HandleListBoxEventL( 
		CQikListBox* aListBox,
		TQikListBoxEvent aEventType,
		TInt aItemIndex,
		TInt aSlotId )
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	for ( int i = 0; i < iListBoxEventDispatch.Count(); i++)
		{
		const TListBoxEventDispatch& currEntry = iListBoxEventDispatch[i];
		if ( currEntry.iListBox == aListBox && currEntry.iEventType == aEventType )
			{
			( this->*currEntry.iHandler )( aListBox, aEventType, aItemIndex, aSlotId );
			break;
			}
		}
				
	// ]]] end generated region [Generated Contents]
	
	}

// [[[ begin generated function: do not modify
/**
 * Helper function to register MQikListBoxObserver event handlers
 */
void CUIQ_MultiPage_3_0MultiPageView::AddListBoxEventHandlerL( 
		CQikListBox* aListBox,
		TQikListBoxEvent aEventType,
		ListBoxEventHandler aHandler )
	{
	
	// [[[ begin [Generated Contents]
	TListBoxEventDispatch entry;
	entry.iListBox = aListBox;
	entry.iEventType = aEventType;
	entry.iHandler = aHandler;
	iListBoxEventDispatch.AppendL( entry );
				
	// ]]] end [Generated Contents]
	
	}

// ]]] end generated function

// [[[ begin generated function: do not modify
/**
 * This function add an item to the list box referenced by aListBoxUniqueHandle.
 * 
 * @param	aListBoxUniqueHandle	The unique handle of the listbox the item will be added to.
 * @param	aDataType				The type of data created, can't be changed later on.
 * @param	aListBoxDataItem		Defines the listbox item to add.
 */
void CUIQ_MultiPage_3_0MultiPageView::AddListBoxItemL( 
		TInt aListBoxUniqueHandle,
		MQikListBoxModel::MQikListBoxModelDataType aDataType,
		TListBoxDataItem aListBoxDataItem )
	{
	
	// [[[ begin [Generated Contents]
	CQikListBox* listbox = LocateControlByUniqueHandle < CQikListBox > ( aListBoxUniqueHandle );
	if ( listbox != NULL )
		{
		MQikListBoxModel& model( listbox->Model() );
		model.ModelBeginUpdateLC();
		MQikListBoxData* data = model.NewDataLC( aDataType );
		aListBoxDataItem.AddListBoxItemInfo( data );
		CleanupStack::PopAndDestroy( data );
		model.ModelEndUpdateL();
		}
				
	// ]]] end [Generated Contents]
	
	}

// ]]] end generated function

// [[[ begin generated function: do not modify
/**
 * This functions deletes a set of items from a list box.
 * 
 * @param	aListBoxUniqueHandle	The unique handle of the listbox items will be removed from.
 * @param	aItemIndexes			Indexes of the items to be deleted.
 */
void CUIQ_MultiPage_3_0MultiPageView::RemoveListBoxItemsL( 
		TInt aListBoxUniqueHandle,
		RArray<TInt>& aItemIndexes )
	{
	
	// [[[ begin [Generated Contents]
	CQikListBox* listbox = LocateControlByUniqueHandle < CQikListBox > ( aListBoxUniqueHandle );
	if ( listbox != NULL )
		{
		MQikListBoxModel& model( listbox->Model() );
		model.ModelBeginUpdateLC();
		//Remember to remove the data from the end and forward, to ensure that the indexes are correct
		aItemIndexes.Sort();
		for (TInt i = aItemIndexes.Count() - 1; i >= 0; i--)
			{
			model.RemoveDataL(aItemIndexes[i]);
			}
		model.ModelEndUpdateL();
		}
				
	// ]]] end [Generated Contents]
	
	}

// ]]] end generated function

/** 
 * Helper function to register MEikEdwinObserver event handlers
 */
void CUIQ_MultiPage_3_0MultiPageView::AddEdwinEventHandlerL( 
		CEikEdwin* aEdwin,
		TEdwinEvent aEvent,
		EdwinEventHandler aHandler )
	{
	// [[[ begin generated region: do not modify [Generated Contents]
		TEdwinEventDispatch entry;
		entry.src = aEdwin;
		entry.event = aEvent;
		entry.handler = aHandler;
		iEdwinEventDispatch.AppendL( entry );
				
	// ]]] end generated region [Generated Contents]
	
	}

/** 
 * Override of the HandleEdwinEventL virtual function
 * 
 * @param aEdwin The Edwin control that sent the event.
 * @param aEventType The event type.
 */
void CUIQ_MultiPage_3_0MultiPageView::HandleEdwinEventL( 
		CEikEdwin* aEdwin,
		TEdwinEvent aEventType )
	{
	// [[[ begin generated region: do not modify [Generated Contents]
		for (int i = 0; i < iEdwinEventDispatch.Count(); i++)
			{
			const TEdwinEventDispatch& currEntry = iEdwinEventDispatch[i];
			if ( currEntry.src == aEdwin && currEntry.event == aEventType )
				{
				( this->*currEntry.handler )( aEdwin, aEventType );
				break;
				}
			}						
				
	// ]]] end generated region [Generated Contents]
	
	}


