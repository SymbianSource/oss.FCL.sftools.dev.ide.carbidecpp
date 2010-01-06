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


package com.nokia.carbide.installpackages;


import com.nokia.carbide.installpackages.gen.InstallPackages.*;
import com.nokia.carbide.installpackages.gen.InstallPackages.util.InstallPackagesResourceFactoryImpl;
import com.nokia.carbide.remoteconnections.Messages;
import com.nokia.carbide.remoteconnections.RemoteConnectionsActivator;
import com.nokia.carbide.remoteconnections.interfaces.IRemoteAgentInstallerProvider;
import com.nokia.cpp.internal.api.utils.core.*;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.eclipse.core.net.proxy.IProxyData;
import org.eclipse.core.runtime.*;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.osgi.service.datalocation.Location;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 *
 */
public class InstallPackages {
	
	public interface IServerData {
		
		/**
		 * The file name of the master xml file
		 * @return String
		 */
		String getMasterFileName();
		
		/**
		 * Return the IRemoteAgentInstallerProvider for this server data
		 * @return IRemoteAgentInstallerProvider
		 */
		IRemoteAgentInstallerProvider getRemoteAgentInstallerProvider();
	}

	private final IServerData serverData;
	private List<PackageType> packageList;
	private String serverPath;
	
	public InstallPackages(IServerData serverData, IRunnableContext runnableContext) {
		Check.checkArg(serverData);
		this.serverData = serverData;
		if (runnableContext == null)
			getPackageList();
		else {
			try {
				runnableContext.run(true, false, new IRunnableWithProgress() {
					public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
						monitor.beginTask(Messages.getString("InstallPackages.GettingPackagesJobLabel"), IProgressMonitor.UNKNOWN); //$NON-NLS-1$
						getPackageList();
					}
				});
			} catch (Exception e) {
				RemoteConnectionsActivator.logError(e);
			}
		}
	}
	
	private void getPackageList() {
		try {
			PackagesType packages = getAvailablePackagesFromServer();
			if (packages != null) {
				EList<PackageType> elist = packages.getPackage();
				packageList = new ArrayList<PackageType>(elist);
				Collections.sort(packageList, new Comparator<PackageType>() {
					public int compare(PackageType o1, PackageType o2) {
						return o1.getDisplayName().compareToIgnoreCase(o2.getDisplayName());
					}
				});
			}
		} catch (Exception e) {
			RemoteConnectionsActivator.logError(e);
			if (e.getCause() instanceof ConnectTimeoutException)
				RemoteConnectionsActivator.logMessage(Messages.getString("InstallPackages.TimeoutMissingProxyMessage"), IStatus.INFO); //$NON-NLS-1$
		}
	}
	
	public List<PackageType> getAvailablePackageList() {
		return packageList;
	}
	
	private static PackagesType loadPackages(URL url) throws Exception  {
		if (url == null)
			return null;

		URI xmlURI = URI.createURI(url.toString());

		InstallPackagesResourceFactoryImpl factory = new InstallPackagesResourceFactoryImpl();
		Resource r = factory.createResource(xmlURI);

		r.load(null);
		EList contents = r.getContents();
	
		DocumentRoot root = (DocumentRoot) contents.get(0);
		PackagesType packages = root.getPackages();

		return packages;
	}

	private PackagesType getAvailablePackagesFromServer() throws Exception {
        GetMethod getMethod = new GetMethod(getRelativePath(serverData.getMasterFileName()));
		HttpClient client = new HttpClient();
		setProxyData(client, getMethod);
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
    		File tempDir = FileUtils.getTemporaryDirectory();
    		IPath path = new Path(tempDir.getAbsolutePath());
    		IPath masterFilePath = path.append(serverData.getMasterFileName());
    		File masterFile = masterFilePath.toFile();
    		if (masterFile.exists())
    			masterFile.delete();
            FileOutputStream fos = new FileOutputStream(masterFile);
            BufferedOutputStream out = new BufferedOutputStream(fos);
            ByteArrayInputStream in = new ByteArrayInputStream(responseBody);
            boolean foundOpenBrace = false;
            int c;
            while ((c = in.read()) != -1) {
            	if (c == '<')
            		foundOpenBrace = true;
            	if (foundOpenBrace)
            		out.write(c);
            }
            out.close();
            in.close();
    		URL url = masterFile.toURI().toURL();
    		return loadPackages(url);
    	}
    	
       return null;
	}
	
	private static java.net.URI getURI(GetMethod getMethod) {
		try {
			return new java.net.URI(getMethod.getURI().toString());
		} catch (Exception e) {
			RemoteConnectionsActivator.logError(e);
		}
		
		return null;
	}

	public static void setProxyData(HttpClient client, GetMethod getMethod) {
		java.net.URI uri = getURI(getMethod);
		if (uri == null)
			return;
		IProxyData proxyData = ProxyUtils.getProxyData(uri);
		if (proxyData == null)
			return;
		String host = proxyData.getHost();
		int port = proxyData.getPort();
		client.getHostConfiguration().setProxy(host, port);
		if (proxyData.isRequiresAuthentication()) {
			String userId = proxyData.getUserId();
			String password = proxyData.getPassword();
			UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(userId, password);
			AuthScope authScope = new AuthScope(host, port);
			client.getState().setCredentials(authScope, credentials);
			getMethod.setDoAuthentication(true);
		}
	}
	
	public String getInstallUrlString(String installFilePath) {
		URL url;
		try {
			url = new URL(installFilePath);
		} catch (MalformedURLException e) {
			return getRelativePath(installFilePath);
		}
		return url.toString();
	}

	private String getRelativePath(String installFilePath) {
		String path = getServerPath();
		return path + "/" + installFilePath; //$NON-NLS-1$
	}

	private String getServerPath() {
		if (serverPath != null)
			return serverPath;
		// see if there's an alternate server, otherwise use IServerData
		Location installLocation = Platform.getInstallLocation();
		URL url = installLocation.getURL();
		IPath path = new Path(url.getPath());
		path = path.append("configuration/server.properties"); //$NON-NLS-1$
		try {
			File file = path.toFile();
			InputStream is = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(is);
			is.close();
			String key = serverData.getRemoteAgentInstallerProvider().getService().getIdentifier();
			String pathStr = (String) properties.get(key);
			if (pathStr != null)
				return serverPath = pathStr;
		} catch (IOException e) {
			RemoteConnectionsActivator.logError(e);
		}
		return ""; //$NON-NLS-1$
	}
}
