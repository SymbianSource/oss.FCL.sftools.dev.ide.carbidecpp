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

import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.dialogs.CapabilitiesDialog;

/**
 * An interface for testing the Choose Capabilities dialog of the MMP Editor
 */
public class CapabilitiesDialogTester {

	/**
	 * Retrieves the capabilities table viewer of the Choose Capabilities dialog.
	 * @param dialog - Choose Capabilities dialog provided by caller
	 * @return table viewer if success, null otherwise
	 */
	public static TableViewer getCapabilitiesViewer(Dialog dialog) {
		if (dialog != null && dialog instanceof CapabilitiesDialog) {
			CapabilitiesDialog cDialog = (CapabilitiesDialog) dialog;
			return cDialog.getCapabilitiesViewer();
		}
		return null;
	}

}
