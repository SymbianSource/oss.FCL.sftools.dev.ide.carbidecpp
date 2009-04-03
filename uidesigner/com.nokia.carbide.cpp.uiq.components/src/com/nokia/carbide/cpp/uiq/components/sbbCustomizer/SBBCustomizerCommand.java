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


package com.nokia.carbide.cpp.uiq.components.sbbCustomizer;

import com.nokia.sdt.component.customizer.ICustomizeComponentCommand;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ui.views.properties.IPropertySource;

public class SBBCustomizerCommand implements ICustomizeComponentCommand {

	private IPropertySource propertySource;
	private String sbbValue;
	private String previousValue;
	public final static String TYPE_PROPERTY_ID = "type";
	public final static String DEFAULT_TYPE_VALUE = "EQikCtOnelineBuildingBlock";
	
	/**
	 * @param instance
	 */
	public SBBCustomizerCommand(EObject instance, String typeValue) {
		Check.checkArg(instance);
		this.sbbValue = typeValue;
		propertySource = (IPropertySource) EcoreUtil.getRegisteredAdapter(instance, IPropertySource.class);
		Check.checkState(propertySource != null);
		previousValue = (String) propertySource.getPropertyValue(TYPE_PROPERTY_ID);
		if (previousValue == null)
			previousValue = DEFAULT_TYPE_VALUE;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.customizer.ICustomizeComponentCommand#canExecute()
	 */
	public boolean canExecute() {
		return sbbValue != null;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.customizer.ICustomizeComponentCommand#execute()
	 */
	public String execute() {
		propertySource.setPropertyValue(TYPE_PROPERTY_ID, sbbValue);
		return null;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.customizer.ICustomizeComponentCommand#undo()
	 */
	public String undo() {
		propertySource.setPropertyValue(TYPE_PROPERTY_ID, previousValue);
		return null;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.customizer.ICustomizeComponentCommand#redo()
	 */
	public String redo() {
		return execute();
	}

	public Object getValue() {
		return sbbValue;
	}

}
