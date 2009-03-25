/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.sdt.uidesigner.ui;

import com.nokia.sdt.displaymodel.adapter.IContainer;
import com.nokia.sdt.editor.*;
import com.nokia.sdt.uidesigner.ui.command.CreationFactory;
import com.nokia.sdt.uidesigner.ui.editparts.ModelObjectOutlineEditPartFactory;
import com.nokia.sdt.uidesigner.ui.utils.Adapters;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.*;
import org.eclipse.gef.dnd.TemplateTransferDropTargetListener;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.parts.ContentOutlinePage;
import org.eclipse.gef.ui.parts.TreeViewer;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.part.PageBook;

import java.util.*;
import java.util.List;

/**
 * Creates an outline pagebook for this editor.
 */
public class ViewEditorOutlinePage extends ContentOutlinePage implements IAdaptable {
	/** 
	 * Pointer to the editor instance associated with this outline page.
	 * Used for calling back on editor methods.
	 */
	private final IDesignerEditor editor;
	/** A pagebook can contain multiple pages (just one in our case). */
	private PageBook pagebook;
	private Control outline;
	
	/**
	 * Create a new outline page for the view editor.
	 * @param editor the editor associated with this outline page (non-null)
	 * @throws IllegalArgumentException if editor is null
	 */
	public ViewEditorOutlinePage(IDesignerEditor editor) {
		super(new TreeViewer());
		Check.checkArg(editor);
		this.editor = editor;
	}

	public void init(IPageSite pageSite) {
		super.init(pageSite);
		ActionRegistry registry = editor.getActionRegistry();
		IActionBars bars = pageSite.getActionBars();
		for (Iterator iter = registry.getActions(); iter.hasNext();) {
			Action action = (Action) iter.next();
			String id = action.getId();
			bars.setGlobalActionHandler(id, registry.getAction(id));
		}
		bars.updateActionBars();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.IPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		pagebook = new PageBook(parent, SWT.NONE);

		outline = getViewer().createControl(pagebook);
		configureOutlineViewer();
		hookOutlineViewer();
		initializeOutlineViewer();
		pagebook.showPage(outline);
	}

	protected void hookOutlineViewer() {
		editor.getSelectionManager().addViewer(getViewer());
	}
	
	protected void configureOutlineViewer() {
		getViewer().setEditDomain(editor.getEditDomain());
		getViewer().setEditPartFactory(new ModelObjectOutlineEditPartFactory());
		ContextMenuProvider cmProvider = new ViewEditorContextMenuProvider(
				getViewer(), editor.getActionRegistry()); 
		getViewer().setContextMenu(cmProvider);
		getSite().registerContextMenu(
				"com.nokia.sdt.uidesigner.view.outline.contextmenu", //$NON-NLS-1$
				cmProvider,	editor.getSelectionManager());		
		getViewer().setKeyHandler(editor.getCommonKeyHandler());
		TemplateTransferDropTargetListener dropTargetListener = 
										new TemplateTransferDropTargetListener(getViewer()) {
			protected CreationFactory getFactory(Object template) {
				Check.checkState(template instanceof ICreationTool);
				setEnablementDeterminedByCommand(true);
				ICreationTool tool = (ICreationTool) template;
				return new CreationFactory(tool);
			}
		};
		getViewer().addDropTargetListener(
				(org.eclipse.jface.util.TransferDropTargetListener) dropTargetListener);
	}
	
	private static boolean wantsToBeCollapsed(EditPart part) {
		Object model = part.getModel();
		if (model instanceof EObject) {
			IContainer container = Adapters.getContainer((EObject) model);
			return container != null && container.collapsedInOutline();
		}
		
		return false;
	}
	
	private static void setExpandedAll(TreeItem treeItem) {
		Object data = treeItem.getData();
		if (!(data instanceof EditPart) || !wantsToBeCollapsed((EditPart) data))
			treeItem.setExpanded(true);
		List itemsList = Arrays.asList(treeItem.getItems());
		for (Iterator iter = itemsList.iterator(); iter.hasNext();) {
			TreeItem childItem = (TreeItem) iter.next();
			setExpandedAll(childItem);
		}
	}

	public static void setExpandedAll(EditPart part) {
		TreeEditPart treeEditPart = (TreeEditPart) part;
		Widget widget = treeEditPart.getWidget();
		if (widget instanceof Tree) {
			TreeItem[] rootItems = ((Tree) widget).getItems();
			List rootItemsList = Arrays.asList(rootItems);
			for (Iterator iter = rootItemsList.iterator(); iter.hasNext();) {
				setExpandedAll((TreeItem) iter.next());
			}
		}
		else {
			setExpandedAll((TreeItem) widget);
		}
	}

	public void initializeOutlineViewer() {
		if (getControl() == null)
			return;
		setContents(editor.getModelObject());
		setExpandedAll(((TreeViewer) getViewer()).getRootEditPart());
	}

	protected void setContents(Object contents) {
		getViewer().setContents(contents);
	}
	
	protected void unhookOutlineViewer() {
		editor.getSelectionManager().removeViewer(getViewer());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.IPage#dispose()
	 */
	public void dispose() {
		unhookOutlineViewer();
		super.dispose();
		IDesignerDataModelEditor ddme = 
			(IDesignerDataModelEditor) editor.getAdapter(IDesignerDataModelEditor.class);
		ddme.disposeOutlinePage();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.IPage#getControl()
	 */
	public Control getControl() {
		return pagebook;
	}

	public EditPartViewer getViewer() {
		return super.getViewer();
	}

	public Object getAdapter(Class adapter) {
		if (IDesignerEditor.class.equals(adapter))
			return editor;
		return null;
	}
}