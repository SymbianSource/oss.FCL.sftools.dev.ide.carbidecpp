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
package com.nokia.carbide.cpp.internal.project.ui.editors.images;

import com.nokia.carbide.cpp.ui.images.IFileImageModel;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Table that maintains a mapping of images for export and the path
 * they will take on the device.  
 * <p>
 * Note: there are both emulator and target
 * paths involved in actuality, but we support the drive-specific format
 * (z:\system\data) (which works on both emulator and device) only.
 * 
 */
public class ExportedImageLocationTableComposite extends Composite {

	private Map<IFileImageModel, IPath> mapping;
	private TableViewer tableViewer;
	private List<IFileImageModel> selectedImages;
	private IPath defaultPath;
	private Text defaultPathText;
	private String defaultUID = "00000000"; //$NON-NLS-1$
	private Combo pkgFileDropdown;
	private IPath pkgFile;
	
	public ExportedImageLocationTableComposite(Composite composite, int style) {
		super(composite, style);
		
		setLayout(new GridLayout(1, false));
		
		mapping = new HashMap<IFileImageModel, IPath>();
		defaultPath = new Path(""); //$NON-NLS-1$

		Composite labelAndDropdown = new Composite(this, SWT.NONE);
		labelAndDropdown.setLayout(new GridLayout(3, false));
		labelAndDropdown.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		
		Label tableLabel = new Label(labelAndDropdown, SWT.NONE);
		tableLabel.setText(Messages.getString("ExportedImageLocationTableComposite.MappingExportedFilesLabel")); //$NON-NLS-1$
		tableLabel.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false));
		
		Label pkgFileLabel = new Label(labelAndDropdown, SWT.NONE);
		pkgFileLabel.setText("Target PKG File:");
		pkgFileLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false));
		
		pkgFileDropdown = new Combo(labelAndDropdown, SWT.READ_ONLY);
		pkgFileDropdown.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		pkgFileDropdown.setItems(new String[0]);
		pkgFile = null;
		
		pkgFileDropdown.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String text = pkgFileDropdown.getText();
				if (text.length() == 0) {
					pkgFile = null;
					tableViewer.getTable().setEnabled(false);
				} else {
					pkgFile = new Path(text);
					tableViewer.getTable().setEnabled(true);
				}
			}
		});
		
		tableViewer = createTableViewer(this);
		tableViewer.getTable().setLayoutData(new GridData(
				SWT.FILL, SWT.FILL, true, true
				));

		Composite defaultPathComposite = new Composite(this, SWT.NONE);
		defaultPathComposite.setLayout(new GridLayout(2, false));
		defaultPathComposite.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		
		Label defaultPathLabel = new Label(defaultPathComposite, SWT.NONE);
		defaultPathLabel.setText(Messages.getString("ExportedImageLocationTableComposite.DefaultTargetPathLabel")); //$NON-NLS-1$
		defaultPathLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));

		defaultPathText = new Text(defaultPathComposite, SWT.BORDER);
		defaultPathText.setToolTipText("This entry controls the default target path (on emulator or device)\nfor each newly selected file.\nThe tokens '${UID3}' may be used to automatically\nsubstitute the first project MMP file's\nUID3 setting into the path."); 
		defaultPathText.setText(defaultPath.toOSString());
		defaultPathText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		defaultPathText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				defaultPath = new Path(defaultPathText.getText());
			}
		});
		
	}

	private TableViewer createTableViewer(Composite parent) {
		TableViewer tableViewer = new TableViewer(parent, SWT.FLAT + SWT.BORDER + SWT.FULL_SELECTION);
		
		final Table table = tableViewer.getTable();
		table.setToolTipText("For each image selected above,\nthere is one row in this table which shows\nwhere the file will be exported to the device.\n\nEdit the target path and/or filename as needed.\n\nYou may clear the target path to revert to the default.");
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.minimumHeight = 150;
		table.setLayoutData(gridData);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableViewerColumn column = new TableViewerColumn(tableViewer, SWT.LEFT);
		column.getColumn().setResizable(true);
		column.getColumn().setText(Messages.getString("ExportedImageLocationTableComposite.ImageFileNameColumnHeader")); //$NON-NLS-1$
		
		column = new TableViewerColumn(tableViewer, SWT.LEFT);
		column.getColumn().setResizable(true);
		column.getColumn().setText(Messages.getString("ExportedImageLocationTableComposite.TargetPathColumnHeader"));  //$NON-NLS-1$
		column.setEditingSupport(new EditingSupport(tableViewer) {

			CellEditor editor = new TextCellEditor(table);
			@Override
			protected boolean canEdit(Object element) {
				return true;
			}

			@Override
			protected CellEditor getCellEditor(Object element) {
				return editor;
			}

			@Override
			protected Object getValue(Object element) {
				return ((Map.Entry<IFileImageModel, IPath>) element).getValue().removeLastSegments(1).toOSString();
			}

			@Override
			protected void setValue(Object element, Object value) {
				Map.Entry<IFileImageModel, IPath> entry = (Entry<IFileImageModel, IPath>) element;
				IPath origPath = new Path(entry.getValue().toString());
				String newPath = value.toString();
				if (newPath.length() == 0) {
					newPath = getExpandedDefaultPath().toOSString();
				}
				entry.setValue(new Path(newPath).append(origPath.lastSegment()));
				getViewer().update(element, null);
			}
			
		});

		column = new TableViewerColumn(tableViewer, SWT.LEFT);
		column.getColumn().setResizable(true);
		column.getColumn().setText(Messages.getString("ExportedImageLocationTableComposite.TargetFilenameColumnHeader"));  //$NON-NLS-1$
		column.setEditingSupport(new EditingSupport(tableViewer) {

			CellEditor editor = new TextCellEditor(table);
			@Override
			protected boolean canEdit(Object element) {
				return true;
			}

			@Override
			protected CellEditor getCellEditor(Object element) {
				return editor;
			}

			@Override
			protected Object getValue(Object element) {
				return ((Map.Entry<IFileImageModel, IPath>) element).getValue().lastSegment();
			}

			@Override
			protected void setValue(Object element, Object value) {
				Map.Entry<IFileImageModel, IPath> entry = (Entry<IFileImageModel, IPath>) element;
				IPath origPath = entry.getValue().removeLastSegments(1);
				String fileName = value.toString();
				if (fileName.length() == 0) {
					fileName = entry.getValue().lastSegment();
				}
				entry.setValue(origPath.append(fileName));
				getViewer().update(element, null);
			}
			
		});
		
		tableViewer.setContentProvider(new IStructuredContentProvider() {
	
			public Object[] getElements(Object inputElement) {
				return mapping.entrySet().toArray();
			}
	
			public void dispose() {
			}
	
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
				viewer.refresh();
			}
			
		});
		tableViewer.setLabelProvider(new ITableLabelProvider() {
	
			public Image getColumnImage(Object element, int columnIndex) {
				return null;
			}
	
			public String getColumnText(Object element, int columnIndex) {
				Map.Entry<IFileImageModel, IPath> entry = (Entry<IFileImageModel, IPath>) element;
				if (columnIndex == 0)
					return entry.getKey().getSourcePath().toOSString();
				else if (columnIndex == 1)
					return entry.getValue().removeLastSegments(1).toOSString();
				else if (columnIndex == 2)
					return entry.getValue().lastSegment();
				else
					return null;
			}
	
			public void dispose() {
				
			}
	
			public boolean isLabelProperty(Object element, String property) {
				return true;
			}
	
			public void addListener(ILabelProviderListener listener) {
			}
	
			public void removeListener(ILabelProviderListener listener) {
			}
			
		});
		
		tableViewer.setFilters(new ViewerFilter[] {
			new ViewerFilter() {

				@Override
				public boolean select(Viewer viewer, Object parentElement,
						Object element) {
					Map.Entry<IFileImageModel, IPath> entry = (Entry<IFileImageModel, IPath>) element;
					return selectedImages.contains(entry.getKey());
				}
				
			}
		});
		tableViewer.setInput(mapping);
	
		TableLayout tableLayout = new TableLayout();
		table.setLayout(tableLayout);
	
		tableLayout.addColumnData(new ColumnWeightData(100));
		tableLayout.addColumnData(new ColumnWeightData(100));
		tableLayout.addColumnData(new ColumnWeightData(100));
		return tableViewer; 
	}

	/**
	 * Resynchronize the table with this list of selected file image models
	 * @param selectedImages
	 */
	public void resync(List<IFileImageModel> selectedImages) {
		IPath expandedDefaultPath = getExpandedDefaultPath();
		this.selectedImages = selectedImages;
		for (IFileImageModel model : selectedImages) {
			if (!mapping.containsKey(model)) {
				mapping.put(model, expandedDefaultPath.append(model.getSourceLocation().lastSegment()));
			}
		}
		tableViewer.refresh();
	}
	
	/**
	 * Get the default path with the UID expanded
	 * @return
	 */
	private IPath getExpandedDefaultPath() {
		return new Path(defaultPath.toOSString().replaceAll("\\$\\{UID3\\}", defaultUID)); //$NON-NLS-1$
	}

	public Map<IFileImageModel, IPath> getMappings() {
		return mapping;
	}
	
	public IPath getDefaultExportPath() {
		return defaultPath;
	}

	/**
	 * @param path
	 */
	public void setDefaultExportPath(IPath path) {
		this.defaultPath = path;
		defaultPathText.setText(path.toOSString());
	}
	
	public void setDefaultUID(String uid) {
		if (uid == null)
			return;
		if (uid.toLowerCase().startsWith("0x")) { //$NON-NLS-1$
			uid = uid.substring(2);
		}
		this.defaultUID = uid;
	}
	
	public void setPkgFileSelection(IPath[] pkgFiles) {
		String[] pkgFileNames = new String[pkgFiles.length];
		for (int i = 0; i < pkgFiles.length; i++) {
			pkgFileNames[i] = pkgFiles[i].toOSString();
		}
		pkgFileDropdown.setItems(pkgFileNames);
		if (pkgFiles.length > 0) {
			pkgFile = pkgFiles[0];
			pkgFileDropdown.setText(pkgFile.toOSString());
		}
		else
			pkgFile = null;
	}
	
	/** Get the project-relative path; may be <code>null</code> */
	public IPath getTargetPkgFile() {
		return pkgFile;
	}
}
