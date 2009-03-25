/*
* Copyright (c) 2006-2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.internal.cpp.epoc.engine.parser;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTopLevelNode;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.generated.ParseException;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.generated.TokenManager;

/**
 * This extends the generated ASTParser to handle specific languages.
 *
 */
public interface IExtensionParser {
	IASTTopLevelNode parseTopLevelNode(TokenManager tokenManager) throws ParseException;
}
