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

package com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bldinf;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode;

/**
 * The PRJ_EXTENSIONS statement.
 *
 */
public interface IASTBldInfPrjExtensionsBlockStatement extends
		IASTBldInfBlockStatement<IASTBldInfExtensionBlockStatement> {
	/** Get the keyword (PRJ_EXTENSIONS or PRJ_TESTEXTENSIONS) */
	IASTLiteralTextNode getKeyword();
	/** Set the keywod (PRJ_EXTENSIONS or PRJ_TESTEXTENSIONS) */
	void setKeyword(IASTLiteralTextNode keyword);

}
