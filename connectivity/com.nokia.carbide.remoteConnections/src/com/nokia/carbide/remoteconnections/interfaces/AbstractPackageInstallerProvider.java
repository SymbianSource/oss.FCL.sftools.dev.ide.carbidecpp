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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Version;

import com.nokia.carbide.installpackages.InstallPackages;
import com.nokia.carbide.installpackages.InstallPackages.IServerData;
import com.nokia.carbide.installpackages.gen.InstallPackages.PackageType;
import com.nokia.carbide.remoteconnections.Messages;
import com.nokia.carbide.remoteconnections.RemoteConnectionsActivator;

public abstract class AbstractPackageInstallerProvider implements IRemoteAgentInstallerProvider {

	public class PackageTypeInstaller implements IRemoteAgentInstaller {
		
		private class PackageContents implements IPackageContents {
			public PackageContents(String defaultFileName, InputStream inputStream) {
				this.defaultFileName = ""; //$NON-NLS-1$
				if (defaultFileName != null)
					this.defaultFileName = defaultFileName;
				this.inputStream = inputStream;
			}

			private String defaultFileName;
			private InputStream inputStream;

			public String getDefaultNameFileName() {
				return defaultFileName;
			}

			public InputStream getInputStream() {
				return inputStream;
			}
		}

		private InstallPackages packages;
		private PackageType packageType;
		
		public PackageTypeInstaller(InstallPackages packages, PackageType packageType) {
			this.packages = packages;
			this.packageType = packageType;
		}

		private boolean isArchive(PackageType pkg) {
			IPath path = new Path(pkg.getInstallFilePath());
			String extension = path.getFileExtension();
			return ZIPEXT.equalsIgnoreCase(extension);
		}
		
		public boolean fileSupportsInstall() {
			return !isArchive(packageType);
		}

		public Image getImage() {
			return getCachedImage(isArchive(packageType) ? ZIPFILEIMGDESC : INSTALLFILEIMGDESC);
		}

		public String getInformation() {
			return packageType.getInformation();
		}

		public String getLabel() {
			return packageType.getDisplayName();
		}

		public Version getInstallerVersion() {
			try {
				return new Version(packageType.getPackageVersion());
			}
			catch (IllegalArgumentException e) {
				RemoteConnectionsActivator.logError(e);
			}
			return null;
		}

		public IPackageContents getPackageContents(IRunnableContext runnableContext) {
			InputStream inputStream = null;
			String installFileUrl = null;
			try {
				installFileUrl = getInstallFileUrl(runnableContext);
				inputStream = getInstallFile(installFileUrl, runnableContext);
			} catch (Exception e) {
				RemoteConnectionsActivator.logError(e);
			}
			String defaultFileName = null;
			if (installFileUrl != null)
				defaultFileName = new Path(installFileUrl).lastSegment();
			return new PackageContents(defaultFileName, inputStream);
		}
		
		private ByteArrayInputStream getInstallFile(String installFileUrl, IRunnableContext runnableContext) throws Exception {
			GetMethod getMethod = new GetMethod(installFileUrl);
			HttpClient client = new HttpClient();
			InstallPackages.setProxyData(client, getMethod);
	        client.getHttpConnectionManager().getParams().setConnectionTimeout(8000);
	        int serverStatus = 0;
	        byte[] responseBody;
			try {
				serverStatus = client.executeMethod(getMethod);
				responseBody = getMethod.getResponseBody();
			} catch (Exception e) {
				// could be HttpException or IOException
				throw new Exception(e);
			} finally {
				getMethod.releaseConnection();
			}
	        
	        // HTTP status codes: 2xx = Success
	    	if (serverStatus >= 200 && serverStatus < 300) {
	            return new ByteArrayInputStream(responseBody);
	    	}
	    	
	       return null;
		}

		private String getInstallFileUrl(IRunnableContext runnableContext) {
			final String[] installFileUrl = { null };
			try {
				runnableContext.run(false, false, new IRunnableWithProgress() {
					public void run(IProgressMonitor monitor) throws InvocationTargetException,	InterruptedException {
						monitor.beginTask(Messages.getString("AbstractPackageInstallerProvider.DownloadingAgentJobLabel"), IProgressMonitor.UNKNOWN); //$NON-NLS-1$
						installFileUrl[0] = packages.getInstallUrlString(packageType.getInstallFilePath());
					}
				});
			} catch (Exception e) {
				RemoteConnectionsActivator.logError(e);
			}
			return installFileUrl[0];
		}
		
	}
	
	protected static final String ZIPEXT = "ZIP"; //$NON-NLS-1$
	protected static final ImageDescriptor ZIPFILEIMGDESC = RemoteConnectionsActivator.getImageDescriptor("icons/archiveFile.png"); //$NON-NLS-1$
	protected static final ImageDescriptor INSTALLFILEIMGDESC = RemoteConnectionsActivator.getImageDescriptor("icons/installFile.png"); //$NON-NLS-1$
	private InstallPackages packages;
	private List<PackageType> packageList;
	private Map<ImageDescriptor, Image> imageCache;

	/**
	 * @deprecated
	 */
	protected abstract IServerData getServerData();

	public List<String> getSDKFamilyNames(IRunnableContext runnableContext) {
		if (packages == null)
			packages = new InstallPackages(getService(), runnableContext);
		Set<String> sdkFamilyNames = new HashSet<String>();
		packageList = packages.getAvailablePackageList();
		if (packageList == null)
			return Collections.emptyList();
		for (PackageType packageType : packageList) {
			String sdkFamily = packageType.getSdkFamily();
			sdkFamilyNames.add(sdkFamily);
		}
		List<String> sdkFamilyNameList = new ArrayList<String>(sdkFamilyNames);
		Collections.sort(sdkFamilyNameList);
		return sdkFamilyNameList;
	}

	public List<String> getVersions(String familyName) {
		Set<String> versions = new HashSet<String>();
		for (PackageType packageType : packageList) {
			if (packageType.getSdkFamily().equals(familyName)) {
				try {
					versions.add(packageType.getSdkVersion());
				}
				catch (IllegalArgumentException e) {
					RemoteConnectionsActivator.logError(e);
				}
			}
		}
		List<String> versionList = new ArrayList<String>(versions);
		Collections.sort(versionList);
		Collections.reverse(versionList);
		return versionList;
	}

	public List<IRemoteAgentInstaller> getRemoteAgentInstallers(String familyName, String version) {
		Set<IRemoteAgentInstaller> installers = new HashSet<IRemoteAgentInstaller>();
		if (packageList == null) {
			getSDKFamilyNames(null);
		}
		for (PackageType packageType : packageList) {
			if (packageType.getSdkFamily().equals(familyName)) {
				try {
					if (version.equals(packageType.getSdkVersion())) {
						installers.add(new PackageTypeInstaller(packages, packageType));
					}
				}
				catch (IllegalArgumentException e) {
					RemoteConnectionsActivator.logError(e);
				}
			}
		}
		List<IRemoteAgentInstaller> installerList = new ArrayList<IRemoteAgentInstaller>(installers);
		Collections.sort(installerList, new Comparator<IRemoteAgentInstaller>() {
			public int compare(IRemoteAgentInstaller o1, IRemoteAgentInstaller o2) {
				return o1.getLabel().compareToIgnoreCase(o2.getLabel());
			}
		});
		return installerList;
	}

	protected synchronized Image getCachedImage(ImageDescriptor desc) {
		if (imageCache == null)
			imageCache = new HashMap<ImageDescriptor, Image>();
		
		Image image = imageCache.get(desc);
		if (image == null) {
			image = desc.createImage();
			imageCache.put(desc, image);
		}
		
		return image;
	}

	public synchronized void dispose() {
		if (imageCache != null) {
			for (Image image : imageCache.values()) {
				image.dispose();
			}
			imageCache.clear();
			imageCache = null;
		}
		packages = null;
	}

}