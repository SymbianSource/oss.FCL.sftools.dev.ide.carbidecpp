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
package com.nokia.carbide.cpp.internal.builder.utils.ui;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;

import com.nokia.carbide.cpp.internal.builder.utils.Activator;


public class PreprocessPreferencePage extends PreferencePage implements IWorkbenchPreferencePage { 
	
	private Button outputToConsoleButton;
	private Button outputToEditorButton;
	private Text argsText;
	

	public PreprocessPreferencePage() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		Composite content = new Composite(parent, SWT.NONE);
		content.setLayout(new GridLayout(2, false));
		content.setLayoutData(new GridData());
		setControl(content);

		outputToConsoleButton = new Button(content, SWT.RADIO);
		outputToConsoleButton.setText(Messages.PreprocessPreferencePage_OutputToConsole);
		outputToConsoleButton.setToolTipText(Messages.PreprocessPreferencePage_OutputToConsoleToolTip);
		outputToConsoleButton.setSelection(outputToConsole());
		outputToConsoleButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		outputToEditorButton = new Button(content, SWT.RADIO);
		outputToEditorButton.setText(Messages.PreprocessPreferencePage_OutputToFile);
		outputToEditorButton.setToolTipText(Messages.PreprocessPreferencePage_OutputToFileToolTip);
		outputToEditorButton.setSelection(!outputToConsole());
		outputToEditorButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		Label argsLabel = new Label(content, SWT.NONE);
		argsLabel.setText(Messages.PreprocessPreferencePage_CppArguments);
		argsLabel.setToolTipText(Messages.PreprocessPreferencePage_CppArgumentsToolTip);

		argsText = new Text(content, SWT.BORDER);
		argsText.setToolTipText(Messages.PreprocessPreferencePage_CppArgumentsToolTip);
		argsText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		argsText.setText(getCppArguments());
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(super.getControl(), CarbideBuilerUtilsHelpIds.CARBIDE_BUILDER_PREPROCESS_PREF);
	}
	
	@Override
	protected Control createContents(Composite parent) {
		return null;
	}

	public void init(IWorkbench workbench) {
	}
	
	
	public boolean performOk() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setValue(BuilderUtilsPreferenceConstants.PREF_OUTPUT_TO_CONSOLE, outputToConsoleButton.getSelection());
		store.setValue(BuilderUtilsPreferenceConstants.PREF_CPP_ARGUMENTS, argsText.getText());

		return super.performOk();
	}

	public static boolean outputToConsole() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		return store.getBoolean(BuilderUtilsPreferenceConstants.PREF_OUTPUT_TO_CONSOLE);
	}

	public static String getCppArguments() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		return store.getString(BuilderUtilsPreferenceConstants.PREF_CPP_ARGUMENTS);
	}
}
