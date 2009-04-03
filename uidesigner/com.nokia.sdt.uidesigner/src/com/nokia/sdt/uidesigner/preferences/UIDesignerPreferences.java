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
package com.nokia.sdt.uidesigner.preferences;

import com.nokia.sdt.preferences.PreferenceConstants;
import com.nokia.sdt.uidesigner.ui.UIDesignerPlugin;
import com.nokia.sdt.uidesigner.ui.utils.Messages;
import com.nokia.sdt.uimodel.UIModelPlugin;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.*;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By 
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to 
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */

public class UIDesignerPreferences
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

	private static final String HELP_CONTEXT_ID = 
		UIDesignerPlugin.PLUGIN_ID + "." + "uiDesignerPreferencesContext"; //$NON-NLS-1$
	
	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		WorkbenchUtils.setHelpContextId(getControl(), HELP_CONTEXT_ID);
	}

	public UIDesignerPreferences() {
		super(GRID);
		setPreferenceStore(UIModelPlugin.getDefault().getPreferenceStore());
	//	setDescription("A demonstration of a preference page implementation");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		addField(new RadioGroupFieldEditor(
				PreferenceConstants.P_PROMPT_BEFORE_EVENT_SAVE,
				Messages.getString("UIDesignerPreferences.0"), //$NON-NLS-1$
			3,
			new String[][] { 
					{ Messages.getString("UIDesignerPreferences.PromptLabel"), MessageDialogWithToggle.PROMPT }, //$NON-NLS-1$
					{ Messages.getString("UIDesignerPreferences.AlwaysLabel"), MessageDialogWithToggle.ALWAYS }, //$NON-NLS-1$
					{ Messages.getString("UIDesignerPreferences.NeverLabel"), MessageDialogWithToggle.NEVER } }, //$NON-NLS-1$
				getFieldEditorParent(),
				true));
		addField(new RadioGroupFieldEditor(
				PreferenceConstants.P_DOUBLE_CLICK_BEHAVIOR,
				Messages.getString("UIDesignerPreferences.DoubleClickPrefPrompt"), //$NON-NLS-1$
			2,
			new String[][] {
					{ Messages.getString("UIDesignerPreferences.EditLabelPrefValueLabel"),  //$NON-NLS-1$
						PreferenceConstants.EDIT_LABEL },
					{ Messages.getString("UIDesignerPreferences.HandleEventPrefValueLabel"),  //$NON-NLS-1$
							PreferenceConstants.HANDLE_EVENT } },
			getFieldEditorParent(), true));
		addField(new BooleanFieldEditor(
				PreferenceConstants.P_PROMPT_BEFORE_UNDOING_SOURCE_SYNC,
				Messages.getString("UIDesignerPreferences.UndoSourceSyncLabel"), //$NON-NLS-1$
				getFieldEditorParent()));
		
		FileFieldEditor fileFieldEditor = new FileFieldEditor(
						PreferenceConstants.P_FILE_HEADER_TEMPLATE_LOCATION,
						Messages.getString("UIDesignerPreferences.FileHeaderTemplateLocationLabel"), //$NON-NLS-1$
						getFieldEditorParent());
		addField(fileFieldEditor);
		Text textControl = fileFieldEditor.getTextControl(getFieldEditorParent());
		GridData data = (GridData) textControl.getLayoutData();
		data.widthHint = 200;
		textControl.setToolTipText(Messages.getString("UIDesignerPreferences.FileHeaderTemplateLocationTip")); //$NON-NLS-1$
		
	/*	
		addField(new DirectoryFieldEditor(PreferenceConstants.P_PATH, 
				"&Directory preference:", getFieldEditorParent()));
		addField(
			new BooleanFieldEditor(
				PreferenceConstants.P_BOOLEAN,
				"&An example of a boolean preference",
				getFieldEditorParent()));

		addField(new RadioGroupFieldEditor(
				PreferenceConstants.P_CHOICE,
			"An example of a multiple-choice preference",
			1,
			new String[][] { { "&Choice 1", "choice1" }, {
				"C&hoice 2", "choice2" }
		}, getFieldEditorParent()));
		addField(
			new StringFieldEditor(PreferenceConstants.P_STRING, "A &text preference:", getFieldEditorParent()));
	*/
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
	
}