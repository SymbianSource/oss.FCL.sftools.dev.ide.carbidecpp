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

import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.cdt.internal.ui.editor.OpenIncludeAction;
import org.eclipse.cdt.internal.ui.search.actions.SelectionSearchGroup;
import org.eclipse.cdt.ui.actions.OpenViewActionGroup;
import org.eclipse.cdt.ui.refactoring.actions.CRefactoringActionGroup;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.actions.ContributionItemFactory;
import org.eclipse.ui.actions.ExportResourcesAction;
import org.eclipse.ui.actions.ImportResourcesAction;
import org.eclipse.ui.actions.NewWizardMenu;
import org.eclipse.ui.actions.WorkingSetFilterActionGroup;
import org.eclipse.ui.navigator.ICommonMenuConstants;

/**
 * The main action group for the SymbianProjectNavigatorView.
 */
public class MainActionGroup extends SPNViewActionGroup {

    NewWizardMenu newWizardMenu;

    ImportResourcesAction importAction;
	ExportResourcesAction exportAction;

	OpenIncludeAction openIncludeAction;

	OpenFileGroup openFileGroup;
	RefactorActionGroup refactorGroup;
	OpenProjectGroup openProjectGroup;
	CollapseAllAction collapseAllAction;

	SelectionSearchGroup selectionSearchGroup;
	OpenViewActionGroup openViewActionGroup;	
	CRefactoringActionGroup crefactoringActionGroup;

	WorkingSetFilterActionGroup workingSetGroup;

	
	public MainActionGroup(SymbianProjectNavigatorView view) {
		super(view);
	}

	/**
	 * Handles key events in viewer.
	 */
	public void handleKeyPressed(KeyEvent event) {
		openFileGroup.handleKeyPressed(event);
		refactorGroup.handleKeyPressed(event);
		openProjectGroup.handleKeyPressed(event);
	}

	/**
	 * Handles key events in viewer.
	 */
	public void handleKeyReleased(KeyEvent event) {
		openFileGroup.handleKeyReleased(event);
		refactorGroup.handleKeyReleased(event);
		openProjectGroup.handleKeyReleased(event);
	}

	protected void makeActions() {
        newWizardMenu = new NewWizardMenu(getView().getSite().getWorkbenchWindow());

        importAction = new ImportResourcesAction(getView().getSite().getWorkbenchWindow());
		exportAction = new ExportResourcesAction(getView().getSite().getWorkbenchWindow());

		openIncludeAction = new OpenIncludeAction(getView().getViewer());

		openFileGroup = new OpenFileGroup(getView());
		openProjectGroup = new OpenProjectGroup(getView());

		refactorGroup = new RefactorActionGroup(getView());

		collapseAllAction = new CollapseAllAction(getView());

		selectionSearchGroup = new SelectionSearchGroup(getView().getSite());
		openViewActionGroup= new OpenViewActionGroup(getView());
		crefactoringActionGroup= new CRefactoringActionGroup(getView());

		IPropertyChangeListener workingSetUpdater = new IPropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent event) {
				String property = event.getProperty();
                                 
				if (WorkingSetFilterActionGroup.CHANGE_WORKING_SET.equals(property)) {
					Object newValue = event.getNewValue();
                                         
					if (newValue instanceof IWorkingSet) {
						getView().setWorkingSet((IWorkingSet) newValue);
					} else if (newValue == null) {
						getView().setWorkingSet(null);
					}
				}
			}
		};
		workingSetGroup = new WorkingSetFilterActionGroup(view.getViewSite().getShell(), workingSetUpdater);
		workingSetGroup.setWorkingSet(getView().getWorkingSet());
	}

	public void fillContextMenu(IMenuManager menu) {
		MenuManager newMenu = new MenuManager(Messages.MainActionGroup_NewMenu);
        menu.add(newMenu);
        newMenu.add(newWizardMenu);
		menu.add(new Separator());
		
		menu.add(new Separator(ICommonMenuConstants.GROUP_OPEN));
		openFileGroup.fillContextMenu(menu);
		menu.add(new Separator());

		menu.add(new Separator(ICommonMenuConstants.GROUP_REORGANIZE));
		refactorGroup.fillContextMenu(menu);
		menu.add(new Separator("group.private1")); //$NON-NLS-1$

		IStructuredSelection elements = (IStructuredSelection) getView().getViewer().getSelection();
		if (OpenIncludeAction.canActionBeAdded(elements)) {
			menu.add(openIncludeAction);
			menu.add(new Separator());
		}

		menu.add(importAction);
		menu.add(exportAction);
		menu.add(new Separator());
		openProjectGroup.fillContextMenu(menu);
		menu.add(new Separator());

		menu.add(new Separator(ICommonMenuConstants.GROUP_SEARCH));
		addSearchMenu(menu, elements);

		if (elements.size() == 1) {
			Object selection = elements.getFirstElement();
			if (selection instanceof IAdaptable) {
				if (((IAdaptable)selection).getAdapter(IResource.class) != null) {
					MenuManager showInSubMenu = new MenuManager("Show in");
					showInSubMenu.add(ContributionItemFactory.VIEWS_SHOW_IN.create(view.getViewSite().getWorkbenchWindow()));
					menu.add(showInSubMenu); 
				}
			}
		}

		menu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		menu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS + "-end")); //$NON-NLS-1$

		menu.add(new Separator(ICommonMenuConstants.GROUP_PROPERTIES));
		openViewActionGroup.fillContextMenu(menu);
		crefactoringActionGroup.fillContextMenu(menu);
	}

	void addSearchMenu(IMenuManager menu, IStructuredSelection selection) {
		IAdaptable element = (IAdaptable) selection.getFirstElement();

		if (element instanceof ITranslationUnit || element instanceof ICProject) {
			return;
		}

		if (SelectionSearchGroup.canActionBeAdded(selection)){
			selectionSearchGroup.fillContextMenu(menu);
		}
	}

	/**
	 * Extends the superclass implementation to set the context in the
	 * subgroups.
	 */
	public void setContext(ActionContext context) {
		super.setContext(context);
		openFileGroup.setContext(context);
		refactorGroup.setContext(context);
		openProjectGroup.setContext(context);
		openViewActionGroup.setContext(context);
		crefactoringActionGroup.setContext(context);
	}

	public void runDefaultAction(IStructuredSelection selection) {
		openFileGroup.runDefaultAction(selection);
		refactorGroup.runDefaultAction(selection);
		openProjectGroup.runDefaultAction(selection);
	}

	public void updateActionBars() {
		openFileGroup.updateActionBars();
		refactorGroup.updateActionBars();
		openProjectGroup.updateActionBars();
		openViewActionGroup.updateActionBars();
		crefactoringActionGroup.updateActionBars();
		workingSetGroup.updateActionBars();
	}

	public void fillActionBars(IActionBars actionBars) {
		openFileGroup.fillActionBars(actionBars);
		refactorGroup.fillActionBars(actionBars);
		openProjectGroup.fillActionBars(actionBars);
		openViewActionGroup.fillActionBars(actionBars);
		crefactoringActionGroup.fillActionBars(actionBars);

		IToolBarManager toolBar = actionBars.getToolBarManager();
		toolBar.add(new Separator());
		toolBar.add(collapseAllAction);
		
		workingSetGroup.fillActionBars(actionBars);
	}

	public void dispose() {
		newWizardMenu.dispose();
		importAction.dispose();
		exportAction.dispose();
		openFileGroup.dispose();
		refactorGroup.dispose();
		openProjectGroup.dispose();
		openViewActionGroup.dispose();
		crefactoringActionGroup.dispose();
		workingSetGroup.dispose();
		super.dispose();
	}
	
}
