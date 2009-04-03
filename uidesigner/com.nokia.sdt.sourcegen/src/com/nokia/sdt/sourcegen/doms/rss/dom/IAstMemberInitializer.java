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
 * An initializer for a member of a STRUCT.
 * Usually this is simple, e.g. for
 * <pre>
 *      foo = 3;
 * </pre>  
 * getMember() returns the "foo" IAstMemberDeclaration,<br>
 * getMemberExpression() returns an IAstIdExpression referring to 
 * IAstName("foo") of this declaration,<br>
 * and getExpression() returns an IAstInitializerExpression containing
 * IAstLiteralExpression(3).<br>
 * <p>
 * Another case is the array initialization:
 * <pre>
 *      foo = { 3, 4, 5 };
 * </pre>
 * getMember() returns "foo" again,<br>
 * getMemberExpression() returns an IAstIdExpression referring to
 * IAstName("foo"),<br>
 * and getInitializerExpression() returns an IAstInitializerExpression
 * containing an IAstExpressionList for the members of the array.
 * <p>
 * Finally, by-member array initialization:
 * <pre>
 *      foo[1] = 3;
 * </pre>
 * getMember() returns "foo" again,<br>
 * getMemberExpression() returns an IAstBinaryExpression(K_SUBSCRIPT)
 * with LHS an IAstIdExpression referring to IAstName("foo")
 * and RHS the IAstLiteralExpression(1),<br>
 * and getInitializerExpression() returns an IAstInitializerExpression
 * containing an IAstLiteralExpression(3).
 * 
 * 
 *
 */
public interface IAstMemberInitializer extends IAstInitializer {
    /** Get the base field being initialized (this does not include any array index) */
    public IAstMemberDeclaration getMember();
    
    /** Get the full member access expression (e.g. foo[3]) (never null) */
    public IAstExpression getMemberExpression();

    /** Set the full member access expression (e.g. foo[3]) (must not be null) */
    public void setMemberExpression(IAstExpression expr);
  
    /** Get the initializer expression (never null) */
    public IAstInitializerExpression getInitializerExpression();
    
    /** Set the initializer expression (must not be null) */
    public void setInitializerExpression(IAstInitializerExpression expr);

    /** Get the length limit (may be null) */
    public IAstExpression getLengthLimit();
    
    /** Set the length limit (may be null) */
    public void setLengthLimit(IAstExpression expr);
}
