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

import java.text.MessageFormat;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
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
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.nokia.carbide.cpp.epoc.engine.model.mmp.EMMPStatement;
import com.nokia.carbide.cpp.internal.project.ui.editors.common.ControlManager;
import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.CompilerPresentationModel.IncludesContainer;
import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.TreePresentationModel.ITreeNode;
import com.nokia.carbide.cpp.ui.CarbideUIPlugin;
import com.nokia.carbide.cpp.ui.ICarbideSharedImages;
import com.nokia.cpp.internal.api.utils.ui.editor.FormEditorEditingContext;

public class CompilerSectionPart extends SectionPart {

	private final MMPEditorContext editorContext;
	private final ControlManager controlManager;
	private final CompilerPresentationModel model;
	private TreeViewer compilerTreeViewer;
	private Button editButton;
	private Button addButton;
	private Button removeButton;
	private Button upButton;
	private Button downButton;
	private Button srcdebugButton;
	private Button strictDependenciesButton;
	
	class TreeLabelProvider extends LabelProvider {
		
		ICarbideSharedImages sharedImages = CarbideUIPlugin.getSharedImages();
		
		public String getText(Object element) {
			String result = ""; //$NON-NLS-1$
			if (element instanceof TreePresentationModel.ITreeNode) {
				TreePresentationModel.ITreeNode container = (ITreeNode) element;
				result = container.getDisplayName();
			} else if (element instanceof Map.Entry) {
				Map.Entry entry = (Entry) element;
				String fmt = Messages.CompilerSectionPart_compilerOptionsFormat;
				Object params[] = {entry.getKey(), entry.getValue()};
				result = MessageFormat.format(fmt, params);
			} else if (element != null) {
				result = element.toString();
			}
			return result;
		}
		
		public Image getImage(Object element) {
			Image result = null;
			if (element instanceof TreePresentationModel.ITreeNode) {
				TreePresentationModel.ITreeNode node = (ITreeNode) element;
				String key = node.getImageKey();
				if (key != null) {
					result = sharedImages.getImage(key);
				}
			}
			return result;

		}
	}
	
	/**
	 * Create the SectionPart
	 * @param parent
	 * @param toolkit
	 * @param style
	 */
	public CompilerSectionPart(Composite parent, FormToolkit toolkit, int style,
			MMPEditorContext editorContext, ControlManager controlManager) {
		super(parent, toolkit, style);
		this.editorContext = editorContext;
		this.controlManager = controlManager;
		model = new CompilerPresentationModel(editorContext);
		createClient(getSection(), toolkit);
	}

	/**
	 * Fill the section
	 */
	private void createClient(Section section, FormToolkit toolkit) {
		section.setText(Messages.CompilerSectionPart_sectionLabel);
		Composite container = toolkit.createComposite(section);
		toolkit.paintBordersFor(container);
		final FormLayout formLayout = new FormLayout();
		formLayout.marginHeight = 2;
		formLayout.marginWidth = 2;
		container.setLayout(formLayout);
		//
		section.setClient(container);

		compilerTreeViewer = new TreeViewer(container, SWT.NONE);
		compilerTreeViewer.setAutoExpandLevel(AbstractTreeViewer.ALL_LEVELS);
		compilerTreeViewer.setLabelProvider(new TreeLabelProvider());
		compilerTreeViewer.setContentProvider(model);
		compilerTreeViewer.setInput(model.getRoot());
		Tree tree = compilerTreeViewer.getTree();
		final FormData formData = new FormData();
		formData.top = new FormAttachment(0, 10);
		formData.right = new FormAttachment(0, 203);
		formData.left = new FormAttachment(0, 10);
		tree.setLayoutData(formData);
		toolkit.adapt(tree, true, true);
		
		compilerTreeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				updateButtons();
			}
		});
		compilerTreeViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				viewerDoubleClicked();
			}
		});
		tree.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.character == SWT.DEL) {
					doRemove();
				}
			}
		});

		final Composite compilerButtonComposite = toolkit.createComposite(container, SWT.NONE);
		final RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
		rowLayout.fill = true;
		rowLayout.wrap = false;
		rowLayout.marginTop = 0;
		compilerButtonComposite.setLayout(rowLayout);
		final FormData formData_1 = new FormData();
		formData_1.bottom = new FormAttachment(0, 150);
		formData_1.left = new FormAttachment(tree, 7, SWT.DEFAULT);
		formData_1.top = new FormAttachment(0, 10);
		compilerButtonComposite.setLayoutData(formData_1);
		toolkit.paintBordersFor(compilerButtonComposite);

		addButton = toolkit.createButton(compilerButtonComposite, Messages.CompilerSectionPart_addBtn, SWT.NONE);
		addButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				doAdd();
			}		
		});
		
		editButton = toolkit.createButton(compilerButtonComposite, Messages.CompilerSectionPart_editBtn, SWT.NONE);
		editButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				doEdit();
			}		
		});

		removeButton = toolkit.createButton(compilerButtonComposite, Messages.CompilerSectionPart_removeBtn, SWT.NONE);
		removeButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				doRemove();
			}		
		});

		upButton = toolkit.createButton(compilerButtonComposite, Messages.CompilerSectionPart_upButton, SWT.NONE);
		upButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				doUp();
			}		
		});

		downButton = toolkit.createButton(compilerButtonComposite, Messages.CompilerSectionPart_downButton, SWT.NONE);
		downButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				doDown();
			}		
		});

		strictDependenciesButton = toolkit.createButton(container, Messages.CompilerSectionPart_perVariantIncludeDependenciesBtn, SWT.CHECK);
		strictDependenciesButton.setToolTipText(Messages.CompilerSectionPart_strictDependenciesTooltip);
		formData.bottom = new FormAttachment(strictDependenciesButton, -5, SWT.TOP);
		final FormData formData_2 = new FormData();
		formData_2.top = new FormAttachment(0, 239);
		formData_2.bottom = new FormAttachment(0, 255);
		formData_2.left = new FormAttachment(tree, 0, SWT.LEFT);
		strictDependenciesButton.setLayoutData(formData_2);

		srcdebugButton = toolkit.createButton(container, Messages.CompilerSectionPart_disableOptsButton, SWT.CHECK);
		srcdebugButton.setToolTipText(Messages.CompilerSectionPart_srcdebugTooltip);
		final FormData formData_3 = new FormData();
		formData_3.top = new FormAttachment(0, 260);
		formData_3.left = new FormAttachment(tree, 0, SWT.LEFT);
		srcdebugButton.setLayoutData(formData_3);
		
		compilerTreeViewer.expandAll();
		
		hookControls();
		refresh();
	}

	protected void viewerDoubleClicked() {
		Object selection = getViewerSelection();
		if (selection != null) {
			Object parent = model.getParent(selection);
			if (parent instanceof ITreeNode) {
				ITreeNode container = (ITreeNode) parent;
				container.doEdit(selection);
			}
		}
	}

	protected Object getViewerSelection() {
		// the tree view is set to single selection
		Object result = null;
		ISelection selection = compilerTreeViewer.getSelection();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ss = (IStructuredSelection) selection;
			result = ss.getFirstElement();
		}
		return result;
	}

	protected void updateButtons() {
		boolean addEnabled = false;
		boolean editEnabled = false;
		boolean removeEnabled = false;
		boolean upEnabled = false;
		boolean downEnabled = false;
		Object selection = getViewerSelection();
		ITreeNode container = getSelectedContainer();
		if (selection != null) {
			addEnabled = container != null && container.canAdd();
			removeEnabled = container != null && container.canRemove(selection);
			editEnabled = container != null && container.canEdit(selection);
			if (container instanceof CompilerPresentationModel.IncludesContainer) {
				CompilerPresentationModel.IncludesContainer ic = (IncludesContainer) container;
				upEnabled = ic.canMoveUp(selection);
				downEnabled = ic.canMoveDown(selection);
			}
		} 
		
		addButton.setEnabled(addEnabled);
		editButton.setEnabled(editEnabled);
		removeButton.setEnabled(removeEnabled);
		upButton.setEnabled(upEnabled);
		downButton.setEnabled(downEnabled);
	}

	private void hookControls() {
		controlManager.add(new CompilerTreeViewerHandler(model, compilerTreeViewer));
		controlManager.add(new FlagSettingHandler(srcdebugButton,
				new FormEditorEditingContext(editorContext.editor, srcdebugButton),
				EMMPStatement.SRCDBG, editorContext));
		controlManager.add(new FlagSettingHandler(strictDependenciesButton,
				new FormEditorEditingContext(editorContext.editor, strictDependenciesButton),
				EMMPStatement.STRICTDEPEND, editorContext));
	}

	@Override
	public void refresh() {
		super.refresh();
		updateButtons();
	}
	
	TreePresentationModel.ITreeNode getSelectedContainer() {
		TreePresentationModel.ITreeNode result = null;
		Object selection = getViewerSelection();
		if (selection instanceof TreePresentationModel.ITreeNode) {
			result = (ITreeNode) selection;
		} else if (selection != null) {
			Object parent = model.getParent(selection);
			if (parent instanceof TreePresentationModel.ITreeNode) {
				result = (ITreeNode) parent;
			}
		}
		return result;
	}
	
	private void doAdd() {
		TreePresentationModel.ITreeNode container = getSelectedContainer();
		if (container != null) {
			container.doAdd();
		}
	}
	
	private void doEdit() {
		Object element = getViewerSelection();
		TreePresentationModel.ITreeNode container = getSelectedContainer();
		if (container != null) {
			container.doEdit(element);
		}
	}
	
	private void doRemove() {
		Object element = getViewerSelection();
		TreePresentationModel.ITreeNode container = getSelectedContainer();
		if (container != null) {
			container.doRemove(element);
		}
	}
	
	private void doUp() {
		Object element = getViewerSelection();
		TreePresentationModel.ITreeNode container = getSelectedContainer();
		if (container instanceof CompilerPresentationModel.IncludesContainer) {
			CompilerPresentationModel.IncludesContainer ic = (IncludesContainer) container;
			ic.doMove(element, -1);
		}
	}
	
	private void doDown() {
		Object element = getViewerSelection();
		TreePresentationModel.ITreeNode container = getSelectedContainer();
		if (container instanceof CompilerPresentationModel.IncludesContainer) {
			CompilerPresentationModel.IncludesContainer ic = (IncludesContainer) container;
			ic.doMove(element, 1);
		}
	}

	CompilerPresentationModel getModel() {
		return model;
	}
}
