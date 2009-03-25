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

package com.nokia.carbide.cpp.internal.codescanner.markers;

import java.net.URL;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.nokia.carbide.cpp.internal.codescanner.Messages;

/**
 * A class to handle the "CodeScanner Marker Details" dialog.
 */
public class CSMarkerDetailsDialog extends MessageDialog {

	private String reference;

	/**
	 * Constructor.
	 * @param parentShell - the parent shell
	 * @param dialogTitle - the dialog title, or <code>null</code> if none
	 * @param dialogMessage the dialog title image, or <code>null</code> if none
	 * @param reference - external reference, or <code>null</code> if none
	 */
	public CSMarkerDetailsDialog (Shell parentShell,
								  String dialogTitle,
								  String dialogMessage,
								  String reference) {
		super(parentShell, dialogTitle, null, dialogMessage, 
			  INFORMATION, new String[] { IDialogConstants.OK_LABEL}, 0);
		this.reference = reference;
	}

	/**
	 * Add a button for external reference if needed.
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		super.createButtonsForButtonBar(parent);
		if (reference != null) {
			Button button = createButton(parent, getButtonLabels().length, Messages.getString("MarkerDetails.ReferenceLabel"), false);
			button.setToolTipText(reference);
			button.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) {
					try {
						URL url = new URL(reference);
						PlatformUI.getWorkbench().getBrowserSupport().getExternalBrowser().openURL(url);
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

}
