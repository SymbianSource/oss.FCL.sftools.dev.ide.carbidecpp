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


package com.nokia.carbide.remoteconnections.interfaces;

import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Version;

import java.io.InputStream;
import java.util.List;

/**
 * An interface to provide installer packages in a tree format.
 * The structure of the tree is: SDK family -> Version -> Installer packages
 * E.g., S60 -> 3.2 -> Application TRK 2.8.9 Installer
 * 
 * NOTE: multiple providers may contribute to the same tree.
 */
public interface IRemoteAgentInstallerProvider {
	
	/**
	 * The interface for a single installer package node in the installer tree
	 */
	public interface IRemoteAgentInstaller {
		
		/**
		 * An interface for a single installer package contents
		 */
		public interface IPackageContents {
			
			/**
			 * The file name to use when a user is asked to save the file locally
			 * @return String
			 */
			String getDefaultNameFileName();
			
			/**
			 * The contents of the package
			 * @return InputStream
			 */
			InputStream getInputStream();
		}
		
		/**
		 * The image to use for the installer package node
		 * @return Image
		 */
		Image getImage(); 
		
		/**
		 * The label to use for the installer package node
		 * @return String
		 */
		String getLabel();
		
		/**
		 * Return the version of the installer package
		 * @return Version
		 */
		Version getInstallerVersion();
		
		/**
		 * A textual additional information or instructions for the installer package node
		 * @return String
		 */
		String getInformation();
		
		/**
		 * Whether the file can be launched as installer or just saved by user
		 * @return boolean
		 */
		boolean fileSupportsInstall();
		
		/**
		 * Retrieve the package contents
		 * @return IPackageContents
		 */
		IPackageContents getPackageContents(IRunnableContext runnableContext);
		
	}
	
	/**
	 * Return the IService for this installer provider
	 * @return IService
	 */
	IService getService();

	/**
	 * Return the top level strings to use for sdk family names
	 * @return List
	 */
	List<String> getSDKFamilyNames(IRunnableContext runnableContext);
	
	/**
	 * Return the second level strings to use for versions for a given sdk family name
	 * @param familyName String
	 * @return List
	 */
	List<Version> getVersions(String familyName);
	
	/**
	 * Return the IRemoteAgentInstaller objects for a given sdk family + version qualifier
	 * @param familyName
	 * @param version
	 * @return
	 */
	List<IRemoteAgentInstaller> getRemoteAgentInstallers(String familyName, Version version);
	
	/**
	 * Dispose this provider
	 */
	void dispose();
}
