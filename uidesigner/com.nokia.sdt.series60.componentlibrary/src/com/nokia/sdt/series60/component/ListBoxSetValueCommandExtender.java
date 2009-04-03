/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.series60.component;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.component.adapter.IAttributes;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.ISetValueCommandExtender;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.series60.component.menu.IMenuEditor;
import com.nokia.sdt.series60.component.menu.MenuEditManager;
import com.nokia.sdt.symbian.S60ComponentAttributes;
import com.nokia.sdt.symbian.dm.SymbianModelUtils;
import com.nokia.sdt.symbian.images.*;
import com.nokia.sdt.uidesigner.ui.command.DataModelCommandWrapper;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.ui.views.properties.IPropertySource;
import org.osgi.framework.Version;

import java.util.Collections;

/**
 * When the list box type is changed to Markable list, 
 * add the appropriate system menu pane.
 * 
 */
public class ListBoxSetValueCommandExtender implements ISetValueCommandExtender {
	public static final String MARKABLE_LIST_MENUPANE_ID = "com.nokia.sdt.series60.MarkableListSystemMenuPane"; //$NON-NLS-1$
	public static final String TYPE_PROPERTY_NAME = "type"; //$NON-NLS-1$
	public static final String MARKABLE_LIST_TYPE_ENUM = "EAknListBoxMarkableList"; //$NON-NLS-1$

	public static final Object LIST_MARK_ICON_PROPERTY_ID = "markIcon"; //$NON-NLS-1$
	
	private EObject instance;
	private IComponent menuItemComponent;
	private IComponent menuPaneComponent;
	private IDesignerDataModel dataModel;

	public ListBoxSetValueCommandExtender(EObject componentInstance) {
		this.instance = componentInstance;
		IComponentInstance ci = ModelUtils.getComponentInstance(instance);
		dataModel = ci.getDesignerDataModel();
		IComponentSet componentSet = dataModel.getComponentSet();
		menuItemComponent = componentSet.lookupComponent(IMenuEditor.MENUITEM_ID);
		Check.checkContract(menuItemComponent != null);
		menuPaneComponent = componentSet.lookupComponent(IMenuEditor.MENUPANE_ID);
		Check.checkContract(menuPaneComponent != null);
		
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.uidesigner.derived.ISetValueCommandExtender#getExtendedCommand(java.lang.String, com.nokia.sdt.uidesigner.ui.command.SetValueCommand)
	 */
	public Command getExtendedCommand(String propertyName, Object newValue, Command command) {
		Command combined = command;
		if (propertyName.equals(TYPE_PROPERTY_NAME)) {
			if (MARKABLE_LIST_TYPE_ENUM.equals(newValue)) {
				
				// find the menu to add the command to
				EObject menuPane = findOrCreateTargetMenuPane(instance);
				if (menuPane != null) {
					// see if the menu item already exists
					EObject menuItem = findMenuItemWithMenuPane(menuPane, MARKABLE_LIST_MENUPANE_ID);
					if (menuItem == null) {
						String menuItemText = getDefaultMenuItemText();
						
						org.eclipse.emf.common.command.Command emfCommand 
							= new AddMenuItemWithMenuPaneCommand( 
								menuPane,
								menuItemText,
								MARKABLE_LIST_MENUPANE_ID);
						DataModelCommandWrapper addMenuCommand = new DataModelCommandWrapper();
						addMenuCommand.setDataModelCommand(emfCommand);
						
						combined = combined.chain(addMenuCommand);
					}
				}
				
				// no longer needed; we substitute the system default if empty
				/*
				// provide a mark icon 
				org.eclipse.emf.common.command.Command emfCommand = new SetMarkIconCommand(instance);
				DataModelCommandWrapper setMarkIconCommand = new DataModelCommandWrapper();
				setMarkIconCommand.setDataModelCommand(emfCommand);
				
				combined = combined.chain(setMarkIconCommand);
				*/
			}
			
		}
		return combined;
	}
	
	/**
	 * Get the name of the menu item invoking the standard menu,
	 * using the baseline SDK.
	 * @return
	 */
	private String getDefaultMenuItemText() {
		IDesignerDataModel dataModel = ModelUtils.getComponentInstance(instance).getDesignerDataModel();
		Version componentVersions = SymbianModelUtils.getComponentVersions(dataModel);
		String menuItemText = Messages.getString("ListBoxSetValueCommandExtenderFactory.MarkUnmarkMenuLabel"); //$NON-NLS-1$
		if (componentVersions != null 
				&& ((componentVersions.getMajor() == 2 && componentVersions.getMinor() >= 8)
				|| (componentVersions.getMajor() >= 3)))
			menuItemText = Messages.getString("ListBoxSetValueCommandExtenderFactory.EditListMenuLabel"); //$NON-NLS-1$
		return menuItemText;
	}

	/**
	 * Find the menu pane to which to add items.  Usually,
	 * this is the Options menu in the Avkon view.  We look for
	 * any parent which has an options menu attribute then
	 * find what options menu it refers to.
	 * @param instance
	 * @return
	 */
	private EObject findOrCreateTargetMenuPane(EObject instance) {
		while (instance != null) {
			IComponentInstance componentInstance = ModelUtils.getComponentInstance(instance);
			if (componentInstance == null)
				break;
			IComponent component = componentInstance.getComponent();
			if (component != null) {
				IAttributes attributes = (IAttributes) component.getAdapter(IAttributes.class);
				if (attributes != null) {
					String optionsMenuName = attributes.getAttribute(S60ComponentAttributes.OPTIONS_MENU_PROPERTY_NAME);
					if (optionsMenuName != null) {
						IPropertySource ps = ModelUtils.getPropertySource(instance);
						String menuInstance = ps.getPropertyValue(optionsMenuName).toString();
						if (menuInstance.length() > 0) {
							EObject optionsMenuBar = componentInstance.getDesignerDataModel().findByNameProperty(
									menuInstance);
							Check.checkState(optionsMenuBar != null);
							
							// now get its one child, the menu pane
							EObject[] kids = ModelUtils.getComponentInstance(optionsMenuBar).getChildren();
							if (kids != null && kids.length > 0)
								return kids[0];
							
							// else add one...
							EObject menuPane = dataModel.createNewComponentInstance(
									menuPaneComponent);
							Check.checkState(menuPane != null);

							org.eclipse.emf.common.command.Command command = dataModel.createAddNewComponentInstanceCommand(
									optionsMenuBar, menuPane, IDesignerDataModel.AT_END);
							Check.checkState(command.canExecute());
							command.execute();
							
							return menuPane;

						}
					}
				}
			}
			instance = componentInstance.getParent();
		}
		return null;
	}

	/**
	 * Look at the items of the menu pane and find one
	 * with the given submenu id.
	 * @param menuPane 
	 * @param menuPaneId
	 * @return a menu item or null
	 */
	private EObject findMenuItemWithMenuPane(EObject menuPane, String menuPaneId) {
		EObject[] menuItems = ModelUtils.getComponentInstance(menuPane).getChildren();
		if (menuItems == null)
			return null;
		for (int i = 0; i < menuItems.length; i++) {
			EObject[] subMenus = ModelUtils.getComponentInstance(menuItems[i]).getChildren();
			if (subMenus != null) {
				for (int j = 0; j < subMenus.length; j++) {
					if (ModelUtils.isInstanceOf(subMenus[j], menuPaneId))
						return menuItems[i];
				}
			}
		}
		return null;
	}

	class AddMenuItemWithMenuPaneCommand extends AbstractCommand {

		private EObject menuPane;
		private String menuItemText;
		private String menuPaneComponentId;
		private IComponent systemMenuPaneComponent;
		private EObject newMenuItem;

		/**
		 * @param menuPane
		 * @param menuItemText
		 * @param markable_list_menupane_id
		 */
		public AddMenuItemWithMenuPaneCommand(EObject menuPane, String menuItemText, String menuPaneComponentId) {
			this.menuPane = menuPane;
			this.menuItemText = menuItemText;
			this.menuPaneComponentId = menuPaneComponentId;

			setLabel(Messages.getString("ListBoxSetValueCommandExtenderFactory.AddMarkableListCommandLabel")); //$NON-NLS-1$

		}

		/* (non-Javadoc)
		 * @see org.eclipse.emf.common.command.AbstractCommand#prepare()
		 */
		@Override
		protected boolean prepare() {
			this.systemMenuPaneComponent = dataModel.getComponentSet().lookupComponent(
					menuPaneComponentId);
			if (this.systemMenuPaneComponent == null)
				return false;
			return true;
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.emf.common.command.Command#execute()
		 */
		public void execute() {
			// make the menu item and add it
			newMenuItem = dataModel.createNewComponentInstance(menuItemComponent);
			org.eclipse.emf.common.command.Command emfCommand = dataModel.createAddNewComponentInstanceCommand(
						menuPane, newMenuItem, IDesignerDataModel.AT_END);
			if (emfCommand.canExecute())
				emfCommand.execute();
			
			IPropertySource ps = ModelUtils.getPropertySource(newMenuItem);
			ps.setPropertyValue(IMenuEditor.LABEL_PROPERTY_ID, menuItemText);
			
			MenuEditManager.assignNewName(dataModel, newMenuItem);
			
			EObject subMenuPane = dataModel.createNewComponentInstance(systemMenuPaneComponent);
			emfCommand = dataModel.createAddNewComponentInstanceCommand(
					newMenuItem, subMenuPane, IDesignerDataModel.AT_END);
			if (emfCommand.canExecute())
				emfCommand.execute();
		}

		/* (non-Javadoc)
		 * @see org.eclipse.emf.common.command.Command#redo()
		 */
		public void redo() {
			execute();
			
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.emf.common.command.AbstractCommand#undo()
		 */
		@Override
		public void undo() {
			Check.checkState(newMenuItem != null);
			org.eclipse.emf.common.command.Command command = dataModel.createRemoveComponentInstancesCommand(Collections.singletonList(newMenuItem));
			if (command.canExecute())
				command.execute();
		}
		
	}

	/*
	 
	 */

	static class SetMarkIconCommand extends AbstractCommand {

		private EObject listInstance;

		/**
		 * @param menuPane
		 * @param menuItemText
		 * @param markable_list_menupane_id
		 */
		public SetMarkIconCommand(EObject listInstance) {
			this.listInstance = listInstance;
			//setLabel(Messages.getString("ListBoxSetValueCommandExtenderFactory.SetMarkIconCommandLabel")); //$NON-NLS-1$

		}

		/* (non-Javadoc)
		 * @see org.eclipse.emf.common.command.AbstractCommand#prepare()
		 */
		@Override
		protected boolean prepare() {
			return true;
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.emf.common.command.Command#execute()
		 */
		public void execute() {
			// get project info
			ProjectImageInfo projectImageInfo = (ProjectImageInfo) ImageGlobals.getProjectImageInfo(
					ModelUtils.getComponentInstance(listInstance));
			if (projectImageInfo == null)
				return;
			
			SymbianImageValueConverter converter = new SymbianImageValueConverter();
			
			// set mark icon if empty
			IPropertySource ps = ModelUtils.getPropertySource(listInstance);
			IPropertySource imagePs = (IPropertySource) ps.getPropertyValue(LIST_MARK_ICON_PROPERTY_ID);
			ImagePropertyInfo imagePropertyInfo = (ImagePropertyInfo) converter.getEditableValue(listInstance, imagePs);
			if (imagePropertyInfo.bitmapInfo != null)
				return;
				
			// get the first mark icon, or some image
			MultiImageInfo[] imageInfos = projectImageInfo.getMultiImageInfos();
			MultiImageInfo imgFile = null;
			String theEnum = null;
			for (MultiImageInfo info : imageInfos) {
				// get a default
				if (theEnum == null) {
					theEnum = findFirstImage(info);
					imgFile = info;
				}
				
				// but override with mark icon
				String markEnum = findMarkIconBitmap(info);
				if (markEnum != null) {
					theEnum = markEnum;
					imgFile = info;
					break;
				}
			}
			if (imgFile == null || theEnum == null)
				return;
				
			ImageInfo bitmapInfo = imgFile.findImageByEnumerator(theEnum);
			Check.checkState(bitmapInfo != null);
			ImageInfo maskInfo = imgFile.getMaskForImage(bitmapInfo);
			
			imagePropertyInfo = new ImagePropertyInfo(imgFile, bitmapInfo, maskInfo);
			converter.applyEditableValue(listInstance, imagePropertyInfo, imagePs); 
		}

		/**
		 * @param info
		 * @return
		 */
		private String findMarkIconBitmap(MultiImageInfo info) {
			// now get a "mark" image, assumed to be a bitmap (not a mask)
			String[] enums = info.getImageEnumeratorList();
			if (enums.length == 0)
				return null;
			
			for (int i = 0; i < enums.length; i++) {
				if (enums[i].toLowerCase().matches(".*mark_icon")) { //$NON-NLS-1$
					return enums[i];
				}
			}
			
			return null;
		}

		/**
		 * @param info
		 * @return
		 */
		private String findFirstImage(MultiImageInfo info) {
			String[] enums = info.getImageEnumeratorList();
			if (enums.length == 0)
				return null;
			
			return enums[0];
		}

		/* (non-Javadoc)
		 * @see org.eclipse.emf.common.command.Command#redo()
		 */
		public void redo() {
			execute();
			
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.emf.common.command.AbstractCommand#undo()
		 */
		@Override
		public void undo() {
			// it was blank before
			SymbianImageValueConverter converter = new SymbianImageValueConverter();
			IPropertySource ps = ModelUtils.getPropertySource(listInstance);
			IPropertySource imagePs = (IPropertySource) ps.getPropertyValue(LIST_MARK_ICON_PROPERTY_ID);
			ImagePropertyInfo imagePropertyInfo = new ImagePropertyInfo();
			converter.applyEditableValue(listInstance, imagePropertyInfo, imagePs);
		}
		
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.datamodel.adapter.IModelAdapter#getEObject()
	 */
	public EObject getEObject() {
		return instance;
	}
}
