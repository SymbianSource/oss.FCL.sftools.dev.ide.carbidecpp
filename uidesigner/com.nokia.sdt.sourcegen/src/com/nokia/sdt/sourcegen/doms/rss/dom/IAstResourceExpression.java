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
 * A resource defined inline as an expression
 *
 * <pre>
 * RESOURCE TYPE r_foo {
 *  ...
 *  <b>
 *  inlineResource = ANOTHERTYPE {
 *      ...
 *  }
 *  </b>
 *  ...
 * }
 * </pre>
 * 
 * 
 *
 */
public interface IAstResourceExpression extends IAstExpression, IAstResource {

}
