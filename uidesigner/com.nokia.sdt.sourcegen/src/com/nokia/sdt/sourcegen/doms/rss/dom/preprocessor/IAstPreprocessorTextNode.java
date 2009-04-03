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

package com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor;


/**
 * Raw text encountered by the preprocessor.  This is found
 * inside macro argument lists and in text outside of
 * preprocessor directives.  
 * <p>
 * <pre>
 * // stuff
 * #if FOO
 *    line1
 * #else 
 *    line2
 * #endif
 * </pre>
 * Here, "// stuff\n", "\tline1\n", and "\tline2\n" are
 * text nodes.  (Preprocessor directives implicitly include
 * the terminating newline.)
 * 
 * 
 *
 */
public interface IAstPreprocessorTextNode extends IAstPreprocessorNode {
    /** Get the text; never null */
    public String getText();
    
    /** Set the text; may not be null */
    public void setText(String text); 
}
