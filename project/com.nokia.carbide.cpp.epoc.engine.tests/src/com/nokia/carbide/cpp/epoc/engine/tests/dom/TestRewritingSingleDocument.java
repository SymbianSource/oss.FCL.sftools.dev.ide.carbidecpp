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

package com.nokia.carbide.cpp.epoc.engine.tests.dom;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IDocumentSourceRegion;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.rewriter.DocumentUpdater;


public class TestRewritingSingleDocument extends TestRewritingSingleDocumentBase {

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.tests.dom.TestRewritingBase#run(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit, java.lang.String)
	 */
	@Override
	protected void run_(IASTTranslationUnit tu, String refText) {
		IDocumentSourceRegion mainRegion = (IDocumentSourceRegion) tu.getSourceRegion();
		DocumentUpdater.updateDocuments(null, tu);
		assertEquals(refText, mainRegion.getDocument().get());
	}
	
}
