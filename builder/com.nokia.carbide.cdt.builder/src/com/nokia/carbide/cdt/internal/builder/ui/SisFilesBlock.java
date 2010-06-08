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
package com.nokia.carbide.cdt.internal.builder.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ISISBuilderInfo;
import com.nokia.carbide.cdt.internal.api.builder.SISBuilderInfo2;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDKFeatures;

/**
 * A composite that displays files in a table. Files can be 
 * added, removed, edited, moved up or down.
 */
public class SisFilesBlock {
	
	IProject project;
	
	/**
	 * This block's control
	 */
	private Composite fControl;
	
	/**
	 * Files being displayed
	 */
	private List<ISISBuilderInfo> fFiles = new ArrayList<ISISBuilderInfo>(); 
	
	/**
	 * The main list control
	 */ 
	private CheckboxTableViewer fFileList;
	
	// Action buttons
	private Button fAddButton;
	private Button fRemoveButton;
	private Button fEditButton;
	private Button fUpButton;
	private Button fDownButton;

	private boolean isEKA2 = false;
	
	private List<Button> sisInfoChangedListeners = new ArrayList<Button>();
	
	/** 
	 * Content provider to show a list of files to be transferred
	 */ 
	class FilesContentProvider implements IStructuredContentProvider {	
	
		public Object[] getElements(Object input) {
			return fFiles.toArray();
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		public void dispose() {
		}
	
	}
	
	/**
	 * Label provider for files to transfer table.
	 */
	class FilesLabelProvider extends LabelProvider implements ITableLabelProvider {

		/**
		 * @see ITableLabelProvider#getColumnText(Object, int)
		 */
		public String getColumnText(Object element, int columnIndex) {
			if (element instanceof ISISBuilderInfo) {
				ISISBuilderInfo file = (ISISBuilderInfo)element;
				switch(columnIndex) {
					case 0:
						return ""; //$NON-NLS-1$
					case 1:
						return ""; //$NON-NLS-1$
					case 2:
						return getSisText(file);
					case 3:
						return getSisxText(file);
				}
			}
			return element.toString();
		}

		/**
		 * @see ITableLabelProvider#getColumnImage(Object, int)
		 */
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}
		
		private String getSisText(ISISBuilderInfo file) {
			IPath unsignedSisPath = file.getUnsignedSISFullPath();
			if (unsignedSisPath != null) {
				return unsignedSisPath.lastSegment();
			}
			return file.getUnsignedSISFileName();
		}

		private String getSisxText(ISISBuilderInfo file) {
			if (file.getSigningType() == ISISBuilderInfo.DONT_SIGN) {
				return ""; //$NON-NLS-1$
			}
			IPath signedSisPath = file.getSignedSISFullPath();
			if (signedSisPath != null) {
				return signedSisPath.lastSegment();
			}
			return file.getSignedSISFileName();				
		}
	}
	
	SisFilesBlock(IProject project) {
		this.project = project;
	}

	/**
	 * Creates this block's control in the given control.
	 * 
	 * @param ancestor containing control
	 */
	public void createControl(Composite ancestor) {
		
		Composite parent = new Composite(ancestor, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		parent.setLayout(layout);
		Font font = ancestor.getFont();
		parent.setFont(font);	
		fControl = parent;	
		
		Table table = new Table(parent, SWT.CHECK | SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		table.setFont(font);
				
		table.setHeaderVisible(true);
		table.setLinesVisible(true);		

		table.setLayout(new TableLayout());

		TableColumn column1 = new TableColumn(table, SWT.NULL);
		column1.setText("Enabled");
		column1.pack();
	
		TableColumn column2 = new TableColumn(table, SWT.NULL);
		column2.setText("Partial upgrade");
		column2.pack();

		TableColumn column3 = new TableColumn(table, SWT.NULL);
		column3.setText("Unsigned SIS file");
		column3.setWidth(200);
	
		TableColumn column4 = new TableColumn(table, SWT.NULL);
		column4.setText("Signed SIS file");
		column4.setWidth(200);
		
		fFileList = new CheckboxTableViewer(table);			
		fFileList.setLabelProvider(new FilesLabelProvider());
		fFileList.setContentProvider(new FilesContentProvider());

		fFileList.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent evt) {
				enableButtons();
			}
		});
		
		fFileList.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent e) {
				if (!fFileList.getSelection().isEmpty()) {
					editFile();
				}
			}
		});
		
		fFileList.addCheckStateListener(new ICheckStateListener() {
			public void checkStateChanged(CheckStateChangedEvent e) {
				ISISBuilderInfo file = (ISISBuilderInfo)e.getElement();
				file.setEnabled(e.getChecked());
			}
		});
		
		table.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent event) {
				if (event.character == SWT.DEL && event.stateMask == 0) {
					removeFiles();
				}
			}
		});	
		
		Composite buttons = new Composite(parent, SWT.NULL);
		buttons.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, false, false));
		buttons.setLayout(new GridLayout());
		buttons.setFont(font);
		
		fAddButton = createPushButton(buttons, "Add");
		fAddButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event evt) {
				addFile();
			}
		});
		
		fEditButton = createPushButton(buttons, "Edit");
		fEditButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event evt) {
				editFile();
			}
		});
		
		fRemoveButton = createPushButton(buttons, "Remove");
		fRemoveButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event evt) {
				removeFiles();
			}
		});

		fUpButton = createPushButton(buttons, "Move Up");
		fUpButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event evt) {
				moveFileUp();
				enableButtons();
			}
		});

		fDownButton = createPushButton(buttons, "Move Down");
		fDownButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event evt) {
				moveFileDown();
				enableButtons();
			}
		});

		enableButtons();
		fAddButton.setEnabled(true);
	}
		
	public void initData(ICarbideBuildConfiguration buildConfig) {
		// don't add the actual elements here
		List<ISISBuilderInfo> listCopy = new ArrayList<ISISBuilderInfo>();
		for (ISISBuilderInfo info : buildConfig.getSISBuilderInfoList()) {
			listCopy.add(new SISBuilderInfo2(info));
		}
		
		isEKA2 = buildConfig.getSDK().getSupportedFeatures().contains(ISymbianSDKFeatures.IS_EKA2);
		
		setFiles(listCopy);

		if (!isEKA2) {
			// hide the partial upgrade column
			fFileList.getTable().getColumn(1).setWidth(0);
		} else {
			fFileList.getTable().getColumn(1).setWidth(90);
		}
	}
	
	public boolean compareConfigurationSettings(ICarbideBuildConfiguration selectedConfig, boolean writeToConfig) {
		
		boolean settingsEqual = true;
		
		List<ISISBuilderInfo> existingSettings = selectedConfig.getSISBuilderInfoList();
		if (existingSettings.size() == fFiles.size()) {
			for (int i=0; i<existingSettings.size(); i++) {
				if (!areSisBuilderInfosEqual(existingSettings.get(i), fFiles.get(i))) {
					settingsEqual = false;
					break;
				}
			}
		} else {
			settingsEqual = false;
		}
		
		if (!settingsEqual && writeToConfig) {
			existingSettings.clear();
			existingSettings.addAll(fFiles);
		}
		
		return settingsEqual;
	}
	
	private boolean areSisBuilderInfosEqual(ISISBuilderInfo info1, ISISBuilderInfo info2) {
		if (info1.getPKGFileString().compareTo(info2.getPKGFileString()) != 0) {
			return false;
		}

		if (info1.getUnsignedSISFileName().compareTo(info2.getUnsignedSISFileName()) != 0) {
			return false;
		}

		if (info1.getContentSearchLocation().compareTo(info2.getContentSearchLocation()) != 0) {
			return false;
		}

		if (info1.getAdditionalOptions().compareTo(info2.getAdditionalOptions()) != 0) {
			return false;
		}

		if (info1.getCertificate().compareTo(info2.getCertificate()) != 0) {
			return false;
		}

		if (info1.getKey().compareTo(info2.getKey()) != 0) {
			return false;
		}

		if (info1.getPassword().compareTo(info2.getPassword()) != 0) {
			return false;
		}

		if (info1.getSignedSISFileName().compareTo(info2.getSignedSISFileName()) != 0) {
			return false;
		}

		if (info1.isCreateStubFormat() != info2.isCreateStubFormat()) {
			return false;
		}

		if (info1.getSigningType() != info2.getSigningType()) {
			return false;
		}

		if (info1.isEnabled() != info2.isEnabled()) {
			return false;
		}
		
		if (info1 instanceof SISBuilderInfo2 && info2 instanceof SISBuilderInfo2) {
			if (((SISBuilderInfo2)info1).isPartialUpgrade() != ((SISBuilderInfo2)info2).isPartialUpgrade()) {
				return false;
			}
		}

		return true;
	}

	public void performDefaults() {
		setFiles(new ArrayList<ISISBuilderInfo>(0));
	}

	private void enableButtons() {
		int selectionCount = ((IStructuredSelection)fFileList.getSelection()).size();
		fEditButton.setEnabled(selectionCount == 1);
		fRemoveButton.setEnabled(selectionCount > 0);
		fUpButton.setEnabled(selectionCount == 1);
		fDownButton.setEnabled(selectionCount == 1);

		if (selectionCount == 1) {
			// disable move up/down if the selection is the first/last in the list
			ISISBuilderInfo selected = (ISISBuilderInfo)((IStructuredSelection)fFileList.getSelection()).getFirstElement();
			if (selected.equals(fFiles.get(0))) {
				fUpButton.setEnabled(false);
			}
			if (selected.equals(fFiles.get(fFiles.size()-1))) {
				fDownButton.setEnabled(false);
			}
		}
	}	
	
	protected Button createPushButton(Composite parent, String label) {
		Button button = new Button(parent, SWT.PUSH);
		button.setFont(parent.getFont());
		if (label != null) {
			button.setText(label);
		}
		button.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));	
		return button;
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
	 * @param files files to be displayed
	 */
	protected void setFiles(List<ISISBuilderInfo> files) {
		fFiles.clear();
		fFiles.addAll(files);

		Table table = fFileList.getTable();
		table.removeAll();

		fFileList.setInput(fFiles);
		fFileList.refresh();

		for (ISISBuilderInfo file : fFiles) {
			fFileList.setChecked(file, file.isEnabled());
		}
		
		if (isEKA2) {
			// initialize the cell editors
			for (int i=0; i<fFiles.size(); i++) {
				addPartialUpgradeEditor(table, i);
			}
		}
	}
	
	private void addPartialUpgradeEditor(Table table, int rowIndex) {
		TableItem item = table.getItem(rowIndex);
		ISISBuilderInfo info = fFiles.get(rowIndex);
		
		final Button partialUpgradeCheckbox = new Button(table, SWT.CHECK | SWT.FLAT);
		partialUpgradeCheckbox.setBackground(table.getBackground());
		partialUpgradeCheckbox.pack();
		partialUpgradeCheckbox.setData(info);
		if (info instanceof SISBuilderInfo2) {
			partialUpgradeCheckbox.setSelection(((SISBuilderInfo2)info).isPartialUpgrade());
		}

		partialUpgradeCheckbox.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			public void widgetSelected(SelectionEvent e) {
				Object o = e.getSource();
				if (o != null && o instanceof Button) {
					Object data = ((Button)o).getData();
					if (data != null && data instanceof ISISBuilderInfo) {
						ISISBuilderInfo info = (ISISBuilderInfo)data;
						if (info instanceof SISBuilderInfo2) {
							SISBuilderInfo2 info2 = (SISBuilderInfo2)info;
							info2.setPartialUpgrade(!info2.isPartialUpgrade());
						}
					}
				}
			}
			
		});
		
		addSisInfoChangedListener(partialUpgradeCheckbox);
		
		partialUpgradeCheckbox.addDisposeListener(new DisposeListener() {

			public void widgetDisposed(DisposeEvent e) {
				removeSisInfoChangedListener(partialUpgradeCheckbox);
			}
			
		});
		
		final TableEditor partialUpgradeCellEditor = new TableEditor(table);
		partialUpgradeCellEditor.grabHorizontal = true;
		partialUpgradeCellEditor.setEditor(partialUpgradeCheckbox, item, 1);
		
		item.addDisposeListener(new DisposeListener() {

			public void widgetDisposed(DisposeEvent e) {
				partialUpgradeCellEditor.dispose();
				partialUpgradeCheckbox.dispose();
			}
			
		});
	}
	
	/**
	 * Returns the files currently being displayed in this block
	 * 
	 * @return files currently being displayed in this block
	 */
	public ISISBuilderInfo[] getFiles() {
		return (ISISBuilderInfo[])fFiles.toArray(new ISISBuilderInfo[fFiles.size()]);
	}
	
	/**
	 * Bring up a dialog that lets the user create a new file to transfer.
	 */
	private void addFile() {
		SISBuilderInfo2 file = new SISBuilderInfo2(project);
		if (isEKA2) {
			file.setSigningType(ISISBuilderInfo.SELF_SIGN);
		}

		AddEditSisFileToBuildDialog dialog = new AddEditSisFileToBuildDialog(getShell(), file, project, isEKA2);
		dialog.setTitle("SIS Properties");
		if (dialog.open() != Window.OK) {
			return;
		}
		fFiles.add(file);
		fFileList.refresh();
		fFileList.setChecked(file, file.isEnabled());
		
		if (isEKA2) {
			addPartialUpgradeEditor(fFileList.getTable(), fFiles.size() - 1);
		}
	}
		
	private void editFile() {
		IStructuredSelection selection = (IStructuredSelection)fFileList.getSelection();
		ISISBuilderInfo file = (ISISBuilderInfo)selection.getFirstElement();
		if (file == null) {
			return;
		}
		AddEditSisFileToBuildDialog dialog = new AddEditSisFileToBuildDialog(getShell(), file, project, isEKA2);
		dialog.setTitle("SIS Properties");
		if (dialog.open() != Window.OK) {
			return;
		}
		fFileList.refresh(file);
		
		// update table checkbox
		if (file instanceof SISBuilderInfo2) {
			SISBuilderInfo2 sisInfo2 = (SISBuilderInfo2)file;
			for (Button button : sisInfoChangedListeners) {
				Object data = button.getData();
				if (data != null && data instanceof SISBuilderInfo2 && data.equals(sisInfo2)) {
					button.setSelection(sisInfo2.isPartialUpgrade());
				}
			}
		}
	}
	
	private void removeFiles() {
		IStructuredSelection selection = (IStructuredSelection)fFileList.getSelection();
		ISISBuilderInfo[] files = new ISISBuilderInfo[selection.size()];
		Iterator iter = selection.iterator();
		int i = 0;
		while (iter.hasNext()) {
			files[i] = (ISISBuilderInfo)iter.next();
			i++;
		}
		removeFiles(files);
	}
	
	private void moveFileUp() {
		IStructuredSelection selection = (IStructuredSelection)fFileList.getSelection();
		ISISBuilderInfo file = (ISISBuilderInfo)selection.getFirstElement();
		int i;
		for (i = 0; i < fFiles.size(); i++) {
			if (fFiles.get(i).equals(file)) {
				fFiles.remove(i);
				fFiles.add(i-1, file);
				fFileList.refresh();
				break;
			}
		}
	}

	private void moveFileDown() {
		IStructuredSelection selection = (IStructuredSelection)fFileList.getSelection();
		ISISBuilderInfo file = (ISISBuilderInfo)selection.getFirstElement();
		int i;
		for (i = 0; i < fFiles.size(); i++) {
			if (fFiles.get(i).equals(file)) {
				fFiles.remove(i);
				fFiles.add(i+1, file);
				fFileList.refresh();
				break;
			}
		}
	}
	
	/**
	 * Removes the given files from the table.
	 * 
	 * @param files
	 */
	public void removeFiles(ISISBuilderInfo[] files) {
		for (int i = 0; i < files.length; i++) {
			fFiles.remove(files[i]);
		}
		fFileList.refresh();
	}
	
	protected Shell getShell() {
		return getControl().getShell();
	}
	
	private void addSisInfoChangedListener(Button listener) {
		if (!sisInfoChangedListeners.contains(listener)) {
			sisInfoChangedListeners.add(listener);
		}
	}

	private void removeSisInfoChangedListener(Button listener) {
		sisInfoChangedListeners.remove(listener);
	}
}
