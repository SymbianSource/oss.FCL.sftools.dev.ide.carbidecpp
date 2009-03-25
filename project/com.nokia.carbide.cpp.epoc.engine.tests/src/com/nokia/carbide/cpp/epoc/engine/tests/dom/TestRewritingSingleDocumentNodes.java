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
import com.nokia.carbide.internal.cpp.epoc.engine.dom.rewriter.DocumentNodeUpdater;


public class TestRewritingSingleDocumentNodes extends TestRewritingSingleDocumentBase {
	/**
	 * @param tu
	 * @param string
	 */
	protected void run_(IASTTranslationUnit tu, String ref) {
		IDocumentSourceRegion mainRegion = (IDocumentSourceRegion) tu.getSourceRegion();

		DocumentNodeUpdater handler = new DocumentNodeUpdater(null, mainRegion.getDocument());
		tu.rewrite(handler);
		
		assertEquals(ref, handler.toString());
	}

}
