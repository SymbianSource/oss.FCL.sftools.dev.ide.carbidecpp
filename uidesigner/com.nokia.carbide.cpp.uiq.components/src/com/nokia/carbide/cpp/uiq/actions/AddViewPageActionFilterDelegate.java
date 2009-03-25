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
package com.nokia.carbide.cpp.uiq.actions;

import com.nokia.carbide.cpp.uiq.components.addViewPageDialog.AddViewPageDialog;
import com.nokia.carbide.cpp.uiq.components.util.UiqUtils;
import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.component.adapter.CommonAttributes;
import com.nokia.sdt.component.symbian.actionFilter.BaseComponentActionFilterDelegate;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.displaymodel.adapter.ILayoutContainer;
import com.nokia.sdt.emf.dm.INode;
import com.nokia.sdt.symbian.dm.UIQModelUtils;
import com.nokia.sdt.uidesigner.ui.command.DataModelCommandWrapper;
import com.nokia.sdt.uidesigner.ui.utils.EditorUtils;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.TextUtils;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.views.properties.IPropertySource;

public class AddViewPageActionFilterDelegate extends BaseComponentActionFilterDelegate {

	//Properties IDs
	public static final String UICONFIGURATION_PROPERTY_UICONFIG = "uiconfig"; 
	public static final String UICONFIGURATION_PROPERTY_COMMANDLIST = "commandList"; 
	public static final String UICONFIGURATION_PROPERTY_VIEWORCONTAINER = "viewOrContainer"; 
	public static final String UICONFIGURATION_PROPERTY_NAME = "name";
	
	public static final String VIEWLAYOUT_PROPERTY_NAME = "name";
	public static final String VIEWPAGE_PROPERTY_TAB_IMAGE = "tabImage";
	public static final String VIEWPAGE_PROPERTY_TAB_CAPTION = "tabCaption";
	
	public static final String CQIKCONTAINER_PROPERTY_SCROLLABLE = "scrollable";
	public static final String CQIKCONTAINER_PROPERTY_LAYOUTMANAGER = "layoutManager";
	public static final String LAYOUTMANAGER_PROPERTY_TYPE = "type";

	public static final String VIEW_PAGE = UIQModelUtils.UIQ_VIEW_PAGE;

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.symbian.actionFilter.BaseComponentActionFilterDelegate#isActionVisibleForSelectedObject(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	protected boolean isActionVisibleForSelectedObject(EObject selected) {
		return UiqUtils.getViewLayout(selected) != null;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.symbian.actionFilter.BaseComponentActionFilterDelegate#updateActionForSelectedObject(org.eclipse.jface.action.IAction, org.eclipse.emf.ecore.EObject)
	 */
	@Override
	protected void updateActionForSelectedObject(IAction action, EObject selected) {
		action.setEnabled(UiqUtils.getViewLayout(selected) != null);
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.symbian.actionFilter.BaseComponentActionFilterDelegate#executeActionCommand(org.eclipse.jface.action.IAction, org.eclipse.emf.ecore.EObject, org.eclipse.gef.commands.CommandStack)
	 */
	@Override
	protected void executeActionCommand(IAction action, EObject selected, CommandStack commandStack) {
		EObject viewLayout = UiqUtils.getViewLayout(selected);
		Check.checkState(viewLayout != null);

		CommandStack stack = (CommandStack) editor.getAdapter(CommandStack.class);
		AddViewPageDialog dialog = new AddViewPageDialog(
				(Shell) this.editor.getAdapter(Shell.class),
				stack, 
				viewLayout);
		
		int ret = dialog.open();
		if (ret == Dialog.OK) {
			addViewPageToModel(stack, viewLayout, dialog.getNameLayout(),
					dialog.getCommandListLayout(),
					dialog.getTabCaption(),dialog.getTabImageObj(),
					dialog.getCommanListView(),
					dialog.getScrollContainer(),dialog.getLayoutManager());
		}
		IComponentInstance viewLayoutInstance = ModelUtils.getComponentInstance(viewLayout);
		
		Check.checkState(viewLayoutInstance != null);
		EObject viewLayouts = viewLayoutInstance.getParent();
		Check.checkState(viewLayouts != null);
		ILayoutContainer lc = ModelUtils.getLayoutContainer(viewLayouts);
		if (lc != null) {
			lc.layoutChildren();
		}
	}
	
	/**
	 * Add layout to the model.
	 * @param nameLayout - Name of the layout
	 * @param commandListLayout - Command list for the layout
	 * @param tabCaption - Tab caption 
	 * @param tabImageObj - Object with the image property
	 * @param commandListView - Command list for the view
	 * @param scrollContainer - Scroll property for the container
	 * @param layoutManager - Type of layout manager (label)
	 */
	private void addViewPageToModel(CommandStack stack, EObject layoutOrContainersGroup, String nameLayout, String commandListLayout, String tabCaption,
				Object tabImageObj, String commandListView, String scrollContainer, String layoutManager){
		int design = 0; //0 view , 1 simple dialog
		EObject container = null;
		IPropertySource viewPageProperties; 
		IPropertySource containerProperties,layoutManagerProperties;
		IComponentSet componentSet = editor.getDataModel().getComponentSet();
		Check.checkContract(componentSet != null);
		org.eclipse.emf.common.command.Command emfCommand = null;
		if (design == 0){ // If the model is a View, set the properties for the view page	
			IComponent viewPageComponent = componentSet.lookupComponent(VIEW_PAGE);
			Check.checkContract(viewPageComponent != null);
			EObject viewPage = editor.getDataModel().createNewComponentInstance(viewPageComponent);
			emfCommand = editor.getDataModel().createAddNewComponentInstanceCommand(layoutOrContainersGroup, viewPage, IDesignerDataModel.AT_END);
			Check.checkState(emfCommand.canExecute());
			DataModelCommandWrapper wrapper = new DataModelCommandWrapper();
			wrapper.setDataModelCommand(emfCommand);
			wrapper.setLabel(TextUtils.inverseTitleCase(Messages.getString("AddViewPageActionFilterDelegate.AddNewPageCommand")));
			stack.execute(wrapper);
			
			viewPageProperties=ModelUtils.getPropertySource(viewPage);
			viewPageProperties.setPropertyValue(VIEWPAGE_PROPERTY_TAB_CAPTION,tabCaption);
			if (tabImageObj != null){
				viewPageProperties.setPropertyValue(VIEWPAGE_PROPERTY_TAB_IMAGE,tabImageObj);
			}
			
			viewPageProperties.setPropertyValue(UICONFIGURATION_PROPERTY_COMMANDLIST,commandListView);
			container = ModelUtils.findFirstComponentInstance(viewPage, UIQModelUtils.UIQ_CQIKCONTAINER, true);
		}
		else {
			//container=ModelUtils.findFirstComponentInstance(layoutOrContainersGroup, UIQ_CQIKCONTAINER, true);
		}
		
		//set container properties
		containerProperties = ModelUtils.getPropertySource(container);
		containerProperties.setPropertyValue(CQIKCONTAINER_PROPERTY_SCROLLABLE, scrollContainer);
		layoutManagerProperties = (IPropertySource)containerProperties.getPropertyValue(CQIKCONTAINER_PROPERTY_LAYOUTMANAGER);
		layoutManagerProperties.setPropertyValue(LAYOUTMANAGER_PROPERTY_TYPE,layoutManager);
		createLayoutManager(stack, container, layoutManager);
		
		EditorUtils.setSelectionToAffectedObjects(editor, emfCommand.getAffectedObjects());
	}
	
	/**
	 * Creates the layout manager
	 * @param layoutManagerTypeId - Id for the layout manager type
	 * @param container - Container for the layout manager
	 */
	private void createLayoutManager(CommandStack stack, EObject container, String layoutManagerTypeId){
		EObject layoutManager;
		
		layoutManager = ModelUtils.findImmediateChildWithAttributeValue(container, CommonAttributes.IS_LAYOUT_MANAGER, "true"); //returns the actual layout manager
		if (layoutManager != null && (layoutManagerTypeId == null ||((INode)layoutManager).getComponentId().equals(layoutManagerTypeId))){
			return;
		}
		else{
			IComponent layoutManagerComponent = editor.getDataModel().getComponentSet().lookupComponent(layoutManagerTypeId);
			if(layoutManagerComponent == null)	System.out.println("*********Layout is null************");
			Check.checkContract(layoutManagerComponent != null);
			EObject gridChild = editor.getDataModel().createNewComponentInstance(layoutManagerComponent);
			
			Command addLayoutManagerCommand = editor.getDataModel().createAddNewComponentInstanceCommand(container, gridChild, 0);
			
			DataModelCommandWrapper wrapper = new DataModelCommandWrapper();
			wrapper.setDataModelCommand(addLayoutManagerCommand);
			wrapper.setLabel(TextUtils.inverseTitleCase(Messages.getString("AddViewPageActionFilterDelegate.ChangeLayoutManagerCommand")));
			stack.execute(wrapper);		
		}
	}
}
