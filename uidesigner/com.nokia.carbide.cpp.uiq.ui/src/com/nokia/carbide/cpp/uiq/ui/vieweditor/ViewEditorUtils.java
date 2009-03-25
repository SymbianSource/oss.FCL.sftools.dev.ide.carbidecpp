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

import java.lang.reflect.InvocationTargetException;
import java.util.StringTokenizer;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.PlatformUI;

import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.editor.IDesignerDataModelEditor;
import com.nokia.sdt.uidesigner.ui.UIDesignerPlugin;
/**
 * This class has some static methods to be called from anywhere.
 * They are some utilities.
 *
 */
public class ViewEditorUtils {
	/**
	 * Generates the name for the Enumerator displayed at the Command ID table
	 * @param uiDesignName
	 * @param commandIdName
	 * @return
	 */
	public static String generateNameEnumerator(String uiDesignName, String commandIdName){
		String newName, commandName;		  
		//designName = removeSpaces(uiDesignName);
		commandName = removeSpaces(commandIdName);
/*		newName = "E" + designName.substring(0,1).toUpperCase() +
				designName.substring(1, designName.length()) +
				commandName.substring(0,1).toUpperCase() +
				commandName.substring(1, commandName.length());		
*/
		newName = "E" + commandName;		

		return newName;
	}
	
	/**
	 * Removes spaces from a given string.
	 * @param s
	 * @return
	 */
	public static String removeSpaces(String s) {
		  StringTokenizer st = new StringTokenizer(s," ",false);
		  String t="";
		  while (st.hasMoreElements()) t += st.nextElement();
		  return t;
	}
	
	/**
	 * Returns the index of the item with the given name at the given table
	 * @param table
	 * @param name
	 * @return index of the item
	 */
	public static int getIndexTableByValue(Table table, String name){
		TableItem[] items = table.getItems();
		int index = 0;
		for (TableItem item : items){
			if (item.getText(0).equals(name)){
				break;
			}
			index++;
		}
		return index;
	}
	
	/**
	 * This code was taken from the EventCommands class. It saves the model.
	 * @return
	 */
	public static boolean saveDataModel(IDesignerDataModelEditor modelEditor) {
		final IDesignerDataModelEditor editor = modelEditor;
		IRunnableWithProgress runnable = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				editor.doSave(monitor);
			}
		};
		try {
			PlatformUI.getWorkbench().getProgressService().runInUI(PlatformUI.getWorkbench().getActiveWorkbenchWindow(), runnable, null);
			return true;
		} catch (InvocationTargetException e) {
			UIDesignerPlugin.getDefault().log(e);
			return false;
		} catch (InterruptedException e) {
			return false;
		}
	}
	
	public static boolean validateUniqueName(IDesignerDataModel model, String name){
		//if its legal, let's check if its unique
		boolean result = true;
		EObject foundObj = model.findByNameProperty(name);
		if (foundObj != null ) {
			return false;
		}
		return result;		
	}
	
}
