/*
========================================================================
 Name		: BirthdaysDocument.h

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
#ifndef BIRTHDAYSDOCUMENT_H
#define BIRTHDAYSDOCUMENT_H

#include <eikdoc.h>
#include <akndoc.h>
#include "TBirthday.h"

class CEikAppUi;
class RReadStream;
class RWriteStream;
class RFs;

/**
 * This document implements the standard CAknDocument interface
 * and manages the list of birthdays.
 */
class CBirthdaysDocument : public CEikDocument
	{
public: 
	// constructor
	static CBirthdaysDocument* NewL( CEikApplication& aApp );

private: 
	// constructors
	CBirthdaysDocument( CEikApplication& aApp );
	virtual ~CBirthdaysDocument();
	void ConstructL();
	
public: 
	// from base class CEikDocument
	CEikAppUi* CreateAppUiL();

	void StoreL( CStreamStore& aStore, CStreamDictionary& aStreamDic ) const;	
	void RestoreL( const CStreamStore& aStore, const CStreamDictionary& aStreamDic );
	
public:
	// accessors for data
	TInt AddBirthdayL();
	void DeleteBirthdayL( TInt anIndex );
	TBirthday* Birthday( TInt anIndex ) const;
	TInt NumBirthdays() const;
	
private:
	// routines used by implicit operator << / operator >> streaming code
	void ExternalizeL( RWriteStream& aStream ) const;
	void InternalizeL( RReadStream& aStream );

	// data
	CArrayFixFlat< TBirthday >* birthdays;
	
	};
#endif // BIRTHDAYSDOCUMENT_H
