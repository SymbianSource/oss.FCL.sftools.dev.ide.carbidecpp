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

import java.util.Vector;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.datamodel.util.NamePropertySupport;

/**
 * This class creates the dialog for the Rename and Duplicate actions for the layouts.
 * It validates the input from the user and set the error messages in the dialog.
 * 
 */
public class ActionsLayoutsDialog extends Dialog{
	private int type;
	//GUI
	private Text nameText;
	private Button button; 
	private Label lblError;
	//model
	private String newName;
	private String oldName;
	public	boolean error;
	private Vector<String> currentLayouts;
	private IDesignerDataModel model;
	
	/**
	 * The class constructor. 
	 * @param parentShell
	 * @param type - 0 for rename action , 1 for Duplicate action
	 * @param oldName - The initial name for the Name field.
	 * @param currentLayouts - All the current layouts in the model
	 * @param designerDataModel 
	 */
	public ActionsLayoutsDialog(Shell parentShell,int type,String oldName,
			Vector<String> currentLayouts, IDesignerDataModel model) {
		super(parentShell);
		
		this.type = type; // 0 - rename , 1 - duplicate
		this.oldName = oldName;
		this.newName = oldName;
		this.currentLayouts = currentLayouts;	
		this.model = model;
	}

	/**
	 * Create contents of the dialog
	 * @param parent
	 */
	protected Control createDialogArea(Composite parent) {
		Composite formComposite = (Composite) super.createDialogArea(parent);
		formComposite.setLayout(new FormLayout());
		lblError = new Label(formComposite, SWT.NONE);
		lblError.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		final FormData frmDataLblError = new FormData();
		frmDataLblError.bottom = new FormAttachment(0, 19);
		frmDataLblError.top = new FormAttachment(0, 1);
		frmDataLblError.left = new FormAttachment(0, 1);
		frmDataLblError.right = new FormAttachment(0, 315);
		lblError.setLayoutData(frmDataLblError);
		lblError.setText(Messages.getString("LayoutsDialog.error"));
		lblError.setVisible(false);
		final Composite controlsComposite = new Composite(formComposite, SWT.NONE);
		final GridLayout dialogGridLayout = new GridLayout();
		dialogGridLayout.numColumns = 2;
		dialogGridLayout.marginTop = 25;
		controlsComposite.setLayout(dialogGridLayout);
		
		final Label nameLabel = new Label(controlsComposite, SWT.NONE);
		nameLabel.setLayoutData(new GridData(80, SWT.DEFAULT));
		if (type==0){ //Rename
			nameLabel.setText(Messages.getString("RenameLayoutsDialog.lblRename"));
		}
		else{
			nameLabel.setText(Messages.getString("DuplicateLayoutsDialog.lblDuplicateName"));
		}

		nameText = new Text(controlsComposite, SWT.BORDER);
		nameText.setText(oldName);
		nameText.addModifyListener(new ModifyListener(){
		      public void modifyText(ModifyEvent event) {
		        newName = nameText.getText(); // Get the widget whose text was modified
		        validateControls();
		      }
		    });
		
		final GridData textGridData = new GridData(SWT.RIGHT, SWT.CENTER, true, false);
		textGridData.widthHint = 180;
		nameText.setLayoutData(textGridData);
		
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
	}

	/**
	 * When the OK button it's pressed, calls this method for validations.
	 */
	protected void okPressed() {
		validateControls();		
		if (!error){
			this.close();
		}
		
	}
	
	/**
	 * Return the initial size of the dialog
	 */
	protected Point getInitialSize() {
		return new Point(318, 125);
	}
	
	/**
	 * This method sets the label for the title of the dialog.
	 * @param newShell
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		if (type == 0){
			newShell.setText(Messages.getString("RenameLayoutsDialog.dialogTitle") + " '" + oldName + "' "); //$NON-NLS-1$
		}
		else{
			newShell.setText(Messages.getString("DuplicateLayoutsDialog.dialogTitle") + " '" + oldName + "' "); //$NON-NLS-1$
		}
	}
	
	/**
	 * This method validates the input from the user. The Name field cannot be empty
	 * and it also validates the NewName is a legal name: doesn't start with a number,
	 * no spaces are allowed, only valid characters are allowed: a-z, A-Z, 0-9, _, -.
	 */
	public void validateControls(){
		int index;
		error = false;
		lblError.setVisible(false);
		if (button != null){
			button.setEnabled(true);
		}
		/*index = currentLayouts.indexOf(newName);
		if (index != -1){
			setError("LayoutsDialog.rename.validateRename");	
		}
		else{*/
			if(newName.equals("")){
				setError("LayoutsDialog.rename.validateEmpty");
			}
			else{
				if (!NamePropertySupport.isLegalName(newName)){
					setError("LayoutsDialog.rename.validateEmpty");
				}
				else{//if its legal, let's check if its unique
					EObject foundObj = model.findByNameProperty(newName);
					if (foundObj != null ) {
						String result = NamePropertySupport.duplicateNameMessage(newName);
						setError(result);
					}
				}
			}
	  // }
	}
	
	/**
	 * This method sets the Error state for the dialog. The label Error is shown
	 * with the corresponding message and the OK button is disabled.
	 * @param msg - The localizable string for the error message.
	 */
	public void setError(String msg){
		error=true;
		if (button != null){
			button.setEnabled(false);			
		}
		String label = Messages.getString(msg);
		if (label.charAt(0) == '!'){
			label = msg;
		}
		lblError.setText(label);
		lblError.setVisible(true);
	}
	
	/**
	 * This method returns the Name field.
	 * @return newName - The user input
	 */
	public String getName(){
		return newName;
	}

}
