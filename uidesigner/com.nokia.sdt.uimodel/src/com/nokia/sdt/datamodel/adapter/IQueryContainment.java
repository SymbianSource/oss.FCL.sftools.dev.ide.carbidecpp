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


package com.nokia.sdt.datamodel.adapter;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.utils.StatusHolder;

/**
 * 	This interface is used to determine dynamic containment rules for a container.
 *  Example javascript protoype implementing this interface:
 *	<blockquote><pre>
 *
 * function CQueryContainment() {
 * }
 * 
 * CQueryContainment.prototype.canContainComponent = function(instance, otherComponent) {
 *  // note: returns IStatus with error for false, or null if true
 *  
 * }
 * 
 * CQueryContainment.prototype.canContainChild = function(instance, child) {
 *  // note: returns IStatus with error for false, or null if true
 * 
 * }
 * 
 * CQueryContainment.prototype.canRemoveChild = function(instance, child) {
 * 
 * }
 * 
 * CQueryContainment.prototype.isValidComponentInPalette = function(instance, otherComponent) {
 *
 * }
 *	</pre></blockquote>
 */
public interface IQueryContainment extends IModelAdapter {

	/**
	 * Returns true if the container can add a new instance of
	 * the given component. This method should check:
	 * 	- the static constraints of the component
	 *  - the static constraints of the container
	 *  - the dynamic constraints of the container.
	 * @param component the component to be instantiated
	 * @param statusHolder a holder for the status returned for failure or null, if none needed.
	 * @return true if a new instance could be added
	 */
	boolean canContainComponent(IComponent component, StatusHolder statusHolder);

	/**
	 * Returns true if the container can add a the child.
	 * This method should check:
	 *    - the static constraints of the child's component
	 *    - the dynamic constraints of the child
	 * 	  - the static constraints of the container
	 *    - the dynamic constraints of the container.
	 * @param child a <code>IComponentInstance</code>
	 * @param statusHolder a holder for the status returned for failure or null, if none needed.
	 * @return <code>true</code> if this container can contain child
	 */
	boolean canContainChild(IComponentInstance child, StatusHolder statusHolder);

	/**
	 * @param child a <code>IComponentInstance</code>
	 * @return <code>true</code> if this child can be removed from this container.
	 */
	boolean canRemoveChild(IComponentInstance child);
	
	/**
	 * Returns true if the container allows the given component to be in the palette
	 * NOTE: this may be called without a display model so it should not rely on any state
	 * supplied by display model objects. It should be implemented as a totally static test.
	 * @param component
	 * @return <code>true</code> if this container doesn't veto this component's existence in the palette
	 */
	boolean isValidComponentInPalette(IComponent component);
}