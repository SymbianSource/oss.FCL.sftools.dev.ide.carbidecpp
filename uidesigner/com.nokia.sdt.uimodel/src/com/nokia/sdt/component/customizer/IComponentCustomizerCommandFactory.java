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


package com.nokia.sdt.component.customizer;

import org.eclipse.emf.ecore.EObject;

/**
 * 
 *
 */
public interface IComponentCustomizerCommandFactory {

	
	/**
	 * @param instance the instance for which to create the customizer command
	 * @return the ICustomizeComponentCommand
	 */
	ICustomizeComponentCommand createCustomizeComponentCommand(EObject instance);

}
