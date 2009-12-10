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

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import com.nokia.cpp.utils.ui.noexport.Messages;

import java.io.File;
import java.util.*;
import java.util.List;

/**
 * A utility class for a directory combo, browse button, and a history.
 *
 */
public class DirectorySelectorWithHistory extends Composite {
	public interface DirectoryChangeListener {
		void setDirectory(); 
	}
	
	private static final String RECENT_FS_DIRECTORIES_STORE_BASE = 
		"com.nokia.sdt.utils.ui.filesystem.browse.history"; //$NON-NLS-1$

	private Combo directoryCombo;
	private Button chooseButton;
	private File dir;
	private java.util.List<String> recentDirectories;

	private List<DirectoryChangeListener> listeners;

	private IDialogSettings settings;
	private String recentDirectoriesStoreKey;
	
	/**
	 * Create an instance in the parent.
	 * @param parent parent, which must have a GridLayout.
	 * @param settings the settings to use to restore/save the directory list
	 * @param directoryGroup optional subgroup of directories (so that clients can either share
	 * directory lists or keep private ones), or null for master list
	 */
	public DirectorySelectorWithHistory(final Composite parent, IDialogSettings settings, String directoryGroup) {
		super(parent, SWT.NONE);
		setLayout(new GridLayout(2, false));
		
		this.settings = settings;
		dir = new File("."); //$NON-NLS-1$
		this.recentDirectories = new ArrayList<String>();
		this.listeners = new ArrayList<DirectoryChangeListener>();
		recentDirectoriesStoreKey = RECENT_FS_DIRECTORIES_STORE_BASE;
		if (directoryGroup != null)
			recentDirectoriesStoreKey += "." + directoryGroup; //$NON-NLS-1$
		
		
		directoryCombo = new Combo(this, SWT.BORDER);
		directoryCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		directoryCombo.setText(dir.getAbsolutePath());
		directoryCombo.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				updateCurrentDirectory();
			}
		});
		directoryCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateCurrentDirectory();
			}
		});
		chooseButton = new Button(this, SWT.PUSH);
		chooseButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		chooseButton.setText(Messages.getString("DirectorySelectorWithHistory.BrowseLabel")); //$NON-NLS-1$
		chooseButton.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dlg = new DirectoryDialog(parent.getShell());
				BrowseDialogUtils.initializeFrom(dlg, directoryCombo.getText());
				String dir = dlg.open();
				if (dir != null) {
					directoryCombo.setText(dir);
					updateCurrentDirectory();
				}
			}
			
		});

		restoreDialogSettings();

	}
	
	public void addListener(DirectoryChangeListener listener) {
		if (!listeners.contains(listener))
			listeners.add(listener);
	}
	
	protected void updateCurrentDirectory() {
		File newDir = new File(directoryCombo.getText());
		if (newDir.equals(dir))
			return;
		dir = newDir;
		saveDialogSettings();
		for (DirectoryChangeListener listener : listeners) {
			listener.setDirectory();
		}
	}
	
    private void saveDialogSettings() {
        if (settings != null) {
        	// remove the bld.inf file from the stack if it exists
        	recentDirectories.remove(directoryCombo.getText());
        	
        	// add the selected bld.inf file to the top of the stack
        	recentDirectories.add(0, directoryCombo.getText());
        	
        	// keep the stack at 20 strings
        	if (recentDirectories.size() > 20) {
        		recentDirectories.remove(20);
        	}
        	
            settings.put(recentDirectoriesStoreKey, recentDirectories.toArray(new String[recentDirectories.size()]));
        }
    }

    private void restoreDialogSettings() {
        if (settings != null) {
        	String[] files = settings.getArray(recentDirectoriesStoreKey);
        	if (files != null && files.length > 0) {
        		recentDirectories.addAll(Arrays.asList(files));
            	directoryCombo.setItems(files);
            	directoryCombo.select(0);
            	dir = new File(directoryCombo.getText());
            }
        }
    }

	/**
	 * @return
	 */
	public File getCurrentDirectory() {
		return dir;
	}

}
