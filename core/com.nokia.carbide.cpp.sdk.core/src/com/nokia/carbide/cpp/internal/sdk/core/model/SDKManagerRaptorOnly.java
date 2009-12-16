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

package com.nokia.carbide.cpp.internal.sdk.core.model;

import java.io.File;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import com.nokia.carbide.cpp.internal.api.sdk.SBSv2Utils;
import com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DefaultType;
import com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType;
import com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DevicesFactory;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKEnvInfoFailureException;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

/**
 * This SDK manager only expects Raptor (SBSv2) to exist.
 */
public class SDKManagerRaptorOnly extends AbstractSDKManager {

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.sdk.core.model.AbstractSDKManager#doScanSDKs()
	 */
	@Override
	protected boolean doScanSDKs() {
		
		String message = SBSv2Utils.scanSBSv2();
		if (message != null) {
			reportError(message);
			
			// no good will come from checking over and over...
			return true;
		}
		
		// TODO LINUX: real configuration
		String epocrootStr = System.getenv("EPOCROOT");
		if (epocrootStr == null)
			epocrootStr = System.getProperty("user.home") + "/epocroot";
		
		File epocroot = new File(epocrootStr);
		
		if (epocroot.isDirectory()) {
			addSymbianSDK(epocroot);
		}
		
		return true;
	}

	protected void addSymbianSDK(File epocroot) {
		// TODO LINUX: see if Raptor has a database for this stuff
		DeviceType deviceType = DevicesFactory.eINSTANCE.createDeviceType();
		deviceType.setDefault(DefaultType.YES_LITERAL);
		deviceType.setEpocroot(epocroot.getAbsolutePath());
		deviceType.setId("raptor");
		deviceType.setName("com.nokia.s60");
		deviceType.setAlias("Default Raptor target");
		deviceType.setToolsroot(new File(epocroot, "epoc32/tools").getAbsolutePath());
		deviceType.setUserdeletable("false");
		
		SymbianSDK sdk = new SymbianSDK(deviceType);
		sdkList.add(sdk);
	}

	protected void reportError(final String string) {
		if (WorkbenchUtils.isJUnitRunning())
			return;
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				MessageDialog.openWarning(WorkbenchUtils.getActiveShell(), "SBSv2 Setup Failed", string);
			}
		});
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.sdk.core.model.AbstractSDKManager#doRemoveSDK(java.lang.String)
	 */
	@Override
	protected boolean doRemoveSDK(String sdkId) {
		reportError("Cannot delete Raptor targets");
		return false;
	}


	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.sdk.core.ISDKManager#checkDevicesXMLSynchronized()
	 */
	public boolean checkDevicesXMLSynchronized() {
		// TODO LINUX: investigate
		return true;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.sdk.core.ISDKManager#getCSLArmToolchainInstallPathAndCheckReqTools()
	 */
	public String getCSLArmToolchainInstallPathAndCheckReqTools()
			throws SDKEnvInfoFailureException {
		throw new SDKEnvInfoFailureException("CSL ARM Tools detection not yet implemented");
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.sdk.core.ISDKManager#getDevicesXMLFile()
	 */
	public File getDevicesXMLFile() {
		// This is a placeholder, since this file is not used in Linux
		return new File(System.getProperty("user.home"), "devices.xml");
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.sdk.core.ISDKManager#setDefaultSDK(com.nokia.carbide.cpp.sdk.core.ISymbianSDK)
	 */
	public void setDefaultSDK(ISymbianSDK sdk) {
		// ignore
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.sdk.core.ISDKManager#updateSDK(com.nokia.carbide.cpp.sdk.core.ISymbianSDK)
	 */
	public void updateSDK(ISymbianSDK sdkId) {
		updateCarbideSDKCache();
	}
	
	@Override
	protected boolean isEPOCRootFixed() {
		return false;
	}

}
