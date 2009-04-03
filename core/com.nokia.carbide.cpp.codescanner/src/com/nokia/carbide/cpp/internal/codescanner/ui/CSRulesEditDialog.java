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

package com.nokia.carbide.cpp.internal.codescanner.ui;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.nokia.carbide.cpp.internal.codescanner.Messages;
import com.nokia.carbide.cpp.internal.codescanner.config.CSCategory;
import com.nokia.carbide.cpp.internal.codescanner.config.CSScript;
import com.nokia.carbide.cpp.internal.codescanner.config.CSSeverity;

/**
 * A class to handle editing various attributes of a CodeScanner rule.
 */
public class CSRulesEditDialog extends TrayDialog {

	// private members of this dialog
	private String title;
	private CSCategory category;
	private CSSeverity severity;
	private String extraString;
	private String extraTitle;
	private boolean hasExtra;
	private Combo categoryCombo;
	private Combo severityCombo;
	private Text extraText;

	/**
	 * Create an instance of this dialog.
	 * @param parentShell
	 * @param title - a string containing the title of this dialog
	 * @param category - a string containing the initial category
	 * @param severity - a string containing the initial severity
	 * @param extraAttribute - a string containing the initial value of extra attribute
	 * @param attributeTitle - a string containing the title of the extra attribute
	 */
	public CSRulesEditDialog (Shell parentShell,
							  CSScript script,
							  CSCategory category,
							  CSSeverity severity,
							  boolean hasExtra,
							  String extraTitle,
							  String extraString) {
		super(parentShell);
		this.category = category;
		this.severity = severity;
		this.title = "Edit Rule \"" + script.toString() + "\"";
		this.hasExtra = hasExtra;
		if (hasExtra) {
			this.extraTitle = extraTitle;
			this.extraString = extraString;
		}
	}

	/**
	 * Return the updated rule category.
	 */
	public CSCategory getCategory() {
		return this.category;
	}

	 /**
	  * Return the updated rule severity.
	  */
	public CSSeverity getSeverity() {
		return this.severity;
	}

	/**
	 * Return the updated extra rule attribute.
	 */
	public String getExtra() {
		return this.extraString;
	}

	/**
	 * Does this rule have extra attribute?
	 * @return true if the rule has extra attribute
	 */
	public boolean hasExtra() {
		return this.hasExtra;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(title);
	}

	/**
	 * Create contents of this dialog.
	 * @param parent - a composite control which will be the parent of the new instance
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		container.setLayout(gridLayout);

		// create combo box for selecting rule category
		final Label categoryLabel = new Label(container, SWT.RIGHT);
		categoryLabel.setLayoutData(new GridData(110, SWT.DEFAULT));
		categoryLabel.setText(Messages.getString("RulesEdit.CategoryTitle"));
		categoryCombo = new Combo(container, SWT.DROP_DOWN);
		categoryCombo.setLayoutData(new GridData(150, SWT.DEFAULT));
		int selectionIndex = 0;
		for (CSCategory category : CSCategory.values()) {
			if (!category.equals(CSCategory.category_unknown)) {
				if (category.equals(this.category)) {
					selectionIndex = category.ordinal();
				}
				categoryCombo.add(category.toString());
			}
		}
		categoryCombo.select(selectionIndex);

		// create combo box for selecting rule severity
		final Label severityLabel = new Label(container, SWT.RIGHT);
		severityLabel.setLayoutData(new GridData(110, SWT.DEFAULT));
		severityLabel.setText(Messages.getString("RulesEdit.SeverityTitle"));
		severityCombo = new Combo(container, SWT.DROP_DOWN);
		severityCombo.setLayoutData(new GridData(150, SWT.DEFAULT));
		selectionIndex = 0;
		for (CSSeverity severity : CSSeverity.values()) {
			if (!severity.equals(CSSeverity.severity_unknown)) {
				if (severity.equals(this.severity)) {
					selectionIndex = severity.ordinal();
				}
				severityCombo.add(severity.toString());
			}
		}
		severityCombo.select(selectionIndex);

		// create text field for editing extra rule attribute
		if (this.hasExtra) {
			final Label extraLabel = new Label(container, SWT.RIGHT);
			extraLabel.setLayoutData(new GridData(110, SWT.DEFAULT));
			extraLabel.setText(this.extraTitle);
			extraText = new Text(container, SWT.BORDER | SWT.WRAP);
			extraText.setLayoutData(new GridData(150, SWT.DEFAULT));
			extraText.setText(this.extraString);			
		}
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, CSUIHelpIds.CODESCANNER_RULE_EDIT_DIALOG);
		return container;
	}

	/**
	 * Things to do when user hit the "OK" button.
	 */
	@Override
	protected void okPressed() {
		// store the selected rule category
		int selectionIndex = categoryCombo.getSelectionIndex();
		String categoryString = "category_" + categoryCombo.getItem(selectionIndex);
		this.category = CSCategory.toCategory(categoryString);

		// store the selected rule severity
		selectionIndex = severityCombo.getSelectionIndex();
		String severityString = "severity_" + severityCombo.getItem(selectionIndex);
		this.severity = CSSeverity.toSeverity(severityString);

		// store the updated extra rule attribute
		if (this.hasExtra) {
			this.extraString = this.extraText.getText();
		}
		
		super.okPressed();
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		if (this.hasExtra) {
			return new Point(320, 200);
		}
		else {
			return new Point(320, 140);
		}	
	}

}
