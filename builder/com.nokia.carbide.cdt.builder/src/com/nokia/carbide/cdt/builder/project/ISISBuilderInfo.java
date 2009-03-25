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
package com.nokia.carbide.cdt.builder.project;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;

/**
 * ISISBuilderInfo represents one instance of configuration data for building SIS files.
 * @see ICarbideBuildConfiguration
 */
public interface ISISBuilderInfo {
	
	public static final int DONT_SIGN = 0;
	public static final int SELF_SIGN = 1;
	public static final int KEY_CERT_SIGN = 2;

	
	/**
	 * Get the project this SIS builder info belongs to
	 * @return
	 */
	IProject getProject();
	
	/**
	 * Get the final SIS file that created by the SIS builer. This may be an unsigned SIS file (OS 8.x and prior)
	 * or a signed sis file (OS 9+).
	 * @return IPath of the final output sis file. File is not guaranteed to exist.
	 */
	IPath getFinalSISFullPath();
	
	/**
	 * Set the PKG file string as it should be written to the settings file 
	 * @param pkgFile
	 */
	void setPKGFile(String pkgFile);
	
	/**
	 * Get the key file string as it should be written to the settings file
	 * @param keyString
	 */
	void setKey(String keyString);
	
	/**
	 * Set the certificate file string as it should be written to the settings file 
	 * @param certString
	 */
	void setCertificate(String certString);
	
	/**
	 * Set the password string as it should be written to the settings file 
	 * @param password
	 */
	void setPassword(String password);
	
	/**
	 * Set the outputFileName string as it should be written to the settings file 
	 * @param outputFileName
	 */
	void setOutputSISFileName(String outputFileName);
	
	/**
	 * Set the signedFileName string as it should be written to the settings file  
	 * @param signedFileName
	 */
	void setSignedSISFileName(String signedFileName);
	
	/**
	 * Set the options string as it should be written to the settings file  
	 * @param options
	 */
	void setAdditionalOptions(String options);
	
	/**
	 * Set the flag to create stub format   
	 * @param flag
	 */
	void setCreateStubFormat(boolean flag);
	
	/**
	 * Get the PKG file string as it comes from the settings file
	 * @return
	 */
	String getPKGFileString();
	
	/**
	 * Get the full path of the PKG file resolved from it's current project location
	 * @return
	 */
	IPath getPKGFullPath();
	
	/**
	 * Get the key string as it comes from the settings file
	 * @return
	 */
	String getKey();
	
	/**
	 * Get the key full path
	 * @return
	 */
	IPath getKeyFullPath();
	
	/**
	 * Get the cert string as it comes from the settings file
	 * @return
	 */
	String getCertificate();
	
	/**
	 * Get the cert full path
	 * @return IPath
	 */
	IPath getCertificateFullPath();
	
	/**
	 * Get the password string as it comes from the settings file
	 * @return
	 */
	String getPassword();
	
	/**
	 * Get the output sis file name as it comes from the settings file
	 * @return The string as it comes from the settings file. May be an empty string.
	 */
	String getUnsignedSISFileName();
	
	/**
	 * Get the full path of the UNSIGNED SIS file name (from makesis.exe)
	 * @return IPath object. The file is not guaranteed to exist.
	 */
	IPath getUnsignedSISFullPath();
	
	/**
	 * Get the output SIGNED sis file name as it comes from the settings file 
	 * @return The string as it comes from the settings file. May be an empty string.
	 */
	String getSignedSISFileName();
	
	/**
	 * Get the full path of the SIGNED SIS file name from signedsis.exe.
	 * @return IPath object. The file is not guaranteed to exist.
	 */
	IPath getSignedSISFullPath();
	
	/**
	 * Get the additional options string as it comes from the settings file 
	 * @return
	 */
	String getAdditionalOptions();
	
	/**
	 * Get the flag if set to create a stub format file
	 * @return
	 */
	boolean isCreateStubFormat();
	
	/**
	 * Set the path to the content search location for makesis. Assumes a full path string.
	 * @param searchLocale
	 */
	public void setContentSearchLocation(String searchLocale);
	
	/**
	 * Get the path to the content search location for makesis that is stored in the configuration settings
	 * @param searchLocale
	 */
	public String getContentSearchLocation();
	
	/**
	 * Get the signing method.  See {@link #DONT_SIGN}, {@link #SELF_SIGN}, {@value #KEY_CERT_SIGN}
	 * @return one of {@link #DONT_SIGN}, {@link #SELF_SIGN}, {@value #KEY_CERT_SIGN}
	 */
	public int getSigningType();
	
	/**
	 * Set the signing method.  See {@link #DONT_SIGN}, {@link #SELF_SIGN}, {@value #KEY_CERT_SIGN}
	 * @param type one of {@link #DONT_SIGN}, {@link #SELF_SIGN}, {@value #KEY_CERT_SIGN}
	 */
	public void setSigningType(int type);
	
	/**
	 * Get the enabled state
	 * @return true if should be built, false otherwise
	 */
	public boolean isEnabled();
	
	/**
	 * Set the enabled state
	 * @param enable true if should be built, false otherwise
	 */
	public void setEnabled(boolean enable);

}
