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
package com.nokia.sdt.component;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;

	/** 
	 * Base class of exceptions thrown by the component system 
	 * 
	 * */
public class ComponentSystemException extends CoreException {
	
    public ComponentSystemException(IStatus status) {
        super(status);
    }
    private static final long serialVersionUID = 3617014139445131576L;  
}
