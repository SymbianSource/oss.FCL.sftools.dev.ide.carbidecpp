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
/* START_USECASES: CU6 END_USECASES */
package com.nokia.carbide.cpp.uiq.ui.vieweditor;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.views.properties.IPropertySource;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.editor.IDesignerDataModelEditor;
import com.nokia.sdt.symbian.dm.UIQModelUtils;
import com.nokia.cpp.internal.api.utils.core.Check;

/**
 * This allows the TreeViewer to be the target of a drop operation.
 *
 */
public class CommandTreeDropAdapter extends ViewerDropAdapter {
	private CommandElementModel modelCommands;
	private IDesignerDataModelEditor editor;
	private TreeViewer viewer;
	private final String UI_ANONYMOUS_GROUP = "com.nokia.carbide.uiq.AnonymousGroup"; //$NON-NLS-1$
	private final String UI_NAMED_GROUP = "com.nokia.carbide.uiq.NamedGroup"; //$NON-NLS-1$
	private final String UI_COMMAND = UIQModelUtils.UIQ_COMMAND;
	private final String COMMAND_PROPERTY_NAMED_GROUP = "namedGroup"; //$NON-NLS-1$
	private final String COMMAND_PROPERTY_ANONYMOUS_GROUP = "anonymousGroup"; //$NON-NLS-1$
	/**
	 * Class constructor
	 * @param viewer
	 * @param modelCommands
	 * @param editor
	 */
	protected CommandTreeDropAdapter(Viewer viewer, CommandElementModel modelCommands,
									IDesignerDataModelEditor editor) {
		super(viewer);
		this.viewer = (TreeViewer) viewer;
		this.modelCommands = modelCommands;
		this.editor = editor;
	}

	/**
	 * Method declared on ViewerDropAdapter
	 * @param target - Item target
	 * @param op - 
	 * @param type - Type of data to be dropped  
	 */
	public boolean validateDrop(Object target, int op, TransferData type) {
	      return CommandElementTransfer.getInstance().isSupportedType(type);
	}
	/**
	 * To change the default operation(MOVE) when the CTRL key
	 * is pressed to COPY
	 * @param event - Drag event
	 */ 
	public void dragEnter(DropTargetEvent event) {
		if (event.detail == DND.DROP_COPY || (modelCommands.duplicate.getSelection())){
	    	modelCommands.isCopy = true;
	    	event.detail = DND.DROP_COPY;	    	
	    }
	    else{
	    	modelCommands.isCopy = false;
	    	event.detail = DND.DROP_MOVE;
	    }
	}
	
	/**
	 * When the user presses the CTRL key and releases then the event change to copy.
	 * @param event
	 */
	public void dragOperationChanged(DropTargetEvent event) {
		if (event.detail == DND.DROP_DEFAULT) {
		     if ((event.operations & DND.DROP_COPY) != 0) {
		            event.detail = DND.DROP_COPY;
		            modelCommands.isCopy = true;		          
		     }
		     else {
		 	       event.detail = DND.DROP_NONE;
		 	 }
		}		
	}
	
	/**
	 * Method declared on ViewerDropAdapter 
	 * @param data - Object of the domain model, casted as the type ElementTrasnfer
	 * @return flag - If the drop operation was completed returns true or else false 
	 */
	 public boolean performDrop(Object data) {
	    Object target = (Object)getCurrentTarget();	    
	    boolean flag = false;
	    if (target == null || (
	    	!(target instanceof CommandModel) &&
	    	!(target instanceof CommandList) &&
	    	!(target instanceof AnonymousGroup) &&
	    	!(target instanceof NamedGroup))){
	       return flag;
	    } 
	    ElementTransfer disguise = (ElementTransfer) data;
	    //cannot drop an element onto itself or a child, when is a move operation
	    if (!modelCommands.isCopy){	    		
				Object objectDrop = returnElement(disguise);
				flag = canDropThisTarget(objectDrop,target);			
				if (!flag){
					return flag;
				}
	    }
	    if ((disguise.getComponentID().equals(UI_COMMAND)) ||
	      (disguise.getComponentID().equals(UI_NAMED_GROUP)) ){
	      flag = dropCommandOrNamed(disguise, target, modelCommands.isCopy, null);
	    } else if (disguise.getComponentID().equals(UI_ANONYMOUS_GROUP)){
	      flag = dropAnonymous(disguise, target, modelCommands.isCopy, null);
	    }
	    viewer.refresh();
	    return flag;
	}	

	 /**
	  * Returns the element in the model that corresponds to the ElementTransfer
	  * @param disguise
	  * @return element in the model casted as object
	  */
	private Object returnElement(ElementTransfer disguise) {
		CommandList list = modelCommands.getCommandListByName(disguise.getCommandListName());
		if (disguise.getComponentID().equals(UI_COMMAND)){
		    return modelCommands.getCommandByNameList(list,disguise.getName());
		   	
		}
		else if (disguise.getComponentID().equals(UI_NAMED_GROUP)){
			return modelCommands.getGroupByName(list, disguise.getName(), 0);
		}
		else if (disguise.getComponentID().equals(UI_ANONYMOUS_GROUP)){
			return modelCommands.getGroupByName(list, disguise.getName(), 1);
		}
		return null;
	}
	
	/**
	 * Returns true if the operation is allowed, or false, if the target is a child
	 * of the objectToDrop.
	 * @param objectToDrop
	 * @param target
	 * @return canDo
	 */
	private boolean canDropThisTarget(Object objectToDrop, Object target){
		boolean canDo = true;
		Object parent = null;
		CommandElementTreeContentProvider provider = 
			(CommandElementTreeContentProvider)	viewer.getContentProvider();
		parent = provider.getParent(target);
		if (parent == null)
			return canDo;
		while(!(parent instanceof CommandList) && canDo){
			canDo = !parent.equals(objectToDrop);
			parent = provider.getParent(parent);
		}	
		return canDo;
	}
	
	/**
	 * This method executes the drop operation for a Anonymous Group
	 * @param disguise - Element transfer
	 * @param target - Object of the domain model
	 * @param isCopy - flag for the operation
	 * @param oldNewNames - HashTable to keep the names of the references when the operation is a copy.
	 * @return - If the operation was completed returns true or else false
	 */ 
	private boolean dropAnonymous(ElementTransfer disguise, Object target, boolean isCopy,
			Hashtable<String, String> oldNewNames) {
		CommandList listTarget;
		
		if (target instanceof AnonymousGroup){			
			return false;
		}
		if (isCopy && (oldNewNames == null)){
			oldNewNames = new Hashtable<String, String>();
		}
		if (target instanceof CommandList){
			listTarget = (CommandList) target;
		}
		else{
			listTarget = ((CommandElementBase) target).getParent();
		}		
		CommandList listDrop = modelCommands.getCommandListByName(disguise.getCommandListName());	
		CommandList listVerify = listDrop;
		AnonymousGroup anonymousToDrop = (AnonymousGroup) modelCommands.getGroupByName(listDrop,
				disguise.getName(), 1);			
		IComponentInstance instance = ModelUtils.lookupReference(editor.getDataModel(),
				anonymousToDrop.getRealName());
		String newName = anonymousToDrop.getRealName();
		String oldName = newName;
		String namedGroup = ""; //$NON-NLS-1$
		String anonymousGroup = oldName;
		if (target instanceof NamedGroup){
			namedGroup = ((CommandElementBase)target).getName();
		}
		if (target instanceof CommandModel){
			if (promoveCommandNamedGroup()) {
				NamedGroup newNamedGroup = checkPromotionCommand(target, listTarget);
				return dropAnonymous(disguise, newNamedGroup,
						isCopy, oldNewNames);
			}
			else{
				return false;
			}
		}
		if (!listTarget.equals(listDrop)){//different commandList			
			newName = modelCommands.addAnonymousGroup(target);//Creates the new instance
			if (isCopy){
				oldNewNames.put(oldName, newName);
				anonymousGroup = newName;
			}
		}
		else{
			if (isCopy){
				newName = modelCommands.addAnonymousGroup(target);//Creates the new instance
				oldNewNames.put(oldName, newName);
				anonymousGroup = newName;
			}
		}
		CommandElementTreeContentProvider provider = 
					(CommandElementTreeContentProvider)	viewer.getContentProvider();
		Object[] children = provider.getChildren(anonymousToDrop);
		//Creates the newInstance in the newCommandList		
		if (!listTarget.equals(listDrop) && !modelCommands.isCopy){//different commandList
			modelCommands.deleteGroup(instance);
			modelCommands.setPropertyElement(newName, modelCommands.COMMAND_PROPERTY_NAME,
					oldName);
			listVerify = listTarget;
		}
		for(Object child : children){
			ElementTransfer childElement = new ElementTransfer(((CommandElementBase)child).getName());
			childElement.setCommandListName(((CommandList)
					(((CommandElementBase)child).getParent())).getName());
			if (child instanceof CommandModel){
				childElement.setComponentID(UI_COMMAND);
				//listDrop = modelCommands.getCommandListByName(nameListDrop);
				if (anonymousToDrop.getType() == 1){					
					AnonymousGroup realGroup = (AnonymousGroup) modelCommands.getGroupByName(listVerify, 
																			anonymousToDrop.getRealName(), 1);
					realGroup.addChildren();
				}
				dropCommandOrNamed(childElement, listTarget, isCopy, oldNewNames);
				if (anonymousToDrop.getType() == 1){
					AnonymousGroup realGroup = (AnonymousGroup) modelCommands.getGroupByName(listVerify, 
																			anonymousToDrop.getRealName(), 1);
					realGroup.removeChildren();
				}
			} else if (child instanceof AnonymousGroup){
				childElement.setComponentID(UI_ANONYMOUS_GROUP);
				dropAnonymous(childElement, listTarget, isCopy, oldNewNames);
			} else if (child instanceof NamedGroup){
				childElement.setComponentID(UI_NAMED_GROUP);
				dropCommandOrNamed(childElement, listTarget, isCopy, oldNewNames);
			}
		}
		for(Object child : children){
			if (!(child instanceof AnonymousGroup)){
				String name = ((CommandElementBase)child).getName();
				if (isCopy){
					name = oldNewNames.get(name);
				}				
				modelCommands.setGroups(name, namedGroup, anonymousGroup);
			}				
		}	
		//Set the focus to the GroupToDrop
		AnonymousGroup finalGroup = (AnonymousGroup) modelCommands.getGroupByName(listTarget, 
				anonymousGroup.concat(namedGroup), 1);
		if (finalGroup != null){
			viewer.setSelection(new StructuredSelection(finalGroup), true);
		}
		return true;			
	}
	/**
	 * Executes the drop operation for a Command or a Named group
	 * @param disguise - Object to be dropped.
	 * @param target - Object of the domain
	 * @param isCopy - flag for the operation
	 * @param oldNewNames - HashTable to keep the names of the references when the operation is a copy.
	 * @return - If the operation was completed returns true or else false
	 */ 
	private boolean dropCommandOrNamed(ElementTransfer disguise, Object target, boolean isCopy,
			Hashtable<String, String> oldNewNames) {
		CommandList list = modelCommands.getCommandListByName(disguise.getCommandListName());
		NamedGroup namedToDrop = null;
		if (isCopy && (oldNewNames == null)){
			oldNewNames = new Hashtable<String, String>();
		}
		if (disguise.getComponentID().equals(UI_COMMAND)){			
		   return dropCommand(disguise, target, isCopy, oldNewNames); 	    
		}
		else{ //Drop a named group
				namedToDrop = (NamedGroup) modelCommands.getGroupByName(list,
						disguise.getName(), 0);			
				String namedGroup = ""; //$NON-NLS-1$
				String anonymousGroup = ""; //$NON-NLS-1$
				CommandList listTarget;
				if (target instanceof CommandList){
					listTarget = (CommandList) target;
				}
				else{
					listTarget = ((CommandElementBase) target).getParent();
				}
				CommandList listDrop = namedToDrop.getParent(); 
				if (target.equals(namedToDrop)){
					return false;
				}
				if (isCopy){
					namedGroup = ""; //$NON-NLS-1$
					anonymousGroup = ""; //$NON-NLS-1$
				}
				if (target instanceof CommandModel){
					if (promoveCommandNamedGroup()) {
						NamedGroup newNamedGroup = checkPromotionCommand(target, listTarget);
						boolean flag = dropCommandOrNamed(disguise, newNamedGroup,
								isCopy, oldNewNames);
						//Set the focus to the GroupToDrop
						viewer.setSelection(new StructuredSelection(newNamedGroup), true);
						return flag;
					}
					else{
						return false;
					}
				} else if (target instanceof NamedGroup){
					namedGroup = ((CommandElementBase)target).getName();
				} else if (target instanceof AnonymousGroup){
					AnonymousGroup groupTarget =((AnonymousGroup)target);
					anonymousGroup = groupTarget.getRealName();					
					if (groupTarget.getType() == 1){
						anonymousGroup = groupTarget.getRealName();
						namedGroup = groupTarget.getNamedGroupName();
					}
				} else if (target instanceof CommandList){
					namedGroup = "";
					anonymousGroup = "";
				}			
				if ((listTarget.equals(listDrop)) && !isCopy){
					modelCommands.setGroups(namedToDrop.getName(), namedGroup, anonymousGroup);
					//Sets the focus to the GroupToDrop
					viewer.setSelection(new StructuredSelection(namedToDrop), true);
					return true;
				}
				else{
					if (isCopy){ //Save the properties for the original group
						namedGroup = namedToDrop.getNGParent() != null ? namedToDrop.getNGParent().getName() : "";
						anonymousGroup = namedToDrop.getAGParent() != null ? namedToDrop.getAGParent().getRealName() : "";						
					}
					setGroupsValue(namedToDrop.getName(),"","");					
					modelCommands.moveNamedGroup(namedToDrop, target, oldNewNames);	
					if (isCopy){ //Reset the properties for the original group
						setGroupsValue(namedToDrop.getName(), namedGroup, anonymousGroup);
					}
					return true;
				}				
			}				
	}

	private void setGroupsValue(String name, String named, String anonymous) {
		IPropertySource propertySource;
		propertySource = ModelUtils.getPropertySource(ModelUtils.lookupReference(editor.getDataModel(),
				name).getEObject());
		CompoundCommand cc = new CompoundCommand();		
		cc.append(modelCommands.createSetPropertyCommand(propertySource, COMMAND_PROPERTY_NAMED_GROUP, named));
		cc.append(modelCommands.createSetPropertyCommand(propertySource, COMMAND_PROPERTY_ANONYMOUS_GROUP, anonymous));
		modelCommands.executeCompoundCommand(cc, Messages.getString("CommandsPage.commandListSection.actions.setGroups"));
	}
	
	/**
	 * Executes the drop operation for a Command 
	 * @param disguise - Object to be dropped.
	 * @param target - Object of the domain
	 * @param isCopy - flag for the operation
	 * @param oldNewNames - HashTable to keep the names of the references when the operation is a copy.
	 * @return - If the operation was completed returns true or else false
	 */ 
	private boolean dropCommand(ElementTransfer disguise, Object target, boolean isCopy,
			Hashtable<String, String> oldNewNames){
		CommandList list = modelCommands.getCommandListByName(disguise.getCommandListName());
		CommandModel commandToDrop = null;
		CommandList listTarget = null;
		String nameNamedGroup = ""; //$NON-NLS-1$
		String nameAnonymousGroup = ""; //$NON-NLS-1$
		if (target instanceof CommandList){
			listTarget = (CommandList)target;
		}
		else{
			listTarget = ((CommandElementBase)target).getParent();
		}		 
		if (isCopy && (oldNewNames == null)){
			oldNewNames = new Hashtable<String, String>();
		}
		List<CommandModel> commandsList = list.getCommands();		   
	    for(CommandModel command : commandsList){//Find the command in the local model
	    	if (command.getName().equals(disguise.getName())){
	    		commandToDrop = command;
	    		break;
	    	}
	    }
	    IComponentInstance instance = ModelUtils.lookupReference(editor.getDataModel(),
				commandToDrop.getName());
	    if (target.equals(commandToDrop)){
	    	return false;
	    }
	    if (target instanceof NamedGroup){
	    	nameNamedGroup = ((NamedGroup) target).getName();
	    } else if (target instanceof AnonymousGroup){
                AnonymousGroup anonymousTarget = ((AnonymousGroup) target);
                if (anonymousTarget.getType() == 1){
                	nameNamedGroup = anonymousTarget.getNamedGroupName();
                	nameAnonymousGroup = anonymousTarget.getRealName();
                }
                else{
                	nameAnonymousGroup = anonymousTarget.getName();
                }	    	
	    } else if (target instanceof CommandModel){
	    	if (promoveCommandNamedGroup()){
	    		NamedGroup newNamedGroup = checkPromotionCommand(target, listTarget);
	    		return dropCommand(disguise, newNamedGroup, isCopy, oldNewNames);
	    	}
	    	else{
	    		return false;
	    	}
	    }	    	
	    if((list.equals(listTarget)) &&  !isCopy){//its the same list, only a change of properties.
	    		modelCommands.setGroups(commandToDrop.getName(), nameNamedGroup, nameAnonymousGroup);
	    		return true;
	    	}
	   else{		    	
			modelCommands.moveCommand(instance, target, oldNewNames);				
			return true;
		}
	    
	}
	
	/**
	 * Launches the message for the promotion of the command.
	 * @return - true if the user press yes or no if the user cancel the operation
	 */
	private boolean promoveCommandNamedGroup() {
		MessageBox confirm = new MessageBox(Display.getCurrent().getActiveShell(),
				SWT.ICON_QUESTION | SWT.YES | SWT.NO);
	    String text = Messages.getString(
	    		"CommandsPage.commandListSection.actions.promoteCommand"); //$NON-NLS-1$
	    confirm.setMessage(text);
		confirm.setText(Messages.getString(
				"CommandsPage.commandListSection.msgs.promoveCommand.title")); //$NON-NLS-1$
		if (confirm.open() == SWT.YES) {			
			return true;
		}
		else{
			return false;
		}		
	}
	/**
	 * Executes the promotion and returns the new NamedGroup
	 * @param target
	 * @param listTarget
	 * @return
	 */
	private NamedGroup checkPromotionCommand(Object target, CommandList listTarget){
		NamedGroup newNamedGroup = null;
		String nameList = listTarget.getName();
		String newNameGroup = modelCommands
						.executePromotion((CommandModel) target);
		newNamedGroup = (NamedGroup) modelCommands
						.getGroupByName(modelCommands.getCommandListByName(nameList), newNameGroup, 0);
		return newNamedGroup;		
	}
	
	/**
	 * Only can receive named group, anonymous group and commands. 
	 * @param data - Object of the domain model, casted as the type ElementTrasnfer
	 * @return flag - If the drop operation was completed returns true or else false 
	 */
	 public boolean performCopy(Object data, Object parent) {
	    boolean flag = false;
	    String nameInstance = "";
	    String componentID = "";
	    nameInstance = ((CommandElementBase) data).getName();
	    if (data instanceof AnonymousGroup){
	    	AnonymousGroup group = (AnonymousGroup) data; 
	    	if (group.getType() == 1){
	    		parent = group.getNGParent();
	    	}
	    	componentID = UI_ANONYMOUS_GROUP;
	    }
	    else{	    	
	    	componentID = ModelUtils.lookupReference(editor.getDataModel(), nameInstance).getComponentId();
	    }
		String nameCommandList = ((CommandElementBase) data).getParent().getName();		
		ElementTransfer element = new ElementTransfer(nameInstance); 
		element.setComponentID(componentID);
        element.setCommandListName(nameCommandList);
	    if ((element.getComponentID().equals(UI_COMMAND)) ||
	      (element.getComponentID().equals(UI_NAMED_GROUP)) ){
	      flag = dropCommandOrNamed(element, parent, true, null);
	    } else if (element.getComponentID().equals(UI_ANONYMOUS_GROUP)){
	      flag = dropAnonymous(element, parent, true, null);
	    }
	    viewer.refresh();
	    return flag;
	}
}
