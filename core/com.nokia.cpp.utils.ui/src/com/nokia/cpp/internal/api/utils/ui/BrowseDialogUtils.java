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
 * Utilities to make it easier to use file and directory dialogs which are associated
 * with text entry fields.
 * <p>
 * First, promote UI where Browse... starts from the current textual entry rather than
 * some random place.
 * <p>
 * Second, overcome terrible SWT behavior:  even if you do set the filter path, then if it 
 * is not 100% correct, it will again revert to the home directory.  So, find the nearest
 * directory that <i>does</i> exist.
 */
public class BrowseDialogUtils {
	/**
	 * When issuing a "Browse..." command next to a text entry field, initialize
	 * the dialog with the nearest existing file or directory in that text field.
	 * @param dialog
	 * @param textEntry
	 * @pathm defaultPath the path to use when the text entry is empty 
	 */
	public static void initializeFrom(FileDialog dialog, Text textEntry, IPath defaultPath) {
		if (textEntry != null) {
			String existing = textEntry.getText().trim();
			if (existing.length() > 0) {
				initializeFrom(dialog, existing);
				return;
			}
		}
		if (defaultPath != null) {
			initializeFrom(dialog, defaultPath);
		}
	}

	/**
	 * When issuing a "Browse..." command next to a text entry field, initialize
	 * the dialog with the nearest existing file or directory in that text field.
	 * @param dialog
	 * @param textEntry
	 */
	public static void initializeFrom(FileDialog dialog, Text textEntry) {
		if (textEntry == null)
			return;
		String existing = textEntry.getText().trim();
		initializeFrom(dialog, existing);
	}

	/**
	 * When issuing a "Browse..." command with an expected file, initialize
	 * the dialog with the nearest existing file or directory.
	 * @param dialog
	 * @param path
	 */
	public static void initializeFrom(FileDialog dialog, IPath path) {
		if (path != null) {
			initializeFrom(dialog, path.toOSString());
		}
	}
	
	/**
	 * When issuing a "Browse..." command with an expected file, initialize
	 * the dialog with the nearest existing file or directory.
	 * @param dialog
	 * @param path
	 */
	public static void initializeFrom(FileDialog dialog, String path) {
		if (path != null && path.length() > 0) {
			boolean isDirectory = path.endsWith("/") || path.endsWith("\\");
			File file = new File(path);
			boolean exists = file.exists();
			if (exists) {
				isDirectory = file.isDirectory();
			}
			if (!isDirectory) {
				dialog.setFileName(file.getName());
			}
			if (exists) {
				if (file.isAbsolute()) {
					dialog.setFilterPath(isDirectory ? file.getAbsolutePath() : file.getParent());
				}
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
	 * the dialog with the nearest existing directory in that text field.
	 * @param dialog
	 * @param textEntry
	 * @param defaultPath the default path if the text entry is empty
	 */
	public static void initializeFrom(DirectoryDialog dialog, Text textEntry, IPath defaultPath) {
		if (textEntry != null) {
			String existing = textEntry.getText().trim();
			if (existing.length() > 0) {
				initializeFrom(dialog, existing);
				return;
			}
		}
		if (defaultPath != null) {
			initializeFrom(dialog, defaultPath);
		}
	}

	/**
	 * When issuing a "Browse..." command next to a text entry field, initialize
	 * the dialog with the nearest existing directory in that text field.
	 * @param dialog
	 * @param textEntry
	 */
	public static void initializeFrom(DirectoryDialog dialog, Text textEntry) {
		if (textEntry == null)
			return;
		String existing = textEntry.getText().trim();
		initializeFrom(dialog, existing);
	}

	/**
	 * When issuing a "Browse..." command with an expected directory, initialize
	 * the dialog with the nearest existing path.
	 * @param dialog
	 * @param path
	 */
	public static void initializeFrom(DirectoryDialog dialog, IPath path) {
		if (path != null) {
			initializeFrom(dialog, path.toOSString());
		}
	}
	
	/**
	 * When issuing a "Browse..." command with an expected directory, initialize
	 * the dialog with the nearest existing path.
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
