/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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

import org.eclipse.emf.ecore.EObject;

import java.util.List;

/**
 *  This interface is used to delegate implementations to other instances in the model.
 *  Example javascript protoype implementing this interface:
 *<blockquote><pre>
 *
 *function CImplementationDelegate() {
 *
 *}
 *
 *CImplementationDelegate.prototype.getDelegateInterfaceNames = function(instance) {
 *	// return array of fully qualified interface names
 *}
 *
 *CImplementationDelegate.prototype.getDelegates = function(instance, interfaceTypeName) {
 *	// check interfaceTypeName matches the one of interest
 *	// return array of instances e.g., instance.children
 *}
 *
 *</pre></blockquote>
 */
public interface IImplementationDelegate extends IModelAdapter {
	
	/**
	 * @return the collection of interfaces that may be delegated by this instance<br>
	 * This allows the framework to:
	 * <li>limit the adapters for which it will request delegates
	 * <li>not cache the adapters that may be delegated, so that adapter delegatation can be more dynamic 
	 */
	List<Class> getDelegateInterfaces();
	
	/**
	 * @param interfaceType the interface type of the implementation adapter
	 * @return an array of instances to query for the adapter
	 */
	List<EObject> getDelegates(Class interfaceType);
	
}

