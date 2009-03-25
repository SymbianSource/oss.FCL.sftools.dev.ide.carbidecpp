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
package com.nokia.sdt.series60.component;

import com.nokia.sdt.component.adapter.IImplFactory;

import org.eclipse.emf.ecore.EObject;

	/**
	 * Default version of the IPropertyExtender implementation
	 * interface.
	 * This version adds the container to the list of instances
	 * that may add extension properties. It does not itself
	 * add extension properties to any other instances.
	 *
	 */
public class DefaultPropertyExtenderFactory implements IImplFactory {

	public Object getImpl(EObject componentInstance) {
		return new DefaultPropertyExtender(componentInstance);
	}
}
