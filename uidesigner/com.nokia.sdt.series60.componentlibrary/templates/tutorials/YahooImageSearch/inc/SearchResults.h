/*
========================================================================
 Name		: SearchResults.h

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
#ifndef SEARCHRESULTS_H
#define SEARCHRESULTS_H

// [[[ begin generated region: do not modify [Generated Includes]
#include <coecntrl.h>		
#include <akniconutils.h>
#include <gulicon.h>
// ]]] end generated region [Generated Includes]


// [[[ begin [Event Handler Includes]
#include <eiklbo.h>
// ]]] end [Event Handler Includes]

// [[[ begin generated region: do not modify [Generated Forward Declarations]
class MEikCommandObserver;		
class CAknDoubleStyleListBox;
class CEikTextListBox;
// ]]] end generated region [Generated Forward Declarations]

/**
 * Container class for SearchResults
 * 
 * @class	CSearchResults SearchResults.h
 */
class CSearchResults : public CCoeControl
	,MEikListBoxObserver	{
public:
	// constructors and destructor
	CSearchResults();
	static CSearchResults* NewL( 
		const TRect& aRect, 
		const CCoeControl* aParent, 
		MEikCommandObserver* aCommandObserver );
	static CSearchResults* NewLC( 
		const TRect& aRect, 
		const CCoeControl* aParent, 
		MEikCommandObserver* aCommandObserver );		
	void ConstructL( 
		const TRect& aRect, 
		const CCoeControl* aParent, 
		MEikCommandObserver* aCommandObserver );
	virtual ~CSearchResults();

public:
	// from base class CCoeControl
	TInt CountComponentControls() const;
	CCoeControl* ComponentControl( TInt aIndex ) const;
	TKeyResponse OfferKeyEventL( const TKeyEvent& aKeyEvent,TEventCode aType );
	void HandleResourceChange( TInt aType );
	
protected:
	// from base class CCoeControl
	void SizeChanged();

private:
	// from base class CCoeControl
	void Draw( const TRect& aRect ) const;

private:
	void InitializeControlsL();
	void LayoutControls();
	CCoeControl* iFocusControl;
	MEikCommandObserver* iCommandObserver;
	// [[[ begin generated region: do not modify [Generated Methods]
public: 
	static void AddListBoxItemL( CEikTextListBox* aListBox, const TDesC& aString );
	static RArray< TInt >* GetSelectedListBoxItemsLC( CEikTextListBox* aListBox );
	static void DeleteSelectedListBoxItemsL( CEikTextListBox* aListBox );
	static void CreateListBoxItemL( TDes& aBuffer, const TDesC& aMainText,
			const TDesC& aSecondaryText );
	CAknDoubleStyleListBox* ListBox();
	void AddListBoxResourceArrayItemL( TInt aResourceId );
	void DeleteListBoxSelectedItemsL();
	void SetupListBoxIconsL();
	static CGulIcon* LoadAndScaleIconL( const TDesC& aFileName, TInt aBitmapId, TInt aMaskId, TSize* aSize, TScaleMode aScaleMode );
	TBool HandleMarkableListCommandL( TInt aCommand );
	// ]]] end generated region [Generated Methods]
	
	void ResetListL();
	
	// [[[ begin generated region: do not modify [Generated Type Declarations]
public: 
	// ]]] end generated region [Generated Type Declarations]
	
	// [[[ begin generated region: do not modify [Generated Instance Variables]
private: 
	CAknDoubleStyleListBox* iListBox;
	// ]]] end generated region [Generated Instance Variables]
	
	
	// [[[ begin [Overridden Methods]
protected: 
	virtual void HandleListBoxEventL( CEikListBox *aListBox, TListBoxEvent anEventType );	
	// ]]] end [Overridden Methods]
	
	
	// [[[ begin [User Handlers]
protected: 
	void HandleListBoxEnterKeyPressedL( CEikListBox* aListBox, TListBoxEvent anEventType );
	// ]]] end [User Handlers]
	
public: 
	enum TControls
		{
		// [[[ begin generated region: do not modify [Generated Contents]
		EListBox,
		
		// ]]] end generated region [Generated Contents]
		
		// add any user-defined entries here...
		
		ELastControl
		};
	enum TListBoxImages
		{
		// [[[ begin generated region: do not modify [Generated Enums]
		EListBoxYahooimagesearch_mbmList_iconIndex = 0,
		EListBoxFirstUserImageIndex
		
		// ]]] end generated region [Generated Enums]
		
		};
	
	// [[[ begin [MEikListBoxObserver support]
private: 
	typedef void (CSearchResults::*ListBoxEventHandler)( CEikListBox *aListBox, TListBoxEvent anEvent );
	
	void AddListBoxEventHandlerL( CEikListBox *aListBox, TListBoxEvent anEvent, ListBoxEventHandler aHandler);
	
	struct TListBoxEventDispatch 
		{ 
		CEikListBox *src; 
		TListBoxEvent event; 
		ListBoxEventHandler handler;
		};
		
	RArray<TListBoxEventDispatch> iListBoxEventDispatch;
	// ]]] end [MEikListBoxObserver support]
	
	};
				
#endif // SEARCHRESULTS_H
