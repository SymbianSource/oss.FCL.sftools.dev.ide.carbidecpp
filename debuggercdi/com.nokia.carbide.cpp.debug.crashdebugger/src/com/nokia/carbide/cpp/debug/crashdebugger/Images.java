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
package com.nokia.carbide.cpp.debug.crashdebugger;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;

public class Images {
	private static final String NAME_PREFIX= CrashDebuggerPlugin.PLUGIN_ID + '.';
	private static final int NAME_PREFIX_LENGTH= NAME_PREFIX.length();
	
	// The plugin registry
	private static ImageRegistry imageRegistry = new ImageRegistry();

	// Subdirectory (under the package containing this class) where 16 color images are
	private static URL fgIconBaseURL;
	static {
		fgIconBaseURL= Platform.getBundle(CrashDebuggerPlugin.getUniqueIdentifier()).getEntry("/icons/"); //$NON-NLS-1$
	}	

	public static String IMG_TERMINATE_ENABLED = NAME_PREFIX + "stop_enabled.gif"; //$NON-NLS-1$
	public static String IMG_TERMINATE_DISABLED = NAME_PREFIX + "stop_disabled.gif"; //$NON-NLS-1$
	public static String IMG_CLEAR_ENABLED = NAME_PREFIX + "clear_enabled.gif"; //$NON-NLS-1$
	public static String IMG_CLEAR_DISABLED = NAME_PREFIX + "clear_disabled.gif"; //$NON-NLS-1$

	public static final ImageDescriptor DESC_TERMINATE_ENABLED = createManaged("", IMG_TERMINATE_ENABLED); //$NON-NLS-1$
	public static final ImageDescriptor DESC_TERMINATE_DISABLED = createManaged("", IMG_TERMINATE_DISABLED); //$NON-NLS-1$
	public static final ImageDescriptor DESC_CLEAR_ENABLED = createManaged("", IMG_CLEAR_ENABLED); //$NON-NLS-1$
	public static final ImageDescriptor DESC_CLEAR_DISABLED = createManaged("", IMG_CLEAR_DISABLED); //$NON-NLS-1$

	public static void initialize() {
	}
	
	private static ImageDescriptor createManaged(String prefix, String name) {
		return createManaged(imageRegistry, prefix, name);
	}
	
	private static ImageDescriptor createManaged(ImageRegistry registry, String prefix, String name) {
		ImageDescriptor result= ImageDescriptor.createFromURL(makeIconFileURL(prefix, name.substring(NAME_PREFIX_LENGTH)));
		registry.put(name, result);
		return result;
	}
	
	public static Image get(String key) {
		return imageRegistry.get(key);
	}

	
	private static URL makeIconFileURL(String prefix, String name) {
		StringBuffer buffer= new StringBuffer(prefix);
		buffer.append(name);
		try {
			return new URL(fgIconBaseURL, buffer.toString());
		} catch (MalformedURLException e) {
			return null;
		}
	}
	
	/**
	 * Helper method to access the image registry from the JavaPlugin class.
	 */
	static ImageRegistry getImageRegistry() {
		return imageRegistry;
	}
}
