/*
========================================================================
 Name		: ImageContainerView.h

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
#ifndef IMAGECONTAINERVIEW_H
#define IMAGECONTAINERVIEW_H

// [[[ begin generated region: do not modify [Generated Includes]
#include <aknview.h>
// ]]] end generated region [Generated Includes]


// [[[ begin [Event Handler Includes]
// ]]] end [Event Handler Includes]

// [[[ begin generated region: do not modify [Generated Forward Declarations]
class CImageContainer;
// ]]] end generated region [Generated Forward Declarations]

// [[[ begin generated region: do not modify [Generated Constants]
// ]]] end generated region [Generated Constants]

/**
 * Avkon view class for ImageContainerView. It is register with the view server
 * by the AppUi. It owns the container control.
 * @class	CImageContainerView ImageContainerView.h
 */
class CImageContainerView : public CAknView
	{
public:
	// constructors and destructor
	CImageContainerView();
	static CImageContainerView* NewL();
	static CImageContainerView* NewLC();		
	void ConstructL();
	virtual ~CImageContainerView();

public:
	// from base class CAknView
	TUid Id() const;
	void HandleCommandL( TInt aCommand );
	
	void SetImageL(const TDesC16& title, CFbsBitmap *aBitmap);

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
	CImageContainer* CreateContainerL();
	// ]]] end generated region [Generated Methods]
	
	
				
	// ]]] end [Public Section]
				
	// [[[ begin [Protected Section]
protected:
	// [[[ begin [Overridden Methods]
	// ]]] end [Overridden Methods]
	
	
	// [[[ begin [User Handlers]
protected: 
	TBool HandleControlPaneRightSoftKeyPressed( TInt aCommand );
	// ]]] end [User Handlers]
	

	// ]]] end [Protected Section]

	// [[[ begin [Private Section]
private:
	// [[[ begin generated region: do not modify [Generated Instance Variables]
	CImageContainer* iImageContainer;
	// ]]] end generated region [Generated Instance Variables]

	// [[[ begin generated region: do not modify [Generated Methods]
	// ]]] end generated region [Generated Methods]
	
	// ]]] end [Private Section]
	
	RBuf iImageTitle;
	CFbsBitmap* iBitmap;
	
	};

#endif // IMAGECONTAINERVIEW_H			
