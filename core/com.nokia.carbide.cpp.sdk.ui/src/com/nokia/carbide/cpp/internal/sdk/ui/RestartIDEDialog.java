/*
* Copyright (c) 2006-2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.cpp.internal.sdk.ui;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

/**
 * A dialog which prompts the user to restart after installing a new feature/plug-in, etc.
 * and provides Yes, No (Restart/Later) buttons. 
 */
public class RestartIDEDialog extends MessageDialog {
	private final static String[] yesNo = new String[] {Messages.getString("RestartIDEDialog.Restart"), Messages.getString("RestartIDEDialog.Later")}; //$NON-NLS-1$ //$NON-NLS-2$
	
	public static final int RESTART_YES = 0;
	public static final int RESTART_NO = 1;
	
	 /** Create a restart dialog. Note that the dialog will have no visual
     * representation (no widgets) until it is told to open.
     * <p>
     * The labels of the buttons to appear in the button bar are supplied in
     * this constructor as an array. The <code>open</code> method will return
     * the index of the label in this array corresponding to the button that was
     * pressed to close the dialog. If the dialog was dismissed without pressing
     * a button (ESC, etc.) then -1 is returned. Note that the <code>open</code>
     * method blocks.
     * </p>
	 * @param parent
	 * @param title
	 * @param message
	 */
	public RestartIDEDialog(Shell parent, String title, String message) {
		super(parent, title, null, message, QUESTION, yesNo, 0);
	}

	/**
	 * Creates and opens a restart dialog.
	 * @param parent 
	 * @param msg message to display on to the dialog.
	 * @return returns status of the restart dialog.
	 */
	public static int show(Shell parent, String msg) {
		RestartIDEDialog dialog = new RestartIDEDialog(parent, Messages.getString("RestartIDEDialog.New_Plugin_Installed"), msg); //$NON-NLS-1$
		int button= dialog.open();
		switch (button) {
			case 0:
				PlatformUI.getWorkbench().restart();
				break;
			case 1:
				
				// Later, do nothing.
				break;
		}
		return button;
	}
}
