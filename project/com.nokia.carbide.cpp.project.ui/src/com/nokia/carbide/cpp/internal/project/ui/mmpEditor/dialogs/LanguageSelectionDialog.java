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
package com.nokia.carbide.cpp.internal.project.ui.mmpEditor.dialogs;

import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ListSelectionDialog;

import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.HelpContexts;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

public class LanguageSelectionDialog extends ListSelectionDialog {

	public LanguageSelectionDialog(Shell parentShell, Object input,
			IStructuredContentProvider contentProvider,
			ILabelProvider labelProvider, String message) {
		super(parentShell, input, contentProvider, labelProvider, message);
	}

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(Messages.getString("LanguageSelectionDialog.dialogTitle")); //$NON-NLS-1$
		WorkbenchUtils.setHelpContextId(shell, HelpContexts.LANGUAGES_DIALOG);
	}

	public CheckboxTableViewer getTableViewer() {
		return getViewer();
	}

}
