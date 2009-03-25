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
 * An individual enumeration value.
 * 
 * <pre>
 * enum foo {
 *      <b>aValue = 2;</b> 
 * }
 * </pre>
 * 
 * The initializer expression is everything to the right of the
 * equals sign.
 * 
 * 
 *
 */
public interface IAstEnumerator extends IAstNode, IAstNameHolder, IAstListElement {
    /** Get the name of the enumerator */
    public IAstName getName();
    
    /** Set the name of the enumerator */
    public void setName(IAstName name);
    
    /** Get the initializer for the enumerator (or null) */
    public IAstInitializerExpression getInitializerExpression();

    /** Set the initializer for the enumerator (or null) */
    public void setInitializerExpression(IAstInitializerExpression init);

    /** Get the owning enumeration */
    public IAstEnumDeclaration getEnumeration();
}
