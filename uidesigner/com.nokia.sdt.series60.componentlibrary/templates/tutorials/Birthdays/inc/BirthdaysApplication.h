/*
========================================================================
 Name		: BirthdaysApplication.h

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
#ifndef BIRTHDAYSAPPLICATION_H
#define BIRTHDAYSAPPLICATION_H

// [[[ begin generated region: do not modify [Generated Includes]
#include <aknapp.h>
// ]]] end generated region [Generated Includes]

// [[[ begin generated region: do not modify [Generated Constants]
const TUid KUidBirthdaysApplication = { $(uid3) };
// ]]] end generated region [Generated Constants]

/**
 *
 * @class	CBirthdaysApplication BirthdaysApplication.h
 * @brief	A CAknApplication-derived class is required by the S60 application 
 *		  framework. It is subclassed to create the application's document 
 *		  object.
 */
class CBirthdaysApplication : public CAknApplication
	{
private:
	TUid AppDllUid() const;
	CApaDocument* CreateDocumentL();
	
	};
			
#endif // BIRTHDAYSAPPLICATION_H		
