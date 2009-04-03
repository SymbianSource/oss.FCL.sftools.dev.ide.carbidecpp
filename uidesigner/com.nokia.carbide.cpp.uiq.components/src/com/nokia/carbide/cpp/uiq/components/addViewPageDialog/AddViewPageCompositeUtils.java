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
package com.nokia.carbide.cpp.uiq.components.addViewPageDialog;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.component.adapter.CommonAttributes;
import com.nokia.sdt.component.adapter.IAttributes;

public class AddViewPageCompositeUtils {
	IComponentSet componentSet;
	public Hashtable<String, String> uiConfigModesLocalizedMap;
	public String[] uiConfigModeLabels;
	public Hashtable<String, String> uiConfigHashTable;
	public Hashtable<String, String> containerScrollHashTable;
	public Hashtable<String,String> layoutManagersHashTable;
	public String[] uiConfigLabels;
	public String[] containerScrollLabels;
	public String[] layoutManagersLabels;
	
	
	public AddViewPageCompositeUtils(IComponentSet componentSet) {
		this.componentSet = componentSet;
		initLabels();
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
	    
	    containerScrollHashTable = new Hashtable<String,String>(); //container labels
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
	 * Initializes the hash table for the layout managers.
	 */
	private void initializeLayoutManagerHashTable() {
		layoutManagersHashTable = new Hashtable<String, String>();
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
