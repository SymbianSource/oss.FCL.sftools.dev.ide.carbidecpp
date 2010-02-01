/*
* Copyright (c) 2010 Nokia Corporation and/or its subsidiary(-ies).
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
package com.nokia.carbide.remoteconnections.discovery.pccs.pccsnative;

public class UPAPIDefinitions {
	// ----------------------------------------------------
	// Data direction used in UPAPI_SendCommandToDevice()
	// function.
	//
	   public static final int UPAPI_DATA_DIRECTION_HOST_TO_DEVICE	=0x01;
	   public static final int UPAPI_DATA_DIRECTION_DEVICE_TO_HOST	=0x02;
	// ----------------------------------------------------
	// Personality codes
	//
	   public static final int UPAPI_PERSONALITY_CODE_PC_SUITE			=0x01;
	   public static final int UPAPI_PERSONALITY_CODE_REMOVABLE_DISC	=0x02;
	   public static final int UPAPI_PERSONALITY_CODE_PICTURE_TRANSFER	=0x03;
	   public static final int UPAPI_PERSONALITY_CODE_MEDIA_TRANSFER	=0x04;
	   public static final int UPAPI_PERSONALITY_CODE_COMBINED			=0x05;
	   public static final int UPAPI_PERSONALITY_CODE_PC_TO_WEB			=0x06;

	   // not from documentation - from email only
	   public static final int UPAPI_PERSONALITY_CODE_RNDIS				=0x08;

	//=========================================================
	// USB Personality API version
	//
	   public static final int UPAPI_VERSION_10		=10;
	//=========================================================

}
