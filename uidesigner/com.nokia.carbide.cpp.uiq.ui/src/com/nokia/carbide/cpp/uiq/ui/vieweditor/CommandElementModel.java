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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

import com.nokia.carbide.cpp.uiq.ui.viewwizard.WizardUtils;
import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IComponentInstanceChildListener;
import com.nokia.sdt.datamodel.adapter.IComponentInstancePropertyListener;
import com.nokia.sdt.datamodel.adapter.IEventBinding;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.datamodel.util.SetPropertyCommand;
import com.nokia.sdt.editor.IDesignerDataModelEditor;
import com.nokia.sdt.symbian.dm.UIQModelUtils;
import com.nokia.sdt.symbian.ui.images.DirectEditingUtilities;
import com.nokia.cpp.internal.api.utils.core.Check;
/**
 * This class keeps the local model, the representation of the UIQ model for the 
 * TreeViewer. It keeps the local and the UIQ model updated, and it calls the refresh 
 * method to keep the TreeViewer updated.
 *
 */
public class CommandElementModel {
	IDesignerDataModelEditor editor;
	UIConfigurationPageUtils pageUtils;
	private List<CommandList> commandLists;
	private List<CommandId> commandIdsList;
	private TreeViewer treeViewer;
	public String uiDesignName;
	public boolean isCopy = false;
	public Button duplicate;
	private EObject commandListConfigurationGroup;
	//Generic IDs
	private final String UI_COMMAND_LISTS_GROUP = UIQModelUtils.UIQ_COMMAND_LISTS_GROUP; 
	private final String UI_COMMAND_LIST = UIQModelUtils.UIQ_COMMAND_LIST;
	private final String UI_ANONYMOUS_GROUP = "com.nokia.carbide.uiq.AnonymousGroup"; //$NON-NLS-1$
	private final String UI_NAMED_GROUP = "com.nokia.carbide.uiq.NamedGroup"; //$NON-NLS-1$
	private final String UI_COMMAND = UIQModelUtils.UIQ_COMMAND;
	private final String UI_COMMAND_ID = UIQModelUtils.UIQ_COMMAND_ID;
	public final String COMMAND_PROPERTY_NAMED_GROUP = "namedGroup"; //$NON-NLS-1$
	public final String COMMAND_PROPERTY_ANONYMOUS_GROUP = "anonymousGroup"; //$NON-NLS-1$
	public final String COMMAND_PROPERTY_COMMAND_ID = "commandId"; //$NON-NLS-1$
	private IComponentInstanceChildListener commandListChildListener;
	public static final String COMMAND_PROPERTY_TYPE = "type";  //$NON-NLS-1$
	public static final String COMMAND_PROPERTY_NAME = "name"; //$NON-NLS-1$
	public static final String COMMAND_PROPERTY_STATEFLAGS = "stateFlags";  //$NON-NLS-1$
	public static final String COMMAND_PROPERTY_STATEFLAGS_DEBUGONLY = "EQikCmdFlagDebugOnly";  //$NON-NLS-1$
	public static final String COMMAND_PROPERTY_TEXT = "text";  //$NON-NLS-1$
	public static final String COMMAND_PROPERTY_SHORTTEXT = "shortText";  //$NON-NLS-1$
	public static final String COMMAND_ID_PROPERTY_NAME = "name"; //$NON-NLS-1$
	public static final String COMMAND_ID_PROPERTY_COMMAND_ID = "commandId"; //$NON-NLS-1$
	public static final String COMMAND_ID_PROPERTY_SYSTEM_ID = "systemCommandId"; //$NON-NLS-1$
	public static final String COMMAND_ID_EVENT="commandEvent"; //$NON-NLS-1$
	//Properties IDs
	public static final String ICON_PROPERTY = "icon"; //$NON-NLS-1$
	public static final String BMP_FILE_PROPERTY = "bmpfile"; //$NON-NLS-1$
	public static final String BMP_ID_PROPERTY = "bmpid"; //$NON-NLS-1$
	public static final String BMP_MASK_PROPERTY = "bmpmask"; //$NON-NLS-1$
	//Listeners
	public IComponentInstancePropertyListener commandListPropertyListener;
	public IComponentInstancePropertyListener commandPropertyListener;
	public IComponentInstancePropertyListener namedGroupPropertyListener;
	public IComponentInstancePropertyListener anonymousGroupPropertyListener;
	private IComponentInstancePropertyListener commandIDPropertyListener;
	
	/**
	 * Class constructor, initializes the variables.
	 * @param editor - DataModelEditor
	 * @param treeViewer - TreeViewer that contains all the elements.
	 * @param uiDesignName - Name of the uiDesing
	 */
	public CommandElementModel(IDesignerDataModelEditor editor, TreeViewer treeViewer,
								String uiDesignName){
		this.editor = editor;
		this.pageUtils = new UIConfigurationPageUtils(editor);
		this.treeViewer = treeViewer;
		this.uiDesignName = uiDesignName;
		commandLists = new ArrayList<CommandList>();
		commandIdsList = new ArrayList<CommandId>();	
	}
		
	/**
	 * Creates the initial data of the local Model from the Real Model.
	 * @param duplicateBtn - To verify the type of operation (copy or move)
	 */
	public void initData(Button duplicateBtn) {
		duplicate = duplicateBtn;
		Vector<String> currentCommandsLists = pageUtils.getCurrentObjectsFromModel(UI_COMMAND_LIST);
		Vector<String> currentCommandIDs = pageUtils.getCurrentObjectsFromModel(UI_COMMAND_ID);
		for (String commandIdName : currentCommandIDs){
			CommandId commandIDModel = new CommandId(commandIdName);
			commandIdsList.add(commandIDModel);
		}
		for (String idName : currentCommandsLists) {
			CommandList newList = new CommandList(idName);
			IComponentInstance commandListObject = ModelUtils.lookupReference(editor.getDataModel(),
										idName);
			commandListObject.addChildListener(commandListChildListener);
			commandListObject.addPropertyListener(commandListPropertyListener);
			commandLists.add(newList);
			EObject[] children = ModelUtils.lookupReference(editor.getDataModel(), idName).getChildren();
			for (int i = 0; i < children.length; i++){
				if (ModelUtils.getComponentInstance(children[i]).
						getComponentId().equals(UI_NAMED_GROUP)){
					ModelUtils.getComponentInstance(children[i]).addPropertyListener(namedGroupPropertyListener);
					String name= ModelUtils.getComponentInstance(children[i]).getName();
					NamedGroup newNamed = new NamedGroup(name);
					newNamed.setParent(newList);
					newList.addNamedGroup(newNamed);
				}
				//Creating all the AnonymousGroup as Type 0
				else if (ModelUtils.getComponentInstance(children[i]).
						getComponentId().equals(UI_ANONYMOUS_GROUP)){
					ModelUtils.getComponentInstance(children[i]).addPropertyListener(anonymousGroupPropertyListener);
					String name= ModelUtils.getComponentInstance(children[i]).getName();
					AnonymousGroup newAnonymous = new AnonymousGroup(name, 0); 
					newAnonymous.setParent(newList);
					newList.addAnonymousGroup(newAnonymous);
				}
				else if (ModelUtils.getComponentInstance(children[i]).
						getComponentId().equals(UI_COMMAND)){
					ModelUtils.getComponentInstance(children[i]).addPropertyListener(commandPropertyListener);
					String name= ModelUtils.getComponentInstance(children[i]).getName();
					CommandModel newCommand = new CommandModel(name, newList);
					IPropertySource properties = ModelUtils.getPropertySource(ModelUtils.
							lookupReference(editor.getDataModel(),name).getEObject());
					String commandID = (String) properties.getPropertyValue(COMMAND_PROPERTY_COMMAND_ID);
					if (!commandID.equals("")) { //$NON-NLS-1$
						CommandId commandIdReference = getCommandIdByName(commandID);
						if (commandIdReference != null){
							commandIdReference.refreshReference(1);
							newCommand.setCommandID(commandID);
						}
					}
					newList.addCommand(newCommand);
				}				
			}
		}		
		for (CommandList list: commandLists){
			setGroupParents(list, UI_COMMAND);			
			setGroupParents(list, COMMAND_PROPERTY_NAMED_GROUP);
		}
	}
	
	/**
	 * Set the local variable
	 * @param tree
	 */
	public void setTreeViewer(TreeViewer tree){
		this.treeViewer = tree;
	}
	
	/**
	 * Set the local variable (listener and the command list group)
	 * @param commandListChildListener
	 */	
	public void setListener(
			IComponentInstanceChildListener commandListChildListener) {
		this.commandListChildListener = commandListChildListener;
		commandListConfigurationGroup = UIConfigurationPageUtils.getObjectById(editor.getDataModel(),
				UI_COMMAND_LISTS_GROUP);
	}
	
	/**
	 * Set the local variables.
	 * @param commandListPropertyListener
	 * @param commandIDPropertyListener
	 * @param commandPropertyListener
	 * @param namedGroupPropertyListener
	 * @param anonymousGroupPropertyListener
	 */
	public void setPropertyListeners(
			IComponentInstancePropertyListener commandListPropertyListener,
			IComponentInstancePropertyListener commandIDPropertyListener,
			IComponentInstancePropertyListener commandPropertyListener,
			IComponentInstancePropertyListener namedGroupPropertyListener,
			IComponentInstancePropertyListener anonymousGroupPropertyListener) {
		this.commandListPropertyListener = commandListPropertyListener;
		this.commandIDPropertyListener = commandIDPropertyListener;
		this.commandPropertyListener = commandPropertyListener;
		this.namedGroupPropertyListener = namedGroupPropertyListener;
		this.anonymousGroupPropertyListener = anonymousGroupPropertyListener;
	}
	
	/**
	 * Set the parents (named and anonymous group) of the commands (or namedGroups) 
	 * for each commandList.
	 * @param list
	 * @param type
	 */
	private void setGroupParents(CommandList list, String type) {
		Object[] childrenList;
		if (type.equals(UI_COMMAND)){
			childrenList = list.getCommands().toArray();
		}
		else{
			childrenList = list.getNamedGroups().toArray();
		}
		
		for (int i = 0; i < childrenList.length; i++){
			IPropertySource properties;
			String name;
			name = ((CommandElementBase) childrenList[i]).getName();
			properties = ModelUtils.getPropertySource(ModelUtils.
											lookupReference(editor.getDataModel(),name).getEObject());
			String strAnonymousGroup = (String) properties.getPropertyValue(COMMAND_PROPERTY_ANONYMOUS_GROUP);
			String strNamedGroup = (String) properties.getPropertyValue(COMMAND_PROPERTY_NAMED_GROUP);
			NamedGroup namedGroup = null;
			AnonymousGroup anonymousGroup = null;
			//It has a namedGroup assigned
			if (!strNamedGroup.equals("")){	 //$NON-NLS-1$
				namedGroup = (NamedGroup)(getGroupByName(list, strNamedGroup, 0));
				if (type.equals(UI_COMMAND)){
					((CommandModel) childrenList[i]).setNGParent(namedGroup);
				}
				else{
					((NamedGroup) childrenList[i]).setNGParent(namedGroup);
				}
				namedGroup.addChildren();
			}
			//It has a AnonymousGroup assigned, then we have to check if it also has a 
			//namedgroup. 
			if (!strAnonymousGroup.equals("") ){ //$NON-NLS-1$
				anonymousGroup = (AnonymousGroup)(getGroupByName(list, strAnonymousGroup, 1));
				//If it has a namedGroup, then we have to change the type of the anonymousGroup
				//and set the namedGroup Parent of this group.
				if ((!strNamedGroup.equals(""))){ //$NON-NLS-1$
					String combinedName = strAnonymousGroup.concat(strNamedGroup);
					anonymousGroup = (AnonymousGroup)(getGroupByName(list, combinedName, 1));
					if (anonymousGroup == null){
						anonymousGroup = new AnonymousGroup(strAnonymousGroup.
											concat(strNamedGroup), 1);
						anonymousGroup.setNGParent(namedGroup);
						anonymousGroup.setNGName(namedGroup);
						anonymousGroup.setParent(list);
						list.addAnonymousGroup(anonymousGroup);						
					}
				}				
				((CommandElementBase) childrenList[i]).setAGParent(anonymousGroup);
				anonymousGroup.addChildren();
			}
			
		}
	}

	/**
	 * Returns the commandId instance 
	 * @param name - Name of the commandId 
	 * @return commandId instance 
	 */
	public CommandId getCommandIdByName(String name) {
		for (CommandId commandId : commandIdsList){
			if (commandId.getName().equals(name)){
					return commandId;
				}
		}
		return null;
	}
	
	/**
	 * Returns the instance of the group that is in the command list given.
	 * @param list
	 * @param name - Name of the group
	 * @param type - 0: named, 1: anonymous
	 * @return instance of the group object-casted.
	 */
	public Object getGroupByName(CommandList list, String name, int type){ 
		if (type == 0){
			List<NamedGroup> listGroup = list.getNamedGroups();
			for (NamedGroup namedGroup : listGroup){
				if (namedGroup.getName().equals(name))
					return namedGroup;
			}
		}
		else {
			List<AnonymousGroup> listGroup = list.getAnonymousGroups();
			for (AnonymousGroup anonymousGroup : listGroup){
				if (anonymousGroup.getName().equals(name))
					return anonymousGroup;
			}
		}
		return null;
	}
	
	/**
	 * Return the commandLists
	 * @return All the commandLists instance in the local model.
	 */
	public List<CommandList> getCommandListElements() {
		return this.commandLists;
	}
	
	/**
	 * Returns the command List instance with the given name.
	 * @param name - Name of the command list
	 * @return Instance of the command list.
	 */
	public CommandList getCommandListByName(String name){
		for (CommandList list : commandLists){
			if (list.getName().equals(name)){
				return list;
			}
		}
		return null;
	}

	/**
	 * Returns the command instance within the local model, that has the given command list
	 * as parent and the name.
	 * @param list
	 * @param nameCommand - Parent of the command
	 * @return Command instance
	 */
	public CommandModel getCommandByNameList(CommandList list, String nameCommand){
		List<CommandModel> commands = list.getCommands();
		for (CommandModel command : commands){
			if (command.getName().equals(nameCommand)){
				return command;
			}
		}		
		return null;
	}
	
	/**
	 * Sets the properties NG and AG of the instance given 
	 * @param nameInstance
	 * @param namedGroup
	 * @param anonymousGroup
	 */
	public void setGroups(String nameInstance, String namedGroup,
			String anonymousGroup) {		
		IComponentInstance instance = ModelUtils.lookupReference(editor.getDataModel(),
				nameInstance);
		IPropertySource properties = ModelUtils.getPropertySource(instance.getEObject());
		CompoundCommand cc = new CompoundCommand();
		cc.append(createSetPropertyCommand(properties, COMMAND_PROPERTY_NAMED_GROUP, namedGroup));
		cc.append(createSetPropertyCommand(properties, COMMAND_PROPERTY_ANONYMOUS_GROUP, anonymousGroup));
		executeCompoundCommand(cc, Messages.getString("CommandsPage.commandListSection.actions.setGroups"));				
	}
	
	/**
	 * Launches the dialog for image property editing. Only for groups and commands.
	 * @param selected
	 */
	public void editTitleIcon(Object selected) {
		//If it is a command or command group
		String name = ((CommandElementBase) selected).getName();
		IComponentInstance instance = ModelUtils.lookupReference(editor.getDataModel(),
										name);
		EObject commandObject= instance.getEObject();
		org.eclipse.gef.commands.Command gefCommand = DirectEditingUtilities.editImageProperty(
                   editor.getSite().getShell(),commandObject,ICON_PROPERTY);
        if (gefCommand != null) {                 
        	FormEditorEditingContext editingContext = new FormEditorEditingContext(null, editor.getFormEditor(), treeViewer);
              EditingContextCommand wrapper = new EditingContextCommand(gefCommand, false, editingContext);
              editor.getCommandStack().execute(wrapper);
        }		
	}
	
	/**
	 * This function execute the upgrade of a command to a namedGroup.
	 * Returns the name of the new Named Group. 
	 * @param commandSelect
	 * @return The name of the namedGroup created.
	 */
	public String executePromotion(CommandModel commandSelect){		
		AddNamedGroupCommand commandPromo = executePromotionCommand(commandSelect);		
		FormEditorEditingContext editingContext = new FormEditorEditingContext(null, editor.getFormEditor(), treeViewer);
		EditingContextCommand wrapper = new EditingContextCommand(commandPromo, false, editingContext);
		editor.getCommandStack().execute(wrapper);	
		EObject child = commandPromo.getNewInstance();
		String nameNamed = (String) ModelUtils.getPropertySource(child).getPropertyValue(COMMAND_PROPERTY_NAME);
		return nameNamed;		
	}
	
	/**
	 * This function creates the command to do the upgrade of a command to a namedGroup.
	 * It check that if the commandId can be deleted, deletes the command and
	 * creates the new namedGroup. 
	 * @param commandSelect
	 * @return Command (undo-able) to be executed.
	 */
	public AddNamedGroupCommand executePromotionCommand(CommandModel commandSelect){
		CommandList list = commandSelect.getParent();
		String name = commandSelect.getName();
		String namedGroupCommand = "";//$NON-NLS-1$
		if (commandSelect.getNGParent() != null ){
			namedGroupCommand = commandSelect.getNGParent().getName();
		}
		String anonymousGroupCommand = "";//$NON-NLS-1$
		if (commandSelect.getAGParent() != null ){
			anonymousGroupCommand = commandSelect.getAGParent().getRealName();
		}
		
		IComponentInstance commandInstance = ModelUtils.lookupReference(editor.getDataModel(),
												name);
		List<Command> deleteCommands = new ArrayList<Command>();
		commandCheckCommandId(commandInstance,deleteCommands);
		
		List<EObject> toRemove = new ArrayList<EObject>();
		EObject eObject = ModelUtils.getEObject(commandInstance);
		toRemove.add(eObject);
		Command removeCommand = editor.getDataModel().createRemoveComponentInstancesCommand(toRemove);
		deleteCommands.add(removeCommand);
	
		EObject listObject = ModelUtils.lookupReference(editor.getDataModel(),
				list.getName()).getEObject();
		EObject child = createAddCommandChild(listObject, UI_NAMED_GROUP);		
		Hashtable<String, String> propertyValue = new Hashtable<String, String>();
		propertyValue.put(COMMAND_PROPERTY_NAMED_GROUP, namedGroupCommand);
		propertyValue.put(COMMAND_PROPERTY_ANONYMOUS_GROUP, anonymousGroupCommand);
		AddNamedGroupCommand commandAddNamed = new AddNamedGroupCommand(editor, deleteCommands,
				propertyValue, child, listObject ,"Add named");		
		return commandAddNamed;
	}
	
	/**
	 * Executes the redo/undo Add command for the Real model. 
	 * @param parent - Component parent
	 * @param componentID 
	 * @param label - Label for the command Stack
	 * @return EObject created.
	 */
	private EObject executeAdd(EObject parent, String componentID,
			String label){
		EObject child = null;
		IComponentSet componentSet = editor.getDataModel().getComponentSet();		
		Check.checkContract(componentSet != null);
		IComponent component = componentSet.lookupComponent(componentID);
		
		Check.checkContract(component != null);
		child = editor.getDataModel().createNewComponentInstance(
				component);	
		CompoundCommand cc = new CompoundCommand();
		cc.setLabel(Messages.getString(label)); 
		Command addCommand = editor.getDataModel()
			.createAddNewComponentInstanceCommand(
					parent, child, IDesignerDataModel.AT_END);
		cc.append(addCommand);
		
		FormEditorEditingContext editingContext = new FormEditorEditingContext(null, editor.getFormEditor(), treeViewer);
		EditingContextCommand wrapper = new EditingContextCommand(cc, false, editingContext);
		editor.getCommandStack().execute(wrapper);		
		return child;
	}
	
	/**
	 * Create Command. 
	 * @param parent - Component parent
	 * @param componentID 
	 * @param label - Label for the command Stack
	 * @return EObject created.
	 */
	private EObject createAddCommandChild(EObject parent, String componentID){
		EObject child = null;
		IComponentSet componentSet = editor.getDataModel().getComponentSet();		
		Check.checkContract(componentSet != null);
		IComponent component = componentSet.lookupComponent(componentID);		
		Check.checkContract(component != null);
		child = editor.getDataModel().createNewComponentInstance(
				component);			
		return child;
	}
	
	/**
	 * Executes the redo/undo Delete command for the Real model. 
	 * @param instance - instance to be deleted.
	 * @param label - Label for the command Stack
	 */
	private void executeDelete(IComponentInstance instance,
				String label) {
		List<EObject> toRemove = new ArrayList<EObject>();
		EObject eObject = ModelUtils.getEObject(instance);
		toRemove.add(eObject);
		CompoundCommand cc = new CompoundCommand();
		cc.setLabel(Messages.getString(label)); 
		Command removeCommand = editor.getDataModel().createRemoveComponentInstancesCommand(toRemove);
		cc.append(removeCommand);
		
		FormEditorEditingContext editingContext = new FormEditorEditingContext(null, editor.getFormEditor(), treeViewer);
		EditingContextCommand wrapper = new EditingContextCommand(cc, false, editingContext);
		editor.getCommandStack().execute(wrapper);		
	}
	
	/**
	 * Launches the message for the promotion of the command.
	 * @return - true if the user press yes or no if the user cancel the operation
	 */
	private boolean promoveCommandNamedGroup() {
		MessageBox confirm = new MessageBox(Display.getCurrent().getActiveShell(),
				SWT.ICON_QUESTION | SWT.YES | SWT.NO);
	    String text = Messages.getString(
	    		"CommandsPage.commandListSection.msgs.addCommand"); //$NON-NLS-1$
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
	 * Checks the commandId's event bindings and deletes them along with the command ID.
	 * @param commandInstance - Instance (Real model) of the command ID
	 */
	private void commandCheckCommandId(IComponentInstance commandInstance,List<Command> deleteCommands) {
		IPropertySource propertiesCommand = ModelUtils.getPropertySource(
										commandInstance.getEObject());
		String nameCommandID = (String)propertiesCommand.getPropertyValue(
										COMMAND_PROPERTY_COMMAND_ID);
		CommandId commandIdModel = getCommandIdByName(nameCommandID);
		if (commandIdModel == null){
			return;
		} 
		if ((commandIdModel.getReference() > 1)){ 
			//it has and commandID and this has more references besides this command
			commandIdModel.refreshReference(-1);
			return;
		}
		else{
			IComponentInstance commandIdInstance = ModelUtils.lookupReference(editor.getDataModel(),
											nameCommandID);
			IEventBinding[] bindings = commandIdInstance.getEventBindings();
			if (bindings != null ){//command ID with handler
				//Then we can delete it
				Command commandRemove = editor.getDataModel().
				createRemoveEventBindingCommand(
						commandIdInstance.findEventBinding(COMMAND_ID_EVENT));
				Check.checkState(commandRemove.canExecute());
				deleteCommands.add(commandRemove);
			}
			//Then remove CommandId			
			//deleteCommandId(commandIdInstance);	
			List<EObject> toRemove = new ArrayList<EObject>();
			EObject eObject = ModelUtils.getEObject(commandIdInstance);
			toRemove.add(eObject);
			Command removeCommand = editor.getDataModel().createRemoveComponentInstancesCommand(toRemove);
			deleteCommands.add(removeCommand);
		}
	}
	
	/**
	 * Adds a new CommandList to the CommandListGroup (Real model)
	 * @param commandListConfigurationGroup - Parent
	 */
	public void addCommandList(EObject commandListConfigurationGroup){ 
		EObject child = executeAdd(commandListConfigurationGroup, UI_COMMAND_LIST,
					"CommandsPage.commandListSection.actions.add"); //$NON-NLS-1$
		ModelUtils.getComponentInstance(child).addPropertyListener(commandListPropertyListener);
	}
	
	/**
	 * Adds a new CommandList to the CommandList'list (local model)
	 * @param newInstance - Real model instance.
	 */
	public void addCommandListModel(EObject newInstance) {
		CommandList newList;
		String newName = ModelUtils.getComponentInstance(newInstance).getName();
		newList = new CommandList(newName);
		commandLists.add(newList);
		treeViewer.refresh();		
	}
	
	/**
	 * Adds a new CommandID to the Real model, with the given name.
	 * @param name - name of the new instance.
	 */
	public String addCommandID(String name){
		EObject child = executeAdd(commandListConfigurationGroup, UI_COMMAND_ID,
							"CommandsPage.commandIdSection.actions.add"); //$NON-NLS-1$
		ModelUtils.getComponentInstance(child).addPropertyListener(commandIDPropertyListener);
		IPropertySource properties = ModelUtils.getPropertySource(child);
		String commandIdProperty = (String) properties.getPropertyValue(COMMAND_ID_PROPERTY_NAME);
		if (name != null){
			properties.setPropertyValue(COMMAND_ID_PROPERTY_NAME, name);
			commandIdProperty = name;
			properties.setPropertyValue(COMMAND_ID_PROPERTY_COMMAND_ID, commandIdProperty);
		}		
		return commandIdProperty;
	}
	
	/**
	 * Adds a new CommandID to the local model.
	 * @param name
	 * @param commandEvent
	 */
	public void addComandIdModel(String name, String commandEvent,EObject newInstance) {
		CommandId newCommandId = new CommandId(name);
		newCommandId.setCommandEvent(commandEvent);
		commandIdsList.add(newCommandId);
		IPropertySource properties = ModelUtils.getPropertySource(newInstance);
		if (!properties.getPropertyValue(COMMAND_ID_PROPERTY_SYSTEM_ID).equals("")){
			newCommandId.setCommandId((String) properties.getPropertyValue(
					COMMAND_ID_PROPERTY_SYSTEM_ID));
		}
		else if (!properties.getPropertyValue(COMMAND_ID_PROPERTY_COMMAND_ID).equals("")){
			newCommandId.setCommandId((String) properties.getPropertyValue(
					COMMAND_ID_PROPERTY_COMMAND_ID));
		}
		
	}
	
	/**
	 * This method manages the addition of a new Named group or a new command to the
	 * real model and initializes its properties.
	 * @param element - Element selected in the tree (local model)
	 * @param componentID - Id of the component to be added.
	 * @param label - Label for the command Stack
	 * @param shortTxt - Default shortText
	 * @param listener - PropertyListener
	 * @return name of the new Instance.
	 */
	public String addNamedOrCommand(Object element,String componentID, String label,
								String shortTxt,
								IComponentInstancePropertyListener listener,
								boolean flagCommandID){
		String shortText = shortTxt; //same as name
		String text;
		String name = ""; //$NON-NLS-1$
		String namedGroup = ""; //$NON-NLS-1$
		String anonymousGroup = "";		 //$NON-NLS-1$
		EObject listObject;
		EObject childID = null;
		CommandList listParent = null;
		AddNamedGroupCommand promotionGroupCommand = null;
		boolean flag = true;
		if (element instanceof CommandList){
			listParent = (CommandList) element;
		}
		else if (element instanceof NamedGroup){
			NamedGroup namedGroupIns = (NamedGroup) element;
			listParent = namedGroupIns.getParent();
			namedGroup = namedGroupIns.getName();
		}
		else if (element instanceof AnonymousGroup){
			AnonymousGroup anonymous = (AnonymousGroup) element;
			listParent = anonymous.getParent();	
			anonymousGroup = anonymous.getRealName();			
			if (anonymous.getNGParent() != null){				
				namedGroup = anonymous.getNamedGroupName();
			}
		}		
		else if (element instanceof CommandModel){
			CommandModel commandSelect = (CommandModel) element;
			listParent = commandSelect.getParent();
			flag = promoveCommandNamedGroup();
			if (flag){
				promotionGroupCommand = executePromotionCommand(commandSelect);
			}
		}		
		if (!flag){
			return "";
		}			
		listObject = ModelUtils.lookupReference(editor.getDataModel(),
					listParent.getName()).getEObject();
		
		//Create command to add a new "Command"
		EObject child = createAddCommandChild(listObject, componentID);			
		
		CompoundCommand cc = new CompoundCommand();
	
		if ((componentID.equals(UI_COMMAND)) && (flagCommandID)) {			
			childID = createAddCommandChild(commandListConfigurationGroup, UI_COMMAND_ID);
			AddCompoundInstanceCommand commandAdd = new AddCompoundInstanceCommand(editor, 
					COMMAND_PROPERTY_COMMAND_ID, child,childID,listObject, 
					Messages.getString("CommandsPage.commandListSection.actions.addCommand"));
			if (promotionGroupCommand != null){ //it's an adding command with a promotion
				PromotionCommand promotionCommand = new PromotionCommand(editor,promotionGroupCommand,
						commandAdd, Messages.getString("CommandsPage.commandListSection.actions.addCommand"));
				cc.append(promotionCommand);
			}
			else{
				cc.append(commandAdd); //It's a normal adding command
			}
		}
		else{
			if ((componentID.equals(UI_NAMED_GROUP)) && (promotionGroupCommand != null)){
				Hashtable<String, String> propertyValue = new Hashtable<String, String>();
				propertyValue.put(COMMAND_PROPERTY_NAMED_GROUP, "");
				propertyValue.put(COMMAND_PROPERTY_ANONYMOUS_GROUP, anonymousGroup);
				AddNamedGroupCommand commandAddNamed = new AddNamedGroupCommand(editor,null, 
						propertyValue, child, listObject ,
						Messages.getString("CommandsPage.commandListSection.actions.addNamed"));				
				PromotionCommand promotionCommand = new PromotionCommand(editor,promotionGroupCommand,
						commandAddNamed, 
						Messages.getString("CommandsPage.commandListSection.actions.addNamed"));
				cc.append(promotionCommand);
			}
			else{ //It' a duplicate operation,adding a command without a command id.
				Command addCommand = editor.getDataModel().createAddNewComponentInstanceCommand(
						listObject, child, IDesignerDataModel.AT_END); 
				cc.setLabel(Messages.getString(label));
				cc.append(addCommand);
			}
		}
		FormEditorEditingContext editingContext = new FormEditorEditingContext(null, editor.getFormEditor(), treeViewer);
		EditingContextCommand wrapper = new EditingContextCommand(cc, false, editingContext);
		editor.getCommandStack().execute(wrapper);
						
		ModelUtils.getComponentInstance(child).addPropertyListener(listener);
		IPropertySource commandProperties = WizardUtils.getProperties(child);			
		name = (String) commandProperties.getPropertyValue(COMMAND_PROPERTY_NAME);
		shortText += name;
		text = shortText;
				
		commandProperties.setPropertyValue(COMMAND_PROPERTY_TYPE, "Screen"); //$NON-NLS-1$
		commandProperties.setPropertyValue(COMMAND_PROPERTY_TEXT, text);
		commandProperties.setPropertyValue(COMMAND_PROPERTY_SHORTTEXT, shortText);				
		
		if ((!namedGroup.equals("") || (!anonymousGroup.equals("")))) {			
			if (promotionGroupCommand == null){
				commandProperties.setPropertyValue(COMMAND_PROPERTY_NAMED_GROUP, namedGroup);
			}
			commandProperties.setPropertyValue(COMMAND_PROPERTY_ANONYMOUS_GROUP, anonymousGroup);
		}
		
		return name;
	}
	
	/**
	 * Adds command to the Model. It receives the current tree Item selected (local model).
	 * @param element - Selected tree item from the local model.
	 */	
	public String addCommand(Object element){
		String newName = addNamedOrCommand(element, UI_COMMAND, "CommandsPage.commandListSection.actions.addCommand", //$NON-NLS-1$
					"New", commandPropertyListener, true);	 //$NON-NLS-1$
		return newName;
	}
	
	/**
	 * Adds a new Command to the local model.
	 * @param newInstance - Real model instance.
	 */
	public void addCommandModel(EObject newInstance){
		String newName = ModelUtils.getComponentInstance(newInstance).getName();
		EObject parent = ModelUtils.getComponentInstance(newInstance).getParent();
		String nameList = ModelUtils.getComponentInstance(parent).getName();
		CommandList listParent = getCommandListByName(nameList);
		CommandModel newCommand = new CommandModel(newName,listParent);
		listParent.addCommand(newCommand);
		IPropertySource properties = ModelUtils.getPropertySource(newInstance);
		if (!properties.getPropertyValue(COMMAND_PROPERTY_ANONYMOUS_GROUP).equals("")){
			refreshPropertiesCommand(newInstance,COMMAND_PROPERTY_ANONYMOUS_GROUP);					
		}
		if (!properties.getPropertyValue(COMMAND_PROPERTY_NAMED_GROUP).equals("")){
			refreshPropertiesCommand(newInstance,COMMAND_PROPERTY_NAMED_GROUP);					
		}
		if (!properties.getPropertyValue(COMMAND_PROPERTY_COMMAND_ID).equals("")){
			refreshPropertiesCommand(newInstance,COMMAND_PROPERTY_COMMAND_ID);					
		}
		treeViewer.refresh();		
	}
	
	/**
	 * This method move the command to the new command list parent in the real model
	 * @param instance - Command to be move
	 * @param target - New command List / Named Group / Anonymous
	 */
	public void moveCommand(IComponentInstance instance, Object target,
			Hashtable<String, String> oldNewName){
		IPropertySource properties = ModelUtils.getPropertySource(
										instance.getEObject());
		String nameInstance = instance.getName();
		CommandList listTarget = null;
		CommandList listDrop = null;
		if (target instanceof CommandList){
			listTarget = (CommandList)target;
		}
		else{
			listTarget = ((CommandElementBase)target).getParent();
		}
		listDrop = getCommandListByName(ModelUtils.getComponentInstance(instance.getParent()).getName());
		IPropertyDescriptor[] descriptors = properties.getPropertyDescriptors();
		Hashtable<String, Object> propertiesValues = new Hashtable<String, Object>();
		if (oldNewName == null){
			oldNewName = new Hashtable<String, String>();
		}
		for (IPropertyDescriptor descriptor : descriptors){			
			propertiesValues.put((String)descriptor.getId(), 
							properties.getPropertyValue(descriptor.getId()));			
		}
		//When the operation is copy and the target list is different then
		//the command ID reference is deleted.
		if (isCopy && (listDrop.equals(listTarget))){
			propertiesValues.put(COMMAND_PROPERTY_COMMAND_ID,""); //$NON-NLS-1$
		}
		if (isCopy || (target instanceof CommandList)){
			propertiesValues.put(COMMAND_PROPERTY_ANONYMOUS_GROUP, ""); //$NON-NLS-1$
			propertiesValues.put(COMMAND_PROPERTY_NAMED_GROUP, ""); //$NON-NLS-1$
		}               
		if (target instanceof AnonymousGroup){
				AnonymousGroup anonymousTarget = ((AnonymousGroup) target);
	    	if (anonymousTarget.getType() == 1){
	    		propertiesValues.put(COMMAND_PROPERTY_NAMED_GROUP,
	    				anonymousTarget.getNamedGroupName());	    		  		
	    	}
	    	propertiesValues.put(COMMAND_PROPERTY_ANONYMOUS_GROUP, 
    				anonymousTarget.getRealName());	  
		}
		else if (target instanceof NamedGroup){
			propertiesValues.remove(COMMAND_PROPERTY_NAMED_GROUP);
			propertiesValues.put(COMMAND_PROPERTY_NAMED_GROUP,
						((CommandElementBase) target).getName());
		}		
		if (!isCopy){ //move
			deleteCommand(instance);
		}
		String finalName = copyElement(UI_COMMAND, propertiesValues, target);
		oldNewName.put(nameInstance, finalName);
		setAllPropertiesElement(finalName,propertiesValues, oldNewName);
	}
	
	/**
	 * This method move the NamedGroup(and all its children) to the new target 
	 * parent in the real model.
	 * @param instance - Command to be move
	 * @param target - New command List / Named Group / Anonymous
	 */
	public void moveNamedGroup(NamedGroup groupToMove, Object target,
			Hashtable<String, String> oldNewName){
		Hashtable<String, Hashtable<String, Object>> objectProperties = new Hashtable<String, 
																	Hashtable<String,Object>>();
		if (oldNewName == null){
			oldNewName = new Hashtable<String, String>();
		}		
		List<Object> all = new ArrayList<Object>();
		List<String> nameAnonymous = new ArrayList<String>();
		List<String> nameNamed = new ArrayList<String>();
		List<String> nameCommands = new ArrayList<String>();
		List<Command> deleteCommands = new ArrayList<Command>();
		Hashtable<String, String> nameComponentID = new Hashtable<String, String>();
		String namedGroup = ""; //$NON-NLS-1$
		String anonymousGroup = ""; //$NON-NLS-1$
		CommandList listParent;
 		getChildrenElements(groupToMove, all);
 		Object [] children = all.toArray();
 		if (target instanceof CommandList){
 			listParent = (CommandList) target;
 		}
 		else{
 			listParent = ((CommandElementBase) target).getParent();	
 		}
 		String nameListParent = listParent.getName();
 		for(int i = 0; i < children.length ; i++){
			Hashtable<String, Object> propertiesValues = new Hashtable<String, Object>();
			String name = ""; //$NON-NLS-1$
			if (children[i] instanceof AnonymousGroup){
				name = ((AnonymousGroup)children[i]).getRealName();
				nameAnonymous.add(name);
			}
			else if (children[i] instanceof NamedGroup){
				name = ((CommandElementBase) children[i]).getName();
				nameNamed.add(name);
			}
			else if (children[i] instanceof CommandModel){
				name = ((CommandElementBase) children[i]).getName();
				nameCommands.add(name);
			}
			IComponentInstance instance = ModelUtils.lookupReference(editor.getDataModel(),
								name);
			IPropertySource properties = ModelUtils.getPropertySource(instance.getEObject());
			IPropertyDescriptor[] descriptors = properties.getPropertyDescriptors();
			for (IPropertyDescriptor descriptor : descriptors){
				propertiesValues.put((String)descriptor.getId(), 
								 properties.getPropertyValue(descriptor.getId()));			
			}
			objectProperties.put(name,propertiesValues);
			String componentID = instance.getComponentId();
			nameComponentID.put(name, componentID);
			if (!isCopy){
				//selectDelete(children[i]);//this is the delete!!!
				deleteCommands.add(getDeleteCommand(children[i]));				
			}			
		}
 		//Append all the delete commands to an compoundCommand
 		CompoundCommand cc = new CompoundCommand();
 		for(Command c : deleteCommands){
 			cc.append(c);
 		}
 		//Execute only one command to delete all the elements
 		executeCompoundCommand(cc,Messages.getString("CommandsPage.commandListSection.actions.moveNamed.delete"));	
 		Object[] allElements = objectProperties.keySet().toArray();
		for (int i = 0; i < allElements.length; i++){
			String name = (String) allElements[i];
			String finalName = copyElement(nameComponentID.get(name),objectProperties.get(name), listParent);
			oldNewName.put(name, finalName);
 		}
		
 		nameAnonymous.addAll(nameNamed);
 		nameAnonymous.addAll(nameCommands);
		if (!isCopy){
			//It has to be in this order because, if we change it, the group's references don't exits
			//yet until we change the name property.
			CompoundCommand ccProperties = new CompoundCommand();
			for (String nameElement : nameAnonymous ) {
		       	getAllPropertiesElementCommand(ccProperties, nameElement,objectProperties.get(nameElement),
	 			        	 		oldNewName);
		    }
	 		
			if (target instanceof NamedGroup){
				namedGroup = ((CommandElementBase)target).getName();
			}
			else if (target instanceof AnonymousGroup){
				AnonymousGroup groupTarget = ((AnonymousGroup)target);
				anonymousGroup = groupTarget.getRealName();	 
				if (groupTarget.getType() == 1){
					anonymousGroup = groupTarget.getRealName();
					namedGroup = groupTarget.getNamedGroupName();
				}
			}
			//sets the groups for the groupToDrop
			//setGroups(oldNewName.get(groupToMove.getName()), namedGroup, anonymousGroup);
			ccProperties.append(createSetPropertyElement(groupToMove.getName(),COMMAND_PROPERTY_NAMED_GROUP, namedGroup));
			ccProperties.append(createSetPropertyElement(groupToMove.getName(),COMMAND_PROPERTY_ANONYMOUS_GROUP,anonymousGroup));			
			
	 		executeCompoundCommand(ccProperties, Messages.getString("CommandsPage.commandListSection.actions.moveNamed.setProperties"));
	 	}
		else{ //Is copy, we need to change the value of NG and AG
			nameAnonymous.remove(groupToMove.getName());			
			CompoundCommand ccPropertiesCopy = new CompoundCommand();
			for (String nameElement : nameAnonymous ) {
				IComponentInstance instance = ModelUtils.lookupReference(editor.getDataModel(),
						(String) nameElement);
				String newName = oldNewName.get(nameElement);
				if (!instance.getComponentId().equals(UI_ANONYMOUS_GROUP)){
					//only for command and named group					
					String ng = (String) objectProperties.get(nameElement).
										get(COMMAND_PROPERTY_NAMED_GROUP);
					String newNG = ""; //$NON-NLS-1$
					if (ng != null && (!ng.equals(""))){//it does have a NG //$NON-NLS-1$
						newNG = oldNewName.get(ng);
						objectProperties.get(nameElement).
									put(COMMAND_PROPERTY_NAMED_GROUP,newNG);
					}
					String ag = (String) objectProperties.get(nameElement).
					get(COMMAND_PROPERTY_ANONYMOUS_GROUP);
					String newAG = ""; //$NON-NLS-1$
					if (ag != null && (!ag.equals(""))){//it does have a NG //$NON-NLS-1$
						newAG = oldNewName.get(ag);
						objectProperties.get(nameElement).
							put(COMMAND_PROPERTY_ANONYMOUS_GROUP,newAG);
					}					
				}
				getAllPropertiesElementCommand(ccPropertiesCopy, newName,objectProperties.get(nameElement),
		        	 		oldNewName);
		    }
			
			//Change command ID property for all the commands when is a Copy and the
			//command list parent and the command list target are the same.			
			if (groupToMove.getParent().getName().equals(nameListParent)){
				for (String commandName : nameCommands){
					String newCommandName = oldNewName.get(commandName);
					//setPropertyElement(newCommandName, COMMAND_PROPERTY_COMMAND_ID, "");
					ccPropertiesCopy.append(createSetPropertyElement(newCommandName, COMMAND_PROPERTY_COMMAND_ID, ""));
				}
				
			}
			executeCompoundCommand(ccPropertiesCopy, Messages.getString("CommandsPage.commandListSection.actions.moveNamed.setProperties"));
			if (target instanceof NamedGroup){
				namedGroup = ((CommandElementBase)target).getName();
			}
			else if (target instanceof AnonymousGroup){
				AnonymousGroup groupTarget = ((AnonymousGroup)target);
				anonymousGroup = groupTarget.getRealName();
				if (groupTarget.getType() == 1){
					anonymousGroup = groupTarget.getRealName();
					namedGroup = groupTarget.getNamedGroupName();
				}
			} 
			else if (target instanceof CommandList){
				namedGroup = "";
				anonymousGroup = "";
			}
			//sets the groups for the groupToDrop
			if((!isCopy) || (!namedGroup.equals("")) || (!anonymousGroup.equals(""))){
				setGroups(oldNewName.get(groupToMove.getName()), namedGroup, anonymousGroup);
			}
		}
		listParent = getCommandListByName(nameListParent);
		NamedGroup finalGroup = (NamedGroup) getGroupByName(listParent, 
												oldNewName.get(groupToMove.getName()), 0);
		treeViewer.setSelection(new StructuredSelection(finalGroup), true);
	}
	
	/**
	 * Creates the new instance and deletes the old one (when is a move operation)
	 * and sets the correct name for the instance.
	 * @param componentID
	 * @param properties
	 * @param parent
	 * @return name of the new instance
	 */
	private String copyElement(String componentID, Hashtable<String, Object> properties,
			Object parent) {
		IComponentInstance instance = null;
		String name = ""; //$NON-NLS-1$
		IPropertySource newProperties;
		if (componentID.equals(UI_COMMAND)){
			name = addNamedOrCommand(parent, UI_COMMAND, "CommandsPage.commandListSection.actions.addCommand", //$NON-NLS-1$
					"New", commandPropertyListener, false);			 //$NON-NLS-1$
		}
		else if(componentID.equals(UI_NAMED_GROUP)){
			name = addNamedGroup(parent);			
		}
		else if (componentID.equals(UI_ANONYMOUS_GROUP)){			
			name = addAnonymousGroup(parent);			
		}
		instance = ModelUtils.lookupReference(editor.getDataModel(),
				name);
		if (!isCopy){ //move
			name = (String)properties.get(COMMAND_PROPERTY_NAME);
			newProperties = ModelUtils.getPropertySource(instance.getEObject());
			newProperties.setPropertyValue(COMMAND_PROPERTY_NAME, name);
		}
		return name;			
	}

	/**
	 * Recursive function to get all the children of a selected node and
	 * the selected node also.
	 * @param item
	 */
	public void getChildrenElements(Object parent, List<Object> all){			
		CommandElementTreeContentProvider provider = 
			(CommandElementTreeContentProvider)	treeViewer.getContentProvider();
		Object[] children = provider.getChildren(parent);
		if (children != null){
			for (int i = 0; i < children.length; i++){
				getChildrenElements(children[i], all);
			}			
		}
		all.add(parent);		
	}
		
	/**
	 * Adds a new Named Group to the real model.
	 * @param element - Selected tree item (local model)
	 * @return name of the new named group.
	 */
	public String addNamedGroup(Object element) {
		return addNamedOrCommand(element, UI_NAMED_GROUP, "CommandsPage.commandListSection.actions.addNamed", //$NON-NLS-1$
				"New", namedGroupPropertyListener, false); //$NON-NLS-1$
	}
	
	/**
	 * Adds a new named group to the local model.
	 * @param parent - Parent (Real model)
	 * @param instance - New Instance (Real model)
	 */
	public void  addNamedGroupModel(EObject parent,IComponentInstance instance){
		String nameNamed = instance.getName();
		NamedGroup newNamedGroup = new NamedGroup(nameNamed);
		String nameList = ModelUtils.getComponentInstance(parent).getName();
		CommandList list =  getCommandListByName(nameList);
		list.addNamedGroup(newNamedGroup);
		newNamedGroup.setParent(list);		
		IPropertySource properties = ModelUtils.getPropertySource(
								ModelUtils.getEObject(instance));
		if (!properties.getPropertyValue(COMMAND_PROPERTY_ANONYMOUS_GROUP).equals("")){
			refreshPropertiesNamedGroup(ModelUtils.getEObject(instance),COMMAND_PROPERTY_ANONYMOUS_GROUP);					
		}
		if (!properties.getPropertyValue(COMMAND_PROPERTY_NAMED_GROUP).equals("")){
			refreshPropertiesNamedGroup(ModelUtils.getEObject(instance),COMMAND_PROPERTY_NAMED_GROUP);					
		}
		treeViewer.refresh();		
	}
	
	/**
	 * Adds a new Anonymous group to the real model.  
	 * @param element - Selected tree item (to obtain the CL parent) (local model)
	 */
	public String  addAnonymousGroup(Object element){
		CommandList listParent = null;
		EObject listObject;
		if (element instanceof CommandList){
			listParent = (CommandList) element;
		}
		else{
			listParent = ((CommandElementBase) element).getParent();
		}
		listObject = ModelUtils.lookupReference(editor.getDataModel(),
				listParent.getName()).getEObject();
		EObject child = executeAdd(listObject, UI_ANONYMOUS_GROUP,
				"CommandsPage.commandListSection.actions.addAnonymous"); //$NON-NLS-1$
		ModelUtils.getComponentInstance(child).addPropertyListener(anonymousGroupPropertyListener);
		return ModelUtils.getComponentInstance(child).getName();
	}

	/**
	 * Adds a new Anonymous group to the model.
	 * @param parent - Parent (Real model)
	 * @param instance - New instance (Real model)
	 */
	public void addAnonymousGroupModel(EObject parent,IComponentInstance instance){
		String nameGroup = instance.getName();
		AnonymousGroup newGroup = new AnonymousGroup(nameGroup, 0);
		String nameList = ModelUtils.getComponentInstance(parent).getName();
		CommandList list =  getCommandListByName(nameList);
		list.addAnonymousGroup(newGroup);
		newGroup.setParent(list);
		treeViewer.refresh();		
	}
	
	/**
	 * Deletes a command instance from the real model.
	 * @param commandInstance
	 */
	public void deleteCommand(IComponentInstance commandInstance) {
		executeDelete(commandInstance,"CommandsPage.commandListSection.actions.removeCommand");			 //$NON-NLS-1$
	}

	/**
	 * Deletes a command instance from the local model.
	 * @param parent - Parent (command list) from the real model.
	 * @param instance - Instance to be deleted (Real model)
	 */
	public void deleteCommandModel(EObject parent, IComponentInstance instance) {
		CommandModel commandModel = null;
		String nameCommand = instance.getName();
		IComponentInstance listInstance = ModelUtils.getComponentInstance(parent);
		String nameCommandList = listInstance.getName();
		CommandList list = getCommandListByName(nameCommandList);
		commandModel = getCommandByNameList(list, nameCommand);		
		list.removeCommand(commandModel);
		if (commandModel.getNGParent() != null){
			commandModel.getNGParent().removeChildren();
		}
		if (commandModel.getAGParent() != null){
			if (commandModel.getNGParent() != null){
				commandModel.getAGParent().removeChildren();
				if (commandModel.getAGParent().getChildren() == 0){
					list.removeAnonymousGroup(commandModel.getAGParent());
				}
			}
			commandModel.getAGParent().removeChildren();
			commandModel.setAGParent(null);
		}
		treeViewer.refresh();
	}
	
	/**
	 * Deletes a commandID instance from the real model.
	 * @param commandIdInstance - Instance to be deleted (real model).
	 */
	public void deleteCommandId(IComponentInstance commandIdInstance) {
		executeDelete(commandIdInstance,"CommandsPage.commandIdSection.actions.remove"); //$NON-NLS-1$
	}
	
	/**
	 * Deletes a commadID instance from the local model.
	 * @param instance - Instance to be deleted (real model)
	 */
	public void deleteCommandIdModel(IComponentInstance instance) {
		String name = instance.getName();
		CommandId commandId = getCommandIdByName(name);
		commandIdsList.remove(commandId);
		treeViewer.refresh();
	}

	/**
	 * Recursive function to delete all the children of a selected node and
	 * the selected node also.
	 * @param item
	 */
	
	public void deleteElements(Object select){
		List<Object> all = new ArrayList<Object>();
		getChildrenElements(select, all);
		Object [] children = all.toArray();
		for (int i = 0; i < children.length; i++){
			selectDelete(children[i]);
		}		
	}
	
	/**
	 * Returns the command corresponding to the delete function  
	 * @param element - local model
	 */
	public Command getDeleteCommand(Object element) {
		String name = ""; //$NON-NLS-1$
		if (element instanceof AnonymousGroup){
			name = ((AnonymousGroup)element).getRealName();
		}
		else if (element instanceof CommandList){
			name = ((CommandList)element).getName();
		}
		else {
			name = ((CommandElementBase)element).getName();
		}
		IComponentInstance instance = ModelUtils.lookupReference(editor.getDataModel(),
				name);
		List<EObject> toRemove = new ArrayList<EObject>();
		EObject eObject = ModelUtils.getEObject(instance);
		
		toRemove.add(eObject);
		Command removeCommand = editor.getDataModel().createRemoveComponentInstancesCommand(toRemove);
		return removeCommand;						
	}
	
	/**
	 * Calls the corresponding delete function (real model) for the element 
	 * @param element - local model
	 */
	public void selectDelete(Object element) {
		String name = ""; //$NON-NLS-1$
		if (element instanceof AnonymousGroup){
			name = ((AnonymousGroup)element).getRealName();
		}
		else if (element instanceof CommandList){
			name = ((CommandList)element).getName();
		}
		else {
			name = ((CommandElementBase)element).getName();
		}
		IComponentInstance instance = ModelUtils.lookupReference(editor.getDataModel(),
				name);
		if (element instanceof CommandModel){
			deleteCommand(instance);
			return;
		}
		else if ((element instanceof NamedGroup) || (element instanceof AnonymousGroup)){
			deleteGroup(instance);
			return;
		} 
		else if (element instanceof CommandList){
			deleteCommandList(instance);
			return;
		}				
	}

	/**
	 * Deletes a group from the real model.
	 * @param instance - Instance to be deleted (Real model)
	 */
	public void deleteGroup(IComponentInstance instance) {
		if (instance.getComponentId().equals(UI_NAMED_GROUP)){
			executeDelete(instance,
				"CommandsPage.commandListSection.actions.removeNamedGroup"); //$NON-NLS-1$
		}
		else{
			executeDelete(instance,
			"CommandsPage.commandListSection.actions.removeAnonymousGroup"); //$NON-NLS-1$
		}
	}
	
	/**
	 * Deletes a group from the local model.
	 * @param parentList - Parent (command list) from the real model.
	 * @param instance - Instance to be deleted (Real model)
	 */
	public void deleteGroupModel(EObject parentList, IComponentInstance instance){
		IComponentInstance listInstance = ModelUtils.getComponentInstance(parentList);
		String nameList = listInstance.getName();
		CommandList list = getCommandListByName(nameList);
		String name = instance.getName(); //be careful with the name of the anonymous group
		NamedGroup named = null;
		AnonymousGroup anonymous = null;
		if (instance.getComponentId().equals(UI_NAMED_GROUP)){
			named = (NamedGroup) getGroupByName(list, name, 0);
			List<CommandModel> commands = list.getCommandsForNamed(named);			
			for (CommandModel command : commands){
				command.setNGParent(null);
				named.removeChildren();
				list.removeCommand(command);
			}
			list.removeNamedGroupCommand(list.getNamedGroups().indexOf(named));
		}
		else{ //name is the real name, can be more than 1 with the same real name
			 //Can be possible that the tree is not visible and then this will fail.
			List<AnonymousGroup> groups = list.getAnonymousGroups();
			List<AnonymousGroup> toDelete = new ArrayList<AnonymousGroup>();
			for (AnonymousGroup group : groups){
				if (group.getRealName().equals(name)){
					toDelete.add(group);
				}
			}
			for (AnonymousGroup group : toDelete) {
				List<CommandModel> commands = list
						.getCommandsForAnonymous(group);
				for (CommandModel command : commands) {
					command.setAGParent(null);
					anonymous.removeChildren();
					list.removeCommand(command);
				}
				list.removeAnonymousGroup(group);
			}
		}
		treeViewer.refresh();
	}
	
	/**
	 * Deletes a command list from the real model.
	 * @param instance - Instance to be deleted (real model)
	 */
	public void deleteCommandList(IComponentInstance instance){
		executeDelete(instance,
				"CommandsPage.commandListSection.actions.removeCommandList"); //$NON-NLS-1$
	}
	
	/**
	 * Deletes a command list from the local model.
	 * @param parent
	 * @param instance
	 */
	public void deleteCommandListModel(EObject parent, IComponentInstance instance){
		String name = instance.getName();
		CommandList list = getCommandListByName(name);
		commandLists.remove(list);
		treeViewer.setInput(getCommandListElements());
	}
	
	/**
	 * Refreshes the properties of the command id in the local model.
	 * @param componentObject
	 * @param propertyId
	 */
	public void refreshPropertyCommandId(EObject componentObject,
			Object propertyId) {
		IComponentInstance commandInstance = ModelUtils.getComponentInstance(componentObject);
		String nameInstance = commandInstance.getName();
		if (((String) propertyId).equals(COMMAND_ID_PROPERTY_NAME)){
			Vector<String> namesComponent = createListNamesRealModel(UI_COMMAND_ID);
			String found = findOldNameComponent(UI_COMMAND_ID, namesComponent);
			if (!found.equals("")) {				
				CommandId oldCommandId = getCommandIdByName(found);
				oldCommandId.setName(nameInstance);
				refreshCommandsReferences(found, nameInstance);
				treeViewer.refresh();
			}
			return;
		}		
	}

	/**
	 * Refreshes all the references made by commands in the local model
	 * @param oldName
	 * @param newName
	 */
	private void refreshCommandsReferences(String oldName, String newName) {
		List<CommandList> allList = getCommandListElements();
		List<CommandModel> allCommands = new ArrayList<CommandModel>();
		for (CommandList list : allList){
			allCommands.addAll(list.getCommands()); 
		}
		for (CommandModel command : allCommands){
			if (command.getCommandID().equals(oldName)){
				command.setCommandID(newName);
			}
		}
	}

	/**
	 * Refreshes the properties of the command in the local model.
	 * @param componentObject
	 * @param propertyId
	 */
	public void refreshPropertiesCommand(EObject componentObject,
			Object propertyId) {
		IComponentInstance commandInstance = ModelUtils.getComponentInstance(componentObject);
		String nameInstance = commandInstance.getName();
		String nameParent = ModelUtils.getComponentInstance(
							commandInstance.getParent()).getName();
		CommandList list = getCommandListByName(nameParent);
		
		CommandModel command = getCommandByNameList(list, nameInstance);
		IPropertySource properties = ModelUtils.getPropertySource(componentObject);
		String strProperty = (String) propertyId;
		if (strProperty.equals(COMMAND_PROPERTY_NAME)){			
			Vector<String> namesComponent = createListNamesRealModel(UI_COMMAND);
			String found = findOldNameComponent(UI_COMMAND, namesComponent);
			if (!found.equals("")) {				
				CommandModel oldCommand = getCommandByNameList(list, found);
				oldCommand.setName(nameInstance);
				treeViewer.refresh();
			}
			return;
		}
		else if (strProperty.equals(COMMAND_PROPERTY_NAMED_GROUP)){
			refreshNamedGroupProperty(command, properties, list);
			return;
		}
		else if (strProperty.equals(COMMAND_PROPERTY_ANONYMOUS_GROUP)){
			refreshAnonymousGroupProperty(command, properties, list);
			return;
		}
		else if (strProperty.equals(COMMAND_PROPERTY_COMMAND_ID)){
			String nameProperty = (String)properties.getPropertyValue(COMMAND_PROPERTY_COMMAND_ID);
			String idName = command.getCommandID();
			CommandId commandId = null;
			if (!idName.equals("")){ //$NON-NLS-1$
				if (!idName.equals(nameProperty)){//change namedGroup
					commandId = getCommandIdByName(idName);
					commandId.refreshReference(-1);										
				}
				else{
					return;
				}
			}
			command.setCommandID(nameProperty);
			if (!nameProperty.equals("")) { //$NON-NLS-1$
				commandId = getCommandIdByName(nameProperty);
				if (commandId != null)
					commandId.refreshReference(1);
			}
			return;
		}
		
	}
	
	/**
	 * Refreshes the properties of the command in the local model.
	 * @param componentObject
	 * @param propertyId
	 */
	public void refreshPropertiesNamedGroup(EObject componentObject,
			Object propertyId) {
		IComponentInstance commandInstance = ModelUtils.getComponentInstance(componentObject);
		String nameInstance = commandInstance.getName();
		String nameParent = ModelUtils.getComponentInstance(
							commandInstance.getParent()).getName();
		CommandList list = getCommandListByName(nameParent);
		
		NamedGroup groupChanged = (NamedGroup) getGroupByName(list, nameInstance, 0);
		IPropertySource properties = ModelUtils.getPropertySource(componentObject);
		String strProperty = (String) propertyId;
		if (strProperty.equals(COMMAND_PROPERTY_NAME)){			
			Vector<String> namesComponent = createListNamesRealModel(UI_NAMED_GROUP);
			String found = findOldNameComponent(UI_NAMED_GROUP, namesComponent);
			if (!found.equals("")) {
				NamedGroup oldGroup = (NamedGroup) getGroupByName(list, found,
						0);
				oldGroup.setName(nameInstance);
				refreshNameAnonymousGroupReferences(list, oldGroup);
				treeViewer.refresh();
			}
			return;
		}
		else if (strProperty.equals(COMMAND_PROPERTY_NAMED_GROUP)){
			refreshNamedGroupProperty(groupChanged, properties, list);
			treeViewer.refresh();
			return;
		}
		else if (strProperty.equals(COMMAND_PROPERTY_ANONYMOUS_GROUP)){
			refreshAnonymousGroupProperty(groupChanged, properties, list);
			return;
		}		
	}
	
	/**
	 * Refreshes the name of the anonymous group type 1, which has this Named group as parent
	 * @param list
	 * @param oldGroup
	 */
	private void refreshNameAnonymousGroupReferences(CommandList list, NamedGroup oldGroup) {		
		List<AnonymousGroup> anonymousReferences = new ArrayList<AnonymousGroup>();
		anonymousReferences = list.getAnonymousGroupsParent(oldGroup);
		for(AnonymousGroup group : anonymousReferences){
			String newName = oldGroup.getName();
			group.setNamedGroupName(newName);
			group.setName(group.getRealName()+newName);
		}
	}

	/**
	 * Refreshes the Command list properties in the local model
	 */
	public void refreshPropertyCommandList(EObject componentObject) {
		IComponentInstance commandInstance = ModelUtils.getComponentInstance(componentObject);
		String nameInstance = commandInstance.getName();
		Vector<String> namesComponent = createListNamesRealModel(UI_COMMAND_LIST);
		String found = findOldNameComponent(UI_COMMAND_LIST, namesComponent);
		if (!found.equals("")) {
			CommandList oldList = (CommandList) getCommandListByName(found);
			if (oldList != null){
				oldList.setName(nameInstance);				
			}
			treeViewer.refresh();
		}
		return;		
	}
	
	/**
	 * Reset the local model.
	 */
	public void resetModel() {
		for (CommandList list: commandLists){
			List<CommandModel> commands = list.getCommands();
			commands.clear();
			List<NamedGroup> groupsNamed = list.getNamedGroups();
			groupsNamed.clear();
			List<AnonymousGroup> groupsAnonymous = list.getAnonymousGroups();
			groupsAnonymous.clear();
		}
		commandLists.clear();
		commandIdsList.clear();
		commandLists = new ArrayList<CommandList>();
		initData(this.duplicate);		
		treeViewer.setInput(getCommandListElements());		
	}
	
	/**
	 * Sets the given property of the instance to the newValue.
	 * @param nameInstance
	 * @param property
	 * @param newValue
	 */
	public void setPropertyElement(String nameInstance,
					Object property, 
					Object newValue){
		IComponentInstance instance = ModelUtils.lookupReference(editor.getDataModel(),
				nameInstance);
		IPropertySource properties = ModelUtils.getPropertySource(instance.getEObject());
		createAndExecuteSetPropertyCommand(editor.getFormEditor(), properties, 
				(String)property, newValue);
	}

	/**
	 * Create the command to change the given property of the instance to the newValue.
	 * @param nameInstance
	 * @param property
	 * @param newValue
	 */
	public SetPropertyCommand createSetPropertyElement(String nameInstance,
					Object property, 
					Object newValue){
		IComponentInstance instance = ModelUtils.lookupReference(editor.getDataModel(),
				nameInstance);
		IPropertySource properties = ModelUtils.getPropertySource(instance.getEObject());
		return createSetPropertyCommand(properties, (String)property, newValue);
	}
	
	/**
	 * Refreshes the Anonymous group properties in the local model. The only property that
	 * can be modified is the name.
	 */
	public void refreshPropertyAnonymousGroup(EObject componentObject,
			Object propertyId) {
		IComponentInstance commandInstance = ModelUtils.getComponentInstance(componentObject);
		String nameInstance = commandInstance.getName();
		String nameParent = ModelUtils.getComponentInstance(
							commandInstance.getParent()).getName();
		CommandList list = getCommandListByName(nameParent);
		Vector<String> namesComponent = createListNamesRealModel(UI_ANONYMOUS_GROUP);
		String found = findOldNameComponent(UI_ANONYMOUS_GROUP, namesComponent);
		if (!found.equals("")) {
			AnonymousGroup oldGroup = (AnonymousGroup) getGroupByName(list, found,
					1);
			if (oldGroup != null){
				oldGroup.setRealName(nameInstance);
				oldGroup.setName(nameInstance);
				refreshNameAnonymousGroupCompound(list, found, nameInstance);
			}
			treeViewer.refresh();
		}
		return;		
	}
	
	/**
	 * Refreshes the compound name of the anonymous group type 1 for this anonymous group
	 * @param list
	 * @param oldGroup
	 */
	private void refreshNameAnonymousGroupCompound(CommandList list,
										String oldName, String newName) {
		List<AnonymousGroup> groups = new ArrayList<AnonymousGroup>();
		groups = list.getAnonymousGroupWithRealName(oldName);
		for(AnonymousGroup group : groups){
			group.setRealName(newName);
			group.setName(newName+group.getNamedGroupName());
		}
	}

	/**
	 * Refreshes the named group property of the given element (Command, AG or NG) in the 
	 * Real model
	 * @param changed - Object of the local model
	 * @param properties 
	 * @param listParent - Command List parent of object
	 */
	private void refreshNamedGroupProperty(CommandElementBase changed, IPropertySource properties, 
				CommandList listParent){
		String nameNewNG = (String)properties.getPropertyValue(COMMAND_PROPERTY_NAMED_GROUP);
		AnonymousGroup anonymousGroupParent = null;
		NamedGroup namedGroupParent = null;
		anonymousGroupParent = changed.getAGParent();
		namedGroupParent = changed.getNGParent();
		NamedGroup group = null;
		if (namedGroupParent != null){ //if it already have a NamedGroup
			if (!namedGroupParent.getName().equals(nameNewNG)){//change namedGroup
				namedGroupParent.removeChildren();
				if (anonymousGroupParent != null){
					if (anonymousGroupParent.getChildren() > 0){
						anonymousGroupParent.removeChildren();
					}
					if (anonymousGroupParent.getChildren() == 0){
						listParent.removeAnonymousGroup(anonymousGroupParent);
					}
					if ((nameNewNG.equals("")) && (anonymousGroupParent.getType() == 1)){ //$NON-NLS-1$
						AnonymousGroup anonymousAlone = (AnonymousGroup) getGroupByName(listParent,
												anonymousGroupParent.getRealName(), 1);
						changed.setAGParent(anonymousAlone);
					}
				}
			}
			else{
				return;
			}
		}
		if (!nameNewNG.equals("")){ //$NON-NLS-1$
				group = (NamedGroup) getGroupByName(listParent, nameNewNG, 0); //New NamedGroup
				if (anonymousGroupParent != null){
					String nameCombined = anonymousGroupParent.getRealName().
											concat(nameNewNG);
					AnonymousGroup groupAG = (AnonymousGroup) getGroupByName(listParent, nameCombined, 1);
					if (groupAG != null){
						groupAG.addChildren();
					}
					else{
						if (anonymousGroupParent.getType() == 0){
							anonymousGroupParent.removeChildren();//The old one was type 0
						}
						groupAG = new AnonymousGroup (nameCombined, 1);
						groupAG.setParent(listParent);
						groupAG.setNGParent(group);
						groupAG.setNGName(group);
						groupAG.addChildren();
						listParent.addAnonymousGroup(groupAG);
					}
					changed.setAGParent(groupAG);
				}
			group.addChildren();					
		}			
		changed.setNGParent(group);
		return;	
	}
	
	/**
	 * Refreshes the anonymous group property of the given element (Command or NG) in the Real model
	 * @param changed - Object of the local model
	 * @param properties 
	 * @param listParent - Command List parent of object
	 */
	private void refreshAnonymousGroupProperty(CommandElementBase changed, IPropertySource properties, 
			CommandList listParent){
		String nameNewAG = (String)properties.getPropertyValue(COMMAND_PROPERTY_ANONYMOUS_GROUP);
		AnonymousGroup anonymousGroupParent = null;
		NamedGroup namedGroupParent = null;
		anonymousGroupParent = changed.getAGParent();
		namedGroupParent = changed.getNGParent();
		AnonymousGroup newGroup = null;
		if (anonymousGroupParent != null){
			if (!anonymousGroupParent.getRealName().equals(nameNewAG)){//we use the real name, not the combined
				if (anonymousGroupParent.getChildren()>0){
					anonymousGroupParent.removeChildren();
				}
				if (anonymousGroupParent.getNGParent() != null){
					if (anonymousGroupParent.getChildren() == 0){
						listParent.removeAnonymousGroup(anonymousGroupParent);
					}
				}									
			}			
			else{
				return;
			}
		}			
		if (!nameNewAG.equals("")) {//It didn't have a AG or it did and it choose a new one //$NON-NLS-1$
			newGroup = (AnonymousGroup) getGroupByName(listParent, nameNewAG, 1);				
			if (namedGroupParent != null){
				if ((newGroup != null) && (newGroup.getChildren()>0)){
					newGroup.removeChildren();
				}
				String nameCombined = newGroup.getName().
									concat(namedGroupParent.getName());
				newGroup = (AnonymousGroup) getGroupByName(listParent, nameCombined, 1);
				if (newGroup != null){
					newGroup.addChildren();
				}
				else{
					newGroup = new AnonymousGroup (nameCombined, 1);
					newGroup.setParent(listParent);
					newGroup.setNGParent(namedGroupParent);
					newGroup.setNGName(namedGroupParent);
					newGroup.addChildren();
					listParent.addAnonymousGroup(newGroup);
				}					
			}
			else{
				newGroup.addChildren();
			}
		}
		changed.setAGParent(newGroup);
		return;		
	}
	
	/**
	 * Sets all the element's properties with the given values. It takes the ids for the
	 * properties from the IPropertyDescriptors[].
	 * @param nameElement - Name of the element
	 * @param hashTable - HashTable with the property ID and the value
	 * @param oldNewNames - HashTable with the old and new names for the elements when
	 * the operation is Copy. 
	 */	
	private void setAllPropertiesElement(String nameElement,
				Hashtable<String, Object> hashTable,
				Hashtable<String, String> oldNewNames) {
		CompoundCommand cc = new CompoundCommand();
		cc.setLabel(Messages.getString("CommandsPage.commandListSection.actions.setProperties"));
	
		IComponentInstance instance = ModelUtils.lookupReference(editor.getDataModel(),
				nameElement);
		IPropertySource property = ModelUtils.getPropertySource(instance.getEObject());
		//Remove name property
		hashTable.remove(COMMAND_PROPERTY_NAME);
		Object[] allProperties = hashTable.keySet().toArray();
		for (int i = 0; i < allProperties.length; i++){
			cc.append(createSetPropertyCommand(property, (String)allProperties[i],hashTable.get(allProperties[i])));			
		}		
		FormEditorEditingContext editingContext = new FormEditorEditingContext(null, editor.getFormEditor(), treeViewer);
        EditingContextCommand wrapper = new EditingContextCommand(cc, false, editingContext);
        editor.getCommandStack().execute(wrapper);
	}
	
	/**
	 * Sets all the element's properties with the given values. It takes the ids for the
	 * properties from the IPropertyDescriptors[].
	 * @param nameElement - Name of the element
	 * @param hashTable - HashTable with the property ID and the value
	 * @param oldNewNames - HashTable with the old and new names for the elements when
	 * the operation is Copy. 
	 */	
	private CompoundCommand getAllPropertiesElementCommand(CompoundCommand cc,String nameElement,
				Hashtable<String, Object> hashTable,
				Hashtable<String, String> oldNewNames) {
		
		IComponentInstance instance = ModelUtils.lookupReference(editor.getDataModel(),
				nameElement);
		IPropertySource property = ModelUtils.getPropertySource(instance.getEObject());
		//Remove name property
		hashTable.remove(COMMAND_PROPERTY_NAME);
		Object[] allProperties = hashTable.keySet().toArray();
		for (int i = 0; i < allProperties.length; i++){
			cc.append(createSetPropertyCommand(property, (String)allProperties[i],hashTable.get(allProperties[i])));			
		}		
		return cc;
	}
	
	/**
	 * Set the properties for the command Id
	 * @param command
	 * @param newCommandID
	 */
	public void setCommandIDModel(CommandModel command, String newCommandID) {
		IPropertySource properties = ModelUtils.getPropertySource(ModelUtils.
				lookupReference(editor.getDataModel(),command.getName()).getEObject());
		createAndExecuteSetPropertyCommand(editor.getFormEditor(), properties, 
				COMMAND_PROPERTY_COMMAND_ID, newCommandID);
	}
	
	/**
	 * Change the specified property using a command
	 * @param inputObject
	 * @param propertySource
	 * @param propertyname
	 * @param newValue
	 */
	public void createAndExecuteSetPropertyCommand(Object inputObject, 
							IPropertySource propertySource, String propertyName, Object newValue){
		FormEditorEditingContext editingContext = new FormEditorEditingContext(null, editor.getFormEditor(), inputObject);
		CompoundCommand cc = new CompoundCommand();
		SetPropertyCommand propertyCommand = new SetPropertyCommand(propertySource,
			propertyName, newValue);
		String text = Messages.getString("CommandsPage.changeProperties");
	    String textFormat = MessageFormat.format(text, propertyName);
	    cc.setLabel(textFormat);
		cc.append(propertyCommand);
		EditingContextCommand wrapper = new EditingContextCommand(cc, false, editingContext);
		editor.getCommandStack().execute(wrapper);
	}
	
	/**
	 * Change the specified property using a command
	 * @param propertySource
	 * @param propertyname
	 * @param newValue
	 */
	public SetPropertyCommand createSetPropertyCommand(IPropertySource propertySource, String propertyName, Object newValue){
		SetPropertyCommand propertyCommand = new SetPropertyCommand(propertySource,
			propertyName, newValue);
		return propertyCommand;
	}
		
	/**
	 * Execute the compound command and sets its label
	 * @param cc
	 * @param label
	 */
	public void executeCompoundCommand(CompoundCommand cc, String label) {
		cc.setLabel(label);
 		FormEditorEditingContext editingContext = new FormEditorEditingContext(null, editor.getFormEditor(), treeViewer);
		EditingContextCommand wrapper = new EditingContextCommand(cc, false, editingContext);
		editor.getCommandStack().execute(wrapper);
	}
	
	/**
	 * Creates a list with the names of all the components in the real model
	 * of the given type
	 * @param componentID - Given type
	 * @return list with names
	 */
	public Vector<String> createListNamesRealModel(String componentID){
		Vector<String> listNames = UIConfigurationPageUtils.getObjectsById(editor.getDataModel(),
					componentID);
		return listNames;			
	}
	
	/**
	 * Find the old name of the component that had just change its name, to change the old
	 * name by the new one in the local model.
	 * @param componentID
	 * @param listNames
	 * @return old Name
	 */
	public String findOldNameComponent(String componentID, Vector<String> listNames){
		String found = "";
		List<CommandList> allList = getCommandListElements();
		if (componentID.equals(UI_COMMAND)){
			List<CommandModel> allCommands = new ArrayList<CommandModel>();
			for (CommandList list : allList){
				allCommands.addAll(list.getCommands()); 
			}
			for (CommandModel command : allCommands){
				String name = command.getName();
				if (!listNames.contains(name)){
					found = name;
					break;
				}
			}
		}
		else if (componentID.equals(UI_COMMAND_ID)){
			for(CommandId id : commandIdsList){
				String name = id.getName();
				if (!listNames.contains(name)){
					found = name;
					break;
				}
			} 
		}
		else if (componentID.equals(UI_ANONYMOUS_GROUP)){
			List<AnonymousGroup> allAnonymous = new ArrayList<AnonymousGroup>();
			for (CommandList list : allList){
				allAnonymous.addAll(list.getAnonymousGroupsOrfant()); 
			}
			for (AnonymousGroup group : allAnonymous){
				String name = group.getRealName();
				if (!listNames.contains(name)){
					found = name;
					break;
				}
			}			
		}
		else if (componentID.equals(UI_NAMED_GROUP)){
			List<NamedGroup> allNamed = new ArrayList<NamedGroup>();
			for (CommandList list : allList){
				allNamed.addAll(list.getNamedGroups()); 
			}
			for (NamedGroup group : allNamed){
				String name = group.getName();
				if (!listNames.contains(name)){
					found = name;
					break;
				}
			}			
		}
		else if (componentID.equals(UI_COMMAND_LIST)){
			for (CommandList list : allList){
				String name = list.getName();
				if (!listNames.contains(name)){
					found = name;
					break;
				}
			}
		}
	return found;	
	}
		
}
