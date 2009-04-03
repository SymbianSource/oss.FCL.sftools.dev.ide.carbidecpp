/*
* Copyright (c) 2008 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.carbide.remoteconnections.wizard;

import com.nokia.carbide.remoteconnections.Messages;
import com.nokia.carbide.remoteconnections.RemoteConnectionsActivator;
import com.nokia.carbide.remoteconnections.interfaces.IConnection;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.*;

import java.io.File;
import java.util.ArrayList;

/**
 * Page in export wizard
 */
public class ExportPage extends AbstractImportExportPage {

	private File file;
	private static String saveAsParent;

	protected ExportPage() {
		super("ExportPage"); //$NON-NLS-1$
		setTitle(Messages.getString("ExportPage.Title")); //$NON-NLS-1$
		setDescription(Messages.getString("ExportPage.Description")); //$NON-NLS-1$
	}

	public void createControl(Composite parent) {
		super.createControl(parent);
		Composite composite = (Composite) getControl();
		
        createViewerGroup(composite, Messages.getString("ExportPage.ViewerGroupLabel")); //$NON-NLS-1$
		viewer.addCheckStateListener(new ICheckStateListener() {
			public void checkStateChanged(CheckStateChangedEvent event) {
				boolean isValid = validatePage(false);
				setPageComplete(isValid);
				if (isValid) {
					Object[] checkedElements = viewer.getCheckedElements();
					connections = new ArrayList<IConnection>();
					for (int i = 0; i < checkedElements.length; i++) {
						connections.add((IConnection) checkedElements[i]);
					}
				}
			}
		});
		viewer.setInput(RemoteConnectionsActivator.getConnectionsManager().getConnections());
		TableColumn[] columns = viewer.getTable().getColumns();
		for (TableColumn tableColumn : columns) {
			tableColumn.pack();
		}
		viewer.setAllChecked(true);
		connections = new ArrayList(RemoteConnectionsActivator.getConnectionsManager().getConnections());

		createBrowseGroup(composite, Messages.getString("ExportPage.BrowseGroupLabel")); //$NON-NLS-1$
        browseButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog =  new FileDialog(getShell(), SWT.SAVE);
				dialog.setText(Messages.getString("ExportPage.FileDialogTitle")); //$NON-NLS-1$
				if (saveAsParent == null)
					saveAsParent = System.getProperty("user.home"); //$NON-NLS-1$
				dialog.setFilterPath(saveAsParent);
				dialog.setFileName("exportedConnections.xml"); //$NON-NLS-1$
				dialog.setOverwrite(true); // prompt for overwrite
				String path = dialog.open();
				if (path != null) {
					IPath saveAsPath = new Path(path);
					saveAsParent = saveAsPath.removeLastSegments(1).toString();
					pathText.setText(saveAsPath.toOSString());
				}
			}
        });

        setPageComplete(validatePage(true));
		RemoteConnectionsActivator.setHelp(composite, ".export_wizard"); //$NON-NLS-1$
	}

	protected boolean validatePage(boolean validateFile) {
		setErrorMessage(null);
		boolean valid = !validateFile;
		if (validateFile) {
			IPath path = new Path(pathText.getText());
			file = path.toFile();
			valid = file.isAbsolute();
		}
		
		if (!valid) {
			setErrorMessage(Messages.getString("ExportPage.NoFileSelectedError")); //$NON-NLS-1$
		}
		else if (viewer.getCheckedElements().length == 0) {
			setErrorMessage(Messages.getString("ExportPage.NoConnectionsSelectedError")); //$NON-NLS-1$
			valid = false;
		}
		
		return valid;
	}

	public File getFile() {
		return file;
	}

}
