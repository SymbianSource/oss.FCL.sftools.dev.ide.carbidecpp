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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

import com.nokia.carbide.cpp.internal.project.ui.editors.common.NamePatternFilter;
import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.HelpContexts;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

public class AddLibraryDialog extends TrayDialog {
	
	private Collection<String> sdkLibraries;
	private Text libraryTextControl;
	private TableViewer libraryViewer;
	private List<String> libraryNames;
	private NamePatternFilter libraryFilter;

	/**
	 * Create the dialog
	 * @param parentShell
	 * @param sdkLibraries the SDK-provided libraries
	 */
	public AddLibraryDialog(Shell parentShell, Collection<String> sdkLibraries) {
		super(parentShell);
		this.sdkLibraries = sdkLibraries;
		setHelpAvailable(true);
	}

	@Override
	protected void okPressed() {
		libraryNames = new ArrayList<String>();
		String customName = libraryTextControl.getText();
		ISelection selection = libraryViewer.getSelection();
		if (selection.isEmpty()) {
			if (customName.length() > 0) {
				libraryNames.add(customName);
			} 
		}
		else {
			if (selection instanceof IStructuredSelection) {
				IStructuredSelection ss = (IStructuredSelection) selection;
				for (Iterator iter = ss.iterator(); iter.hasNext(); ) {
					libraryNames.add(iter.next().toString());
				}
			}
		}
		super.okPressed();
	}

	public List<String> getLibraryNames() {
		return libraryNames;
	}

	/**
	 * Create contents of the dialog
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);

		final Label prompt = new Label(container, SWT.WRAP);
		prompt.setText(Messages.getString("AddLibraryDialog.prompt")); //$NON-NLS-1$

		libraryTextControl = new Text(container, SWT.BORDER);
		libraryTextControl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));		
		libraryTextControl.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateFilter();
				updateOKButton();
			}
		});

		libraryViewer = new TableViewer(container, SWT.MULTI | SWT.BORDER);
		libraryViewer.setContentProvider(new ArrayContentProvider());
		libraryViewer.setSorter(new ViewerSorter());
		libraryViewer.setInput(sdkLibraries);
		libraryViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				updateOKButton();
			}
		});
		libraryViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				okPressed();
			}
		});
		Table table = libraryViewer.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		installFilter();
		
		return container;
	}

	protected void updateOKButton() {
		boolean haveText = libraryTextControl.getText().length() > 0;
		boolean haveListSelection = !libraryViewer.getSelection().isEmpty();
		getButton(IDialogConstants.OK_ID).setEnabled(haveText || haveListSelection);
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
		return new Point(394, 460);
	}
	
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Messages.getString("AddLibraryDialog.dialogTitle")); //$NON-NLS-1$
		WorkbenchUtils.setHelpContextId(newShell, HelpContexts.ADD_LIBRARY_DIALOG);
	}

	/**
	 * Install name filter for the library list viewer.
	 */
	protected void installFilter() {
		libraryFilter = new NamePatternFilter();
		libraryFilter.setPattern(null);
		libraryViewer.addFilter(libraryFilter);
	}

	/**
	 * Update library list using text entered by user as filter.
	 */
	protected void updateFilter() {
		String pattern = libraryTextControl.getText();
		libraryFilter.setPattern(pattern);
		libraryViewer.refresh();
		libraryViewer.getControl().redraw();
	}
}
