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
package com.nokia.sdt.symbian.ui.appeditor;

import com.nokia.sdt.emf.dm.Language;
import com.nokia.sdt.symbian.SymbianLanguage;
import com.nokia.sdt.symbian.ui.noexport.Messages;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;

import java.util.ArrayList;
import java.util.List;

public class LanguagesDialog extends Dialog {
	
	private List<SymbianLanguage> allowedLanguages;
	private CheckboxTableViewer viewer;
	private SymbianLanguage[] okResult;

	class ContentProvider implements IStructuredContentProvider {
		public Object[] getElements(Object inputElement) {
			return allowedLanguages.toArray();
		}
		public void dispose() {
		}
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}
	class TableLabelProvider extends LabelProvider implements ITableLabelProvider {
		public String getColumnText(Object element, int columnIndex) {
			return element.toString();
		}
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}
	}
	private Table table;
	/**
	 * Create the dialog
	 * @param parentShell
	 */
	public LanguagesDialog(Shell parentShell, List<Language> enabledLanguages) {
		super(parentShell);
		
		allowedLanguages = new ArrayList<SymbianLanguage>();
		// filter already enabled languages from the language list
		SymbianLanguage[] allLanguages = SymbianLanguage.getLanguages();
		for (int i = 0; i < allLanguages.length; i++) {
			SymbianLanguage currLang = allLanguages[i];
			boolean enabled = false;
			for (Language lang : enabledLanguages) {
				if (lang.getLanguageCode() == currLang.code) {
					enabled = true;
					break;
				}
			}
			if (!enabled) {
				allowedLanguages.add(currLang);
			}
		}
	}

	/**
	 * Create contents of the dialog
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);

		final Label selectOneOrLabel = new Label(container, SWT.NONE);
		selectOneOrLabel.setText(Messages.getString("LanguagesDialog.prompt")); //$NON-NLS-1$

		viewer = CheckboxTableViewer.newCheckList(container, SWT.BORDER);
		viewer.setContentProvider(new ContentProvider());
		viewer.setLabelProvider(new TableLabelProvider());
		viewer.setInput(new Object());
		table = viewer.getTable();
		table.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		//
				
		return container;
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
		return new Point(386, 375);
	}
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Messages.getString("LanguagesDialog.dialogTitle")); //$NON-NLS-1$
	}
	
	
	
	@Override
	protected void okPressed() {
		Object[] array = viewer.getCheckedElements();
		if (array != null & array.length > 0) {
			okResult = new SymbianLanguage[array.length];
			for (int i = 0; i < array.length; i++) {
				okResult[i] = (SymbianLanguage) array[i];
			}
		}
		super.okPressed();
	}

	public SymbianLanguage[] getSelectedLanguages() {
		return okResult;
	}
}
