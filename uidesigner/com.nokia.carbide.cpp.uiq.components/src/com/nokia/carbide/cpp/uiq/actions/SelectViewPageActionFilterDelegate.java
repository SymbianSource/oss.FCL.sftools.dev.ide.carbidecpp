/*
* Copyright (c) 2008 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.carbide.cpp.uiq.actions;

import com.nokia.carbide.cpp.uiq.components.util.UiqUtils;
import com.nokia.sdt.component.symbian.actionFilter.BaseComponentActionFilterDelegate;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.displaymodel.IDisplayModel;
import com.nokia.sdt.displaymodel.adapter.ILayoutContainer;
import com.nokia.sdt.editor.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.IEditorPart;

import java.util.Collections;

public class SelectViewPageActionFilterDelegate extends BaseComponentActionFilterDelegate {
	
	private EObject viewLayout;

	@Override
	protected void executeActionCommand(IAction action, EObject selected, CommandStack commandStack) {
	}

	@Override
	protected boolean isActionVisibleForSelectedObject(EObject selected) {
		return UiqUtils.getViewLayout(selected) != null;
	}

	@Override
	protected void updateActionForSelectedObject(IAction action, EObject selected) {
		viewLayout =  UiqUtils.getViewLayout(selected);
		EObject[] viewpages = getViewPages(viewLayout);
		action.setEnabled(viewpages.length > 0);
		if (viewpages.length == 0)
			return;
		
		action.setMenuCreator(new IMenuCreator() {
			private Menu menu;

			public void dispose() {
				if (menu != null)
					menu.dispose();
				menu = null;
			}

			public Menu getMenu(Control parent) {
				return null;
			}

			private void fillMenu(Menu menu) {
				// clear
				MenuItem[] items = menu.getItems();
				for (int i = 0; i < items.length; i++) {
					items[i].dispose();
				}

				ILayoutContainer lc = ModelUtils.getLayoutContainer(viewLayout);
				EObject[] visChildren = lc.getVisibleChildren();
				if (visChildren.length == 0)
					return;
				
				final EObject visChild = visChildren[0];
				final EObject[] viewpages = getViewPages(viewLayout);
				for (final EObject viewpage : viewpages) {
					MenuItem item = new MenuItem(menu, SWT.CHECK);
					final IComponentInstance ci = ModelUtils.getComponentInstance(viewpage);
					item.setText(ci.getName());
					if (viewpage.equals(visChild)) {
						item.setSelection(true);
					}
					else {
						item.addSelectionListener(new SelectionListener() {
							public void widgetDefaultSelected(SelectionEvent e) {}
	
							public void widgetSelected(SelectionEvent e) {
								selectViewPage(viewpage);
							}

						});
					}
				}
			}
			
			public Menu getMenu(Menu parent) {
				if (menu == null) {
					menu = new Menu(parent);
					menu.addMenuListener(new MenuListener() {

						public void menuHidden(MenuEvent e) {
						}

						public void menuShown(MenuEvent e) {
							fillMenu(menu);
						}
					});
				}
				return menu;
			}
		});
	}

	private EObject[] getViewPages(final EObject viewLayout) {
		return viewLayout != null ? 
			ModelUtils.getComponentInstance(viewLayout).getChildren() : new EObject[0];
	}

	private void selectViewPage(EObject viewpage) {
		final IComponentInstance ci = ModelUtils.getComponentInstance(viewpage);
		IDesignerDataModel model = ci.getDesignerDataModel();
		IDisplayModel displayModel = model.getExistingDisplayModelForRootContainer(ci.getRootContainer());
		if (displayModel != null)
			displayModel.setSelectedObjects(new EObject[] { viewpage });
		IEditorPart editor = EditorServices.findEditor(model);
		if (editor != null) {
			IDesignerEditor designerEditor = (IDesignerEditor) editor.getAdapter(IDesignerEditor.class);
			if (designerEditor != null) {
				final ISelectionManager selectionManager = designerEditor.getSelectionManager();
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						selectionManager.setSelectionFromNames(Collections.singletonList(ci.getName()));
					}
				});
			}
		}
	}
	
}
