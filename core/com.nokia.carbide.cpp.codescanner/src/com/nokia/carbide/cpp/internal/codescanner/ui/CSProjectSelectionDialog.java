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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.dialogs.SelectionStatusDialog;

import com.nokia.carbide.cpp.internal.codescanner.CSPlugin;

/**
 * A class to handle selection of a project.
 */
public class CSProjectSelectionDialog extends SelectionStatusDialog {
	
	/**
	 * Inner class to handle labels of the project selection table.
	 */
	private class CSProjectLabelProvider extends LabelProvider implements
		ITableLabelProvider {

		public String getColumnText(Object element, int index) {
			IProject project = (IProject) element;
			String projectName = "";
			try {
				projectName = project.getDescription().getName();
			}
			catch (CoreException e) {
				//ignore
			}
			return projectName;
		}
		
		public Image getColumnImage(Object element, int index) {
			return null;
		}

	}

	// private members for various controls of this preference page
	private IProject[] projects;
	private TableViewer tableViewer;

	/**
	 * Create an instance of this dialog.
	 * @param parentShell 
	 * @param projectList - list of projects to be displayed in this dialog
	 */
	CSProjectSelectionDialog(Shell parentShell, IProject[] projectList) {
		super(parentShell);
		setTitle("Project Specific Settings");  
		setMessage("Select the project to configure :"); 
		projects = projectList;
		
        int shellStyle = getShellStyle();
        setShellStyle(shellStyle | SWT.MAX | SWT.RESIZE);
	}

	/**
	 * Create contents of this dialog.
	 * @param parent - the parent composite
	 */
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite)super.createDialogArea(parent);
		createMessageArea(container);
		
		tableViewer = new TableViewer(container, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		GridData data= new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint= 200;
		data.widthHint= 200;
		tableViewer.getTable().setLayoutData(data);
		tableViewer.setLabelProvider(new CSProjectLabelProvider());
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				doSelectionChanged(((IStructuredSelection) event.getSelection()).toArray());
			}
		});
		tableViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
                okPressed();
			}
		});

		initializeTable();
		doSelectionChanged(null);
		return container;
	}

    /**
     * Compute the result and return it.
     */
	protected void computeResult() {
	}

	/**
	 * Handle selection within this dialog.
	 * @param objects - objects selected
	 */
	private void doSelectionChanged(Object[] objects) {
		if (objects == null || objects.length != 1) {
			updateStatus(new Status(IStatus.ERROR, CSPlugin.PLUGIN_ID, ""));
			setSelectionResult(null);
		} else {
			updateStatus(new Status(IStatus.OK, CSPlugin.PLUGIN_ID, "")); 
			setSelectionResult(objects);
		}
	}

	/**
	 * Initialize the project selection table.
	 */
	private void initializeTable() {
		for (int i = 0; i < projects.length; i++) {
			TableItem tableItem = new TableItem(tableViewer.getTable(), SWT.LEFT, i);
			tableItem.setData(projects[i]);
			tableItem.setText(projects[i].getName());
		}
	}
}
