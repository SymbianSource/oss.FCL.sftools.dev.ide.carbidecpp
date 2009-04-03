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

import com.nokia.carbide.cpp.internal.project.ui.editors.common.*;
import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.commands.EMMPListSelector;
import com.nokia.cpp.internal.api.utils.ui.editor.FormEditorEditingContext;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import java.util.*;
import java.util.List;

/**
 * Displays a list of MMP source files that couldn't be found in the project.
 */
public class MissingSourcesSectionPart extends SectionPart {

	private TableViewer tableViewer;
	private Button removeButton;
	private MMPEditorContext editorContext;
	private List<IPath> missingFilePaths;
	private final SourcesPage page;


	public MissingSourcesSectionPart(Composite parent, FormToolkit toolkit,
			int style, SourcesPage page) {
		super(parent, toolkit, style);
		this.page = page;
		createClient(getSection(), toolkit);
	}

	private void createClient(Section section, FormToolkit toolkit) {
		section.setText(Messages.MissingSourcesSectionPart_missingSourcesSectionTitle);
		section.setDescription(Messages.MissingSourcesSectionPart_missingSourcesDescription);
		Composite container = toolkit.createComposite(section);
		toolkit.paintBordersFor(container);
		container.setLayout(new FormLayout());
		//
		section.setClient(container);

		final Composite buttonComposite = toolkit.createComposite(container, SWT.NONE);
		final RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
		rowLayout.fill = true;
		rowLayout.wrap = false;
		rowLayout.marginRight = 1;
		rowLayout.marginLeft = 1;
		buttonComposite.setLayout(rowLayout);
		final FormData formData = new FormData();
		formData.bottom = new FormAttachment(100, -5);
		formData.right = new FormAttachment(100, 0);
		formData.top = new FormAttachment(0, 0);
		buttonComposite.setLayoutData(formData);

		removeButton = toolkit.createButton(buttonComposite, Messages.MissingSourcesSectionPart_removeButton, SWT.NONE);
		removeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				removeButtonPressed();
			}
		});

		tableViewer = new TableViewer(container, SWT.MULTI);
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setLabelProvider(new LabelProvider());
		Table table = tableViewer.getTable();
		toolkit.adapt(table);
		final FormData formData_1 = new FormData();
		formData_1.bottom = new FormAttachment(100, -9);
		formData_1.right = new FormAttachment(buttonComposite, -5, SWT.LEFT);
		formData_1.top = new FormAttachment(0, 3);
		formData_1.left = new FormAttachment(0, 5);
		table.setLayoutData(formData_1);
		toolkit.adapt(table, true, true);
		tableViewer.setInput(new Object());
		table.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.character == SWT.DEL) {
					removeButtonPressed();
				}
			}
		});
	}
	
	private void hookControls(ControlManager controlManager) {
		controlManager.add(new MissingSourceControlHandler(tableViewer));
	}

	public void initialize(IManagedForm managedForm, MMPEditorContext editorContext, ControlManager controlManager) {
		this.editorContext = editorContext;
		initialize(managedForm);
		hookControls(controlManager);
	}

	@Override
	public void refresh() {
		super.refresh();
		tableViewer.setInput(missingFilePaths);
	}

	public void setMissingFilePaths(List<IPath> paths) {
		missingFilePaths = paths;
	}
	
	protected void removeButtonPressed() {
		List<Integer> indices = new ArrayList<Integer>();
		List sourceList = EMMPListSelector.SOURCES.fetchList(editorContext.mmpView);
		IStructuredSelection selection = (IStructuredSelection) tableViewer.getSelection();
		Iterator iter = selection.iterator();
		while (iter.hasNext()) {
			int index = sourceList.indexOf(iter.next());
			if (index >= 0) {
				indices.add(index);
			}
		}
		
		RemoveListValueOperation command = new RemoveListValueOperation(
				editorContext.mmpView, 
				new FormEditorEditingContext(editorContext.editor, tableViewer.getControl()),
				ControlHandler.getHandlerForViewer(tableViewer),
				EMMPListSelector.SOURCES,
				indices);
		editorContext.executeOperation(command);
		
		missingFilePaths.removeAll(selection.toList());
		tableViewer.refresh();
	}
	
	class MissingSourceControlHandler extends ControlHandler {

		public MissingSourceControlHandler(StructuredViewer viewer) {
			super(viewer, true);
		}

		@SuppressWarnings("unchecked") //$NON-NLS-1$
		@Override
		public void addListItems(List items) {
			// adding and removing affects both the sources tree and
			// this list. To keep in sync we refresh all
			refresh();
		}

		@Override
		public void removeListItems(List items) {
			refresh();
		}

		@Override
		protected void doRefresh() {
			// get the page to update both the sources tree
			// and the missing files list.
			page.refresh();
		}
	}
}
