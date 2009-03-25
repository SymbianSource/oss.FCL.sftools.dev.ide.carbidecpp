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
package com.nokia.cpp.internal.api.utils.ui;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.utils.ui.noexport.Messages;

import java.util.ArrayList;

/**
 * Dialog allowing selection of a folder inside a given container
 * (like ContainerSelectionDialog, but actually limiting the scope).
 *
 */
public class ProjectFolderSelectionDialog extends Dialog {

	private Label messageLabel;
	private String message;
	private IContainer root;
	private TreeViewer folderList;
	protected IPath path;
	private final boolean showFiles;

	public ProjectFolderSelectionDialog(Shell parentShell, IContainer root, String message, boolean showFiles) {
		super(parentShell);
		setShellStyle(getShellStyle() | SWT.RESIZE);
		this.root = root;
		this.showFiles = showFiles;
		this.message = message != null ? message : Messages.getString("ProjectFolderSelectionDialog.SelectFolderDescriptionLabel"); //$NON-NLS-1$
		this.path = new Path(""); //$NON-NLS-1$
	}

	public ProjectFolderSelectionDialog(Shell parentShell, IContainer root, String message) {
		this(parentShell, root, message, false);
	}
	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Messages.getString("ProjectFolderSelectionDialog.SelectFolderTitle")); //$NON-NLS-1$
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		messageLabel = new Label(composite, SWT.NONE);
		messageLabel.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		messageLabel.setText(message);
		
		folderList = new TreeViewer(composite);
		GridDataFactory.fillDefaults().grab(true, true).hint(SWT.DEFAULT, 200).applyTo(folderList.getTree());
		folderList.setContentProvider(new WorkbenchContentProvider() {
			/* (non-Javadoc)
			 * @see org.eclipse.ui.model.BaseWorkbenchContentProvider#getChildren(java.lang.Object)
			 */
			@Override
			public Object[] getElements(Object element) {
				// first element is the project
				return new Object[] { root };
			}
			
			public Object[] getChildren(Object element) {
				Object[] orig = super.getChildren(element);
				if (showFiles)
					return orig;
				
				java.util.List<Object> containers = new ArrayList<Object>();
				for (Object item : orig) {
					if (item instanceof IContainer) {
						containers.add(item);
					}
				}
				return containers.toArray();
			}
		});
		folderList.setLabelProvider(new WorkbenchLabelProvider());
		folderList.setInput(new Object());
		
		folderList.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				if (selection.isEmpty())
					path = null;
				else {
					IResource resource = (IResource)selection.getFirstElement();
					if (resource instanceof IContainer) {
						path = FileUtils.removePrefixFromPath(root.getLocation(), 
								resource.getLocation());
					} else {
						path = null;
					}
				}	
				Button okButton = getButton(IDialogConstants.OK_ID);
				if (okButton != null)
					okButton.setEnabled(path != null);
			}
			
		});
		
		return composite;
	}

	/**
	 * @return
	 */
	public IPath getPath() {
		return path;
	}
	
}
