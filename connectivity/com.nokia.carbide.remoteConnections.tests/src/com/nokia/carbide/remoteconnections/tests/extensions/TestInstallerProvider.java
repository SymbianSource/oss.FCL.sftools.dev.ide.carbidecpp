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

import com.nokia.carbide.remoteconnections.interfaces.IRemoteAgentInstallerProvider;
import com.nokia.carbide.remoteconnections.interfaces.IService;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Version;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 *
 */
public class TestInstallerProvider implements IRemoteAgentInstallerProvider {
	
	private static final Version S60_32 = new Version("3.2");
	private static final Version S60_50 = new Version("5.0");
	private static final Version UIQ_30 = new Version("3.0");
	private static final String S60 = "S60";
	private static final String UIQ = "UIQ";
	private static final String INSTALLER_CONTENTS = "This is a mock installer package.";
	private final static Image fileIcon = 
		PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(
				ISharedImages.IMG_OBJ_FILE).createImage();
	
	private boolean initialized;
	private IService service;
	
	private static class TestInstaller implements IRemoteAgentInstaller {
		
		private final String family;
		private final Version version;

		public TestInstaller(String family, Version version) {
			this.family = family;
			this.version = version;
		}
		
		public String getFamily() {
			return family;
		}

		public Version getVersion() {
			return version;
		}
		
		public Image getImage() {
			return fileIcon;
		}
		
		public String getLabel() {
			return "Test installer package";
		}

		public Version getInstallerVersion() {
			return null;
		}
		
		public String getInformation() {
			return "This is a " + getLabel() + " for " + family + " " + version;
		}
		
		public boolean fileSupportsInstall() {
			return true;
		}

		public IPackageContents getPackageContents(IRunnableContext runnableContext) {
			try {
				runnableContext.run(false, false, new IRunnableWithProgress() {
					public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
						getMockData(monitor);
					}
				});
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			return new IPackageContents() {

				public String getDefaultNameFileName() {
					return "TestInstaller.txt";
				}

				public InputStream getInputStream() {
					return new ByteArrayInputStream(INSTALLER_CONTENTS.getBytes());
				}
				
			};
		}
	}
	
	private IRemoteAgentInstaller[] installers = {
		new TestInstaller(S60, S60_32),
		new TestInstaller(S60, S60_50),
		new TestInstaller(UIQ, UIQ_30)
	};
	
	public TestInstallerProvider(IService service) {
		this.service = service;
	}

	public List<String> getSDKFamilyNames(IRunnableContext runnableContext) {
		if (!initialized) {
			try {
				runnableContext.run(false, false, new IRunnableWithProgress() {
					public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
						getMockData(monitor);
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
			initialized = true;
		}
		String[] familyNames = { S60, UIQ };
		return new ArrayList<String>(Arrays.asList(familyNames));
	}
	
	public List<Version> getVersions(String familyName) {
		if (familyName.equals(S60)) {
			Version[] versions = { S60_32, S60_50 };
			return new ArrayList<Version>(Arrays.asList(versions));
		}
		else if (familyName.equals(UIQ))
			return Collections.singletonList(UIQ_30);
		
		return Collections.EMPTY_LIST;
	}
	
	public List<IRemoteAgentInstaller> getRemoteAgentInstallers(String familyName, Version version) {
		if (familyName.equals(S60)) {
			if (version.equals(S60_32))
				return Collections.singletonList(installers[0]);
			else if (version.equals(S60_50))
				return Collections.singletonList(installers[1]);
		}
		else if (familyName.equals(UIQ) && version.equals(UIQ_30))
			return Collections.singletonList(installers[2]);
		
		return Collections.EMPTY_LIST;
	}

	private static void getMockData(IProgressMonitor monitor) throws InterruptedException {
		monitor.beginTask("Getting data", 3);
		Thread.sleep(100);
		monitor.worked(1);
		Thread.sleep(100);
		monitor.worked(1);
		Thread.sleep(100);
		monitor.worked(1);
		monitor.done();
	}

	public void dispose() {
	}

	public IService getService() {
		return service;
	}

}
