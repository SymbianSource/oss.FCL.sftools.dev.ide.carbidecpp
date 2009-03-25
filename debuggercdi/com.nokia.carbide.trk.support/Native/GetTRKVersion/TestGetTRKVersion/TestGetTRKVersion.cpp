/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies).
* All rights reserved.
* This component and the accompanying materials are made available
* under the terms of the License "Eclipse Public License v1.0"
* which accompanies this distribution, and is available
* at the URL "http://www.eclipse.org/legal/epl-v10.html".
*
* Initial Contributors:
* Nokia Corporation - initial contribution.
*
* Contributors:
*
* Description: 
*
*/
// TestGetTRKVersion.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include "GetTRKVersion.h"

int main(int argc, char* argv[])
{
	printf("Calling GetTRKVersion()\n");

	long version[3];
	DWORD error = GetTRKVersion("COM8", -1, 0, 0, 0, 0, version);
	
	if (error != 0)
		printf("Error = %d\n", error);
	else
		printf("Version ints = %d, %d, %d\n", version[0], version[1], version[2]);
	
	return 0;
}

