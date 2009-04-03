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
* A generic table viewer tab for Symbian OS view, which is parent for many other tabs such as Processes Tab.
* 
*/
package com.nokia.carbide.cpp.debug.kernelaware.ui;

import org.eclipse.debug.core.DebugException;
import org.eclipse.jface.action.*;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

import com.nokia.carbide.cpp.debug.kernelaware.OSDataManager;
import com.nokia.carbide.cpp.debug.kernelaware.OSObject;
import com.nokia.carbide.cpp.debug.kernelaware.OSObjectChunk;
import com.nokia.carbide.cpp.debug.kernelaware.OSObjectLibrary;
import com.nokia.carbide.cpp.debug.kernelaware.OSObjectProcess;
import com.nokia.carbide.cpp.debug.kernelaware.OSObjectThread;

import cwdbg.EclipseDEConstants;

public class GenericTableTab {

	class MyTableDataProvider implements IStructuredContentProvider {

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
		 */
		public Object[] getElements(Object inputElement) {
			if (inputElement == null)
				return new Object[0];

			if (inputElement instanceof OSDataManager) {
				OSDataManager osDM = (OSDataManager) inputElement;
				try {
					switch (objectType) {
					case EclipseDEConstants.J_OSObjectType_Process:
						return osDM.getProcesses();
					case EclipseDEConstants.J_OSObjectType_Thread:
						return osDM.getThreads();
					case EclipseDEConstants.J_OSObjectType_Chunk:
						return osDM.getChunks();
					case EclipseDEConstants.J_OSObjectType_Library:
						return osDM.getLibraries();
					default:
						assert (false);
						break;
					}
				} catch (DebugException e) {
					e.printStackTrace();
				}
			}

			return new Object[0];
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
		 */
		public void dispose() {
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
		 *      java.lang.Object, java.lang.Object)
		 */
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			if (newInput == null)
				osDataManager = null;
			else if (newInput instanceof OSDataManager)
				osDataManager = (OSDataManager) newInput;
		}

	}

	/**
	 * Label provider for table.
	 */
	class MyTableLabelProvider extends OSViewLabelProvider implements
			ITableLabelProvider {

		MyTableLabelProvider(Viewer viewer) {
			super(viewer);
		}

		/**
		 * @see ITableLabelProvider#getColumnText(Object, int)
		 */
		public String getColumnText(Object element, int columnIndex) {
			String value = null;

			if (element instanceof OSObject) {
				OSObject item = (OSObject) element;
				if (columnIndex < sortablePropertyDescriptors.length) {
					value = item
							.getFormatedPropertyValue(sortablePropertyDescriptors[columnIndex]
									.getId());

					if (value == null) {
						// the property is not available. Hide the column from
						// the user. This may happen when, for instance, we
						// switch from a stop mode machine to a run mode machine
						columns[columnIndex].setWidth(0);
						columns[columnIndex].setResizable(false);
					} else {
						// make sure corresponding column is visible. This may
						// happen when we switch from a run mode machine to a stop
						// mode machine.
						if (columns[columnIndex].getResizable() == false) {
							columns[columnIndex].setResizable(true);
							// This may cause a bit of flickering, but no big
							// deal.
							columns[columnIndex].pack();
						}
					}
				}
			}

			return value;
		}

		/**
		 * @see ITableLabelProvider#getColumnImage(Object, int)
		 */
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}
	}

	private IPropertyDescriptor[] sortablePropertyDescriptors = null;

	private int objectType;

	private Text filterText;
	
    private ToolBarManager clearFilterToolBar;

	private ItemFilter nameFilter = new ItemFilter();

	private TableViewer viewer;

	private TableColumn[] columns = null;

	private int sortedColumnIndex = 0;

	// This is the model input for this tab. Refer to where it's set.
	@SuppressWarnings(value = { "unused" }) //$NON-NLS-1$
	private OSDataManager osDataManager = null;

	public GenericTableTab(int objectType) {
		this.objectType = objectType;

		switch (objectType) {
		case EclipseDEConstants.J_OSObjectType_Process:
			sortablePropertyDescriptors = OSObjectProcess
					.getSortablePropertyDescriptorList();
			break;

		case EclipseDEConstants.J_OSObjectType_Thread:
			sortablePropertyDescriptors = OSObjectThread
					.getSortablePropertyDescriptorList();
			break;

		case EclipseDEConstants.J_OSObjectType_Chunk:
			sortablePropertyDescriptors = OSObjectChunk
					.getSortablePropertyDescriptorList();
			break;

		case EclipseDEConstants.J_OSObjectType_Library:
			sortablePropertyDescriptors = OSObjectLibrary
					.getSortablePropertyDescriptorList();
			break;
		}
	}

	public TableViewer createControl(TabFolder tabFolder, TabItem tabItem) {
		final Composite composite = new Composite(tabFolder, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		gridLayout.marginWidth = 0;
		gridLayout.marginTop = 5;
		gridLayout.marginHeight = 0;
		composite.setLayout(gridLayout);
		tabItem.setControl(composite);

		final Label filterLabel = new Label(composite, SWT.NONE);
		final GridData gridData = new GridData(SWT.RIGHT, SWT.CENTER, false, false);
		gridData.horizontalIndent = 5;
		filterLabel.setLayoutData(gridData);
		filterLabel.setText(Messages.getString("SymbianOSView.NameFilterLabel")); //$NON-NLS-1$

		filterText = new Text(composite, SWT.SINGLE | SWT.BORDER | SWT.SEARCH | SWT.CANCEL);
		filterText.setText(Messages.getString("SymbianOSView.NameFilterInitialText")); //$NON-NLS-1$
		filterText.setToolTipText(Messages.getString("SymbianOSView.NameFilterToolTip")); //$NON-NLS-1$
		final GridData gd_filterText = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		gd_filterText.widthHint = 200;
		filterText.setLayoutData(gd_filterText);
		filterText.addFocusListener(new FocusAdapter() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.swt.events.FocusListener#focusLost(org.eclipse.swt.events.FocusEvent)
			 */
			public void focusGained(FocusEvent e) {
				/*
				 * Running in an asyncExec because the selectAll() does not
				 * appear to work when using mouse to give focus to text.
				 */
				Display display = filterText.getDisplay();
				display.asyncExec(new Runnable() {
					public void run() {
						if (!filterText.isDisposed()) {
							filterText.selectAll();
						}
					}
				});
			}
		});

		filterText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				FilterItems();
				clearFilterToolBar.getControl().setVisible(filterText.getText().length() > 0);
			}
		});
		clearFilterToolBar = new ToolBarManager(SWT.FLAT | SWT.HORIZONTAL);
		clearFilterToolBar.createControl(composite);

		IAction clearTextAction = new Action("", IAction.AS_PUSH_BUTTON) {//$NON-NLS-1$
			public void run() {
				filterText.setText(""); //$NON-NLS-1$
				FilterItems();
			}
		};
		clearTextAction.setToolTipText(Messages.getString("SymbianOSView.ClearToolTip")); //$NON-NLS-1$
		clearTextAction.setImageDescriptor(SymbianOSView.clearImageDesc);
		clearTextAction.setDisabledImageDescriptor(SymbianOSView.clearImageDesc);
		clearFilterToolBar.add(clearTextAction);
		clearFilterToolBar.update(false);
        // initially there is no text to clear
		clearFilterToolBar.getControl().setVisible(false);

		viewer = new TableViewer(composite, SWT.FULL_SELECTION);
		viewer.addOpenListener(new IOpenListener() {
			public void open(OpenEvent event) {
				return;
			}
		});
		viewer.setContentProvider(new MyTableDataProvider());
		viewer.setLabelProvider(new MyTableLabelProvider(viewer));
		viewer.setInput(new Object());
		viewer.addFilter(nameFilter);

		Table table = viewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		final GridData gridData_2 = new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1);
		table.setLayoutData(gridData_2);

		int columnNum = sortablePropertyDescriptors.length;

		columns = new TableColumn[columnNum];

		for (int i = 0; i < columnNum; i++) {
			columns[i] = new TableColumn(table, SWT.CENTER);
			final int columnIndex = i;
			final String propID = (String) sortablePropertyDescriptors[i]
					.getId();
			columns[i].addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					sortByProperty(propID, columnIndex);
				}
			});
			columns[i].setWidth(118);
			columns[i].setAlignment(SWT.LEFT);
			columns[i].setText((String) sortablePropertyDescriptors[i]
					.getDisplayName());
		}

		return viewer;
	}

	private void sortByProperty(final String propertyID, final int columnIndex) {
		int sortDirection = SWT.UP;
		if (sortedColumnIndex == columnIndex) {
			if (viewer.getTable().getSortDirection() == SWT.UP)
				sortDirection = SWT.DOWN;
		}

		// Put an "arrow" mark on the current sorted column.
		sortedColumnIndex = columnIndex;
		viewer.getTable().setSortColumn(columns[sortedColumnIndex]);
		viewer.getTable().setSortDirection(sortDirection);

		final int f_sortDirection = sortDirection;
		viewer.setSorter(new ViewerSorter() {
			public int compare(Viewer viewer, Object e1, Object e2) {

				if (f_sortDirection == SWT.DOWN) // reverse sorting
				{
					Object tmp = e1;
					e1 = e2;
					e2 = tmp;
				}

				if ((e1 instanceof OSObject) && (e2 instanceof OSObject)) {
					OSObject left = (OSObject) e1;
					OSObject right = (OSObject) e2;
					Object leftProp = left.getRawPropertyValue(propertyID);
					Object rightProp = right.getRawPropertyValue(propertyID);

					if (leftProp instanceof String)
						return ((String) leftProp)
								.compareToIgnoreCase((String) rightProp);
					else if (leftProp instanceof Integer)
						return ((Integer) leftProp)
								.compareTo((Integer) rightProp);
					else if (leftProp instanceof Long)
						return ((Long) leftProp).compareTo((Long) rightProp);
					else
						assert (false); // unsupported data type.
				}

				return super.compare(viewer, e1, e2);
			}

			public boolean isSorterProperty(Object element, String property) {
				return true;
			}
		});
	}

	private void FilterItems() {
		String pattern = filterText.getText();

		if (pattern.length() == 0)
			// This will call viewer.refresh().
			viewer.removeFilter(nameFilter);
		else {
			nameFilter.setPattern(pattern);

			if (viewer.getFilters().length == 0)
				// This will call viewer.refresh().
				viewer.addFilter(nameFilter);
			else
				viewer.refresh();
		}
	}
}
