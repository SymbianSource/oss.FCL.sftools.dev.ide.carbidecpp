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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IPath;
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
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.nokia.carbide.cdt.builder.EMMPPathContext;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPAIFInfo;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPResource;
import com.nokia.carbide.cpp.internal.project.ui.editors.common.ControlManager;
import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.ResourcePresentationModel.ResourceBlockNode;
import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.TreePresentationModel.*;
import com.nokia.carbide.cpp.ui.CarbideUIPlugin;
import com.nokia.carbide.cpp.ui.ICarbideSharedImages;

public class ResourcesSectionPart extends SectionPart {
	
	private MMPEditorContext editorContext;	
	private ResourcePresentationModel model;
	private ControlManager controlManager;
	private TreeViewer resourceTreeViewer;
	private Button removeButton;
	private Button editButton;
	private Button addButton;
	
	class TreeLabelProvider extends LabelProvider {
		
		ICarbideSharedImages sharedImages;
		
		TreeLabelProvider() {
			sharedImages = CarbideUIPlugin.getSharedImages();
		}
		
		public String getText(Object element) {
			String result = ""; //$NON-NLS-1$
			if (element instanceof TreePresentationModel.ITreeNode) {
				TreePresentationModel.ITreeNode node = (TreePresentationModel.ITreeNode) element;
				result = node.getDisplayName();
			} else if (element instanceof IMMPResource) {
				IMMPResource r = (IMMPResource) element;
				IPath path = r.getSource();
				IPath projPath = editorContext.pathHelper.convertMMPToProject(EMMPPathContext.START_RESOURCE, path);
				result = projPath != null? projPath.toString() : path.toString();
			} else if (element instanceof IMMPAIFInfo) {
				IMMPAIFInfo info = (IMMPAIFInfo) element;
				result = info.getTarget() != null ? info.getTarget().toString() : ""; //$NON-NLS-1$
			}
			else if (element != null) {
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
	public ResourcesSectionPart(Composite parent, FormToolkit toolkit, int style) {
		super(parent, toolkit, style);
		createClient(getSection(), toolkit);
	}
	
	public void initialize(MMPEditorContext editorContext, ControlManager controlManager) {
		this.editorContext = editorContext;
		this.controlManager = controlManager;
		this.model = new ResourcePresentationModel(editorContext);		

		resourceTreeViewer.setLabelProvider(new TreeLabelProvider());
		resourceTreeViewer.setContentProvider(model);
		resourceTreeViewer.setInput(model.getRoot());
		
		hookControls();
		refresh();
		
		resourceTreeViewer.expandAll();
		refresh();
	}

	/**
	 * Fill the section
	 */
	private void createClient(Section section, FormToolkit toolkit) {
		section.setText(Messages.ResourcesSectionPart_resourcesSectionTitle);
		section.setDescription(Messages.ResourcesSectionPart_resourcesDescription);
		Composite container = toolkit.createComposite(section);
		final FormLayout formLayout = new FormLayout();
		formLayout.marginHeight = 2;
		formLayout.marginWidth = 2;
		container.setLayout(formLayout);
		toolkit.paintBordersFor(container);
		//
		section.setClient(container);

		resourceTreeViewer = new TreeViewer(container, SWT.NONE);
		Tree tree = resourceTreeViewer.getTree();
		final FormData formData = new FormData();
		formData.right = new FormAttachment(0, 247);
		formData.bottom = new FormAttachment(100, -3);
		formData.top = new FormAttachment(0, 3);
		formData.left = new FormAttachment(0, 3);
		tree.setLayoutData(formData);
		toolkit.adapt(tree, true, true);
		
		resourceTreeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				updateButtons();
			}
		});
		resourceTreeViewer.addDoubleClickListener(new IDoubleClickListener() {
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

		final Composite composite = new Composite(container, SWT.NONE);
		final RowLayout buttonComposite = new RowLayout(SWT.VERTICAL);
		buttonComposite.marginTop = 0;
		buttonComposite.fill = true;
		buttonComposite.pack = false;
		composite.setLayout(buttonComposite);
		final FormData formData_1 = new FormData();
		formData_1.bottom = new FormAttachment(0, 90);
		formData_1.left = new FormAttachment(tree, 7, SWT.DEFAULT);
		formData_1.top = new FormAttachment(0, 3);
		composite.setLayoutData(formData_1);
		toolkit.adapt(composite);

		addButton = toolkit.createButton(composite, Messages.ResourcesSectionPart_addBtn, SWT.NONE);
		addButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				doAdd();
			}
		});

		editButton = toolkit.createButton(composite, Messages.ResourcesSectionPart_editBtn, SWT.NONE);
		editButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				doEdit();
			}
		});

		removeButton = toolkit.createButton(composite, Messages.ResourcesSectionPart_removeBtn, SWT.NONE);
		removeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				doRemove();
			}
		});
	}
	
	class ResourceTreeViewerHandler extends TreeViewerHandler {

		public ResourceTreeViewerHandler(AbstractTreeViewer viewer) {
			super(viewer, true);
		}
		
		@Override
		public void refresh() {
			model.refreshFromModel();
			super.refresh();
		}

		@Override
		public void refreshElement(Object element) {
			model.refreshFromModel();
			super.refreshElement(element);
		}

		@Override
		public List<Object> modelToViewerElements(List<Object> elements) {
			List<Object> result = new ArrayList<Object>();
			for (Object object : elements) {
				if (object instanceof IMMPResource) {
					ResourceBlockNode rbn = model.findBlockForMMPResource((IMMPResource)object);
					if (rbn != null) {
						object = rbn;
					}
				}
				result.add(object);
			}
			return result;
		}

		@Override
		public List<Object> viewerToModelElements(List<Object> elements) {
			List<Object> result = new ArrayList<Object>();
			for (Object object : elements) {
				if (object instanceof ResourceBlockNode) {
					ResourceBlockNode rbn = (ResourceBlockNode) object;
					result.add(rbn.getMMPResource());
				} else {
					result.add(object);
				}
			}
			return result;
		}
	}
	
	private void hookControls() {
		controlManager.add(new ResourceTreeViewerHandler(resourceTreeViewer));
	}

	protected TreeObjectRef getViewerSelection() {
		// the tree view is set to single selection		
		TreeObjectRef result = null;
		ISelection selection = resourceTreeViewer.getSelection();
		if (selection instanceof ITreeSelection) {
			List<TreeObjectRef> mapSelection = model.mapTreeViewerSelection((ITreeSelection) selection);
			if (mapSelection != null && mapSelection.size() > 0) {
				result = mapSelection.get(0);
			}
		}
		return result;
	}

	/** 
	 * If a container is selected, returns the container.
	 * If a non-container element is selected returns the parent of the element
	 * @return
	 */
	TreePresentationModel.ITreeNode getSelectedContainer() {
		ITreeNode result = null;
		TreeObjectRef selection = getViewerSelection();
		if (selection != null) {
			if (selection.object instanceof ITreeNode) {
				result = (ITreeNode) selection.object;
			} else {
				result = selection.parent;
			}
		}
		return result;
	}
	
	/**
	 * Always returns the parent container of the selection, regardless of whether
	 * the selection is a container or not
	 * @return
	 */
	TreePresentationModel.ITreeNode getContainerOfSelection() {
		TreePresentationModel.ITreeNode result = null;
		TreeObjectRef selection = getViewerSelection();
		if (selection != null) {
			result = selection.parent == model.getRoot()? null : selection.parent;
		}
		return result;
	}

	protected void doAdd() {
		TreePresentationModel.ITreeNode container = getSelectedContainer();
		if (container != null) {
			container.doAdd();
		}
	}

	protected void doEdit() {
		TreeObjectRef selection = getViewerSelection();
		if (selection != null) {
			ITreeNode container = getSelectedContainer();
			if (container != null) {
				container.doEdit(selection.object);
			}
		}
	}
	
	void setSelection(ITreeNode node, Object element) {
		TreePath treePath = model.getTreePath(node, element);
		TreeSelection ts = new TreeSelection(treePath);
		resourceTreeViewer.setSelection(ts);
	}

	protected void doRemove() {
		TreeObjectRef selection = getViewerSelection();
		if (selection != null) {
			setSelection(selection.parent, null);
			selection.parent.doRemove(selection.object);
		}
	}
	
	protected void viewerDoubleClicked() {
		doEdit();
	}
	
	@Override
	public void refresh() {
		super.refresh();
		updateButtons();
	}
	
	protected void updateButtons() {
		Object selection = null;
		TreeObjectRef ts = getViewerSelection();
		if (ts != null) {
			selection = ts.object;
		}
		// since containers can contain other containers the selected container and the
		// container of the selection may be different
		ITreeNode selectedContainer = getSelectedContainer();
		ITreeNode containerOfSelection = getContainerOfSelection();
		boolean addEnabled = selectedContainer != null && selectedContainer.canAdd();
		boolean removeEnabled = containerOfSelection != null && containerOfSelection.canRemove(selection);
		ITreeNode editContainer = containerOfSelection != null? containerOfSelection : selectedContainer;
		boolean editEnabled = editContainer != null && editContainer.canEdit(selection);
		
		addButton.setEnabled(addEnabled);
		editButton.setEnabled(editEnabled);
		removeButton.setEnabled(removeEnabled);
	}
}
