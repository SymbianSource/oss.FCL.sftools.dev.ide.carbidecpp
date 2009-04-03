// [[[ begin generated region: do not modify [Generated System Includes]
#include <barsread.h>
#include <stringloader.h>
#include <aknlists.h>
#include <eikenv.h>
#include <akniconarray.h>
#include <eikclbd.h>
#include <aknsfld.h>
#include <aknutils.h>
#include <aknviewappui.h>
#include <eikappui.h>
#include <lists_3_0.mbg>
#include <lists_3_0.rsg>
// ]]] end generated region [Generated System Includes]

// [[[ begin generated region: do not modify [Generated User Includes]
#include "lists_3_0ListBox2.h"
#include "lists_3_0ListBox2View.h"
#include "lists_3_0.hrh"
// ]]] end generated region [Generated User Includes]

// [[[ begin generated region: do not modify [Generated Constants]
_LIT( Klists_3_0File, "\\resource\\apps\\lists_3_0.mbm" );
// ]]] end generated region [Generated Constants]

/**
 * First phase of Symbian two-phase construction. Should not 
 * contain any code that could leave.
 */
CLists_3_0ListBox2::CLists_3_0ListBox2()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	iListBox = NULL;
	iListBoxSearchField = NULL;
	// ]]] end generated region [Generated Contents]
	
	}
/** 
 * Destroy child controls.
 */
CLists_3_0ListBox2::~CLists_3_0ListBox2()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	delete iListBox;
	iListBox = NULL;
	delete iListBoxSearchField;
	iListBoxSearchField = NULL;
	// ]]] end generated region [Generated Contents]
	
	}
				
/**
 * Construct the control (first phase).
 *  Creates an instance and initializes it.
 *  Instance is not left on cleanup stack.
 * @param aRect bounding rectangle
 * @param aParent owning parent, or NULL
 * @param aCommandObserver command observer
 * @return initialized instance of CLists_3_0ListBox2
 */
CLists_3_0ListBox2* CLists_3_0ListBox2::NewL( 
		const TRect& aRect, 
		const CCoeControl* aParent, 
		MEikCommandObserver* aCommandObserver )
	{
	CLists_3_0ListBox2* self = CLists_3_0ListBox2::NewLC( 
			aRect, 
			aParent, 
			aCommandObserver );
	CleanupStack::Pop( self );
	return self;
	}

/**
 * Construct the control (first phase).
 *  Creates an instance and initializes it.
 *  Instance is left on cleanup stack.
 * @param aRect The rectangle for this window
 * @param aParent owning parent, or NULL
 * @param aCommandObserver command observer
 * @return new instance of CLists_3_0ListBox2
 */
CLists_3_0ListBox2* CLists_3_0ListBox2::NewLC( 
		const TRect& aRect, 
		const CCoeControl* aParent, 
		MEikCommandObserver* aCommandObserver )
	{
	CLists_3_0ListBox2* self = new ( ELeave ) CLists_3_0ListBox2();
	CleanupStack::PushL( self );
	self->ConstructL( aRect, aParent, aCommandObserver );
	return self;
	}
			
/**
 * Construct the control (second phase).
 *  Creates a window to contain the controls and activates it.
 * @param aRect bounding rectangle
 * @param aCommandObserver command observer
 * @param aParent owning parent, or NULL
 */ 
void CLists_3_0ListBox2::ConstructL( 
		const TRect& aRect, 
		const CCoeControl* aParent, 
		MEikCommandObserver* aCommandObserver )
	{
	if ( aParent == NULL )
	    {
		CreateWindowL();
	    }
	else
	    {
	    SetContainerWindowL( *aParent );
	    }
	iFocusControl = NULL;
	iCommandObserver = aCommandObserver;
	InitializeControlsL();
	SetRect( aRect );
	ActivateL();
	// [[[ begin generated region: do not modify [Post-ActivateL initializations]
	// ]]] end generated region [Post-ActivateL initializations]
	
	}
			
/**
* Return the number of controls in the container (override)
* @return count
*/
TInt CLists_3_0ListBox2::CountComponentControls() const
	{
	return ( int ) ELastControl;
	}
				
/**
* Get the control with the given index (override)
* @param aIndex Control index [0...n) (limited by #CountComponentControls)
* @return Pointer to control
*/
CCoeControl* CLists_3_0ListBox2::ComponentControl( TInt aIndex ) const
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	switch ( aIndex )
		{
	case EListBox:
		return iListBox;
	case EListBoxSearchField:
		return iListBoxSearchField;
		}
	// ]]] end generated region [Generated Contents]
	
	// handle any user controls here...
	
	return NULL;
	}
				
/**
 *	Handle resizing of the container. This implementation will lay out
 *  full-sized controls like list boxes for any screen size, and will layout
 *  labels, editors, etc. to the size they were given in the UI designer.
 *  This code will need to be modified to adjust arbitrary controls to
 *  any screen size.
 */				
void CLists_3_0ListBox2::SizeChanged()
	{
	CCoeControl::SizeChanged();
	LayoutControls();
	// [[[ begin generated region: do not modify [Generated Contents]
			
	AknFind::HandleFixedFindSizeChanged( this, iListBox, iListBoxSearchField );
	// ]]] end generated region [Generated Contents]
	
	}
				
// [[[ begin generated function: do not modify
/**
 * Layout components as specified in the UI Designer
 */
void CLists_3_0ListBox2::LayoutControls()
	{
	iListBox->SetExtent( TPoint( 0, 0 ), iListBox->MinimumSize() );
	}
// ]]] end generated function

/**
 *	Handle key events.
 */				
TKeyResponse CLists_3_0ListBox2::OfferKeyEventL( 
		const TKeyEvent& aKeyEvent, 
		TEventCode aType )
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	if ( aKeyEvent.iCode == EKeyLeftArrow 
		|| aKeyEvent.iCode == EKeyRightArrow )
		{
		// Listbox takes all events even if it doesn't use them
		return EKeyWasNotConsumed;
		}
	if ( iListBoxSearchField != NULL
		&& iListBoxSearchField->OfferKeyEventL( aKeyEvent, aType ) 
			== EKeyWasConsumed )
		{
		return EKeyWasConsumed;
		}
	
	// ]]] end generated region [Generated Contents]
	
	if ( iFocusControl != NULL
		&& iFocusControl->OfferKeyEventL( aKeyEvent, aType ) == EKeyWasConsumed )
		{
		return EKeyWasConsumed;
		}
	return CCoeControl::OfferKeyEventL( aKeyEvent, aType );
	}
				
// [[[ begin generated function: do not modify
/**
 *	Initialize each control upon creation.
 */				
void CLists_3_0ListBox2::InitializeControlsL()
	{
	iListBox = new ( ELeave ) CAknSingleHeadingStyleListBox;
	iListBox->SetContainerWindowL( *this );
		{
		TResourceReader reader;
		iEikonEnv->CreateResourceReaderLC( reader, R_LISTS_3_0_LIST_BOX2_LIST_BOX );
		iListBox->ConstructFromResourceL( reader );
		CleanupStack::PopAndDestroy(); // reader internal state
		}
	// the listbox owns the items in the list and will free them
	iListBox->Model()->SetOwnershipType( ELbmOwnsItemArray );
	
	// setup the icon array so graphics-style boxes work
	SetupListBoxIconsL();
	
	
	// add list items
	AddListBoxResourceArrayItemL( R_LISTS_3_0_LIST_BOX2_LISTBOX_ITEM1 );
	AddListBoxResourceArrayItemL( R_LISTS_3_0_LIST_BOX2_LISTBOX_ITEM2 );
	AddListBoxResourceArrayItemL( R_LISTS_3_0_LIST_BOX2_LISTBOX_ITEM3 );
	
	// set up the Search Field
	
	CAknSearchField* searchField = 
			CAknSearchField::NewL( *this, CAknSearchField::ESearch, 0, 40 );
	
	CleanupStack::PushL ( searchField );
	CAknFilteredTextListBoxModel* filteredModel = 
		static_cast< CAknFilteredTextListBoxModel* >( iListBox->Model() );
	filteredModel->CreateFilterL( iListBox, searchField );
	CleanupStack::Pop( searchField );
	searchField->MakeVisible( ETrue );
	iListBoxSearchField = searchField;
	
	iListBox->SetFocus( ETrue );
	iFocusControl = iListBox;
	
	}
// ]]] end generated function

/** 
 * Handle global resource changes, such as scalable UI or skin events (override)
 */
void CLists_3_0ListBox2::HandleResourceChange( TInt aType )
	{
	CCoeControl::HandleResourceChange( aType );
	SetRect( iAvkonViewAppUi->View( TUid::Uid( ELists_3_0ListBox2ViewId ) )->ClientRect() );
	// [[[ begin generated region: do not modify [Generated Contents]
	// ]]] end generated region [Generated Contents]
	
	}
				
/**
 *	Draw container contents.
 */				
void CLists_3_0ListBox2::Draw( const TRect& aRect ) const
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	CWindowGc& gc = SystemGc();
	gc.Clear( aRect );
	
	// ]]] end generated region [Generated Contents]
	
	}
				
// [[[ begin generated function: do not modify
/**
 *	Add a list box item to a list.
 */
void CLists_3_0ListBox2::AddListBoxItemL( 
		CEikTextListBox* aListBox,
		const TDesC& aString )
	{
	CTextListBoxModel* model = aListBox->Model();
	CDesCArray* itemArray = static_cast< CDesCArray* > ( model->ItemTextArray() );
	itemArray->AppendL( aString );
	aListBox->HandleItemAdditionL();
	}

// ]]] end generated function

// [[[ begin generated function: do not modify
/**
 * Get the array of selected item indices, with respect to the list model.
 * The array is sorted in ascending order.
 * The array should be destroyed with two calls to CleanupStack::PopAndDestroy(),
 * the first with no argument (referring to the internal resource) and the
 * second with the array pointer.
 * @return newly allocated array, which is left on the cleanup stack;
 *	or NULL for empty list. 
 */
RArray< TInt >* CLists_3_0ListBox2::GetSelectedListBoxItemsLC( CEikTextListBox* aListBox )
	{
	CAknFilteredTextListBoxModel* model = 
		static_cast< CAknFilteredTextListBoxModel *> ( aListBox->Model() );
	if ( model->NumberOfItems() == 0 )
		return NULL;
		
	// get currently selected indices
	const CListBoxView::CSelectionIndexArray* selectionIndexes =
		aListBox->SelectionIndexes();
	TInt selectedIndexesCount = selectionIndexes->Count();
	if ( selectedIndexesCount == 0 )
		return NULL;
		
	// copy the indices and sort numerically
	RArray<TInt>* orderedSelectedIndices = 
		new (ELeave) RArray< TInt >( selectedIndexesCount );
	
	// push the allocated array
	CleanupStack::PushL( orderedSelectedIndices );
	
	// dispose the array resource
	CleanupClosePushL( *orderedSelectedIndices );
	
	// see if the search field is enabled
	CAknListBoxFilterItems* filter = model->Filter();
	if ( filter != NULL )
		{
		// when filtering enabled, translate indices back to underlying model
		for ( TInt idx = 0; idx < selectedIndexesCount; idx++ )
			{
			TInt filteredItem = ( *selectionIndexes ) [ idx ];
			TInt actualItem = filter->FilteredItemIndex ( filteredItem );
			orderedSelectedIndices->InsertInOrder( actualItem );
			}
		}
	else
		{
		// the selection indices refer directly to the model
		for ( TInt idx = 0; idx < selectedIndexesCount; idx++ )
			orderedSelectedIndices->InsertInOrder( ( *selectionIndexes ) [ idx ] );
		}	
		
	return orderedSelectedIndices;
	}

// ]]] end generated function

// [[[ begin generated function: do not modify
/**
 * Delete the selected item or items from the list box.
 */
void CLists_3_0ListBox2::DeleteSelectedListBoxItemsL( CEikTextListBox* aListBox )
	{
	CAknFilteredTextListBoxModel* model = 
		static_cast< CAknFilteredTextListBoxModel *> ( aListBox->Model() );
	if ( model->NumberOfItems() == 0 )
		return;
	
	RArray< TInt >* orderedSelectedIndices = GetSelectedListBoxItemsLC( aListBox );		
	if ( !orderedSelectedIndices )
		return;
		
	// Delete selected items from bottom up so indices don't change on us
	CDesCArray* itemArray = static_cast< CDesCArray* > ( model->ItemTextArray() );
	TInt currentItem = 0;
	
	for ( TInt idx = orderedSelectedIndices->Count(); idx-- > 0; ) 
		{
		currentItem = ( *orderedSelectedIndices )[ idx ];
		itemArray->Delete ( currentItem );
		}
	
	// dispose the array resources
	CleanupStack::PopAndDestroy();
	
	// dispose the array pointer
	CleanupStack::PopAndDestroy( orderedSelectedIndices );
	
	// refresh listbox's cursor now that items are deleted
	AknListBoxUtils::HandleItemRemovalAndPositionHighlightL(
		aListBox, currentItem, ETrue );
	}

// ]]] end generated function

// [[[ begin generated function: do not modify
/**
 *	Get the listbox.
 */
CAknSingleHeadingStyleListBox* CLists_3_0ListBox2::ListBox()
	{
	return iListBox;
	}

// ]]] end generated function

// [[[ begin generated function: do not modify
/**
 *	Create a list box item with the given column values.
 */
void CLists_3_0ListBox2::CreateListBoxItemL( TDes& aBuffer, 
		const TDesC& aHeadingText,
		const TDesC& aMainText )
	{
	_LIT ( KStringHeader, "%S\t%S" );
	
	aBuffer.Format( KStringHeader(), &aHeadingText, &aMainText );
	} 
				
// ]]] end generated function

// [[[ begin generated function: do not modify
/**
 *	Add an item to the list by reading the text items from the array resource
 *	and setting a single image property (if available) from an index
 *	in the list box's icon array.
 *	@param aResourceId id of an ARRAY resource containing the textual
 *	items in the columns
 *	
 */
void CLists_3_0ListBox2::AddListBoxResourceArrayItemL( TInt aResourceId )
	{
	CDesCArray* array = iCoeEnv->ReadDesCArrayResourceL( aResourceId );
	CleanupStack::PushL( array );
	// This is intended to be large enough, but if you get 
	// a USER 11 panic, consider reducing string sizes.
	TBuf<512> listString; 
	CreateListBoxItemL( listString, ( *array ) [ 0 ], ( *array ) [ 1 ] );
	AddListBoxItemL( iListBox, listString );
	CleanupStack::PopAndDestroy( array );
	} 
				
// ]]] end generated function

// [[[ begin generated function: do not modify
/**
 *	Set up the list's icon array.
 */
void CLists_3_0ListBox2::SetupListBoxIconsL()
	{
	CArrayPtr< CGulIcon >* icons = NULL;		
	icons = new (ELeave) CAknIconArray( 1 );
	CleanupStack::PushL( icons );
	CGulIcon* icon;
	// for EListBoxLists_3_0List_iconIndex
	icon = LoadAndScaleIconL(
			Klists_3_0File, EMbmLists_3_0List_icon, EMbmLists_3_0List_icon_mask,
			NULL, EAspectRatioPreserved );
	CleanupStack::PushL( icon );
	icons->AppendL( icon );
	CleanupStack::Pop( icon );
	CleanupStack::Pop( icons );
	
	if ( icons != NULL )
		{
		iListBox->ItemDrawer()->ColumnData()->SetIconArray( icons );
		}
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
CGulIcon* CLists_3_0ListBox2::LoadAndScaleIconL( 
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
 *	Handle commands relating to markable lists.
 */
TBool CLists_3_0ListBox2::HandleMarkableListCommandL( TInt aCommand )
	{
	
	switch ( aCommand ) 
		{
		case EAknCmdMark:
		case EAknCmdUnmark:
		case EAknMarkAll:
		case EAknUnmarkAll:
			AknSelectionService::HandleMarkableListProcessCommandL(
				aCommand, iListBox );
			return ETrue;
		}return EFalse;
	}

// ]]] end generated function

