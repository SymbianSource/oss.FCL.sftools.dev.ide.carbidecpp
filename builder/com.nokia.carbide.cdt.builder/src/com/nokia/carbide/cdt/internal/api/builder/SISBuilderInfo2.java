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
package com.nokia.carbide.cdt.internal.api.builder;

import org.eclipse.cdt.core.settings.model.ICStorageElement;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cdt.builder.project.ISISBuilderInfo;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.cpp.internal.api.utils.core.TrackedResource;

public class SISBuilderInfo2 implements ISISBuilderInfo {

	private static final String PKGFILESTORAGE = "PKGFILESTORAGE"; //$NON-NLS-1$
	private static final String OUTPUTFILENAMESTORAGE = "OUTPUTFILENAMESTORAGE"; //$NON-NLS-1$
	private static final String SEARCHLOCATIONSTORAGE = "SEARCHLOCATIONSTORAGE"; //$NON-NLS-1$
	private static final String ADDITIONALOPTIONSSTORAGE = "ADDITIONALOPTIONSSTORAGE"; //$NON-NLS-1$
	private static final String CERTIFICATESTORAGE = "CERTIFICATESTORAGE"; //$NON-NLS-1$
	private static final String KEYSTORAGE = "KEYSTORAGE"; //$NON-NLS-1$
	private static final String PASSWORDSTORAGE = "PASSWORDSTORAGE"; //$NON-NLS-1$
	private static final String SIGNEDSISFILENAMESTORAGE = "SIGNEDSISFILENAMESTORAGE"; //$NON-NLS-1$
	private static final String CREATESTUBSTORAGE = "CREATESTUBSTORAGE"; //$NON-NLS-1$
	private static final String SIGNINGTYPESTORAGE = "SIGNINGTYPESTORAGE"; //$NON-NLS-1$
	private static final String ENABLEDSTORAGE = "ENABLEDSTORAGE"; //$NON-NLS-1$
	private static final String PARTIALUPGRADESTORAGE = "PARTIALUPGRADESTORAGE"; //$NON-NLS-1$
	
	
	private TrackedResource projectTracker;
	
	private String pkgFile = "";
	private String outputFilename = ""; //$NON-NLS-1$
	private String searchLocation = ""; //$NON-NLS-1$
	private String additionalOptions = ""; //$NON-NLS-1$
	private String certificate = ""; //$NON-NLS-1$
	private String key = ""; //$NON-NLS-1$
	private String password = ""; //$NON-NLS-1$
	private String signedSisFilename = ""; //$NON-NLS-1$
	private boolean createStub = false;
	private int signingType = DONT_SIGN;
	private boolean isEnabled = true;
	private boolean hasSisChanges = false;
	private boolean hasSisxChanges = false;
	private boolean isPartialUpgrade = false;
	
	
	public SISBuilderInfo2(IProject project) {
		this.projectTracker = new TrackedResource(project);
	}
	
	public SISBuilderInfo2(ISISBuilderInfo oldSisInfo) {
		projectTracker = new TrackedResource(oldSisInfo.getProject());
		pkgFile = oldSisInfo.getPKGFileString();
		outputFilename = oldSisInfo.getUnsignedSISFileName();
		searchLocation = oldSisInfo.getContentSearchLocation();
		additionalOptions = oldSisInfo.getAdditionalOptions();
		certificate = oldSisInfo.getCertificate();
		key = oldSisInfo.getKey();
		password = oldSisInfo.getPassword();
		signedSisFilename = oldSisInfo.getSignedSISFileName();
		createStub = oldSisInfo.isCreateStubFormat();
		signingType = oldSisInfo.getSigningType();
		isEnabled = oldSisInfo.isEnabled();
		if (oldSisInfo instanceof SISBuilderInfo2) {
			SISBuilderInfo2 oldInfo = (SISBuilderInfo2)oldSisInfo;
			hasSisChanges = oldInfo.hasSisChanges();
			hasSisxChanges = oldInfo.hasSisxChanges();
			isPartialUpgrade = oldInfo.isPartialUpgrade();
		}
	}
	
	public IProject getProject() {
		return projectTracker.getProject();
	}

	public void loadFromStorage(ICStorageElement rootStorage) {
		String value = rootStorage.getAttribute(PKGFILESTORAGE);
		if (value != null) {
			pkgFile = value;
		}
		
		value = rootStorage.getAttribute(OUTPUTFILENAMESTORAGE);
		if (value != null) {
			outputFilename = value;
		}

		value = rootStorage.getAttribute(SEARCHLOCATIONSTORAGE);
		if (value != null) {
			searchLocation = value;
		}

		value = rootStorage.getAttribute(ADDITIONALOPTIONSSTORAGE);
		if (value != null) {
			additionalOptions = value;
		}

		value = rootStorage.getAttribute(CERTIFICATESTORAGE);
		if (value != null) {
			certificate = value;
		}

		value = rootStorage.getAttribute(KEYSTORAGE);
		if (value != null) {
			key = value;
		}

		value = rootStorage.getAttribute(PASSWORDSTORAGE);
		if (value != null) {
			password = value;
		}

		value = rootStorage.getAttribute(SIGNEDSISFILENAMESTORAGE);
		if (value != null) {
			signedSisFilename = value;
		}
		
		value = rootStorage.getAttribute(CREATESTUBSTORAGE);
		if (value != null) {
			createStub = (value.compareToIgnoreCase("true") == 0); //$NON-NLS-1$
		}

		value = rootStorage.getAttribute(SIGNINGTYPESTORAGE);
		if (value != null) {
			try {
				signingType = Integer.parseInt(value);
			} catch (NumberFormatException e) {
			}
		}
		
		value = rootStorage.getAttribute(ENABLEDSTORAGE);
		if (value != null) {
			isEnabled = (value.compareToIgnoreCase("true") == 0); //$NON-NLS-1$
		}
		
		value = rootStorage.getAttribute(PARTIALUPGRADESTORAGE);
		if (value != null) {
			isPartialUpgrade = (value.compareToIgnoreCase("true") == 0); //$NON-NLS-1$
		}
	}
	
	public void saveToStorage(ICStorageElement rootStorage) {
		if (pkgFile.trim().length() > 0) {
			rootStorage.setAttribute(PKGFILESTORAGE, pkgFile);
		}

		if (outputFilename.trim().length() > 0) {
			rootStorage.setAttribute(OUTPUTFILENAMESTORAGE, outputFilename);
		}

		if (searchLocation.trim().length() > 0) {
			rootStorage.setAttribute(SEARCHLOCATIONSTORAGE, searchLocation);
		}

		if (additionalOptions.trim().length() > 0) {
			rootStorage.setAttribute(ADDITIONALOPTIONSSTORAGE, additionalOptions);
		}

		if (certificate.trim().length() > 0) {
			rootStorage.setAttribute(CERTIFICATESTORAGE, certificate);
		}

		if (key.trim().length() > 0) {
			rootStorage.setAttribute(KEYSTORAGE, key);
		}

		if (password.trim().length() > 0) {
			rootStorage.setAttribute(PASSWORDSTORAGE, password);
		}

		if (signedSisFilename.trim().length() > 0) {
			rootStorage.setAttribute(SIGNEDSISFILENAMESTORAGE, signedSisFilename);
		}

		rootStorage.setAttribute(CREATESTUBSTORAGE, createStub ? "true" : "false"); //$NON-NLS-1$ //$NON-NLS-2$

		try {
			rootStorage.setAttribute(SIGNINGTYPESTORAGE, Integer.toString(signingType));
		} catch (NumberFormatException e) {
		}

		rootStorage.setAttribute(ENABLEDSTORAGE, isEnabled ? "true" : "false"); //$NON-NLS-1$ //$NON-NLS-2$

		rootStorage.setAttribute(PARTIALUPGRADESTORAGE, isPartialUpgrade ? "true" : "false"); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	public IPath getFinalSISFullPath() {
		ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(projectTracker.getProject());
		if (cpi != null) {
			ISymbianSDK sdk = cpi.getDefaultConfiguration().getSDK();
			if (sdk.getOSVersion().getMajor() < 9) {
				return getUnsignedSISFullPath();
			}
		}
		return getSignedSISFullPath();
	}

	
	public void setPKGFile(String pkgFileString) {
		if (pkgFile.compareTo(pkgFileString) != 0) {
			hasSisChanges = true;
			pkgFile = pkgFileString;
		}
	}
	
	public String getPKGFileString() {
		return pkgFile;
	}
	
	public void setContentSearchLocation(String searchLocale) {
		if (searchLocation.compareTo(searchLocale) != 0) {
			hasSisChanges = true;
			searchLocation = searchLocale;
		}
	}
	
	public String getContentSearchLocation() {
		return searchLocation;
	}
	
	public IPath getPKGFullPath() {
		IPath fullPath;
		if (getPKGFileString().indexOf(":") > 0) { //$NON-NLS-1$
			fullPath = new Path(getPKGFileString());
		} else {		
			fullPath = CarbideBuilderPlugin.getProjectRoot(projectTracker.getProject());
			if (fullPath != null)
				fullPath = fullPath.append(getPKGFileString());
		}
		return fullPath;
	}
	
	public String getCertificate() {
		return certificate;
	}
	
	public IPath getCertificateFullPath() {
		IPath fullPath;
		if (getCertificate().indexOf(":") > 0) { //$NON-NLS-1$
			fullPath = new Path(getCertificate());
		} else {		
			fullPath = CarbideBuilderPlugin.getProjectRoot(projectTracker.getProject());
			fullPath = fullPath.append(getCertificate());
		}
		return fullPath;
	}

	public String getKey() {
		return key;
	}
	
	public IPath getKeyFullPath() {
		IPath fullPath;
		if (getKey().indexOf(":") > 0) { //$NON-NLS-1$
			fullPath = new Path(getKey());
		} else {		
			fullPath = CarbideBuilderPlugin.getProjectRoot(projectTracker.getProject());
			fullPath = fullPath.append(getKey());
		}
		return fullPath;
	}

	public String getUnsignedSISFileName() {
		return outputFilename;
	}
	
	public IPath getUnsignedSISFullPath() {
		IPath fullPath;
		if (getUnsignedSISFileName().length() == 0) {
			// Use default PKG file name
			fullPath = getPKGFullPath();
			fullPath = fullPath.removeFileExtension();
			fullPath = fullPath.addFileExtension("sis"); //$NON-NLS-1$
		} else if (getUnsignedSISFileName().indexOf(":") > 0) { //$NON-NLS-1$
			// SIS already a full path
			fullPath = new Path(getUnsignedSISFileName());
		} else {
			// probably a relative path, make relative to PKG file
			fullPath = getPKGFullPath();
			fullPath = fullPath.removeLastSegments(1);
			fullPath = fullPath.append(getUnsignedSISFileName());
		}
		return fullPath;
	}

	public String getPassword() {
		return password;
	}

	public String getSignedSISFileName() {
		return signedSisFilename;
	}
	
	public IPath getSignedSISFullPath() {
		IPath fullPath;
		if (getSignedSISFileName().length() == 0) {
			// Use default PKG file name
			fullPath = getPKGFullPath();
			fullPath = fullPath.removeFileExtension();
			fullPath = fullPath.addFileExtension("sisx"); //$NON-NLS-1$
		} else if (getSignedSISFileName().indexOf(":") > 0) { //$NON-NLS-1$
			// SIS already a full path
			fullPath = new Path(getSignedSISFileName());
		} else {
			// probably a relative path, make relative to PKG file
			fullPath = getPKGFullPath();
			fullPath = fullPath.removeLastSegments(1);
			fullPath = fullPath.append(getSignedSISFileName());
		}
		return fullPath;
	}

	public void setCertificate(String certString) {
		if (certificate.compareTo(certString) != 0) {
			hasSisxChanges = true;
			certificate = certString;
		}
	}

	public void setKey(String keyString) {
		if (key.compareTo(keyString) != 0) {
			hasSisxChanges = true;
			key = keyString;
		}
	}

	public void setOutputSISFileName(String outputFileName) {
		if (this.outputFilename.compareTo(outputFileName) != 0) {
			hasSisChanges = true;
			this.outputFilename = outputFileName;
		}
	}

	public void setPassword(String password) {
		if (this.password.compareTo(password) != 0) {
			hasSisxChanges = true;
			this.password = password;
		}
	}

	public void setSignedSISFileName(String signedFileName) {
		if (signedSisFilename.compareTo(signedFileName) != 0) {
			hasSisxChanges = true;
			signedSisFilename = signedFileName;
		}
	}

	public String getAdditionalOptions() {
		return additionalOptions;
	}

	public boolean isCreateStubFormat() {
		return createStub;
	}

	public void setAdditionalOptions(String options) {
		if (additionalOptions.compareTo(options) != 0) {
			hasSisxChanges = true;
			additionalOptions = options;
		}
	}

	public void setCreateStubFormat(boolean flag) {
		if (createStub != flag) {
			hasSisChanges = true;
			createStub = flag;
		}
	}

	public int getSigningType() {
		return signingType;
	}

	public void setSigningType(int type) {
		if (type >= DONT_SIGN && type <= KEY_CERT_SIGN) {
			if (signingType != type) {
				hasSisxChanges = true;
				signingType = type;
			}
		}
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean enable) {
		isEnabled = enable;
	}
	
	public boolean hasSisChanges() {
		return hasSisChanges;
	}
	
	public void setHasSisChanges(boolean changed) {
		hasSisChanges = changed;
	}

	public boolean hasSisxChanges() {
		return hasSisxChanges;
	}
	
	public void setHasSisxChanges(boolean changed) {
		hasSisxChanges = changed;
	}

	/**
	 * Returns whether or not a partial upgrade should be built if appropriate
	 * @since 2.0
	 */
	public boolean isPartialUpgrade() {
		return isPartialUpgrade;
	}

	/**
	 * Sets whether or not a partial upgrade should be built if appropriate
	 * @since 2.0
	 */
	public void setPartialUpgrade(boolean enable) {
		// not setting hasSisChanges intentionally here
		isPartialUpgrade = enable;
	}
}
