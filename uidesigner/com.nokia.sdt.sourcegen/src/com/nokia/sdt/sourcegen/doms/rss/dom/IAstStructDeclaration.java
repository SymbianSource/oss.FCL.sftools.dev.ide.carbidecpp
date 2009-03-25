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
 * Declaration of a STRUCT type 
 * 
 * 
 *
 */
public interface IAstStructDeclaration extends IAstCompositeTypeSpecifier, IAstNameHolder, IAstTopLevelNode {
    /** Get the struct name */
    public IAstName getStructName();
    
    /** Set the struct name */
    public void setStructName(IAstName name);

    /** Get the length prefix declarator
     * 
     * @return IAstLengthPrefixDeclarator, or null if this is not length-prefixed
     */
    public IAstLengthPrefixDeclarator getLengthPrefixDeclarator();

    /** Set the length prefix declarator
     * 
     * @param decl IAstLengthPrefixDeclarator, or null if this is not length-prefixed
     */
    public void setLengthPrefixDeclarator(IAstLengthPrefixDeclarator decl);

}
