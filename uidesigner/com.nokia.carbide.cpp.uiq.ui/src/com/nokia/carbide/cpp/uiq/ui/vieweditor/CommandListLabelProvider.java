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

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
/**
 * This class manages the labels from the TreeViewer
 * The column 1 has the name of the instance and the column 2 has the command ID only
 * for commands.
 *
 */
public class CommandListLabelProvider extends LabelProvider implements
		ITableLabelProvider{

	/**
	 * Class constructor
	 */
	public CommandListLabelProvider(){
	}
	
	/**
	 * Returns the label for the given element
	 */
	public String getText(Object element) {
	    
	     if (element instanceof CommandModel ){
	    	 CommandModel command = (CommandModel) element;
	    	 return command.getName();
	     }  else if (element instanceof CommandList){
	    	 	CommandList commandList = (CommandList) element;
	    	 	return commandList.getName();	    	 
	     	}
	     else if (element instanceof NamedGroup){
	    	 	NamedGroup named = (NamedGroup) element;
	    	 	return named.getName();	    	 
	     	}
	     else if (element instanceof AnonymousGroup){
	    	 	AnonymousGroup anonymous = (AnonymousGroup) element;
	    	 	return anonymous.getLabelName();	    	 	
	     	}
	     else
	    	 return "";
	     
	 }

	/**
	 * Method declared in ITableLabelProvider
	 */
	 public Image getColumnImage(Object element, int indexColumn) {
		return null;
	 }

	/**
	 * Returns the label for the given element
	 * @param 
	 */
	 public String getColumnText(Object element, int indexColumn) {
	  String lbl = "";
		 if (indexColumn == 0) {
			if (element instanceof CommandModel) {
				CommandModel command = (CommandModel) element;
				return command.getName();
			} else if (element instanceof CommandList) {
				CommandList commandList = (CommandList) element;
				return commandList.getName();
			} else if (element instanceof NamedGroup) {
				NamedGroup named = (NamedGroup) element;
				return named.getName();
			} else if (element instanceof AnonymousGroup) {
				AnonymousGroup anonymous = (AnonymousGroup) element;
				return anonymous.getLabelName();
				
			} else
				return "";
		}
		else{ //if its the column 2, then returns the command id only for commands
			if (element instanceof CommandModel){
				return ((CommandModel) element).getCommandID();
			}
			return lbl;
		}
	 }
}

	
