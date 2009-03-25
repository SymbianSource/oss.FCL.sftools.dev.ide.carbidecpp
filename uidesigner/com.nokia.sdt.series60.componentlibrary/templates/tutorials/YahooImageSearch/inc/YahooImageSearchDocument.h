/*
========================================================================
 Name		: YahooImageSearchDocument.h

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
#ifndef YAHOOIMAGESEARCHDOCUMENT_H
#define YAHOOIMAGESEARCHDOCUMENT_H

#include <akndoc.h>
		
class CEikAppUi;

/**
* @class	CYahooImageSearchDocument YahooImageSearchDocument.h
* @brief	A CAknDocument-derived class is required by the S60 application 
*		   framework. It is responsible for creating the AppUi object. 
*/
class CYahooImageSearchDocument : public CAknDocument
	{
public: 
	// constructor
	static CYahooImageSearchDocument* NewL( CEikApplication& aApp );

private: 
	// constructors
	CYahooImageSearchDocument( CEikApplication& aApp );
	void ConstructL();
	
public: 
	// from base class CEikDocument
	CEikAppUi* CreateAppUiL();
	};
#endif // YAHOOIMAGESEARCHDOCUMENT_H
