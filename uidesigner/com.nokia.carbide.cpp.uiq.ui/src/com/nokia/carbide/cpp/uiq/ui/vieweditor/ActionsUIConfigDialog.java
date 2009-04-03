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
/* START_USECASES: CU5 END_USECASES */
package com.nokia.carbide.cpp.uiq.ui.vieweditor;


import java.util.List;
import java.util.Vector;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.nokia.sdt.symbian.dm.UIQModelUtils;
/**
 * This class creates the dialog for the actions Add and Duplicate for the UI configuration.
 * It validates the user's input and manages the errors.
 *
 */
public class ActionsUIConfigDialog extends Dialog{
	private int type; //0 for Add and 1 for Duplicate
	//GUI
	private Label errorLbl; 
	private Combo uiConfigCombo;
	private Combo layoutCombo;
	private Combo commandListCombo;
	private boolean error;
	private Button button; 
	private String uiConfigValue;
	private String layoutValue;
	private String commandListValue;
	private List<String> uiConfigValueTable;
	//Model
	private UIConfigurationPageUtils uiConfigurationPageUtils;
	private final String UI_LAYOUT;
	private final String COMMAND_LIST = UIQModelUtils.UIQ_COMMAND_LIST;
	
	/**
	 * The class constructor.
	 * @param parentShell 
	 * @param type - 0 for Add and 1 for Duplicate
	 * @param uiConfigurationPageUtils
	 * @param UI_LAYOUT - The ID for the layout type (CQikContainer or View Layout)
	 * @param uiConfigValueTable - The names of the currents UI Configurations
	 */
	protected ActionsUIConfigDialog(Shell parentShell,int type,UIConfigurationPageUtils uiConfigurationPageUtils,
			String UI_LAYOUT,
			List<String> uiConfigValueTable) {
		super(parentShell);
		this.type = type;
		this.uiConfigurationPageUtils = uiConfigurationPageUtils;
		this.UI_LAYOUT = UI_LAYOUT;
		this.uiConfigValueTable = uiConfigValueTable;
	}
	
	/**
	 * Create contents of the dialog
	 * @param parent
	 */
	protected Control createDialogArea(Composite parent) {
		Composite formComposite = (Composite) super.createDialogArea(parent);
		formComposite.setLayout(new FormLayout());
		errorLbl = new Label(formComposite, SWT.NONE);
		errorLbl.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		final FormData errorLblFormData = new FormData();
		errorLblFormData.bottom = new FormAttachment(0, 20);
		errorLblFormData.top = new FormAttachment(0, 0);
		errorLblFormData.right = new FormAttachment(0, 335);
		errorLblFormData.left = new FormAttachment(0, 0);
		errorLbl.setLayoutData(errorLblFormData);
		errorLbl.setText(Messages.getString("AddUIConfigDialog.lblError"));
		errorLbl.setVisible(false);
		
		final Composite controlsComposite = new Composite(formComposite, SWT.NONE);
		final GridLayout controlsGridLayout = new GridLayout();
		controlsGridLayout.numColumns = 2;
		controlsComposite.setLayout(controlsGridLayout);
		final FormData controlsCompositeFormData = new FormData();
		controlsCompositeFormData.bottom = new FormAttachment(0, 140-(type*60));
		controlsCompositeFormData.right = new FormAttachment(0, 336);
		controlsCompositeFormData.top = new FormAttachment(0, 23);
		controlsCompositeFormData.left = new FormAttachment(0, -1);
		controlsComposite.setLayoutData(controlsCompositeFormData);

		final Label uiConfigLbl = new Label(controlsComposite, SWT.NONE);
		uiConfigLbl.setLayoutData(new GridData(110, SWT.DEFAULT));
		uiConfigLbl.setText(Messages.getString("AddUIConfigDialog.lblUIConfig"));

		uiConfigCombo = new Combo(controlsComposite, SWT.READ_ONLY);
		uiConfigCombo.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent arg0) {
				uiConfigValue = uiConfigCombo.getItem(0); 
			}

			public void widgetSelected(SelectionEvent arg0) {
				uiConfigValue = uiConfigCombo.getItem(uiConfigCombo.getSelectionIndex());
				errorLbl.setVisible(false);
				error = false;
				validateUIConfig();
			}
		});
		final GridData uiConfigComboGridData = new GridData(SWT.LEFT, SWT.CENTER, true, false);
		uiConfigComboGridData.widthHint = 180;
		uiConfigCombo.setLayoutData(uiConfigComboGridData);

		if (type == 0) { //Add window
			final Label layoutLbl = new Label(controlsComposite, SWT.NONE);
			layoutLbl.setLayoutData(new GridData(110, SWT.DEFAULT));
			layoutLbl.setText(Messages.getString("AddUIConfigDialog.lblLayout"));
			layoutCombo = new Combo(controlsComposite, SWT.READ_ONLY);
			layoutCombo.addSelectionListener(new SelectionListener() {

				public void widgetDefaultSelected(SelectionEvent arg0) {
					layoutValue = layoutCombo.getItem(0); 
				}

				public void widgetSelected(SelectionEvent arg0) {
					layoutValue = layoutCombo.getItem(layoutCombo.getSelectionIndex());
					validateControls();
				}
				
			});
			final GridData layoutComboGridData = new GridData(SWT.LEFT,
					SWT.CENTER, true, false);
			layoutComboGridData.widthHint = 180;
			layoutCombo.setLayoutData(layoutComboGridData);
			final Label commandLbl = new Label(controlsComposite, SWT.NONE);
			commandLbl.setLayoutData(new GridData(110, SWT.DEFAULT));
			commandLbl.setText(Messages.getString("AddUIConfigDialog.lblCommand"));
			commandListCombo = new Combo(controlsComposite, SWT.READ_ONLY);
			commandListCombo.addSelectionListener(new SelectionListener() {

				public void widgetDefaultSelected(SelectionEvent arg0) {
					commandListValue = "";//commandListCombo.getItem(0); 
				}

				public void widgetSelected(SelectionEvent arg0) {
					if (commandListCombo.getSelectionIndex() == 0){
						commandListValue = "";
					}
					else{
						commandListValue = commandListCombo.getItem(commandListCombo.getSelectionIndex());
					}
				}
				
			});
			final GridData commandComboGridData = new GridData(SWT.LEFT,
					SWT.CENTER, true, false);
			commandComboGridData.widthHint = 180;
			commandListCombo.setLayoutData(commandComboGridData);
		}
		initControls();
		return formComposite;	
	}
	

	/**
	 * Create contents of the button bar
	 * @param parent
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		System.currentTimeMillis();
		button = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
		if (error){
			button.setEnabled(false);
		}
		else{
			button.setEnabled(true);
		}
			
	}
	
	/**
	 * Return the initial size of the dialog
	 */
	protected Point getInitialSize() {
		return new Point(343, 185-(type*60));
	}
	
	/**
	 * This method validates the user's input. The system will validate that the Developer doesn’t 
	 * create more than one UI Configuration with the same UI Config Mode.
	 */
	public void validateControls(){
		validateUIConfig();		
		if (layoutValue.equals("")){
			setError("UIConfigurationPage.validate.layout");
			layoutCombo.setFocus();
		}
	}
	
	/**
	 * When the user press the OK button, this method is called for the 
	 * validations. If there is no error then the dialog is closed.
	 */
	protected void okPressed() {
		if (type == 0){
			validateControls();
		}
		if (!error){
			this.close();
		}	
		
	}
	
	/**
	 * This method configures the Title for the dialog.
	 * @param newShell
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		if (type == 0){ //Add
			newShell.setText(Messages.getString("AddUIConfigDialog.dialogTitle")); //$NON-NLS-1$
		}
		else{ //Duplicate
			newShell.setText(Messages.getString("DuplicateUIConfigDialog.dialogTitle")); //$NON-NLS-1$
		}
	}
	
	/**
	 * This method validates the user's input. Initialize the state of the dialog to no error.
	 * The error label is set invisible, the flag Error is set to False, and the
	 * OK button is enabled.
	 * The validations are executed and in case of error it calls the setError method.
	 */
	public void validateUIConfig() {
		error = false;
		errorLbl.setVisible(false);
		if (button != null){
			button.setEnabled(true);
		}		
		if (uiConfigValue.equals("")){
			setError("UIConfigurationPage.validate.UIConfig");
			uiConfigCombo.setFocus();
			return;
		}
		for(int i = 0; i < uiConfigValueTable.size(); i++){
			if (uiConfigValueTable.get(i).equals(uiConfigValue)){
				setError("DuplicateLayoutsDialog.errorLabel.uiMode");
				break;
			}			
		}
	}
	
	/**
	 * It sets the state for Error of the dialog. The error label is visible.
	 * The OK button is disabled and the flag error is set to true.
	 * @param msg - The localizable string for the error message.
	 */
	public void setError(String msg){
		error = true;
		if (button != null){
			button.setEnabled(false);			
		}
		errorLbl.setText(Messages.getString(msg));
		errorLbl.setVisible(true);
	}
	
	/**
	 * This method initializes the fields of the dialog.
	 */
	private void initControls(){
		initUIConfigCombo(); //init UI config mode combo
		uiConfigValue = "";
		validateUIConfig();
		if (this.type == 0) {//Add window
			 initLayoutCombo();
			 initCommandListCombo();
			 layoutValue="";
			 commandListValue="";
		}
	}
	
	/**
	 * Initializes the layouts  combo.
	 */
	private void initLayoutCombo(){
		Vector<String> currentLayouts = uiConfigurationPageUtils.getCurrentObjectsFromModel(UI_LAYOUT);
		//layoutCombo.add(Messages.getString("LayoutCombo.option"));
		for (String layoutName : currentLayouts) {
			layoutCombo.add(layoutName);
		}		
	}
	
	/**
	 * Initializes the command list combo.
	 */
	private void initCommandListCombo(){
		Vector<String> currentCommandLists = uiConfigurationPageUtils.getCurrentObjectsFromModel(COMMAND_LIST);
		commandListCombo.add(Messages.getString("CommandListCombo.option"));
		for (String commandListName : currentCommandLists) {
			commandListCombo.add(commandListName);
		}
	}
	
	/**
	 * Initializes the UI configuration combo.
	 */
	private void initUIConfigCombo(){
		for (int i=0; i<uiConfigurationPageUtils.uiConfigModeLabels.length; i++) {
	    	uiConfigCombo.add(uiConfigurationPageUtils.uiConfigModeLabels[i]);		
		}
	}

	/**
	 * Returns the UI config type string
	 * @return Returns the UI config string selected
	 */
	public String getUiConfigValue() {
		return uiConfigValue;
	}
	/**
	 * Returns the layout name string
	 * @return Returns the layout string selected
	 */
	public String getLayoutValue() {
		return layoutValue;
	}
	/**
	 * Returns the command list name string
	 * @return name of the command list selected.
	 */
	public String getCommandListValue() {
		return commandListValue;
	}
}
