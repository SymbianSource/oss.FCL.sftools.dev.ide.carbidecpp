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
package com.nokia.carbide.cpp.internal.project.ui.editors.inf;

import java.util.*;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IExport;
import com.nokia.carbide.cpp.internal.project.ui.editors.common.*;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.ui.editor.FormEditorEditingContext;

public class ExportSectionPart extends SectionPart {
		
	private final BldInfEditorContext editorContext;
	private final EBldInfListSelector listSelector;
	private final ControlManager controlManager;
	private final boolean isTest;
	private Button addButton;
	private Button editButton;
	private Button upButton;
	private Button downButton;
	private Button removeButton;
	private TableViewer tableViewer;
	private Composite buttonComposite;
	
	
	private final int COLUMN_SOURCE = 0;
	private final int COLUMN_TARGET = 1;
	private final int COLUMN_ZIP = 2;
	
	class TableLabelProvider extends LabelProvider implements ITableLabelProvider {
		public String getColumnText(Object element, int columnIndex) {
			if (element instanceof IExport) {
				IExport export = (IExport)element;
				if (columnIndex == COLUMN_SOURCE)
					return export.getSourcePath().toOSString();
				if (columnIndex == COLUMN_TARGET) {
					IPath target = export.getTargetPath();
					if (target != null)
						return target.toOSString();
					else
						return new Path("/epoc32/include").append(export.getSourcePath()).toOSString(); //$NON-NLS-1$
				}
				if (columnIndex == COLUMN_ZIP) {
					return Boolean.valueOf(export.isZipped()).toString();
				}
			}
			return element.toString();
		}
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}
	}
	
	/**
	 * Create the SectionPart
	 * @param parent
	 * @param toolkit
	 * @param style
	 */
	public ExportSectionPart(BldInfEditorContext editorContext, 
			EBldInfListSelector listSelector,
			Composite parent, FormToolkit toolkit, int style,
			ControlManager controlManager, boolean isTest) {
		super(parent, toolkit, style);
		this.editorContext = editorContext;
		this.listSelector = listSelector;
		this.controlManager = controlManager;
		this.isTest = isTest;
		
		createClient(getSection(), toolkit);
	}

	@Override
	public boolean setFormInput(Object input) {
		// input is expected to be the List<String> from the bld.inf model
		Check.checkArg(input instanceof List);
		tableViewer.setInput(input);
		return true;
	}

	/**
	 * Fill the section
	 */
	private void createClient(Section section, FormToolkit toolkit) {
		
		Composite body = toolkit.createComposite(section);
		final FormLayout formLayout = new FormLayout();
		formLayout.marginHeight = 2;
		formLayout.marginWidth = 2;
		body.setLayout(formLayout);
		toolkit.paintBordersFor(body);
		//
		section.setClient(body);

		tableViewer = new TableViewer(body, SWT.MULTI + SWT.BORDER + SWT.FULL_SELECTION);
		tableViewer.setLabelProvider(new TableLabelProvider());
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setInput(new Object());
		Table table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		
		TableColumn tableColumn = new TableColumn(table, SWT.LEFT);
		tableColumn.setText(Messages.ExportSectionPart_SourceColumn_Label);
		tableColumn = new TableColumn(table, SWT.LEFT);
		tableColumn.setText(Messages.ExportSectionPart_TargetColumn_Label);
		tableColumn = new TableColumn(table, SWT.CENTER);
		tableColumn.setText(Messages.ExportSectionPart_ZippedColumn_Label);
		
		TableLayout tableLayout = new TableLayout();
		table.setLayout(tableLayout);

		tableLayout.addColumnData(new ColumnWeightData(40, true));
		tableLayout.addColumnData(new ColumnWeightData(40, true));
		tableLayout.addColumnData(new ColumnWeightData(10, true));

		final FormData formData = new FormData();
		formData.top = new FormAttachment(0, 5);
		formData.left = new FormAttachment(0, 5);
		table.setLayoutData(formData);
		toolkit.paintBordersFor(table);
		toolkit.adapt(table, true, true);
		tableViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				editButtonPressed();
			}
		});
		table.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.character == SWT.DEL) {
					removeButtonPressed();
				}
			}
		});

		buttonComposite = toolkit.createComposite(body, SWT.NONE);
		formData.bottom = new FormAttachment(buttonComposite, 15, SWT.BOTTOM);
		formData.right = new FormAttachment(buttonComposite, -5, SWT.LEFT);
		final FormData formData_1 = new FormData();
		formData_1.top = new FormAttachment(0, 5);
		formData_1.right = new FormAttachment(0, 580);
		buttonComposite.setLayoutData(formData_1);
		final RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
		rowLayout.marginTop = 0;
		rowLayout.wrap = false;
		rowLayout.marginBottom = 0;
		rowLayout.fill = true;
		rowLayout.pack = false;
		buttonComposite.setLayout(rowLayout);
		toolkit.paintBordersFor(buttonComposite);
		
		controlManager.add(new ControlHandler(tableViewer, false));
		tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				updateButtons();
			}
		});

		addButton = toolkit.createButton(buttonComposite, Messages.ExportSectionPart_Add, SWT.NONE);
		addButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				addButtonPressed();
			}
		});

		editButton = toolkit.createButton(buttonComposite, Messages.ExportSectionPart_Edit, SWT.NONE);
		editButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				editButtonPressed();
			}
		});

		upButton = toolkit.createButton(buttonComposite, Messages.ExportSectionPart_Up, SWT.NONE);
		upButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				upButtonPressed();
			}
		});

		downButton = toolkit.createButton(buttonComposite, Messages.ExportSectionPart_Down, SWT.NONE);
		downButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				downButtonPressed();
			}
		});

		removeButton = toolkit.createButton(buttonComposite, Messages.ExportSectionPart_Remove, SWT.NONE);
		removeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				removeButtonPressed();
			}
		});
		
		updateButtons();
	}
	
	@SuppressWarnings("unchecked") //$NON-NLS-1$
	protected void addButtonPressed() {
		IExport newExport = editorContext.bldInfView.createExport();
		
		ExportFileDialog dlg = new ExportFileDialog(getSection().getShell(), isTest ? Messages.ExportSectionPart_AddTestExport : Messages.ExportSectionPart_AddExport, newExport, editorContext.project);
		int dlgResult = dlg.open();
		if (dlgResult == Dialog.OK) {
			List<IExport> addedList = new ArrayList<IExport>();
			addedList.add(newExport);

			AddListValueOperation op = new AddListValueOperation(
					editorContext.bldInfView, 
					new FormEditorEditingContext(editorContext.editor, tableViewer.getControl()),
					ControlHandler.getHandlerForControl(tableViewer.getControl()), 
					listSelector, addedList);
			editorContext.executeOperation(op);
			refresh();
		}
	}

	protected void editButtonPressed() {
		ISelection selection = tableViewer.getSelection();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ss = (IStructuredSelection) selection;
			IExport export = (IExport)ss.getFirstElement();
			
			// make a copy of the export object - this is required for undo to work properly
			IExport newExport = editorContext.bldInfView.createExport();
			newExport.setSourcePath(export.getSourcePath());
			newExport.setTargetPath(export.getTargetPath());
			newExport.setZipped(export.isZipped());

			ExportFileDialog dlg = new ExportFileDialog(getSection().getShell(), isTest ? Messages.ExportSectionPart_EditTestExport : Messages.ExportSectionPart_EditExport, newExport, editorContext.project);
			int dlgResult = dlg.open();
			if (dlgResult == Dialog.OK) {

				Map<Integer, Object> replaceMap = new HashMap<Integer, Object>();
				int selectionIndex = tableViewer.getTable().getSelectionIndex();
				replaceMap.put(selectionIndex, newExport);

				ReplaceListValueOperation op = new ReplaceListValueOperation(
						editorContext.bldInfView, 
						new FormEditorEditingContext(editorContext.editor, tableViewer.getControl()),
						ControlHandler.getHandlerForControl(tableViewer.getControl()), 
						listSelector, replaceMap);
				editorContext.executeOperation(op);
				refresh();
			}
		}
	}

	protected void removeButtonPressed() {
		int[] indicesArray = tableViewer.getTable().getSelectionIndices();
		List<Integer> indices = new ArrayList<Integer>();
		for (int index : indicesArray) {
			indices.add(index);
		}
		RemoveListValueOperation op = new RemoveListValueOperation(
				editorContext.bldInfView, 
				new FormEditorEditingContext(editorContext.editor, tableViewer.getControl()),
				ControlHandler.getHandlerForControl(tableViewer.getControl()), 
				listSelector, indices);
		editorContext.executeOperation(op);
		refresh();
	}

	protected void upButtonPressed() {
		moveSelectedItem(-1);
	}
	
	protected void downButtonPressed() {
		moveSelectedItem(1);
	}
	
	private void moveSelectedItem(int delta) {
		int index = tableViewer.getTable().getSelectionIndex();
		if (index >= 0) {
			Map<Integer, Integer> moveMap = new HashMap<Integer, Integer>();
			moveMap.put(index, index+delta);
			MoveListValueOperation op = new MoveListValueOperation(
					editorContext.bldInfView, 
					new FormEditorEditingContext(editorContext.editor, tableViewer.getControl()),
					ControlHandler.getHandlerForControl(tableViewer.getControl()), 
					listSelector, moveMap);
			editorContext.executeOperation(op);
			refresh();
		}				
	}
	
	public void refresh() {
		super.refresh();
		controlManager.refresh();
		updateButtons();
	}

	private void updateButtons() {
		int selectionSize = 0;
		int firstSelection = tableViewer.getTable().getSelectionIndex();
		ISelection selection = tableViewer.getSelection();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ss = (IStructuredSelection) selection;
			selectionSize = ss.size();
		}
		editButton.setEnabled(selectionSize == 1);
		removeButton.setEnabled(selectionSize > 0);
		upButton.setEnabled(selectionSize == 1 && firstSelection > 0);
		downButton.setEnabled(selectionSize == 1 && 
				firstSelection < tableViewer.getTable().getItemCount() - 1);
	}
}
