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

import com.nokia.sdt.component.customizer.IComponentCustomizerCommandFactory;
import com.nokia.sdt.component.customizer.ICustomizeComponentCommand;

import org.eclipse.emf.ecore.EObject;

/**
 * 
 *
 */
public class ListCustomizerCommandFactory implements IComponentCustomizerCommandFactory {

	private String listBoxLayoutValue = ListCustomizerCommand.DEFAULT_LISTBOX_VALUE;

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.customizer.IComponentCustomizerCommandFactory#createCustomizeComponentCommand(org.eclipse.emf.ecore.EObject)
	 */
	public ICustomizeComponentCommand createCustomizeComponentCommand(EObject instance) {
		return new ListCustomizerCommand(instance, listBoxLayoutValue);
	}

	public void setValue(String listBoxLayoutValue) {
		this.listBoxLayoutValue = listBoxLayoutValue; 
	}
}
