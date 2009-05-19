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

package com.nokia.carbide.remoteconnections.interfaces;

import org.eclipse.core.runtime.IAdaptable;

import java.util.Map;

/**
 * An extended interface for remote connections services 
 */
public interface IService2 extends IService, IAdaptable {

	/**
	 * Return whether this service uses the Device OS setting in the connection edit page
	 * @return boolean
	 */
	boolean wantsDeviceOS();
	
	/**
	 * Return a map of service specific default settings
	 * @return Map 
	 */
	Map<String, String> getDefaults();

}
