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
package com.nokia.sdt.component.symbian.attributes;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.adapter.IAttributes;
import com.nokia.sdt.component.symbian.IFacetContainer;
import com.nokia.sdt.emf.component.AttributeType;
import com.nokia.sdt.emf.component.AttributesType;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Logging;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import java.text.MessageFormat;
import java.util.Iterator;

/**
 * 
 */
public class AttributeAdapter implements IAttributes {

	private Plugin plugin;
	private IComponent component;
	private EStructuralFeature attributesFeature;
	
		/**
		 * Initialize the adapter with the list of attributes.
		 * Elements are of type AttributeType
		 * @param attributesFeature
		 */
	AttributeAdapter(Plugin plugin, IComponent component, 
					 EStructuralFeature attributesFeature) {
		//Check.checkArg(plugin); // EJS: not needed
		Check.checkArg(component);
		this.plugin = plugin;
		this.component = component;
		this.attributesFeature = attributesFeature;
	}
	
	private EList getAttributeListFromContainer(IFacetContainer fc) {
		EList result = null;
		EObject container = fc.getEMFContainer();
		Object featureObj = container.eGet(attributesFeature);
		if (featureObj instanceof AttributesType) {
			AttributesType attrsObj = (AttributesType) featureObj;
			result = attrsObj.getAttribute();
		}
		return result;
	}
	
	private String getAttribute(IFacetContainer fc, String key) {
		String result = null;
		EList attributes = getAttributeListFromContainer(fc);
		if (attributes != null) {
			for (Iterator iter = attributes.iterator(); iter.hasNext();) {
				AttributeType attr = (AttributeType) iter.next();
				if (key.equals(attr.getKey())) {
					result = attr.getValue();
					if (result == null)
						result = ""; //$NON-NLS-1$
					break;
				}
			}
		}
		
		if (result == null) {
			for (Iterator iter = fc.getAdditionalFacetContainers(); 
				 iter.hasNext() && result == null;) {
				IFacetContainer nextFC = (IFacetContainer) iter.next();
				result = getAttribute(nextFC, key);
			}
		}

		return result;
	}
	
	public String getAttribute(String key) {
		if (key == null) return null;
		IFacetContainer fc = (IFacetContainer) component;
		String result = getAttribute(fc, key);
		if (result == null) {
			for (Iterator iter = fc.getAdditionalFacetContainers(); 
				 iter.hasNext() && result == null;) {
				fc = (IFacetContainer) iter.next();
				result = getAttribute(fc, key);
			}
		}
		return result;
	}

	public boolean isAttributeDefined(String key) {
		return getAttribute(key) != null;
	}

	public int getIntegerAttribute(String key, int defaultValue) {
		int result = defaultValue;
		String value = getAttribute(key);
		if (value != null) {
			try {
				result = Integer.parseInt(value);
			}
			catch (NumberFormatException x) {
				Object args[] = {component.getId(), key, value};
				String msg = MessageFormat.format(Messages.getString("badIntAttribute"), args); //$NON-NLS-1$
				Logging.log(plugin, Logging.newStatus(plugin, IStatus.ERROR, msg));
			}
		}
		return result;
	}

	public boolean getBooleanAttribute(String key, boolean defaultValue) {
		boolean result = defaultValue;
		String value = getAttribute(key);
		if (value != null) {
			result = Boolean.valueOf(value).booleanValue();
		}
		return result;
	}

	public IComponent getComponent() {
		return component;
	}
}
