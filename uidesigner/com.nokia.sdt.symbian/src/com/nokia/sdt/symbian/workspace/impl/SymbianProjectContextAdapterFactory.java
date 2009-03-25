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
package com.nokia.sdt.symbian.workspace.impl;

import com.nokia.sdt.symbian.workspace.ISymbianProjectContext;
import com.nokia.sdt.workspace.IProjectContext;
import com.nokia.sdt.workspace.WorkspaceContext;

import org.eclipse.core.runtime.IAdapterFactory;

public class SymbianProjectContextAdapterFactory implements IAdapterFactory {
	
	private static Class adapterList[] = {ISymbianProjectContext.class};

	public Object getAdapter(Object adaptableObject, Class adapterType) {
		ISymbianProjectContext result = null;
		
		// assumes the caller has already done the usual instanceof
		// and IAdaptable.getAdapter calls
	
		IProjectContext pc = WorkspaceContext.getContext().discoverProjectContext(adaptableObject);
		if (pc != null) {
			result = (ISymbianProjectContext) pc.getAdapter(ISymbianProjectContext.class);
		}
		return result;
	}

	public Class[] getAdapterList() {
		return adapterList;
	}
}
