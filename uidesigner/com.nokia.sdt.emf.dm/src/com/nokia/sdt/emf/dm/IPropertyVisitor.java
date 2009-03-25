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
package com.nokia.sdt.emf.dm;

import com.nokia.sdt.emf.dm.impl.EStringToIPropertyValueMapEntryImpl;

public interface IPropertyVisitor {
	
	/**
	 * Visit a single property value.
	 * The visit method cannot change a property value but cannot reset
	 * it, i.e. remove the entry. However, if the visit method sets the
	 * value to null the entry will be reset on its behalf.
	 * @return null to continue, anything else to stop
	 */
	Object visit(IPropertyContainer container, EStringToIPropertyValueMapEntryImpl entry);
}
