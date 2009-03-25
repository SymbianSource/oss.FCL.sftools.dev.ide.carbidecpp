/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.sdt.series60.component.listCustomizer;

import com.nokia.sdt.component.customizer.ICustomizeComponentCommand;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * 
 *
 */
public class ListCustomizerCommand implements ICustomizeComponentCommand {

	private IPropertySource propertySource;
	private String listBoxStyleValue;
	private String previousValue;
	private final static String STYLE_PROPERTY_ID = "style";
	public final static String DEFAULT_LISTBOX_VALUE = "CAknSingleStyleListBox";
	
	/**
	 * @param instance
	 */
	public ListCustomizerCommand(EObject instance, String listBoxStyleValue) {
		Check.checkArg(instance);
		this.listBoxStyleValue = listBoxStyleValue;
		propertySource = (IPropertySource) EcoreUtil.getRegisteredAdapter(instance, IPropertySource.class);
		Check.checkState(propertySource != null);
		previousValue = (String) propertySource.getPropertyValue(STYLE_PROPERTY_ID);
		if (previousValue == null)
			previousValue = DEFAULT_LISTBOX_VALUE;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.customizer.ICustomizeComponentCommand#canExecute()
	 */
	public boolean canExecute() {
		return listBoxStyleValue != null;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.customizer.ICustomizeComponentCommand#execute()
	 */
	public String execute() {
		propertySource.setPropertyValue(STYLE_PROPERTY_ID, listBoxStyleValue);
		return null;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.customizer.ICustomizeComponentCommand#undo()
	 */
	public String undo() {
		propertySource.setPropertyValue(STYLE_PROPERTY_ID, previousValue);
		return null;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.customizer.ICustomizeComponentCommand#redo()
	 */
	public String redo() {
		return execute();
	}

}
