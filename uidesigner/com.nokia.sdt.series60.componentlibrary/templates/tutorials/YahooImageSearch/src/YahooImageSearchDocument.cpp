/*
========================================================================
 Name		: YahooImageSearchDocument.cpp

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
// [[[ begin generated region: do not modify [Generated User Includes]
#include "YahooImageSearchDocument.h"
#include "YahooImageSearchAppUi.h"
// ]]] end generated region [Generated User Includes]

/**
 * @brief Constructs the document class for the application.
 * @param anApplication the application instance
 */
CYahooImageSearchDocument::CYahooImageSearchDocument( CEikApplication& anApplication )
	: CAknDocument( anApplication )
	{
	}

/**
 * @brief Completes the second phase of Symbian object construction. 
 * Put initialization code that could leave here.  
 */ 
void CYahooImageSearchDocument::ConstructL()
	{
	}
	
/**
 * Symbian OS two-phase constructor.
 *
 * Creates an instance of CYahooImageSearchDocument, constructs it, and
 * returns it.
 *
 * @param aApp the application instance
 * @return the new CYahooImageSearchDocument
 */
CYahooImageSearchDocument* CYahooImageSearchDocument::NewL( CEikApplication& aApp )
	{
	CYahooImageSearchDocument* self = new (ELeave) CYahooImageSearchDocument( aApp );
	CleanupStack::PushL( self );
	self->ConstructL();
	CleanupStack::Pop( self );
	return self;
	}

/**
 * @brief Creates the application UI object for this document.
 * @return the new instance
 */	
CEikAppUi* CYahooImageSearchDocument::CreateAppUiL()
	{
	return new (ELeave) CYahooImageSearchAppUi;
	}
				
