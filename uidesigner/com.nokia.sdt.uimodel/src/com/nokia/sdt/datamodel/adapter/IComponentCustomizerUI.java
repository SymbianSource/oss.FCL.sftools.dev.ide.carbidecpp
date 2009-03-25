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


package com.nokia.sdt.datamodel.adapter;

import com.nokia.sdt.component.customizer.IComponentCustomizerCommandFactory;

import org.eclipse.swt.widgets.Composite;

/**
 * 
 *
 */
public interface IComponentCustomizerUI extends IModelAdapter {

	/**
	 * Creates UI (for a wizard or customizer dialog) in parent composite.
	 * @param parent a <code>Composite</code>
	 * @return the customizer composite
	 */
	Composite getCustomizerComposite(Composite parent);
	/**
	 * Embodies user choices in return value (command factory)
	 * @return <code>IComponentCustomizerCommandFactory</code>
	 */
	IComponentCustomizerCommandFactory getCommandFactory();
	
}
