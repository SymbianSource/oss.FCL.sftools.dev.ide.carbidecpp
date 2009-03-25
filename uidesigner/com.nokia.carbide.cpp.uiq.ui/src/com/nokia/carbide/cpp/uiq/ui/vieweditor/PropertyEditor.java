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
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.ModelUtils;
 /**
  * This class creates a window to edit the compound properties: CPF flags and States
  * flags. It is launched from the Commands Page.
  */
public class PropertyEditor extends Dialog{
	private CommandElementModel modelCommands;
	private IPropertySource properties;
	private IPropertySource flagsProperties;
	private String flagID;
	private Hashtable<String, String> hashProperties;
	private Hashtable<String, String> hashInitialState;
	private static final String COMMAND_PROPERTY_STATE_FLAGS = "stateFlags";
	private int size;
	private CheckboxTableViewer checkboxViewer;
	private List<CheckBoxItem> propertiesList;
	
	/**
	 *  Class Constructor
	 *  @param parentShell
	 * @param flagID 
	 *    
	 */
	protected PropertyEditor(Shell parentShell, String flagID, IComponentInstance instance,
						CommandElementModel modelCommands) {
		super(parentShell);
		this.flagID = flagID;
		if (flagID == COMMAND_PROPERTY_STATE_FLAGS){
			size = 0;
		}
		else{
			size = 1;
		}
		properties = ModelUtils.getPropertySource(instance.getEObject());
		flagsProperties = (IPropertySource)properties.getPropertyValue(flagID);
		hashProperties = new Hashtable<String, String>();
		hashInitialState = new Hashtable<String, String>();
		this.modelCommands = modelCommands;
		propertiesList = new ArrayList<CheckBoxItem>();
	}
	
	
	
	/**
	 * Create contents of the dialog
	 * @param parent
	 */
	protected Control createDialogArea(Composite parent) {		
		initializeProperties();		
		Composite composite = new Composite(parent, SWT.NONE);
	    composite.setLayout(new GridLayout(1, false));		
	    checkboxViewer = CheckboxTableViewer.newCheckList(
	        composite, SWT.BORDER);	   
	    GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
	    gridData.widthHint = 150+(size*140);
	    checkboxViewer.getTable().setLayoutData(gridData);
	    checkboxViewer.setContentProvider(new TableContentProvider());
	    checkboxViewer.setLabelProvider(new TableLabelProvider());
	    checkboxViewer.setInput(propertiesList.toArray());
	    setCheckedProperties();
	    checkboxViewer.addCheckStateListener(new ICheckStateListener() {
	        public void checkStateChanged(CheckStateChangedEvent event) {
	            if (((CheckBoxItem)event.getElement()).getValue()){	            	
	            	((CheckBoxItem)event.getElement()).setValue(false);
	            }
	            else{	            	
	        	 ((CheckBoxItem)event.getElement()).setValue(true);
	           }
	        }
	    });
		return null;	
	}
	
	/**
	 * Initializes the table with the properties of the given compound property 
	 */
	private void initializeProperties(){		
		IPropertyDescriptor[] descriptors = flagsProperties.
											getPropertyDescriptors();		
		List<String> nameProperties = new ArrayList<String>();
		for (int i = 0; i < descriptors.length; i++){
			hashProperties.put((String)descriptors[i].getDisplayName(),
					(String)descriptors[i].getId());
			nameProperties.add((String)descriptors[i].getDisplayName());
		}
		Collections.sort(nameProperties);
		Iterator iter = nameProperties.iterator();
		while (iter.hasNext()){	
			String label = (String)iter.next();			
			Boolean value = (Boolean)(flagsProperties.
									getPropertyValue(hashProperties.get(label)));	
			hashInitialState.put(hashProperties.get(label), value.toString());
			CheckBoxItem itemCheck = new CheckBoxItem(label, value.booleanValue());
			propertiesList.add(itemCheck);
		}
	}
	/**
	 * Sets the initial state of the elements at the table
	 */
	private void setCheckedProperties(){
		List<Object> checked = new ArrayList<Object>();
		for(CheckBoxItem item : propertiesList){
			if (item.getValue()){
				checked.add(item);
			}
		}
		checkboxViewer.setCheckedElements(checked.toArray());
	}
	/**
	 * Refreshes the property on the real model.
	 */
	public void refreshProperty() {		
		String key = "Name:"+""+" "+"Property:"+flagID;
		for(CheckBoxItem element : propertiesList){
			String flag = hashProperties.get(element.getName());
			Boolean value = element.getValue();
			if (!hashInitialState.get(flag).equals(value.toString())){
				modelCommands.createAndExecuteSetPropertyCommand(
						key, flagsProperties, flag,value.booleanValue());
			}
		}	
		this.close();
	}
	/**
	 * Return the initial size of the dialog
	 */
	protected Point getInitialSize() {
		return new Point(190+(size*160), 270+(size*95));
	}
	
	/**
	 * This method sets the label for the title of the dialog.
	 * @param newShell
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		if (flagID.equals(COMMAND_PROPERTY_STATE_FLAGS)){
			newShell.setText(Messages.getString("PropertyEditor.stateFlags.title")); 
		}
		else{
			newShell.setText(Messages.getString("PropertyEditor.cpfFlags.title"));
		}
	}
	
	/**
	 * When the user press the OK button, this method is called for the 
	 * refreshed.
	 */
	protected void okPressed() {
		
		refreshProperty();
	}
	
	/**
	 * This inner class represents the object property for the checkboxTableviewer.
	 *
	 */
	class CheckBoxItem {
		TableEditor editor;
		Button button;
		String nameProperty;
		boolean value;
		
		/**
		 * Class constructor
		 * @param name
		 * @param value
		 */
		public CheckBoxItem(String name, boolean value){
			this.nameProperty = name;
			this.value = value;
		}
		
		/**
		 * Returns the value
		 * @return
		 */
		public boolean getValue() {
			return value;
		}
		
		/**
		 * Returns the name of the property (displayed value)
		 * @return
		 */
		public String getName(){
			return nameProperty;
		}
		/**
		 * Sets the value of the property
		 * @param value
		 */
		public void setValue(boolean value) {
			this.value = value;			
		}
	}
	
	/**
	 * This inner class provides the content for the Table. 
	 */
	class TableContentProvider implements IStructuredContentProvider{
		
		public void dispose() {			
		}

		/**
		 * Returns all the elements in the table
		 */
		public Object[] getElements(Object parent) {
			return propertiesList.toArray();
		}		

		public void inputChanged(Viewer arg0, Object arg1, Object arg2) {			
		}
		
	}
	
	/**
	 * This class provides the labels for the CheckboxTableViewer
	 *
	 */
	class TableLabelProvider extends LabelProvider {
			
		public Image getImage(Object element) {
			return null;
		}

		/**
		 * Returns the name of the property		    
		 * @param element            
		 * @return String
		 */
		public String getText(Object element) {
		   return ((CheckBoxItem) element).getName();
		}	
	}

}
