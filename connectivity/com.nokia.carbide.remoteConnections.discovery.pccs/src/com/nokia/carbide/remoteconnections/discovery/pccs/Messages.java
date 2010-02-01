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
package com.nokia.carbide.remoteconnections.discovery.pccs;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "com.nokia.carbide.remoteconnections.discovery.pccs.messages"; //$NON-NLS-1$
	public static String Activator_Agent_Name;
	public static String Activator_PCCS_Location;
	public static String Activator_PCSuite_Location;
	public static String ConnAPILibrary_PCCS_Not_Found_Error;
	public static String ConnAPILibrary_PCSuite_Not_Found_Error;
	public static String PCCSConnection_PCCS_CONAOpenDM_Error;
	public static String PCCSConnection_PCCS_CONARegisterNotifyCallback_Error;
	public static String PCCSConnection_PCCS_CONARegisterNotifyCallback_Pointer_Error;
	public static String PCCSConnection_PCCS_Not_Enough_Memory_Error;
	public static String PCCSConnection_PCCS_Version_Error;
	public static String PCCSConnection_PCSuite_Version_Error;
	public static String PCCSDiscoveryAgent_PCCS_Not_Found_Error;
	public static String PCCSDiscoveryAgent_PCCS_Version_Error;
	public static String PCCSDiscoveryAgent_PCSuite_Not_Found_Error;
	public static String PCCSDiscoveryAgent_PCSuite_Version_Error;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
