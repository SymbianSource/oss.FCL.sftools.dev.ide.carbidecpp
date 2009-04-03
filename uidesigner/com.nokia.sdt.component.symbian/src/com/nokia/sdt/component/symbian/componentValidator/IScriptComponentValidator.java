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
package com.nokia.sdt.component.symbian.componentValidator;

import com.nokia.sdt.component.symbian.scripting.WrappedInstance;
import com.nokia.sdt.displaymodel.ILookAndFeel;

import org.eclipse.emf.ecore.EObject;

public interface IScriptComponentValidator {
	
	/**
	 * @see com.nokia.sdt.datamodel.adapter.IComponentValidator#validate(EObject)
	 * @return single IModelMessage or array or collection of IModelMessage
	 */
	Object validate(WrappedInstance wrappedInstance, ILookAndFeel laf);

	String queryPropertyChange(WrappedInstance wrappedInstance,
			String propertyPath, Object newValue, ILookAndFeel laf);

}
