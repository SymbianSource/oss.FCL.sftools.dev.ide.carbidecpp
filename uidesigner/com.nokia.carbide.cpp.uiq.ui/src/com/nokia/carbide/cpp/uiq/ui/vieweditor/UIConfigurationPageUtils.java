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
/* START_USECASES: CU5 END_USECASES */
package com.nokia.carbide.cpp.uiq.ui.vieweditor;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.component.adapter.CommonAttributes;
import com.nokia.sdt.component.adapter.IAttributes;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.editor.IDesignerDataModelEditor;
import com.nokia.sdt.emf.dm.INode;
import com.nokia.sdt.emf.dm.INodeVisitor;
import com.nokia.sdt.symbian.dm.DesignerDataModel;

/**
 * This class has some static methods that implements functions that are used in
 * other classes. This class has some reusable code.
 *
 */
public class  UIConfigurationPageUtils {
	public static Hashtable<String, String> uiConfigModesLocalizedMap;
	public static String[] uiConfigModeLabels;
	public static Hashtable<String, String> uiConfigHashTable;
	public static Hashtable<String, String> containerScrollHashTable;
	public static Hashtable<String,String> layoutManagersHashTable;
	public static String[] uiConfigLabels;
	public static String[] containerScrollLabels;
	public static String[] layoutManagersLabels;
	
	private IDesignerDataModelEditor editor;
	
	/**
	 * Class constructor.
	 * @param editor
	 */
	public UIConfigurationPageUtils(IDesignerDataModelEditor editor){
		this.editor = editor;		
	}
	
	/**
	 * Model Support Functions
	 * @param dataModel - The dataModel of the application. 
	 * @param id - The ID of the object that we are looking for.
	 * @return object - The object found, otherwise null.
	 */ 
	public static EObject getObjectById(IDesignerDataModel dataModel,
			final String id) {
		INodeVisitor objectFinder = new INodeVisitor() {
			public Object visit(INode node) {
				if (node.getComponentId().equals(id))
					return node;
				return null;
			}
		};
		return (EObject) ((DesignerDataModel) dataModel).getDesignerData()
				.visitNodes(objectFinder);
	}
	
	/**
	 * Model Support Functions. This function returns a vector with all the instance 
	 * of the ID.
	 * @param dataModel - The dataModel of the application. 
	 * @param id - The ID of the object that we are looking for.
	 * @return object - The string vector with the names of the objects found, otherwise null.
	 */
	public static Vector<String> getObjectsById(IDesignerDataModel dataModel,
			final String id) {
		final Vector<String> objects = new Vector<String>();
		INodeVisitor objectFinder = new INodeVisitor() {
			public Object visit(INode node) {
				if (node.getComponentId().equals(id))
					objects.add(node.getName());
				return null;
			}
		};
		((DesignerDataModel) dataModel).getDesignerData()
				.visitNodes(objectFinder);
		
		return objects;
	}
	
	/**
	 * Returns all the current object's names of the ID received.
	 * @param id - ID for the object.
	 * @return an array with all the names.
	 */
	public Vector<String>getCurrentObjectsFromModel(final String id) {		
		return UIConfigurationPageUtils.getObjectsById(editor.getDataModel(),id);
	} 
	
	/**
	 * Initializes the labels for the UI config mode and the Scrollable property.
	 * The hash table for the UI Config mode has the key for the localizable string as
	 * key and the constants for the config modes as values.
	 */
	public void initLabels(){
	    String[] uiConfigModesKeys = new String[] {
                "UIConfigurationPage.softkeyPortraitStyle", //$NON-NLS-1$
                "UIConfigurationPage.softkeyTouchPortraitStyle", //$NON-NLS-1$ 
                "UIConfigurationPage.penTouchPortraitStyle"}; //$NON-NLS-1$

	    String[] uiConfigModesValues = new String[] {
                "KQikSoftkeyStylePortrait", //$NON-NLS-1$
                "KQikSoftkeyStyleTouchPortrait", //$NON-NLS-1$ 
                "KQikPenStyleTouchPortrait"}; //$NON-NLS-1$
	    
	    String[] containerTypesKeys = new String[] {
				"ContainerSelectionPage.nonScrollable", //$NON-NLS-1$
				"ContainerSelectionPage.scrollable"}; //$NON-NLS-1$
		String[] containerTypesValues = new String[] {
				"EQikCtContainer", //$NON-NLS-1$
				"EQikCtScrollableContainer"}; //$NON-NLS-1$
	    
	    uiConfigModesLocalizedMap = new Hashtable<String, String>(); // key, value
	    uiConfigModeLabels = initializeLocalizedMap(uiConfigModesKeys, uiConfigModesValues, uiConfigModesLocalizedMap);
	    
	    containerScrollHashTable=new Hashtable<String,String>(); //container labels
	    containerScrollLabels = initializeLocalizedMap(containerTypesKeys,containerTypesValues, containerScrollHashTable);
	    initializeLayoutManagerHashTable();
	 }

   	/**
   	 * Initializes the hash table.
   	 * @param keys - Keys for the hash table
   	 * @param values - Values for the hash table.
   	 * @param labelValueMap - Hash table
   	 * @return array with the localizaded keys.
   	 */
	private String[] initializeLocalizedMap(String[] keys,String[] values,
	    		Hashtable<String, String> labelValueMap) { 

	    String[] labels = new String[keys.length];

	    	for (int i = 0; i < keys.length; i++) {
	    		String label = Messages.getString(keys[i]);
	    		labelValueMap.put(label, values[i]);
	    		labels[i] = label;
	    	}
	    	return labels;

	}
	
	/**
	 * Initializes the labels for the UI config mode.
	 * This hash table has  the constants for the modes as keys and the label as values. 
	 */
	public void initLabelsUIConfig(){
	    String[] uiConfigModesValues = new String[] {
                "UIConfigurationPage.softkeyPortraitStyle", //$NON-NLS-1$
                "UIConfigurationPage.softkeyTouchPortraitStyle", //$NON-NLS-1$ 
                "UIConfigurationPage.penTouchPortraitStyle"}; //$NON-NLS-1$

	    String[] uiConfigModesKeys = new String[] {
                "KQikSoftkeyStylePortrait", //$NON-NLS-1$
                "KQikSoftkeyStyleTouchPortrait", //$NON-NLS-1$ 
                "KQikPenStyleTouchPortrait"}; //$NON-NLS-1$
		
	    uiConfigHashTable = new Hashtable<String, String>(); // key, value
	    uiConfigLabels = initializeHashLabels(uiConfigModesKeys, uiConfigModesValues, uiConfigHashTable);
  
	 }

	/**
   	 * Initializes the hash table.
   	 * @param keys - Keys for the hash table (constants)
   	 * @param values - Values for the hash table. (keys for multilanguage labels)
   	 * @param labelValueMap - Hash table
   	 * @return array with the localizaded keys.
   	 */
	private String[] initializeHashLabels(String[] keys,String[] values,
	    		Hashtable<String, String> labelValueMap
	    		) { 
		String[] labels = new String[keys.length];
    	for (int i = 0; i < keys.length; i++) {
	    		String label = Messages.getString(values[i]);
	    		labelValueMap.put(keys[i], label);
	    		labels[i] = label;
	    	}
	    	return labels;
	}
	/**
	 * Return the label  for the constant received.
	 * @param uiConfigMode constant.
	 * @return the label for that constant.
	 */
	public String getLabelUIConfig(String uiConfigMode){ 
		return uiConfigHashTable.get(uiConfigMode);	
	}
	
	/**
	 * Returns the value for a property.
	 * @param nameInstance - Name of the Instance of the object
	 * @param idProperty - ID of the property
	 * @return the value of the property.
	 */
	public String getPropertyValueByName(String nameInstance,String idProperty){
		IPropertySource properties;
		EObject	instance;
		String value;
		instance = ModelUtils.lookupReference(editor.getDataModel(), nameInstance).getEObject();
		properties = ModelUtils.getPropertySource(instance);
		value = (String)properties.getPropertyValue(idProperty);
		return value;		
	}
	
	/**
	 * Returns the value for a property.
	 * @param instance - EObject of the instance.
	 * @param idProperty - ID for the property.
	 * @return The value of the property.
	 */
	public String getPropertyValueByInstance(EObject instance,String idProperty){
		IPropertySource properties;
		String value;
		properties = ModelUtils.getPropertySource(instance);
		value = (String)properties.getPropertyValue(idProperty);
		return value;		
	}

	/**
	 * Initializes the hash table for the layout managers.
	 */
	private void initializeLayoutManagerHashTable() {
		layoutManagersHashTable = new Hashtable<String, String>();
		IComponentSet componentSet = editor.getDataModel().getComponentSet();
		if (componentSet != null) {
			ArrayList<String> layoutManagerKeysList = new ArrayList<String>();
			ArrayList<String> layoutManagerLabelsList = new ArrayList<String>();
			for (Iterator<IComponent> iter = componentSet.iterator(); iter.hasNext();) {
				IComponent component = iter.next();
				IAttributes attributes = (IAttributes) component.getAdapter(IAttributes.class);
				if (!component.isAbstract() &&
						attributes.getBooleanAttribute(CommonAttributes.IS_LAYOUT_MANAGER, false)) {
					layoutManagerKeysList.add(component.getId());
					layoutManagerLabelsList.add(component.getFriendlyName());
				}
			}
			
			String[] layoutManagersKeys = new String[layoutManagerKeysList.size()];
			layoutManagersLabels = new String[layoutManagerKeysList.size()];
			
			for (int i=0; i<layoutManagerKeysList.size(); i++) { 
				layoutManagersKeys[i] = (String)layoutManagerKeysList.get(i);
				layoutManagersLabels[i] = (String)layoutManagerLabelsList.get(i);
				String label = (String)layoutManagerLabelsList.get(i);
				layoutManagersHashTable.put(label, layoutManagersKeys[i]);
			}
		} else {
			layoutManagersLabels = new String[]{};
		}
	}
		
}
