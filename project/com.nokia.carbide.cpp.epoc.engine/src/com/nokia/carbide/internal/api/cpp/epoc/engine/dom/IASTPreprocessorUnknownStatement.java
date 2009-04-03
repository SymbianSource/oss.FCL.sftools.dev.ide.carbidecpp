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
 * An unknown directive.  This includes everything after the '#'.
 *
 */
public interface IASTPreprocessorUnknownStatement extends IASTPreprocessorStatement {
	/** Get the tokens making up the directive, excluding '#' */
	IASTPreprocessorTokenStream getDirective();

	/** Set the tokens making up the directive, excluding '#' */
	void setDirective(IASTPreprocessorTokenStream name);

}
