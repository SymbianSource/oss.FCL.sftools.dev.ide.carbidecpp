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

import com.nokia.carbide.cdt.builder.EpocEnginePathHelper;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.*;
import com.nokia.carbide.cpp.internal.project.ui.editors.common.*;
import com.nokia.carbide.cpp.internal.project.ui.wizards.MMPWizard;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;
import com.nokia.cpp.internal.api.utils.ui.editor.FormEditorEditingContext;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.*;
import org.eclipse.ui.ide.IDE;

import java.util.*;
import java.util.List;

public class MakMakeFilesSectionPart extends SectionPart {
		
	private final BldInfEditorContext editorContext;
	private final EBldInfListSelector listSelector;
	private final ControlManager controlManager;
	private final boolean isTest;
	private Button createMMPButton;
	private Button addMMPButton;
	private Button addMakeButton;
	private Button editButton;
	private Button launchEditorButton;
	private Button upButton;
	private Button downButton;
	private Button removeButton;
	private TableViewer tableViewer;
	private Composite buttonComposite;
	private MMPWizard mmpWizard;
	private MMPFileDialog mmpFileDialog;
	private MakeFileDialog makeFileDialog;
	private IEditorPart componentEditor;
	
	private final static String YES = Messages.MakMakeFilesSectionPart_Yes;
	private final static String NO = Messages.MakMakeFilesSectionPart_No;
	
    
	class TableLabelProvider extends LabelProvider implements ITableLabelProvider {
		public String getColumnText(Object element, int columnIndex) {
			if (element instanceof IMakMakeReference) {
				IMakMakeReference ref = (IMakMakeReference)element;
				
				if (isTest) {
					switch (columnIndex) {
					case 0: return ref.getPath().toOSString();
					case 1: return ref.isTidy() ? YES : NO;
					case 2: return ref.isManual() ? YES : NO;
					case 3: return ref.isSupport() ? YES : NO;
					}
				} else {
					switch (columnIndex) {
					case 0: return ref.getPath().toOSString();
					case 1: return ref.isTidy() ? YES : NO;
					case 2: return ref.isBuildAsArm() ? YES : NO;
					}
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
	public MakMakeFilesSectionPart(BldInfEditorContext editorContext, 
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
		body.setLayout(new FormLayout());
		toolkit.paintBordersFor(body);
		//
		section.setClient(body);

		final TableWrapData tableWrapData_3 = new TableWrapData(TableWrapData.LEFT, TableWrapData.TOP);
		tableWrapData_3.colspan = 2;
		section.setLayoutData(tableWrapData_3);

		final Table table = new Table(body, SWT.V_SCROLL | SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION);
		final FormData formData = new FormData();
		formData.top = new FormAttachment(0, 5);
		formData.left = new FormAttachment(0, 5);
		table.setLayoutData(formData);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		final TableColumn fileColumn = new TableColumn(table, SWT.NONE, 0);
		fileColumn.setWidth(449);
		fileColumn.setText(Messages.MakMakeFilesSectionPart_Component);

		final TableColumn tidyColumn = new TableColumn(table, SWT.NONE, 1);
		tidyColumn.setWidth(35);
		tidyColumn.setText(Messages.MakMakeFilesSectionPart_Tidy);
		
		if (isTest) {
			final TableColumn manualColumn = new TableColumn(table, SWT.NONE, 2);
			manualColumn.setWidth(50);
			manualColumn.setText(Messages.MakMakeFilesSectionPart_Manual);
			
			final TableColumn supportColumn = new TableColumn(table, SWT.NONE, 3);
			supportColumn.setWidth(50);
			supportColumn.setText(Messages.MakMakeFilesSectionPart_Support);
		} else {
			final TableColumn buildAsArmColumn = new TableColumn(table, SWT.NONE, 2);
			buildAsArmColumn.setWidth(100);
			buildAsArmColumn.setText(Messages.MakMakeFilesSectionPart_BuildAsArm);
		}

		tableViewer = new TableViewer(table);
		tableViewer.setLabelProvider(new TableLabelProvider());
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setInput(new Object());

		toolkit.paintBordersFor(table);
		toolkit.adapt(table, true, true);
		tableViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				launchEditorButtonPressed();
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
		formData_1.right = new FormAttachment(0, 680);
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

		createMMPButton = toolkit.createButton(buttonComposite, Messages.MakMakeFilesSectionPart_CreateMMP, SWT.NONE);
		createMMPButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				createMMPFileButtonPressed();
			}
		});

		addMMPButton = toolkit.createButton(buttonComposite, Messages.MakMakeFilesSectionPart_AddMMP, SWT.NONE);
		addMMPButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				addMMPFileButtonPressed();
			}
		});

		addMakeButton = toolkit.createButton(buttonComposite, Messages.MakMakeFilesSectionPart_AddMake, SWT.NONE);
		addMakeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				addMakeFileButtonPressed();
			}
		});

		editButton = toolkit.createButton(buttonComposite, Messages.MakMakeFilesSectionPart_Edit, SWT.NONE);
		editButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				editButtonPressed();
			}
		});

		launchEditorButton = toolkit.createButton(buttonComposite, Messages.MakMakeFilesSectionPart_Open, SWT.NONE);
		launchEditorButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				launchEditorButtonPressed();
			}
		});

		upButton = toolkit.createButton(buttonComposite, Messages.MakMakeFilesSectionPart_Up, SWT.NONE);
		upButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				upButtonPressed();
			}
		});

		downButton = toolkit.createButton(buttonComposite, Messages.MakMakeFilesSectionPart_Down, SWT.NONE);
		downButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				downButtonPressed();
			}
		});

		removeButton = toolkit.createButton(buttonComposite, Messages.MakMakeFilesSectionPart_Remove, SWT.NONE);
		removeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				removeButtonPressed();
			}
		});
		
		updateButtons();
	}
	
	protected void createMMPFileButtonPressed() {
		mmpWizard = new MMPWizard(isTest);
		WizardDialog dlg = new WizardDialog(WorkbenchUtils.getActiveShell(), mmpWizard);
		if (dlg.open() == Window.OK) {
			refresh();
		}
	}

	protected void addMMPFileButtonPressed() {
		mmpFileDialog = new MMPFileDialog(getSection().getShell(), null, editorContext, isTest);
		int dlgResult = mmpFileDialog.open();
		if (dlgResult == Dialog.OK) {
			List<IMakMakeReference> addedList = new ArrayList<IMakMakeReference>();
			addedList.add(mmpFileDialog.getMakeMakeFile());

			AddListValueOperation op = new AddListValueOperation(
					editorContext.bldInfView, 
					new FormEditorEditingContext(editorContext.editor, tableViewer.getControl()),
					ControlHandler.getHandlerForControl(tableViewer.getControl()), 
					listSelector, addedList);
			editorContext.executeOperation(op);
			refresh();
		}
	}

	protected void addMakeFileButtonPressed() {
		makeFileDialog = new MakeFileDialog(getSection().getShell(), null, editorContext, isTest);
		int dlgResult = makeFileDialog.open();
		if (dlgResult == Dialog.OK) {
			List<IMakMakeReference> addedList = new ArrayList<IMakMakeReference>();
			addedList.add(makeFileDialog.getMakeMakeFile());

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
			IMakMakeReference ref = (IMakMakeReference)ss.getFirstElement();
			
			IMakMakeFileDialog dlg;
			
			if (ref instanceof IMMPReference) {
				dlg = new MMPFileDialog(getSection().getShell(), (IMMPReference)ref, editorContext, isTest);
				mmpFileDialog = (MMPFileDialog) dlg;
			} else {
				dlg = new MakeFileDialog(getSection().getShell(), (IMakefileReference)ref, editorContext, isTest);
				makeFileDialog = (MakeFileDialog) dlg;
			}

			int dlgResult = dlg.show();
			if (dlgResult == Dialog.OK) {

				Map<Integer, Object> replaceMap = new HashMap<Integer, Object>();
				int selectionIndex = tableViewer.getTable().getSelectionIndex();
				replaceMap.put(selectionIndex, dlg.getMakeMakeFile());

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

	protected void launchEditorButtonPressed() {
		ISelection selection = tableViewer.getSelection();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ss = (IStructuredSelection) selection;
			IMakMakeReference ref = (IMakMakeReference)ss.getFirstElement();
			
			EpocEnginePathHelper pathHelper = new EpocEnginePathHelper(editorContext.project);

			try {
				componentEditor = IDE.openEditor(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage(), editorContext.project.getFile(pathHelper.convertToProject(ref.getPath())));
			} catch (PartInitException e) {
				e.printStackTrace();
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
		launchEditorButton.setEnabled(selectionSize == 1);
		removeButton.setEnabled(selectionSize > 0);
		upButton.setEnabled(selectionSize == 1 && firstSelection > 0);
		downButton.setEnabled(selectionSize == 1 && 
				firstSelection < tableViewer.getTable().getItemCount() - 1);
	}

	public TableViewer getTableViewer() {
		return tableViewer;
	}

	public Button getCreateMMPButton() {
		return createMMPButton;
	}

	public Button getAddMMPButton() {
		return addMMPButton;
	}

	public Button getAddMakeFileButton() {
		return addMakeButton;
	}

	public Button getEditButton() {
		return editButton;
	}

	public Button getOpenButton() {
		return launchEditorButton;
	}

	public Button getUpButton() {
		return upButton;
	}

	public Button getDownButton() {
		return downButton;
	}

	public Button getRemoveButton() {
		return removeButton;
	}

	public Wizard getMMPWizard() {
		return mmpWizard;
	}

	public Dialog getMMPFileDialog() {
		return mmpFileDialog;
	}

	public Dialog getMakeFileDialog() {
		return makeFileDialog;
	}

	public IEditorPart getComponentEditor() {
		return componentEditor;
	}
}
