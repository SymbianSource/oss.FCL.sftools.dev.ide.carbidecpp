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
package com.nokia.carbide.cdt.internal.api.builder.ui;

import com.nokia.carbide.cdt.builder.EpocEngineHelper;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IExtension;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.ListenerList;

import org.eclipse.core.runtime.*;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.List;

/*
 * UIDs:
 * table
 * buildOrderColumn
 * fileNameColumn
 * locationColumn
 * selectAllButton
 * deselectAllButton
 * excludeExtensionMakefilesCheckbox
 * excludeTestComponentsCheckbox
 */
/**
 *
 */
public class MMPSelectionUI extends Composite implements ISelectionProvider {

	private static final String AUTOTEST_UID = ".uid"; //$NON-NLS-1$
	private static int MMP_SELECTION_THRESHOLD = 3;

	public final class FileInfo {
    	private int ordinal;
    	private boolean isTest;
    	private boolean isMMP;
    	private String fileName;
    	private String location;
    	private boolean checked;
		private IPath path;
    	
    	private FileInfo() {}
    	
		private FileInfo(IPath path, int ordinal, boolean isTest) {
			this.path = path;
			this.ordinal = ordinal;
    		this.isTest = isTest;
			fileName = path.lastSegment();
			isMMP = "mmp".equalsIgnoreCase(new Path(fileName).getFileExtension()); //$NON-NLS-1$
			String pathString = path.toString();
			location = pathString.substring(0, pathString.length() - fileName.length());
    	}	

    	public int getOrdinal() {
    		return ordinal;
    	}
    	
    	public boolean isTest() {
    		return isTest;
    	}
    	
    	public boolean isMMP() {
    		return isMMP;
    	}
    	
    	public String getFileName() {
    		return fileName;
    	}
    	
    	public String getLocation() {
			return location;
    	}
    	
    	public boolean isChecked() {
			return checked;
		}

		public void setChecked(boolean checked) {
			this.checked = checked;
		}

		/**
		 * Returns whether this info corresponds to path
		 * @param path IPath
		 * @return boolean
		 */
		public boolean hasPath(IPath path) {
			// since this path is absolute, all relative paths should end with the same segments
			IPath testPath = this.path.removeFirstSegments(this.path.segmentCount() - path.segmentCount());
			return Arrays.asList(testPath.segments()).equals(Arrays.asList(path.segments()));
		}

		@Override
		public String toString() {
			return getFileName();
		}
		
    }
    
	private final class LabelProvider implements ITableLabelProvider {
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			FileInfo info = (FileInfo) element;
			switch (columnIndex) {
			case BUILD_ORDER_COLUMN:
				String suffix = info.isTest() ? " " + ICarbideProjectInfo.TEST_COMPONENT_LABEL : ""; 
				return Integer.toString(info.getOrdinal()) + suffix;
			case FILENAME_COLUMN:
				return info.getFileName();
			case LOCATION_COLUMN:
				return info.getLocation();
			}
			return null;
		}

		public void addListener(ILabelProviderListener listener) {
		}

		public void dispose() {
		}

		public boolean isLabelProperty(Object element, String property) {
			return false;
		}

		public void removeListener(ILabelProviderListener listener) {
		}
	}

	private static final int BUILD_ORDER_COLUMN = 0;
	private static final int FILENAME_COLUMN = 1;
	private static final int LOCATION_COLUMN = 2;
	
	private CheckboxTableViewer viewer;
	private Button selectAllButton;
	private Button deselectAllButton;
	private Button excludeExtensionMakefilesCheckbox;
	private Button excludeTestComponentsCheckbox;
	private ListenerList<ISelectionChangedListener> listeners;
	private List<ISymbianBuildContext> buildConfigs;
	private IPath bldInfFile;
	private final IRunnableContext runnableContext;
    private List<FileInfo> data = Collections.emptyList();
    private boolean hasUnnamedProjectExtensions;
	private ViewerFilter testFileFilter;
	private ViewerFilter extMakFileFilter;
	private int lastSortColumn = BUILD_ORDER_COLUMN; // default
	private int sortDirection = 1; // default
	private boolean useSBSv2Builder;

	/**
	 * Create the composite
	 * @param parent
	 * @param style
	 */
	public MMPSelectionUI(Composite parent, int style, IRunnableContext runnableContext) {
		super(parent, style);
		this.runnableContext = runnableContext;
		
		// layout self
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayout.marginBottom = 5;
		gridLayout.marginRight = 10;
		gridLayout.marginLeft = 10;
		setLayout(gridLayout);

		// viewer
		viewer = CheckboxTableViewer.newCheckList(this, SWT.BORDER | SWT.SINGLE | SWT.FULL_SELECTION);
		viewer.setContentProvider(new ArrayContentProvider());
		viewer.setLabelProvider(new LabelProvider());
		Table table = viewer.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		table.setData(AUTOTEST_UID, "table"); //$NON-NLS-1$
		
		// build order column
		TableColumn buildOrderColumn = new TableColumn(table, SWT.NONE); // alignment ignored when checkboxes in column
		buildOrderColumn.setText(Messages.getString("MMPSelectionUI.BuildOrderColumnLabel")); //$NON-NLS-1$
		buildOrderColumn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateSortDirection(BUILD_ORDER_COLUMN);
				ViewerComparator comparator = getViewerComparator(BUILD_ORDER_COLUMN);
				viewer.setComparator(comparator);
				setColumnSorting((TableColumn) e.getSource(), sortDirection);
			}
		});
		buildOrderColumn.setData(AUTOTEST_UID, "buildOrderColumn"); //$NON-NLS-1$
		
		// file name column
		TableColumn fileNameColumn = new TableColumn(table, SWT.LEFT);
		fileNameColumn.setText(Messages.getString("MMPSelectionUI.FileNameColumnLabel")); //$NON-NLS-1$
		fileNameColumn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateSortDirection(FILENAME_COLUMN);
				ViewerComparator comparator = getViewerComparator(FILENAME_COLUMN);
				viewer.setComparator(comparator);
				setColumnSorting((TableColumn) e.getSource(), sortDirection);
			}
		});
		fileNameColumn.setData(AUTOTEST_UID, "fileNameColumn"); //$NON-NLS-1$
		
		// location column
		final TableColumn locationColumn = new TableColumn(table, SWT.LEFT);
		locationColumn.setText(Messages.getString("MMPSelectionUI.LocationColumnLabel")); //$NON-NLS-1$
		locationColumn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateSortDirection(LOCATION_COLUMN);
				ViewerComparator comparator = getViewerComparator(LOCATION_COLUMN);
				viewer.setComparator(comparator);
				setColumnSorting((TableColumn) e.getSource(), sortDirection);
			}
		});
		locationColumn.setData(AUTOTEST_UID, "locationColumn"); //$NON-NLS-1$
		
		setColumnSorting(buildOrderColumn, sortDirection);
		table.setHeaderVisible(true);
		
		// listen to checks
		viewer.addCheckStateListener(new ICheckStateListener() {
			public void checkStateChanged(CheckStateChangedEvent event) {
				FileInfo info = (FileInfo) event.getElement();
				info.setChecked(event.getChecked());
				fireSelectionChanged();
			}
		});
		
		// listen to double clicks
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				for (Iterator iterator = selection.iterator(); iterator.hasNext();) {
					FileInfo info = (FileInfo) iterator.next();
					info.setChecked(!info.isChecked());
					viewer.setChecked(info, info.isChecked());
					fireSelectionChanged();
				}
			}
		});

		// select all/deselect all buttons
		final Composite composite = new Composite(this, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false));
		composite.setLayout(new GridLayout());

		selectAllButton = new Button(composite, SWT.NONE);
		selectAllButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		selectAllButton.setText(Messages.getString("MMPSelectionUI.SelectAllLabel")); //$NON-NLS-1$
		selectAllButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setAllChecked(true);
			}
		});
		selectAllButton.setData(AUTOTEST_UID, "selectAllButton"); //$NON-NLS-1$
		deselectAllButton = new Button(composite, SWT.NONE);
		deselectAllButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		deselectAllButton.setText(Messages.getString("MMPSelectionUI.DeselectAllLabel")); //$NON-NLS-1$
		deselectAllButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setAllChecked(false);
			}
		});
		deselectAllButton.setData(AUTOTEST_UID, "deselectAllButton"); //$NON-NLS-1$
		
		// exclude extension makefiles check box
		excludeExtensionMakefilesCheckbox = new Button(this, SWT.CHECK);
		excludeExtensionMakefilesCheckbox.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		excludeExtensionMakefilesCheckbox.setText(Messages.getString("MMPSelectionUI.ExcludeExtMakefilesLabel")); //$NON-NLS-1$
		excludeExtensionMakefilesCheckbox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean exclude = ((Button) e.getSource()).getSelection();
				if (extMakFileFilter == null) {
					extMakFileFilter = new ViewerFilter() {
						@Override
						public boolean select(Viewer viewer, Object parentElement, Object element) {
							FileInfo info = (FileInfo) element;
							boolean select = info.isMMP();
							if (!select)
								info.setChecked(false);
							return select;
						}
					};
				}
				if (exclude) {
					viewer.addFilter(extMakFileFilter);
					fireSelectionChanged();
				} 
				else {
					viewer.removeFilter(extMakFileFilter);
				}
				updateCheckedStateFromViewer();
			}
		});
		excludeExtensionMakefilesCheckbox.setData(AUTOTEST_UID, "excludeExtensionMakefilesCheckbox"); //$NON-NLS-1$
		
		// exclude test components checkbox
		excludeTestComponentsCheckbox = new Button(this, SWT.CHECK);
		excludeTestComponentsCheckbox.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		excludeTestComponentsCheckbox.setText(Messages.getString("MMPSelectionUI.ExcludeTestCompsLabel")); //$NON-NLS-1$
		excludeTestComponentsCheckbox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean exclude = ((Button) e.getSource()).getSelection();
				if (testFileFilter == null) {
					testFileFilter = new ViewerFilter() {
						@Override
						public boolean select(Viewer viewer, Object parentElement, Object element) {
							FileInfo info = (FileInfo) element;
							boolean select = !info.isTest();
							if (!select)
								info.setChecked(false);
							return select;
						}
					};
				}
				if (exclude) {
					viewer.addFilter(testFileFilter);
					fireSelectionChanged();
				} 
				else {
					viewer.removeFilter(testFileFilter);
				}
				updateCheckedStateFromViewer();
			}
		});
		excludeTestComponentsCheckbox.setData(AUTOTEST_UID, "excludeTestComponentsCheckbox"); //$NON-NLS-1$
	}
	
	private void updateCheckedStateFromViewer() {
		for (FileInfo info : data) {
			info.setChecked(viewer.getChecked(info));
		}
	}

	/**
	 * Sets the data for this UI from bld.inf file and list of build configs
	 * @param bldInfFile IPath
	 * @param buildConfigs List<ISymbianBuildContext>
	 */
	public void setBldInfFile(final IPath bldInfFile, final List<ISymbianBuildContext> buildContexts, final boolean useSBSv2Builder) {
		if (bldInfFile.equals(this.bldInfFile) && buildConfigs.equals(this.buildConfigs))
			return;
		
		this.bldInfFile = bldInfFile;
		this.buildConfigs = buildContexts;
		this.useSBSv2Builder = useSBSv2Builder;

		try {
			runnableContext.run(true, true, new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) {
					data = new ArrayList<FileInfo>();
					List<IPath> normalMakMakeList = new ArrayList<IPath>();
					List<IPath> testMakMakeList = new ArrayList<IPath>();
					EpocEngineHelper.getMakMakeFiles(bldInfFile, buildConfigs, 
							normalMakMakeList, testMakMakeList, monitor);
					int i = 0;
			    	for (IPath currPath : normalMakMakeList) {
						data.add(new FileInfo(currPath, ++i, false));
			    	}
			    	for (IPath currPath : testMakMakeList) {
						data.add(new FileInfo(currPath, ++i, true));
			    	}

			    	// named extensions are only supported in SBSv2
			    	if (useSBSv2Builder) {
				    	List<IExtension> normalNamedExtensionsList = new ArrayList<IExtension>();
						List<IExtension> testNamedExtensionsList = new ArrayList<IExtension>();
						EpocEngineHelper.getNamedExtensions(bldInfFile, buildConfigs,
								normalNamedExtensionsList, testNamedExtensionsList, monitor);
						
				    	for (IExtension extension : normalNamedExtensionsList) {
							data.add(new FileInfo(new Path(extension.getName()), ++i, false));
				    	}
				    	for (IExtension extension : testNamedExtensionsList) {
							data.add(new FileInfo(new Path(extension.getName()), ++i, true));
				    	}
			    	}
					
			    	hasUnnamedProjectExtensions = EpocEngineHelper.hasUnnamedExtensions(bldInfFile, buildConfigs, monitor);
				}
			});
		} catch (InvocationTargetException e) {
		} catch (InterruptedException e) {
			// Nothing to do if the user interrupts.
		}
		viewer.setInput(data);
		TableColumn[] columns = viewer.getTable().getColumns();
		for (TableColumn tableColumn : columns) {
			tableColumn.pack();
		}
		if (data.size() > MMP_SELECTION_THRESHOLD) {
			setAllChecked(false);
		}
		else {
			setAllChecked(true);
		}
	}
	
    /**
     * Sets checked state of all items
     * @param state boolean
     */
    public void setAllChecked(boolean state) {
    	for (TableItem	item : viewer.getTable().getItems()) { // only want filtered items
    		item.setChecked(state);
    		FileInfo info = (FileInfo) item.getData();
    		info.setChecked(state);
		}
		fireSelectionChanged();
    }

	private ViewerComparator getViewerComparator(int column) {
		switch (column) {
			case BUILD_ORDER_COLUMN:
				return new ViewerComparator() {
					public int compare(Viewer viewer, Object e1, Object e2) {
						FileInfo info1 = (FileInfo) e1;
						FileInfo info2 = (FileInfo) e2;
						return (info1.getOrdinal() - info2.getOrdinal()) * sortDirection ;
					}
				};
			case FILENAME_COLUMN:
				return new ViewerComparator() {
					public int compare(Viewer viewer, Object e1, Object e2) {
						FileInfo info1 = (FileInfo) e1;
						FileInfo info2 = (FileInfo) e2;
						return info1.getFileName().compareToIgnoreCase(info2.getFileName()) * sortDirection ;
					}
				};
			case LOCATION_COLUMN:
				return new ViewerComparator() {
					public int compare(Viewer viewer, Object e1, Object e2) {
						FileInfo info1 = (FileInfo) e1;
						FileInfo info2 = (FileInfo) e2;
						return info1.getLocation().compareToIgnoreCase(info2.getLocation()) * sortDirection ;
					}
				};
				
		}
		return null;
	}

	private void updateSortDirection(int column) {
		if (lastSortColumn == column) {
			sortDirection *= -1;
		}
		else {
			sortDirection = 1;
			lastSortColumn = column;
		}
	}

	private void setColumnSorting(TableColumn column, int order) {
		Table table = viewer.getTable();
		table.setSortColumn(column);
		table.setSortDirection(order > 0 ? SWT.UP : SWT.DOWN);
		updateCheckedStateFromViewer();
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	/**
	 * Returns structured selection of FileInfos that can be queried for isChecked()
	 * @see org.eclipse.jface.viewers.ISelectionProvider#getSelection()
	 * @return ISelection
	 */
	public ISelection getSelection() {
		return new StructuredSelection(data);
	}

	/**
	 * @see org.eclipse.jface.viewers.ISelectionProvider#addSelectionChangedListener(org.eclipse.jface.viewers.ISelectionChangedListener)
	 */
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		if (listeners == null)
			listeners = new ListenerList<ISelectionChangedListener>();
		listeners.add(listener);
	}

	/**
	 * @see org.eclipse.jface.viewers.ISelectionProvider#removeSelectionChangedListener(org.eclipse.jface.viewers.ISelectionChangedListener)
	 */
	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		if (listeners != null)
			listeners.remove(listener);
	}
	
	private void fireSelectionChanged() {
		if (listeners != null) {
			for (ISelectionChangedListener listener : listeners) {
				SelectionChangedEvent event = new SelectionChangedEvent(this, getSelection());
				listener.selectionChanged(event);
			}
		}
	}

	/**
	 * Expects structured selection FileInfos from which will update checked state
	 * FileInfos must be those received from getSelection()
	 * @see org.eclipse.jface.viewers.ISelectionProvider#getSelection()
	 * @see org.eclipse.jface.viewers.ISelectionProvider#setSelection(org.eclipse.jface.viewers.ISelection)
	 */
	public void setSelection(ISelection selection) {
		Check.checkState(selection instanceof IStructuredSelection);
		IStructuredSelection structuredSelection = (IStructuredSelection) selection;
		for (Iterator iterator = structuredSelection.iterator(); iterator.hasNext();) {
			Object element = iterator.next();
			Check.checkContract(element instanceof FileInfo);
			FileInfo info = (FileInfo) element;
			viewer.setChecked(info, info.isChecked());
		}
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		viewer.getTable().setEnabled(enabled);
		selectAllButton.setEnabled(enabled);
		deselectAllButton.setEnabled(enabled);
		excludeExtensionMakefilesCheckbox.setEnabled(enabled);
		excludeTestComponentsCheckbox.setEnabled(enabled);
	}
	
	/**
	 * @return true if all items checked
	 */
	public boolean isCheckedAll() {
		for (FileInfo info : data) {
			if (!info.isChecked())
				return false;
		}
		
		return true;
	}
	
	/**
	 * @return true if all items unchecked
	 */
	public boolean isUncheckedAll() {
		for (FileInfo info : data) {
			if (info.isChecked())
				return false;
		}
		
		return true;
	}
	
	public String getExtensionsWarningMessage() {
		if (hasUnnamedProjectExtensions && !isCheckedAll()) {
			if (useSBSv2Builder) {
				return Messages.getString("MMPSelectionUI.prjExtensionsWarningSBSv2"); //$NON-NLS-1$
			} else {
				return Messages.getString("MMPSelectionUI.prjExtensionsWarningSBSv1"); //$NON-NLS-1$
			}
		}
		return null;
	}

	/**
	 * Set the MMP selection threshold. When number of MMPs <= threshold, all MMPs are selected by default.
	 * When number of MMPs > threshold, no MMP is selected by default.
	 * @param threshold - threshold for MMP selection
	 */
	public static void setMMPSelectionThreshold(int threshold) {
		MMP_SELECTION_THRESHOLD = threshold;
	}

}
