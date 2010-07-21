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

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IDialogSettings;
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
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
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

import com.nokia.cdt.internal.debug.launch.LaunchPlugin;
import com.nokia.cpp.internal.api.utils.core.Pair;

/**
 * A composite that displays files in a table. Files can be 
 * added, removed, and edited..
 */
public class FilesBlock {
	
	FileTransferTab fLaunchTab;
	/**
	 * This block's control
	 */
	private Composite fControl;
	
	/**
	 * Files being displayed
	 */
	private List<FileToTransfer> fFiles = new ArrayList<FileToTransfer>(); 
	
	/**
	 * The main list control
	 */ 
	private CheckboxTableViewer fFileList;
	
	// Action buttons
	private Button fAddButton;
	private Button fRemoveButton;
	private Button fEditButton;
	private Button fSelectAllButton;
	private Button fDeSelectAllButton;
	
	// column weights
	private float fWeight1 = .1F;
	private float fWeight2 = .45F;
	
	// ignore column re-sizing when the table is being resized
	private boolean fResizingTable = false; 
	
	// index of column used for sorting
	private int fSortColumn = 1;
	
	// cache of file info: we expect this to remain relatively static while
	// any UI using FilesBlock is up.
	private static Map<File, Pair<Long, Boolean>> fFileInfoCache = new LinkedHashMap<File, Pair<Long,Boolean>>();
	private static long fFileInfoCacheFlushTime = 0;
	private static final int CACHE_CHECK_QUANTUM = 60 * 1000;

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
			if (element instanceof FileToTransfer) {
				FileToTransfer file = (FileToTransfer)element;
				switch(columnIndex) {
					case 0:
						return ""; //$NON-NLS-1$
					case 1:
						return file.getHostPath();
					case 2: 
						return file.getTargetPath();						
				}
			}
			return element.toString();
		}

		/**
		 * @see ITableLabelProvider#getColumnImage(Object, int)
		 */
		public Image getColumnImage(Object element, int columnIndex) {
			if (columnIndex == 1 && element instanceof FileToTransfer) {
				// add warning icon for any host files that don't exist
				FileToTransfer file = (FileToTransfer)element;
				File hostFile = new Path(file.getHostPath()).toFile();
				if (!fileExists(hostFile)) {
					return LaunchPlugin.getImageDescriptor("icons/Launch/etool16/warning_obj.gif").createImage();
				}
			}
			return null;
		}

	}
	
	FilesBlock(FileTransferTab launchTab) {
		fLaunchTab = launchTab;
		fFileInfoCacheFlushTime = System.currentTimeMillis();
	}

	/**
	 * Creates this block's control in the given control.
	 * 
	 * @param ancestor containing control
	 */
	public void createControl(Composite ancestor) {
		
		Composite parent= new Composite(ancestor, SWT.NULL);
		GridLayout layout= new GridLayout();
		layout.numColumns= 2;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		parent.setLayout(layout);
		Font font = ancestor.getFont();
		parent.setFont(font);	
		fControl = parent;	
		
		GridData data;

		Table table= new Table(parent, SWT.CHECK | SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION);
		data= new GridData(GridData.FILL_BOTH);
		table.setLayoutData(data);
		table.setFont(font);
				
		table.setHeaderVisible(true);
		table.setLinesVisible(true);		

		TableLayout tableLayout= new TableLayout();
		table.setLayout(tableLayout);

		TableColumn column1= new TableColumn(table, SWT.NULL);
		column1.setText(Messages.getString("FileTransferTab.2")); //$NON-NLS-1$
		column1.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				sortByEnabled();
			}
		});
	
		TableColumn column2= new TableColumn(table, SWT.NULL);
		column2.setText(Messages.getString("FileTransferTab.3")); //$NON-NLS-1$
		column2.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				sortByHostPath();
			}
		});
		
		TableColumn column3= new TableColumn(table, SWT.NULL);
		column3.setText(Messages.getString("FileTransferTab.4")); //$NON-NLS-1$
		column3.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				sortByTargetPath();
			}
		});
		
		fFileList= new CheckboxTableViewer(table);			
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
				FileToTransfer file = (FileToTransfer)e.getElement();
				file.setEnabled(e.getChecked());
				fLaunchTab.dataChanged();
			}
		});
		
		table.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent event) {
				if (event.character == SWT.DEL && event.stateMask == 0) {
					removeFiles();
				}
			}
		});	
		
		Composite buttons= new Composite(parent, SWT.NULL);
		buttons.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		layout= new GridLayout();
		layout.marginHeight= 0;
		layout.marginWidth= 0;
		buttons.setLayout(layout);
		buttons.setFont(font);
		
		fAddButton = createPushButton(buttons, Messages.getString("FileTransferTab.5")); //$NON-NLS-1$
		fAddButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event evt) {
				addFile();
			}
		});
		
		fEditButton= createPushButton(buttons, Messages.getString("FileTransferTab.6")); //$NON-NLS-1$
		fEditButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event evt) {
				editFile();
			}
		});
		
		fRemoveButton= createPushButton(buttons, Messages.getString("FileTransferTab.7")); //$NON-NLS-1$
		fRemoveButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event evt) {
				removeFiles();
			}
		});

		fSelectAllButton= createPushButton(buttons, Messages.getString("FileTransferTab.10")); //$NON-NLS-1$
		fSelectAllButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event evt) {
				for (int i = 0; i < fFiles.size(); i++) {
					FileToTransfer file = (FileToTransfer)fFiles.get(i);
					file.setEnabled(true);
					fFileList.setChecked(file, true);
				}
				fLaunchTab.dataChanged();
			}
		});

		fDeSelectAllButton= createPushButton(buttons, Messages.getString("FileTransferTab.11")); //$NON-NLS-1$
		fDeSelectAllButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event evt) {
				for (int i = 0; i < fFiles.size(); i++) {
					FileToTransfer file = (FileToTransfer)fFiles.get(i);
					file.setEnabled(false);
					fFileList.setChecked(file, false);
				}
				fLaunchTab.dataChanged();
			}
		});

		configureTableResizing(parent, buttons, table, column1, column2, column3);
		
		enableButtons();
		fAddButton.setEnabled(true);
	}
	
	/**
	 * Sorts by enabled.
	 */
	private void sortByEnabled() {
		fFileList.setSorter(new ViewerSorter() {
			public int compare(Viewer viewer, Object e1, Object e2) {
				if ((e1 instanceof FileToTransfer) && (e2 instanceof FileToTransfer)) {
					FileToTransfer left= (FileToTransfer)e1;
					FileToTransfer right= (FileToTransfer)e2;
					if (left.getEnabled() == right.getEnabled()) {
						return 0;
					} else if (left.getEnabled()) {
						return 1;
					}
					else
						return -1;
				}
				return super.compare(viewer, e1, e2);
			}
			
			public boolean isSorterProperty(Object element, String property) {
				return true;
			}
		});		
		fSortColumn = 1;		
	}
	
	/**
	 * Sorts by host path.
	 */
	private void sortByHostPath() {
		fFileList.setSorter(new ViewerSorter() {
			public int compare(Viewer viewer, Object e1, Object e2) {
				if ((e1 instanceof FileToTransfer) && (e2 instanceof FileToTransfer)) {
					FileToTransfer left= (FileToTransfer)e1;
					FileToTransfer right= (FileToTransfer)e2;
					return left.getHostPath().compareToIgnoreCase(right.getHostPath());
				}
				return super.compare(viewer, e1, e2);
			}
			
			public boolean isSorterProperty(Object element, String property) {
				return true;
			}
		});	
		fSortColumn = 3;			
	}
	
	/**
	 * Sorts by target path.
	 */
	private void sortByTargetPath() {
		fFileList.setSorter(new ViewerSorter() {
			public int compare(Viewer viewer, Object e1, Object e2) {
				if ((e1 instanceof FileToTransfer) && (e2 instanceof FileToTransfer)) {
					FileToTransfer left= (FileToTransfer)e1;
					FileToTransfer right= (FileToTransfer)e2;
					return left.getTargetPath().compareToIgnoreCase(right.getTargetPath());
				}
				return super.compare(viewer, e1, e2);
			}
			
			public boolean isSorterProperty(Object element, String property) {
				return true;
			}
		});		
		fSortColumn = 2;		
	}
		
	private void enableButtons() {
		int selectionCount= ((IStructuredSelection)fFileList.getSelection()).size();
		fEditButton.setEnabled(selectionCount == 1);
		fRemoveButton.setEnabled(selectionCount > 0);
	}	
	
	protected Button createPushButton(Composite parent, String label) {
		Button button = new Button(parent, SWT.PUSH);
		button.setFont(parent.getFont());
		if (label != null) {
			button.setText(label);
		}
		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		button.setLayoutData(gd);	
		return button;
	}
	
	/**
	 * Correctly resizes the table so no phantom columns appear
	 */
	protected void configureTableResizing(final Composite parent, final Composite buttons, final Table table, final TableColumn column1, final TableColumn column2, final TableColumn column3) {
		parent.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				resizeTable(parent, buttons, table, column1, column2, column3);
			}
		}); 
		table.addListener(SWT.Paint, new Listener() {
			public void handleEvent(Event event) {
				table.removeListener(SWT.Paint, this);
				resizeTable(parent, buttons, table, column1, column2, column3);
			}
		});
		column1.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				if (column1.getWidth() > 0 && !fResizingTable) {
					fWeight1 = getColumnWeight(0);
				}
			}
		});
		column2.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				if (column2.getWidth() > 0 && !fResizingTable) {
					fWeight2 = getColumnWeight(1);
				}
			}
		});
	}	

	private void resizeTable(Composite parent, Composite buttons, Table table, TableColumn column1, TableColumn column2, TableColumn column3) {
		fResizingTable = true;
		int parentWidth = -1;
		int parentHeight = -1;
		if (parent.isVisible()) {
			Rectangle area = parent.getClientArea();
			parentWidth = area.width;
			parentHeight = area.height;
		} else {
			Point parentSize = parent.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			parentWidth = parentSize.x;
			parentHeight = parentSize.y;
		}
		Point preferredSize = table.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		int width = parentWidth - 2 * table.getBorderWidth();
		if (preferredSize.y > parentHeight) {
			// Subtract the scrollbar width from the total column width
			// if a vertical scrollbar will be required
			Point vBarSize = table.getVerticalBar().getSize();
			width -= vBarSize.x;
		}
		width-= buttons.getSize().x;
		Point oldSize = table.getSize();
		if (oldSize.x > width) {
			// table is getting smaller so make the columns
			// smaller first and then resize the table to
			// match the client area width
			column1.setWidth(Math.round(width * fWeight1));
			column2.setWidth(Math.round(width * fWeight2));
			column3.setWidth(width - (column1.getWidth() + column2.getWidth()));
			table.setSize(width, parentHeight);
		} else {
			// table is getting bigger so make the table
			// bigger first and then make the columns wider
			// to match the client area width
			table.setSize(width, parentHeight);
			column1.setWidth(Math.round(width * fWeight1));
			column2.setWidth(Math.round(width * fWeight2));
			column3.setWidth(width - (column1.getWidth() + column2.getWidth()));
		 }
		 fResizingTable = false;		
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
	protected void setFiles(List<FileToTransfer> files) {
		fFiles.clear();
		for (int i = 0; i < files.size(); i++) {
			FileToTransfer file = (FileToTransfer)files.get(i);
			fFiles.add(file);
		}
		fFileList.setInput(fFiles);
		fFileList.refresh();

		for (int i = 0; i < fFiles.size(); i++) {
			FileToTransfer file = (FileToTransfer)fFiles.get(i);
			fFileList.setChecked(file, file.getEnabled());
		}
	}
	
	/**
	 * Returns the files currently being displayed in this block
	 * 
	 * @return files currently being displayed in this block
	 */
	public FileToTransfer[] getFiles() {
		return (FileToTransfer[])fFiles.toArray(new FileToTransfer[fFiles.size()]);
	}
	
	/**
	 * Bring up a dialog that lets the user create a new file to transfer.
	 */
	private void addFile() {
		FileToTransfer file = new FileToTransfer();
		AddEditFileToTransferDialog dialog= new AddEditFileToTransferDialog(getShell(), file);
		dialog.setTitle(Messages.getString("FileTransferTab.8")); //$NON-NLS-1$
		if (dialog.open() != Window.OK) {
			return;
		}
		fFiles.add(file);
		fFileList.refresh();
		fFileList.setChecked(file, file.getEnabled());
		fLaunchTab.dataChanged();
	}
		
	private void editFile() {
		IStructuredSelection selection= (IStructuredSelection)fFileList.getSelection();
		FileToTransfer file= (FileToTransfer)selection.getFirstElement();
		if (file == null) {
			return;
		}
		AddEditFileToTransferDialog dialog= new AddEditFileToTransferDialog(getShell(), file);
		dialog.setTitle(Messages.getString("FileTransferTab.9")); //$NON-NLS-1$
		if (dialog.open() != Window.OK) {
			return;
		}
		fFileList.refresh(file);
		fLaunchTab.dataChanged();
	}
	
	private void removeFiles() {
		IStructuredSelection selection= (IStructuredSelection)fFileList.getSelection();
		FileToTransfer[] files = new FileToTransfer[selection.size()];
		Iterator<?> iter = selection.iterator();
		int i = 0;
		while (iter.hasNext()) {
			files[i] = (FileToTransfer)iter.next();
			i++;
		}
		removeFiles(files);
	}
	
	/**
	 * Removes the given files from the table.
	 * 
	 * @param files
	 */
	public void removeFiles(FileToTransfer[] files) {
		for (int i = 0; i < files.length; i++) {
			fFiles.remove(files[i]);
		}
		fFileList.refresh();
		fLaunchTab.dataChanged();
	}
	
	protected Shell getShell() {
		return getControl().getShell();
	}

	/**
	 * Persist table settings into the give dialog store, prefixed
	 * with the given key.
	 * 
	 * @param settings dialog store
	 * @param qualifier key qualifier
	 */
	public void saveColumnSettings(IDialogSettings settings, String qualifier) {
		for (int i = 0; i < 2; i++) {
			//persist the first 2 column weights
			settings.put(qualifier + ".column" + i, getColumnWeight(i));	 //$NON-NLS-1$
		}
		settings.put(qualifier + ".sortColumn", fSortColumn); //$NON-NLS-1$
	}
	
	private float getColumnWeight(int col) {
		Table table = fFileList.getTable();
		int tableWidth = table.getSize().x;
		int columnWidth= table.getColumn(col).getWidth();
		if (tableWidth > columnWidth) {
			return ((float)columnWidth) / tableWidth;
		}
		return 1/3F;
	}
	
	/**
	 * Restore table settings from the given dialog store using the
	 * given key.
	 * 
	 * @param settings dialog settings store
	 * @param qualifier key to restore settings from
	 */
	public void restoreColumnSettings(IDialogSettings settings, String qualifier) {
		fWeight1 = restoreColumnWeight(settings, qualifier, 0);
		fWeight2 = restoreColumnWeight(settings, qualifier, 1);
		fFileList.getTable().layout(true);
		try {
			fSortColumn = settings.getInt(qualifier + ".sortColumn"); //$NON-NLS-1$
		} catch (NumberFormatException e) {
			fSortColumn = 1;
		}
		switch (fSortColumn) {
			case 1:
				sortByEnabled();
				break;
			case 2:
				sortByHostPath();
				break;
			case 3:
				sortByTargetPath();
				break;
		}
	}
	
	private float restoreColumnWeight(IDialogSettings settings, String qualifier, int col) {		
		try {
			return settings.getFloat(qualifier + ".column" + col); //$NON-NLS-1$
		} catch (NumberFormatException e) {
			return 1/3F;
		}

	}

	Viewer getViewer() {
		return fFileList;
	}
	
	boolean fileExists(File file) {
		Pair<Long, Boolean> entry = fFileInfoCache.get(file);
		
		// recheck the status occasionally, either after time passes or
		// whenever the UI is recreated (as in constructor)
		if (System.currentTimeMillis() >= fFileInfoCacheFlushTime) {
			// remove entries not checked at all in a while
			long deadLine = System.currentTimeMillis() - CACHE_CHECK_QUANTUM;
			Iterator<Pair<Long, Boolean>> iter = fFileInfoCache.values().iterator();
			while (iter.hasNext()) {
				Pair<Long, Boolean> ientry = iter.next();
				if (ientry.first < deadLine) {
					iter.remove();
				}
			}
			fFileInfoCacheFlushTime = System.currentTimeMillis() + CACHE_CHECK_QUANTUM;
		}
		
		if (entry == null) {
			//System.out.println("Checking " + file);
			entry = new Pair<Long, Boolean>(System.currentTimeMillis(), file.exists());
			fFileInfoCache.put(file, entry);
		} else {
			//System.out.println("Not checking " + file);
		}
		return entry.second;
	}
}
