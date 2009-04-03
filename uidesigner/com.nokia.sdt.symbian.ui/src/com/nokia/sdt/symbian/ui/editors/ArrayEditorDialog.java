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
package com.nokia.sdt.symbian.ui.editors;

import com.nokia.sdt.component.property.ISequencePropertySource;
import com.nokia.sdt.component.symbian.properties.ArrayEditableValue;
import com.nokia.sdt.component.symbian.properties.CompoundPropertyTypeDescriptor;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.symbian.ui.UIPlugin;
import com.nokia.sdt.symbian.ui.noexport.Messages;
import com.nokia.sdt.uidesigner.ui.UndoablePropertySheetEntry;
import com.nokia.sdt.uidesigner.ui.EditValidatingCommandStack;
import com.nokia.cpp.internal.api.utils.core.TextUtils;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;
import com.swtdesigner.ResourceManager;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.JFaceColors;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.views.properties.*;

import java.util.List;

public class ArrayEditorDialog extends Dialog implements IPropertySourceProvider {

	private static final String HELP_CONTEXT_ID = UIPlugin.PLUGIN_ID + "arrayEditorDialogContextId";
	private TableViewer tableViewer;
	private int lastSelectedIndex = -1;
	private PropertySheetPage propertyPage;
	private ArrayEditableValue editValue;
	private ILabelProvider labelProvider;
	private boolean fixedLengthArray;
	private List arrayItems;
	private StatusLineManager statusLineManager;
	private Button newButton;
	private Button deleteButton;
	private Button downButton;
	private Button upButton;
	
	/**
	 * Create the dialog
	 * @param parentShell
	 * @param startValue the input value to the dialog
	 * @param labelProvider label provider for element values
	 * @param fixedLengthArray true if item addition and removal is prohibited
	 */
	public ArrayEditorDialog(Shell parentShell, ArrayEditableValue startValue, 
			ILabelProvider labelProvider, boolean fixedLengthArray) {
		super(parentShell);
		this.editValue = new ArrayEditableValue(startValue);
		this.labelProvider = labelProvider;
		this.fixedLengthArray = fixedLengthArray;
	}
	
	public IPropertySource getPropertySource(Object object) {
		IPropertySource result = null;
		if (object instanceof IPropertySource) {
			result = (IPropertySource) object;
		}
		return result;
	}
	
	public ArrayEditableValue getValue() {
		return editValue;
	}
	
	/**
	 * Create contents of the dialog
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new FormLayout());

		final Composite tableContainer = new Composite(container, SWT.NONE);
		final FormData formData = new FormData();
		formData.right = new FormAttachment(0, 245);
		formData.bottom = new FormAttachment(100, -5);
		formData.top = new FormAttachment(0, 5);
		formData.left = new FormAttachment(0, 5);
		tableContainer.setLayoutData(formData);
		tableContainer.setLayout(new FormLayout());	
		
		tableViewer = new TableViewer(tableContainer, 
				SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		tableViewer.setContentProvider(new IStructuredContentProvider() {
			public Object[] getElements(Object inputElement) {
				Object[] result;
				if (inputElement instanceof ArrayEditableValue) {
					ArrayEditableValue value = (ArrayEditableValue) inputElement;
					ISequencePropertySource sps = value.getValue();
					arrayItems = sps.toList();
					result = arrayItems.toArray();
				} else {
					result = new Object[0];
				}
				return result;
			}

			public void dispose() {
			}

			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			}
		});
		
		tableViewer.setLabelProvider(new TableLabelProvider(labelProvider));
		tableViewer.setComparer(new IElementComparer() {

			public boolean equals(Object a, Object b) {
				return a == b;
			}

			public int hashCode(Object element) {
				return element.hashCode();
			}
		});
		
		tableViewer.addPostSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				int newIndex = tableViewer.getTable().getSelectionIndex();
				if (newIndex != lastSelectedIndex) {
					lastSelectedIndex = newIndex;
					setPropertyViewInputToSelectedItem();
					updateButtons();
					updateCellEditor();
					
					Display.getCurrent().asyncExec(new Runnable() {
						public void run() {
							Object item = selectedArrayItem();
							if (item != null) {
								tableViewer.editElement(item, 1);
							}
						}
					});
				}
			}
		});
		
		final Table table = tableViewer.getTable();
	
		table.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				Rectangle bounds = tableViewer.getTable().getBounds();
				TableColumn col = table.getColumn(0);
				col.setWidth(25);
				col = table.getColumn(1);
				col.setWidth(bounds.width-28);
			}
			
		});
	
		@SuppressWarnings("unused")  //$NON-NLS-1$
		TableColumn tc = new TableColumn(table, SWT.LEFT);
		tc = new TableColumn(table, SWT.LEFT);
		final FormData formData_2 = new FormData();
		formData_2.bottom = new FormAttachment(0, 240);
		formData_2.right = new FormAttachment(100, -5);
		formData_2.top = new FormAttachment(0, 5);
		formData_2.left = new FormAttachment(0, 10);
		table.setLayoutData(formData_2);
		tableViewer.setInput(editValue);	
		
		final Composite buttonContainer = new Composite(tableContainer, SWT.NONE);
		final FormData formData_3 = new FormData();
		formData_3.bottom = new FormAttachment(100, -5);
		formData_3.right = new FormAttachment(100, -5);
		formData_3.top = new FormAttachment(0, 255);
		formData_3.left = new FormAttachment(0, 11);
		buttonContainer.setLayoutData(formData_3);

		newButton = new Button(buttonContainer, SWT.NONE);
		newButton.setBounds(1, 2, 63, 26);
		newButton.setText(Messages.getString("ArrayEditorDialog.NewButtonTitle")); //$NON-NLS-1$
		newButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				addArrayItem();
			}
		});

		deleteButton = new Button(buttonContainer, SWT.NONE);
		deleteButton.setBounds(75, 2, 60, 26);
		deleteButton.setText(Messages.getString("ArrayEditorDialog.DeleteButtonTitle")); //$NON-NLS-1$
		deleteButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				deleteArrayItem();
			}
		});

		upButton = new Button(buttonContainer, SWT.NONE);
		upButton.setBounds(160, 2, 27, 26);
		upButton.setImage(ResourceManager.getPluginImage(UIPlugin.getDefault(), "icons/upArrow.png")); //$NON-NLS-1$
		upButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				moveItem(-1);
			}
		});

		downButton = new Button(buttonContainer, SWT.NONE);
		downButton.setBounds(191, 2, 27, 26);
		downButton.setImage(ResourceManager.getPluginImage(UIPlugin.getDefault(), "icons/downArrow.png")); //$NON-NLS-1$
		downButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				moveItem(1);
			}
		});

		final Composite propertySheetContainer = new Composite(container, SWT.NONE);
		propertySheetContainer.setLayout(new FormLayout());
		final FormData formData_x = new FormData();
		formData_x.bottom = new FormAttachment(tableContainer, 0, SWT.BOTTOM);
		formData_x.right = new FormAttachment(100, -5);
		formData_x.top = new FormAttachment(tableContainer, 0, SWT.TOP);
		formData_x.left = new FormAttachment(0, 260);
		propertySheetContainer.setLayoutData(formData_x);

		final Composite propertySheetWrapper = new Composite(propertySheetContainer, SWT.NONE);
		propertySheetWrapper.setLayout(new FillLayout());
		final FormData formData_4 = new FormData();
		formData_4.bottom = new FormAttachment(0, 240);
		formData_4.top = new FormAttachment(0, 5);
		formData_4.right = new FormAttachment(100, -5);
		formData_4.left = new FormAttachment(0, 3);
		propertySheetWrapper.setLayoutData(formData_4);

		final Label statusLabel = new Label(propertySheetContainer, SWT.WRAP);
		final FormData formData_1 = new FormData();
		formData_1.bottom = new FormAttachment(100, -5);
		formData_1.right = new FormAttachment(propertySheetWrapper, 0, SWT.RIGHT);
		formData_1.top = new FormAttachment(propertySheetWrapper, 5, SWT.DEFAULT);
		formData_1.left = new FormAttachment(0, 5);
		statusLabel.setLayoutData(formData_1);
		
		createPropertyPage(propertySheetWrapper, statusLabel);
		
		if (arrayItems != null && arrayItems.size() > 0) {
			tableViewer.setSelection(new StructuredSelection(arrayItems.get(0)));
			setPropertyViewInputToSelectedItem();
		}
		
		WorkbenchUtils.setHelpContextId(container, HELP_CONTEXT_ID);
		return container;
	}
	
	private void createPropertyPage(Composite propertyContainer, Label statusLabel) {
		propertyPage = new PropertySheetPage();
		EditValidatingCommandStack unusedCommandStack = new EditValidatingCommandStack();
		unusedCommandStack.initialize(propertyContainer.getShell(),
				ModelUtils.getModel(editValue.getEObject()));
		UndoablePropertySheetEntry rootEntry = new UndoablePropertySheetEntry(unusedCommandStack);
		rootEntry.setPropertySourceProvider(this);
		propertyPage.setRootEntry(rootEntry);
		propertyPage.createControl(propertyContainer);
			
		statusLineManager = new StatusLineManager(statusLabel);
			
		propertyPage.makeContributions(new NullMenuManager(), 
				new NullToolBarManager(), statusLineManager);
		
		final IPropertySheetEntryListener listener = new IPropertySheetEntryListener() {
			public void childEntriesChanged(IPropertySheetEntry node) {
				IPropertySheetEntry[] childEntries = node.getChildEntries();
				for (IPropertySheetEntry entry : childEntries) {
					entry.addPropertySheetEntryListener(this);
				}
			}

			public void valueChanged(IPropertySheetEntry entry) {
				Object item = selectedArrayItem();
				if (item != null) {
					tableViewer.update(selectedArrayItem(), null);
				}
			}

			public void errorMessageChanged(IPropertySheetEntry entry) {
			}
		};
		rootEntry.addPropertySheetEntryListener(listener);		
	}
	
	private void updateCellEditor() {
		tableViewer.cancelEditing();
		final int index = tableViewer.getTable().getSelectionIndex();
		CellEditor[] cellEditors = tableViewer.getCellEditors();
		if (cellEditors != null && cellEditors.length == 2 && cellEditors[0] != null) {
			cellEditors[1].dispose();
		}
		tableViewer.setCellEditors(null);
		
		CompoundPropertyTypeDescriptor cptd = editValue.getCompoundPropertyTypeDescriptor();
		if (index >= 0 && cptd != null) {
			final String PROPERTY = "value";
			tableViewer.setColumnProperties(new String[] {null, PROPERTY});
			tableViewer.setCellModifier(new ICellModifier() {

				public boolean canModify(Object element, String property) {
					return true;
				}

				public Object getValue(Object element, String property) {
					Object result = element;
					if (element instanceof IPropertySource) {
						result = ((IPropertySource)element).getEditableValue();
					}
					return result;
				}

				public void modify(Object element, String property, Object value) {
					// TableViewer passes a TableItem instead of the model object
					if (element instanceof TableItem) {
						element = ((TableItem)element).getData();
					}
					String id = Integer.valueOf(index).toString();
					editValue.getValue().setPropertyValue(id, value);
					tableViewer.refresh(element);
					setPropertyViewInputToSelectedItem();
				}
			});
			CellEditor ce = cptd.createPropertyEditor(tableViewer.getTable(), SWT.NONE,
					editValue.getEObject(), editValue.getValue().getElementPath(index));
			tableViewer.setCellEditors(new CellEditor[] {null, ce});
		}
		
	}
	
	class TableLabelProvider extends LabelProvider implements ITableLabelProvider {
		
		private ILabelProvider itemSpecificProvider;
		
		TableLabelProvider(ILabelProvider labelProvider) {
			this.itemSpecificProvider = labelProvider;
		}
		
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}
		
		public String getColumnText(Object element, int columnIndex) {
			String result = null;
			if (columnIndex == 0) {
				int index = getItemIndex(element);
				result = Integer.toString(index) + "."; //$NON-NLS-1$
			} else {
				if (itemSpecificProvider != null) {
					result = itemSpecificProvider.getText(element);
				}
				if (result == null) {
					result = element.toString();
				}
			}
			return result;
		}
	}

	/**
	 * Create contents of the button bar
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		// override to create with no default button. The user should
		// be able to press Enter to begin editing in the property sheet but
		// that would trigger the default button.
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				false);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(541, 375);
	}
	
	Object selectedArrayItem() {
		Object result = null;
		IStructuredSelection ss = (IStructuredSelection) tableViewer.getSelection();
		if (ss != null) {
			result = ss.getFirstElement();
		}
		return result;
	}
	
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Messages.getString("ArrayEditorDialog.DialogTitle")); //$NON-NLS-1$
	}
	
	private void setPropertyViewInputToSelectedItem() {
		int index = tableViewer.getTable().getSelectionIndex();
		if (index >= 0) {
			IPropertySource ps = (IPropertySource) selectedArrayItem();
			// if the elements are compound then we need a "fake"
			// parent value, containing just this single property source
			// child, in order to allow editing of the "editable" value
			CompoundPropertyTypeDescriptor cptd = editValue.getCompoundPropertyTypeDescriptor();
			if (cptd != null && false && cptd.hasCellEditor()) {
				CompoundPropertySourceWrapper wrapper = new CompoundPropertySourceWrapper(
						ps, editValue.getEObject(), Integer.valueOf(index), 
						editValue.getCompoundPropertyTypeDescriptor());
				setPropertyViewInput(wrapper);
			} else {
				setPropertyViewInput(ps);
			}
		} else {
			setPropertyViewInput(null);
		}
	}
	
	private void setPropertyViewInput(Object obj) {
		if (obj == null) {
			propertyPage.selectionChanged(null, StructuredSelection.EMPTY);
		} else if (obj instanceof IPropertySource) {
			IStructuredSelection ss = new StructuredSelection(obj);
			propertyPage.selectionChanged(null, ss);
		}
	}
	
	private void updateButtons() {
		// Commented out code to disable image buttons because they look very strange when disabled.
		Object selectedItem = selectedArrayItem();
		if (selectedItem != null) {
		//	int selectedIndex = getItemIndex(selectedItem);
		//	upButton.setEnabled(selectedIndex > 0);
		//	downButton.setEnabled(selectedIndex < (arrayItems.size() - 1));
			deleteButton.setEnabled(!fixedLengthArray);
			
		} else {
		//	downButton.setEnabled(false);
		//	upButton.setEnabled(false);
			deleteButton.setEnabled(false);
		}
		newButton.setEnabled(!fixedLengthArray);
	}
	
	private void addArrayItem() {
		int insertPos = arrayItems.size();
		Object selectedItem = selectedArrayItem();
		if (selectedItem != null) {
			insertPos = getItemIndex(selectedItem) + 1;
		}
		ISequencePropertySource sps = editValue.getValue();
		if (sps.isCompoundElement()) {
			sps.addCompoundProperty(insertPos);
		} else {
			sps.addSimpleProperty(insertPos, null);
		}
		tableViewer.setInput(editValue);
		tableViewer.setSelection(new StructuredSelection(arrayItems.get(insertPos)));
		setPropertyViewInputToSelectedItem();
		updateButtons();
	}
	
	private void moveItem(int delta) {
		tableViewer.cancelEditing();
		int currIndex = getItemIndex(selectedArrayItem());
		if (currIndex >= 0) {
			int newIndex = currIndex + delta;
			if (newIndex >= 0 && newIndex < arrayItems.size()) {
				editValue.getValue().move(currIndex, newIndex);
				tableViewer.setInput(editValue);
				tableViewer.getTable().select(newIndex);
				setPropertyViewInputToSelectedItem();
			}
		}
	}
	
	int getItemIndex(Object item) {
		int result = -1;
		if (arrayItems != null && item != null) {
			for (int i = 0; i < arrayItems.size(); i++) {
				if (arrayItems.get(i) == item) {
					result = i;
					break;
				}
			}
		}
		return result;
	}
	
	private void deleteArrayItem() {
		tableViewer.cancelEditing();
		Object selectedItem = selectedArrayItem();
		if (selectedItem != null) {
			ISequencePropertySource sps = editValue.getValue();
			int index = getItemIndex(selectedItem);
			if (index >= 0) {
				sps.remove(index);
				tableViewer.refresh();
				if (index >= arrayItems.size()) {
					index = arrayItems.size() - 1;
				}
				tableViewer.getTable().select(index);
				updateButtons();
			}
		}
	}
	
	static class StatusLineManager implements IStatusLineManager {
		
		private Label label;
		private String normalMessage;
		private String errorMessage;
		
		StatusLineManager(Label label) {
			this.label = label;
		}
		
		private void updateLabel() {
			if (!TextUtils.isEmpty(errorMessage)) {
				Color color = JFaceColors.getErrorText(Display.getCurrent());
				label.setForeground(color);
				label.setText(errorMessage);
			} else {
				Color color = Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_FOREGROUND);	
				label.setForeground(color);
				label.setText(normalMessage != null? normalMessage : ""); //$NON-NLS-1$
			}
		}

		public IProgressMonitor getProgressMonitor() {
			return null;
		}

		public boolean isCancelEnabled() {
			return false;
		}

		public void setCancelEnabled(boolean enabled) {
		}

		public void setErrorMessage(String message) {
			errorMessage = message;
			updateLabel();
		}

		public void setErrorMessage(Image image, String message) {
			setErrorMessage(message);
		}

		public void setMessage(String message) {
			normalMessage = message;
			updateLabel();
		}

		public void setMessage(Image image, String message) {
			setMessage(message);
		}

		public void add(IAction action) {
		}

		public void add(IContributionItem item) {
		}

		public void appendToGroup(String groupName, IAction action) {
		}

		public void appendToGroup(String groupName, IContributionItem item) {
		}

		public IContributionItem find(String id) {
			return null;
		}

		public IContributionItem[] getItems() {
			return null;
		}

		public IContributionManagerOverrides getOverrides() {
			return null;
		}

		public void insertAfter(String id, IAction action) {
		}

		public void insertAfter(String id, IContributionItem item) {
		}

		public void insertBefore(String id, IAction action) {
		}

		public void insertBefore(String id, IContributionItem item) {
		}

		public boolean isDirty() {
			return false;
		}

		public boolean isEmpty() {
			return false;
		}

		public void markDirty() {
		}

		public void prependToGroup(String groupName, IAction action) {
		}

		public void prependToGroup(String groupName, IContributionItem item) {
		}

		public IContributionItem remove(String id) {
			return null;
		}

		public IContributionItem remove(IContributionItem item) {
			return null;
		}

		public void removeAll() {
		}

		public void update(boolean force) {
		}
	}
	
	static class NullMenuManager implements IMenuManager {
		public void addMenuListener(IMenuListener listener) {
		}
		public IMenuManager findMenuUsingPath(String path) {
			return null;
		}
		public IContributionItem findUsingPath(String path) {
			return null;
		}
		public boolean getRemoveAllWhenShown() {
			return false;
		}
		public boolean isEnabled() {
			return false;
		}
		public void removeMenuListener(IMenuListener listener) {
		}
		public void setRemoveAllWhenShown(boolean removeAll) {
		}
		public void updateAll(boolean force) {
		}
		public void add(IAction action) {
		}
		public void add(IContributionItem item) {
		}
		public void appendToGroup(String groupName, IAction action) {
		}
		public void appendToGroup(String groupName, IContributionItem item) {
		}
		public IContributionItem find(String id) {
			return null;
		}
		public IContributionItem[] getItems() {
			return null;
		}
		public IContributionManagerOverrides getOverrides() {
			return null;
		}
		public void insertAfter(String id, IAction action) {
		}
		public void insertAfter(String id, IContributionItem item) {
		}
		public void insertBefore(String id, IAction action) {
		}
		public void insertBefore(String id, IContributionItem item) {
		}
		public boolean isDirty() {
			return false;
		}
		public boolean isEmpty() {
			return false;
		}
		public void markDirty() {
		}
		public void prependToGroup(String groupName, IAction action) {
		}
		public void prependToGroup(String groupName, IContributionItem item) {
		}
		public IContributionItem remove(String id) {
			return null;
		}
		public IContributionItem remove(IContributionItem item) {
			return null;
		}
		public void removeAll() {
		}
		public void update(boolean force) {
		}
		public void dispose() {
		}
		public void fill(Composite parent) {
		}
		public void fill(Menu parent, int index) {
		}
		public void fill(ToolBar parent, int index) {
		}
		public void fill(CoolBar parent, int index) {
		}
		public String getId() {
			return null;
		}
		public boolean isDynamic() {
			return false;
		}
		public boolean isGroupMarker() {
			return false;
		}
		public boolean isSeparator() {
			return false;
		}
		public boolean isVisible() {
			return false;
		}
		public void saveWidgetState() {
		}
		public void setParent(IContributionManager parent) {
		}
		public void setVisible(boolean visible) {
		}
		public void update() {
		}
		public void update(String id) {
		}
	}
	
	static class NullToolBarManager implements IToolBarManager {
		public void add(IAction action) {
		}
		public void add(IContributionItem item) {
		}
		public void appendToGroup(String groupName, IAction action) {
		}
		public void appendToGroup(String groupName, IContributionItem item) {
		}
		public IContributionItem find(String id) {
			return null;
		}
		public IContributionItem[] getItems() {
			return null;
		}
		public IContributionManagerOverrides getOverrides() {
			return null;
		}
		public void insertAfter(String id, IAction action) {
		}
		public void insertAfter(String id, IContributionItem item) {
		}
		public void insertBefore(String id, IAction action) {
		}
		public void insertBefore(String id, IContributionItem item) {
		}
		public boolean isDirty() {
			return false;
		}
		public boolean isEmpty() {
			return false;
		}
		public void markDirty() {
		}
		public void prependToGroup(String groupName, IAction action) {
		}
		public void prependToGroup(String groupName, IContributionItem item) {
		}
		public IContributionItem remove(String id) {
			return null;
		}
		public IContributionItem remove(IContributionItem item) {
			return null;
		}
		public void removeAll() {
		}
		public void update(boolean force) {
		}
	}
}
