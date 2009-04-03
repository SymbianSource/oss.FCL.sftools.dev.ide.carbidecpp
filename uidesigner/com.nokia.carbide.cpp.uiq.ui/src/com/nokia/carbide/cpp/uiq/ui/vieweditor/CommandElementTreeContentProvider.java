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

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;

/**
 * This class sets content for  the treeViewer
 *
 */
public class CommandElementTreeContentProvider extends ArrayContentProvider
		implements ITreeContentProvider {
	TreeViewer treeViewer;
	
	/**
	 * Returns the root elements (Command Lists)
	 * @param inputElement
	 * @return roots
	 */
	public Object[] getElements(Object inputElement){
		List<CommandList> roots = (List<CommandList>) inputElement;
		return roots.toArray();
	}
	
	/**
	 * Returns the children's array of the given parent
	 * @param parent
	 */
	public Object[] getChildren(Object parent) {
		if (parent instanceof CommandList){
		CommandList commandList = (CommandList) parent;
		List<AnonymousGroup> allAnonymous = new ArrayList<AnonymousGroup>();
		List<AnonymousGroup> repeated = new ArrayList<AnonymousGroup>();
		allAnonymous = commandList.getAnonymousGroupsOrfant();
		for (AnonymousGroup group : allAnonymous){
			if ((group.getType() == 0)){
				repeated.add(group);
			}
		}
	    return concat(commandList.getCommandsOrfant().toArray(),
	    		repeated.toArray(),				
				commandList.getNamedGroupsOrfant().toArray());
		}
		else{ if (parent instanceof NamedGroup){
			NamedGroup namedGroup = (NamedGroup) parent;
			return getChildrenNamedGroup(namedGroup);
			}
		else{ if (parent instanceof AnonymousGroup){
				AnonymousGroup anonymousGroup = (AnonymousGroup) parent;
				if (anonymousGroup.getType() == 1){
					CommandList listParent = anonymousGroup.getParent();
					NamedGroup namedGroup = (NamedGroup)(listParent.getGroupByName(
							anonymousGroup.getNamedGroupName(), 0));
					return getChildrenAnonymousGroup(anonymousGroup, namedGroup);
				}
				else{
					return getChildrenAnonymousGroup(anonymousGroup, null);
				}
							
			}			
		}
		return null;
		}
	}

	/**
	 * Returns the children of the AnonymousGroup. This children only have the
	 * anonymous group assigned
	 * @param anonymousGroup
	 * @return
	 */
	private List<CommandModel> getChildrenAnonymousGroupAlone(
			AnonymousGroup anonymousGroup) {
		List<CommandModel> commands = new ArrayList<CommandModel>();
		commands = anonymousGroup.getParent().getCommandsForAnonymousAlone(anonymousGroup);
		return commands;
	}
	
	/**
	 * Returns the result array of the concatenation of the 3 arrays given.
	 * @param array1
	 * @param array2
	 * @param array3
	 * @return array1 + array2 + array3
	 */
	protected Object[] concat(Object[] object, Object[] more, Object[] more2) {
		Object[] both = new Object[object.length + more.length + more2.length];
		System.arraycopy(object, 0, both, 0, object.length);
		System.arraycopy(more, 0, both, object.length, more.length);
		System.arraycopy(more2, 0, both, object.length + more.length, more2.length);		
		return both;
	}
	
	/**
	 * Returns the parent of the given element
	 */
	public Object getParent(Object child) {
		if (child instanceof CommandModel){
		CommandModel command = (CommandModel) child;
		AnonymousGroup anonymousGroup = command.getAGParent();
		NamedGroup namedGroup = command.getNGParent();
		if ((anonymousGroup != null) && (namedGroup != null)){
			return anonymousGroup;
		}
		if (anonymousGroup != null){
			return anonymousGroup;
		}
		if (namedGroup != null){
			return namedGroup;
		}
		return command.getParent();//child of commandList
		}
		else if (child instanceof NamedGroup){
				NamedGroup namedGroup = (NamedGroup)child;
				AnonymousGroup anonymousGroup = namedGroup.getAGParent();
				NamedGroup namedGroupP = namedGroup.getNGParent();
				if ((anonymousGroup != null) && (namedGroupP != null)){
					return anonymousGroup;
				}
				if (anonymousGroup != null){
					return anonymousGroup;
				}
				if (namedGroupP != null){
					return namedGroupP;
				}
				return namedGroup.getParent(); //child of commandList
			}
		else if (child instanceof AnonymousGroup){
				AnonymousGroup anonymousGroup = (AnonymousGroup) child;
				if (anonymousGroup.getType() == 0){
					return anonymousGroup.getParent();
				}
				else{
					return anonymousGroup.getNGParent();
				}
			}
		return null;
	}

	/**
	 * Indicates if the given element has children or not to show a + /- at the TreeViewer
	 */
	public boolean hasChildren(Object element) {
		if (element instanceof CommandList){
			CommandList commandList = (CommandList) element;
			if ((commandList.getCommands().toArray().length > 0) ||
		 	    (commandList.getNamedGroups().toArray().length > 0) ||
		 	    (commandList.getAnonymousGroups().toArray().length > 0)){
				return true;
			}			
		}
		if (element instanceof AnonymousGroup){
			AnonymousGroup anonymousGroup = (AnonymousGroup) element;
			if (anonymousGroup.getChildren() > 0){
				return true;
			}			
		}
		if (element instanceof NamedGroup){
			NamedGroup namedGroup = (NamedGroup) element;
			if (namedGroup.getChildren() > 0){
				return true;
			}			
		}
		return false;
	}
	
	/**
	 * Method declared in the ITreeContentProvider
	 */
	public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		this.treeViewer = (TreeViewer) v;		
	}
	
	/**
	 * Returns the children'array of the named group given
	 * @param parent
	 * @return children array
	 */
	public Object[]	getChildrenNamedGroup(NamedGroup parent){
		CommandList commandList = parent.getParent();
		List<CommandModel> commands = new ArrayList<CommandModel>();
		commands = commandList.getCommandsForNamed(parent);
		List<AnonymousGroup> anonymousGroups = new ArrayList<AnonymousGroup>();
		anonymousGroups = commandList.getAnonymousGroups();
		List<NamedGroup> namedGroups = new ArrayList<NamedGroup>();
		namedGroups = commandList.getNamedGroups();
		List<Object> allObjects = new ArrayList<Object>();
		if (commands.size() > 0){
			for (CommandModel command : commands){
				allObjects.add(command);
			}
		}
		if (namedGroups.size() != 0) {
			//Get all the namedGroups
			for (NamedGroup ng : namedGroups) {
				if ((ng.getNGParent() != null) && (ng.getAGParent() == null) ) {
					if (ng.getNGParent().getName().equals(parent.getName())) {
						allObjects.add((Object)ng);
					}
				} 
			}		
		}
		if (anonymousGroups.size() > 0) {
			//Get all the anonymmousGroups
			for (AnonymousGroup ag : anonymousGroups) {
				if (ag.getType() != 0) {
					if(ag.getNamedGroupName().equals(parent.getName())){
						allObjects.add((Object)ag);
					}					
				} 
			}
		}
		return allObjects.toArray();		
	}
	
	/**
	 * Returns the array of children for the given anonymous parent. If named is different
	 * from null then the Anonymous group is type 1 
	 * @param parent
	 * @param named
	 * @return
	 */
	public Object[]	getChildrenAnonymousGroup(AnonymousGroup parent, NamedGroup named){
		CommandList commandList = parent.getParent();
		List<CommandModel> commands = new ArrayList<CommandModel>();
		if (named != null){
			commands = commandList.getCommandsForAnonymous(parent);
		}
		else{
			commands = getChildrenAnonymousGroupAlone(parent);
		}
		List<NamedGroup> namedGroups = new ArrayList<NamedGroup>();
		namedGroups = commandList.getNamedGroups();
		List<Object> allObjects = new ArrayList<Object>();
		if (commands.size() > 0){
			for (CommandModel command : commands){
				allObjects.add(command);
			}
		}
		if (namedGroups.size() > 0) {
			//Get all the namedGroups
			for (NamedGroup ng : namedGroups) {
				if (ng.getAGParent() != null) {
					if (ng.getAGParent().getName().equals(parent.getName())) {
						allObjects.add(ng);
					}
				} 
			}
		}
		return allObjects.toArray();		
	}
		
	public void dispose() {		
	}

	
}
