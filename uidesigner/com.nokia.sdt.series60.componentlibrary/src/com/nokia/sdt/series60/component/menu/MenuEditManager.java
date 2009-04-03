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

import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.TextUtils;
import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.symbian.displaymodel.Utilities;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.*;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.datamodel.util.NamePropertySupport;
import com.nokia.sdt.editor.*;
import com.nokia.sdt.editor.IDesignerEditor.TransientListener;
import com.nokia.sdt.editor.IDesignerDataModelEditor.SaveListener;
import com.nokia.sdt.emf.dm.INode;
import com.nokia.sdt.uidesigner.ui.utils.EditorUtils;
import com.nokia.sdt.utils.*;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.views.properties.IPropertySource;

import java.util.Collections;
import java.util.List;

public class MenuEditManager {

	private IDesignerEditor designerEditor;
	private IDesignerDataModelEditor designerDataModelEditor;
	private IComponentInstance menuBarInstance;
	private IFigure transientLayer;
	private TransientListener transientListener;
	private IComponentInstance selectedInstance;
	private KeyListener keyListener;
	private SaveListener saveListener;
	private IComponentInstanceChildListener menuBarListener;
	private IComponentInstance menuBarParent;
	
	private static int nextTempNameSuffix = 0;
	private final static String NAME_PREFIX = "~temp"; //$NON-NLS-1$
	private final static String ITEM_SUFFIX = "MenuItem"; //$NON-NLS-1$

	public MenuEditManager(MenuBarEditor menuBarEditor) {
		this.menuBarInstance = Utilities.getComponentInstance(menuBarEditor.getEObject());
		this.menuBarParent = getMenuBarParentInstance();
		if ((menuBarListener == null) && (menuBarParent != null)) {
			menuBarParent.addChildListener(menuBarListener = new IComponentInstanceChildListener() {
				public void childAdded(EObject parent, EObject child) {}
				public void childRemoved(EObject parent, EObject child) {
					if ((menuBarParent != null) && (child.equals(menuBarInstance.getEObject()))) {
						menuBarInstance = null;
						selectObject(menuBarParent.getEObject());
						if (menuBarListener != null) {
							menuBarParent.removeChildListener(menuBarListener);
							menuBarListener = null;
						}
					}
				}
				public void childrenReordered(EObject parent) {}
			});
		}
	}

	protected void setMenuBarInstance(IComponentEditor menuBarEditor) {
		menuBarInstance = Utilities.getComponentInstance(menuBarEditor.getEObject());
	}

	protected void setSelectedEditor(IComponentEditor selectedEditor, IDesignerEditor editor) {
		if (!editor.isTransientMode())
			return;
		this.designerEditor = editor;
		this.designerDataModelEditor = (IDesignerDataModelEditor) editor.getAdapter(IDesignerDataModelEditor.class);
		if (transientListener == null) {
			designerEditor.addTransientListener(transientListener = new TransientListener() {
				public void enteredTransientMode() {}
				public void prepareToExitTransientMode() {}
				public void exitingTransientMode() {
					designerEditor.removeTransientListener(transientListener);
					transientListener = null;
					// this could occur due to undoing menubar creation, 
					// so the menubar may not be in the model - check before pruning
					if ((menuBarInstance != null) && 
							ModelUtils.getModel(menuBarInstance.getEObject()) != null)
						pruneAllTempItems();
					if (keyListener != null) {
						designerEditor.getKeyEventProvider().removeKeyListener(keyListener);
						keyListener = null;
					}
					if (saveListener != null) {
						designerDataModelEditor.removeSaveListener(saveListener);
						saveListener = null;
					}
					selectedInstance = null;
				}
			});
		}
		if (keyListener == null) {
			designerEditor.getKeyEventProvider().addKeyListener(keyListener = new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					handleKeyPressed(e);
				}
			});
		}
		if (saveListener == null) {
			designerDataModelEditor.addSaveListener(saveListener = new SaveListener() {
				public boolean queryAboutToSave(IDesignerDataModelEditor editor) {
					return true; // no veto
				}
				public void preSaveNotify(IDesignerDataModelEditor editor, IProgressMonitor monitor) {
					selectedInstance = getNearestNonTemporaryInstance(selectedInstance.getEObject());
					pruneAllTempItems();
				}
				public void postSaveNotify(IDesignerDataModelEditor editor, IProgressMonitor monitor) {
					if (selectedInstance != null) {
						addTempItems(selectedInstance.getEObject());
						selectObject(selectedInstance.getEObject());
					}
				}
			});
		}
		IComponentInstance previousSelection = null;
		if (selectedInstance != null) {
			if (selectedInstance.getDesignerDataModel() != null) // part of data model
				previousSelection = selectedInstance;
		}
		else {
			// if this is a transition to edit model (selected instance is null),
			// and the user selected the menubar, 
			// we want to direct the user to the temporary item
			// where he obviously wants to start typing
			if (isMenuBar(Utilities.getComponentInstance(selectedEditor.getEObject()))) {
				// We're in the midst of selection synchronizing, so it's best to let that finish
				// before changing the selection to the top level item
				Display.getCurrent().asyncExec(new Runnable() {
					public void run() {
						selectTopLevelTemporaryItem();
					}
				});
			}
		}
		selectedInstance = Utilities.getComponentInstance(selectedEditor.getEObject());
		pruneNonTerminatingTempItems();
		// it's important that we don't prune the item that may be holding up the current cascading menu
		if ((previousSelection != null) && isMenuPane(previousSelection) || 
				(isPrimaryMenuItem(previousSelection) &&
				!EditorUtils.isSameObjectOrChild(previousSelection.getEObject(), selectedEditor.getEObject())))
			pruneTempItems(previousSelection, true);
		if (!editor.isTransientMode()) // pruning can cause switch to layout mode by changing the selection 
			return;
		addTempItems(selectedEditor.getEObject());
		setNewEditors(selectedEditor);
		doLayout(menuBarInstance.getEObject());
		transientLayer = designerEditor.getTransientLayerRootFigure().getParent();
		transientLayer.repaint();
	}
	
	private void pruneNonTerminatingTempItems() {
		// Through drag and drop, items may be added other than at the end.
		// A temp item may be inserted in a non-terminating postion.
		pruneTempItems(menuBarInstance, false);
	}

	protected void pruneAllTempItems() {
		if (menuBarInstance == null)
			return;
		designerEditor.getSelectionManager().setSync(false);
		pruneTempItems(menuBarInstance, true);
		designerEditor.getSelectionManager().setSync(true);
	}

	protected void pruneTempItems(IComponentInstance root, boolean all) {
		if (root == null)
			return;
		EObject[] children = root.getChildren();
		if (children != null) {
			for (int i = (children.length - 1); i >= 0; i--) {
				EObject child = children[i];
				IComponentInstance childInstance = Utilities.getComponentInstance(child);
				pruneTempItems(childInstance, all);
				if (isTemporaryObject(child) && (all || !child.equals(children[children.length - 1])))
					removeChild(child);
				else if (isMenuPane(childInstance)) {
					if (isTemporaryMenuPaneWithNonTemporaryChildren(child))
						assignNewName(designerEditor.getDataModel(), child);
				}
			}
		}
	}

	protected void unsetAllEditors() {
		if (menuBarInstance == null || menuBarInstance.getRootContainer() == null)
			return;
		unsetEditors(menuBarInstance);
		doLayout(menuBarInstance.getEObject());
		if (transientLayer != null)
			transientLayer.repaint();
	}
	
	private IComponentInstance getNearestNonTemporaryInstance(EObject object) {
		// go up the parent tree looking for an object that is not temporary
		// or not the menu pane holding only temporary items
		IComponentInstance instance = Utilities.getComponentInstance(object);
		while (isTemporaryObject(object) || hasOnlyTemporaryChildren(object)) {
			if (isMenuBar(instance))
				return instance;
			object = instance.getParent();
			instance = Utilities.getComponentInstance(object);
		}
		
		return instance;
	}
	
	private boolean hasOnlyTemporaryChildren(EObject object) {
		IComponentInstance instance = Utilities.getComponentInstance(object);
		if (!hasNoChildren(instance)) {
			EObject[] children = instance.getChildren();
			for (int i = 0; i < children.length; i++) {
				if (!isTemporaryObject(children[i]))
					return false;
			}
		}
		return true;
	}

	private void addTempItems(EObject object) {
		IComponentInstance instance = Utilities.getComponentInstance(object);
		if (isMenuBar(instance))
			addTempItemsToMenuBarOrTopLevelItem(instance);
		else if (isMenuPane(instance))
			addTempItemsToMenuPane(instance);
		else if (isMenuItem(instance))
			addEmptyItemsToMenuItem(instance);
	}

	private void addTempItemsToMenuBarOrTopLevelItem(IComponentInstance instance) {
		Check.checkState(ModelUtils.isInstanceOf(instance.getEObject(), IMenuEditor.MENUBAR_ID) ||
				ModelUtils.isInstanceOf(instance.getEObject(), IMenuEditor.MENUITEMBASE_ID));
		if (hasNoChildren(instance)) {
			addTemporaryItem(Utilities.getComponentInstance(addTemporaryPane(instance)));
		}
		else {
			addTempItemsToMenuPane(getFirstChildInstance(instance));
		}
	}
	
	private void addTempItemsToMenuPane(IComponentInstance instance) {
		if (instance == null)
			return;
		Check.checkState(ModelUtils.isInstanceOf(instance.getEObject(), IMenuEditor.MENUPANE_ID));
		if (hasNoChildren(instance) || !isLastMenuItemTemporary(instance)) {
			addTemporaryItem(instance);
		}
		if (isSubMenuPane(instance)) {
			addTempItemsToMenuBarOrTopLevelItem(menuBarInstance);
		}
	}

	private void addEmptyItemsToMenuItem(IComponentInstance instance) {
		Check.checkState(ModelUtils.isInstanceOf(instance.getEObject(), IMenuEditor.MENUITEMBASE_ID));
		if (!isTemporaryObject(instance.getEObject())) {
			if (isPrimaryMenuItem(instance)) {
				if (hasNoChildren(instance))
					addTemporaryItem(Utilities.getComponentInstance(addTemporaryPane(instance)));
				else
					addTempItemsToMenuPane(getFirstChildInstance(instance));
			}
			else {
				addTempItemsToMenuPane(Utilities.getComponentInstance(instance.getParent()));
			}
		}
		addTempItemsToMenuBarOrTopLevelItem(menuBarInstance);
	}
	
	protected void removeChild(EObject child) {
		List toRemove = Collections.singletonList(child);
		Command command = 
			designerEditor.getDataModel().createRemoveComponentInstancesCommand(toRemove);
		if (command.canExecute())
			command.execute();
	}
	
	protected EObject addChildAtIndex(IComponentInstance parent, String childId, int index) {
		IComponent component = 
			designerEditor.getDataModel().getComponentSet().lookupComponent(childId);

		// verify parent is editable
		IQueryContainment queryContainment = (IQueryContainment) EcoreUtil.getRegisteredAdapter(
				parent.getEObject(), IQueryContainment.class);
		StatusHolder statusHolder = new StatusHolder();
		if (queryContainment == null || !queryContainment.canContainComponent(component, statusHolder))
			return null;

		EObject newChild = designerEditor.getDataModel().createNewComponentInstance(component);
		Command command = designerEditor.getDataModel().createAddNewComponentInstanceCommand(
											parent.getEObject(), newChild, index);
		if (command.canExecute()) {
			command.execute();
			assignTemporaryName(newChild);
			return newChild;
		}
		
		return null;
	}
	
	protected EObject addChildAtEnd(IComponentInstance parent, String childId) {
		return addChildAtIndex(parent, childId, IDesignerDataModel.AT_END);
	}

	protected static void assignTemporaryName(EObject object) {
		IPropertySource properties = Utilities.getPropertySource(object);
		properties.setPropertyValue(INode.NAME_PROPERTY, NAME_PREFIX + nextTempNameSuffix++);
	}
	
	/**
	 * Assign a name to a menu item based on its label property.
	 * @param model
	 * @param object
	 */
	public static void assignNewName(IDesignerDataModel model, EObject object) {
		IPropertySource properties = Utilities.getPropertySource(object);
		properties.setPropertyValue(INode.NAME_PROPERTY, getNewName(model, object));
	}
	
	/**
	 * Get a nwe name for a menu item based on the label string.
	 * @param model
	 * @param object
	 * @param label
	 */
	protected static String getNewName(IDesignerDataModel model, EObject object, String label) {
		IComponentInstance instance = Utilities.getComponentInstance(object);
		String newName = null;
		if (label != null) {
			String identifier = TextUtils.legalizeIdentifier(label);
			identifier += ITEM_SUFFIX;
			identifier = TextUtils.inverseTitleCase(identifier);
			newName = NamePropertySupport.generateNameForModel(model, 
					instance.getComponent(), identifier, false);
			newName = TextUtils.inverseTitleCase(newName);
		}
		else {
			newName = NamePropertySupport.generateDefaultName(model, 
					instance.getComponent());
		}

		return newName;
	}
	
	/**
	 * Get a nwe name for a menu item based on its label property.
	 * @param model
	 * @param object
	 */
	protected static String getNewName(IDesignerDataModel model, EObject object) {
		IPropertySource properties = Utilities.getPropertySource(object);
		String label = (String) properties.getPropertyValue(IMenuEditor.LABEL_PROPERTY_ID);
		
		return getNewName(model, object, label);
	}

	protected static boolean hasTemporaryName(EObject object) {
		IComponentInstance instance = Utilities.getComponentInstance(object);
		String name = instance.getName();
		if (name.length() <= NAME_PREFIX.length())
			return false;
		String suffix = name.substring(NAME_PREFIX.length());
		int suffixValue = -1;
		try {
			suffixValue = Integer.parseInt(suffix);
		}
		catch (NumberFormatException e) {
		}
		return name.startsWith(NAME_PREFIX) && (suffixValue < nextTempNameSuffix);
	}

	protected static boolean isTemporaryObject(EObject object) {
		return getMenuEditor(object).isTemporaryObject();
	}
	
	protected static boolean isTemporaryMenuPaneWithNonTemporaryChildren(EObject object) {
		IComponentInstance instance = Utilities.getComponentInstance(object);
		if (isMenuPane(instance) && isTemporaryObject(object)) {
			EObject[] children = instance.getChildren();
			if (children != null) {
				for (int i = 0; i < children.length; i++) {
					EObject child = children[i];
					if (!isTemporaryObject(child))
						return true;
				}
			}
		}
		return false;
	}
	
	protected static boolean isLastMenuItemTemporary(IComponentInstance parentInstance) {
		EObject[] children = parentInstance.getChildren();
		if (children == null)
			return false;
		
		return isTemporaryObject(children[children.length - 1]);
	}
	
	protected static boolean isOnlyMenuItemTemporary(IComponentInstance parentInstance) {
		EObject[] children = parentInstance.getChildren();
		if (children == null)
			return false;
		
		return (children.length == 1) && isTemporaryObject(children[0]);
	}

	protected static boolean hasNoChildren(IComponentInstance instance) {
		EObject[] children = instance.getChildren();
		return (children == null) || (children.length == 0);
	}

	private EObject addTemporaryPane(IComponentInstance parent) {
		return addChildAtEnd(parent, IMenuEditor.MENUPANE_ID);
	}

	private EObject addTemporaryItem(IComponentInstance parent) {
		return addChildAtEnd(parent, IMenuEditor.MENUITEM_ID);
	}

	protected static IComponentInstance getFirstChildInstance(IComponentInstance instance) {
		EObject[] children = instance.getChildren();
		if (children == null)
			return null;

		return Utilities.getComponentInstance(children[0]);
	}

	private void unsetEditors(IComponentInstance root) {
		setEditor(root.getEObject(), null);
		EObject[] children = root.getChildren();
		if (children == null)
			return;
		
		for (int i = 0; i < children.length; i++) {
			IComponentInstance childInstance = Utilities.getComponentInstance(children[i]);
			unsetEditors(childInstance);
		}
		
	}
	
	private void setEditor(EObject object, IDesignerEditor editor) {
		getMenuEditor(object).setEditor(editor);
	}
	
	protected static boolean isMenuBar(IComponentInstance componentInstance) {
		if (componentInstance == null)
			return false;
		return componentInstance.getComponentId().equals(IMenuEditor.MENUBAR_ID);
	}
	
	protected static boolean isMenuPane(IComponentInstance componentInstance) {
		if (componentInstance == null)
			return false;
		return componentInstance.getComponentId().equals(IMenuEditor.MENUPANE_ID);
	}
	
	protected static boolean isMenuItem(IComponentInstance componentInstance) {
		if (componentInstance == null)
			return false;
		return ModelUtils.isInstanceOf(componentInstance.getEObject(), IMenuEditor.MENUITEMBASE_ID);
	}
	
	protected static boolean isPrimaryMenuItem(IComponentInstance componentInstance) {
		return isMenuItem(componentInstance) &&
			!getMenuEditor(componentInstance.getEObject()).isSubMenu();
	}

	protected static boolean isSubMenuItem(IComponentInstance componentInstance) {
		return isMenuItem(componentInstance) &&
			getMenuEditor(componentInstance.getEObject()).isSubMenu();
	}

	protected static boolean isPrimaryMenuPane(IComponentInstance componentInstance) {
		return isMenuPane(componentInstance) &&
			!getMenuEditor(componentInstance.getEObject()).isSubMenu();
	}
	
	protected static boolean isSubMenuPane(IComponentInstance componentInstance) {
		return isMenuPane(componentInstance) &&
			getMenuEditor(componentInstance.getEObject()).isSubMenu();
	}
	
	private void setNewEditors(IComponentEditor selectedEditor) {
		setEditor(selectedEditor.getEObject(), designerEditor);
		IComponentInstance selectedInstance = Utilities.getComponentInstance(selectedEditor.getEObject());
		if (getMenuEditor(selectedEditor.getEObject()).isSubMenu()) {
			EObject parent = selectedInstance.getParent();
			setEditor(parent, designerEditor);
			if (getMenuEditor(parent).isSubMenu()) {
				EObject grandParent = Utilities.getComponentInstance(parent).getParent();
				setEditor(grandParent, designerEditor);
				setChildren(Utilities.getComponentInstance(parent)); // set the peers
			}
		}
		if (!isMenuBar(selectedInstance) && !isPrimaryMenuPane(selectedInstance))
			setChildren(selectedInstance);
	}
	
	private static void doLayout(EObject layoutRoot) {
		// layout the children first, because parents may be dependent on child sizes
		EObject[] children = Utilities.getComponentInstance(layoutRoot).getChildren();
		if (children != null) {
			for (int i = 0; i < children.length; i++) {
				doLayout(children[i]);
			}
		}
		Utilities.getLayoutContainer(layoutRoot).layoutChildren();
	}

	private void setChildren(IComponentInstance parent) {
		EObject[] children = parent.getChildren();
		if (children == null)
			return;
		
		for (int i = 0; i < children.length; i++) {
			setEditor(children[i], designerEditor);
			setChildren(Utilities.getComponentInstance(children[i]));
		}
	}
	
	protected static IMenuEditor getMenuEditor(EObject object) {
		IComponentEditor componentEditor = Utilities.getComponentEditor(object);
		if (componentEditor == null)
			return null;
		return (IMenuEditor) componentEditor.getAdapter(IMenuEditor.class);
	}
	
	private IComponentInstance getMenuBarParentInstance() {
		EObject menuBarParent = menuBarInstance.getParent();
		if (menuBarParent != null)
			return Utilities.getComponentInstance(menuBarParent);
		
		return null;
	}
	
	private void selectObject(EObject object) {
		GraphicalViewer viewer = designerEditor.getUpperGraphicalViewer();
		EditPart part = (EditPart) viewer.getEditPartRegistry().get(object);
        if (part != null) {
            viewer.setSelection(new StructuredSelection(part));
            viewer.getControl().setFocus();
        }
	}
	
	protected void selectTopLevelTemporaryItem() {
		EObject[] children = menuBarInstance.getChildren();
		if (children == null)
			return;
		IComponentInstance pane = getFirstChildInstance(menuBarInstance);
		children = pane.getChildren();
		if (children == null)
			return;
		for (int i = children.length - 1; i >= 0; i--) {
			if (isTemporaryObject(children[i])) {
				selectObject(children[i]);
				break;
			}
		}
	}

	protected void selectNextItem() {
		if (!isMenuItem(selectedInstance))
			return;
		
		EObject parent = selectedInstance.getParent();
		IComponentInstance parentInstance = Utilities.getComponentInstance(parent);
		selectObject(getNextItem(parentInstance));
	}
	
	protected static int getChildIndex(EObject[] siblings, EObject child) {
		if (siblings == null)
			return -1;
		
		for (int i = 0; i < siblings.length; i++) {
			if (siblings[i].equals(child))
				return i;
		}
		
		return siblings.length;
	}

	private EObject getNextItem(IComponentInstance parentInstance) {
		EObject[] children = parentInstance.getChildren();
		if (children == null)
			return null;
		
		int curIndex = getChildIndex(children, selectedInstance.getEObject());
		if ((curIndex + 1) >= children.length )
			return addTemporaryItem(parentInstance);

		return children[curIndex + 1];
	}
	
	private void selectFirstChildOfChild(IComponentInstance instance) {
		EObject[] children = instance.getChildren();
		if (children == null)
			return;
		
		EObject child = children[0];
		selectFirstChild(Utilities.getComponentInstance(child));
	}

	private void selectFirstChild(IComponentInstance instance) {
		EObject[] children = instance.getChildren();
		if (children != null);
			selectObject(children[0]);
	}
	
	private void selectLastChild(IComponentInstance instance) {
		EObject[] children = instance.getChildren();
		if (children != null)
			selectObject(children[children.length - 1]);
	}
	
	private void selectParent(IComponentInstance instance) {
		selectObject(instance.getParent());
	}
	
	private void selectParentOfParent(IComponentInstance instance) {
		EObject parent = instance.getParent();
		IComponentInstance parentInstance = Utilities.getComponentInstance(parent);
		selectParent(parentInstance);
	}
	
	protected void selectNextSibling(IComponentInstance instance) {
		EObject parent = instance.getParent();
		IComponentInstance parentInstance = Utilities.getComponentInstance(parent);
		EObject[] children = parentInstance.getChildren();
		if (children == null)
			return;
		
		for (int i = 0; i < children.length; i++) {
			EObject child = children[i];
			if (child.equals(instance.getEObject())) {
				if ((i + 1) < children.length)
					selectObject(children[i + 1]);
				break;
			}
		}
	}
	
	private void selectPreviousSibling(IComponentInstance instance) {
		EObject parent = instance.getParent();
		IComponentInstance parentInstance = Utilities.getComponentInstance(parent);
		EObject[] children = parentInstance.getChildren();
		if (children == null)
			return;
		
		for (int i = children.length - 1; i >= 0; i--) {
			EObject child = children[i];
			if (child.equals(instance.getEObject())) {
				if ((i - 1) >= 0)
					selectObject(children[i - 1]);
				break;
			}
		}
	}
	
	protected void selectRight() {
		if (isPrimaryMenuItem(selectedInstance) && !isTemporaryObject(selectedInstance.getEObject())) {
			selectFirstChildOfChild(selectedInstance);
		}
	}
	
	protected void selectLeft() {
		if (isSubMenuItem(selectedInstance)) {
			selectParentOfParent(selectedInstance);
		}
		else if (isSubMenuPane(selectedInstance)) {
			selectParent(selectedInstance);
		}
	}
	
	private void selectedDown() {
		if (isMenuBar(selectedInstance)) {
			selectFirstChildOfChild(selectedInstance);
		}
		else if (isMenuPane(selectedInstance)) {
			selectFirstChild(selectedInstance);
		}
		else if (isMenuItem(selectedInstance)) {
			selectNextSibling(selectedInstance);
		}
	}
	
	private void selectUp() {
		if (isMenuItem(selectedInstance)) {
			selectPreviousSibling(selectedInstance);
		}
	}

	private void selectTop() {
		if (isMenuItem(selectedInstance)) {
			EObject parent = selectedInstance.getParent();
			IComponentInstance parentInstance = Utilities.getComponentInstance(parent);
			selectFirstChild(parentInstance);
		}
	}

	private void selectBottom() {
		if (isMenuItem(selectedInstance)) {
			EObject parent = selectedInstance.getParent();
			IComponentInstance parentInstance = Utilities.getComponentInstance(parent);
			selectLastChild(parentInstance);
		}
	}
	
	protected void handleKeyPressed(KeyEvent e) {
		e.doit = false;
		if (e.keyCode == SWT.ARROW_RIGHT) {
			selectRight();
		}
		else if (e.keyCode == SWT.ARROW_LEFT) {
			selectLeft();
		}
		else if (e.keyCode == SWT.ARROW_DOWN) {
			selectedDown();
		}
		else if (e.keyCode == SWT.ARROW_UP) {
			selectUp();
		}
		else if (e.keyCode == SWT.HOME) {
			selectTop();
		}
		else if (e.keyCode == SWT.END) {
			selectBottom();
		}
		else {
			e.doit = true;
		}
	}

	protected void layoutMenubar() {
		if (menuBarInstance != null) {
			doLayout(menuBarInstance.getEObject());
		}
	}
}
