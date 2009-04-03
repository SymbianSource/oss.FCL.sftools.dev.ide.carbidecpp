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
/**
 * 
 */
package com.nokia.sdt.component.symbian.actionFilter;

import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.editor.IDesignerEditor;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
 * This base class is used to provide generic EObject-based context menu
 * entries to components.  The plugin XML for the popupMenus extension should
 * use a filter with name="action-filter-test-passes:&lt;override of this class&gt;"
 * and an action with class="&lt;override of this class&gt;". 
 * <p>
 * This implementation assumes only one object can be selected at a time.
 * <p>
 * This object does NOT survive between calls on IActionFilterTest and IObjectActionDelegate.
 * Do not store any state.
 * 
 *
 */
public abstract class BaseComponentActionFilterDelegate implements IObjectActionDelegate,
		IActionFilterTest {

	protected IDesignerEditor editor;
	protected GraphicalEditPart editPart;
	protected Shell shell;
	private EObject selected;

	/**
	 * 
	 */
	public BaseComponentActionFilterDelegate() {
	}

	/**
	 * Get the shell, valid while testing and executing 
	 * @return
	 */
	protected Shell getShell() {
		return shell;
	}
	
	/**
	 * Get the shell, valid while testing  
	 * @return
	 */
	protected IDesignerEditor getEditor() {
		return editor;
	}
	
	/**
	 * Get the shell, valid while testing and executing 
	 * @return
	 */
	protected GraphicalEditPart getEditPart() {
		return editPart;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction, org.eclipse.ui.IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		editor = (IDesignerEditor) targetPart.getAdapter(IDesignerEditor.class);
		if (editor == null) {
			// something in outline is selected
			ISelectionProvider provider = (ISelectionProvider) targetPart.getAdapter(ISelectionProvider.class);
			if (provider != null && provider.getSelection() instanceof IStructuredSelection) {
				Object selected = ((IStructuredSelection) provider.getSelection()).getFirstElement();
				if (selected instanceof IAdaptable) {
					editor = (IDesignerEditor) ((IAdaptable) selected).getAdapter(IDesignerEditor.class);
				}
			}
		}
		shell = targetPart.getSite().getShell();
		editPart = null;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.symbian.actionFilter.IActionFilterTest#test(org.eclipse.emf.common.notify.Notifier)
	 */
	public boolean test(Object target, Notifier notifier) {
		if (shell == null && target instanceof EditPart) {
			shell = ((EditPart) target).getViewer().getControl().getShell();
		}
		// this filters whether the item even appears in a menu 
		if (notifier instanceof EObject) {
			return isActionVisibleForSelectedObject((EObject) notifier);
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		// this filters whether the action is enabled for the given selection
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			if (structuredSelection.size() == 1) {
				Object firstElement = structuredSelection.getFirstElement();
				if (firstElement instanceof IAdaptable) {
					IAdaptable adaptable = (IAdaptable) firstElement;
					IComponentInstance instance = 
						(IComponentInstance) adaptable.getAdapter(IComponentInstance.class);
					if (instance != null) {
						EObject eObject = instance.getEObject();
						this.selected = eObject;
						if (firstElement instanceof GraphicalEditPart) {
							this.editPart = (GraphicalEditPart) firstElement;
							this.shell = editPart.getViewer().getControl().getShell();
						}
						updateActionForSelectedObject(action, eObject);
						return;
					}
				}
			}
		}

		this.editPart = null;
		this.selected = null;
		action.setEnabled(false);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		Check.checkState(editor != null);
		CommandStack stack = (CommandStack) editor.getAdapter(CommandStack.class);
		executeActionCommand(action, selected, stack);
	}

	/**
	 * Tell whether the action should be in the menu for the selected object.
	 */
	abstract protected boolean isActionVisibleForSelectedObject(EObject selected);
	
	/**
	 * Update the action given the currently selected object, including
	 * primarily its enabled state and action text.
	 */
	abstract protected void updateActionForSelectedObject(IAction action, EObject selected);

	/**
	 * Create and execute the command(s) to invoke the action.
	 * @param action TODO
	 */
	abstract protected void executeActionCommand(IAction action, EObject selected, CommandStack commandStack);

}
