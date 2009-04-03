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


package com.nokia.carbide.trk.support.service;

import com.nokia.carbide.installpackages.InstallPackages.IServerData;
import com.nokia.carbide.remoteconnections.interfaces.*;

/**
 *
 */
public class TRKInstallerProvider extends AbstractPackageInstallerProvider {

	public class TRKServerData implements IServerData {

		private static final String MASTER_FILE_NAME = "TRKPackages.xml"; //$NON-NLS-1$
		
		public String getMasterFileName() {
			return MASTER_FILE_NAME;
		}

		public IRemoteAgentInstallerProvider getRemoteAgentInstallerProvider() {
			return TRKInstallerProvider.this;
		}

	}

	private IService service;

	public TRKInstallerProvider(IService service) {
		this.service = service;
	}

	public IService getService() {
		return service;
	}

	@Override
	protected IServerData getServerData() {
		return new TRKServerData();
	}
	
}
