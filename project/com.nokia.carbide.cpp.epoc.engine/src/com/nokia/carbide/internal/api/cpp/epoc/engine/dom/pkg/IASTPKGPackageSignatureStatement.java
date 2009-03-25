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

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode;

public interface IASTPKGPackageSignatureStatement extends IASTPKGStatement {
	public IASTLiteralTextNode getKey();

	public void setKey(IASTLiteralTextNode sourcePath);

	public IASTLiteralTextNode getCert();

	public void setCert(IASTLiteralTextNode mimeType);

}
