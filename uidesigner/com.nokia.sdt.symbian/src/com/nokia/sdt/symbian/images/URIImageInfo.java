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

package com.nokia.sdt.symbian.images;

import com.nokia.carbide.cpp.ui.images.IImageModel;
import com.nokia.sdt.symbian.SymbianPlugin;
import com.nokia.sdt.symbian.images.uriHandlers.*;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Logging;

import org.eclipse.core.runtime.IStatus;

import java.text.MessageFormat;
import java.util.*;

/**
 * This class represents an image represented by a URI in UIQ.
 * <p>
 * Unfortunately, UIQ's URIs violate RFC 2396 so we can't use java.net.URI or
 * EMF's URI.  Instead we roll our own... sigh.
 * 
 *
 */
public class URIImageInfo {

	/** List of handlers for URI schemes */
	static Map<String, IURIImageInfoSchemeHandlerFactory> registeredSchemeHandlers = 
		new HashMap<String,IURIImageInfoSchemeHandlerFactory>();
	
	static {
		registerSchemeHandler("", new FileURIImageInfoSchemeHandlerFactory()); //$NON-NLS-1$
		registerSchemeHandler("file", new FileURIImageInfoSchemeHandlerFactory()); //$NON-NLS-1$
		registerSchemeHandler("uiq_themed", new UIQSystemURIImageInfoSchemeHandlerFactory()); //$NON-NLS-1$
		registerSchemeHandler("uiq_icon", new UIQSystemURIImageInfoSchemeHandlerFactory()); //$NON-NLS-1$
		registerSchemeHandler("uiq_appicon", new UIQSystemURIImageInfoSchemeHandlerFactory()); //$NON-NLS-1$
		registerSchemeHandler("uiq_thumb", new UIQThumbURIImageInfoSchemeHandlerFactory()); //$NON-NLS-1$
	}
	
	public static void registerSchemeHandler(String scheme, IURIImageInfoSchemeHandlerFactory handler) {
		registeredSchemeHandlers.put(scheme, handler);
	}
	
	private String scheme; 
	private String schemeSpecificPart; 
	private boolean simpleFile;
	private String uristring;
	private String query;
	private String fullPath;
	private IURIImageSchemeHandler schemeHandler;
	private ProjectImageInfo projectImageInfo;
	
	public URIImageInfo(ProjectImageInfo info, String uristring) {
		Check.checkArg(uristring);
		Check.checkArg(info);
		this.projectImageInfo = info;
		this.uristring = uristring;
		
		// check for a bare filepath
		int colonIdx = uristring.indexOf(':');
		int queryIdx = uristring.indexOf('?');
		if (colonIdx < 0) {
			scheme = ""; //$NON-NLS-1$
			schemeSpecificPart = uristring;
		} else if (colonIdx == 1) {
			// drive letter
			schemeSpecificPart = uristring;
			scheme = ""; //$NON-NLS-1$
			colonIdx = -1;
		} else {
			// some other UIQ URI
			scheme = uristring.substring(0, colonIdx);
			schemeSpecificPart = uristring.substring(colonIdx + 1);
		}
		
		if (queryIdx >= 0) {
			query = uristring.substring(queryIdx + 1);
			fullPath = uristring.substring(colonIdx + 1, queryIdx);
		} else {
			fullPath = schemeSpecificPart;
			if (scheme.length() == 0 || scheme.equals("file")) { //$NON-NLS-1$
				simpleFile = true;
				// remove authority
				if (fullPath.startsWith("//")) { //$NON-NLS-1$
					fullPath = fullPath.substring(2);
					// and initial slash if full path with device
					if (fullPath.length() > 3  
							&& fullPath.charAt(0) == '/'
							&& Character.isLetter(fullPath.charAt(1))
							&& fullPath.charAt(2) == ':') {
						fullPath = fullPath.substring(1);
					}
				}
			}
		}
		IURIImageInfoSchemeHandlerFactory factory = registeredSchemeHandlers.get(scheme);
		if (factory != null) {
			schemeHandler = factory.createHandler(projectImageInfo);
		}
	}

	/** Tell whether the URI refers to a file, only (not an MBM). */
	public boolean isSimpleFile() {
		return simpleFile;
	}
	
	/** Get the URI scheme or blank if none */
	public String getScheme() {
		return scheme;
	}

	/** Get the URI's scheme-specific part, everything after the colon in this interpretation */
	public String getSchemeSpecificPart() {
		return schemeSpecificPart;
	}

	/** Get the full path, or everything after the scheme but before the query */
	public String getFullPath() {
		return fullPath;
	}
	
	/** Return the content of the query, or null */
	public String getQuery() {
		return query;
	}
	
	/** Get the string that will appear in the property. */
	public String getPropertyString() {
    	return uristring;
	}
	
	/**
	 * Tell if the URI's scheme is recognized.
	 * @return boolean
	 */
	public boolean isKnownScheme() {
		return registeredSchemeHandlers.containsKey(scheme);
	}

	/**
	 * Get an array of the schemes known to URIs.
	 * @return array, never null
	 */
	public String[] getKnownSchemes() {
		Set<String> keySet = registeredSchemeHandlers.keySet();
		return (String[]) keySet.toArray(new String[keySet.size()]);
	}

	/**
	 * Get the handler for images under this scheme.
	 * @return {@link IURIImageSchemeHandler} or <code>null</code>
	 */
	public IURIImageSchemeHandler getSchemeHandler() {
		return schemeHandler;
	}
	
	public IStatus validate() {
		if (schemeHandler == null) {
			// bad scheme
			StringBuilder schemeList = new StringBuilder();
			for (String scheme : getKnownSchemes()) { 
				schemeList.append(", "); //$NON-NLS-1$
				schemeList.append(scheme);
			}
			return Logging.newStatus(SymbianPlugin.getDefault(), IStatus.WARNING,  
					MessageFormat.format(
							Messages.getString("URIImageInfo.InvalidURISchemeFormat"), //$NON-NLS-1$
							new Object[] { scheme, schemeList }));
		} else {
			// check the scheme validation
			return schemeHandler.validate(scheme, fullPath, query);
			/*
			IImageModel model = schemeHandler.getImageModel(scheme, fullPath, query);
			if (model == null) {
				return Logging.newStatus(SymbianPlugin.getDefault(), IStatus.WARNING,  
					"Could not find an image in the build that resolves to the URI");
			}
			return model.validate();*/
		}
		
	}
	public IImageModel getImageModel() {
		if (schemeHandler == null)
			return null;
		return schemeHandler.getImageModel(scheme, fullPath, query);
	}
}
