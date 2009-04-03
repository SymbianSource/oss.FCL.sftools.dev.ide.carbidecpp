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
package com.nokia.sdt.component.symbian.scripting.impl;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.adapter.IComponentScriptAdapter;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Plugin;

/**
 * This factory creates component script adapters.
 * 
 *
 */
public class ScriptAdapterFactory implements IAdapterFactory {
	
	private Class adapterList[] = { IComponentScriptAdapter.class };
	
	/**
	 */
	public ScriptAdapterFactory(Plugin plugin) {
	}

	public Object getAdapter(Object adaptableObject, Class adapterType) {
		Object result = null;
		if (adaptableObject instanceof IComponent &&
			IComponentScriptAdapter.class.equals(adapterType)) {
			result = new ScriptAdapter((IComponent) adaptableObject);
		}
		return result;
	}

	public Class[] getAdapterList() {
		return adapterList;
	}
}
