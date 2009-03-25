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
package com.nokia.carbide.cpp.internal.project.ui.mmpEditor;

import java.util.*;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
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
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.nokia.carbide.cdt.builder.EpocEngineHelper;
import com.nokia.carbide.cpp.internal.project.ui.editors.common.*;
import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.commands.EMMPListSelector;
import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.dialogs.AddLibraryDialog;
import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.dialogs.EditLibraryDialog;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.TextUtils;
import com.nokia.cpp.internal.api.utils.ui.editor.FormEditorEditingContext;

public class LibrarySectionPart extends SectionPart {
	
	private Button downButton;
	public static final String LIB_EXTENSION = "lib"; //$NON-NLS-1$
	
	private final MMPEditorContext editorContext;
	private final EMMPListSelector listSelector;
	private final ControlManager controlManager;
	private Button upButton;
	private Button removeButton;
	private Button editButton;
	private Button addButton;
	private TableViewer tableViewer;
	private Composite buttonComposite;
	
	class TableLabelProvider extends LabelProvider implements ITableLabelProvider {
		public String getColumnText(Object element, int columnIndex) {
			String text = element.toString();
			String extension = TextUtils.getExtension(text);
			if (LIB_EXTENSION.equalsIgnoreCase(extension)) {
				text = text.substring(0, text.length() - (LIB_EXTENSION.length() + 1));
			}
			return text;
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
	public LibrarySectionPart(MMPEditorContext editorContext, 
			EMMPListSelector listSelector,
			Composite parent, FormToolkit toolkit, int style,
			ControlManager controlManager) {
		super(parent, toolkit, style);
		this.editorContext = editorContext;
		this.listSelector = listSelector;
		this.controlManager = controlManager;
		
		createClient(getSection(), toolkit);
	}

	@Override
	public boolean setFormInput(Object input) {
		// input is expected to be the List<String> from the mmp model
		Check.checkArg(input instanceof List);
		tableViewer.setInput(input);
		List list = (List) input;
		if (list.size() > 0) {
			this.getSection().setExpanded(true);
		}
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

		tableViewer = new TableViewer(body, SWT.MULTI);
		tableViewer.setLabelProvider(new TableLabelProvider());
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setInput(new Object());
		Table table = tableViewer.getTable();
		final FormData formData = new FormData();
		formData.left = new FormAttachment(0, 5);
		formData.top = new FormAttachment(0, 5);
		table.setLayoutData(formData);
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
		formData_1.right = new FormAttachment(0, 380);
		buttonComposite.setLayoutData(formData_1);
		final RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
		rowLayout.marginTop = 0;
		rowLayout.wrap = false;
		rowLayout.marginBottom = 0;
		rowLayout.fill = true;
		rowLayout.pack = false;
		buttonComposite.setLayout(rowLayout);
		
		controlManager.add(new ControlHandler(tableViewer, false));
		tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				updateButtons();
			}
		});

		addButton = toolkit.createButton(buttonComposite, Messages.LibrarySectionPart_addBtn, SWT.NONE);
		addButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				addButtonPressed();
			}
		});

		editButton = toolkit.createButton(buttonComposite, Messages.LibrarySectionPart_editBtn, SWT.NONE);
		editButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				editButtonPressed();
			}
		});

		upButton = toolkit.createButton(buttonComposite, Messages.LibrarySectionPart_upBtn, SWT.NONE);
		upButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				upButtonPressed();
			}
		});

		downButton = toolkit.createButton(buttonComposite, Messages.LibrarySectionPart_downBtn, SWT.NONE);
		downButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				downButtonPressed();
			}
		});

		removeButton = toolkit.createButton(buttonComposite, Messages.LibrarySectionPart_removeBtn, SWT.NONE);
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
		Set<String> sdkLibraries = EpocEngineHelper.getSDKLibrariesForBuildContext(editorContext.activeBuildConfig);
		// remove already included libraries from the list so we don't show them as candidates for adding
		List<String> includedList = (List<String>) tableViewer.getInput();
		sdkLibraries.removeAll(includedList);
		
		AddLibraryDialog dlg = new AddLibraryDialog(getSection().getShell(), sdkLibraries);
		int dlgResult = dlg.open();
		if (dlgResult == Dialog.OK) {
			List<String> libNames = dlg.getLibraryNames();
			if (libNames != null && libNames.size() > 0) {
				// ensure the names have the .lib extension
				for (int i = 0; i < libNames.size(); i++) {
					String libName = libNames.get(i);
					String extension = TextUtils.getExtension(libName);
					if (!LIB_EXTENSION.equalsIgnoreCase(extension)) {
						libName += "." + LIB_EXTENSION; //$NON-NLS-1$
						libNames.set(i, libName);
					}
				}
				
				AddListValueOperation op = new AddListValueOperation(
						editorContext.mmpView, 
						new FormEditorEditingContext(editorContext.editor, tableViewer.getControl()),
						ControlHandler.getHandlerForControl(tableViewer.getControl()), 
						listSelector, libNames);
				editorContext.executeOperation(op);
				refresh();
			}
		}
	}

	protected void editButtonPressed() {
		ISelection selection = tableViewer.getSelection();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ss = (IStructuredSelection) selection;
			String originalLibName = ss.getFirstElement().toString();
			int dotPos = originalLibName.lastIndexOf("."); //$NON-NLS-1$
			if (dotPos > 0 && dotPos < originalLibName.length()-1) {
				originalLibName = originalLibName.substring(0, dotPos);
			}
	
			EditLibraryDialog dlg = new EditLibraryDialog(getSection().getShell(), originalLibName);
			int dlgResult = dlg.open();
			if (dlgResult == Dialog.OK) {
				String newLibName = dlg.getLibraryName();
				String extension = TextUtils.getExtension(newLibName);
				if (!LIB_EXTENSION.equalsIgnoreCase(extension)) {
					newLibName += "." + LIB_EXTENSION; //$NON-NLS-1$
				}
				
				Map<Integer, Object> replaceMap = new HashMap<Integer, Object>();
				int selectionIndex = tableViewer.getTable().getSelectionIndex();
				replaceMap.put(Integer.valueOf(selectionIndex), newLibName);

				ReplaceListValueOperation op = new ReplaceListValueOperation(
						editorContext.mmpView, 
						new FormEditorEditingContext(editorContext.editor, tableViewer.getControl()),
						ControlHandler.getHandlerForControl(tableViewer.getControl()), 
						listSelector, replaceMap);
				editorContext.executeOperation(op);
				refresh();
			}
		}
	}

	@SuppressWarnings("unchecked") //$NON-NLS-1$
	protected void removeButtonPressed() {
		int[] indicesArray = tableViewer.getTable().getSelectionIndices();
		List<Integer> indices = new ArrayList<Integer>();
		for (int index : indicesArray) {
			indices.add(index);
		}
		RemoveListValueOperation op = new RemoveListValueOperation(
				editorContext.mmpView, 
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
			moveMap.put(index, index + delta);
			MoveListValueOperation op = new MoveListValueOperation(
					editorContext.mmpView, 
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
