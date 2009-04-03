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

package com.nokia.carbide.internal.cpp.epoc.engine.parser.bsf;

import com.nokia.carbide.cpp.epoc.engine.EpocEnginePlugin;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.*;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bsf.*;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTUtils;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.*;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Region;

import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is a parser for BSF. 
 *
 */
public class BSFParser implements IDocumentParser {
	/** Pattern ignoring space, matching a keyword, and allowing for possible options following a space. */
	/* Avoids consuming trailing space */
	private static final Pattern KEYWORD_AND_OPTIONS = Pattern.compile(
			"\\s*((?:\\w|_)+)\\s*(?:\\s+(.*?)(?>\\s+)?)?(\\s*)"); //$NON-NLS-1$
	
	private IPath path;

	private boolean hadErrors;

	private PositionTrackingReader reader;
	private int lineNumber;
	private int lineOffset;

	private IDocument document;
	
	public BSFParser() {
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.parser.bsf.IBSFParser#parse(org.eclipse.core.runtime.IPath, java.io.Reader)
	 */
	public IASTBSFTranslationUnit parse(IPath path, IDocument document) {
		this.document = document;
		this.reader = new PositionTrackingReader(new StringReader(document.get()));
		IASTListNode<IASTBSFStatement> stmts = ASTBSFFactory.createBSFStatementListNode();
		IASTBSFTranslationUnit tu = ASTBSFFactory.createBSFTranslationUnit(stmts);
		hadErrors = false;
		try {
			String line;
			this.path = path;
			this.lineOffset = reader.getOffset();
			this.lineNumber = reader.getLineNumber();
			while ((line = reader.readLine()) != null) {
				IASTBSFStatement stmt = parseStatement(line);
				if (stmt != null) {
					stmts.add(stmt);
				}
				this.lineOffset = reader.getOffset();
				this.lineNumber = reader.getLineNumber();
			}
		} catch (IOException e) {
			EpocEnginePlugin.log(e);
		}
		
		if (!stmts.isEmpty()) {
			ParserUtils.setSourceRangeForListNode(null, stmts, null);
		} else {
			stmts.setSourceRegion(ASTUtils.createDocumentSourceRegion(document, path, 
					new Region(0, 0)));
		}
		tu.copySourceInfo(stmts);
		
		tu.setDirtyTree(false);
		
		return tu;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.parser.IDocumentParser#hadErrors()
	 */
	public boolean hadErrors() {
		return hadErrors;
	}
	
	/**
	 * @param line
	 * @return
	 */
	private IASTBSFStatement parseStatement(String line) {
		if (line.trim().length() == 0) {
			return null;
		}

		IDocumentSourceRegion region = ASTUtils.createDocumentSourceRegion( 
				document,
				path,
    			new Region(lineOffset, reader.getOffset() - lineOffset));

		IASTBSFStatement stmt = null;
		
		if (line.startsWith("#")) { //$NON-NLS-1$
			stmt = ASTBSFFactory.createBSFCommentStatement(line);
			 ((IASTBSFCommentStatement) stmt).getTokenStream().setSourceRegion(region.copy());
		} else {
			Matcher matcher = KEYWORD_AND_OPTIONS.matcher(line);
			if (!matcher.matches()) {
				return ASTBSFFactory.createBSFProblemStatement(line,
						ASTFactory.createMessage(IMessage.ERROR,
								"BSFParser.UnknownStatement", //$NON-NLS-1$
								new Object[0], 
								getMessageLocation()));
			}
			
			String keyword = matcher.group(1).toUpperCase();
			String arguments = matcher.group(2);
	
			IASTLiteralTextNode keywordNode = ASTBSFFactory.createPreprocessorLiteralTextNode(keyword);
			ISourceRegion keywordRegion = ASTUtils.createDocumentSourceRegion( 
					document,
					path,
	    			new Region(lineOffset + matcher.start(1), keyword.length()));
			keywordNode.setSourceRegion(keywordRegion);
			
			if (arguments != null) {
				IASTLiteralTextNode argumentsNode = ASTBSFFactory.createPreprocessorLiteralTextNode(arguments);
				ISourceRegion argumentsRegion = ASTUtils.createDocumentSourceRegion( 
						document,
						path,
						new Region(lineOffset + matcher.start(2), arguments.length()));
				argumentsNode.setSourceRegion(argumentsRegion);
				
				stmt = ASTBSFFactory.createBSFArgumentStatement(keywordNode, argumentsNode);
			} else {
				stmt = ASTBSFFactory.createBSFFlagStatement(keywordNode);
			}
		}
		stmt.setSourceRegion(region);
		
		return stmt;
	}

	/**
	 * @return
	 */
	private MessageLocation getMessageLocation() {
		return new MessageLocation(path, lineNumber, 0);
	}

}
