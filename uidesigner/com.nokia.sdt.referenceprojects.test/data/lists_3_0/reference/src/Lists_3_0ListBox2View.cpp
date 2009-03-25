// [[[ begin generated region: do not modify [Generated System Includes]
#include <aknviewappui.h>
#include <eikmenub.h>
#include <avkon.hrh>
#include <barsread.h>
#include <stringloader.h>
#include <aknlists.h>
#include <eikenv.h>
#include <akniconarray.h>
#include <eikclbd.h>
#include <aknsfld.h>
#include <aknutils.h>
#include <akncontext.h>
#include <akntitle.h>
#include <eikbtgpc.h>
#include <aknlistquerydialog.h>
#include <lists_3_0.rsg>
// ]]] end generated region [Generated System Includes]

// [[[ begin generated region: do not modify [Generated User Includes]

#include "lists_3_0.hrh"
#include "lists_3_0ListBox2View.h"
#include "lists_3_0ListBox2.h"
// ]]] end generated region [Generated User Includes]

// [[[ begin generated region: do not modify [Generated Constants]
// ]]] end generated region [Generated Constants]

/**
 * First phase of Symbian two-phase construction. Should not contain any
 * code that could leave.
 */
Clists_3_0ListBox2View::Clists_3_0ListBox2View()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	iLists_3_0ListBox2 = NULL;
	// ]]] end generated region [Generated Contents]
	
	}

/** 
 * The view's destructor removes the container from the control
 * stack and destroys it.
 */
Clists_3_0ListBox2View::~Clists_3_0ListBox2View()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	delete iLists_3_0ListBox2;
	iLists_3_0ListBox2 = NULL;
	// ]]] end generated region [Generated Contents]
	
	}

/**
 * Symbian two-phase constructor.
 * This creates an instance then calls the second-phase constructor
 * without leaving the instance on the cleanup stack.
 * @return new instance of Clists_3_0ListBox2View
 */
Clists_3_0ListBox2View* Clists_3_0ListBox2View::NewL()
	{
	Clists_3_0ListBox2View* self = Clists_3_0ListBox2View::NewLC();
	CleanupStack::Pop( self );
	return self;
	}

/**
 * Symbian two-phase constructor.
 * This creates an instance, pushes it on the cleanup stack,
 * then calls the second-phase constructor.
 * @return new instance of Clists_3_0ListBox2View
 */
Clists_3_0ListBox2View* Clists_3_0ListBox2View::NewLC()
	{
	Clists_3_0ListBox2View* self = new ( ELeave ) Clists_3_0ListBox2View();
	CleanupStack::PushL( self );
	self->ConstructL();
	return self;
	}


/**
 * Second-phase constructor for view.  
 * Initialize contents from resource.
 */ 
void Clists_3_0ListBox2View::ConstructL()
	{
	// [[[ begin generated region: do not modify [Generated Code]
	BaseConstructL( R_LISTS_3_0_LIST_BOX2_LISTS_3_0_LIST_BOX2_VIEW );
				
	// ]]] end generated region [Generated Code]
	
	// add your own initialization code here
	
	}

/**
 * @return The UID for this view
 */
TUid Clists_3_0ListBox2View::Id() const
	{
	return TUid::Uid( ELists_3_0ListBox2ViewId );
	}

/**
 * Handle a command for this view (override)
 * @param aCommand command id to be handled
 */
void Clists_3_0ListBox2View::HandleCommandL( TInt aCommand )
	{
	// [[[ begin generated region: do not modify [Generated Code]
	TBool commandHandled = EFalse;
	switch ( aCommand )
		{	// code to dispatch to the AknView's menu and CBA commands is generated here
		default:
			break;
		}
	
	// markable list command handling
	if ( !commandHandled ) 
		{
		commandHandled = iLists_3_0ListBox2->HandleMarkableListCommandL( aCommand );
		}
		
	if ( !commandHandled ) 
		{
	
		if ( aCommand == EAknSoftkeyExit )
			{
			AppUi()->HandleCommandL( EEikCmdExit );
			}
	
		}
	// ]]] end generated region [Generated Code]
	
	}

/**
 *	Handles user actions during activation of the view, 
 *	such as initializing the content.
 */
void Clists_3_0ListBox2View::DoActivateL( 
		const TVwsViewId& /*aPrevViewId*/,
		TUid /*aCustomMessageId*/,
		const TDesC8& /*aCustomMessage*/ )
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	SetupStatusPaneL();
	
				
	if ( iLists_3_0ListBox2 == NULL )
		{
		iLists_3_0ListBox2 = CreateContainerL();
		iLists_3_0ListBox2->SetMopParent( this );
		AppUi()->AddToStackL( *this, iLists_3_0ListBox2 );
		} 
	// ]]] end generated region [Generated Contents]
	
	}

/**
 */
void Clists_3_0ListBox2View::DoDeactivate()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	CleanupStatusPane();
	
	if ( iLists_3_0ListBox2 != NULL )
		{
		AppUi()->RemoveFromViewStack( *this, iLists_3_0ListBox2 );
		delete iLists_3_0ListBox2;
		iLists_3_0ListBox2 = NULL;
		}
	// ]]] end generated region [Generated Contents]
	
	}

/** 
 * Handle status pane size change for this view (override)
 */
void Clists_3_0ListBox2View::HandleStatusPaneSizeChange()
	{
	CAknView::HandleStatusPaneSizeChange();
	
	// this may fail, but we're not able to propagate exceptions here
	TVwsViewId view;
	AppUi()->GetActiveViewId( view );
	if ( view.iViewUid == Id() )
		{
		TInt result;
		TRAP( result, SetupStatusPaneL() );
		}
	
	// [[[ begin generated region: do not modify [Generated Code]
	// ]]] end generated region [Generated Code]
	
	}

// [[[ begin generated function: do not modify
void Clists_3_0ListBox2View::SetupStatusPaneL()
	{
	// reset the context pane
	TUid contextPaneUid = TUid::Uid( EEikStatusPaneUidContext );
	CEikStatusPaneBase::TPaneCapabilities subPaneContext = 
		StatusPane()->PaneCapabilities( contextPaneUid );
	if ( subPaneContext.IsPresent() && subPaneContext.IsAppOwned() )
		{
		CAknContextPane* context = static_cast< CAknContextPane* > ( 
			StatusPane()->ControlL( contextPaneUid ) );
		context->SetPictureToDefaultL();
		}
	
	// setup the title pane
	TUid titlePaneUid = TUid::Uid( EEikStatusPaneUidTitle );
	CEikStatusPaneBase::TPaneCapabilities subPaneTitle = 
		StatusPane()->PaneCapabilities( titlePaneUid );
	if ( subPaneTitle.IsPresent() && subPaneTitle.IsAppOwned() )
		{
		CAknTitlePane* title = static_cast< CAknTitlePane* >( 
			StatusPane()->ControlL( titlePaneUid ) );
		TResourceReader reader;
		iEikonEnv->CreateResourceReaderLC( reader, R_LISTS_3_0_LIST_BOX2_TITLE_RESOURCE );
		title->SetFromResourceL( reader );
		CleanupStack::PopAndDestroy(); // reader internal state
		}
				
	}

// ]]] end generated function

// [[[ begin generated function: do not modify
void Clists_3_0ListBox2View::CleanupStatusPane()
	{
	}

// ]]] end generated function

/**
 *	Creates the top-level container for the view.  You may modify this method's
 *	contents and the CLists_3_0ListBox2::NewL() signature as needed to initialize the
 *	container, but the signature for this method is fixed.
 *	@return new initialized instance of CLists_3_0ListBox2
 */
CLists_3_0ListBox2* Clists_3_0ListBox2View::CreateContainerL()
	{
	return CLists_3_0ListBox2::NewL( ClientRect(), NULL, this );
	}
// [[[ begin generated function: do not modify
/**
 *	Create a list box item with the given column values.
 */
void Clists_3_0ListBox2View::CreateListMultiQuery1ItemL( 
		TDes& aBuffer, 
		TBool aSelected, 
		
		const TDesC& aMainText,
		const TDesC& aSecondaryText )
	{
	_LIT ( KStringHeader, "%S\t%S\t" );

	aBuffer.Format( KStringHeader(), &aMainText, &aSecondaryText );
	}
// ]]] end generated function

// [[[ begin generated function: do not modify
/**
 *	Add an item to the list by reading the text items from resource.
 *	@param aSelected ETrue if item is initially selected
 *	@param aResourceId id of an ARRAY resource containing the textual
 *	items in the columns
 *	
 */
void Clists_3_0ListBox2View::CreateListMultiQuery1ResourceArrayItemL( 
		TDes& aBuffer, 
		TBool aSelected, 
		TInt aResourceId )
	{
	CDesCArray* array = CCoeEnv::Static()->ReadDesCArrayResourceL( aResourceId );
	CleanupStack::PushL( array );
	CreateListMultiQuery1ItemL( aBuffer, aSelected, ( *array ) [ 0 ], ( *array ) [ 1 ] );
	CleanupStack::PopAndDestroy( array );
	}
// ]]] end generated function

// [[[ begin generated function: do not modify
/**
 * Initialize contents of the popup item list.  This constructs the array
 *	and pushes it on the cleanup stack.
 *	@return item array, never null
 */
CDesCArray* Clists_3_0ListBox2View::InitializeListMultiQuery1LC()
	{
	const int KNumItems = 2;
	CDesCArray* itemArray = NULL;
	itemArray = new ( ELeave ) CDesCArrayFlat( KNumItems ? KNumItems : 1 );
	CleanupStack::PushL( itemArray );
	
	// This is intended to be large enough, but if you get 
	// a USER 11 panic, consider reducing string sizes.
	TBuf< 512 > des;
	CreateListMultiQuery1ResourceArrayItemL(
			des, EFalse, R_LISTS_3_0_LIST_BOX2_LISTBOX_ITEM4 );
	itemArray->AppendL( des );
	CreateListMultiQuery1ResourceArrayItemL(
			des, EFalse, R_LISTS_3_0_LIST_BOX2_LISTBOX_ITEM5 );
	itemArray->AppendL( des );
	return itemArray;
	}
// ]]] end generated function

// [[[ begin generated function: do not modify
/**
 * Show the popup list query dialog for listMultiQuery1, 
 * allowing zero or more items to be selected.  
 * <p>
 * This returns either the list of selected indices (which may be empty)
 * or NULL if the user canceled.  If non-NULL, you must clean up 
 * the indices.  For instance:<p>
 * <pre>
 *		CListBoxView::CSelectionIndexArray* indices = RunListMultiQuery1LC();
 *		if ( indices )
 *			{
 *			... use indices ...
 *			CleanupStack::PopAndDestroy( indices );
 *			}
 * </pre>
 *
 * @param aOverrideText optional override prompt text
 * @param aOverrideItemArray if not NULL, overridden array of formatted list items (passes ownership)
 * @return array of selected indices (left on cleanup stack) or NULL if user canceled
 */
CListBoxView::CSelectionIndexArray* Clists_3_0ListBox2View::RunListMultiQuery1LC( 
		const TDesC* aOverrideText,
		CDesCArray* aOverrideItemArray )
	{
	CListBoxView::CSelectionIndexArray* indexArray = 
			new ( ELeave ) CArrayFixFlat< TInt >( 1 );
	CleanupStack::PushL( indexArray );
		
	CAknListQueryDialog* queryDialog = 
			new ( ELeave ) CAknListQueryDialog( indexArray );
	CleanupStack::PushL( queryDialog );
	
	queryDialog->PrepareLC( R_LISTS_3_0_LIST_BOX2_LIST_MULTI_QUERY1 );
	if ( aOverrideText != NULL )
		{
		queryDialog->SetHeaderTextL( *aOverrideText );
		}
		
	// initialize list items
	CDesCArray* itemArray = NULL;
	
	if ( aOverrideItemArray != NULL ) 
		{
		CleanupStack::PushL( aOverrideItemArray );
		itemArray = aOverrideItemArray;
		}
	else
		{
		itemArray = InitializeListMultiQuery1LC();
		}
		
	queryDialog->SetItemTextArray( itemArray );
	queryDialog->SetOwnershipType( ELbmOwnsItemArray );
	CleanupStack::Pop( itemArray );
	
	// run dialog
	TInt result = queryDialog->RunLD();
	
	// clean up
	CleanupStack::Pop( queryDialog );
	
	if ( result == 0 )
		{
		CleanupStack::PopAndDestroy( indexArray );
		return NULL;
		}
	else
		{
		return indexArray;
		}
	}
	
// ]]] end generated function


