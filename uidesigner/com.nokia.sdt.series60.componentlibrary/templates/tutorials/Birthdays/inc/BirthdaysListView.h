/*
========================================================================
 Name		: BirthdaysListView.h

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
#ifndef BIRTHDAYSLISTVIEW_H
#define BIRTHDAYSLISTVIEW_H

// [[[ begin generated region: do not modify [Generated Includes]
#include <aknview.h>
// ]]] end generated region [Generated Includes]


// [[[ begin [Event Handler Includes]
// ]]] end [Event Handler Includes]

// [[[ begin generated region: do not modify [Generated Forward Declarations]
class CBirthdaysList;
// ]]] end generated region [Generated Forward Declarations]

// [[[ begin generated region: do not modify [Generated Constants]
// ]]] end generated region [Generated Constants]

/**
 * Avkon view class for BirthdaysListView. It is register with the view server
 * by the AppUi. It owns the container control.
 * @class	CBirthdaysListView BirthdaysListView.h
 */
class CBirthdaysListView : public CAknView
	{
public:
	// constructors and destructor
	CBirthdaysListView();
	static CBirthdaysListView* NewL();
	static CBirthdaysListView* NewLC();		
	void ConstructL();
	virtual ~CBirthdaysListView();

public:
	// from base class CAknView
	TUid Id() const;
	void HandleCommandL( TInt aCommand );

protected:
	// from base class CAknView
	void DoActivateL(
		const TVwsViewId& aPrevViewId,
		TUid aCustomMessageId,
		const TDesC8& aCustomMessage);
	void DoDeactivate();
	void HandleStatusPaneSizeChange();
	
private:
	void SetupStatusPaneL();
	void CleanupStatusPane();
	// [[[ begin [Public Section]
public:
	// [[[ begin generated region: do not modify [Generated Methods]
	CBirthdaysList* CreateContainerL();
	static TInt RunRemoveConfQueryL( const TDesC* aOverrideText = NULL );
	// ]]] end generated region [Generated Methods]
	
	
				
	// ]]] end [Public Section]
				
	// [[[ begin [Protected Section]
protected:
	// [[[ begin [Overridden Methods]
	// ]]] end [Overridden Methods]
	
	
	// [[[ begin [User Handlers]
protected: 
	TBool HandleAddMenuItemSelectedL( TInt aCommand );
	TBool HandleDeleteMenuItemSelectedL( TInt aCommand );
	TBool HandleEditMenuItemSelectedL( TInt aCommand );
	void HandleBirthdaysListViewDeactivated();
	// ]]] end [User Handlers]
	

	// ]]] end [Protected Section]

	// [[[ begin [Private Section]
private:
	// [[[ begin generated region: do not modify [Generated Instance Variables]
	CBirthdaysList* iBirthdaysList;
	// ]]] end generated region [Generated Instance Variables]

	// [[[ begin generated region: do not modify [Generated Methods]
	// ]]] end generated region [Generated Methods]
	
	// ]]] end [Private Section]
	
	enum TListQuery1Images
		{
		// [[[ begin generated region: do not modify [Generated Enums]
		// ]]] end generated region [Generated Enums]
		
		};
	};

#endif // BIRTHDAYSLISTVIEW_H			
