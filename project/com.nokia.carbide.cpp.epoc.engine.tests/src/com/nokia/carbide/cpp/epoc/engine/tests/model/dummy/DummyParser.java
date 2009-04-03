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

package com.nokia.carbide.cpp.epoc.engine.tests.model.dummy;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ASTFactory;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTopLevelNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IDocumentSourceRegion;
import com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.IPreprocessorResults;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.BaseASTParser;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.ParserUtils;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;


public class DummyParser extends BaseASTParser {

	public DummyParser() {
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.parser.IParser#parse(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit, com.nokia.carbide.cpp.epoc.engine.preprocessor.IViewConfiguration)
	 */
	public IASTTranslationUnit parse(IPreprocessorResults preprocessorResults) {
		
		// NOTE: do not use this as an example for writing other
		// real models -- it does not even parse!  Issues like
		// mapping source between the TUs are overlooked.
		
		IASTTranslationUnit tu = preprocessorResults.getFilteredTranslationUnit();
		IASTTranslationUnit dummyTu = ASTFactory.createTranslationUnit();
		//dummyTu.setMainDocument(tu.getMainDocument());
		//dummyTu.setMainLocation(tu.getMainLocation());
		
		for (IASTTopLevelNode node : tu.getNodes()) {
			IASTDummyStatement stmt = new ASTDummyStatement(
					ASTFactory.createPreprocessorLiteralTextNode(node.getNewText().trim()));
			stmt.copySourceInfo(node);
			stmt.getText().copySourceInfo(node);
			// exclude final newline
			IDocumentSourceRegion tail = stmt.getText().getSourceRegion().getInclusiveTailRegion();
			IRegion region = tail.getRegion();
			tail.setRegion(new Region(region.getOffset(), region.getLength() - 1));
			dummyTu.getNodes().add(stmt);
			stmt.setSourceNodes(node.getSourceNodes());
			stmt.setId(node.getId());
		}
		ParserUtils.setSourceRangeForListNode(null, dummyTu.getNodes(), null);
		dummyTu.copySourceInfo(dummyTu.getNodes());
		finalizeTu(tu, dummyTu);
		

		return dummyTu;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.parser.IParser#hadErrors()
	 */
	public boolean hadErrors() {
		return false;
	}
}
