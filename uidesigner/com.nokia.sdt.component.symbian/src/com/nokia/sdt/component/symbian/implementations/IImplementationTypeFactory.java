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


/**
 * 
 * 
 * This interface defines what a plugin must provide to allow it to extend components'
 * implementations elements. This is the interface that must be extended for an
 * implementation extension.
 *
 */
public interface IImplementationTypeFactory {
	
	/**
	 * @return an interface extending 
	 * <code>com.nokia.sdt.datamodel.adapter.IModelAdapter</code>
	 */
	Class getInterface();
	
	/**
	 * @return a class implementing 
	 * <code>com.nokia.sdt.component.symbian.implementations.ICodeImplAdapter</code>
	 * or <code>null</code> if no code implementation supported
	 */
	Class getCodeImplAdapterClass();
	
	/**
	 * @return a class implementing 
	 * <code>com.nokia.sdt.component.symbian.implementations.IScriptImplAdapter</code>
	 * or <code>null</code> if no script implementation supported
	 */
	Class getScriptImplAdapterClass();
}
