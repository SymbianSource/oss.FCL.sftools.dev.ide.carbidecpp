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

import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import java.util.List;

/**
 * A dialog that displays files in a table with checkboxes.
 * Constructor argument is List<IFile> and the dialog modifies the list with only the checked files if ok.
 */
public class FilesListDialog extends Dialog {

	private class FilesListContentProvider implements IStructuredContentProvider {
		public Object[] getElements(Object inputElement) {
			Check.checkArg(inputElement instanceof List);
			List<IFile> paths = (List<IFile>) inputElement;
			return paths.toArray();
		}

		public void dispose() {}
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {}
	}
	
	private class FilesListLabelProvider extends LabelProvider {
		private ILabelProvider wbLabelProvider;

		public FilesListLabelProvider() {
			wbLabelProvider = WorkbenchLabelProvider.getDecoratingWorkbenchLabelProvider();
		}
		
		@Override
		public Image getImage(Object element) {
			return wbLabelProvider.getImage(element);
		}

		@Override
		public String getText(Object element) {
			IFile file = (IFile) element;
			return file.getFullPath().makeRelative().toString();
		}
	}

	private TableViewer filesTableViewer;
	private final List<IFile> filesList;
	private String caption;
	private String title;
	private Button okButton;
	private Text label;
	private boolean initiallyChecked;
	private boolean editable;
	private String okButtonLabel;
	private String cancelButtonLabel;
	
	/**
	 * Create the dialog with checkbox viewer
	 * @param parentShell
	 * @param filesList
	 * @param title
	 * @param caption
	 * @param initiallyChecked
	 */
	public FilesListDialog(Shell parentShell, List<IFile> filesList, String title, String caption, boolean initiallyChecked) {
		this(parentShell, filesList, title, caption, SWT.DIALOG_TRIM | SWT.RESIZE | SWT.MODELESS);
		this.initiallyChecked = initiallyChecked;
		editable = true;
	}
	
	/**
	 * Create the dialog with no checkboxes
	 * @param parentShell
	 * @param filesList
	 * @param title
	 * @param caption
	 */
	public FilesListDialog(Shell parentShell, List<IFile> filesList, String title, String caption) {
		// use system modal to ensure supercedes progress dialog
		this(parentShell, filesList, title, caption, SWT.DIALOG_TRIM | SWT.RESIZE | SWT.SYSTEM_MODAL);
	}
	
	private FilesListDialog(Shell parentShell, List<IFile> filesList, String title, String caption, int style) {
		super(parentShell);
		setShellStyle(style);
		this.filesList = filesList;
		this.title = title;
		this.caption = caption;
		this.okButtonLabel = IDialogConstants.OK_LABEL;
		this.cancelButtonLabel = IDialogConstants.CANCEL_LABEL;
	}
	
	/**
	 * Create contents of the dialog
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new FillLayout(SWT.VERTICAL));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		final Composite contents = new Composite(container, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayout.verticalSpacing = 20;
		gridLayout.marginTop = 10;
		gridLayout.marginRight = 10;
		gridLayout.marginLeft = 10;
		gridLayout.marginBottom = 10;
		gridLayout.horizontalSpacing = 20;
		contents.setLayout(gridLayout);
		
		label = new Text(contents, SWT.WRAP | SWT.READ_ONLY);
		final GridData gd_label = new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1);
		gd_label.widthHint = 400;
		label.setLayoutData(gd_label);
		label.setText(caption);

		if (editable)
			filesTableViewer = CheckboxTableViewer.newCheckList(contents, SWT.BORDER);
		else
			filesTableViewer = new TableViewer(contents, SWT.BORDER);
		
		final GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd_table.widthHint = 350;
		filesTableViewer.getTable().setLayoutData(gd_table);
		//
		filesTableViewer.setContentProvider(new FilesListContentProvider());
		filesTableViewer.setLabelProvider(new FilesListLabelProvider());
		filesTableViewer.setInput(filesList);
		if (editable) {
			final CheckboxTableViewer viewer = (CheckboxTableViewer) filesTableViewer;
			viewer.setAllChecked(initiallyChecked);
			viewer.addCheckStateListener(new ICheckStateListener() {
				public void checkStateChanged(CheckStateChangedEvent event) {
					updateOkButtonEnabled();
				}
			});
			final Button selectAllButton = new Button(contents, SWT.NONE);
			selectAllButton.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					viewer.setAllChecked(true);
					updateOkButtonEnabled();
				}
			});
			selectAllButton.setLayoutData(new GridData(90, SWT.DEFAULT));
			selectAllButton.setText("Select &All");
	
			final Button selectNoneButton = new Button(contents, SWT.NONE);
			selectNoneButton.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					viewer.setAllChecked(false);
					updateOkButtonEnabled();
				}
			});
			selectNoneButton.setLayoutData(new GridData(90, SWT.DEFAULT));
			selectNoneButton.setText("&Deselect All");
		}
		
		filesTableViewer.getControl().forceFocus();
		
		return area;
	}
	
	public void setAltButtonLabels(String okButtonLabel, String cancelButtonLabel) {
		this.okButtonLabel = okButtonLabel;
		this.cancelButtonLabel = cancelButtonLabel;
	}

	/**
	 * Create contents of the button bar
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		okButton = createButton(parent, IDialogConstants.OK_ID, okButtonLabel, true);
		updateOkButtonEnabled();
		
		createButton(parent, IDialogConstants.CANCEL_ID, cancelButtonLabel, false);
	}
	
	private void updateOkButtonEnabled() {
		if (editable)
			okButton.setEnabled(((CheckboxTableViewer) filesTableViewer).getCheckedElements().length > 0);
	}

	protected void okPressed() {
		if (editable) {
			TableItem[] tableItems = filesTableViewer.getTable().getItems();
			for (int i = 0; i < tableItems.length; i++) {
				TableItem tableItem = tableItems[i];
				if (!tableItem.getChecked()) {
					filesList.remove((IFile) tableItem.getData());
				}
			}
		}
		super.okPressed();
	}
	
	/**
	 * Return the initial size of the dialog
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(500, 375);
	}
	
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(title);
	}
}
