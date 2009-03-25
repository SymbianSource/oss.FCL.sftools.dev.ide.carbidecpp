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
package com.nokia.sdt.component.symbian;

import com.nokia.sdt.component.*;
import com.nokia.sdt.component.symbian.actionFilter.ActionFilterAdapterFactory;
import com.nokia.sdt.component.symbian.componentAttachment.ComponentAttachmentNotifier;
import com.nokia.sdt.component.symbian.implementations.InstanceImplementationsAdapterFactory;
import com.nokia.sdt.component.symbian.properties.ComponentInstanceInitializer;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.IStatus;

import java.util.*;


public class ComponentSet implements IComponentSet {

	private ComponentProvider provider;
	private HashMap<String, Component> components = new HashMap();
	private HashMap<String, ITypeDescriptor> typeMap = new HashMap();
	private Map persistenceProperties;

	
	ComponentSet(ComponentProvider provider) {
		Check.checkArg(provider);
		this.provider = provider;
	}
	
	public IComponentProvider getProvider() {
		return provider;
	}
	
	void setPersistenceProperties(Map properties) {
		persistenceProperties = properties;
	}
		
	public void addComponent(Component component) {
		component.setComponentSet(this);
        String id = component.getEMFComponent().getQualifiedName();
        if (components.get(id) != null)
            ComponentSystemPlugin.log(Logging.newStatus(
                    ComponentSystemPlugin.getDefault(),
                    IStatus.ERROR,
                    Messages.getString("ComponentSet.DuplicateComponent") + id)); //$NON-NLS-1$
		components.put(id, component);
	}
	
	public void addTypeDescriptor(String qualifiedName, ITypeDescriptor td) {
		td.setComponentSet(this);
        if (typeMap.get(qualifiedName) != null)
            ComponentSystemPlugin.log(Logging.newStatus(
                    ComponentSystemPlugin.getDefault(),
                    IStatus.ERROR,
                    Messages.getString("ComponentSet.DuplicateType") + qualifiedName)); //$NON-NLS-1$
		typeMap.put(qualifiedName, td);
	}

	public IComponent lookupComponent(String id) {
		IComponent component = (IComponent) components.get(id);
		return component;
	}
	public ITypeDescriptor lookupTypeDescriptor(String id) {
		ITypeDescriptor result = (ITypeDescriptor) typeMap.get(id);
		return result;
	}

	public Iterator iterator() {
		return Collections.unmodifiableCollection(components.values()).iterator();
	}

	public int numComponents() {
		return components.size();
	}

	public int numTypeDescriptors() {
		return typeMap.size();
	}

	public void initializeDataModel(IDesignerDataModel dataModel) {
		dataModel.addAdapterFactory(new InstanceImplementationsAdapterFactory());
		dataModel.addAdapterFactory(new ActionFilterAdapterFactory());
		
		// The ComponentInstanceInitializer listens for added objects and performs
		// any necessary one-time initialization on them.
		@SuppressWarnings("unused") 
		ComponentInstanceInitializer initializer = new ComponentInstanceInitializer(dataModel);

		@SuppressWarnings("unused") 
		ComponentAttachmentNotifier componentAttachmentNotifier = new ComponentAttachmentNotifier(dataModel);
	}

	public Map getPropertiesForPersistence() {
		return persistenceProperties;
	}

	public boolean arePropertiesCompatible(Map properties) {
		boolean result = false;
		if (persistenceProperties != null && properties != null) {
			result = persistenceProperties.equals(properties);
		}
		return result;
	}

	public IStatus validate() {
		StatusBuilder builder = new StatusBuilder(ComponentSystemPlugin.getDefault());
	
		// TODO add more tests, this is just a start
		validateBaseComponents(builder);
	
		String message = Messages.getString("ComponentSet.0"); //$NON-NLS-1$
		return builder.createStatus(message, null);
	}
	
	private void validateBaseComponents(StatusBuilder builder) {
		// Remove any components with missing bases and report a diagnostic
		for (Iterator iter = components.entrySet().iterator(); iter.hasNext();) {
			Map.Entry entry = (Map.Entry) iter.next();
			String componentID = (String) entry.getKey();
			Component component = (Component) entry.getValue();
			if (component.getBaseComponent() == null) {
				String baseID = component.getEMFComponent().getBaseComponent();
				if (baseID != null) {
					iter.remove();
					String fmt = Messages.getString("ComponentSet.1"); //$NON-NLS-1$
					Object params[] = {baseID, componentID};
					builder.add(IStatus.ERROR, fmt, params);
				}
			}
		}
	}
}
