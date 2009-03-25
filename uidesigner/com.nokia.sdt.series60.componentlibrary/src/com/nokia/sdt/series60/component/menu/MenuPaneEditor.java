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
package com.nokia.sdt.series60.component.menu;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.ui.views.properties.IPropertySource;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.symbian.displaymodel.Utilities;
import com.nokia.sdt.component.symbian.laf.Series60LAF;
import com.nokia.sdt.component.symbian.visual.javascript.Colors;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.ILayout;
import com.nokia.sdt.datamodel.adapter.IQueryContainment;
import com.nokia.sdt.datamodel.adapter.IVisualAppearance;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.displaymodel.ILookAndFeel;
import com.nokia.sdt.displaymodel.adapter.ILayoutObject;
import com.nokia.sdt.editor.IComponentEditor;
import com.nokia.sdt.editor.IDesignerEditor;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.sdt.utils.StatusHolder;
import com.nokia.sdt.utils.drawing.GC;

public class MenuPaneEditor implements IVisualAppearance, ILayout, 
		IQueryContainment, IComponentEditor, IMenuEditor {


	private EObject eObject;
	private IComponentInstance componentInstance;
	private ILayoutObject layoutObject;
	private IPropertySource propertySource;
	private IDesignerEditor editor;
	private MenuEditManager menuEditManager;

	public MenuPaneEditor(EObject instance) {
		eObject = instance;
		this.componentInstance = Utilities.getComponentInstance(getEObject());
		this.layoutObject = Utilities.getLayoutObject(getEObject());
		this.propertySource = Utilities.getPropertySource(getEObject());
		Check.checkArg(componentInstance);
		Check.checkArg(propertySource);
		menuEditManager = getMenuEditManager();
	}

	public void draw(GC gc, ILookAndFeel laf) {
		Check.checkState(layoutObject != null);
		gc.setBackground(Colors.getColor(255, 255, 255));
		Rectangle bounds = layoutObject.getBounds();
		Rectangle rect = new Rectangle(0, 0, bounds.width - 4, bounds.height - 4);
		gc.fillRectangle(rect);

		gc.setForeground(Colors.getColor(0, 0, 0));
		gc.drawRectangle(rect);
		
		// shadows
		gc.setForeground(laf.getColor(Series60LAF.CONTROL_SHADOW_INNER));
		gc.drawLine(1, rect.height + 1, rect.width + 1, rect.height + 1);
		gc.drawLine(rect.width + 1, 1, rect.width + 1, rect.height + 1);
		
		gc.setForeground(laf.getColor(Series60LAF.CONTROL_SHADOW_OUTER));
		gc.drawLine(2, rect.height + 2, rect.width + 2, rect.height + 2);
		gc.drawLine(rect.width + 2, 2, rect.width + 2, rect.height + 2);
	}

	public Point getPreferredSize(int wHint, int hHint, ILookAndFeel laf) {
		Point size = new Point(0, 7);
		EObject[] children = componentInstance.getChildren();
		if (children != null) {
			for (int i = 0; i < children.length; i++) {
				IVisualAppearance visual = Utilities.getVisualAppearance(children[i]);
				Point childSize = visual.getPreferredSize(-1, -1, laf);
				size.x = Math.max(size.x, childSize.x + 15);
				size.y += childSize.y + 2;
			}
		}
		return size;
	}
	
	public void layout(ILookAndFeel laf) {
		EObject[] children = componentInstance.getChildren();
		if (children == null)
			return;
		boolean isShowing = !isSubMenu() || (editor != null);
		int curY = 2;
		for (int i = 0; i < children.length; i++) {
			Point size = new Point(0, 0);
			if (isShowing) {
				IVisualAppearance visual = Utilities.getVisualAppearance(children[i]);
				size = visual.getPreferredSize(-1, -1, laf);
			}
			ILayoutObject layoutObject = Utilities.getLayoutObject(children[i]);
			Rectangle childBounds = new Rectangle(5, curY, size.x, size.y);
			layoutObject.setBounds(childBounds);
			curY += size.y + 2;
		}
	}

	public boolean canContainComponent(IComponent component, StatusHolder statusHolder) {
		boolean isMenuItem = ModelUtils.isOfType(component, MENUITEMBASE_ID);
		if (!isMenuItem) {
			Utilities.setStatus(statusHolder, Messages.getString("MenuPaneEditor.ContainError"), null); //$NON-NLS-1$
			return false;
		}
			
		return true;
	}

	public boolean canContainChild(IComponentInstance child, StatusHolder statusHolder) {
		IComponent component = child.getComponent();
		return canContainComponent(component, statusHolder);
	}

	public boolean canRemoveChild(IComponentInstance child) {
		return true;
	}

	public boolean isValidComponentInPalette(IComponent component) {
		return ModelUtils.isOfType(component, MENUITEMBASE_ID);
	}

	public void beginEditing(IDesignerEditor editor) {
		if (menuEditManager != null)
			menuEditManager.setSelectedEditor(this, editor);
	}
	
	public void endEditing() {
		if (menuEditManager != null)
			menuEditManager.unsetAllEditors();
	}
	
	public boolean isTemporaryObject() {
		return MenuEditManager.hasTemporaryName(getEObject());
	}
	
	public boolean isUserRemovable() {
		return !isTemporaryObject();
	}

	public Object getAdapter(Class adapter) {
		if (adapter.equals(getClass()) || adapter.equals(IMenuEditor.class))
			return this;
		
		return null;
	}
	
	public void setEditor(IDesignerEditor editor) {
		this.editor = editor;
	}

	public IDesignerEditor getEditor() {
		return editor;
	}
	
	public boolean isSubMenu() {
		EObject container = Utilities.getComponentInstance(getEObject()).getParent();
		return (container != null) &&
			!ModelUtils.isInstanceOf(container, MENUBAR_ID);
	}
	
	private MenuEditManager getMenuEditManager() {
		EObject parent = componentInstance.getParent();
		IComponentInstance parentInstance = Utilities.getComponentInstance(parent);
		while ((parentInstance != null) && 
				!ModelUtils.isInstanceOf(parentInstance.getEObject(), MENUBAR_ID)) {
			parent = parentInstance.getParent();
			parentInstance = Utilities.getComponentInstance(parent);
		}
		if (parentInstance == null)
			return null;

		IComponentEditor componentEditor = Utilities.getComponentEditor(parent);
		MenuBarEditor menuBarEditor = (MenuBarEditor) componentEditor.getAdapter(MenuBarEditor.class);
		return menuBarEditor.getMenuEditManager();
	}
	
	public EObject getEObject() {
		return eObject;
	}

}
