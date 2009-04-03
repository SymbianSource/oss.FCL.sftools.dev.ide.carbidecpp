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
package com.nokia.sdt.component.symbian.sourcemapping;

import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.emf.component.MapResourceElementType;
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpression;
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstResource;

import org.eclipse.ui.views.properties.IPropertySource;

/**
 * Map array elements which are wholly or partly instances.
 * This handles these patterns:
 * <p>
 *   &lt;mapArrayMember propertyId="." member="foo" /&gt;<br>
 *   <p>
 *   &lt;mapArrayMember propertyId="." member="foo" &gt;<br>
 *   		&lt;select ...&gt;
 *   		...
 *   		&lt;/select&gt;
 *   &lt;/mapArrayMember&gt;<br>
 *   <p> 
 *   &lt;mapArrayMember propertyId="." member="foo" &gt;<br>
 *   	&lt;mapResourceElement ... /&gt;<br>
 *   &lt;/mapArrayMember&gt; <br>
 *   <p>
 * 
 *
 */
public interface IArrayInstanceElementMapper {
	/**
	 * Tell if the elements of the array can be updated
	 * one-by-one or if they must all be recreated each time.
	 * This corresponds to the presence of an instanceIdentifyingMember
	 * attribute on the mapXXXElement facet. 
	 */
	public boolean canIdentifyInstances();
	
	/**
	 * Get the uniquifying key for this element, or null plus
	 * reported message if this cannot be determined
	 * This is only called if #canIdentifyInstances() returns true
	 * @param expr the existing array element
	 * @return the instance name
	 * @see #canIdentifyInstances()
	 */
	public String lookupInstance(IAstExpression expr);

	/**
	 * Create (expr==null) or update (expr!=null) an element in
	 * the array.
	 * @param expr existing value or null.  Null always passed
	 * when #canIdentifyInstances() returns false
	 * @param instance the current instance
	 * @param properties the instance's properties
	 * @return new element, or null for reported error
	 * @see #canIdentifyInstances()
	 */
	public IAstExpression createOrUpdate(IAstExpression expr,
			IComponentInstance instance,
			IPropertySource properties);

	/**
	 * If instances can be uniquely identified, update
	 * the mapping.
	 * @param mre
	 * @param rsrc
	 */
	public void registerMapping(MapResourceElementType mre, IAstResource rsrc);

}
