/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.sdt.test.componentLibrary.editorPages;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.adapter.IDesignerImages;
import com.nokia.sdt.datamodel.*;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.editor.*;
import com.nokia.sdt.workspace.IDesignerDataModelSpecifier;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.*;

import java.util.*;
import java.util.List;

public class TestEditorPage extends FormPage {

	private class TreeLabelProvider extends LabelProvider {
		public String getText(Object element) {
			if (element instanceof EObject) {
				IComponentInstance ci = 
					ModelUtils.getComponentInstance((EObject) element);
				return ci.getName();
			}
			return super.getText(element);
		}
		public Image getImage(Object element) {
			if (element instanceof EObject) {
				IComponentInstance ci = 
					ModelUtils.getComponentInstance((EObject) element);
				IComponent component = ci.getComponent();
				if (component != null) {
					IDesignerImages images = 
						(IDesignerImages) component.getAdapter(IDesignerImages.class);
					if (images != null)
						return images.getSmallIcon();
				}
			}
			return null;
		}
	}
	
	private class TreeContentProvider implements ITreeContentProvider {
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
		public void dispose() {
		}
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof IDesignerDataModelEditor) {
				return ((IDesignerDataModelEditor) inputElement).getDataModel().getRootContainers();
			}
			
			return null;
		}
		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof EObject) {
				IComponentInstance ci = 
					ModelUtils.getComponentInstance((EObject) parentElement);
				EObject[] children = ci.getChildren();
				List<EObject> childList = new ArrayList();
				for (EObject child : children) {
					IComponentEditor editor = ModelUtils.getComponentEditor(child);
					if (editor == null || !editor.isTemporaryObject())
						childList.add(child);
				}
				
				return (Object[]) childList.toArray(new Object[childList.size()]);
			}
			
			return null;
		}
		public Object getParent(Object element) {
			if (element instanceof EObject) {
				IComponentInstance ci = 
					ModelUtils.getComponentInstance((EObject) element);
				if (ci != null)
					return ci.getParent();
			}
			return null;
		}
		public boolean hasChildren(Object element) {
			Object[] children = getChildren(element);
			return children != null && children.length > 0;
		}
	}
	
	private Tree tree;
	private final IDesignerDataModelEditor dataModelEditor;
	private TreeViewer treeViewer;
	private ISelectionManager selectionManager;
	private IDesignerDataModelListener notifierListener;

	public TestEditorPage(IDesignerDataModelEditor editor, String id, String title) {
		super(editor.getFormEditor(), id, title);
		editor.getCommandStack().addCommandStackEventListener(new CommandStackEventListener() {
			public void stackChanged(CommandStackEvent event) {
				switch (event.getDetail()) {
				case CommandStack.POST_EXECUTE:
				case CommandStack.POST_REDO:
				case CommandStack.POST_UNDO:
					refresh(false);
				}
			}
		});
		DesignerDataModelNotifier.instance().addListener(notifierListener = new IDesignerDataModelListener() {
			public void dataModelChanged(IDesignerDataModelSpecifier modelSpecifier) {}
			public void dataModelInitialized(IDesignerDataModel model) {
				refresh(true); // in case the user reverts
			}
		});
		dataModelEditor = editor;
		IDesignerEditor designer = 
			(IDesignerEditor) dataModelEditor.getAdapter(IDesignerEditor.class);
		if (designer != null) {
			selectionManager = designer.getSelectionManager();
		}
	}
	
	private void refresh(boolean reload) {
		if (treeViewer != null)
			if (reload) {
				// wait for editor to get model after reload
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						if (treeViewer.getContentProvider() == null)
							return;
						
						treeViewer.setInput(dataModelEditor);
						treeViewer.expandAll();
					}
				});
			}
			else {
				treeViewer.refresh();
			}
	}

	@Override
	public void dispose() {
		if (notifierListener != null) {
			DesignerDataModelNotifier.instance().removeListener(notifierListener);
			notifierListener = null;
		}
		super.dispose();
	}
	
	@Override
	protected void createFormContent(IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		form.setText("Model Tree View");
		Composite body = form.getBody();
		final FillLayout fillLayout = new FillLayout(SWT.VERTICAL);
		fillLayout.marginHeight = 10;
		fillLayout.marginWidth = 10;
		body.setLayout(fillLayout);
		toolkit.paintBordersFor(body);

		final Section section = toolkit.createSection(body, SWT.NONE);
		section.setText(dataModelEditor.getDataModel().getModelSpecifier().getDisplayName());

		final Composite composite = toolkit.createComposite(section, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.VERTICAL));
		toolkit.paintBordersFor(composite);
		section.setClient(composite);

		treeViewer = new TreeViewer(composite, SWT.NONE);
		treeViewer.setLabelProvider(new TreeLabelProvider());
		treeViewer.setContentProvider(new TreeContentProvider());
		treeViewer.setInput(dataModelEditor);
		tree = treeViewer.getTree();
		toolkit.adapt(tree, true, true);
		treeViewer.expandAll();
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				ISelection selection = event.getSelection();
				if (selectionManager != null) {
					setSelectionToSelectionManager(selection);
				}
			}
		});
		if (selectionManager != null) {
			selectionManager.addSelectionChangedListener(new ISelectionChangedListener() {
				public void selectionChanged(SelectionChangedEvent event) {
					setSelectionToTree(event.getSelection());
				}
			});
		}
	}
	
	private void setSelectionToTree(ISelection selection) {
		ISelection eventSelection = convertToModelSelection(selection);
		if (!eventSelection.equals(treeViewer.getSelection()))
			treeViewer.setSelection(eventSelection);
	}
	
	private ISelection convertToModelSelection(ISelection selection) {
		List<EObject> objectList = new ArrayList();
		if (selection instanceof StructuredSelection) {
			for (Iterator iter = ((StructuredSelection) selection).iterator(); iter.hasNext();) {
				Object object = iter.next();
				if (object instanceof EditPart)
					objectList.add((EObject) ((EditPart) object).getModel());
				else if (object instanceof EObject)
					objectList.add((EObject) object);
			}
			selection = new StructuredSelection(objectList);	
		}
		
		return selection;
	}

	private void setSelectionToSelectionManager(ISelection selection) {
		if (!selection.isEmpty() &&
				!selection.equals(convertToModelSelection(selectionManager.getSelection())))
			selectionManager.setSelection(selection);
	}

}
