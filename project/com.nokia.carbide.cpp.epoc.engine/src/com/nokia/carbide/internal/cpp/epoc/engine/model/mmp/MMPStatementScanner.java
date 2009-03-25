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

package com.nokia.carbide.internal.cpp.epoc.engine.model.mmp;

import com.nokia.carbide.cpp.epoc.engine.EpocEnginePlugin;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.EMMPStatement;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPAIFInfo;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.*;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPAifStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPFlagStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPListArgumentStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPOptionStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPProblemStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPSingleArgumentStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPStartBlockStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPUidStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IMMPSourcePathDependentContext;
import com.nokia.carbide.internal.cpp.epoc.engine.model.ModelConverter;
import com.nokia.carbide.internal.cpp.epoc.engine.model.SimpleArgList;
import com.nokia.carbide.internal.cpp.epoc.engine.model.StringListConverter;

import org.eclipse.core.runtime.IPath;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class scans one statement at a time, maintaining state
 * about what statements it has seen before.  MMP usually allows
 * a non-list statement to appear only once, and some statements
 * interact (SOURCEPATH, SOURCE; TARGETPATH, LANG, [SYSTEM]RESOURCE).   
 *
 */
public class MMPStatementScanner {

	private MMPView view;
	private IASTMMPSingleArgumentStatement currentSourcePathStmt;
	private IPath currentSourcePath;
	private Set<EMMPStatement> singleArgStmts;
	private Collection<IASTStatement> knownStmts;
	private boolean handledUid;
	private Collection<IPath> sourcePaths;

	public MMPStatementScanner(MMPView view) {
		this.view = view;
		this.currentSourcePath = view.defaultProjectRelativeSourcePath();
		this.currentSourcePathStmt = null;
		this.singleArgStmts = new HashSet<EMMPStatement>();
		this.knownStmts = view.getKnownStatements();
		this.sourcePaths = view.getSourcePaths();
		this.handledUid = false;
	}
	
	/**
	 * Scan SOURCEPATH statements to update the current base for sources.
	 */
	private void scanSourcePath(IASTMMPSingleArgumentStatement sourcePathExpr) {
		IPath sourcePath = view.fromMmpToProjectPath(sourcePathExpr.getArgument());
		sourcePaths.add(sourcePath);
		currentSourcePath = sourcePath;
		currentSourcePathStmt = sourcePathExpr;
	}

	/**
	 * Scan DEFFILE statement to get the IPath entry.
	 */
	private void scanDefFile(IASTMMPSingleArgumentStatement defFile) {
		if (view.getSingleArgumentSettings().get(EMMPStatement.DEFFILE) == null) {
			view.getSingleArgumentSettings().put(EMMPStatement.DEFFILE, defFile.getArgument().getValue());
			view.defFileBase = view.getCurrentDirectory();
		} else {
			knownStmts.remove(defFile);
		}
	}

	/**
	 * Scan a list using the given converter.
	 */
	private void scanList(IASTListArgumentStatement statement, List list,
			ModelConverter converter) {
		IASTListNode<IASTLiteralTextNode> elements = statement.getArguments();
		for (IASTLiteralTextNode element : elements) {
			Object model = converter.fromNode(element);
			if (model != null)
				list.add(model);
		}
	}
	
	/**
	 * Scan a string list.
	 */
	private void scanStringList(IASTListArgumentStatement statement,
			List<String> list) {
		scanList(statement, list, new StringListConverter(IASTLiteralTextNode.EStyle.PREPROCESSOR));
	}
	
	/**
	 * Scan a path list with the given statement type. 
	 * @param useSourcePath if set, the current SOURCEPATH influences
	 * lookup of sources
	 */
	private void scanPathList(IASTMMPListArgumentStatement statement,
			List<IPath> list, boolean useSourcePath) {
		if (useSourcePath) {
			setSourcePathContext(statement);
			SourcePathListConverter sourcePathListConverter = 
				new SourcePathListConverter(view, statement.getKeywordName());
			sourcePathListConverter.setCurrentSourcePath(currentSourcePath);
			scanList(statement, list, sourcePathListConverter);
		}
		else
			scanList(statement, list, new PathListConverter(view, null)); 
	}

	private void setSourcePathContext(IASTMMPStatement statement) {
		if (currentSourcePathStmt == null)
			statement.getSourcePathDependentContext().setSourcePathStatement(IMMPSourcePathDependentContext.DEFAULT_SOURCEPATH_STATEMENT);
		else
			statement.getSourcePathDependentContext().setSourcePathStatement(currentSourcePathStmt);
	}

	private void scanListArgumentStatement(IASTMMPListArgumentStatement list) {
		EMMPStatement stmt;
		try {
			stmt = EMMPStatement.valueOf(list.getKeywordName().toUpperCase());
		} catch (IllegalArgumentException e) {
			unhandled(list);
			return;
		}
		
		if (stmt == EMMPStatement.SOURCE) {
			//scanSource(list);
			scanPathList(list, view.getSources(), true);
		} else if (stmt == EMMPStatement.USERINCLUDE) {
			scanPathList(list, view.getUserIncludes(), false);
		} else if (stmt == EMMPStatement.SYSTEMINCLUDE) {
			scanPathList(list, view.getSystemIncludes(), false);
		} else if (stmt == EMMPStatement.RESOURCE) {
			scanPathList(list, view.getUserResources(), true);
		} else if (stmt == EMMPStatement.SYSTEMRESOURCE) {
			scanPathList(list, view.getSystemResources(), true);
		} else if (stmt == EMMPStatement.DOCUMENT) {
			scanPathList(list, view.getDocuments(), true);
		} else if (stmt == EMMPStatement.LANG) {
			scanList(list, view.getLanguages(), new LanguageListConverter());
		} else if (view.getListArgumentSettings().containsKey(stmt)) {
			scanStringList(list, view.getListArgumentSettings().get(stmt));
		} else {
			unhandled(list);
		}
	
	}

	private void scanFlagStatement(IASTMMPFlagStatement flagStmt) {
		EMMPStatement stmt;
		try {
			stmt = EMMPStatement.valueOf(flagStmt.getKeywordName().toUpperCase());
		} catch (IllegalArgumentException e) {
			unhandled(flagStmt);
			return;
		}

		if (!view.getFlags().contains(stmt)) {
			view.getFlags().add(stmt);
		} else {
			knownStmts.remove(stmt);
		}
	}

	private void scanSingleArgumentStatement(IASTMMPSingleArgumentStatement stmt) {
		EMMPStatement stmtType;
		try {
			stmtType = EMMPStatement.valueOf(stmt.getKeywordName().toUpperCase());
		} catch (IllegalArgumentException e) {
			unhandled(stmt);
			return;
		}
		
		if (!view.getSingleArgumentSettings().containsKey(stmtType)) {
			unhandled(stmt);
			return;
		}

		// take the first one only
		if (!singleArgStmts.contains(stmtType)) {
			view.getSingleArgumentSettings().put(stmtType, stmt.getArgument().getValue());
			singleArgStmts.add(stmtType);
		} else {
			knownStmts.remove(stmt);
		}
	}

	/**
	 * scan a START ... END block statement.
	 */
	private void scanBlockStatement(List list,
			Map modelToStmtMap,
			IASTMMPStartBlockStatement blockStmt, ModelConverter converter) {
		Object model = converter.fromNode(blockStmt);
		if (model == null) {
			EpocEnginePlugin.log(new IllegalArgumentException("Ignoring invalid block statement: " + blockStmt.getNewText())); //$NON-NLS-1$
		} else {
			list.add(model);
			modelToStmtMap.put(model, blockStmt);
		}
	}
	

	private void scanStartBlockStatement(IASTMMPStartBlockStatement blockStmt) {
		String blockType = blockStmt.getBlockType().getValue();
		if (MMPView.RESOURCE_KEYWORD.equalsIgnoreCase(blockType)) {
			setSourcePathContext(blockStmt);
			scanBlockStatement(view.getResourceBlocks(),
					view.resourceBlockToStatementMap,
					blockStmt, 
					new ResourceBlockListConverter(view, currentSourcePath));
		} else if (MMPView.BITMAP_KEYWORD.equalsIgnoreCase(blockType)) {
			scanBlockStatement(view.getBitmaps(),
					view.bitmapBlockToStatementMap,
					blockStmt, new BitmapBlockListConverter(view));
		} else {
			// start platform ... end
			if (view.isMacroDefined(blockType.toUpperCase())) {
				for (IASTMMPStatement stmt : blockStmt.getStatements()) {
					scanStatement(stmt);
				}
			}
		}
	}
	
	private void scanUidStatement(IASTMMPUidStatement uid) {
		if (!handledUid) {
			view.setUid2(uid.getUid2() != null ? uid.getUid2().getValue() : null);
			view.setUid3(uid.getUid3() != null ? uid.getUid3().getValue() : null);
			handledUid = true;
		} else {
			knownStmts.remove(uid);
		}
	}
	
	/**
	 * Add an AIF statement to the model.
	 *
	 */
	private void scanAifStatement(IASTMMPAifStatement aif) {
		AIFConverter converter = new AIFConverter(view); 
		IMMPAIFInfo aifInfo = converter.fromNode(aif);
		if (aifInfo != null) {
			view.getAifs().add(aifInfo);
			view.aifToStatementMap.put(aifInfo, aif);
		}
	}

	private void scanOptionStatement(IASTMMPOptionStatement option) {
		Map<String, String> map;
		if (EMMPStatement.OPTION.matches(option))
			map = view.getOptions();
		else if (EMMPStatement.LINKEROPTION.matches(option))
			map = view.getLinkerOptions();
		else if (EMMPStatement.OPTION_REPLACE.matches(option))
			map = view.getReplaceOptions();
		else {
			unhandled(option);
			return;
		}
		
		String compiler = option.getCompiler().getValue().toUpperCase();
		SimpleArgList args = new SimpleArgList("", option.getOptions(), ""); //$NON-NLS-1$ //$NON-NLS-2$
		String options = args.toString();
		String existing = map.get(compiler);
		if (existing != null)
			map.put(compiler, existing + " " + options);  //$NON-NLS-1$
		else
			map.put(compiler, options);
	}
	
	private void unhandled(IASTMMPStatement statement) {
		knownStmts.remove(statement);
		
		/*
		// temporary error logging code
		EpocEnginePlugin.log(new IllegalArgumentException(
				MessageFormat.format(Messages.getString("MMPStatementScanner.UnrecognizedStatementMessage"), //$NON-NLS-1$
						new Object[] { 
							view.getModel().getPath(),
							statement.getNewText() })));
		*/
	}
	
	public void scanStatement(IASTMMPStatement statement) {
		// get a valid base directory
		view.updateCurrentDirectory(statement);
		
		// initial guess; may be modified
		knownStmts.add(statement);
		
		if (statement instanceof IASTMMPAifStatement) {
			scanAifStatement((IASTMMPAifStatement) statement);
		}
		else if (statement instanceof IASTMMPFlagStatement) {
			scanFlagStatement((IASTMMPFlagStatement) statement);
		}
		else if (statement instanceof IASTMMPListArgumentStatement) {
			IASTMMPListArgumentStatement list = (IASTMMPListArgumentStatement) statement;
			scanListArgumentStatement(list);
		}
		else if (statement instanceof IASTMMPOptionStatement) {
			scanOptionStatement((IASTMMPOptionStatement) statement);
		}
		else if (statement instanceof IASTMMPSingleArgumentStatement) {
			IASTMMPSingleArgumentStatement singleArgStmt = (IASTMMPSingleArgumentStatement) statement;
			if (EMMPStatement.SOURCEPATH.matches(singleArgStmt)) {
				scanSourcePath(singleArgStmt);
			} else if (EMMPStatement.DEFFILE.matches(singleArgStmt)) {
				scanDefFile(singleArgStmt);
			} else {
				scanSingleArgumentStatement(singleArgStmt);
			}
		}
		else if (statement instanceof IASTMMPStartBlockStatement) {
			scanStartBlockStatement((IASTMMPStartBlockStatement) statement);
		} 
		else if (statement instanceof IASTMMPUidStatement) {
			scanUidStatement((IASTMMPUidStatement) statement);
		} 
		else if (statement instanceof IASTMMPProblemStatement) {
			unhandled(statement);
		}
		else {
			unhandled(statement);
		}
		
	}

}
