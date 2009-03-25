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
import com.nokia.sdt.component.symbian.ComponentSystemPlugin;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IImplementationDelegate;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.util.Iterator;
import java.util.List;

/**
 * 
 *
 */
public class InstanceImplementationsAdapterFactory extends AdapterFactoryImpl {

	public InstanceImplementationsAdapterFactory() {
	}

	protected Adapter createAdapter(Notifier target, Object type) {
		// this is usually internal to adaptNew() and this implementation doesn't use it.
		Check.checkState(false); // should never get here
		return null;
	}

	public boolean isFactoryForType(Object type) {
        if (ComponentSystemPlugin.getImplementationTypes() == null)
            return false;
		return ComponentSystemPlugin.getImplementationTypes().containsType((Class) type);
	}
	
	private Adapter findAdapter(Notifier target, Class type, boolean associate) {
		Adapter adapter = null;
		IComponentInstance instance = (IComponentInstance) 
					EcoreUtil.getRegisteredAdapter((EObject) target, IComponentInstance.class);
		if (instance != null) {
			IComponent component = instance.getComponent();
			while (component != null) {
				IComponentImplementations implementations = 
					(IComponentImplementations) component.getAdapter(IComponentImplementations.class);
				String interfaceId = ImplementationsTypesRegistry.getInterfaceId(type);
				List associatedInterfaces = implementations.getAssociatedInterfaces(interfaceId);
				if (!associatedInterfaces.isEmpty()) {
					for (Iterator iter = associatedInterfaces.iterator(); iter.hasNext();) {
						String associatedInterfaceId = (String) iter.next();
						if (implementations.supportsInterface(associatedInterfaceId)) {
							Adapter temp = 
								implementations.getImplementationAdapter(associatedInterfaceId, (EObject) target);
							if (associate)
								associate(temp, target);
							if (associatedInterfaceId.equals(ImplementationsTypesRegistry.getInterfaceId(type))) {
								adapter = temp;
							}
						}
					}
					if (adapter != null)
						break;
				}
				component = component.getComponentBase();
			}
		}
		
		return adapter;
	}
	
	private boolean isApplicableInterfaceType(Class type, List<Class> types) {
		for (Class cls : types) {
			if (type.isAssignableFrom(cls))
				return true;
		}
		
		return false;
	}

	public Adapter adaptNew(Notifier target, Object type) {
		Adapter delegateAdapter = findAdapter(target, IImplementationDelegate.class, false);
		boolean doCacheAdapter = true;
		if (delegateAdapter instanceof IImplementationDelegate) {
			IImplementationDelegate delegate = (IImplementationDelegate) delegateAdapter;
			List<Class> delegateInterfaces = delegate.getDelegateInterfaces();
			if (isApplicableInterfaceType((Class) type, delegateInterfaces)) {
				doCacheAdapter = false; // we don't cache adapters that may be delegated
				List<EObject> delegates = ((IImplementationDelegate) delegateAdapter).getDelegates((Class) type);
				if (delegates != null) {
					for (EObject object : delegates) {
						Adapter a = findAdapter(object, (Class) type, false);
						if (a != null)
							return a;
					}
				}
			}
		}
		return findAdapter(target, (Class) type, doCacheAdapter);
	}
}
