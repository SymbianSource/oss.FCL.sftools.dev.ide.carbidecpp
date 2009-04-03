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


package com.nokia.sdt.component.symbian.actions;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.adapter.CommonAttributes;
import com.nokia.sdt.component.adapter.IAttributes;
import com.nokia.sdt.component.symbian.IFacetContainer;
import com.nokia.sdt.component.symbian.actionFilter.IActionFilterTest;
import com.nokia.sdt.component.symbian.displaymodel.Utilities;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.displaymodel.adapter.ILayoutContainer;
import com.nokia.sdt.editor.IDesignerEditor;
import com.nokia.sdt.uidesigner.ui.utils.EditorUtils;
import com.nokia.cpp.internal.api.utils.core.ILocalizedStrings;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import java.text.MessageFormat;
import java.util.Collections;

public class SwitchVisibleChildActionDelegate implements IObjectActionDelegate, IMenuCreator, IActionFilterTest {
	
	private static class SelectInstanceAction extends Action {
		private IComponentInstance instance;
		private final IDesignerEditor editor;
		
		public SelectInstanceAction(IComponentInstance instance, IDesignerEditor editor) {
			this.instance = instance;
			this.editor = editor;
			setText(instance.getName());
		}

		@Override
		public void run() {
			EditorUtils.setDisplayModelSelectedObjects(editor.getDisplayModel(), 
									Collections.singletonList(instance.getEObject()));
		}

	}

	private EObject container;
	protected IDesignerEditor editor;

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
	}

	public void run(IAction action) {
	}

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
						container = findSwitchableContainerWantingMenu(instance.getEObject());
						updateActionForContainer(action, container);
						return;
					}
				}
			}
		}

		this.container = null;
		action.setEnabled(false);
	}

	public boolean test(Object target, Notifier notifier) {
		// this filters whether the item even appears in a menu 
		if (notifier instanceof EObject) {
			return findSwitchableContainerWantingMenu((EObject) notifier) != null;
		}
		
		return false;
	}
	
	private static boolean switchesVisibleChild(EObject object) {
		IComponentInstance ci = Utilities.getComponentInstance(object);
		if (ci == null)
			return false;
		
		IAttributes attributes = (IAttributes) ci.getComponent().getAdapter(IAttributes.class);
		return attributes.getBooleanAttribute(CommonAttributes.SWITCHABLE_CHILD_CONTAINER, false);
	}
	
	private static boolean wantsSwitchingMenu(EObject object) {
		IComponentInstance ci = Utilities.getComponentInstance(object);
		if (ci == null)
			return false;
		
		IAttributes attributes = (IAttributes) ci.getComponent().getAdapter(IAttributes.class);
		return attributes.getBooleanAttribute(CommonAttributes.SWITCHABLE_CHILD_CONTEXT_MENU, false);
	}
	
	private static EObject findSwitchableContainerWantingMenu(EObject selected) {
		IComponentInstance ci = Utilities.getComponentInstance(selected);
		if (ci == null)
			return null;
		
		if (switchesVisibleChild(selected) && wantsSwitchingMenu(selected))
			return selected;
		
		return findSwitchableContainerWantingMenu(ci.getParent());
	}

	protected void updateActionForContainer(IAction action, EObject container) {
		if (container == null)
			return;
		IComponent component = Utilities.getComponentInstance(container).getComponent();
		IAttributes attributes = (IAttributes) component.getAdapter(IAttributes.class);
		String childLabel = attributes.getAttribute(CommonAttributes.SWITCHABLE_CHILD_CONTEXT_MENU_CHILD_LABEL);
		if (childLabel != null) {
			IFacetContainer facetContainer = (IFacetContainer) component.getAdapter(IFacetContainer.class);
			ILocalizedStrings localizedStrings = facetContainer.getLocalizedStrings();
			if (localizedStrings != null) {
				childLabel = localizedStrings.checkPercentKey(childLabel);
			}
			action.setText(MessageFormat.format(
					Messages.getString("SwitchVisibleChildActionDelegate.SwitchMenuFormat"), //$NON-NLS-1$
					new Object[] { childLabel }));
		}
		action.setEnabled(!Utilities.getLayoutChildren(container).isEmpty());
		action.setMenuCreator(this);
	}

	public void dispose() {
	}

	public Menu getMenu(Control parent) {
		return null;
	}

	public Menu getMenu(Menu parent) {
		Menu menu = new Menu(parent);
		menu.addMenuListener(new MenuAdapter() {
			public void menuShown(MenuEvent e) {
				fillMenu((Menu) e.getSource());
			}
		});
		return menu;
	}

	protected void fillMenu(Menu menu) {
		if (menu == null) {
			return;
		}
		
		MenuItem[] items = menu.getItems();
		for (int i = 0; i < items.length; i++) {
			items[i].dispose();
		}
		
		ILayoutContainer layoutContainer = Utilities.getLayoutContainer(container);
		EObject curVisibleChild = layoutContainer.getVisibleChildren()[0];
		for (EObject child : Utilities.getLayoutChildren(container)) {
			IComponentInstance ci = Utilities.getComponentInstance(child);
			IAction action = new SelectInstanceAction(ci, editor);
			if (child.equals(curVisibleChild))
				action.setChecked(true);
			ActionContributionItem item = new ActionContributionItem(action);
			item.fill(menu, -1);
		}
	}
		
}
