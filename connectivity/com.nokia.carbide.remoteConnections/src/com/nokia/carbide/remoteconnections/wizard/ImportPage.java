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
import com.nokia.carbide.remoteconnections.internal.registry.Reader;
import com.nokia.cpp.internal.api.utils.ui.BrowseDialogUtils;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.*;

import java.io.*;

/**
 * Page in import wizard
 */
public class ImportPage extends AbstractImportExportPage {

	// the following two arrays need to correspond
	static final String[] FILTER_EXTS  = { 
		"*.xml", //$NON-NLS-1$
		"*.*" //$NON-NLS-1$
	};
	static final String[] FILTER_EXT_NAMES  = { 
		Messages.getString("ImportPage.XMLFilesLabel"), //$NON-NLS-1$
		Messages.getString("ImportPage.AllFilesLabel") //$NON-NLS-1$
	};
	

	protected ImportPage() {
		super("ImportPage"); //$NON-NLS-1$
		setTitle(Messages.getString("ImportPage.Title")); //$NON-NLS-1$
		setDescription(Messages.getString("ImportPage.Description")); //$NON-NLS-1$
	}

	public void createControl(Composite parent) {
		super.createControl(parent);
		Composite composite = (Composite) getControl();

        createBrowseGroup(composite, Messages.getString("ImportPage.BrowseGroupLabel")); //$NON-NLS-1$
	    browseButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileDialog = new FileDialog(getShell(), SWT.OPEN);
				fileDialog.setText(Messages.getString("ImportPage.FileDialogTitle")); //$NON-NLS-1$
				fileDialog.setFilterExtensions(FILTER_EXTS);
				fileDialog.setFilterNames(FILTER_EXT_NAMES);
				BrowseDialogUtils.initializeFrom(fileDialog, pathText);
				String pathstr = fileDialog.open();
				if (pathstr != null) {
					pathText.setText(pathstr);
				}
			}
	    });
        createViewerGroup(composite, Messages.getString("ImportPage.ViewerGroupLabel")); //$NON-NLS-1$
		viewer.addCheckStateListener(new ICheckStateListener() {
			public void checkStateChanged(CheckStateChangedEvent event) {
				setPageComplete(validatePage(false));
			}
		});

        setPageComplete(validatePage(true));
		RemoteConnectionsActivator.setHelp(composite, ".import_wizard"); //$NON-NLS-1$
	}

	protected boolean validatePage(boolean validateFile) {
		setErrorMessage(null);
		boolean valid = !validateFile;
		if (validateFile) {
			connections = null;
			viewer.setInput(connections);
			IPath path = new Path(pathText.getText());
			File file = path.toFile();
			if (file.exists()) {
				try {
					InputStream is = new FileInputStream(file); 
					connections = Reader.readFromXML(RemoteConnectionsActivator.getConnectionTypeProvider(), is);
					viewer.setInput(connections);
					TableColumn[] columns = viewer.getTable().getColumns();
					for (TableColumn tableColumn : columns) {
						tableColumn.pack();
					}
					viewer.setAllChecked(true);
					valid = true;
				}
				catch (Throwable t) {
					// if we get an exception, it just means this was not a valid file
				}
			}
		}
		
		if (!valid) {
			setErrorMessage(Messages.getString("ImportPage.NoValidFileSelectedError")); //$NON-NLS-1$
		}
		else if (connections.isEmpty()) {
			setErrorMessage(Messages.getString("ImportPage.NoConnectionsInFileError")); //$NON-NLS-1$
			valid = false;
		}
		else if (viewer.getCheckedElements().length == 0) {
			setErrorMessage(Messages.getString("ImportPage.NoConnectionsSelectedError")); //$NON-NLS-1$
			valid = false;
		}
		
		return valid;
	}

}
