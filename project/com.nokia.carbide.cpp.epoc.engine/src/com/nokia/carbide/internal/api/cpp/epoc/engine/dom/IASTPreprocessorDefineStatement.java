/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.internal.api.cpp.epoc.engine.dom;


/**
 * A #define statement
 *
 */
public interface IASTPreprocessorDefineStatement extends IASTPreprocessorStatement {
    /** Get the macro name (never null) */
    public IASTLiteralTextNode getMacroName();
    
    /** Set the macro name (may not be null) */
    public void setMacroName(IASTLiteralTextNode name);

    /** Get the macro arguments (may be null) */
    public IASTListNode<IASTLiteralTextNode> getMacroArgs();
    
    /** Set the macro arguments (may be null) */
    public void setMacroArgs(IASTListNode<IASTLiteralTextNode> args);

    /** Get the definition text of the macro (may be null) */
    public IASTPreprocessorTokenStream getMacroExpansion();
    
    /** Set the definition text of the macro (may be null) */
    public void setMacroExpansion(IASTPreprocessorTokenStream expansion);
}
