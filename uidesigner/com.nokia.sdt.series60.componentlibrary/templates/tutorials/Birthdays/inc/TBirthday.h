/*
========================================================================
 Name		: TBirthday.h

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

#ifndef TBIRTHDAY_H_
#define TBIRTHDAY_H_

#include <e32std.h>
#include <e32base.h>

class RWriteStream;
class RReadStream;

/**
 * One birthday record.
 */
struct TBirthday
{
public:
	static const int KMaxNameLength = 80;		// limited in UI
	static const int KMaxTodoLength = 500;		// limited in UI
	
	TBirthday() : iName(), iTodo()
		{
		// don't set empty date, since this is out of range of the editor
		iDate = TDateTime( 2000, EJanuary, 1, 0, 0, 0, 0 );
		}
	
	TBirthday( const TDesC& aName, const TTime& aDate, const TDesC& aTodo ) :
		iName( aName ), iDate( aDate ), iTodo( aTodo )
		{
		}
		
	virtual ~TBirthday()
		{
		}
	
	const TDesC& Name() const
		{
		return iName;
		}
		
	const TTime& Date() const
		{
		return iDate;
		}
	
	const TDesC& Todo() const
		{
		return iTodo;
		}
		
	void SetName( const TDesC& aName )
		{
		iName = aName;
		}
		
	void SetDate( const TTime& aDate )
		{
		iDate = aDate;
		}
		
	void SetTodo( const TDesC& aTodo )
		{
		iTodo = aTodo;
		}
		
	static TInt GetDaysUntil( const TBirthday& aBirthday );
	static TInt GetNextAge( const TBirthday& aBirthday );
	
	void ExternalizeL( RWriteStream& aStream ) const;
	void InternalizeL( RReadStream& aStream );
	
private:
	TBuf<KMaxNameLength> iName;
	TTime iDate;
	TBuf<KMaxTodoLength> iTodo;
};

#endif /*TBIRTHDAY_H_*/
