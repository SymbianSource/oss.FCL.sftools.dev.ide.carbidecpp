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

package com.nokia.sdt.sourcegen.doms.rss.dom;

/**
 * This represents a "rls_string" statement
 * 
 * <pre>
 * rls_string STRING_my_string "foo"<0x0001>"longer foo"
 * </pre>
 * 
 * 
 *
 */
public interface IAstRlsString extends IAstTopLevelNode, IAstNameHolder {
    /** Get the identifier (never null) */
    IAstName getIdentifier();
    
    /** Set the identifier (cannot be null) */
    void setIdentifier(IAstName name);
    
    /** Get the string (never null, always K_STRING) */
    IAstLiteralExpression getString();
    
    /** Set the string (cannot be null, must be K_STRING) */
    void setString(IAstLiteralExpression string);

    /** Set the string to a constant */
    void setString(String string);
}
