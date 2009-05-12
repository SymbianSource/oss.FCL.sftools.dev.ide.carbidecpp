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

import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.HelpContexts;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import java.util.HashMap;
import java.util.Map;

public class ToolOptionsDialog extends ValidatingDialog {

	private ComboViewer toolChainViewer;
	private Text optionsText;
	
	private String theToolChain;
	private String theOptions;
	private boolean isAdding; // either adding or editing
	private Map<String, String> currentOptions;
	// map of internal mmp tool chain id -> display text
	private Map<String, String> toolChains;
	private final String title;

	/**
	 * Create the dialog
	 * @param parentShell
	 */
	public ToolOptionsDialog(Shell parentShell, String title, boolean isAdding) {
		super(parentShell);
		this.title = title;
		this.isAdding = isAdding;
		toolChains = getToolChainIDs();
		setHelpAvailable(true);
	}
	
	public void setInitialValues(String toolChain, String options) {
		theToolChain = toolChain;
		theOptions = options;
	}
	
	/**
	 * The dialog must be initialized with the current map of 
	 * tool options before it is opened. This map is not updated
	 * by this dialog. Rather, it is used to handle the case
	 * where the user tries to add options for a tool chain
	 * which already has options. The user is alerted and offered
	 * the choice of morphing the add operation to an edit.
	 * @param currentOptions existing options map from the mmp view
	 */
	public void setCurrentOptions(Map<String, String> currentOptions) {
		this.currentOptions = currentOptions;
	}
	
	/**
	 * Get the user's selected tool chain. May be one for which options
	 * already exist.
	 * @return
	 */
	public String getToolChain() {
		return theToolChain;
	}
	
	/**
	 * Returns the uer-entered options string
	 */
	public String getOptions() {
		return theOptions;
	}
	
	Map<String, String> getToolChainIDs() {
		Map<String, String> result = new HashMap<String, String>();
		result.put("CW", Messages.getString("ToolOptionsDialog.CW_TOOL")); //$NON-NLS-1$ //$NON-NLS-2$
		result.put("ARMCC", Messages.getString("ToolOptionsDialog.ARMCC_TOOL")); //$NON-NLS-1$ //$NON-NLS-2$
		result.put("GCC", Messages.getString("ToolOptionsDialog.GCC_TOOL")); //$NON-NLS-1$ //$NON-NLS-2$
		result.put("GCCE", Messages.getString("ToolOptionsDialog.GCCE_TOOL")); //$NON-NLS-1$ //$NON-NLS-2$
		result.put("MSVC", Messages.getString("ToolOptionsDialog.MSVC_TOOL")); //$NON-NLS-1$ //$NON-NLS-2$
		return result;
	}
	
	public IStatus validate() {
		IStatus result = Status.OK_STATUS;
		if (optionsText.getText().length() == 0) {
			result = makeStatus(IStatus.ERROR, Messages.getString("ToolOptionsDialog.optionsTextValidationErr")); //$NON-NLS-1$
		}
		return result;
	}

	@Override
	protected void captureResults() {
		theToolChain = getSelectedToolChain();
		theOptions = optionsText.getText();
	}
	
	String getSelectedToolChain() {
		IStructuredSelection ss = (IStructuredSelection) toolChainViewer.getSelection();
		Object element = ss.getFirstElement();
		return element != null? element.toString() : null;
	}
	
	class ToolChainLabelProvider extends LabelProvider {
		public String getText(Object element) {
			return toolChains.get(element.toString());
		}
	}
	
	/**
	 * Create contents of the dialog
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new FormLayout());

		final Label selectTheToolLabel = new Label(container, SWT.NONE);
		final FormData formData_1 = new FormData();
		formData_1.right = new FormAttachment(100, -5);
		formData_1.top = new FormAttachment(0, 10);
		formData_1.left = new FormAttachment(0, 5);
		selectTheToolLabel.setLayoutData(formData_1);
		if (isAdding) {
			selectTheToolLabel.setText(Messages.getString("ToolOptionsDialog.addPromptLabel")); //$NON-NLS-1$
		} else {
			selectTheToolLabel.setText(Messages.getString("ToolOptionsDialog.editPromptLabel")); //$NON-NLS-1$
		}

		final Label toolChainLabel = new Label(container, SWT.NONE);
		final FormData formData = new FormData();
		formData.bottom = new FormAttachment(0, 63);
		formData.top = new FormAttachment(0, 50);
		formData.right = new FormAttachment(selectTheToolLabel, 60, SWT.LEFT);
		formData.left = new FormAttachment(selectTheToolLabel, 0, SWT.LEFT);
		toolChainLabel.setLayoutData(formData);
		toolChainLabel.setText(Messages.getString("ToolOptionsDialog.toolChainLabel")); //$NON-NLS-1$

		toolChainViewer = new ComboViewer(container, SWT.READ_ONLY);
		Combo combo = toolChainViewer.getCombo();
		final FormData formData_2 = new FormData();
		formData_2.bottom = new FormAttachment(0, 66);
		formData_2.top = new FormAttachment(0, 45);
		formData_2.right = new FormAttachment(0, 195);
		formData_2.left = new FormAttachment(0, 65);
		combo.setLayoutData(formData_2);
		toolChainViewer.setLabelProvider(new ToolChainLabelProvider());
		toolChainViewer.setContentProvider(new ArrayContentProvider());
		toolChainViewer.setSorter(new ViewerSorter());
		toolChainViewer.setInput(toolChains.keySet().toArray());
		if (theToolChain != null) {
			toolChainViewer.setSelection(new StructuredSelection(theToolChain));
		}
		if (isAdding) {
			selectFirstAvailableToolChain();
			toolChainViewer.addSelectionChangedListener(new ISelectionChangedListener() {
				public void selectionChanged(SelectionChangedEvent event) {
					toolChainSelectionChanged();
				}
			});
		}
		else {
			// if we're editing an existing value don't allow changing the tool chain
			// this avoids asking the user how to handle the case when the options field
			// has text and the user switches to another tool chain that already has text.
			toolChainViewer.getControl().setEnabled(false);
		}
	
		final Label optionsLabel = new Label(container, SWT.NONE);
		final FormData formData_3 = new FormData();
		formData_3.left = new FormAttachment(0, 5);
		formData_3.top = new FormAttachment(toolChainLabel, 24, SWT.DEFAULT);
		formData_3.bottom = new FormAttachment(0, 100);
		optionsLabel.setLayoutData(formData_3);
		optionsLabel.setText(Messages.getString("ToolOptionsDialog.optionsTextLabel")); //$NON-NLS-1$

		optionsText = new Text(container, SWT.BORDER);
		final FormData formData_4 = new FormData();
		formData_4.right = new FormAttachment(selectTheToolLabel, 0, SWT.RIGHT);
		formData_4.top = new FormAttachment(optionsLabel, 0, SWT.TOP);
		formData_4.left = new FormAttachment(combo, 0, SWT.LEFT);
		optionsText.setLayoutData(formData_4);
		if (theOptions != null) {
			optionsText.setText(theOptions);
		}
		return container;
	}

	/**
	 * Handles the case where the user selects a tool chain
	 * that already has an options setting, allowing the user
	 * to edit the existing value if desired.
	 */
	protected void toolChainSelectionChanged() {
		String toolChain = getSelectedToolChain();
		if (toolChain != null) {
			String currValue = currentOptions.get(toolChain);
			if (currValue != null) {
				boolean dlgResult = MessageDialog.openQuestion(getShell(), Messages.getString("ToolOptionsDialog.editExistingSettingDialogTitle"),  //$NON-NLS-1$
					Messages.getString("ToolOptionsDialog.editExistingSettingPrompt")); //$NON-NLS-1$
				if (dlgResult) {
					optionsText.setText(currValue);
				} else {
					selectFirstAvailableToolChain();
				}
			}
		}
	}
	
	void selectFirstAvailableToolChain() {
		for (String toolChainID : toolChains.keySet()) {
			if (!currentOptions.containsKey(toolChainID)) {
				toolChainViewer.setSelection(new StructuredSelection(toolChainID));
				break;
			}
		}
	}

	/**
	 * Create contents of the button bar
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(500, 197);
	}

	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(title);
		WorkbenchUtils.setHelpContextId(newShell, HelpContexts.TOOL_OPTIONS_DIALOG);
	}

	public Combo getToolChainCombo() {
		return toolChainViewer.getCombo();
	}

	public Text getOptionsText() {
		return optionsText;
	}

}
