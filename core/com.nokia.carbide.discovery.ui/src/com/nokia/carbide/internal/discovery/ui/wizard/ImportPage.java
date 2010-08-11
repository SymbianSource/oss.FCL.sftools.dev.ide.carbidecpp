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

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;

import com.nokia.cpp.internal.api.utils.ui.BrowseDialogUtils;

/**
 * Page in import wizard
 */
class ImportPage extends AbstractImportExportPage {

	private boolean importOriginalVersion;

	// the following two arrays need to correspond
	static final String[] FILTER_EXTS  = { 
		"*.xml", //$NON-NLS-1$
		"*.*" //$NON-NLS-1$
	};
	static final String[] FILTER_EXT_NAMES  = { 
		"XML Files",
		"All Files"
	};
	

	protected ImportPage() {
		super("ImportPage"); //$NON-NLS-1$
		setTitle("Import Feature Configuration and Install");
		setDescription("Import a feature configurations from a file and install the features");
	}

	public void createControl(Composite parent) {
		super.createControl(parent);
		Composite composite = (Composite) getControl();

        createBrowseGroup(composite, "Feature configuration file:");
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
				}
			}
	    });
        createViewerGroup(composite, "Import Features:");
		viewer.addCheckStateListener(new ICheckStateListener() {
			public void checkStateChanged(CheckStateChangedEvent event) {
				setPageComplete(validatePage());
			}
		});

        setPageComplete(validatePage());
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
	    		importOriginalVersion = originalVersionCheck.getSelection();
	    	}
		});
	}
	
	protected boolean validatePage() {
		setErrorMessage(null);
		IPath path = new Path(pathText.getText());
		File file = path.toFile();
		if (file.exists()) {
			FeatureInfo[] featureInfos = getFeaturesFromFile(file);
			if (featureInfos.length == 0) {
				setErrorMessage("The file has no valid features");
				return false;
			}
			viewer.setInput(featureInfos);
			updateViewer();
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

	private FeatureInfo[] getFeaturesFromFile(File file) {
		// TODO
		return new FeatureInfo[0];
	}

}
