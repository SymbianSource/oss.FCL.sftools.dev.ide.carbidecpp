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
package com.nokia.cdt.internal.debug.launch.ui;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.cdt.ui.CUIPlugin;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;

import com.nokia.cdt.internal.debug.launch.LaunchPlugin;

public class LaunchImages {
	private static final String NAME_PREFIX= LaunchPlugin.PLUGIN_ID + '.';
	private static final int NAME_PREFIX_LENGTH= NAME_PREFIX.length();
	
	// The plugin registry
	private static ImageRegistry imageRegistry = new ImageRegistry();

	// Subdirectory (under the package containing this class) where 16 color images are
	private static URL fgIconBaseURL;
	static {
		fgIconBaseURL= Platform.getBundle(LaunchPlugin.getUniqueIdentifier()).getEntry("/icons/"); //$NON-NLS-1$
	}	

	private static final String T_TABS = "Tabs/"; //$NON-NLS-1$
	
	public static String IMG_VIEW_MAIN_TAB = NAME_PREFIX + "main_tab.gif"; //$NON-NLS-1$
	public static String IMG_VIEW_ARGUMENTS_TAB = NAME_PREFIX + "arguments_tab.gif"; //$NON-NLS-1$
	public static String IMG_VIEW_ENVIRONMENT_TAB = NAME_PREFIX + "environment_tab.gif"; //$NON-NLS-1$
	public static String IMG_VIEW_DEBUGGER_TAB = NAME_PREFIX + "debugger_tab.gif"; //$NON-NLS-1$
	public static String IMG_VIEW_EXCEPTIONS_TAB = NAME_PREFIX + "x86_exceptions_16.png"; //$NON-NLS-1$
	public static String IMG_VIEW_SOURCE_TAB = NAME_PREFIX + "source_tab.gif"; //$NON-NLS-1$
	public static String IMG_VIEW_CONNECTION_TAB = NAME_PREFIX + "connection.png"; //$NON-NLS-1$
	public static String IMG_VIEW_FILE_TRANSFER_TAB = NAME_PREFIX + "file_transfer.png"; //$NON-NLS-1$
	public static String IMG_VIEW_INSTALLATION_TAB = NAME_PREFIX + "installation.png"; //$NON-NLS-1$
	public static String IMG_ROM_IMAGE_TAB = NAME_PREFIX + "rom_image.png"; //$NON-NLS-1$
	public static String IMG_ROM_LOG_TAB = NAME_PREFIX + "rom_log.png"; //$NON-NLS-1$
	public static String IMG_EXECUTABLES_TAB = NAME_PREFIX + "executables.png"; //$NON-NLS-1$

	public static final ImageDescriptor DESC_TAB_MAIN= createManaged(T_TABS, IMG_VIEW_MAIN_TAB);
	public static final ImageDescriptor DESC_TAB_ARGUMENTS = createManaged(T_TABS, IMG_VIEW_ARGUMENTS_TAB);
	public static final ImageDescriptor DESC_TAB_ENVIRONMENT = createManaged(T_TABS, IMG_VIEW_ENVIRONMENT_TAB);
	public static final ImageDescriptor DESC_TAB_DEBUGGER = createManaged(T_TABS, IMG_VIEW_DEBUGGER_TAB);
	public static final ImageDescriptor DESC_TAB_EXCEPTIONS = createManaged(T_TABS, IMG_VIEW_EXCEPTIONS_TAB);
	public static final ImageDescriptor DESC_TAB_SOURCE = createManaged(T_TABS, IMG_VIEW_SOURCE_TAB);
	public static final ImageDescriptor DESC_TAB_CONNECTION = createManaged(T_TABS, IMG_VIEW_CONNECTION_TAB);
	public static final ImageDescriptor DESC_TAB_FILE_TRANSFER = createManaged(T_TABS, IMG_VIEW_FILE_TRANSFER_TAB);
	public static final ImageDescriptor DESC_TAB_INSTALLATION = createManaged(T_TABS, IMG_VIEW_INSTALLATION_TAB);
	public static final ImageDescriptor DESC_TAB_ROM_IMAGE = createManaged(T_TABS, IMG_ROM_IMAGE_TAB);
	public static final ImageDescriptor DESC_TAB_ROM_LOG = createManaged(T_TABS, IMG_ROM_LOG_TAB);
	public static final ImageDescriptor DESC_TAB_EXECUTABLES = createManaged(T_TABS, IMG_EXECUTABLES_TAB);

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
			CUIPlugin.getDefault().log(e);
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
