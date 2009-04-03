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
import java.util.List;
/**
 * This class represents the Command List instance in the local model.
 *
 */
public class CommandList {	
	private String name;
	private List<CommandModel> commands;
	private List<NamedGroup> namedGroups;
	private List<AnonymousGroup> anonymousGroups;

	/**
	 * Class constructor
	 * @param name
	 */
	public CommandList(String name){
		this.name = name;
		commands = new ArrayList<CommandModel>();
		namedGroups = new ArrayList<NamedGroup>();
		anonymousGroups = new ArrayList<AnonymousGroup>();
	}
	/**
	 * Returns the name of the command list
	 * @return
	 */
	public String getName(){
		return name;
	}
	/**
	 * Sets the name of the command list
	 * @return
	 */
	public void setName(String name){
		this.name = name;
	}
	/**
	 * Add child to the commandList
	 * @param command
	 */
	public void addCommand(CommandModel command){
		commands.add(command);
	}
	
	/**
	 * Remove a command from the command list
	 * @param command
	 */
	public void removeCommand(CommandModel command){		
		commands.remove(commands.indexOf(command));
	}
	
	/**
	 * Add child to the commandList
	 * @param command
	 */
	public void addNamedGroup(NamedGroup group){
		namedGroups.add(group);
	}
	
	/**
	 * Remove a named group from the command list
	 * @param index
	 */
	public void removeNamedGroupCommand(int index){
		namedGroups.remove(index);
	}
	
	/**
	 * Add child to the commandList
	 * @param command
	 */
	public void addAnonymousGroup(AnonymousGroup group){
		anonymousGroups.add(group);
	}
	
	/**
	 * Remove an anonymous group from the command list
	 * @param group
	 */
	public void removeAnonymousGroup(AnonymousGroup group){
		anonymousGroups.remove(anonymousGroups.indexOf(group));
	}
	
	/**
	 * Returns the list of commands of this command list
	 * @return
	 */
	public List<CommandModel> getCommands(){
		return commands;
	}
	
	/**
	 * Returns the list of commands without AG nor NG in this command list
	 * @return
	 */
	public List<CommandModel> getCommandsOrfant(){
		List<CommandModel> allOrfant = new ArrayList<CommandModel>();
		for (CommandModel command: commands){
			if ((command.getAGParent()) == null && (command.getNGParent() == null) ){
				allOrfant.add(command);
			}
		}
		return allOrfant;
	}
	
	/**
	 * Returns the commands for this named group
	 * @param namedGroup
	 * @return
	 */
	public List<CommandModel> getCommandsForNamed(NamedGroup namedGroup){
		List<CommandModel> commandsNamed = new ArrayList<CommandModel>();
		for (CommandModel command: commands){
			if ((command.getAGParent()) == null && (command.getNGParent() != null) ){
				if (command.getNGParent().getName().equals(namedGroup.getName())){
					commandsNamed.add(command);
				}
			}
			//If the command has the two groups
			if ((command.getAGParent()) != null && (command.getNGParent() != null) ){
				if (command.getNGParent().getName().equals(namedGroup.getName())){
					//commandsNamed.add(command);
				}
			}
		}
		return commandsNamed;
	}
	
	/**
	 * Returns the commands for this anonymous group
	 * @param anonymousGroup
	 * @return
	 */
	public List<CommandModel> getCommandsForAnonymous(AnonymousGroup anonymousGroup){
		List<CommandModel> commandsAnonymous = new ArrayList<CommandModel>();
		for (CommandModel command: commands){
			if ((command.getAGParent()) != null && (command.getNGParent() == null) ){
				if (command.getAGParent().getName().equals(anonymousGroup.getName())){
					commandsAnonymous.add(command);
				}
			}
			//If the command has the two groups, and the named doesn't have a namedGroup			
			if ((command.getAGParent()) != null && (command.getNGParent() != null) ){
				if ((command.getAGParent().getName().equals(anonymousGroup.getName()))){
						//&& (anonymousGroup.getNGParent() == null)){
					commandsAnonymous.add(command);
				}
			}
		}
		return commandsAnonymous;
	}
	
	/**
	 * Returns the commands for this anonymous group. The commands only have set an AG.
	 * @param anonymous
	 * @return
	 */
	public List<CommandModel> getCommandsForAnonymousAlone(AnonymousGroup anonymous){
		List<CommandModel> commandsAnonymous = new ArrayList<CommandModel>();
		for (CommandModel command: commands){
			if ((command.getAGParent()) != null && (command.getNGParent() == null) ){
				if (command.getAGParent().getName().equals(anonymous.getName())){
					commandsAnonymous.add(command);
				}
			}
		}
		return commandsAnonymous;
	}
	
	/**
	 * Returns the list of named groups.
	 * @return
	 */
	public List<NamedGroup> getNamedGroups(){
		return namedGroups;
	}

	/**
	 * Returns the list of the anonymous group.
	 * @return
	 */
	public List<AnonymousGroup> getAnonymousGroups(){
		return anonymousGroups;
	}
	
	/**
	 * Returns the list of anonymous group without a NG parent.
	 * @return
	 */
	public List<AnonymousGroup> getAnonymousGroupsOrfant(){
		List<AnonymousGroup> all = new ArrayList<AnonymousGroup>();		
		List<AnonymousGroup> allNamed = new ArrayList<AnonymousGroup>();
		for (AnonymousGroup group : anonymousGroups){
			if (group.getNamedGroupName().equals("")){
				all.add(group);
			}
			else{
				allNamed.add(group);
			}
		}
		for (AnonymousGroup group : allNamed){
			String realName = group.getRealName();
			AnonymousGroup named = (AnonymousGroup) getGroupByName(realName, 1);
			if ((all.contains(named)) && (named.getChildren()<=0)){
				all.remove(named);
			}
		}
		return all;
	}
	
	/**
	 * Returns the list of anonymous group with this NG parent.
	 * @param parent - NG Parent of the anonymous group 
	 * @return
	 */
	public List<AnonymousGroup> getAnonymousGroupsParent(NamedGroup parent){
		List<AnonymousGroup> allNamed = new ArrayList<AnonymousGroup>();
		for (AnonymousGroup group : anonymousGroups){
			if ((group.getNGParent() != null) && (group.getNGParent().equals(parent))){
				allNamed.add(group);
			}
		}		
		return allNamed;
	}
	/**
	 * Gets all the anonymous group type 1, with the given real name
	 * @param oldName
	 * @return list of anonymous groups
	 */
	public List<AnonymousGroup> getAnonymousGroupWithRealName(String oldName) {
		List<AnonymousGroup> compound = new ArrayList<AnonymousGroup>();
		for (AnonymousGroup group : anonymousGroups){
			if ((group.getType() == 1) && (group.getRealName().equals(oldName))){
				compound.add(group);
			}
		}		
		return compound;
	}	
	
	/**
	 * Returns the list of named group without parents (AG or NG). 
	 * @return
	 */
	public List<NamedGroup> getNamedGroupsOrfant(){
		List<NamedGroup> all = new ArrayList<NamedGroup>();
		
		for (NamedGroup group : namedGroups){
			if ((group.getNGParent() == null) && (group.getAGParent() == null)){
				all.add(group);
			}
		}
		return all;
	}

	/**
	 * Returns the named (anonymous) group instance for the given name and the given type
	 * @param name
	 * @param type - 0 for named groups and 1 for anonymous group
	 * @return Named or Anonymous group instance
	 */
	public Object getGroupByName(String name, int type){ // 0 - named 1 -anonymous
		if (type == 0){
			List<NamedGroup> listGroup = this.getNamedGroups();
			for (NamedGroup namedGroup : listGroup){
				if (namedGroup.getName().equals(name))
					return namedGroup;
			}
		}
		else {
			List<AnonymousGroup> listGroup = this.getAnonymousGroups();
			for (AnonymousGroup anonymousGroup : listGroup){
				if (anonymousGroup.getName().equals(name))
					return anonymousGroup;
			}
		}
		return null;
	}
	/**
	 * @param commands the commands to set
	 */
	public void setCommands(List<CommandModel> commands) {
		this.commands = commands;
	}
	
	
}
