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

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.symbian.displaymodel.Utilities;
import com.nokia.sdt.component.symbian.laf.Series60LAF;
import com.nokia.sdt.component.symbian.visual.javascript.Colors;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.*;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.displaymodel.ILookAndFeel;
import com.nokia.sdt.displaymodel.adapter.ILayoutObject;
import com.nokia.sdt.editor.*;
import com.nokia.sdt.emf.dm.INode;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.sdt.utils.StatusHolder;
import com.nokia.sdt.utils.drawing.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.ui.views.properties.IPropertySource;

public class MenuItemEditor implements IVisualAppearance, ILayout, 
		IQueryContainment, IDirectLabelEdit, IComponentEditor, IMenuEditor, ISetValueCommandExtender {
	
	class MakeNonTemporaryCommand extends Command {
		private IDesignerDataModel dataModel;
		private EObject object;
		private String labelString;
		private String newName;
		private int index;
		private boolean paneWasTemporary;
		private String paneName;
		private String grandParentName;

		public MakeNonTemporaryCommand(EObject object, String label) {
			this.object = object;
			labelString = label;
			IComponentInstance instance = Utilities.getComponentInstance(object);
			dataModel = instance.getDesignerDataModel();
			EObject pane = instance.getParent();
			newName = MenuEditManager.getNewName(dataModel, object, label);
			paneWasTemporary = 
				(MenuEditManager.getMenuEditor(pane) != null) && MenuEditManager.hasTemporaryName(pane);
			IComponentInstance paneInstance = Utilities.getComponentInstance(pane);
			index = MenuEditManager.getChildIndex(paneInstance.getChildren(), object);
			paneName = paneInstance.getName();
			IComponentInstance grandParentInstance = Utilities.getComponentInstance(paneInstance.getParent());
			grandParentName = grandParentInstance.getName();
			setLabel("create menu item");
		}
		
		private void setLabelProperty() {
			IPropertySource properties = Utilities.getPropertySource(object);
			properties.setPropertyValue(IMenuEditor.LABEL_PROPERTY_ID, labelString);
		}
		
		private void setNameProperty(EObject obj, String name) {
			IPropertySource properties = Utilities.getPropertySource(obj);
			properties.setPropertyValue(INode.NAME_PROPERTY, name);
		}
		
		private EObject getMenuPane() {
			return dataModel.findByNameProperty(paneName);
		}
		
		private void setNamesAndLabel() {
			if (paneWasTemporary) {
				EObject menuPane = getMenuPane();
				if (MenuEditManager.hasTemporaryName(menuPane)) {
					paneName = MenuEditManager.getNewName(dataModel, menuPane, null);
					setNameProperty(menuPane, paneName);
				}
			}
			setNameProperty(object, newName);
			setLabelProperty();
		}
		
		@Override
		public void execute() {
			setNamesAndLabel();
			menuEditManager.selectNextItem();
		}

		@Override
		public void undo() {
			if (paneWasTemporary) {
				menuEditManager.removeChild(getMenuPane());
			}
			else {
				object = dataModel.findByNameProperty(newName);
				if (object != null)
					menuEditManager.removeChild(object);
			}
			menuEditManager.layoutMenubar();
			menuEditManager.selectNextItem();
			object = null;
		}

		@Override
		public void redo() {
			IComponentInstance paneInstance = null;
			EObject menuPane = getMenuPane();
			if (menuPane == null) {
				EObject grandParent = dataModel.findByNameProperty(grandParentName);
				Check.checkState(grandParent != null);
				IComponentInstance grandParentInstance = Utilities.getComponentInstance(grandParent);
				if (MenuEditManager.hasNoChildren(grandParentInstance))
					menuPane = menuEditManager.addChildAtEnd(grandParentInstance, IMenuEditor.MENUPANE_ID);
				else {
					paneInstance = MenuEditManager.getFirstChildInstance(grandParentInstance);
					menuPane = paneInstance.getEObject();
				}
				if (paneInstance == null)
					paneInstance = Utilities.getComponentInstance(menuPane);
				paneName = paneInstance.getName();
			}
			if (paneInstance == null)
				paneInstance = Utilities.getComponentInstance(menuPane);
			object = menuEditManager.addChildAtIndex(paneInstance, IMenuEditor.MENUITEM_ID, index);
			setNamesAndLabel();
			menuEditManager.layoutMenubar();
			menuEditManager.selectNextItem();
		}
	}
	
	private EObject eObject;
	private IComponentInstance componentInstance;
	private ILayoutObject layoutObject;
	private IPropertySource propertySource;
	private static final char ELLIPSIS = '\u2026';
	private IDesignerEditor editor;
	private MenuEditManager menuEditManager;

	public MenuItemEditor(EObject instance) {
		eObject = instance;
		this.componentInstance = Utilities.getComponentInstance(getEObject());
		this.layoutObject = Utilities.getLayoutObject(getEObject());
		this.propertySource = Utilities.getPropertySource(getEObject());
		Check.checkArg(componentInstance);
		Check.checkArg(propertySource);
		menuEditManager = getMenuEditManager();
	}
	
	private String getDisplayText(GC gc, ILookAndFeel laf) {
		IPropertySource properties = Utilities.getPropertySource(getEObject());
		String text = (String) properties.getPropertyValue(LABEL_PROPERTY_ID);
		if (isTemporaryObject()) {
			text = Messages.getString("MenuItemEditor.TypeHere"); //$NON-NLS-1$
		}
		
		// use ellipsis if text is too long
		StringBuffer buffer = new StringBuffer(text);
		Point extent = gc.stringExtent(buffer.toString());
		Rectangle bounds = getVisualBounds(null, laf);
		while (extent.x > bounds.width) {
			buffer.setLength(buffer.length() - 2);
			buffer.append(ELLIPSIS);
			extent = gc.stringExtent(buffer.toString());
			bounds = getVisualBounds(null, laf);
		}
		
		return buffer.toString();
	}
	
	private IFont getMenuFont(ILookAndFeel laf) {
		String menuFont = isSubMenu() ? "menuitem.submenu.font" : "menuitem.font"; //$NON-NLS-1$ //$NON-NLS-2$
		return laf.getFont(menuFont);
	}
	
	private Point getTextLocation(ILookAndFeel laf) {
		return laf.getDimension("note.inset"); //$NON-NLS-1$
	}
	
	public void draw(GC gc, ILookAndFeel laf) {
		if (isSubMenu() && (editor == null))
			return;
		
		Check.checkState(layoutObject != null);
		IFont font = getMenuFont(laf);
		gc.setFont(font);

		Rectangle bounds = layoutObject.getBounds();
		EObject[] children = componentInstance.getChildren();
		if ((children != null) && (children.length > 0)) {
			// draw an arrow
			gc.setForeground(Colors.getColor(0, 0, 0));
			int arrowTop = (bounds.height - 7) / 2;
			int arrowBottom = arrowTop + 7;
			for (int i = 0; i < 4; i++)
				gc.drawLine(bounds.width - 9 + i, arrowTop + i, bounds.width - 9 + i, arrowBottom - i);
		}
		
		if (isTemporaryObject()) {
			gc.setForeground(Colors.getColor(128, 128, 128));
		}
		else {
			gc.setForeground(laf.getColor("listitem.text")); //$NON-NLS-1$
		}
		gc.setBackground(laf.getColor("EEikColorMenuPaneBackground")); //$NON-NLS-1$
        
		Point start = getTextLocation(laf);
		gc.drawFormattedString(getDisplayText(gc, laf), 
                new Rectangle(start.x, start.y, bounds.width, bounds.height), 
                IFontConstants.DRAW_TRANSPARENT);
	}
	
	public Point getPreferredSize(int wHint, int hHint, ILookAndFeel laf) {
		IFont font = getMenuFont(laf);
        int height = font != null ? font.getHeight() : 12;
        
        Point screenSize = laf.getDimension(Series60LAF.SCREEN_SIZE);
        int padding = laf.getInteger("note.padding", 4) * 2 + height;
        int minDimension = Math.min(screenSize.x, screenSize.y);
        int width = isSubMenu() ? (minDimension * 4) / 7 : minDimension - padding;
        return new Point(width, height + height/2);
	}
	
	private EObject getSubPane() {
		EObject[] children = componentInstance.getChildren();
		if ((children != null) && (children.length >= 1))
			return children[0];
		
		return null;
	}

	public void layout(ILookAndFeel laf) {
		EObject subPane = getSubPane();
		if (subPane != null) {
			Check.checkState(layoutObject != null);
			Rectangle bounds = layoutObject.getBounds();
			ILayoutObject childLayoutObject = Utilities.getLayoutObject(subPane);
			if (editor != null) {
				IVisualAppearance childVisual = Utilities.getVisualAppearance(subPane);
				Point childSize = childVisual.getPreferredSize(-1, -1, laf);
				childLayoutObject.setBounds(new Rectangle(bounds.width, 0, childSize.x, childSize.y));
			}
			else {
				childLayoutObject.setBounds(new Rectangle(bounds.width, 0, 0, 0));
			}
		}
		Utilities.getLayoutObject(eObject).forceRedraw();
	}
	
	public boolean canContainComponent(IComponent component, StatusHolder statusHolder) {
		if (MenuEditManager.isTemporaryObject(getEObject())) {
			Utilities.setStatus(statusHolder, Messages.getString("MenuItemEditor.TempItemContainError"), null); //$NON-NLS-1$
			return false;
		}

		if (getSubPane() != null && !MenuEditManager.isTemporaryObject(getSubPane())) {
			Utilities.setStatus(statusHolder, Messages.getString("MenuItemEditor.ContainErrorSingle"), null); //$NON-NLS-1$
			return false;
		}
		
		boolean isMenuPane = ModelUtils.isOfType(component, MENUPANE_ID);
		if (!isMenuPane) {
			Utilities.setStatus(statusHolder, Messages.getString("MenuItemEditor.ContainError"), null); //$NON-NLS-1$
			return false;
		}
		else if (isSubMenu()) {
			Utilities.setStatus(statusHolder, Messages.getString("MenuItemEditor.ContainError2Levels"), null); //$NON-NLS-1$
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

	public String[] getPropertyPaths() {
		return new String[] { LABEL_PROPERTY_ID };
	}

	public Rectangle getVisualBounds(String propertyPath, ILookAndFeel laf) {
		Point location = getTextLocation(laf);
		Rectangle bounds = layoutObject.getBounds();
		return new Rectangle(location.x, location.y, bounds.width, bounds.height);
	}

	public IFont getLabelFont(String propertyPath, ILookAndFeel laf) {
		return getMenuFont(laf);
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
		EObject pane = Utilities.getComponentInstance(getEObject()).getParent();
		if (pane == null)
			return false;
		EObject container = Utilities.getComponentInstance(pane).getParent();
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

	public Command getExtendedCommand(String propertyName, Object newValue, Command command) {
		EObject object = getEObject();
		if (propertyName.equals(IMenuEditor.LABEL_PROPERTY_ID) && (newValue != null)) {
			if ((MenuEditManager.getMenuEditor(object) != null) && MenuEditManager.hasTemporaryName(object)) {
				return new MakeNonTemporaryCommand(object, newValue.toString());
			}
		}
		return command;
	}
}
