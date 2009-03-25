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


package com.nokia.sdt.series60.component.menu;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.symbian.displaymodel.Utilities;
import com.nokia.sdt.component.symbian.laf.Series60LAF;
import com.nokia.sdt.datamodel.adapter.*;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.displaymodel.ILookAndFeel;
import com.nokia.sdt.displaymodel.adapter.ILayoutObject;
import com.nokia.sdt.editor.*;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.sdt.utils.StatusHolder;
import com.nokia.sdt.utils.drawing.GC;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

public class MenuBarEditor
	implements IVisualAppearance, ILayout, IQueryContainment, IComponentEditor, IMenuEditor {

	private EObject eObject;
	private IComponentInstance componentInstance;
	private ILayoutObject layoutObject;
	private IDesignerEditor editor;
	private MenuEditManager menuEditManager;
	
	/**
	 * @param instance
	 */
	public MenuBarEditor(EObject instance) {
		eObject = instance;
		this.componentInstance = Utilities.getComponentInstance(getEObject());
		this.layoutObject = Utilities.getLayoutObject(getEObject());
		Check.checkArg(componentInstance);
		menuEditManager = new MenuEditManager(this);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.datamodel.adapter.IVisualAppearance#draw(com.nokia.sdt.utils.drawing.GC, com.nokia.sdt.displaymodel.ILookAndFeel)
	 */
	public void draw(GC gc, ILookAndFeel laf) {
		// only the children draw
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.datamodel.adapter.IVisualAppearance#getPreferredSize(int, int, com.nokia.sdt.displaymodel.ILookAndFeel)
	 */
	public Point getPreferredSize(int wHint, int hHint, ILookAndFeel laf) {
		Point size = new Point(0, 0);
		EObject[] children = componentInstance.getChildren();
		if ((children != null) && (children.length >= 1)) {
			IVisualAppearance visual = Utilities.getVisualAppearance(children[0]);
			size = visual.getPreferredSize(-1, -1, laf);
		}
		return size;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.datamodel.adapter.ILayout#layout(com.nokia.sdt.displaymodel.ILookAndFeel)
	 */
	public void layout(ILookAndFeel laf) {
		Check.checkState(layoutObject != null);
		// self
		Point size = getPreferredSize(-1, -1, laf);
		Point screenSize = laf.getDimension(Series60LAF.SCREEN_SIZE);
		
		Rectangle newBounds;
		if (laf.getBoolean(Series60LAF.IS_PORTRAIT, false)) {
			Rectangle contentBounds = laf.getRectangle(Series60LAF.CONTENT_PANE_BOUNDS);
			Rectangle controlPaneBounds = laf.getRectangle(Series60LAF.CONTROL_PANE_BOUNDS);
			newBounds = new Rectangle(contentBounds.x + (contentBounds.width - size.x) / 2, 
					controlPaneBounds.y - size.y, size.x, size.y);
		}
		else {
			newBounds = new Rectangle(screenSize.x - size.x - 3, 
					(screenSize.y - size.y) / 2, size.y, size.y);
		}
		layoutObject.setBounds(newBounds);

		// child
		EObject[] children = componentInstance.getChildren();
		if ((children != null) && (children.length >= 1)) {
			IVisualAppearance childVisual = Utilities.getVisualAppearance(children[0]);
			Point childSize = childVisual.getPreferredSize(-1, -1, laf);
			ILayoutObject childLayoutObject = Utilities.getLayoutObject(children[0]);
			childLayoutObject.setBounds(new Rectangle(0, 0, childSize.x, childSize.y));
		}
	}

	public boolean canContainComponent(IComponent component, StatusHolder statusHolder) {
		EObject[] children = componentInstance.getChildren();
		if ((children != null) && (children.length >= 1)) {
			Utilities.setStatus(statusHolder, Messages.getString("MenuBarEditor.ContainErrorSingleChild"), null); //$NON-NLS-1$
			return false;
		}
		
		boolean isMenuPane = ModelUtils.isOfType(component, MENUPANE_ID);
		if (!isMenuPane) {
			Utilities.setStatus(statusHolder, Messages.getString("MenuBarEditor.ContainError"), null); //$NON-NLS-1$
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
		return ModelUtils.isOfType(component, MENUPANE_ID);
	}

	public void beginEditing(IDesignerEditor editor) {
		menuEditManager.setMenuBarInstance(this);
		menuEditManager.setSelectedEditor(this, editor);
	}
	
	public void endEditing() {
		menuEditManager.unsetAllEditors();
	}

	public boolean isTemporaryObject() {
		return false;
	}
	
	public boolean isUserRemovable() {
		return true;
	}

	public Object getAdapter(Class adapter) {
		if (adapter.equals(getClass()) || adapter.equals(IMenuEditor.class))
			return this;
		
		return EcoreUtil.getRegisteredAdapter(getEObject(), adapter);
	}
	
	public void setEditor(IDesignerEditor editor) {
		this.editor = editor;
	}
	
	public IDesignerEditor getEditor() {
		return editor;
	}
	
	public boolean isSubMenu() {
		return false;
	}

	public MenuEditManager getMenuEditManager() {
		return menuEditManager;
	}

	public EObject getEObject() {
		return eObject;
	}
}
