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
package com.nokia.sdt.datamodel.adapter;

import com.nokia.sdt.datamodel.util.ModelUtils;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

public abstract class PropertyVisitor {
	
	public interface Visitor {
		
		/**
		 * Called with a single property value, which may be
		 * a simple object, IPropertySource, or ISequencePropertySource.
		 * The return value is used to indicate if compound and array properties
		 * should be recursively visited.
		 * @return true to enter compound and array properties, false to skip
		 */
		boolean visit(IPropertyDescriptor pd, Object value);
		
		/** 
		 * Called after each property to check if visiting should continue.
		 * A null result means to continue. A non-null result stops visiting,
		 * returning this value from the top-level visit.
		 */
		Object getCompletionResult();
	}
	
	/**
	 * Visit the immediate properties of the object. In order
	 * to visit into compound or array properties the visitor
	 * must call visit recursively, they are not visited automatically.
	 * @return the non-null result returned by the visitor, if any, or null
	 * if all properties were visited.
	 */
	public static Object visit(EObject object, Visitor visitor) {
		Object result = null;
		IPropertySource ps = ModelUtils.getPropertySource(object);
		if (ps != null) {
			result = visit(ps, visitor);
		}
		return result;
	}
	
	/**
	 * Visit the immediate properties of the property source. In order
	 * to visit into compound or array properties the visitor
	 * must call visit recursively, they are not visited automatically.
	 * @return the non-null result returned by the visitor, if any, or null
	 * if all properties were visited.
	 */
	public static Object visit(IPropertySource propertySource, Visitor visitor) {
		Object result = null;
		IPropertyDescriptor[] pds = propertySource.getPropertyDescriptors();
		for (IPropertyDescriptor pd : pds) {
			Object pv = propertySource.getPropertyValue(pd.getId());
			boolean recurse = visitor.visit(pd, pv);
			result = visitor.getCompletionResult();
			if (result != null) {
				break;
			}
			if (recurse) {
				if (pv instanceof IPropertySource) {
					result = visit((IPropertySource)pv, visitor);
					if (result != null) {
						break;
					}
				}
			}
		}
		return result;
	}
}
