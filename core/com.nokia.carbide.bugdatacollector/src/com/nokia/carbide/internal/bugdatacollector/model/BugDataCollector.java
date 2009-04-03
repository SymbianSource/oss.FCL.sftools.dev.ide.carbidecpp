/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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
 
package com.nokia.carbide.internal.bugdatacollector.model;

import java.util.Hashtable;
import org.eclipse.core.runtime.FileLocator;

import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.widgets.Shell;
import org.osgi.framework.Bundle;
import org.osgi.framework.Version;

import com.nokia.carbide.cpp.sdk.core.*;
import java.io.*;
import java.net.URL;
import com.nokia.carbide.cpp.ProductPlugin;
import com.nokia.carbide.internal.bugdatacollector.plugin.BugDataCollectorPlugin;
import com.nokia.carbide.internal.bugdatacollector.resources.Messages;
import com.nokia.carbide.internal.bugdatacollector.ui.preferences.BugDataCollectorPreferencePage;
import com.nokia.carbide.internal.bugreport.export.*;


/**
 * This class implements the IProduct interface and provides 
 * Carbide related information to the bug-reporter.
 *
 */
public class BugDataCollector implements IProduct {
	final String CARBIDE_FEATURE = "com.nokia.carbide.cpp"; //$NON-NLS-1$
	final String FIELD_VALUE_COMPONENT = "Aloha Support Request"; //$NON-NLS-1$
	final String FIELD_COMPONENT = "component"; //$NON-NLS-1$
	final String FIELD_VERSION = "version"; //$NON-NLS-1$
	final String FIELD_BUILD = "cf_product_bld"; //$NON-NLS-1$
	final String FIELD_USERNAME = "Bugzilla_login"; //$NON-NLS-1$
	final String FIELD_PASSWORD = "Bugzilla_password"; //$NON-NLS-1$
	final String FIELD_PRODUCT_NAME = "Carbide.c.Public"; //$NON-NLS-1$
	final String VERSION_PREFIX = "v"; //$NON-NLS-1$
	final String DIAGNOSTIC_LOG_FILE_NAME = "Carbide_Debugger_Log.xml"; //$NON-NLS-1$
	final String ABOUT_MAPPINGS_ENTRY = "/about.mappings"; //$NON-NLS-1$
	final long MAX_ATTACHMENT_SIZE = 10*1024*1024; // 10 megabytes
	final int MAX_DESCRIPTION_LENGTH = 100000;
	final int MAX_SUMMARY_LENGTH = 255;
	final int VERSION_INDEX = 0;
	final int QUALIFIER_INDEX = 1;
	
	public String getDescriptionLabelText() {
		return Messages.getString("BugDataCollector.DescriptionLabelText"); //$NON-NLS-1$
	}

	public String getPageDescriptionText() {
		return Messages.getString("BugDataCollector.PageDescriptionText"); //$NON-NLS-1$
	}
	
	public Hashtable<String, String> getFields() {
		String[] versionInfo = getCarbideVersion();
		Hashtable<String, String> fields = new Hashtable<String, String>();
		fields.put(FIELD_COMPONENT, FIELD_VALUE_COMPONENT);
		fields.put(FIELD_VERSION, versionInfo[VERSION_INDEX]);
		fields.put(FIELD_BUILD, versionInfo[QUALIFIER_INDEX]);
		fields.put(FIELD_USERNAME, BugDataCollectorPreferencePage.getUsername());
		fields.put(FIELD_PASSWORD, BugDataCollectorPreferencePage.getPassword());
		
		return fields;
	}
	
	public String[] getAttachments() {
		String sdkFile = ""; //$NON-NLS-1$
		String diagnosticFile = ""; //$NON-NLS-1$
		if (BugDataCollectorPreferencePage.sendSdkData()) {
			try {
				String devicesXml = ISDKManager.DEFAULT_DEVICES_XML_DIR + ISDKManager.DEVICES_FILE_NAME;
				File f = new File(devicesXml);
				if (f.exists() && f.isFile()) {
					sdkFile = devicesXml;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (BugDataCollectorPreferencePage.sendDiagnostic()) {
			try {
				String diagnosticLog = Platform.getLocation().addTrailingSeparator().toOSString() + DIAGNOSTIC_LOG_FILE_NAME;
				File f = new File(diagnosticLog);
				if (f.exists() && f.isFile()) {
					diagnosticFile = diagnosticLog;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (sdkFile != "" && diagnosticFile != "") { //$NON-NLS-1$ //$NON-NLS-2$
			String[] files = {sdkFile, diagnosticFile};
			return files;
		} else if (sdkFile != "") { //$NON-NLS-1$
			String[] files = {sdkFile};
			return files;
		} else if (diagnosticFile != "") { //$NON-NLS-1$
			String[] files = {diagnosticFile};
			return files;
		} else {
			return null;
		}
	}

	public long getMaxAttachmentSize() {
		return MAX_ATTACHMENT_SIZE;
	}

	public int getMaxDescriptionLength() {
		return MAX_DESCRIPTION_LENGTH;
	}

	public int getMaxSummaryLength() {
		return MAX_SUMMARY_LENGTH;
	}

	public String getPageTitleText() {
		return Messages.getString("BugDataCollector.PageTitleText"); //$NON-NLS-1$
	}

	public String getUrl() {
		return Platform.getResourceString(BugDataCollectorPlugin.getDefault().getBundle(), "%data.Url"); //$NON-NLS-1$
	}
	
	public String getShowBugUrl(String bugId) {
		return String.format(Platform.getResourceString(BugDataCollectorPlugin.getDefault().getBundle(), "%data.BugUrl"),bugId); //$NON-NLS-1$
	}
	
	public String getConsoleText(String bugId) {
		return String.format(Messages.getString("BugDataCollector.ConsoleLine"), bugId); //$NON-NLS-1$
	}
	
	public boolean uiServiceNeeded() {
		// we need ui service (i.e. show a preference page) when 
		// username and/or password are not set.
		if (BugDataCollectorPreferencePage.getUsername() == "" ||  //$NON-NLS-1$
			BugDataCollectorPreferencePage.getPassword() == "") //$NON-NLS-1$
			return true;
		return false;
	}
	
	public String getUiServiceText() {
		return Messages.getString("BugDataCollector.UiServiceText"); //$NON-NLS-1$
	}
	
	public void showUiService(Shell shell) {
		BugDataCollectorPreferencePage.showYourself(shell);
	}
	
	public String getUiServiceLinkText() {
		return Messages.getString("BugDataCollector.UiServiceLinkText"); //$NON-NLS-1$
	}
	
	public String getProductName() {
		return FIELD_PRODUCT_NAME;
	}
	
	/**
	 * Tries to read about.mappings file in com.nokia.carbide.cpp plugin.
	 * The full build number is read from about.mappings.
	 * @param productPluginBundle com.nokia.carbide.cpp.ProductPlugin
	 * @return  "" if failed, otherwise e.g. "019.200801310205"
	 */
	String tryToReadFullBuildNumber(Bundle productPluginBundle) {
		String buildNumber = ""; //$NON-NLS-1$
		try {
			URL relativeURL = productPluginBundle.getEntry(ABOUT_MAPPINGS_ENTRY);
			URL localURL = FileLocator.toFileURL(relativeURL);
			File file = new File(localURL.getPath());
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			try {
				while ((line = reader.readLine()) != null) {
					if (line.startsWith("0=")) { //$NON-NLS-1$
						buildNumber = line.substring(2);
						break;
					}
				}
			} finally {
				reader.close();
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return buildNumber;
	}

	/**
	 * Reads the Carbide version programmatically.
	 * @return Carbide version e.g. {"v1.3.0","9"}
	 */
	String[] getCarbideVersion() {
		String[] version = {"", ""}; //$NON-NLS-1$ //$NON-NLS-2$
		try {
			String bundleVersion = (String) ProductPlugin.getDefault().getBundle().getHeaders().get("Bundle-Version"); //$NON-NLS-1$
			Version pluginVersion = new Version(bundleVersion);
			version[VERSION_INDEX] = VERSION_PREFIX + pluginVersion.getMajor() + "." + //$NON-NLS-1$
													  pluginVersion.getMinor() + "." + //$NON-NLS-1$
													  pluginVersion.getMicro();
			version[QUALIFIER_INDEX] = pluginVersion.getQualifier();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return version;
	}
	
}
