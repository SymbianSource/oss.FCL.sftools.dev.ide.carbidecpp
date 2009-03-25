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


package com.nokia.sdt.component.adapter;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.ecore.EObject;

import java.util.List;


/**
 * 
 *
 */
public interface IComponentImplementations extends IComponentAdapter {

	/**
	 * @param interfaceId
	 * @return true if this component has an implementation for the interface class
	 */
	boolean supportsInterface(String interfaceId);
	
	/**
	 * @param interfaceId
	 * @return a java.util.List of interface Ids 
	 * which are all implemented by the same implementation
	 * (ie., the implementation that implements that interface)
	 */
	List getAssociatedInterfaces(String interfaceId);

	/**
	 * @param interfaceId
	 * @param instance an EObject
	 * @return the implementation org.eclipse.emf.common.notify.Adapter 
	 */
	Adapter getImplementationAdapter(String interfaceId, EObject instance);
	
}
