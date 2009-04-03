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

package com.nokia.carbide.internal.cpp.epoc.engine.parser.pkg;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.IDocument;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.IDocumentParser;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.generated.PKGParserCore;
import com.nokia.cpp.internal.api.utils.core.*;

/**
 * This is a parser for PKG.
 * 
 * 
 */
public class PKGParser implements IDocumentParser {
	private IPKGParserConfiguration config;
	protected boolean hadErrors;

	/**
	 * @param config
	 */
	public PKGParser(IPKGParserConfiguration config) {
		Check.checkArg(config);
		this.config = config;
	}

	// @Override
	public IASTTranslationUnit parse(IPath path, IDocument document) {
		PKGTokenManager tokenManager = new PKGTokenManager(config, document,
				path);
		PKGParserCore parser = new PKGParserCore(tokenManager);
		IASTTranslationUnit tu = parser.parse();
		tu.setMainDocument(document);
		tu.setMainLocation(path);

		/*
		 * // give every node an id int idx = 0; IASTTopLevelNode prev = null;
		 * for (IASTTopLevelNode node : tu.getNodes()) { node.setId(new
		 * Integer(idx++)); if (prev != null) { prev.setNext(node); } prev =
		 * node; }
		 */

		hadErrors = parser.hadErrors();

		tu.setDirtyTree(false);
		return tu;
		/*
		 * PKGTokenManager tokenManager = new PKGTokenManager(config);
		 * PKGParserCore parser = new PKGParserCore(tokenManager);
		 * IASTPKGTranslationUnit pkgTu = parser.parse();
		 * 
		 * hadErrors = parser.hadErrors() || pkgTu == null;
		 *  // slaw //
		 * finalizeTu(preprocessorResults.getFilteredTranslationUnit(), pkgTu);
		 * return pkgTu;
		 */
	}

	// @Override
	public boolean hadErrors() {
		return hadErrors;
	}

}
