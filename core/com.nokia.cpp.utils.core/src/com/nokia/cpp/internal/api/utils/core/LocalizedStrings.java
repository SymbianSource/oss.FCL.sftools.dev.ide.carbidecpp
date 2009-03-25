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
package com.nokia.cpp.internal.api.utils.core;

import com.nokia.cpp.utils.core.noexport.UtilsCorePlugin;

import org.eclipse.core.runtime.IStatus;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.*;

	/**
	 * Simple localized string utility class. Used
	 * for component files because we can't rely on
	 * the classpath for user component projects.
	 *
	 */
public class LocalizedStrings implements ILocalizedStrings {
	private URL directoryURL;
		// base file name, without any locale
	private String baseName;
	
		// map Locale -> Properties
	private HashMap localeMap = new HashMap();
	
	final static String EXTENSION = ".properties"; //$NON-NLS-1$
	
	public LocalizedStrings(File directory, String baseName) {
		this((URL) null, baseName);
		try {
			setDirectoryURL(directory.toURL());
		} catch (MalformedURLException e) {
			Check.failedArg(e);
		}
	}
	
	public LocalizedStrings(URL directoryURL, String baseName) {
		//Check.checkArg(plugin); // EJS: not needed
		Check.checkArg(baseName);
		this.directoryURL = directoryURL;
		this.baseName = baseName;
	}
	
	public void setDirectoryURL(URL directoryURL) {
		this.directoryURL = directoryURL;
	}
	
	public String getString(String key) {
		if (key == null)
			return null;
		return getString(key, Locale.getDefault());
	}
	
	public String getString(String key, Locale l) {
		String result = null;
		Properties props = getPropertiesForLocale(l);
		if (props != null) {
			result = props.getProperty(key);
		}

		if (result == null)
			return "!" + key + "!";
		
		return result;
	}
	
	/**
	 * Treats strings beginning with a single % as a key.
	 * The % is stripped before lookup.
	 * The prefix %% is an escape for a single %
	 */
	public String checkPercentKey(String s) {
		String result;
		if (s == null)
			result = null;
		else if (s.startsWith("%")) {
			s = s.substring(1);
			if (!s.startsWith("%")) {
				result = getString(s);
			}
			else
				result = s;
		}
		else
			result = s;
		return result;
	}
	
	public boolean hasString(String s) {
		return hasStringForLocale(s, Locale.getDefault());
	}
	
	public boolean hasStringForLocale(String s, Locale l) {
		boolean result = false;
		Properties props = getPropertiesForLocale(l);
		if (props != null) {
			result = props.get(s) != null;
		}
		return result;
	}
	
	private Properties getPropertiesForLocale(Locale l) {
		Properties result = null;
		synchronized(localeMap) {
			result = (Properties) localeMap.get(l);
			if (result == null) {
				// to set up the defaults we begin with the most
				// general locale and progress to the most specific
				Properties lastFound = null;
				Properties props = loadProperties("", "", "", null); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				if (props != null) lastFound = props;
				props = loadProperties(l.getLanguage(), "", "", lastFound); //$NON-NLS-1$ //$NON-NLS-2$
				if (props != null) lastFound = props;
				props = loadProperties(l.getLanguage(), l.getCountry(), "", lastFound); //$NON-NLS-1$
				if (props != null) lastFound = props;
				props = loadProperties(l.getLanguage(), l.getCountry(), l.getVariant(), lastFound);
				if (props != null) lastFound = props;
				
				result = lastFound;
				localeMap.put(l, result);
			}
		}
		return result;
	}
	
	private boolean nonEmpty(String s) {
		return s != null && s.length() > 0;
	}
	
	private String makeFileName(String lang, String country, String variant) {
		StringBuffer result = new StringBuffer(baseName);
		if (nonEmpty(lang)) {
			result.append("_"); //$NON-NLS-1$
			result.append(lang);
			if (nonEmpty(country)) {
				result.append("_"); //$NON-NLS-1$
				result.append(country);
				if (nonEmpty(variant)) {
					result.append("_"); //$NON-NLS-1$
					result.append(variant);
				}
			}
		}
		result.append(EXTENSION);
		return result.toString();
	}
	
	private Properties loadProperties(String lang, String country, String variant, Properties defaults) {
		Properties result = null;
		String fileName = makeFileName(lang, country, variant);
		try {
			URL propUrl = new URL(directoryURL, fileName);
			InputStream is = propUrl.openStream();
			if (is != null) {
					result = new Properties(defaults);
					result.load(is);
					is.close();
			}
		} catch (Exception e) {
			if (!(e instanceof FileNotFoundException)) {
				result = null;
				String format = "Error loading properties from {0}";
				Object args[] = { fileName };
				String msg = MessageFormat.format(format, args);
				Logging.log(UtilsCorePlugin.getDefault(), 
						Logging.newStatus(UtilsCorePlugin.getDefault(), IStatus.ERROR, msg));
			}
		}
		return result;
	}
	
	

}
