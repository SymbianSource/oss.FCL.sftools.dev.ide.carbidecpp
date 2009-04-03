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
package com.nokia.carbide.cpp.internal.project.ui.views;

import java.util.Iterator;

import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.cdt.core.model.ISourceReference;
import org.eclipse.cdt.internal.ui.util.EditorUtility;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.*;
import org.eclipse.ui.actions.OpenFileAction;
import org.eclipse.ui.actions.OpenWithMenu;

/**
 * This is the action group for the open actions.
 */
public class OpenFileGroup extends SPNViewActionGroup {

	private OpenFileAction openFileAction;
	private OpenNonWorkspaceFileAction openNonWorkspaceFileAction;

	private OpenMBMMIFFileAction openMBMMIFAction;

    /**
     * The id for the Open With submenu.
     */
    public static final String OPEN_WITH_ID = PlatformUI.PLUGIN_ID
            + ".OpenWithSubMenu"; //$NON-NLS-1$

    
    public OpenFileGroup(SymbianProjectNavigatorView view) {
		super(view);
	}

	protected void makeActions() {
		openFileAction = new OpenFileAction(getView().getSite().getPage());
		openNonWorkspaceFileAction = new OpenNonWorkspaceFileAction(getView().getSite().getPage());
		openMBMMIFAction = new OpenMBMMIFFileAction(getView().getSite().getPage()); 
	}

	public void fillContextMenu(IMenuManager menu) {
		
        IStructuredSelection selection = (IStructuredSelection) getContext().getSelection();
		boolean onlyFilesSelected = !selection.isEmpty() && allResourcesAreOfType(selection, IResource.FILE);

		if (onlyFilesSelected) {
			openFileAction.selectionChanged(selection);
			menu.add(openFileAction);
			fillOpenWithMenu(menu, selection);
		}
		
		if (selection.size() == 1 && selection.getFirstElement() instanceof INonWorkspaceFileEntry) {
			openNonWorkspaceFileAction.selectionChanged(selection);
			menu.add(openNonWorkspaceFileAction);
		}

		if (selection.size() == 1 && selection.getFirstElement() instanceof IMBMMIFFileEntry) {
			openMBMMIFAction.selectionChanged(selection);
			menu.add(openMBMMIFAction);
		}
	}

	/**
	 * Adds the OpenWith submenu to the context menu.
	 * 
	 * @param menu
	 *            the context menu
	 * @param selection
	 *            the current selection
	 */
	private void fillOpenWithMenu(IMenuManager menu, IStructuredSelection selection) {
        // Only supported if exactly one file is selected.
        if (selection.size() != 1) {
			return;
		}
        Object element = selection.getFirstElement();
        if (!(element instanceof IAdaptable)) {
			return;
		}

        MenuManager submenu = new MenuManager(Messages.OpenFileGroup_OpenWith, OPEN_WITH_ID);
        submenu.add(new OpenWithMenu(view.getSite().getPage(), (IAdaptable) element));
        menu.add(submenu);
	}

    /* (non-Javadoc)
     * @see org.eclipse.ui.actions.ActionGroup#fillActionBars(org.eclipse.ui.IActionBars)
     */
    public void fillActionBars(IActionBars actionBars) {
    }
    /* (non-Javadoc)
     * @see org.eclipse.ui.actions.ActionGroup#updateActionBars()
     */
    public void updateActionBars() {
    }

	/**
	 * Runs the default action (open file).
	 */
	public void runDefaultAction(IStructuredSelection selection) {
        Object element = selection.getFirstElement();
		if (element instanceof ICElement) {
			ICElement celement = (ICElement) element;
			try {
				IEditorPart part = EditorUtility.openInEditor(celement);
				if (part != null) {
					IWorkbenchPage page = getView().getSite().getPage();
					page.bringToTop(part);
					if (celement instanceof ISourceReference) {
						EditorUtility.revealInEditor(part, celement);
					}
				}
			} catch (Exception e) {
			}
		} else if (element instanceof IAdaptable) {
            openFileAction.selectionChanged(selection);
            openFileAction.run();
            openNonWorkspaceFileAction.selectionChanged(selection);
            openNonWorkspaceFileAction.run();
            openMBMMIFAction.selectionChanged(selection);
            openMBMMIFAction.run();
        }
	}

    private boolean allResourcesAreOfType(IStructuredSelection selection, int resourceMask) {
    	Iterator resources = selection.iterator();
    	while (resources.hasNext()) {
            Object next = resources.next();
            IResource resource = null;
            if (next instanceof IResource) {
            	resource = (IResource) next;
            } else if (next instanceof IAdaptable) {
            	resource = (IResource) ((IAdaptable) next).getAdapter(IResource.class);
			}
            if (resource != null) {
                if (!resourceIsType(resource, resourceMask)) {
    				return false;
    			}
            }
            else {
				return false;
			}
        }
        return true;
    }

    public boolean resourceIsType(IResource resource, int resourceMask) {
        return (resource.getType() & resourceMask) != 0;
    }
}
