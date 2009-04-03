/*
========================================================================
 Name		: BirthdaysDocument.cpp

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
#include <s32file.h>

// [[[ begin generated region: do not modify [Generated User Includes]
#include "BirthdaysDocument.h"
#include "BirthdaysAppUi.h"
// ]]] end generated region [Generated User Includes]

/**
 * @brief Constructs the document class for the application.
 * @param anApplication the application instance
 */
CBirthdaysDocument::CBirthdaysDocument( CEikApplication& anApplication )
	: CEikDocument( anApplication )
	{
	
	}

CBirthdaysDocument::~CBirthdaysDocument()
	{
	if ( birthdays )
		{
		delete birthdays;
		birthdays = NULL;
		}
	}

/**
 * @brief Completes the second phase of Symbian object construction. 
 * Put initialization code that could leave here.  
 */ 
void CBirthdaysDocument::ConstructL()
	{
	birthdays = new (ELeave) CArrayFixFlat< TBirthday >( 8 );
	}
	
/**
 * Symbian OS two-phase constructor.
 *
 * Creates an instance of CBirthdaysDocument, constructs it, and
 * returns it.
 *
 * @param aApp the application instance
 * @return the new CBirthdaysDocument
 */
CBirthdaysDocument* CBirthdaysDocument::NewL( CEikApplication& aApp )
	{
	CBirthdaysDocument* self = new (ELeave) CBirthdaysDocument( aApp );
	CleanupStack::PushL( self );
	self->ConstructL();
	CleanupStack::Pop( self );
	return self;
	}

/**
 * @brief Creates the application UI object for this document.
 * @return the new instance
 */	
CEikAppUi* CBirthdaysDocument::CreateAppUiL()
	{
	return new (ELeave) CBirthdaysAppUi;
	}
				

/**
 * 	Add a new, empty birthday, and return its index.
 */
TInt CBirthdaysDocument::AddBirthdayL()
	{
	birthdays->AppendL( TBirthday() );
	SetChanged( ETrue );
	return birthdays->Count() - 1;
	}

/**
 *	Delete the birthday at index.
 */	
void CBirthdaysDocument::DeleteBirthdayL( TInt index )
	{
	birthdays->Delete( index );
	SetChanged( ETrue );
	}
	
/**
 *	Get a mutable pointer to the birthday at the given index.
 *	@param index number from [ 0 ... NumBirthdays() )
 *	@return pointer, never null
 */
TBirthday* CBirthdaysDocument::Birthday( TInt index ) const
	{
	return &birthdays->At( index );
	}
	
/**
 *	Get the count of birthdays.  
 */
TInt CBirthdaysDocument::NumBirthdays() const
	{
	return birthdays->Count();
	}

// This section handles persistent storage.

/**
 *	Store the birthdays to the stream store (override from CEikDocument)
 */
void CBirthdaysDocument::StoreL( CStreamStore& aStore, CStreamDictionary& aStreamDic ) const
	{
	// save the document data by writing a substream
	RStoreWriteStream stream;

	TStreamId id = stream.CreateLC( aStore );
	
	ExternalizeL( stream ); 
	
	stream.CommitL();

	CleanupStack::PopAndDestroy(); // stream internal state

	// assign a key to the data (use the app's UID)
	aStreamDic.AssignL( Application()->AppDllUid(), id );	
	}
	
/**
 *	Read the birthdays to the stream store (override from CEikDocument)
 */
void CBirthdaysDocument::RestoreL( const CStreamStore& aStore, const CStreamDictionary& aStreamDic )
	{
	//	Get the stream id for the data
	TStreamId id = aStreamDic.At( Application()->AppDllUid() );

	//	restore the model from that stream 
	RStoreReadStream stream;

	stream.OpenLC( aStore, id );

 	InternalizeL( stream ); 

	CleanupStack::PopAndDestroy(); // stream internal state
	
	SetChanged( EFalse );
	}

/**
 *	Write the birthdays to the stream.  This method is implicitly
 *	required by template <T> operator << ( RWriteStream&, const T& ).
 */
void CBirthdaysDocument::ExternalizeL( RWriteStream& aStream ) const
	{
	aStream << (TInt32) birthdays->Count();	
	for ( int i = 0; i < birthdays->Count(); i++ ) 
		{
		aStream << birthdays->At( i );
		}
	}
	
/**
 *	Read the birthdays to the stream.  This method is implicitly
 *	required by template <T> operator >> ( RReadStream&, T& ).
 */
void CBirthdaysDocument::InternalizeL( RReadStream& aStream )
	{
	TInt32 count;
	aStream >> count;
	for ( int i = 0; i < count; i++ ) 
		{
		TBirthday birthday;
		aStream >> birthday;
		birthdays->AppendL( birthday );
		}
	
	}
	
