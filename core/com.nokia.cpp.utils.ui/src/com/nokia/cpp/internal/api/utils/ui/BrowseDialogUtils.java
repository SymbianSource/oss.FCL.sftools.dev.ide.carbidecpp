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

package com.nokia.cpp.internal.api.utils.ui;

import java.io.File;

import org.eclipse.core.runtime.IPath;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Text;

/**
 * Utilities to make it easier to use file dialogs.
 */
public class BrowseDialogUtils {
	/**
	 * When issuing a "Browse..." command next to a text entry field, initialize
	 * the dialog with the existing path in that text field.
	 * @param dialog
	 * @param textEntry
	 */
	public static void initializeFrom(FileDialog dialog, Text textEntry) {
		if (textEntry == null)
			return;
		String existing = textEntry.getText();
		initializeFrom(dialog, existing);
	}

	/**
	 * When issuing a "Browse..." command with an expected path, initialize
	 * the dialog with the existing path.
	 * @param dialog
	 * @param path
	 */
	public static void initializeFrom(FileDialog dialog, IPath path) {
		if (path != null) {
			initializeFrom(dialog, path.toOSString());
		}
	}
	
	/**
	 * When issuing a "Browse..." command with an expected path, initialize
	 * the dialog with the existing path.
	 * @param dialog
	 * @param path
	 */
	public static void initializeFrom(FileDialog dialog, String path) {
		if (path != null && path.length() > 0) {
			File file = new File(path);
			if (file.exists()) {
				if (file.isAbsolute()) {
					dialog.setFilterPath(file.getParent());
				}
				dialog.setFileName(file.getName());
			} else {
				if (!file.isAbsolute())
					return;
				File dir = file.getParentFile();
				while (dir != null && !dir.exists()) {
					dir = dir.getParentFile();
				}
				if (dir != null) {
					dialog.setFilterPath(dir.getAbsolutePath());
				}
			}
		}
	}
	
	/**
	 * When issuing a "Browse..." command next to a text entry field, initialize
	 * the dialog with the existing path in that text field.
	 * @param dialog
	 * @param textEntry
	 */
	public static void initializeFrom(DirectoryDialog dialog, Text textEntry) {
		if (textEntry == null)
			return;
		String existing = textEntry.getText();
		initializeFrom(dialog, existing);
	}

	/**
	 * When issuing a "Browse..." command with an expected path, initialize
	 * the dialog with the existing path.
	 * @param dialog
	 * @param path
	 */
	public static void initializeFrom(DirectoryDialog dialog, IPath path) {
		if (path != null) {
			initializeFrom(dialog, path.toOSString());
		}
	}
	
	/**
	 * When issuing a "Browse..." command with an expected path, initialize
	 * the dialog with the existing path.
	 * @param dialog
	 * @param path
	 */
	public static void initializeFrom(DirectoryDialog dialog, String path) {
		if (path != null && path.length() > 0) {
			File file = new File(path);
			if (!file.isAbsolute())
				return;
			while (file != null && !file.exists()) {
				file = file.getParentFile();
			}
			if (file != null) {
				dialog.setFilterPath(file.getAbsolutePath());
			}
		}
	}
}
