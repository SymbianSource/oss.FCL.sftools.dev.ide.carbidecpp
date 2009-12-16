/**
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

package com.nokia.carbide.remoteconnections.internal;

import java.util.Map;

import com.nokia.carbide.remoteconnections.interfaces.IConnectedService;

/**
 * An extended interface to a connected service
 * @since 3.0
 */
public interface IConnectedService2 extends IConnectedService {

	/**
	 * Return the properties for this connected service
	 * @return Map
	 */
	Map<String, String> getProperties();
}
