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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SearchPattern;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.freescale.cdt.debug.cw.core.os.OSProcess;
import com.freescale.cdt.debug.cw.core.os.OSProcess.ParsedProcess;
import com.nokia.cpp.internal.api.utils.core.Check;

public class ChooseProcessDialog extends TrayDialog {

	private class ProcessesLabelProvider extends LabelProvider implements ITableLabelProvider {

		public String getColumnText(Object element, int columnIndex) {
			if (element instanceof ParsedProcess) {
				ParsedProcess parsedProcess = (ParsedProcess) element;
				if (columnIndex == PROCESS_NAME_COLUMN)
					return parsedProcess.getProcessName();
				else if (columnIndex == PROCESS_ID_COLUMN)
					return parsedProcess.getProcessId();
			}
			return null;
		}

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

	}
	
	public class ItemFilter extends ViewerFilter {

		private SearchPattern searchPattern;

		public ItemFilter() {
			searchPattern = new SearchPattern();
			searchPattern.setPattern(""); //$NON-NLS-1$
		}

		@Override
		public boolean select(Viewer viewer, Object parentElement, Object element) {
			return match(element.toString());
		}

		public boolean match(String str) {
			return searchPattern.matches(str);
		}
		
		public void setPattern(String pattern) {
			searchPattern.setPattern(pattern);
		}
	}

	private static final String AUTOTEST_UID = ".uid"; //$NON-NLS-1$
	private static final int PROCESS_ID_COLUMN = 0;
	private static final int PROCESS_NAME_COLUMN = 1;

	private OSProcess[] processesOnTarget;
	private OSProcess selectedProcess;
	private String defaultProcessName;
	private Text filterText;
    private ToolBarManager clearFilterToolBar;
	private ItemFilter nameFilter = new ItemFilter();
	private TableViewer viewer;
	private Map<ParsedProcess, OSProcess> parsedProcessMap;
	private int lastSortColumn = PROCESS_ID_COLUMN; // default
	private int sortDirection = 1; // default
	
    public static final ImageDescriptor clearImageDesc = AbstractUIPlugin.imageDescriptorFromPlugin(
    		PlatformUI.PLUGIN_ID, "$nl$/icons/full/etool16/clear_co.gif"); //$NON-NLS-1$

	/**
	 * @param processesOnTarget OSProcess[]
	 * @param defaultProcessName String
	 * @param parentShell Shell
	 */
	public ChooseProcessDialog(OSProcess[] processesOnTarget, String defaultProcessName, Shell parentShell) {
		super(parentShell);
		setShellStyle(getShellStyle() | SWT.RESIZE);
		this.processesOnTarget = processesOnTarget;
		Check.checkContract(processesOnTarget.length > 0);
		this.defaultProcessName = defaultProcessName;
	}
	
	protected Control createDialogArea(Composite parent) {
		Composite dialogArea = (Composite) super.createDialogArea(parent);
		final Label label = new Label(dialogArea, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		label.setText(Messages.getString("ChooseProcessDialog.Message")); //$NON-NLS-1$)
		
		Composite filterComposite = new Composite(dialogArea, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.marginWidth = 0;
		gridLayout.marginTop = 5;
		gridLayout.marginHeight = 0;
		gridLayout.numColumns = 2;
		filterComposite.setLayout(gridLayout);

		filterText = new Text(filterComposite, SWT.SINGLE | SWT.BORDER | SWT.SEARCH | SWT.CANCEL);
		filterText.setText(Messages.getString("ChooseProcessDialog.NameFilterInitialText")); //$NON-NLS-1$
		filterText.setToolTipText(Messages.getString("ChooseProcessDialog.NameFilterToolTip")); //$NON-NLS-1$
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
		clearFilterToolBar.createControl(filterComposite);

		IAction clearTextAction = new Action("", IAction.AS_PUSH_BUTTON) {//$NON-NLS-1$
			public void run() {
				filterText.setText(""); //$NON-NLS-1$
				FilterItems();
			}
		};
		clearTextAction.setToolTipText(Messages.getString("ChooseProcessDialog.ClearToolTip")); //$NON-NLS-1$
		clearTextAction.setImageDescriptor(clearImageDesc);
		clearTextAction.setDisabledImageDescriptor(clearImageDesc);
		clearFilterToolBar.add(clearTextAction);
		clearFilterToolBar.update(false);
        // initially there is no text to clear
		clearFilterToolBar.getControl().setVisible(false);

		viewer = new TableViewer(dialogArea, SWT.BORDER | SWT.SINGLE | SWT.FULL_SELECTION);
		Table table = viewer.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		table.setData(AUTOTEST_UID, "table"); //$NON-NLS-1$
		
		// process id column
		TableColumn processIdColumn = new TableColumn(table, SWT.LEFT);
		processIdColumn.setText(Messages.getString("ChooseProcessDialog.IDLabel")); //$NON-NLS-1$
		processIdColumn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateSortDirection(PROCESS_ID_COLUMN);
				ViewerComparator comparator = getViewerComparator(PROCESS_ID_COLUMN);
				viewer.setComparator(comparator);
				setColumnSorting((TableColumn) e.getSource(), sortDirection);
			}
		});
		processIdColumn.setData(AUTOTEST_UID, "processIdColumn"); //$NON-NLS-1$
		
		// process name column
		TableColumn processNameColumn = new TableColumn(table, SWT.LEFT);
		processNameColumn.setText(Messages.getString("ChooseProcessDialog.NameLabel")); //$NON-NLS-1$
		processNameColumn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateSortDirection(PROCESS_NAME_COLUMN);
				ViewerComparator comparator = getViewerComparator(PROCESS_NAME_COLUMN);
				viewer.setComparator(comparator);
				setColumnSorting((TableColumn) e.getSource(), sortDirection);
			}
		});
		processNameColumn.setData(AUTOTEST_UID, "processNameColumn"); //$NON-NLS-1$
		
		viewer.setContentProvider(new ArrayContentProvider());
		viewer.setLabelProvider(new ProcessesLabelProvider());
		parseProcesses();
		viewer.setInput(parsedProcessMap.keySet());
		
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				Object object = selection.getFirstElement();
				Check.checkState(object instanceof ParsedProcess);
				selectedProcess = parsedProcessMap.get(object);
				okPressed();
			}
		});
		
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				Button okButton = getButton(IDialogConstants.OK_ID);
				if (okButton != null) {
					okButton.setEnabled(!event.getSelection().isEmpty());
				}
			}
			
		});
		
		setColumnSorting(processIdColumn, sortDirection);
		table.setHeaderVisible(true);
		
		TableColumn[] columns = viewer.getTable().getColumns();
		for (TableColumn tableColumn : columns) {
			tableColumn.pack();
		}
		OSProcess defaultProcess = findProcessByName(defaultProcessName);
		if (defaultProcess == null)
			defaultProcess = processesOnTarget[0];
		viewer.setSelection(new StructuredSelection(defaultProcess), true);
		
		return dialogArea;
	}	

	private OSProcess findProcessByName(String defaultProcessName) {
		for (OSProcess process : processesOnTarget) {
			ParsedProcess parsedProcess = process.parseProcess();
			if (parsedProcess != null && defaultProcessName.equals(process.parseProcess().second))
				return process;
		}
		
		return null;
	}

	private void parseProcesses() {
		parsedProcessMap = new HashMap<ParsedProcess, OSProcess>();
		for (OSProcess process : processesOnTarget) {
			ParsedProcess parsedProcess = process.parseProcess();
			if (parsedProcess != null)
				parsedProcessMap.put(parsedProcess, process);
		}
	}
	
	private ViewerComparator getViewerComparator(int column) {
		switch (column) {
			case PROCESS_NAME_COLUMN:
				return new ViewerComparator() {
					public int compare(Viewer viewer, Object e1, Object e2) {
						String p1 = ((ParsedProcess) e1).getProcessName();
						String p2 = ((ParsedProcess) e2).getProcessName();
						if (p1 == null || p2 == null)
							return 0;
						return p1.compareToIgnoreCase(p2) * sortDirection ;
					}
				};
			case PROCESS_ID_COLUMN:
				return new ViewerComparator() {
					public int compare(Viewer viewer, Object e1, Object e2) {
						String p1 = ((ParsedProcess) e1).getProcessId();
						String p2 = ((ParsedProcess) e2).getProcessId();
						int i1 = 0;
						int i2 = 0;
						try {
							i1 = Integer.parseInt(p1);
							i2 = Integer.parseInt(p2);
						}
						catch (Exception e) {
							// ignore, just return 0
						}
						return (i1 - i2) * sortDirection ;
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
	}

	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Messages.getString("ChooseProcessDialog.Title")); //$NON-NLS-1$
		PlatformUI.getWorkbench().getHelpSystem().setHelp(newShell, LaunchTabHelpIds.ATTACH_CHOOSE_PROCESS);
	}

	@Override
	protected void okPressed() {
		StructuredSelection selectedItems = (StructuredSelection) viewer.getSelection();
		Check.checkState(!selectedItems.isEmpty());
		Object object = selectedItems.getFirstElement();
		Check.checkState(object instanceof ParsedProcess);
		selectedProcess = parsedProcessMap.get(object);
		super.okPressed();
	}

	public OSProcess getSelectedProcess() {
		return selectedProcess;
	}

	@Override
	protected Point getInitialSize() {
		return new Point(300, 375);
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

	@Override
	protected Control createButtonBar(Composite parent) {
		Control control = super.createButtonBar(parent);
		
		// disable the ok button.  it will be enabled when they select something
		getButton(IDialogConstants.OK_ID).setEnabled(false);
		
		return control;
	}
}
