/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.internal.api.cpp.epoc.engine.dom.pkg;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode;


public interface IASTPKGInstallFileStatement extends
		IASTPKGLocalizedStatementBase {
	IASTLiteralTextNode getTargetFile();

	void setTargetFile(IASTLiteralTextNode targetFile);

	IASTListNode<IASTLiteralTextNode> getOptions();

	void setOptions(IASTListNode<IASTLiteralTextNode> options);

	/**
	 * Get the status of either using language variant style with {} or not
	 * clear up ambiguous situation when only one language is presented
	 */
	boolean getLanguageDependentSyntaxStatus();

}
