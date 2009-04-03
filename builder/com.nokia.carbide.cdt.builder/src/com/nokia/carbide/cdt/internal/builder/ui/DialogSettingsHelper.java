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
package com.nokia.carbide.cdt.internal.builder.ui;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;

/**
 * Helper class for dealing with setting and restoring dialog settings.
 */
public class DialogSettingsHelper {
	
	/**
	 * Common dialog settings
	 */
	public static final String DIALOG_ORIGIN_X = CarbideBuilderPlugin.PLUGIN_ID + ".DIALOG_ORIGIN_X"; //$NON-NLS-1$
	public static final String DIALOG_ORIGIN_Y = CarbideBuilderPlugin.PLUGIN_ID + ".DIALOG_ORIGIN_Y"; //$NON-NLS-1$
	public static final String DIALOG_WIDTH = CarbideBuilderPlugin.PLUGIN_ID + ".DIALOG_WIDTH"; //$NON-NLS-1$
	public static final String DIALOG_HEIGHT = CarbideBuilderPlugin.PLUGIN_ID + ".DIALOG_HEIGHT"; //$NON-NLS-1$
	public static final String DIALOG_SASH_WEIGHTS_1 = CarbideBuilderPlugin.PLUGIN_ID + ".DIALOG_SASH_WEIGHTS_1"; //$NON-NLS-1$
	public static final String DIALOG_SASH_WEIGHTS_2 = CarbideBuilderPlugin.PLUGIN_ID + ".DIALOG_SASH_WEIGHTS_2"; //$NON-NLS-1$

	/**
	 * Persists the location and dimensions of the shell in the
	 * Debug UI Plugin dialog settings under the provided dialog settings section name
	 * 
	 * @param shell The shell whose geometry is to be stored
	 * @param dialogSettingsSectionName The name of the dialog settings section
	 */
	public static void persistShellGeometry(Shell shell, String dialogSettingsSectionName) {
		Point shellLocation = shell.getLocation();
		Point shellSize = shell.getSize();
		IDialogSettings settings = getDialogSettings(dialogSettingsSectionName);
		settings.put(DIALOG_ORIGIN_X, shellLocation.x);
		settings.put(DIALOG_ORIGIN_Y, shellLocation.y);
		settings.put(DIALOG_WIDTH, shellSize.x);
		settings.put(DIALOG_HEIGHT, shellSize.y);
	}
	
	private static IDialogSettings getDialogSettings(String dialogSettingsSectionName) {
		IDialogSettings settings = CarbideBuilderPlugin.getDefault().getDialogSettings();
		IDialogSettings section = settings.getSection(dialogSettingsSectionName);
		if (section == null) {
			section = settings.addNewSection(dialogSettingsSectionName);
		} 
		return section;
	}
	
	/**
	 * Returns the initial size which is the larger of the <code>initialSize</code> or
	 * the size persisted in the Debug UI Plugin dialog settings under the provided dialog setttings section name.
	 * If no size is persisted in the settings, the <code>initialSize</code> is returned. 
	 * 
	 * @param initialSize The initialSize to compare against
	 * @param dialogSettingsSectionName The name of the dialog settings section
	 * @return the initial size
	 */
	public static Point getInitialSize(String dialogSettingsSectionName, Point initialSize) {
		IDialogSettings settings = getDialogSettings(dialogSettingsSectionName);
		try {
			int x, y;
			x = settings.getInt(DIALOG_WIDTH);
			y = settings.getInt(DIALOG_HEIGHT);
			return new Point(Math.max(x, initialSize.x), Math.max(y, initialSize.y));
		} catch (NumberFormatException e) {
		}
		return initialSize;
	}
	
	/**
	 * Returns the initial location which is persisted in the Debug UI Plugin dialog settings
	 * under the provided dialog setttings section name.
	 * If location is not persisted in the settings, the <code>null</code> is returned. 
	 * 
	 * @param dialogSettingsSectionName The name of the dialog settings section
	 * @return The initial location or <code>null</code>
	 */
	public static Point getInitialLocation(String dialogSettingsSectionName) {
		IDialogSettings settings = getDialogSettings(dialogSettingsSectionName);
		try {
			int x= settings.getInt(DIALOG_ORIGIN_X);
			int y= settings.getInt(DIALOG_ORIGIN_Y);
			return new Point(x,y);
		} catch (NumberFormatException e) {
		}
		return null;
	}
}
