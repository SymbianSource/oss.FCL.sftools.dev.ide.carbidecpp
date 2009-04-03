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
package com.nokia.sdt.component.symbian.scripting;

import com.nokia.sdt.scripting.IScriptContext;

/**
 * This interface wraps a call to script code using the
 * given context. Specifically, it enables the global
 * include() function to work properly.
 * 
 * 
 * @see com.nokia.sdt.component.symbian.scripting.ComponentScriptingManager
 */
public interface IScriptContextWrapper {
    public Object run(IScriptContext context) throws Exception;
}
