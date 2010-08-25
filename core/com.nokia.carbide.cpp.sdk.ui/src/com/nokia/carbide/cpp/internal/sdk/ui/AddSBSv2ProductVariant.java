/*
* Copyright (c) 2007-2010 Nokia Corporation and/or its subsidiary(-ies).
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

/**
 * Dialog that allows the user to add a new SBSv2 custom variant configuration
 */
public class AddSBSv2ProductVariant extends TrayDialog {

	private CCombo aliasCombo;
	private CCombo variantCombo;
	private String newConfigString;

	private List<String> aliasList = new ArrayList<String>();
	private List<String> variantList = new ArrayList<String>();
	private String defaultAlias;
	
	/**
	 * Create the dialog
	 * @param parentShell
	 * @param selectedAlias 
	 */
	public AddSBSv2ProductVariant(Shell parentShell, String selectedAlias, HashMap<String, String> aliasMap, 
										List<String> productVariantList) {
		
		super(parentShell);
		for (String alias : aliasMap.keySet()){
			aliasList.add(alias);
		}
		variantList = productVariantList;
		defaultAlias = selectedAlias;
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
		
		getButton(IDialogConstants.OK_ID).setEnabled(false);
	}

	/**
	 * Create contents of the dialog
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		
		Composite container = (Composite) super.createDialogArea(parent);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		container.setLayout(gridLayout);

		final Label aliasLabel = new Label(container, SWT.NONE);
		aliasLabel.setToolTipText("Select a build alias."); 
		aliasLabel.setText("Select a build alias: "); //$NON-NLS-1$

		aliasCombo = new CCombo(container, SWT.BORDER);
		aliasCombo.setLayoutData(new GridData(263, SWT.DEFAULT));
		Collections.sort(aliasList);
		aliasCombo.setItems((String[])aliasList.toArray(new String[aliasList.size()]));
		aliasCombo.select(0);
		aliasCombo.setEditable(false);
		aliasCombo.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				validateData();
			}
			public void widgetDefaultSelected(SelectionEvent e) {
				// ignore
			}
		});
		aliasCombo.addKeyListener(new KeyListener() {
			public void keyReleased(KeyEvent e) {
				validateData();
			}
			public void keyPressed(KeyEvent e) {
				
			}
		});
		
		
		if (defaultAlias != null && defaultAlias.length() > 0)
			aliasCombo.setText(defaultAlias);
		
		new Label(container, SWT.NONE);

		final Label variantLabel = new Label(container, SWT.NONE);
		variantLabel.setToolTipText("Select a product variant."); //$NON-NLS-1$
		variantLabel.setText("Select a product variant: "); //$NON-NLS-1$

		variantCombo = new CCombo(container, SWT.BORDER);
		variantCombo.setLayoutData(new GridData(263, SWT.DEFAULT));
		Collections.sort(variantList);
		variantCombo.setItems((String[])variantList.toArray(new String[variantList.size()]));
		variantCombo.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				validateData();
			}
			public void widgetDefaultSelected(SelectionEvent e) {
				// ignore
			}
		});
		variantCombo.addKeyListener(new KeyListener() {
			public void keyReleased(KeyEvent e) {
				validateData();
			}
			public void keyPressed(KeyEvent e) {
				
			}
		});
		
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, SDKUIHelpIds.SDK_ADD_DIALOG);
		return container;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("Add a custom product configuraiton."); //$NON-NLS-1$
	}

	/**
	 * Return the initial size of the dialog
	 */
	@Override
	protected Point getInitialSize() {
		return super.getInitialSize();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		
		if (!isValidIDName()){
			return;
		}
		newConfigString = aliasCombo.getText() + "." +  variantCombo.getText();
		super.okPressed();
	}


	private boolean isValidIDName(){
		boolean isValid = true;

		if (aliasCombo.getText() == null || aliasCombo.getText().length() == 0 ||
			variantCombo.getText() == null || variantCombo.getText().length() == 0){
			MessageDialog.openError(getShell(), "Invalid Configuration Name", "Please supply both an alias and product variant to create a new configuration.");
			return false;
		}

		return isValid;
	}

	public String getUserCreatedVariant() {
		return newConfigString;
	}
	
	private void validateData(){
		if (variantCombo.getText().length() > 0){
			getButton(IDialogConstants.OK_ID).setEnabled(true);
		} else {
			getButton(IDialogConstants.OK_ID).setEnabled(false);
			return;
		}
		
		if (aliasCombo.getText().length() > 0){
			getButton(IDialogConstants.OK_ID).setEnabled(true);
		} else {
			getButton(IDialogConstants.OK_ID).setEnabled(false);
			return;
		}
	}

}
