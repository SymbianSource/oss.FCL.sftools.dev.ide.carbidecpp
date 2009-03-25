/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.sdt.component.symbian.implementations;

import com.nokia.sdt.component.adapter.IImplFactory;
import com.nokia.sdt.component.symbian.ComponentSystemPlugin;
import com.nokia.sdt.emf.component.CodeType;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.osgi.framework.Bundle;

import java.util.HashMap;
import java.util.Map;

public class CodeImplFactory {
	private Class loadedClass = null;
	private IImplFactory implFactory = null;
	private Map instanceToImpl = null;

	public CodeImplFactory(Bundle bundle, CodeType codeType) {
		Check.checkContract(codeType != null);

		// first check to see if a plugin was specified by the codetype
		String pluginName = codeType.getPlugin();
		if (pluginName != null)
			bundle = Platform.getBundle(pluginName);
		
		// if not, and the component's bundle (passed in) is null,
		// use the ComponentSystemPlugin's bundle

        if (bundle == null)
        	bundle = ComponentSystemPlugin.getDefault().getBundle();
		
		try {
			loadedClass = bundle.loadClass(codeType.getClass_());
		} 
		catch (ClassNotFoundException e) {
			ComponentSystemPlugin.log(e);
			Check.failedArg(e);
		}
		
		implFactory = getCodeImplFactory();
		instanceToImpl = new HashMap();
	}
	
	private IImplFactory getCodeImplFactory() {
		IImplFactory implFactory = null;
		try {
			implFactory = (IImplFactory) loadedClass.newInstance();
		} 
		catch (Exception e) {
			ComponentSystemPlugin.log(e);
		}

		return implFactory;
	}
	
	public Object getImpl(EObject instance) {
		Object impl = null;
		if (!instanceToImpl.containsKey(instance)) {
			impl = implFactory.getImpl(instance);
			instanceToImpl.put(instance, impl);
		}
		else
			impl = instanceToImpl.get(instance);
		
		return impl;
	}
}
