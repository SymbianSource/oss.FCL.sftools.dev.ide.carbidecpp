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

public class MCAPIDefinitions {
	//=========================================================
	// Media Control API versions 
	//
	public static final int  MCAPI_VERSION_30		=30;
	public static final int  MCAPI_VERSION_31		=31;
	public static final int  MCAPI_VERSION_32		=32;
	//=========================================================
 	// ----------------------------------------------------
	// Media types used in CONAPI_MEDIA
	//
	public static final int CONAPI_MEDIA_ALL			= 0x01;
	public static final int CONAPI_MEDIA_IRDA			= 0x02;
	public static final int CONAPI_MEDIA_SERIAL			= 0x04;
	public static final int CONAPI_MEDIA_BLUETOOTH		= 0x08;
	public static final int CONAPI_MEDIA_USB			= 0x10;
	// ----------------------------------------------------

 	// ----------------------------------------------------
	// Media sub types used in CONAPI_MEDIA_INFO
	//
		// Infrared media
	public static final int API_MEDIA_TYPE_INFRARED		= 0x00000002;
		// Serial media
	public static final int API_MEDIA_TYPE_SERIAL		= 0x00000004;
		// Bluetooth media
	public static final int	API_MEDIA_TYPE_BLUETOOTH	= 0x00000008;
	public static final int	API_MEDIA_TYPE_BT_MS		= 0x00010008;
	public static final int	API_MEDIA_TYPE_BT_BC		= 0x00020008;
	public static final int	API_MEDIA_TYPE_BT_TO		= 0x00030008;
	public static final int	API_MEDIA_TYPE_BT_IVT		= 0x00040008;
		// USB media
	public static final int	API_MEDIA_TYPE_USB			= 0x00000010;
 	// ----------------------------------------------------

 	// ----------------------------------------------------
	// Media specific parameters
	//
	public static final int  CONAPI_MEDIA_ACTIVE 			=0x00000001;	// Media is active.
	public static final int  CONAPI_MEDIA_NOT_ACTIVE 		=0x00000002;	// Media is not active.
	public static final int  CONAPI_MEDIA_FUNCTIONAL		=0x00000004;	// Media is active and functional.
	public static final int  CONAPI_MEDIA_NOT_FUNCTIONAL	=0x00000008;	// Media is active but not functional.
	public static final int  CONAPI_MEDIA_IC_SUPPORTED 		=0x00000010;	// Media is supporting incoming connections. 
	public static final int  CONAPI_MEDIA_IC_NOT_SUPPORTED	=0x00000020;	// Media is not supporting incoming connections.
 	// ----------------------------------------------------

 	// ----------------------------------------------------
	// Macros. Use these to check values in the CONAPI_MEDIA structure.
	//
//		#define	CONAPI_GET_MEDIA_TYPE(dwMedia)			(0x000000FF & dwMedia)
	public static int CONAPI_GET_MEDIA_TYPE(int dwMedia) {
		return (0x000000FF & dwMedia);
	}
//		#define	CONAPI_IS_MEDIA_ACTIVE(dwState)			(0x00000001 & dwState)
	public static boolean CONAPI_IS_MEDIA_ACTIVE(int dwState) {
		return (0x00000001 & dwState) > 0;
	}
//		#define	CONAPI_IS_MEDIA_UNACTIVE(dwState)		((0x00000002 & dwState)>>1)
	public static boolean CONAPI_IS_MEDIA_UNACTIVE(int dwState) {
		return ((0x00000002 & dwState)>>1) > 0;
	}
//		#define	CONAPI_IS_MEDIA_FUNCTIONAL(dwState)		((0x00000004 & dwState)>>2)
	public static boolean CONAPI_IS_MEDIA_FUNCTIONAL(int dwState) {
		return ((0x00000004 & dwState)>>2) > 0;
	}
//		#define	CONAPI_IS_MEDIA_NOT_FUNCTIONAL(dwState)	((0x00000008 & dwState)>>3)
	public static boolean CONAPI_IS_MEDIA_NOT_FUNCTIONAL(int dwState) {
		return ((0x00000008 & dwState)>>3) > 0;
	}
//		#define	CONAPI_IS_IC_SUPPORTED(dwOptions)		((0x00000010 & dwOptions)>>4)
	public static boolean CONAPI_IS_IC_SUPPORTED(int dwOptions) {
		return ((0x00000010 & dwOptions)>>4) > 0;
	}
//		#define	CONAPI_IS_IC_UNSUPPORTED(dwOptions)		((0x00000020 & dwOptions)>>5)
	public static boolean CONAPI_IS_IC_UNSUPPORTED(int dwOptions) {
		return ((0x00000020 & dwOptions)>>5) > 0;
	}
 	// ----------------------------------------------------
}
