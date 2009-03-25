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
/**
 * 
 */
package com.nokia.sdt.series60.component;

import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IPropertyExtenders;
import com.nokia.sdt.datamodel.util.ModelUtils;

import org.eclipse.emf.ecore.EObject;

public class DefaultPropertyExtender implements IPropertyExtenders {
	private EObject instance;
	DefaultPropertyExtender(EObject componentInstance) {
		this.instance = componentInstance;
	}
	public EObject[] getPropertyExtenders(EObject targetInstance) {
		EObject[] result = null;
		IComponentInstance ci = ModelUtils.getComponentInstance(targetInstance);
		if (ci != null && ci.getParent() != null) {
			result = new EObject[1];
			result[0] = ci.getParent();
		}
		return result;
	}

	public String[] getExtensionSetNames(EObject targetInstance) {
		return null;
	}
	
	public EObject getEObject() {
		return instance;
	}
}