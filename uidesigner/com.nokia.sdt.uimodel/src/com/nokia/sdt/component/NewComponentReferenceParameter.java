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
package com.nokia.sdt.component;

/**
 * The object is used in conjunction with component
 * reference properties and the ISetValueCommandExtender
 * interface.
 * 
 * When a component reference property uses the "creationKeys"
 * attribute to enable creation of new instances, instances
 * of NewComponentReferenceMarker are passed to the
 * ISetValueCommandExtender in order to signal the
 * type of new object to create.
 * 
 * The NewComponentReferenceParameter is accessed through
 * the SetValueCommand which is passed as a parameter to
 * ISetValueCommandExtender.getExtendedCommand.
 *
 */
public class NewComponentReferenceParameter {

	private String creationKey;
	
	public NewComponentReferenceParameter(String creationKey) {
		this.creationKey = creationKey;
	}
	
	/**
	 * Retrieve the key that indicates what type of object
	 * to create. The string is opaque to the component system
	 * and is meaningful only to the ISetValueCommandExtender
	 * implementation.
	 */
	public String getCreationKey() {
		return creationKey;
	}

	@Override
	public String toString() {
		return creationKey;
	}
}
