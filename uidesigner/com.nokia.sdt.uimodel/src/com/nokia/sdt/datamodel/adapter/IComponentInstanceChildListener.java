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
package com.nokia.sdt.datamodel.adapter;

import org.eclipse.emf.ecore.EObject;

/**
 * Listener for child insertion/removal events.
 * This information is also available via EMF notification events, 
 * but this interface provides abstraction from the specific structure of the model.
 */
public interface IComponentInstanceChildListener {
	
	/**
	 * A child was instance was added
	 */
	void childAdded(EObject parent, EObject child);
	
	/**
	 * A child instance was removed
	 */
	void childRemoved(EObject parent, EObject child);
	
	/**
	 * Children of this object have been reordered
	 * @param parent
	 */
	void childrenReordered(EObject parent);

}
