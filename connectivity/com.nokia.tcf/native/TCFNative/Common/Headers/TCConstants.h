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
#ifndef __TCCONSTANTS_H__
#define __TCCONSTANTS_H__

#include <stdio.h>

#ifdef _MSC_VER
#define LL(x) x##i64
#else
#define LL(x) x##LL
#endif

// com.nokia.tcf.api.ITCConnection.java
#define DEFAULT_COMM_ERROR_RETRY_INTERVAL LL(1)
#define DEFAULT_COMM_ERROR_RETRY_TIMEOUT LL(300)

// com.nokia.tcf.api.ITCMessageOptions.java

#define DESTINATION_INPUTSTREAM LL(0)
#define DESTINATION_CLIENTFILE LL(1)
#define DEFAULT_DESTINATION LL(0)
#define DEFAULT_INPUTSTREAM_OVERFLOW LL(1)
#define UNWRAP_LEAVE_HEADERS LL(0)
#define UNWRAP_DELETE_HEADERS LL(1)
#define DEFAULT_UNWRAP_OPTION LL(0)
#define ENCODE_NO_FORMAT LL(0)
#define ENCODE_FORMAT LL(1)
#define ENCODE_TRK_FORMAT LL(2)
#define DEFAULT_ENCODE_FORMAT LL(0)
#define DEFAULT_OST_VERSION 1L

#endif //__TCCONSTANTS_H__
