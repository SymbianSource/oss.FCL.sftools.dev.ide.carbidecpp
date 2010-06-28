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
 *				Deniz TURAN
 * Description: 
 * 				
 */
package com.nokia.carbide.cpp.sysdoc.internal.hover.uitlis;

import org.eclipse.jface.dialogs.MessageDialog;

import com.nokia.carbide.cpp.sysdoc.hover.Activator;
import com.nokia.carbide.cpp.sysdoc.internal.hover.core.HoverManager;

/**
 * An helper class to display dialog window to show error messages
 */
public class DialogHelper {

	public static void displayErrorDialog(final String msg) {
		displayErrorDialog(msg, "Developer Library Hover Help Error",
				MessageDialog.ERROR);
	}

	public static void displayErrorDialog(final String msg, final String title,
			final int icon) {

		Runnable mssageRunnable = new Runnable() {
			public void run() {
				MessageDialog message = new MessageDialog(Activator
						.getDefault().getWorkbench().getDisplay()
						.getActiveShell(), title, null, msg, icon,
						new String[] { "OK" }, 1);
				message.open();

			}
		};
		if (!HoverManager.isTestMode()) {
			ExecutorAgent.run(mssageRunnable);
		}
	}
}
