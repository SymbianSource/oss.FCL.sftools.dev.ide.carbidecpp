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

import com.nokia.carbide.internal.cpp.epoc.engine.parser.bldinf.BldInfParser;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.bsf.BSFParser;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.mmp.IMMPParserConfiguration;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.mmp.MMPParser;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.pkg.IPKGParserConfiguration;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.pkg.PKGParser;


public abstract class ParserFactory {
	/**
	 * Create a parser for the preprocessor view of a file.
	 * Everything that's not a directive is an uninterpreted
	 * token stream (IASTPreprocessorTokenStreamStatement).
	 * @return parser
	 */
	public static IDocumentParser createPreParser() {
		return new PreParser();
	}

	/**
	 * Create a preprocessor expression parser, which takes a token stream
	 * and expands it with provided macros and evaluates it into a final
	 * expression as much as possible.
	 * @return
	 */
	public static IPreprocessorExpressionParser createPreprocessorExpressionParser() {
		return new ASTParser();
	}

	/**
	 * Create an MMP parser, whose output contains only IASTMMPStatements.
	 * @param mmpConfig MMP-specific parser configuration
	 * @return
	 */
	public static ITranslationUnitParser createMMPParser(IMMPParserConfiguration mmpConfig) {
		return new MMPParser(mmpConfig);
	}

	/**
	 * Create an bld.inf parser, whose output contains only IASTBldInfStatements.
	 * @return
	 */
	public static ITranslationUnitParser createBldInfParser() {
		return new BldInfParser();
	}
	
	/**
	 * Create a BSF parser, whose output contains only IASTBSFStatements.
	 * @return
	 */
	public static IDocumentParser createBSFParser() {
		return new BSFParser();
	}

	/**
	 * Create a PKG parser, whose output contains only IASTBSFStatements.
	 * @return
	 */
	public static IDocumentParser createPKGParser(IPKGParserConfiguration pkgConfig) {
		return new PKGParser(pkgConfig);
	}
}
