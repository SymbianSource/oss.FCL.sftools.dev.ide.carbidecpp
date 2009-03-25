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
package com.nokia.sdt.emf.dm.util;

import com.nokia.sdt.emf.dm.IPropertyValue;

	/**
	 * Utility for dispatching base on the runtime
	 * type of a property value. Using this helps
	 * with maintainance in the event new types are added.
	 */
public abstract class PropertyValueSwitch {
	
	protected abstract Object handleStringValue(IPropertyValue pv);
	
	protected abstract Object handleCompoundValue(IPropertyValue pv);
	
	protected abstract Object handleSequenceValue(IPropertyValue pv);
	
	protected Object handleNoValue(IPropertyValue pv) {
		throw new IllegalStateException();
	}

	public Object doSwitch(IPropertyValue pv) {
		Object result;
		if (pv.hasStringValue()) {
			result = handleStringValue(pv);
		}
		else if (pv.hasCompoundValue()) {
			result = handleCompoundValue(pv);
		}
		else if (pv.hasSequenceValue()) {
			result = handleSequenceValue(pv);
		}
		else if (pv.getValue() == null) {
			result = handleNoValue(pv);
		}
		else {
			// unhandle new property type
			throw new IllegalStateException();
		}
		return result;
	}
}
