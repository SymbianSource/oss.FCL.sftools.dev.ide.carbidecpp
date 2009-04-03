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

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.adapter.IComponentImplementations;
import com.nokia.sdt.component.symbian.*;
import com.nokia.sdt.emf.component.*;
import com.nokia.sdt.scripting.IScriptObject;
import com.nokia.sdt.scripting.ScriptException;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Logging;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import java.util.*;

public class ComponentImplementationsAdapter implements IComponentImplementations {

	private IComponent component;
	private EStructuralFeature implementationsFeature;
	private Map codeTypeToImplFactory = null;
	private Map scriptTypeToImplFactory = null;
	
	public ComponentImplementationsAdapter(Plugin plugin, IComponent component, EStructuralFeature implementationsFeature) {
		Check.checkArg(component);
		this.component = component;
		this.implementationsFeature = implementationsFeature;
		codeTypeToImplFactory = new HashMap();
		scriptTypeToImplFactory = new HashMap();
	}

	private ImplementationType getImplementationTypeFromContainer(IFacetContainer fc, String interfaceId) {
		EObject container = fc.getEMFContainer();
		Object featureObj = container.eGet(implementationsFeature);
		if (featureObj instanceof ImplementationsType) {
			ImplementationsType implementationsType = (ImplementationsType) featureObj;
			for (Iterator implementationIter = implementationsType.getImplementation().iterator(); 
																	implementationIter.hasNext();) {
				ImplementationType implementationType = (ImplementationType) implementationIter.next();
				for (Iterator interfaceIter = implementationType.getInterface().iterator(); 
																	interfaceIter.hasNext();) {
					InterfaceType interfaceType = (InterfaceType) interfaceIter.next();
					if (interfaceType.getId().equals(interfaceId)) {
						return implementationType;
					}
				}
			}
		}
		
		return null;
	}
	
	public boolean supportsInterface(String interfaceId) {
		// assumes the string in the interface id attribute is what would
		// be returned from the interface class' getName() method
		return getImplementationTypeFromContainer((IFacetContainer) component, interfaceId) != null;
	}

	public List getAssociatedInterfaces(String interfaceId) {
		Check.checkArg(interfaceId);
		ImplementationType implementationType = getImplementationTypeFromContainer(
										(IFacetContainer) component, interfaceId);
		if (implementationType == null)
			return Collections.EMPTY_LIST;
		
		List list = new ArrayList();
		for (Iterator iter = implementationType.getInterface().iterator(); iter.hasNext();) {
			InterfaceType interfaceType = (InterfaceType) iter.next();
			list.add(interfaceType.getId());
		}
		
		return list;
	}

	public Adapter getImplementationAdapter(String interfaceId, EObject instance) {
		Adapter adapter = null;
		
		ImplementationType implementationType = getImplementationTypeFromContainer(
													(IFacetContainer) component, interfaceId);
		ScriptType scriptType = implementationType.getScript();
		CodeType codeType = implementationType.getCode();
		
		Check.checkContract((scriptType == null) != (codeType == null));
		if (scriptType != null)
			adapter = createAdapterFromScript(interfaceId, instance, scriptType);
		else // codeType != null
			adapter = createAdapterFromCode(interfaceId, instance, codeType);
		
		return adapter;
	}

	private Adapter createAdapterFromCode(String interfaceId, EObject instance, CodeType codeType) {
		Adapter adapter = null;
		
		CodeImplFactory implFactory = null;
		if (!codeTypeToImplFactory.containsKey(codeType)) {
			implFactory = new CodeImplFactory(((Component) component).getBundle(), codeType);
			codeTypeToImplFactory.put(codeType, implFactory);
		}
		else
			implFactory = (CodeImplFactory) codeTypeToImplFactory.get(codeType);
		
		Object impl = implFactory.getImpl(instance);
			
		Class adapterClass = 
			ComponentSystemPlugin.getImplementationTypes().getCodeAdapterClass(interfaceId);
		
		try {
			adapter = (Adapter) adapterClass.newInstance();
		} 
		catch (Throwable e) {
			ComponentSystemPlugin.log(e);
		}
		
		if (adapter instanceof ICodeImplAdapter) {
			((ICodeImplAdapter) adapter).init(impl, instance);
		} else {
			ComponentSystemPlugin.log(new AssertionError(interfaceId));
			adapter = null;
		}
		
		return adapter;
	}

	private Adapter createAdapterFromScript(String interfaceId, EObject instance, ScriptType scriptType) {
		Adapter adapter = null;

		ScriptImplFactory implFactory = null;
		if (!scriptTypeToImplFactory.containsKey(scriptType)) {
			try {
				implFactory = new ScriptImplFactory(component, scriptType, false);
                scriptTypeToImplFactory.put(scriptType, implFactory);
			} 
			catch (ScriptException e0) {
                ComponentSystemPlugin.log(e0);
                scriptTypeToImplFactory.put(scriptType, null);
			}
		}
		else
			implFactory = (ScriptImplFactory) scriptTypeToImplFactory.get(scriptType);
		
		if (implFactory == null)
			return null;
		
		IScriptObject scriptObject = null;
		try {
			scriptObject = implFactory.getScriptObject(instance);
		} 
		catch (ScriptException e1) {
            return null;
		}
		
		Class adapterClass = 
			ComponentSystemPlugin.getImplementationTypes().getScriptAdapterClass(interfaceId);
		if (adapterClass == null)
            return null;
        
		try {
			adapter = (Adapter) adapterClass.newInstance();
		} 
		catch (Throwable e) {
			Logging.log(ComponentSystemPlugin.getDefault(),
                    Logging.newStatus(ComponentSystemPlugin.getDefault(), e));
            return null;
		}
		
		Check.checkContract(adapter instanceof IScriptImplAdapter);
        try {
            ((IScriptImplAdapter) adapter).init(implFactory.getScriptContext(), scriptObject, instance, component);
        } catch (ScriptException e) {
            adapter = null;
        }
		
		return adapter;
	}

	public IComponent getComponent() {
		return component;
	}

}
