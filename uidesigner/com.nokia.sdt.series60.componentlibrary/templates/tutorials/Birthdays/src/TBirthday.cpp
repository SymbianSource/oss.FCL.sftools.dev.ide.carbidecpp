/*
========================================================================
 Name		: TBirthday.cpp

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

#include "TBirthday.h"

static TDateTime NextBirthday( const TDateTime& aToday, const TBirthday& aBirthday )
	{
	// get date 
	TDateTime birthdayDate( aBirthday.Date().DateTime() );
	
	// set year for next birthday
	if ( aToday.Month() < birthdayDate.Month() || aToday.Day() < birthdayDate.Day() )
		birthdayDate.SetYear( aToday.Year() );
	else
		birthdayDate.SetYear( aToday.Year() + 1 );

	return birthdayDate;
	}
	
TInt TBirthday::GetDaysUntil( const TBirthday& aBirthday )
	{
	// get current date
	TTime nowTime;
	nowTime.HomeTime();
	TDateTime now( nowTime.DateTime() );

	// get next birthday
	TTime nextBirthday = NextBirthday( now, aBirthday );
	
	// get difference in days
	return nextBirthday.DaysFrom( now ).Int();
	}
	
TInt TBirthday::GetNextAge( const TBirthday& aBirthday )
	{
	// get current date
	TTime nowTime;
	nowTime.HomeTime();
	TDateTime now( nowTime.DateTime() );

	// get next birthday
	TDateTime nextBirthday = NextBirthday( now, aBirthday );

	// get birth date
	TDateTime birthDate( aBirthday.Date().DateTime() );

	return nextBirthday.Year() - birthDate.Year();
	}
	
void TBirthday::ExternalizeL( RWriteStream& aStream ) const
	{
	aStream << iName;	
	aStream << iDate.Int64();
	aStream << iTodo;	
	}

void TBirthday::InternalizeL( RReadStream& aStream )
	{
	aStream >> iName;	
	TInt64 int64;
	aStream >> int64;
	iDate = TTime( int64 );
	aStream >> iTodo;	
	}

