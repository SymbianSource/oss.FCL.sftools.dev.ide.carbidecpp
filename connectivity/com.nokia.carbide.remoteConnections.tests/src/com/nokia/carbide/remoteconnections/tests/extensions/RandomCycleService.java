/*
* Copyright (c) 2008 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.carbide.remoteconnections.tests.extensions;

import com.nokia.carbide.remoteconnections.interfaces.*;

/**
 *
 */
public class RandomCycleService implements IService {

	private IRemoteAgentInstallerProvider installerProvider;

	/**
	 * 
	 */
	public RandomCycleService() {
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.remoteconnections.extensions.IService#getDisplayName()
	 */
	public String getDisplayName() {
		return "Random Cycle Service";
	}

	public String getAdditionalServiceInfo() {
		return "Testing the status will cycle through various states";
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.remoteconnections.extensions.IService#getIdentifier()
	 */
	public String getIdentifier() {
		return getClass().getName();
	}

	public IRemoteAgentInstallerProvider getInstallerProvider() {
		if (installerProvider == null)
			installerProvider = new TestInstallerProvider(this);
		return installerProvider;
	}

	public boolean isTestable() {
		return true;
	}

}
