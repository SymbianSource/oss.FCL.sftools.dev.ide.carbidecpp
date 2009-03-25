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
 * Listener for property events.
 * This information is also available via EMF notification events, 
 * but this interface provides abstraction from the specific structure of the model.
 * Example javascript protoype implementing this interface:
 *	<blockquote><pre>
 *
 * function CPropertyListener() {
 * }
 *
 * CPropertyListener.prototype.propertyChanged = function(instance, propertyId) {
 * 
 * }
 *	</pre></blockquote>
 */
public interface IComponentInstancePropertyListener {
	
	/**
	 * A property value was changed. For compound properties, which may be nested
	 * at more than one level, only the top-level property name is reported.
	 * @param componentInstance the component instance containing the property
	 * @param propertyId the name of the property
	 */
	void propertyChanged(EObject componentInstance, Object propertyId);

}
