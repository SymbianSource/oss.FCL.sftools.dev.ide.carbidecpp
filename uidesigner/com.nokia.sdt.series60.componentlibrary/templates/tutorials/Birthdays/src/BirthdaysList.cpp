/*
========================================================================
 Name		: BirthdaysList.cpp

 Description: 

 Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
 All rights reserved.
 This component and the accompanying materials are made available
 under the terms of the License "Eclipse Public License v1.0"
 which accompanies this distribution, and is available
 at the URL "http://www.eclipse.org/legal/epl-v10.html".

 Contributors:
 Nokia Corporation - initial contribution.
========================================================================
*/
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
#include <aknconsts.h>
#include <avkon.mbg>
#include <$(baseName).rsg>
#include <$(baseName)_aif.mbg>
// ]]] end generated region [Generated System Includes]

// [[[ begin generated region: do not modify [Generated User Includes]
#include "BirthdaysList.h"
#include "BirthdaysListView.h"
#include "$(baseName).hrh"
#include "BirthdaysList.hrh"
#include "BirthdayForm.hrh"
// ]]] end generated region [Generated User Includes]

#include "BirthdayForm.h"
#include "BirthdaysDocument.h"

// [[[ begin generated region: do not modify [Generated Constants]
_LIT( K$(baseName$titlelower)_aifFile, "\\resource\\apps\\$(baseName)_aif.mif" );
// ]]] end generated region [Generated Constants]

/**
 * First phase of Symbian two-phase construction. Should not 
 * contain any code that could leave.
 */
CBirthdaysList::CBirthdaysList()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	iListBox = NULL;
	iListBoxSearchField = NULL;
	// ]]] end generated region [Generated Contents]
	
	}
/** 
 * Destroy child controls.
 */
CBirthdaysList::~CBirthdaysList()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	delete iListBox;
	iListBoxEventDispatch.Close();		
	delete iListBoxSearchField;
	// ]]] end generated region [Generated Contents]
	
	}
				
/**
 * Construct the control (first phase).
 *  Creates an instance and initializes it.
 *  Instance is not left on cleanup stack.
 * @param aRect bounding rectangle
 * @param aParent owning parent, or NULL
 * @param aCommandObserver command observer
 * @return initialized instance of CBirthdaysList
 */
CBirthdaysList* CBirthdaysList::NewL( 
		const TRect& aRect, 
		const CCoeControl* aParent, 
		MEikCommandObserver* aCommandObserver )
	{
	CBirthdaysList* self = CBirthdaysList::NewLC( aRect, aParent, aCommandObserver );
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
 * @return new instance of CBirthdaysList
 */
CBirthdaysList* CBirthdaysList::NewLC( 
		const TRect& aRect, 
		const CCoeControl* aParent, 
		MEikCommandObserver* aCommandObserver )
	{
	CBirthdaysList* self = new (ELeave) CBirthdaysList();
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
void CBirthdaysList::ConstructL( 
		const TRect& aRect, 
		const CCoeControl* aParent, 
		MEikCommandObserver* aCommandObserver )
	{
	if ( !aParent )
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
TInt CBirthdaysList::CountComponentControls() const
	{
	return (int) ELastControl;
	}
				
/**
* Get the control with the given index (override)
* @param aIndex Control index [0...n) (limited by #CountComponentControls)
* @return Pointer to control
*/
CCoeControl* CBirthdaysList::ComponentControl( TInt aIndex ) const
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
void CBirthdaysList::SizeChanged()
	{
	CCoeControl::SizeChanged();
	LayoutControls();
	// [[[ begin generated region: do not modify [Generated Contents]
			
	AknFind::HandlePopupFindSizeChanged( this, iListBox, iListBoxSearchField );
	// ]]] end generated region [Generated Contents]
	
	}
				
// [[[ begin generated function: do not modify
/**
 * Layout components as specified in the UI Designer
 */
void CBirthdaysList::LayoutControls()
	{
	iListBox->SetExtent( TPoint( 0, 0 ), iListBox->MinimumSize() );
	}
// ]]] end generated function

/**
 *	Handle key events.
 */				
TKeyResponse CBirthdaysList::OfferKeyEventL( const TKeyEvent& aKeyEvent, TEventCode aType )
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	if ( ( aKeyEvent.iCode == EKeyLeftArrow ) || ( aKeyEvent.iCode == EKeyRightArrow ) )
		{
			// Listbox takes all events even if it doesn't use them
			return EKeyWasNotConsumed;
		}
	if ( iListBoxSearchField && 
			iListBoxSearchField->OfferKeyEventL( aKeyEvent, aType ) == EKeyWasConsumed )
		return EKeyWasConsumed;
	
	// ]]] end generated region [Generated Contents]
	
	if ( iFocusControl &&
			iFocusControl->OfferKeyEventL( aKeyEvent, aType ) == EKeyWasConsumed )
		return EKeyWasConsumed;
	return CCoeControl::OfferKeyEventL( aKeyEvent, aType );
	}
				
// [[[ begin generated function: do not modify
/**
 *	Initialize each control upon creation.
 */				
void CBirthdaysList::InitializeControlsL()
	{
	iListBox = new (ELeave) CAknDoubleLargeStyleListBox;
	iListBox->SetContainerWindowL( *this );
		{
		TResourceReader reader;
		iEikonEnv->CreateResourceReaderLC( reader, R_BIRTHDAYS_LIST_LIST_BOX );
		iListBox->ConstructFromResourceL( reader );
		CleanupStack::PopAndDestroy();
		}
	// the listbox owns the items in the list and will free them
	iListBox->Model()->SetOwnershipType( ELbmOwnsItemArray );
	
	// setup the icon array so graphics-style boxes work
	SetupListBoxIconsL();
	
	iListBox->SetListBoxObserver( this );
	AddListBoxEventHandlerL( iListBox, EEventEnterKeyPressed, &CBirthdaysList::HandleListBoxEnterKeyPressedL );
	
	
	// add list items
	
	// set up the Search Field
	CAknSearchField* searchField = CAknSearchField::NewL( *this, CAknSearchField::EPopup, 0, 40 );
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
void CBirthdaysList::HandleResourceChange( TInt aType )
	{
	CCoeControl::HandleResourceChange( aType );
	SetRect( iAvkonViewAppUi->View( TUid::Uid( EBirthdaysListViewId ) )->ClientRect() );
	// [[[ begin generated region: do not modify [Generated Contents]
	// ]]] end generated region [Generated Contents]
	
	}
				
/**
 *	Draw container contents.
 */				
void CBirthdaysList::Draw( const TRect& aRect ) const
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	CWindowGc& gc = SystemGc();
	gc.Clear( aRect );
	
	// ]]] end generated region [Generated Contents]
	
	}
				
/**
 *	Add a list box item to a list.
 */
void CBirthdaysList::AddListBoxItemL( CEikTextListBox* aListBox, const TDesC& aString )
	{
	CTextListBoxModel* model = aListBox->Model();
	CDesCArray* itemArray = static_cast< CDesCArray* > ( model->ItemTextArray() );
	itemArray->AppendL( aString );
	aListBox->HandleItemAdditionL();
	}
				
// [[[ begin generated function: do not modify
/**
 *	Get the listbox.
 */
CAknDoubleLargeStyleListBox* CBirthdaysList::ListBox()
	{
	return iListBox;
	}
// ]]] end generated function

// [[[ begin generated function: do not modify
/**
 *	Create a list box item with the given column values.
 */
void CBirthdaysList::CreateListBoxItemL( TDes& aBuffer, TInt aIconIndex,
		const TDesC& aMainText,
		const TDesC& aSecondaryText )
	{
	_LIT ( KStringHeader, "%d\t%S\t%S" );

	aBuffer.Format( KStringHeader(), aIconIndex, &aMainText, &aSecondaryText );
	}
// ]]] end generated function

// [[[ begin generated function: do not modify
/**
 *	Add an item to the list by reading the text items from the array resource
 *	and setting a single image property (if available) from an index
 *	in the list box's icon array.
 *	@param aResourceId id of an ARRAY resource containing the textual
 *	items in the columns
 *	@param aIconIndex the index in the icon array, or -1
 */
void CBirthdaysList::AddListBoxResourceArrayItemL( TInt aResourceId, TInt aIconIndex )
	{
	CDesCArray* array = iCoeEnv->ReadDesCArrayResourceL( aResourceId );
	CleanupStack::PushL( array );
	// This is intended to be large enough, but if you get 
	// a USER 11 panic, consider reducing string sizes.
	TBuf<512> listString; 
	CreateListBoxItemL( listString, aIconIndex, ( *array ) [ 0 ], 
		( *array ) [ 1 ] );
	AddListBoxItemL( iListBox, listString );
	CleanupStack::PopAndDestroy( array );
	}
// ]]] end generated function

/**
 *	Set up the list's icon array.  This should be called before 
 *	activating the container.
 */
void CBirthdaysList::SetupListBoxIconsL()
	{
	CArrayPtr< CGulIcon >* icons = NULL;		
	// [[[ begin generated region: do not modify [Generated Code]
	icons = new (ELeave) CAknIconArray( 4 );
	CleanupStack::PushL( icons );
	CGulIcon* icon;
	// for EListBoxAvkonQgn_indi_marked_addIndex
	icon = LoadAndScaleIconL(
			KAvkonBitmapFile, EMbmAvkonQgn_indi_marked_add, EMbmAvkonQgn_indi_marked_add_mask,
			NULL, EAspectRatioPreserved );
	CleanupStack::PushL( icon );
	icons->AppendL( icon );
	CleanupStack::Pop( icon );
	// for EListBox$(baseName$titlelower)_aifPresentIndex
	icon = LoadAndScaleIconL(
			K$(baseName$titlelower)_aifFile, EMbm$(baseName$titlelower)_aifPresent, EMbm$(baseName$titlelower)_aifPresent_mask,
			NULL, EAspectRatioPreserved );
	CleanupStack::PushL( icon );
	icons->AppendL( icon );
	CleanupStack::Pop( icon );
	// for EListBox$(baseName$titlelower)_aifPresent_exclIndex
	icon = LoadAndScaleIconL(
			K$(baseName$titlelower)_aifFile, EMbm$(baseName$titlelower)_aifPresent_excl, EMbm$(baseName$titlelower)_aifPresent_excl_mask,
			NULL, EAspectRatioPreserved );
	CleanupStack::PushL( icon );
	icons->AppendL( icon );
	CleanupStack::Pop( icon );
	CleanupStack::Pop( icons );
	// ]]] end generated region [Generated Code]
	
	if ( icons != NULL )
		{
		iListBox->ItemDrawer()->ColumnData()->SetIconArray( icons );
		}
	}
	
/** 
 *	Handle commands relating to markable lists.
 */
TBool CBirthdaysList::HandleMarkableListCommandL( TInt aCommand )
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	 
	switch ( aCommand ) 
		{
		case EAknCmdMark:
		case EAknCmdUnmark:
		case EAknMarkAll:
		case EAknUnmarkAll:
			AknSelectionService::HandleMarkableListProcessCommandL(
				aCommand, iListBox );
			return ETrue;
		}
	
	// ]]] end generated region [Generated Contents]
	
	return EFalse;
	}
	
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
CGulIcon* CBirthdaysList::LoadAndScaleIconL( const TDesC& aFileName, TInt aBitmapId, TInt aMaskId, TSize* aSize, TScaleMode aScaleMode )
	{
	CFbsBitmap *bitmap, *mask;
	AknIconUtils::CreateIconL( bitmap, mask, aFileName, aBitmapId, aMaskId );
	
	TSize size;
	if ( !aSize )
		{
		// Use size from the image header.  In case of SVG,
		// we preserve the image data for a while longer, since ordinarily
		// it is disposed at the first GetContentDimensions() or SetSize() call.
		AknIconUtils::PreserveIconData( bitmap );
		AknIconUtils::GetContentDimensions( bitmap, size );
		}
	else
		size = *aSize;
	
	AknIconUtils::SetSize( bitmap, size, aScaleMode );
	AknIconUtils::SetSize( mask, size, aScaleMode );
	
	if ( !aSize )
		AknIconUtils::DestroyIconData( bitmap );
	
	return CGulIcon::NewL( bitmap, mask );
	}

// ]]] end generated function

/**
 * Delete the selected item or items from the list box.
 */
void CBirthdaysList::DeleteSelectedListBoxItemsL( CEikTextListBox* aListBox )
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

/**
 * Get the array of selected item indices, with respect to the list model.
 * The array is sorted in ascending order.
 * The array should be destroyed with two calls to CleanupStack::PopAndDestroy(),
 * the first with no argument (referring to the internal resource) and the
 * second with the array pointer.
 * @return newly allocated array, which is left on the cleanup stack;
 *	or NULL for empty list. 
 */
RArray< TInt >* CBirthdaysList::GetSelectedListBoxItemsLC( CEikTextListBox* aListBox )
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

void CBirthdaysList::FormatBirthdayListItemL( TBuf<256>& des, TInt anIndex )
	{
	TBuf<100> info;
		
	_LIT( KDateFormat, "%F%N %*D" );
	_LIT( KAgeFormat, " (age %d)" );
	TBirthday* birthday = iBirthdaysDocument->Birthday( anIndex );

	birthday->Date().FormatL( info, KDateFormat ); 
	
	TInt iconIndex = EListBox$(baseName$titlelower)_aifPresentIndex;
	TInt daysAway = TBirthday::GetDaysUntil( *birthday );
	if ( daysAway <= 7 )
		{
		iconIndex = EListBox$(baseName$titlelower)_aifPresent_exclIndex;
		TBuf<30> ageBuf;
		TInt age = TBirthday::GetNextAge( *birthday );
		ageBuf.Format( KAgeFormat, age );
		info.Append( ageBuf );
		}
	
	CreateListBoxItemL( des, iconIndex, birthday->Name(), info );
	}
	
/**
 * 	Update a birthday entry.
 */
void CBirthdaysList::UpdateBirthdayL( TInt anIndex )
	{
	CAknFilteredTextListBoxModel* model = 
		static_cast< CAknFilteredTextListBoxModel *> ( iListBox->Model() );

	CDesCArray* itemArray = static_cast< CDesCArray* > ( model->ItemTextArray() );
	
	TBuf<256> des;
	FormatBirthdayListItemL( des, anIndex );

	// replace item (can't seem to update and refresh one item?)
	itemArray->Delete ( anIndex );
	iListBox->HandleItemRemovalL();
	itemArray->InsertL ( anIndex, des );
	iListBox->HandleItemAdditionL();

	iBirthdaysDocument->SetChanged( ETrue );		
	}


/**
 * 	Add a birthday from the document into the list.
 */
void CBirthdaysList::AddBirthdayL( TInt anIndex )
	{
	CAknFilteredTextListBoxModel* model = 
		static_cast< CAknFilteredTextListBoxModel *> ( iListBox->Model() );

	CDesCArray* itemArray = static_cast< CDesCArray* > ( model->ItemTextArray() );
	
	TBuf<256> des;
	
	FormatBirthdayListItemL( des, anIndex );

	itemArray->AppendL( des );
	
	// refresh listbox now that items are added
	iListBox->HandleItemAdditionL();
	}

/**
 * 	Load birthdays from document.
 */
void CBirthdaysList::LoadBirthdaysL()
	{
	if ( !iBirthdaysDocument )
		return;
		
	CAknFilteredTextListBoxModel* model = 
		static_cast< CAknFilteredTextListBoxModel *> ( iListBox->Model() );

	CDesCArray* itemArray = static_cast< CDesCArray* > ( model->ItemTextArray() );
	itemArray->Reset();
	
	// populate the list with model data
	TInt count = iBirthdaysDocument->NumBirthdays();
	for ( TInt i = 0; i < count; i++ )
		{
		AddBirthdayL( i );
		}
	}
	
void CBirthdaysList::DeleteBirthdaysL()
	{
	RArray< TInt >* selectedBirthdays = GetSelectedListBoxItemsLC( iListBox );
	if ( !selectedBirthdays )
		return;
		
	// delete from model, in reverse order
	for ( TInt idx = selectedBirthdays->Count(); idx-- > 0; ) 
		iBirthdaysDocument->DeleteBirthdayL( ( *selectedBirthdays )[ idx ] );
	
	// remove from list
	DeleteSelectedListBoxItemsL( iListBox );
	
	CleanupStack::PopAndDestroy(); // internal resource
	CleanupStack::PopAndDestroy( selectedBirthdays );
	}

void CBirthdaysList::RunAddBirthdayL()
	{
	CBirthdayForm* form = CBirthdayForm::NewL( NULL );

	form->PrepareLC( R_BIRTHDAY_FORM_BIRTHDAY_FORM );

	// make a birthday
	TInt index = iBirthdaysDocument->AddBirthdayL();
	TBirthday* birthday = iBirthdaysDocument->Birthday( index );

	// tell the form which birthday to modify, and to use defaults
	form->SetBirthday( birthday, ETrue );

	// activate the form to edit the new birthday
	form->RunLD();	// Note, return is always 0 since we use Back to leave the form.
	
	if ( form->IsBirthdayChanged() )
		{
		// add birthday to list
		AddBirthdayL( index );

		// save changes		
		iBirthdaysDocument->SaveL();
		}
	else
		{
		// throw away new birthday
		iBirthdaysDocument->DeleteBirthdayL( index );
		}
	}		

void CBirthdaysList::RunEditBirthdayL()
	{
	CBirthdayForm* form = CBirthdayForm::NewL( NULL );

	form->PrepareLC( R_BIRTHDAY_FORM_BIRTHDAY_FORM );

	TInt index = iListBox->CurrentItemIndex();

	// see if the search field is enabled
	CAknFilteredTextListBoxModel* model = 
		static_cast< CAknFilteredTextListBoxModel *> ( iListBox->Model() );
	
	CAknListBoxFilterItems* filter = model->Filter();
	if ( filter != NULL )
		index = filter->FilteredItemIndex ( index );
	
	TBirthday* birthday = iBirthdaysDocument->Birthday( index );

	// tell the form which birthday to modify, using the given data
	form->SetBirthday( birthday, EFalse );

	// activate the form to edit the new birthday
	form->RunLD();	// Note, return is always 0 since we use Back to leave the form.
	
	if ( form->IsBirthdayChanged() )
		{
		// update birthday
		UpdateBirthdayL( index );

		// save changes
		iBirthdaysDocument->SaveL();
		}
	}
/** 
 * Override of the HandleListBoxEventL virtual function
 */
void CBirthdaysList::HandleListBoxEventL( CEikListBox *aListBox, TListBoxEvent anEventType )
	{
	for (int i = 0; i < iListBoxEventDispatch.Count(); i++)
		{
		const TListBoxEventDispatch& currEntry = iListBoxEventDispatch[i];
		if (currEntry.src == aListBox && currEntry.event == anEventType)
			{
			(this->*currEntry.handler)( aListBox, anEventType );
			break;
			}
		}
	}
/** 
 * Helper function to register MEikListBoxObserver event handlers
 */
void CBirthdaysList::AddListBoxEventHandlerL( CEikListBox *aListBox, TListBoxEvent anEvent, ListBoxEventHandler aHandler )
	{
	TListBoxEventDispatch entry;
	entry.src = aListBox;
	entry.event = anEvent;
	entry.handler = aHandler;
	iListBoxEventDispatch.AppendL( entry );
	}
/** 
 * Handle the EEventEnterKeyPressed event.
 */
void CBirthdaysList::HandleListBoxEnterKeyPressedL( CEikListBox* /* aListBox */, TListBoxEvent /* anEventType */ )
	{
	RunEditBirthdayL();
	}
				
