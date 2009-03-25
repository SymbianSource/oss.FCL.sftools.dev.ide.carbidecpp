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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.nokia.carbide.cpp.internal.codescanner.CSPlugin;
import com.nokia.carbide.cpp.internal.codescanner.Messages;
import com.nokia.carbide.cpp.internal.codescanner.config.CSConfigSettings;
import com.nokia.carbide.cpp.internal.codescanner.config.CSFileFilter;

/**
 * A class to create and control the CodeScanner File Filters tab page.
 */
public class CSFileFiltersTabPage extends Composite {

	/**
	 * Inner class to handle labels of the file filters table.
	 */
	private class CSFileFiltersLabelProvider extends LabelProvider implements
	ITableLabelProvider {

		/*
		 * (non-Javadoc)
		 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
		 */
		public String getColumnText(Object element, int index) {
			CSFileFilter fileFilter = (CSFileFilter) element;
			return fileFilter.getFilterRE();
		}

		/*
		 * (non-Javadoc)
		 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
		 */
		public Image getColumnImage(Object element, int index) {
			return null;
		}

		/*
		 * (non-Javadoc)
		 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
		 */
		public String getText(Object element) {
			CSFileFilter fileFilter = (CSFileFilter) element;
			return fileFilter.getFilterRE();
		}
	}

	// private members for various controls of this property page
	private Group tableGroup = null;
	private TableViewer fileFiltersTableViewer = null;
	private Button addFileFilterButton = null;
	private Button editFileFilterButton = null;
	private Button removeFileFilterButton = null;
	private Button removeAllButton = null;
	private CSConfigSettings defaultConfigSettings = null;
	private List<CSFileFilter> filterList = null;
	private ViewerSorter filtersSorter = null;

	/**
	 * Create contents of this tab page.
	 * @param parent - the parent composite
	 */
	public CSFileFiltersTabPage(Composite parent) {
		super(parent, SWT.NONE);
		defaultConfigSettings = CSPlugin.getConfigManager().getDefaultConfig();
		filterList = new ArrayList<CSFileFilter>();

		this.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		this.setLayout(gridLayout);

		// table for CodeScanner file filters
		tableGroup = new Group(this, SWT.NONE);
		final GridData tableGridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		tableGridData.heightHint = 300;
		tableGridData.widthHint = 200;
		tableGroup.setText(Messages.getString("FileFiltersTabPage.FileFiltersLabel"));
		tableGroup.setLayoutData(tableGridData);
		tableGroup.setLayout(new GridLayout());

		fileFiltersTableViewer = new TableViewer(tableGroup, SWT.BORDER | SWT.MULTI);
		fileFiltersTableViewer.setLabelProvider(new CSFileFiltersLabelProvider());
		fileFiltersTableViewer.setContentProvider(new ArrayContentProvider());
		filtersSorter = new ViewerSorter();
		fileFiltersTableViewer.setSorter(filtersSorter);
		fileFiltersTableViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				handleEdit();
			}
		});

		Table fileFiltersTable = fileFiltersTableViewer.getTable();
		fileFiltersTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		TableColumn tableColumn = new TableColumn(fileFiltersTable, SWT.LEFT);
		tableColumn.setWidth(200);

		// various buttons for manipulating the file filters
		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		final GridLayout buttonGridLayout = new GridLayout();
		buttonGridLayout.makeColumnsEqualWidth = true;
		composite.setLayout(buttonGridLayout);
		final GridData buttonsGridData = new GridData(SWT.NONE, SWT.NONE, true, false);
		buttonsGridData.widthHint = 80;

		addFileFilterButton = new Button(composite, SWT.NONE);
		addFileFilterButton.setLayoutData(buttonsGridData);
		addFileFilterButton.setText(Messages.getString("FileFiltersTabPage.AddFileFilterLabel"));
		addFileFilterButton.setToolTipText(Messages.getString("FileFiltersTabPage.AddFileFilterMessage"));
		addFileFilterButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				handleAdd();
			}
		});
		
		editFileFilterButton = new Button(composite, SWT.NONE);
		editFileFilterButton.setLayoutData(buttonsGridData);
		editFileFilterButton.setText(Messages.getString("FileFiltersTabPage.EditFileFilterLabel"));
		editFileFilterButton.setToolTipText(Messages.getString("FileFiltersTabPage.EditFileFilterMessage"));
		editFileFilterButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				handleEdit();
			}
		});
		editFileFilterButton.setEnabled(false);
		
		removeFileFilterButton = new Button(composite, SWT.NONE);
		removeFileFilterButton.setLayoutData(buttonsGridData);
		removeFileFilterButton.setText(Messages.getString("FileFiltersTabPage.RemoveFileFilterLabel"));
		removeFileFilterButton.setToolTipText(Messages.getString("FileFiltersTabPage.RemoveFileFilterMessage"));
		removeFileFilterButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				handleRemove();
			}
		});
		removeFileFilterButton.setEnabled(false);
		
		removeAllButton = new Button(composite, SWT.NONE);
		removeAllButton.setLayoutData(buttonsGridData);
		removeAllButton.setText(Messages.getString("FileFiltersTabPage.RemoveAllFileFiltersLabel"));
		removeAllButton.setToolTipText(Messages.getString("FileFiltersTabPage.RemoveAllFileFiltersMessage"));
		removeAllButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				handleRemoveAll();
			}
		});
		
		// enable the edit button only when a single file filter is selected
		fileFiltersTable.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				boolean enableEdit = (fileFiltersTableViewer.getTable().getSelectionCount() == 1);
				editFileFilterButton.setEnabled(enableEdit);
				boolean enableRemove = (fileFiltersTableViewer.getTable().getSelectionCount() > 0);
				removeFileFilterButton.setEnabled(enableRemove);
			}
		});	
	}
	
	/**
	 * Enables the receiver if the argument is true, and disables it otherwise.
	 * @param enabled - the new enable state.
	 */
	public void setEnabled(boolean enabled) {
		boolean fileFilterSelected = (fileFiltersTableViewer.getTable().getSelectionCount() == 1);
		tableGroup.setEnabled(enabled);
		fileFiltersTableViewer.getTable().setEnabled(enabled);
		addFileFilterButton.setEnabled(enabled);
		editFileFilterButton.setEnabled(enabled && fileFilterSelected);
		removeFileFilterButton.setEnabled(enabled);
		removeAllButton.setEnabled(enabled);
	}

	/**
	 * Set the default values of this tab page.
	 */
	public void setDefaults() {
		filterList.clear();
		EList<String> fileFilterList = defaultConfigSettings.getSourceFilters().getExclude();
		fileFiltersTableViewer.getTable().removeAll();
		for (Iterator<String> iterator = fileFilterList.iterator(); iterator.hasNext();) {
			String filterRE = iterator.next();
			CSFileFilter fileFilter = new CSFileFilter(filterRE);
			filterList.add(fileFilter);
		}
		fileFiltersTableViewer.setInput(filterList);
	}

	/**
	 * Retrieve the stored preference settings values of this tab page.
	 */
	public void getStoredPreferenceValues() {
		IPreferenceStore store = CSPlugin.getCSPrefsStore();
		String fileFiltersString = store.getString(CSPreferenceConstants.FILE_FILTERS); 
		if (fileFiltersString == null)
			return;
		StringTokenizer fileFilters = new StringTokenizer(fileFiltersString, CSPreferenceConstants.DELIMETER);
		filterList.clear();
		// populate the file filters table with stored values
		while (fileFilters.hasMoreTokens()) {
			String filterRE = fileFilters.nextToken();
			CSFileFilter fileFilter = new CSFileFilter(filterRE);
			filterList.add(fileFilter);
		}
		fileFiltersTableViewer.setInput(filterList);
	}
	
	/**
	 * Retrieve the stored property settings values of this tab page.
	 */
	public void getStoredPropertyValues(IDialogSettings pageSettings) {
		String fileFiltersString = pageSettings.get(CSPreferenceConstants.FILE_FILTERS); 
		if (fileFiltersString == null)
			return;
		StringTokenizer fileFilters = new StringTokenizer(fileFiltersString, CSPreferenceConstants.DELIMETER);
		filterList.clear();
		// populate the file filters table with stored values
		while (fileFilters.hasMoreTokens()) {
			String filterRE = fileFilters.nextToken();
			CSFileFilter fileFilter = new CSFileFilter(filterRE);
			filterList.add(fileFilter);
		}
		fileFiltersTableViewer.setInput(filterList);
	}

	/**
	 * Set the stored preference settings values of this tab page.
	 */
	public boolean setStoredPreferenceValues(){
		IPreferenceStore store = CSPlugin.getCSPrefsStore();
		String fileFiltersString = "";		
		for (int i = 0; i < filterList.toArray().length; i++) {
			CSFileFilter fileFilter = (CSFileFilter) filterList.toArray()[i];
			String filterRE = fileFilter.getFilterRE();
			if (filterRE != null && filterRE.length() > 0) {
				fileFiltersString += filterRE + CSPreferenceConstants.DELIMETER;
			}
		}
		store.setValue(CSPreferenceConstants.FILE_FILTERS, fileFiltersString);
		return true;
	}

	/**
	 * Set the stored property settings values of this tab page.
	 */
	public boolean setStoredPropertyValues(IDialogSettings pageSettings) {
		String fileFiltersString = "";		
		for (int i = 0; i < filterList.toArray().length; i++) {
			CSFileFilter fileFilter = (CSFileFilter) filterList.toArray()[i];
			String filterRE = fileFilter.getFilterRE();
			if (filterRE != null && filterRE.length() > 0) {
				fileFiltersString += filterRE + CSPreferenceConstants.DELIMETER;
			}
		}
		pageSettings.put(CSPreferenceConstants.FILE_FILTERS, fileFiltersString);
		return true;
	}

	/**
	 * Initialize the stored preference settings values of this tab page.
	 */
	public static void initializePreferenceValues() {
		IPreferenceStore store = CSPlugin.getCSPrefsStore();
		CSConfigSettings configSettings = CSPlugin.getConfigManager().getDefaultConfig();
		String fileFiltersString = "";
		EList<String> fileFilterList = configSettings.getSourceFilters().getExclude();
		for (Iterator<String> iterator = fileFilterList.iterator(); iterator.hasNext();) {
			String filterRE = iterator.next();
			if (filterRE != null && filterRE.length() > 0) {
				fileFiltersString += filterRE + CSPreferenceConstants.DELIMETER;
			}
		}
		store.setDefault(CSPreferenceConstants.FILE_FILTERS, fileFiltersString);
	}

	/**
	 * Initialize the stored property settings values of this tab page.
	 */
	public static void initializePropertyValues(IDialogSettings pageSettings) {
		CSConfigSettings configSettings = CSPlugin.getConfigManager().getDefaultConfig();
		String fileFiltersString = "";
		EList<String> fileFilterList = configSettings.getSourceFilters().getExclude();
		for (Iterator<String> iterator = fileFilterList.iterator(); iterator.hasNext();) {
			String filterRE = iterator.next();
			if (filterRE != null && filterRE.length() > 0) {
				fileFiltersString += filterRE + CSPreferenceConstants.DELIMETER;
			}
		}
		pageSettings.put(CSPreferenceConstants.FILE_FILTERS, fileFiltersString);
	}

	/**
	 * Things to do when user hit the "Add" button.
	 */
	private void handleAdd() {
		CSFileFilterEditDialog dialog = new CSFileFilterEditDialog(getShell(), "Add File Filter", "");
		if (dialog.open() == CSFileFilterEditDialog.OK) {
			String filterRE = dialog.getFileFilter();
			if (filterRE != null && filterRE.length() > 0 && !fileFilterExist(filterRE)) {
				CSFileFilter fileFilter = new CSFileFilter(filterRE);
				filterList.add(fileFilter);
				fileFiltersTableViewer.setInput(filterList);
				fileFiltersTableViewer.refresh(true);
			}
		}
	}

	/**
	 * Things to do when user hit the "Edit" button.
	 */
	private void handleEdit() {
		ISelection selection = fileFiltersTableViewer.getSelection();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ss = (IStructuredSelection) selection;
			Object element = ss.getFirstElement();
			if (element instanceof CSFileFilter) {
				CSFileFilter fileFilter = (CSFileFilter) element;
				if (fileFilter != null) {
					String oldFilterRE = fileFilter.getFilterRE();
					CSFileFilterEditDialog dialog = new CSFileFilterEditDialog(getShell(), "Edit File Filter", oldFilterRE);
					if (dialog.open() == CSFileFilterEditDialog.OK) {
						String newFilterRE = dialog.getFileFilter();
						if (newFilterRE != null && newFilterRE.length() > 0 &&
							!newFilterRE.matches(oldFilterRE) &&
							!fileFilterExist(newFilterRE)) {
							fileFilter.setFilter(newFilterRE);
							fileFiltersTableViewer.refresh(true);
						}
					}		
				}
			}
		}
	}

	/**
	 * Things to do when user hit the "Remove" button.
	 */
	private void handleRemove() {
		ISelection selection = fileFiltersTableViewer.getSelection();
		if (!selection.isEmpty() && selection instanceof IStructuredSelection) {
			filterList.removeAll(((IStructuredSelection)selection).toList());
			fileFiltersTableViewer.setInput(filterList);
			fileFiltersTableViewer.refresh(true);
		}
	}

	/**
	 * Things to do when user hit the "RemoveAll" button.
	 */
	private void handleRemoveAll() {
		filterList.clear();
		fileFiltersTableViewer.setInput(filterList);
		fileFiltersTableViewer.refresh(true);
	}

	/**
	 * Check whether a file filter already exists.
	 */
	private boolean fileFilterExist(String fileFilter) {
		if (filterList.contains(fileFilter)) {
			MessageDialog.openWarning(getShell(), 
				Messages.getString("FileFiltersTabPage.FileFilterExistTitle"), 
				Messages.getString("FileFiltersTabPage.FileFilterExistMessage"));
			return true;
		}
		return false;
	}

}
