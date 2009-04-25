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

// com.nokia.tcf.api.ITCConnection.java
#define DEFAULT_COMM_ERROR_RETRY_INTERVAL 1i64
#define DEFAULT_COMM_ERROR_RETRY_TIMEOUT 300i64

// com.nokia.tcf.api.ITCMessageOptions.java

#define DESTINATION_INPUTSTREAM 0i64
#define DESTINATION_CLIENTFILE 1i64
#define DEFAULT_DESTINATION 0i64
#define DEFAULT_INPUTSTREAM_OVERFLOW 1i64
#define UNWRAP_LEAVE_HEADERS 0i64
#define UNWRAP_DELETE_HEADERS 1i64
#define DEFAULT_UNWRAP_OPTION 0i64
#define ENCODE_NO_FORMAT 0i64
#define ENCODE_FORMAT 1i64
#define ENCODE_TRK_FORMAT 2i64
#define DEFAULT_ENCODE_FORMAT 0i64
#define DEFAULT_OST_VERSION 1L

#endif //__TCCONSTANTS_H__
