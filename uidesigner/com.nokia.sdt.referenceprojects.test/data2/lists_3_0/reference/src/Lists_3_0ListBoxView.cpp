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
#include <akncontext.h>
#include <akntitle.h>
#include <eikbtgpc.h>
#include <lists_3_0.mbg>
#include <aknlistquerydialog.h>
#include <lists_3_0.rsg>
// ]]] end generated region [Generated System Includes]

// [[[ begin generated region: do not modify [Generated User Includes]

#include "lists_3_0.hrh"
#include "lists_3_0ListBoxView.h"
#include "lists_3_0ListBox.h"
// ]]] end generated region [Generated User Includes]

// [[[ begin generated region: do not modify [Generated Constants]
_LIT( Klists_3_0File, "\\resource\\apps\\lists_3_0.mbm" );
// ]]] end generated region [Generated Constants]

/**
 * First phase of Symbian two-phase construction. Should not contain any
 * code that could leave.
 */
Clists_3_0ListBoxView::Clists_3_0ListBoxView()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	iLists_3_0ListBox = NULL;
	// ]]] end generated region [Generated Contents]
	
	}

/** 
 * The view's destructor removes the container from the control
 * stack and destroys it.
 */
Clists_3_0ListBoxView::~Clists_3_0ListBoxView()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	delete iLists_3_0ListBox;
	iLists_3_0ListBox = NULL;
	// ]]] end generated region [Generated Contents]
	
	}

/**
 * Symbian two-phase constructor.
 * This creates an instance then calls the second-phase constructor
 * without leaving the instance on the cleanup stack.
 * @return new instance of Clists_3_0ListBoxView
 */
Clists_3_0ListBoxView* Clists_3_0ListBoxView::NewL()
	{
	Clists_3_0ListBoxView* self = Clists_3_0ListBoxView::NewLC();
	CleanupStack::Pop( self );
	return self;
	}

/**
 * Symbian two-phase constructor.
 * This creates an instance, pushes it on the cleanup stack,
 * then calls the second-phase constructor.
 * @return new instance of Clists_3_0ListBoxView
 */
Clists_3_0ListBoxView* Clists_3_0ListBoxView::NewLC()
	{
	Clists_3_0ListBoxView* self = new ( ELeave ) Clists_3_0ListBoxView();
	CleanupStack::PushL( self );
	self->ConstructL();
	return self;
	}


/**
 * Second-phase constructor for view.  
 * Initialize contents from resource.
 */ 
void Clists_3_0ListBoxView::ConstructL()
	{
	// [[[ begin generated region: do not modify [Generated Code]
	BaseConstructL( R_LISTS_3_0_LIST_BOX_LISTS_3_0_LIST_BOX_VIEW );
				
	// ]]] end generated region [Generated Code]
	
	// add your own initialization code here
	
	}

/**
 * @return The UID for this view
 */
TUid Clists_3_0ListBoxView::Id() const
	{
	return TUid::Uid( ELists_3_0ListBoxViewId );
	}

/**
 * Handle a command for this view (override)
 * @param aCommand command id to be handled
 */
void Clists_3_0ListBoxView::HandleCommandL( TInt aCommand )
	{
	// [[[ begin generated region: do not modify [Generated Code]
	TBool commandHandled = EFalse;
	switch ( aCommand )
		{	// code to dispatch to the AknView's menu and CBA commands is generated here
		default:
			break;
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
void Clists_3_0ListBoxView::DoActivateL( 
		const TVwsViewId& /*aPrevViewId*/,
		TUid /*aCustomMessageId*/,
		const TDesC8& /*aCustomMessage*/ )
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	SetupStatusPaneL();
	
				
	if ( iLists_3_0ListBox == NULL )
		{
		iLists_3_0ListBox = CreateContainerL();
		iLists_3_0ListBox->SetMopParent( this );
		AppUi()->AddToStackL( *this, iLists_3_0ListBox );
		} 
	// ]]] end generated region [Generated Contents]
	
	}

/**
 */
void Clists_3_0ListBoxView::DoDeactivate()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	CleanupStatusPane();
	
	if ( iLists_3_0ListBox != NULL )
		{
		AppUi()->RemoveFromViewStack( *this, iLists_3_0ListBox );
		delete iLists_3_0ListBox;
		iLists_3_0ListBox = NULL;
		}
	// ]]] end generated region [Generated Contents]
	
	}

/** 
 * Handle status pane size change for this view (override)
 */
void Clists_3_0ListBoxView::HandleStatusPaneSizeChange()
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
void Clists_3_0ListBoxView::SetupStatusPaneL()
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
		iEikonEnv->CreateResourceReaderLC( reader, R_LISTS_3_0_LIST_BOX_TITLE_RESOURCE );
		title->SetFromResourceL( reader );
		CleanupStack::PopAndDestroy(); // reader internal state
		}
				
	}

// ]]] end generated function

// [[[ begin generated function: do not modify
void Clists_3_0ListBoxView::CleanupStatusPane()
	{
	}

// ]]] end generated function

/**
 *	Creates the top-level container for the view.  You may modify this method's
 *	contents and the CLists_3_0ListBox::NewL() signature as needed to initialize the
 *	container, but the signature for this method is fixed.
 *	@return new initialized instance of CLists_3_0ListBox
 */
CLists_3_0ListBox* Clists_3_0ListBoxView::CreateContainerL()
	{
	return CLists_3_0ListBox::NewL( ClientRect(), NULL, this );
	}
// [[[ begin generated function: do not modify
/**
 *	Create a list box item with the given column values.
 */
void Clists_3_0ListBoxView::CreateListQuery1ItemL( 
		TDes& aBuffer, 
		
		TInt aIconIndex,
		const TDesC& aMainText,
		const TDesC& aSecondaryText )
	{
	_LIT ( KStringHeader, "%d\t%S\t%S" );

	aBuffer.Format( KStringHeader(), aIconIndex, &aMainText, &aSecondaryText );
	}
// ]]] end generated function

// [[[ begin generated function: do not modify
/**
 *	Add an item to the list by reading the text items from resource
 *	and setting a single image property (if available) from an index
 *	in the list box's icon array.
 *	@param aResourceId id of an ARRAY resource containing the textual
 *	items in the columns
 *	@param aIconIndex the index in the icon array, or -1
 */
void Clists_3_0ListBoxView::CreateListQuery1ResourceArrayItemL( 
		TDes& aBuffer, 
		TInt aResourceId, TInt aIconIndex )
	{
	CDesCArray* array = CCoeEnv::Static()->ReadDesCArrayResourceL( aResourceId );
	CleanupStack::PushL( array );
	CreateListQuery1ItemL( aBuffer, aIconIndex, ( *array ) [ 0 ], 
		( *array ) [ 1 ] );
	CleanupStack::PopAndDestroy( array );
	}
// ]]] end generated function

// [[[ begin generated function: do not modify
/**
 * Initialize contents of the popup item list.  This constructs the array
 *	and pushes it on the cleanup stack.
 *	@return item array, never null
 */
CDesCArray* Clists_3_0ListBoxView::InitializeListQuery1LC()
	{
	const int KNumItems = 2;
	CDesCArray* itemArray = new ( ELeave ) CDesCArrayFlat( KNumItems ? KNumItems : 1 );
	CleanupStack::PushL( itemArray );
	
	// This is intended to be large enough, but if you get 
	// a USER 11 panic, consider reducing string sizes.
	TBuf< 512 > des;
	CreateListQuery1ResourceArrayItemL(
			des, R_LISTS_3_0_LIST_BOX_LISTBOX_ITEM3,
			EListQuery1Lists_3_0List_iconIndex );
	itemArray->AppendL( des );
	CreateListQuery1ResourceArrayItemL(
			des, R_LISTS_3_0_LIST_BOX_LISTBOX_ITEM4,
			EListQuery1Lists_3_0List_iconIndex );
	itemArray->AppendL( des );
	return itemArray;
	}
// ]]] end generated function

// [[[ begin generated function: do not modify
/**
 *	Set up the list query's icon array.  If any icons are used, allocates an
 *	icon array, and places it on the cleanup stack. 
 *	@return icon array, or NULL
 */
CArrayPtr< CGulIcon >* Clists_3_0ListBoxView::SetupListQuery1IconsLC()
	{
	CArrayPtr< CGulIcon >* icons = NULL;
	icons = new (ELeave) CAknIconArray( 1 );
	CleanupStack::PushL( icons );
	CGulIcon* icon;
	// for EListQuery1Lists_3_0List_iconIndex
	icon = LoadAndScaleIconL(
			Klists_3_0File, EMbmLists_3_0List_icon, EMbmLists_3_0List_icon_mask,
			NULL, EAspectRatioPreserved );
	CleanupStack::PushL( icon );
	icons->AppendL( icon );
	CleanupStack::Pop( icon );
	return icons;
	}
	
// ]]] end generated function

// [[[ begin generated function: do not modify
/**
 *	This routine loads and scales a bitmap or icon.
 *
 *	@param aFileName the MBM or MIF filename
 *	@param aBitmapId the bitmap id
 *	@param aMaskId the mask id or -1 for none
 *	@param aSize the TSize for the icon, or NULL to use real size
 *	@param aScaleMode one of the EAspectRatio* enums when scaling
 *
 */
CGulIcon* Clists_3_0ListBoxView::LoadAndScaleIconL( 
		const TDesC& aFileName,
		TInt aBitmapId, 
		TInt aMaskId, 
		TSize* aSize, 
		TScaleMode aScaleMode )
	{
	CFbsBitmap* bitmap;
	CFbsBitmap* mask;
	AknIconUtils::CreateIconL( bitmap, mask, aFileName, aBitmapId, aMaskId );
	
	TSize size;
	if ( aSize == NULL )
		{
		// Use size from the image header.  In case of SVG,
		// we preserve the image data for a while longer, since ordinarily
		// it is disposed at the first GetContentDimensions() or SetSize() call.
		AknIconUtils::PreserveIconData( bitmap );
		AknIconUtils::GetContentDimensions( bitmap, size );
		}
	else
		{
		size = *aSize;
		}
	
	AknIconUtils::SetSize( bitmap, size, aScaleMode );
	AknIconUtils::SetSize( mask, size, aScaleMode );
	
	if ( aSize == NULL )
		{
		AknIconUtils::DestroyIconData( bitmap );
		}
	
	return CGulIcon::NewL( bitmap, mask );
	}

// ]]] end generated function

// [[[ begin generated function: do not modify
/**
 * Show the popup list query dialog for listQuery1.
 * <p>
 * You may override the designer-specified items or icons, though generally
 * both should be overridden together.
 * @param aOverrideText optional override text
 * @param aOverrideItemArray if not NULL, the array of formatted list items to display (passes ownership)
 * @param aOverrideIconArray if not NULL, the array of icons to display (passes ownership)
 * @return selected index (>=0) or -1 for Cancel
 */
TInt Clists_3_0ListBoxView::RunListQuery1L( 
		const TDesC* aOverrideText,
		CDesCArray* aOverrideItemArray,
		CArrayPtr< CGulIcon >* aOverrideIconArray )
	{
	TInt index = 0;
	CAknListQueryDialog* queryDialog = NULL;
	queryDialog = new ( ELeave ) CAknListQueryDialog( &index );	
	CleanupStack::PushL( queryDialog );
	
	queryDialog->PrepareLC( R_LISTS_3_0_LIST_BOX_LIST_QUERY1 );
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
		itemArray = InitializeListQuery1LC();
		}
		
	queryDialog->SetItemTextArray( itemArray );
	queryDialog->SetOwnershipType( ELbmOwnsItemArray );
	CleanupStack::Pop( itemArray );
	
	// initialize list icons
	CArrayPtr< CGulIcon >* iconArray = NULL;
	
	if ( aOverrideIconArray != NULL )
		{
		CleanupStack::PushL( aOverrideIconArray );
		iconArray = aOverrideIconArray;
		}
	else
		{
		iconArray = SetupListQuery1IconsLC();
		}
		
	if ( iconArray != NULL ) 
		{
		queryDialog->SetIconArrayL( iconArray );	// passes ownership
		CleanupStack::Pop( iconArray );
		}
	
	// run dialog
	TInt result = queryDialog->RunLD();
	
	// clean up
	CleanupStack::Pop( queryDialog );
	
	return result == 0 ? -1 : index;
	}
	
// ]]] end generated function


