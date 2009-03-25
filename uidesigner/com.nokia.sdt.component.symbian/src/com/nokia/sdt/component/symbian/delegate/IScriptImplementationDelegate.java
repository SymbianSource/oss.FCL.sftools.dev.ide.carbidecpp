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


package com.nokia.sdt.component.symbian.delegate;

import com.nokia.sdt.component.symbian.scripting.WrappedInstance;

/**
 * This script interface is used to delegate implementations to other instances in the model
 *
 */
public interface IScriptImplementationDelegate {
	
	/**
	 * @return an array of wrapped instances to query for the adapter
	 */
	WrappedInstance[] getDelegates(WrappedInstance instance, String interfaceTypeName);

	/**
	 * @param instance WrappedInstance
	 * @return an array of fully qualified interface names
	 */
	String[] getDelegateInterfaceNames(WrappedInstance instance);
}
