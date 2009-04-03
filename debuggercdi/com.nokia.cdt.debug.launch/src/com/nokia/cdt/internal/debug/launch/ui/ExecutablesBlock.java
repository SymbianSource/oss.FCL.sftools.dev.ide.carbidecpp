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
package com.nokia.cdt.internal.debug.launch.ui;

import org.eclipse.cdt.launch.AbstractCLaunchDelegate;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;

import java.util.*;
import java.util.List;

/**
 * A composite that displays executables in a table. Executables can be enabled
 * and disabled for debugging.
 */
public class ExecutablesBlock {

	ExecutablesTab fLaunchTab;
	/**
	 * This block's control
	 */
	private Composite fControl;

	/**
	 * Files being displayed
	 */
	private List<ExeFileToDebug> fExeFiles = new ArrayList<ExeFileToDebug>();

	/**
	 * The main list control
	 */
	private CheckboxTableViewer fExeFileList;
	private ExeFileToDebug fMainExecutable;
	private boolean wantsMainExecutable;
	
	private final static Color GRAY = new Color(null, 128, 128, 128);

	/**
	 * Label provider for executables table
	 */
	class ExeFilesLabelProvider extends LabelProvider implements IColorProvider {
		
		@Override
		public String getText(Object element) {
			if (element instanceof ExeFileToDebug) {
				ExeFileToDebug file = (ExeFileToDebug) element;
				return file.getExePath();
			}
			return element.toString();
		}

		public Color getBackground(Object element) {
			return null;
		}

		public Color getForeground(Object element) {
			if (element == fMainExecutable)
				return GRAY;
			return null;
		}
		
	}

	ExecutablesBlock(ExecutablesTab launchTab, boolean wantsMainExecutable) {
		fLaunchTab = launchTab;
		this.wantsMainExecutable = wantsMainExecutable;
	}

	/**
	 * Creates this block's control in the given control.
	 * 
	 * @param ancestor
	 *            containing control
	 */
	public void createControl(Composite comp) {
		fControl = comp;

		GridData data;

		Table table = new Table(comp, SWT.CHECK | SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION);
		data = new GridData(GridData.FILL, GridData.FILL, true, true, 1, 1);
		data.horizontalIndent = 10;
		table.setLayoutData(data);

		table.setHeaderVisible(false);
		table.setLinesVisible(false);
		table.setToolTipText(Messages.getString("ExecutablesTab.TableToolTip")); //$NON-NLS-1$

		TableLayout tableLayout = new TableLayout();
		table.setLayout(tableLayout);

		fExeFileList = new CheckboxTableViewer(table);
		fExeFileList.setLabelProvider(new ExeFilesLabelProvider());
		fExeFileList.setContentProvider(new ArrayContentProvider());

		// listen to checks
		fExeFileList.addCheckStateListener(new ICheckStateListener() {
			public void checkStateChanged(CheckStateChangedEvent e) {
				ExeFileToDebug file = (ExeFileToDebug) e.getElement();
				if (file == fMainExecutable) {
					fExeFileList.setChecked(file, true);
				}
				else{
					file.setEnabled(e.getChecked());
				}
				if (fExeFileList.getCheckedElements().length == 0)
					fExeFileList.setChecked(fExeFiles.get(0), true);
				fLaunchTab.dataChanged();					
			}
		});
		// listen to double clicks
		fExeFileList.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent e) {
				IStructuredSelection selection = (IStructuredSelection) e.getSelection();
				for (Iterator iterator = selection.iterator(); iterator.hasNext();) {
					ExeFileToDebug file = (ExeFileToDebug) iterator.next();
					if (file != fMainExecutable) {
						fExeFileList.setChecked(file, !fExeFileList.getChecked(file));
					}
				}
				if (fExeFileList.getCheckedElements().length == 0)
					fExeFileList.setChecked(fExeFiles.get(0), true);
				fLaunchTab.dataChanged();					
			}
		});
		
		fExeFileList.setComparator(new ViewerComparator());
	}

	/**
	 * Returns this block's control
	 * 
	 * @return control
	 */
	public Control getControl() {
		return fControl;
	}

	/**
	 * Sets the files to be displayed in this block
	 * 
	 * @param files
	 *            files to be displayed
	 */
	protected void setFiles(List<ExeFileToDebug> files) {
		
		ArrayList<ExeFileToDebug> tempFiles = new ArrayList<ExeFileToDebug>(files);
		fExeFiles.retainAll(tempFiles);
		tempFiles.removeAll(fExeFiles);
		fExeFiles.addAll(tempFiles);
		
		if (fExeFileList.getContentProvider() != null)
		{
			fExeFileList.setInput(fExeFiles);
			fExeFileList.refresh();
			
			String launchExeName = ""; //$NON-NLS-1$
			try {
				launchExeName = AbstractCLaunchDelegate.getProgramName(fLaunchTab.getLaunchConfiguration());
			} catch (CoreException e) {}

			for (int i = 0; i < fExeFiles.size(); i++) {
				ExeFileToDebug file = (ExeFileToDebug) fExeFiles.get(i);
				fExeFileList.setChecked(file, file.getEnabled());
				if (wantsMainExecutable && file.getExePath().equalsIgnoreCase(launchExeName)) {
					fMainExecutable = file;
				}
			}			
		}
	}

	/**
	 * Returns the files currently being displayed in this block
	 * 
	 * @return files currently being displayed in this block
	 */
	public ExeFileToDebug[] getFiles() {
		return (ExeFileToDebug[]) fExeFiles.toArray(new ExeFileToDebug[fExeFiles.size()]);
	}

	protected Shell getShell() {
		return getControl().getShell();
	}

	public void setEnabled(boolean enabled) {
		fExeFileList.getTable().setEnabled(enabled);		
	}
	
	public void setAllChecked(boolean enabled) {
		fExeFileList.setAllChecked(enabled); // set the checked state in the table
		// ensure at least one file is left checked
		ExeFileToDebug fileToBeChecked = fMainExecutable;
		if (fileToBeChecked == null)
			fileToBeChecked = fExeFiles.get(0);
		fExeFileList.setChecked(fileToBeChecked, true);
		// enable or disable all the files - ensuring the one file is still enabled
		for (ExeFileToDebug exeFile : fExeFiles)
			exeFile.setEnabled(enabled);
		fileToBeChecked.setEnabled(true);
		
		fLaunchTab.dataChanged();
	}
}
