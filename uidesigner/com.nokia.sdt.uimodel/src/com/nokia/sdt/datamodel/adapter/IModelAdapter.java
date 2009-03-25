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
	 * Base interface for adapters allowing navigation from the adapter
	 * interface back to the EObject. This may be used to access
	 * additional adapters.
	 */
public interface IModelAdapter {

	EObject	getEObject();
}
