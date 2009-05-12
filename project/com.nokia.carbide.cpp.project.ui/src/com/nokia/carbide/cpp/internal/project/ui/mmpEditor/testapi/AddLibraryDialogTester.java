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

package com.nokia.carbide.cpp.internal.project.ui.mmpEditor.testapi;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Text;

import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.dialogs.AddLibraryDialog;

/**
 * An interface for testing the Add Library dialog of the MMP Editor
 */
public class AddLibraryDialogTester {

	/**
	 * Retrieves the "Library name" text widget of the Add Library dialog.
	 * @param dialog - Add Library dialog provided by caller
	 * @return text widget if success, null otherwise
	 */
	public static Text getLibraryText(Dialog dialog) {
		if (dialog != null && dialog instanceof AddLibraryDialog) {
			AddLibraryDialog aDialog = (AddLibraryDialog) dialog;
			return aDialog.getLibraryText();
		}
		return null;
	}

	/**
	 * Retrieves the library table viewer of the Add Library dialog.
	 * @param dialog - Add Library dialog provided by caller
	 * @return table viewer if success, null otherwise
	 */
	public static TableViewer getLibraryViewer(Dialog dialog) {
		if (dialog != null && dialog instanceof AddLibraryDialog) {
			AddLibraryDialog aDialog = (AddLibraryDialog) dialog;
			return aDialog.getLibraryViewer();
		}
		return null;
	}

}
