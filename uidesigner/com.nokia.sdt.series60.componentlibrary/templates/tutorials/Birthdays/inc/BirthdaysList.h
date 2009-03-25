/*
========================================================================
 Name		: BirthdaysList.h

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
#ifndef BIRTHDAYSLIST_H
#define BIRTHDAYSLIST_H

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
class CAknDoubleLargeStyleListBox;
class CEikTextListBox;
class CAknSearchField;
// ]]] end generated region [Generated Forward Declarations]

class CBirthdaysDocument;
struct TBirthday;

/**
 * Container class for BirthdaysList
 * 
 * @class	CBirthdaysList BirthdaysList.h
 */
class CBirthdaysList : public CCoeControl
	,MEikListBoxObserver	{
public:
	// constructors and destructor
	CBirthdaysList();
	static CBirthdaysList* NewL( 
		const TRect& aRect, 
		const CCoeControl* aParent, 
		MEikCommandObserver* aCommandObserver );
	static CBirthdaysList* NewLC( 
		const TRect& aRect, 
		const CCoeControl* aParent, 
		MEikCommandObserver* aCommandObserver );		
	void ConstructL( 
		const TRect& aRect, 
		const CCoeControl* aParent, 
		MEikCommandObserver* aCommandObserver );
	virtual ~CBirthdaysList();

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
	static void CreateListBoxItemL( TDes& aBuffer, TInt aIconIndex,
			const TDesC& aMainText,
			const TDesC& aSecondaryText );
	CAknDoubleLargeStyleListBox* ListBox();
	void AddListBoxResourceArrayItemL( TInt aResourceId, TInt aIconIndex );
	void DeleteListBoxSelectedItemsL();
	void SetupListBoxIconsL();
	static CGulIcon* LoadAndScaleIconL( const TDesC& aFileName, TInt aBitmapId, TInt aMaskId, TSize* aSize, TScaleMode aScaleMode );
	TBool HandleMarkableListCommandL( TInt aCommand );
	// ]]] end generated region [Generated Methods]
	
	// [[[ begin generated region: do not modify [Generated Type Declarations]
public: 
	// ]]] end generated region [Generated Type Declarations]
	
	// [[[ begin generated region: do not modify [Generated Instance Variables]
private: 
	CAknDoubleLargeStyleListBox* iListBox;
	CAknSearchField *iListBoxSearchField;
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
		EListBoxSearchField,
		
		// ]]] end generated region [Generated Contents]
		
		// add any user-defined entries here...
		
		ELastControl
		};
	enum TListBoxImages
		{
		// [[[ begin generated region: do not modify [Generated Enums]
		EListBoxAvkonQgn_indi_marked_addIndex = 0,
		EListBox$(baseName$titlelower)_aifPresentIndex = 1,
		EListBox$(baseName$titlelower)_aifPresent_exclIndex = 2,
		EListBoxFirstUserImageIndex
		
		// ]]] end generated region [Generated Enums]
		
		};
		
public:
	void SetBirthdaysL( CBirthdaysDocument* aDocument )
		{
		iBirthdaysDocument = aDocument;
		LoadBirthdaysL();
		}
		
	CBirthdaysDocument* Birthdays()
		{
		return iBirthdaysDocument;
		}
		
	void LoadBirthdaysL();
	
	void AddBirthdayL( TInt anIndex );
	void UpdateBirthdayL( TInt anIndex );
	void DeleteBirthdaysL();
	
	void RunAddBirthdayL();
	void RunEditBirthdayL();
	
private:
	void FormatBirthdayListItemL( TBuf<256>& des, TInt anIndex );
	
	CBirthdaysDocument* iBirthdaysDocument;
	
	// [[[ begin [MEikListBoxObserver support]
private: 
	typedef void (CBirthdaysList::*ListBoxEventHandler)( CEikListBox *aListBox, TListBoxEvent anEvent );
	
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
				
#endif // BIRTHDAYSLIST_H
