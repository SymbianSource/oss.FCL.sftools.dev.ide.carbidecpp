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
 * Declaration of a member of a STRUCT 
 * 
 * 
 *
 */
public interface IAstMemberDeclaration extends IAstDeclaration, IAstNameHolder {
    public static final IAstMemberDeclaration[] EMPTY_ARRAY = new IAstMemberDeclaration[0];
    
    /**
     * Get the type of the member
     * @return IAstDeclaration (never null)
     */
    public IAstSimpleDeclaration getMemberType();

    /** Set the type of the member
     * 
     * @param decl an IAstDeclaration (must not be null)
     */ 
    public void setMemberType(IAstSimpleDeclaration decl);
    
    /** Get the name of the member
     * 
     * @return name (never null)
     */
    public IAstName getMemberName();

    /** Set the name of the member
     * 
     * @param name (must not be null)
     */ 
    public void setMemberName(IAstName name);

    /** 
     * Get the default initializer expression for the member, or null for none 
     * 
     * @return IAstInitializerExpression, or null
     */
    public IAstInitializerExpression getInitializerExpression();
    
    /** 
     * Set the default initializer expression for the member, or null for none 
     * 
     * @param expr IAstInitializerExpression, or null
     */
    public void setInitializerExpression(IAstInitializerExpression expr);
    
    /** Get the array declarator
     * 
     * @return IAstArrayDeclarator, or null if this is not an array
     */
    public IAstArrayDeclarator getArrayDeclarator();

    /** Set the array declarator
     * 
     * @param decl IAstArrayDeclarator, or null if this is not an array
     */
    public void setArrayDeclarator(IAstArrayDeclarator decl);

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
    
    /** Get the length limit (text member types only)
     * @return IAstExpression, or null when no limit 
     */
    public IAstExpression getLengthLimit();

    /** Set the length limit (text member types only)
     * @param expr IAstExpression, or null for no limit 
     */
    public void setLengthLimit(IAstExpression expr);

    /** Tells whether the length limit N is specified (text member types only) 
     * in the BUF&lt;N&gt; format or the BUF member(N) format
     * @return true: BUF&lt;N&gt;, false: BUF member(N)
     */ 
    public boolean hasTemplateStyle();

    /** Tell whether the length limit N is specified (text member types only)
     * in the BUF&lt;N&gt; format or the BUF member(N) format
     * @param templateStyle true: BUF&lt;N&gt;, false: BUF member(N)
     */ 
    public void setTemplateStyle(boolean templateStyle);

    /**
     * Find the STRUCT this member is in
     */
    public IAstStructDeclaration getStructType();
    
}
