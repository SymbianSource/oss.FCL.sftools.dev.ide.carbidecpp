/*
========================================================================
 Name		: YahooImageSearchAppUi.h

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
#ifndef YAHOOIMAGESEARCHAPPUI_H
#define YAHOOIMAGESEARCHAPPUI_H

// [[[ begin generated region: do not modify [Generated Includes]
#include <aknviewappui.h>
// ]]] end generated region [Generated Includes]

// [[[ begin generated region: do not modify [Generated Forward Declarations]
class CSearchResultsView;
class CImageContainerView;
// ]]] end generated region [Generated Forward Declarations]

/**
 * @class	CYahooImageSearchAppUi YahooImageSearchAppUi.h
 * @brief The AppUi class handles application-wide aspects of the user interface, including
 *		view management and the default menu, control pane, and status pane.
 */
class CYahooImageSearchAppUi : public CAknViewAppUi
	{
public: 
	// constructor and destructor
	CYahooImageSearchAppUi();
	virtual ~CYahooImageSearchAppUi();
	void ConstructL();

public:
	// from CCoeAppUi
	virtual TKeyResponse HandleKeyEventL(
				const TKeyEvent& aKeyEvent,
				TEventCode aType );

	// from CEikAppUi
	void HandleCommandL( TInt aCommand );
	void HandleResourceChangeL( TInt aType );

	// from CAknAppUi
	void HandleViewDeactivation( const TVwsViewId& aViewIdToBeDeactivated, const TVwsViewId &aNewlyActivatedViewId );

	CImageContainerView* ImageContainerView() {return iImageContainerView;}
private:
	void InitializeContainersL();
	// [[[ begin generated region: do not modify [Generated Methods]
public: 
	// ]]] end generated region [Generated Methods]
	
	// [[[ begin generated region: do not modify [Generated Instance Variables]
private: 
	CSearchResultsView* iSearchResultsView;
	CImageContainerView* iImageContainerView;
	// ]]] end generated region [Generated Instance Variables]
	
	
	// [[[ begin [User Handlers]
protected: 
	// ]]] end [User Handlers]
	
	};

#endif // YAHOOIMAGESEARCHAPPUI_H			
