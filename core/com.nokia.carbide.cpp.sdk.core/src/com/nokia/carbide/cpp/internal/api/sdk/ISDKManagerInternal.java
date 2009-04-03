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
 * Internal methods only, instead of putting them in the public ISDKManager interface.
 * @since 2.0
 */
public interface ISDKManagerInternal {

	/**
	 * Add an ICarbideDevicesXMLChangeListener listener
	 * @param listener - An instance of ICarbideDevicesXMLChangeListener
	 */
	public void addDevicesXMLChangeListener(ICarbideDevicesXMLChangeListener listener);
	
	/**
	 * Remove an ICarbideDevicesXMLChangeListener listener
	 * @param listener - An instance of ICarbideDevicesXMLChangeListener
	 */
	public void removeDevicesXMLChangeListener(ICarbideDevicesXMLChangeListener listener);
	
	/**
	 * Notify listeners that devices.xml content has changed outside of Carbide.
	 */
	public void fireDevicesXMLChanged();

}
