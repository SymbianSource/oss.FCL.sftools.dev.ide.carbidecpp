/*
* Copyright (c) 2010 Nokia Corporation and/or its subsidiary(-ies).
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
package com.nokia.carbide.internal.discovery.ui.wizard;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.progress.UIJob;

import com.nokia.carbide.discovery.ui.Activator;
import com.nokia.cpp.internal.api.utils.ui.BrowseDialogUtils;

/**
 * Page in import wizard
 */
class ImportPage extends AbstractImportExportPage {

	// the following two arrays need to correspond
	static final String[] FILTER_EXTS  = { 
		"*.xml", //$NON-NLS-1$
		"*.*" //$NON-NLS-1$
	};
	static final String[] FILTER_EXT_NAMES  = { 
		"XML Files",
		"All Files"
	};

	private String currentPath;
	private ImportExportData readData;
	protected boolean wantsOriginalVersions;
	
	protected ImportPage() {
		super("ImportPage"); //$NON-NLS-1$
		setTitle("Import Feature Configuration and Install");
		setDescription("Import a feature configurations from a file and install the features");
	}

	public void createControl(Composite parent) {
		super.createControl(parent);
		Composite composite = (Composite) getControl();

        createBrowseGroup(composite, "Import file:");
        createViewerGroup(composite, "Import Features:");

        setPageComplete(validatePage());
	}
	
	@Override
	protected void createBrowseGroup(Composite parent, String labelText) {
		super.createBrowseGroup(parent, labelText);
		pathText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				handlePathChanged();
			}
		});
		
		browseButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileDialog = new FileDialog(getShell(), SWT.OPEN);
				fileDialog.setText("Select an Exported Feature Configuration File");
				fileDialog.setFilterExtensions(FILTER_EXTS);
				fileDialog.setFilterNames(FILTER_EXT_NAMES);
				BrowseDialogUtils.initializeFrom(fileDialog, pathText);
				String pathstr = fileDialog.open();
				if (pathstr != null) {
					pathText.setText(pathstr);
//					handlePathChanged();
				}
			}
		});
	}
	
	@Override
	protected void createViewerGroup(Composite parent, String labelText) {
		super.createViewerGroup(parent, labelText);

		viewer.addCheckStateListener(new ICheckStateListener() {
			public void checkStateChanged(CheckStateChangedEvent event) {
				setPageComplete(validatePage());
			}
		});
		updateViewer();
	}

	private void startGetInputJob(final String path) {
		UIJob j = new UIJob("Reading Feature Configuration File") {
			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				try {
					FileInputStream is = new FileInputStream(path);
					readData = Streamer.readFromXML(is);
					viewer.setInput(readData.getFeatureInfos());
					updateViewer();
					monitor.done();
				} catch (IOException e) {
					// may have bad/incomplete path, so don't log this
				} catch (Exception e) {
					Activator.logError(MessageFormat.format("Could not read data from file: {0}", path), e);
				}
				return Status.OK_STATUS;
			} 
		};
		j.schedule();
	}
	
	protected void createVersionRadioGroup(Composite parent) {
	    Composite composite = new Composite(parent, SWT.NONE);
        GridLayoutFactory.fillDefaults().applyTo(composite);
	    GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, false).applyTo(composite);
	    
	    final Button originalVersionCheck = new Button(composite, SWT.CHECK);  
	    originalVersionCheck.setText("Attempt import original feature versions");
	    originalVersionCheck.addSelectionListener(new SelectionAdapter() {
			@Override
	    	public void widgetSelected(SelectionEvent e) {
	    		wantsOriginalVersions = originalVersionCheck.getSelection();
	    	}
		});
	}

	protected boolean validatePage() {
		setErrorMessage(null);
		IPath path = new Path(pathText.getText());
		if (isFilePath(path.toOSString())) {
			if (readData == null || readData.getFeatureInfos().isEmpty()) {
				setErrorMessage("No valid features found in configurations file");
				return false;
			}
			if (readData == null || readData.getURIs().isEmpty()) {
				setErrorMessage("No valid repositories found in configurations file");
				return false;
			}
		}
		else {
			setErrorMessage("A valid exported feature configuration file must be selected");
			return false;
		}
		
		if (viewer.getCheckedElements().length == 0) {
			setErrorMessage("At least one feature must be selected for import");
			return false;
		}
		
		return true;
	}

	private boolean isFilePath(String path) {
		File file = new File(path);
		return file.exists() && !file.isDirectory();
	}

	private void handlePathChanged() {
		String path = pathText.getText();
		if (!path.equals(currentPath) && isFilePath(path)) {
			startGetInputJob(currentPath = path);
		}
	}

	public ImportExportData getData() {
		return readData;
	}
}
