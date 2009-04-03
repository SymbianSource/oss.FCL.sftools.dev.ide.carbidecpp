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
 * A token that came from a macro.<p>
 * For this, {@link #getParentLocation() the parent location } is the context of the macro expansion --
 *  i.e. another macro or a document 
 *
 */
public interface IMacroTokenLocation extends ITokenLocation {
	/** The macro providing the token */
	IASTPreprocessorDefineStatement getDefineStatement();
}
