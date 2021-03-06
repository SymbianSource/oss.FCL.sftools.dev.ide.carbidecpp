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
package com.nokia.carbide.cdt.internal.builder.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;

public class AddEditEnvVarDialog extends Dialog {
	protected static final String FIELD_NAME = "FIELD_NAME"; //$NON-NLS-1$
	protected static final int TEXT = 100;
	protected static final int BROWSE = 101;
	protected static final int VARIABLE = 102;
	
	protected Composite panel;
	
	protected List<FieldSummary> fieldList = new ArrayList<FieldSummary>();
	protected List<Control> controlList = new ArrayList<Control>();
	protected List<Validator> validators = new ArrayList<Validator>();
	protected Map<Object, Object> valueMap = new HashMap<Object, Object>();

	private String title;
	
	public AddEditEnvVarDialog(Shell shell, String title) {
		super(shell);
		this.title = title;
		setShellStyle(getShellStyle() | SWT.RESIZE);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		if (title != null) {
			shell.setText(title);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createButtonBar(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createButtonBar(Composite parent) {
		Control bar = super.createButtonBar(parent);
		validateFields();
		return bar;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite)super.createDialogArea(parent);
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		panel = new Composite(container, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		panel.setLayout(layout);
		panel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		for (Iterator<FieldSummary> i = fieldList.iterator(); i.hasNext();) {
			FieldSummary field = (FieldSummary)i.next();
			switch(field.type) {
				case TEXT:
					createTextField(field.name, field.initialValue, field.allowsEmpty);
					break;
				case BROWSE:
					createBrowseField(field.name, field.initialValue, field.allowsEmpty);
					break;
				case VARIABLE:
					createVariablesField(field.name, field.initialValue, field.allowsEmpty);
					break;
			}
		}
		
		fieldList = null; // allow it to be gc'd
		Dialog.applyDialogFont(container);
		return container;
	}
	
	public void addBrowseField(String labelText, String initialValue, boolean allowsEmpty) {
		fieldList.add(new FieldSummary(BROWSE, labelText, initialValue, allowsEmpty));
	}
	public void addTextField(String labelText, String initialValue, boolean allowsEmpty) {
		fieldList.add(new FieldSummary(TEXT, labelText, initialValue, allowsEmpty));
	}
	public void addVariablesField(String labelText, String initialValue, boolean allowsEmpty) {
		fieldList.add(new FieldSummary(VARIABLE, labelText, initialValue, allowsEmpty));
	}

	protected void createTextField(String labelText, String initialValue, boolean allowEmpty) { 
		Label label = new Label(panel, SWT.NONE);
		label.setText(labelText);
		label.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
		
		final Text text = new Text(panel, SWT.SINGLE | SWT.BORDER);
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		text.setData(FIELD_NAME, labelText);
		
		// make sure rows are the same height on both panels.
		label.setSize(label.getSize().x, text.getSize().y); 
		
		if (initialValue != null) {
			text.setText(initialValue);
		}
		
		if (!allowEmpty) {
			validators.add(new Validator() {
				public boolean validate() {
					return !text.getText().equals(""); //$NON-NLS-1$
				}
			});
			text.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					validateFields();
				}
			});
		}
		
		controlList.add(text);
	}
	
	protected void createBrowseField(String labelText, String initialValue, boolean allowEmpty) {
		Label label = new Label(panel, SWT.NONE);
		label.setText(labelText);
		label.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
		
		Composite comp = new Composite(panel, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight=0;
		layout.marginWidth=0;
		comp.setLayout(layout);
		comp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		final Text text = new Text(comp, SWT.SINGLE | SWT.BORDER);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = 200;
		text.setLayoutData(data);
		text.setData(FIELD_NAME, labelText);

		// make sure rows are the same height on both panels.
		label.setSize(label.getSize().x, text.getSize().y); 
		
		if (initialValue != null) {
			text.setText(initialValue);
		}

		if (!allowEmpty) {
			validators.add(new Validator() {
				public boolean validate() {
					return !text.getText().equals(""); //$NON-NLS-1$
				}
			});

			text.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					validateFields();
				}
			});
		}
		
		controlList.add(text);
	}
	
	
	public void createVariablesField(String labelText, String initialValue, boolean allowEmpty) {
		Label label = new Label(panel, SWT.NONE);
		label.setText(labelText);
		label.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
		
		Composite comp = new Composite(panel, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight=0;
		layout.marginWidth=0;
		comp.setLayout(layout);
		comp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		final Text text = new Text(comp, SWT.SINGLE | SWT.BORDER);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = 200;
		text.setLayoutData(data);
		text.setData(FIELD_NAME, labelText);

		// make sure rows are the same height on both panels.
		label.setSize(label.getSize().x, text.getSize().y); 
		
		if (initialValue != null) {
			text.setText(initialValue);
		}

		if (!allowEmpty) {
			validators.add(new Validator() {
				public boolean validate() {
					return !text.getText().equals(""); //$NON-NLS-1$
				}
			});

			text.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					validateFields();
				}
			});
		}
		
		controlList.add(text);
				
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	protected void okPressed() {
		for (Iterator<Control> i = controlList.iterator(); i.hasNext(); ) {
			Control control = (Control)i.next();
			if (control instanceof Text) {
				valueMap.put(control.getData(FIELD_NAME), ((Text)control).getText());
			}
		}
		controlList = null;
		super.okPressed();
	}

	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#open()
	 */
	public int open() {
		applyDialogFont(panel);
		return super.open();
	}
	
	public Object getValue(String key) {
		return valueMap.get(key);
	}
	
	public String getStringValue(String key) {
		return  (String) getValue(key);
	}
	
	public void validateFields() {
		for(Iterator<Validator> i = validators.iterator(); i.hasNext(); ) {
			Validator validator = (Validator) i.next();
			if (!validator.validate()) {
				getButton(IDialogConstants.OK_ID).setEnabled(false);
				return;
			}
		}
		getButton(IDialogConstants.OK_ID).setEnabled(true);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#getInitialLocation(org.eclipse.swt.graphics.Point)
	 */
	protected Point getInitialLocation(Point initialSize) {
		Point initialLocation= DialogSettingsHelper.getInitialLocation(getDialogSettingsSectionName());
		if (initialLocation != null) {
			return initialLocation;
		}
		return super.getInitialLocation(initialSize);
	}

	
	protected String getDialogSettingsSectionName() {
		return CarbideBuilderPlugin.PLUGIN_ID + ".MULTIPLE_INPUT_DIALOG_2"; //$NON-NLS-1$
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#getInitialSize()
	 */
	protected Point getInitialSize() {
		Point size = super.getInitialSize();
		return DialogSettingsHelper.getInitialSize(getDialogSettingsSectionName(), size);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#close()
	 */
	public boolean close() {
		DialogSettingsHelper.persistShellGeometry(getShell(), getDialogSettingsSectionName());
		return super.close();
	}

	protected class FieldSummary {
		int type;
		String name;
		String initialValue;
		boolean allowsEmpty;
		
		public FieldSummary(int type, String name, String initialValue, boolean allowsEmpty) {
			this.type = type;
			this.name = name;
			this.initialValue = initialValue;
			this.allowsEmpty = allowsEmpty;
		}
	}
	
	protected class Validator {
		boolean validate() {
			return true;
		}
	}

}
