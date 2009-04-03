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
import com.nokia.carbide.trk.support.Activator;

import org.eclipse.jface.operation.IRunnableContext;
import org.osgi.framework.Version;

import java.net.URL;
import java.util.List;

/**
 *
 */
public class TracingInstallerProvider extends AbstractPackageInstallerProvider {

	public class ServerData implements IServerData {

		private static final String SERVER = "http://dacvs002.americas.nokia.com/trk"; //$NON-NLS-1$
		private static final String MASTER_FILE_NAME = "TracingPackages.xml"; //$NON-NLS-1$
		
		public String getMasterFileName() {
			return MASTER_FILE_NAME;
		}

		public URL getRelativePathRoot() {
			URL url = null;
			try {
				url = new URL(SERVER);
			} catch (Exception e) {
				Activator.log(e);
			}
			return url;
		}
		
		public IRemoteAgentInstallerProvider getRemoteAgentInstallerProvider() {
			return TracingInstallerProvider.this;
		}

	}

	private IService service;

	public TracingInstallerProvider(IService service) {
		this.service = service;
	}

	@Override
	public List<IRemoteAgentInstaller> getRemoteAgentInstallers(String familyName, Version version) {
		return super.getRemoteAgentInstallers(familyName, version);
	}

	@Override
	public List<String> getSDKFamilyNames(IRunnableContext runnableContext) {
		return super.getSDKFamilyNames(runnableContext);
	}

	@Override
	public List<Version> getVersions(String familyName) {
		return super.getVersions(familyName);
	}
	
	public IService getService() {
		return service;
	}

	@Override
	protected IServerData getServerData() {
		return new ServerData();
	}
	
}
