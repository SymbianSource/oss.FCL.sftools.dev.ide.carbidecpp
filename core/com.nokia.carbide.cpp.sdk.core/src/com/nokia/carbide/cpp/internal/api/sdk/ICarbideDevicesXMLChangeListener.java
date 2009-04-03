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
*/
package com.nokia.carbide.cpp.internal.api.sdk;

/**
 * Interface to check for modifications to devices.xml outside of Carbide.
 * @since 2.0
 *
 */
public interface ICarbideDevicesXMLChangeListener {
	
	/**
	 * Event received when devices.xml on disk is different from the SDK list in Carbide.
	 */
	public void devicesXMLOutOfSync();
}
