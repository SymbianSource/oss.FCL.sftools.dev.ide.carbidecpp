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

import com.nokia.carbide.cpp.epoc.engine.model.mmp.EMMPLanguage;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.EMMPStatement;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPAIFInfo;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPBitmap;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPResource;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.*;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.ASTMMPFactory;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPAifStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPFlagStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPListArgumentStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPOptionStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPSingleArgumentStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPStartBlockStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPUidStatement;
import com.nokia.carbide.internal.cpp.epoc.engine.model.*;
import com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.IConditionalBlock;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.IPath;

import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * This class updates the MMP by updatening one statement at a time, 
 * maintaining state about what statements it has handled.  This
 * is a destructive operation because it changes the view as
 * it updates the TU.   
 *
 */
public class MMPStatementUpdater extends BaseStatementUpdater {

	private MMPView mmpView;
	private IPath currentSourcePath;
	/** Map of Keyword+Compiler to last option */
	private Map<Pair<String, String>, IASTMMPOptionStatement> lastOptionStmts;
	private Map<IMMPAIFInfo, IASTMMPAifStatement> aifToStatementMap; 
	private Map<IMMPResource, IASTMMPStartBlockStatement> resourceToStatementMap; 
	private Map<IMMPBitmap, IASTMMPStartBlockStatement> bitmapToStatementMap; 
	private Map<IASTMMPAifStatement, IMMPAIFInfo> statementToAifMap; 
	private Map<IASTMMPStartBlockStatement, IMMPResource> statementToResourceMap; 
	private Map<IASTMMPStartBlockStatement, IMMPBitmap> statementToBitmapMap; 
	private ListIterator<IPath> sourceIter;
	private ListIterator<IPath> userIncludeIter;
	private ListIterator<IPath> systemIncludeIter;
	private ListIterator<IPath> userResourceIter;
	private ListIterator<IPath> systemResourceIter;
	private ListIterator<IPath> documentIter;
	private ListIterator<EMMPLanguage> langIter;
	private Map<EMMPStatement, ListIterator<String>> listArgIters;
	private Map<EMMPStatement, IASTListArgumentStatement> lastListStmts;
	
	public MMPStatementUpdater(MMPView view, List<IViewModification> modifications, List<IMessage> messages) {
		super(view, modifications, messages);
		this.mmpView = view;
		this.currentSourcePath = view.defaultProjectRelativeSourcePath();
		this.lastOptionStmts = new LinkedHashMap<Pair<String,String>, IASTMMPOptionStatement>();
		this.listArgIters = new LinkedHashMap<EMMPStatement, ListIterator<String>>();
		this.lastListStmts = new LinkedHashMap<EMMPStatement, IASTListArgumentStatement>();
		
		this.statementToAifMap = new IdentityHashMap<IASTMMPAifStatement, IMMPAIFInfo>();
		this.statementToResourceMap = new IdentityHashMap<IASTMMPStartBlockStatement, IMMPResource>();
		this.statementToBitmapMap = new IdentityHashMap<IASTMMPStartBlockStatement, IMMPBitmap>();
		
		sourceIter = view.getSources().listIterator();
		userIncludeIter = view.getUserIncludes().listIterator();
		systemIncludeIter = view.getSystemIncludes().listIterator();
		userResourceIter = view.getUserResources().listIterator();
		systemResourceIter = view.getSystemResources().listIterator();
		documentIter = view.getDocuments().listIterator();
		langIter = view.getLanguages().listIterator();
		
		for (Map.Entry<EMMPStatement, List<String>> entry : view.getListArgumentSettings().entrySet()) {
			if (entry.getValue() != null)
				listArgIters.put(entry.getKey(), entry.getValue().listIterator());
		}

	}
	
	protected void scanStatements() {
		// Make an ordered map of model objects -> existing statements.
		// The ordering in the map will be used to drive the reordering modification
		// later on.  So if the model refers to new objects, store a null entry for those
		// until we later fix them up.
		resourceToStatementMap = scanStructuredBlocks(
				mmpView.resourceBlockToStatementMap, statementToResourceMap, 
				mmpView.getResourceBlocks(), IASTMMPStartBlockStatement.class,
				new ResourceBlockListConverter(mmpView, null));
		bitmapToStatementMap = scanStructuredBlocks(
				mmpView.bitmapBlockToStatementMap, statementToBitmapMap, 
				mmpView.getBitmaps(), IASTMMPStartBlockStatement.class,
				new BitmapBlockListConverter(mmpView));
		aifToStatementMap = scanStructuredBlocks(mmpView.aifToStatementMap,
				statementToAifMap, mmpView.getAifs(), IASTMMPAifStatement.class,
				new AIFConverter(mmpView));

	}
	
	private void updateFlagStatement(IASTMMPFlagStatement flagStmt) {
		EMMPStatement stmt;
		try {
			stmt = EMMPStatement.valueOf(flagStmt.getKeywordName().toUpperCase());
		} catch (IllegalArgumentException e) {
			return;
		}
	
		// accounted for?
		if (mmpView.getFlags().contains(stmt)) {
			changeStatement(flagStmt);
			mmpView.getFlags().remove(stmt);
			return;
		}
		
		// get rid of the turned-off flag
		deleteStatement(flagStmt);
	}

	/**
	 * Update SOURCEPATH, by tracking its current value in order to
	 * correctly convert SOURCE/RESOURCE/SYSTEMRESOURCE/START RESOURCE/DOCUMENT
	 * statements back to MMP form.  Remove any previous SOURCEPATH that is
	 * no longer used. 
	 */
	private void updateSourcePath(IASTMMPSingleArgumentStatement sourcePath) {
		changeStatement(sourcePath);
		currentSourcePath = mmpView.fromMmpToProjectPath(sourcePath.getArgument());
	}

	/**
	 * Update the first DEFFILE statement.
	 */
	private void updateDefFile(IASTMMPSingleArgumentStatement defFile) {
		String value = mmpView.getSingleArgumentSettings().get(EMMPStatement.DEFFILE);
		// already handled?
		if (value == null) {
			deleteStatement(defFile);
			mmpView.getSingleArgumentSettings().remove(EMMPStatement.DEFFILE);
			return;
		}
		
		changeStatement(defFile);
		
		// no change?
		if (value.equals(defFile.getArgument().getValue())) {
			mmpView.getSingleArgumentSettings().remove(EMMPStatement.DEFFILE);
			return;
		}
		
		// update value and mark handled
		defFile.getArgument().setValue(value);
		mmpView.getSingleArgumentSettings().remove(EMMPStatement.DEFFILE);
	}

	private void updateSingleArgumentStatement(IASTMMPSingleArgumentStatement stmt) {
		EMMPStatement stmtType;
		try {
			stmtType = EMMPStatement.valueOf(stmt.getKeywordName().toUpperCase());
		} catch (IllegalArgumentException e) {
			return;
		}

		// check for a change
		String value = mmpView.getSingleArgumentSettings().get(stmtType);
		if (value == null || value.trim().length() == 0) {
			deleteStatement(stmt);
			mmpView.getSingleArgumentSettings().put(stmtType, null);
			return;
		}
		
		changeStatement(stmt);
		
		if (stmt.getArgument().getValue().equals(value)) {
			mmpView.getSingleArgumentSettings().put(stmtType, null);
			return;
		}
		
		// update value and mark handled
		stmt.getArgument().setValue(value);
		mmpView.getSingleArgumentSettings().put(stmtType, null);
	}

	private void updateUidStatement(IASTMMPUidStatement uid) {
		// same?
		if (equalLiteralNode(uid.getUid2(), mmpView.getUid2())
			&& equalLiteralNode(uid.getUid3(), mmpView.getUid3())) {
			mmpView.setUid2(null);
			mmpView.setUid3(null);
			return;
		}

		// Remove if unset
		if (mmpView.getUid2() == null && mmpView.getUid3() == null) {
			deleteStatement(uid);
			return;
		}
			
		changeStatement(uid);
		
		// update
		if (mmpView.getUid2() != null)
			uid.setUid2(ASTFactory.createPreprocessorLiteralTextNode(mmpView.getUid2()));
		else if (mmpView.getUid3() != null)
			uid.setUid2(ASTFactory.createPreprocessorLiteralTextNode("0")); //$NON-NLS-1$
		else
			uid.setUid2(null);
		if (mmpView.getUid3() != null)
			uid.setUid3(ASTFactory.createPreprocessorLiteralTextNode(mmpView.getUid3()));
		else
			uid.setUid3(null);
		
		mmpView.setUid2(null);
		mmpView.setUid3(null);
		
	}

	private void updateOptionStatement(IASTMMPOptionStatement option) {
		Map<String, String> map;
		if (EMMPStatement.OPTION.matches(option))
			map = mmpView.getOptions();
		else if (EMMPStatement.LINKEROPTION.matches(option))
			map = mmpView.getLinkerOptions();
		else if (EMMPStatement.OPTION_REPLACE.matches(option))
			map = mmpView.getReplaceOptions();
		else {
			Check.checkState(false);
			return;
		}
		
		String keyword = option.getKeywordName().toUpperCase();
		StringBuilder optionString = new StringBuilder();
		for (IASTLiteralTextNode node : option.getOptions()) {
			optionString.append(node.getStringValue());
			optionString.append(' ');
		}
		String currentOption = optionString.toString().trim();
		String compiler = option.getCompiler().getValue().toUpperCase();
		String modelOption = map.get(compiler);

		if (modelOption == null) {
			// no options anymore
			deleteStatement(option);
			return;
		}
		
		if (modelOption.trim().equals(currentOption.trim())) {
			// no change
			map.remove(compiler);
			lastOptionStmts.put(new Pair(keyword, compiler), option);
			return;
		}

		if (modelOption.startsWith(currentOption)) {
			// assume the existing statement is untouched and the
			// next one has the rest or any changes
			String remainingOptions = modelOption.substring(currentOption.length()).trim();
			if (remainingOptions.length() > 0) {
				map.put(compiler, remainingOptions);
			}
			else
				map.remove(compiler);
			lastOptionStmts.put(new Pair(keyword, compiler), option);
		} else {
			// old DOM no longer applies -- add later
			deleteStatement(option);
			lastOptionStmts.remove(new Pair(keyword, compiler));
		}

	}

	/**
	 * Update an AIF statement from the model.
	 */
	private void updateAifStatement(IASTMMPAifStatement aif) {
		applyStructuredItemChange(mmpView.getAifs(),
				aif,
				new AIFConverter(mmpView),
				aifToStatementMap,
				statementToAifMap);
	}

	
	/**
	 * update a START ... END block statement.
	 */
	private <ModelType, ElementType extends IASTMMPStatement>
	void updateBlockStatement(Collection<ModelType> modelList, 
			IASTMMPStartBlockStatement blockStmt,
			StructuredItemStatementListConverter<ElementType, ModelType> converter,
			Map<ModelType, ElementType> modelToStatementMap,
			Map<ElementType, ModelType> statementToModelMap) {
		applyStructuredItemChange(modelList, 
				blockStmt, 
				converter,
				modelToStatementMap,
				statementToModelMap);
	}

	private void updateStartBlockStatement(IASTMMPStartBlockStatement blockStmt) {
		String blockType = blockStmt.getBlockType().getValue();
		if (MMPView.RESOURCE_KEYWORD.equalsIgnoreCase(blockType)) {
			updateBlockStatement(mmpView.getResourceBlocks(), blockStmt, 
					new ResourceBlockListConverter(mmpView, currentSourcePath),
					resourceToStatementMap, statementToResourceMap);
		} else if (MMPView.BITMAP_KEYWORD.equalsIgnoreCase(blockType)) {
			updateBlockStatement(mmpView.getBitmaps(), blockStmt, new BitmapBlockListConverter(mmpView),
					bitmapToStatementMap, statementToBitmapMap);
		} else {
			// start platform ... end
			if (mmpView.isMacroDefined(blockType.toUpperCase())) {
				ListIterator<? extends IASTTopLevelNode> oldStatementIterator = statementIterator;
				statementIterator = (ListIterator) blockStmt.getStatements().listIterator();
				try {
					while (statementIterator.hasNext()) {
						IASTTopLevelNode tln = statementIterator.next();
						if (tln instanceof IASTMMPStatement)
							updateStatement((IASTMMPStatement) tln);
					}
				} finally {
					statementIterator = oldStatementIterator;
				}
				if (blockStmt.isDirtyTree()) {
					changeStatement(blockStmt);
				}
			}
		}
	}

	/**
	 * Update a single list argument statement. This handles removing obsolete
	 * elements, inserting in place if possible, and honoring the dictum to add
	 * new elements outside conditional blocks.
	 * <p>
	 * The AstListIterator implementation should handle changes with respect
	 * to intervening context statements (e.g. SOURCEPATH).
	 * @param stmt
	 *            the list statement
	 * @param converter
	 *            the converter to and from high level objects and AST nodes
	 * @param modelIterator
	 *            high-level object list
	 */
	private <ElementType extends IASTLiteralTextNode, ModelType> void updateMMPListStatement(
			IASTListArgumentStatement stmt,
			List<ModelType> modelList,
			ListIterator<ModelType> modelIter,
			EMMPStatement stmtType,
			ListStatementListConverter<ElementType, ModelType> converter) {

		if (modelIter != null) {
			lastListStmts.put(EMMPStatement.valueOf(stmt.getKeywordName().toUpperCase()), stmt);
		}
		
		updateListStatement((IASTListHolder<ElementType>) stmt, modelList, modelIter, converter);
		
		if (stmt.getList().isEmpty()) {
			lastListStmts.remove(stmtType);
		}
	}
	
	/**
	 * update a string list.
	 */
	private void updateStringListStatement(IASTListArgumentStatement statement,
			List<String> list,
			final EMMPStatement stmtType,
			ListIterator<String> listIterator) { 
		updateMMPListStatement(statement, list, listIterator, stmtType,
				new ListArgumentStatementListAdapter<String>(
						stmtType.toString(),
						new StringListConverter(IASTLiteralTextNode.EStyle.PREPROCESSOR)));
	}
	
	static class ListArgumentStatementListAdapter<ModelType> implements ListStatementListConverter<IASTLiteralTextNode, ModelType> {

		private String keyword;
		private final ModelConverter<IASTLiteralTextNode, ModelType> converter;

		public ListArgumentStatementListAdapter(String keyword,
				ModelConverter<IASTLiteralTextNode, ModelType> converter) {
			this.keyword = keyword;
			this.converter = converter;
		}

		public IASTListHolder<IASTLiteralTextNode> createNewListStatement() {
			return ASTMMPFactory.createMMPListArgumentStatement(keyword);
		}

		public boolean allowEmptyStatements() {
			return false;
		}

		public boolean canAddToStatement(ModelType model) {
			return true;
		}

		public boolean changeRequiresNewContext(ModelType existing,
				ModelType newElement) {
			return false;
		}

		public IASTStatement createContextStatement(ModelType model) {
			return null;
		}

		public ModelType fromNode(IASTLiteralTextNode node) {
			return converter.fromNode(node);
		}

		public IASTLiteralTextNode toNode(ModelType elementObj) {
			return converter.toNode(elementObj);
		}

		public Pair<IASTNode, IASTNode> getInsertAnchors() {
			return null;
		}
		
		public void associateContextStatement(IASTStatement stmt,
				IASTStatement contextStmt) {
		}
		
	}
	/**
	 * update a path list with the given statement type. 
	 * @param useSourcePath if set, the current SOURCEPATH influences
	 * lookup of sources
	 */
	private void updatePathListStatement(IASTMMPListArgumentStatement statement,
			List<IPath> list,
			ListIterator<IPath> listIterator,
			EMMPStatement stmtType,
			boolean useSourcePath) {
		if (useSourcePath) {
			SourcePathListConverter sourcePathListConverter = new SourcePathListConverter(mmpView, stmtType.toString());
			sourcePathListConverter.setCurrentSourcePath(currentSourcePath);
			updateMMPListStatement(statement, list, listIterator, stmtType, sourcePathListConverter);
		}
		else
			updateMMPListStatement(statement, list, listIterator, stmtType,
					new ListArgumentStatementListAdapter(stmtType.toString(),
							new PathListConverter(mmpView, null))); 
	}

	private void updateListArgumentStatement(IASTMMPListArgumentStatement list) {
		EMMPStatement stmt;
		try {
			stmt = EMMPStatement.valueOf(list.getKeywordName().toUpperCase());
		} catch (IllegalArgumentException e) {
			return;
		}
		
		if (stmt == EMMPStatement.SOURCE) {
			//updateSource(list);
			updatePathListStatement(list, mmpView.getSources(), sourceIter, stmt, true);
		} else if (stmt == EMMPStatement.USERINCLUDE) {
			updatePathListStatement(list, mmpView.getUserIncludes(), userIncludeIter, stmt, false);
		} else if (stmt == EMMPStatement.SYSTEMINCLUDE) {
			updatePathListStatement(list, mmpView.getSystemIncludes(), systemIncludeIter, stmt, false);
		} else if (stmt == EMMPStatement.RESOURCE) {
			updatePathListStatement(list, mmpView.getUserResources(), userResourceIter, stmt, true);
		} else if (stmt == EMMPStatement.SYSTEMRESOURCE) {
			updatePathListStatement(list, mmpView.getSystemResources(), systemResourceIter, stmt, true);
		} else if (stmt == EMMPStatement.DOCUMENT) {
			updatePathListStatement(list, mmpView.getDocuments(), documentIter, stmt, true);
		} else if (stmt == EMMPStatement.LANG) {
			updateMMPListStatement(list, mmpView.getLanguages(), langIter, stmt,
					new ListArgumentStatementListAdapter(
							stmt.toString(),
							new LanguageListConverter()));
		} else if (mmpView.getListArgumentSettings().containsKey(stmt)) {
			updateStringListStatement(list, mmpView.getListArgumentSettings().get(stmt), stmt, listArgIters.get(stmt));
		} else {
			// user deleted entry, appearently
			deleteStatement(list);
		}
	
	}

	protected void doUpdateStatement(IASTStatement statement) {
		// special cases first; these may derive from more generic types
		if (statement instanceof IASTMMPAifStatement) {
			updateAifStatement((IASTMMPAifStatement) statement);
		}
		else if (statement instanceof IASTMMPUidStatement) {
			updateUidStatement((IASTMMPUidStatement) statement);
		}
		else if (statement instanceof IASTMMPOptionStatement) {
			updateOptionStatement((IASTMMPOptionStatement) statement);
		}
		else if (statement instanceof IASTMMPStartBlockStatement) {
			updateStartBlockStatement((IASTMMPStartBlockStatement) statement);
		} 
		else if (statement instanceof IASTMMPFlagStatement) {
			updateFlagStatement((IASTMMPFlagStatement) statement);
		}
		else if (statement instanceof IASTMMPSingleArgumentStatement) {
			IASTMMPSingleArgumentStatement singleArgStmt = (IASTMMPSingleArgumentStatement) statement;
			if (EMMPStatement.SOURCEPATH.matches(singleArgStmt)) {
				updateSourcePath(singleArgStmt);
			} else if (EMMPStatement.DEFFILE.matches(singleArgStmt)) {
				updateDefFile(singleArgStmt);
			} else {
				updateSingleArgumentStatement(singleArgStmt);
			}
		}
		else if (statement instanceof IASTMMPListArgumentStatement) {
			IASTMMPListArgumentStatement list = (IASTMMPListArgumentStatement) statement;
			updateListArgumentStatement(list);
		}
		else {
			// ignore
		}
	}

	private void applyNewFlagArgumentStatements() {
		for (Iterator iter = mmpView.getFlags().iterator(); iter.hasNext();) {
			// make new entry
			EMMPStatement stmtType = (EMMPStatement) iter.next();
			IASTMMPFlagStatement stmt = ASTMMPFactory.createMMPFlagStatement(stmtType.toString());
			addStatement(stmt);
			iter.remove();
		}
	}
	
	private void applyNewSingleArgumentStatements() {
		for (Iterator iter = mmpView.getSingleArgumentSettings().entrySet().iterator(); iter.hasNext();) {
			Map.Entry<EMMPStatement, String> entry = (Map.Entry<EMMPStatement, String>) iter.next();
			EMMPStatement stmtType = entry.getKey();
			String value = entry.getValue();
			if (value == null)
				continue;
			
			// make new entry
			IASTMMPSingleArgumentStatement newStmt =
				ASTMMPFactory.createMMPSingleArgumentStatement(stmtType.toString(), value);
			addStatement(newStmt);
			
			iter.remove();
		}
		
		// other single-arg stmts: not SOURCEPATH as it is handled in-line
		String defFile = mmpView.getSingleArgumentSettings().get(EMMPStatement.DEFFILE);
		if (defFile != null) {
			IASTMMPSingleArgumentStatement newStmt =
				ASTMMPFactory.createMMPSingleArgumentStatement(
						EMMPStatement.DEFFILE.toString(), 
						defFile);
			addStatement(newStmt);
			
			mmpView.getSingleArgumentSettings().remove(EMMPStatement.DEFFILE);
		}
	}

	private void applyNewUidStatement() {
		if (mmpView.getUid2() != null || mmpView.getUid3() != null) {
			addStatement(ASTMMPFactory.createMMPUidStatement(mmpView.getUid2(), mmpView.getUid3()));
			mmpView.setUid2(null);
			mmpView.setUid3(null);
		}
	}
	
	private void applyNewOptionStatement(String keyword, Map<String, String> map) {
		// remaining options go into new statements
		for (Iterator<Map.Entry<String, String>> iter = map.entrySet().iterator(); iter.hasNext(); ) {
			Map.Entry<String, String> entry = iter.next();
			IASTMMPOptionStatement option;
			String compiler = entry.getKey();
			
			String[] optionStrings = reparseOptionString(entry.getValue());
			
			option = lastOptionStmts.get(new Pair(keyword, compiler));
			if (option != null) {
				// modify last statement
				for (String optionString : optionStrings) {
					option.getOptions().add(ASTMMPFactory.createPreprocessorLiteralTextNode(optionString));
				}
				lastOptionStmts.remove(new Pair(keyword, compiler));
				changeStatement(option);
			} else {
				// add new statement
				option = ASTMMPFactory.createMMPOptionStatement(
						keyword, compiler, optionStrings);
				addStatement(option);
			}
			iter.remove();
		}
		
	}
		
	/**
	 * In our model, the OPTION/etc string is a single command line,
	 * with quoted subparts. Reparse that string to extract tokens
	 * and quoted regions.
	 * @param value
	 * @return
	 */
	private String[] reparseOptionString(String value) {
		SimpleArgList args = new SimpleArgList(value);
		return args.getArgv().toArray(new String[args.getArgv().size()]);
	}

	private void applyNewOptionStatements() {
		applyNewOptionStatement(EMMPStatement.OPTION.toString(),
				mmpView.getOptions());
		applyNewOptionStatement(EMMPStatement.OPTION_REPLACE.toString(),
				mmpView.getReplaceOptions());
		applyNewOptionStatement(EMMPStatement.LINKEROPTION.toString(),
				mmpView.getLinkerOptions());
	}
	
	private void applyNewAifStatements() {
		applyNewStructuredItemStatements(mmpView.getAifs(), 
				new AIFConverter(mmpView), 
				aifToStatementMap);
	}
	
	private void applyNewStartBlockStatements() {
		applyNewStructuredItemStatements(mmpView.getResourceBlocks(), 
				new ResourceBlockListConverter(mmpView, currentSourcePath),
				resourceToStatementMap);
		applyNewStructuredItemStatements(mmpView.getBitmaps(), 
				new BitmapBlockListConverter(mmpView),
				bitmapToStatementMap);
	}
	
	private <ElementType extends IASTLiteralTextNode, ModelType>
	void applyNewListArgumentStatement(ListIterator<ModelType> iter, 
			IASTListArgumentStatement lastStmt,
			EMMPStatement stmtType,
			ListStatementListConverter<ElementType, ModelType> converter) {
		if (!iter.hasNext())
			return;
		
		boolean changed = false;
		boolean isNew = false;
		while (iter.hasNext()) {
			ModelType model = iter.next();

			if (lastStmt == null
					|| mmpView.isLangNodeInConditionalBlock(lastStmt)
					|| !converter.canAddToStatement(model)) {
				// add context
				IASTStatement context = converter.createContextStatement(model);
				if (context != null) {
					addStatement(context);
				}
				
				if (changed && !isNew && lastStmt != null) {
					// may have updated nodes already
					if (!changedStatement(lastStmt))
						changeStatement(lastStmt);
				}
				
				// make new statement
				lastStmt = ASTMMPFactory.createMMPListArgumentStatement(stmtType.toString());
				converter.associateContextStatement(lastStmt, context);

				//if (lastStmt.getSourcePathDependentContext() != null) {
				//	lastStmt.getSourcePathDependentContext().setSourcePathStatement((IASTMMPSingleArgumentStatement) context);
				//}
				addStatement(lastStmt);
				changed = false;
				isNew = true;
			}

			ElementType el = converter.toNode(model);
			if (el != null) {
				lastStmt.getList().add(el);
				changed = true;
			}
			iter.remove();
		}
		
		if (changed && !isNew) {
			// may have updated nodes already
			if (!changedStatement(lastStmt))
				changeStatement(lastStmt);
		}
	}
	
	private void applyNewListArgumentStatements() {
		PathListConverter pathConverter = new PathListConverter(mmpView, null);
		SourcePathListConverter sourcePathConverter = new SourcePathListConverter(mmpView, null);
		sourcePathConverter.setCurrentSourcePath(currentSourcePath);
		sourcePathConverter.setStatementName(EMMPStatement.SOURCE.toString());
		applyNewListArgumentStatement(sourceIter,
				lastListStmts.get(EMMPStatement.SOURCE),
				EMMPStatement.SOURCE,
				sourcePathConverter);
		sourcePathConverter.setStatementName(EMMPStatement.USERINCLUDE.toString());
		applyNewListArgumentStatement(userIncludeIter,
				lastListStmts.get(EMMPStatement.USERINCLUDE),
				EMMPStatement.USERINCLUDE,
				new ListArgumentStatementListAdapter(EMMPStatement.USERINCLUDE.toString(),
						pathConverter));
		sourcePathConverter.setStatementName(EMMPStatement.SYSTEMINCLUDE.toString());
		applyNewListArgumentStatement(systemIncludeIter,
				lastListStmts.get(EMMPStatement.SYSTEMINCLUDE),
				EMMPStatement.SYSTEMINCLUDE,
				new ListArgumentStatementListAdapter(
						EMMPStatement.SYSTEMINCLUDE.toString(),
						pathConverter));
		sourcePathConverter.setStatementName(EMMPStatement.RESOURCE.toString());
		applyNewListArgumentStatement(userResourceIter,
				lastListStmts.get(EMMPStatement.RESOURCE),
				EMMPStatement.RESOURCE,
				sourcePathConverter);
		sourcePathConverter.setStatementName(EMMPStatement.SYSTEMRESOURCE.toString());
		applyNewListArgumentStatement(systemResourceIter,
				lastListStmts.get(EMMPStatement.SYSTEMRESOURCE),
				EMMPStatement.SYSTEMRESOURCE,
				sourcePathConverter);
		sourcePathConverter.setStatementName(EMMPStatement.DOCUMENT.toString());
		applyNewListArgumentStatement(documentIter,
				lastListStmts.get(EMMPStatement.DOCUMENT),
				EMMPStatement.DOCUMENT,
				sourcePathConverter);
		applyNewListArgumentStatement(langIter,
				lastListStmts.get(EMMPStatement.LANG),
				EMMPStatement.LANG,
				new ListArgumentStatementListAdapter(
						EMMPStatement.LANG.toString(),
						new LanguageListConverter()));
		for (Map.Entry<EMMPStatement, ListIterator<String>> entry : listArgIters.entrySet()) {
			applyNewListArgumentStatement(entry.getValue(),
					lastListStmts.get(entry.getKey()),
					entry.getKey(),
					new ListArgumentStatementListAdapter(
							entry.getKey().toString(),
							new StringListConverter(IASTLiteralTextNode.EStyle.PREPROCESSOR)));
		}
		
	}
	
	/**
	 * Add new statements to the MMP.  The previous calls to
	 * #updateStatement() have already updated the view to delete
	 * view model elements corresponding to previously existing nodes in the TU,
	 * so anything left in the view is new.  Once finished, everything
	 * in the view should be reverted to empty. 
	 */
	protected void applyNewStatements() {
		mmpView.updateCurrentDirectory(mmpView.getModel().getPath());

		applyNewFlagArgumentStatements();
		applyNewSingleArgumentStatements();
		applyNewUidStatement();
		applyNewOptionStatements();
		applyNewAifStatements();
		applyNewStartBlockStatements();
		
		applyNewListArgumentStatements();
	}
	
	private boolean statementUsesSourcePath(IASTMMPStatement statement) {
		if (statement.getKeywordName() == null)
			return false;
		
		if (statement.getKeywordName().matches("(?i)SOURCE|RESOURCE|SYSTEMRESOURCE|DOCUMENT")) {//$NON-NLS-1$
			return true;
		}
		if (statement instanceof IASTMMPStartBlockStatement) {
			return "RESOURCE".equalsIgnoreCase(((IASTMMPStartBlockStatement) statement).getBlockType().getValue()); //$NON-NLS-1$
		}
		return false;
	}

	/**
	 * Remove stray SOURCEPATH statements
	 *
	 */
	protected void cleanupSourcePaths() {
		IASTMMPStatement lastSourcePath = null;
		IConditionalBlock lastSourcePathBlock = null;
		boolean usedSourcePath = true;
		
		IASTTranslationUnit tu = mmpView.getFilteredTranslationUnit();
		IASTTopLevelNode[] nodes = (IASTTopLevelNode[]) tu.getNodes().toArray(new IASTTopLevelNode[tu.getNodes().size()]);
		for (IASTTopLevelNode node : nodes) {
			if (!(node instanceof IASTMMPStatement))
				continue;
			IASTMMPStatement stmt = (IASTMMPStatement) node;
			IConditionalBlock block = mmpView.getLangNodeConditionalBlock(stmt);
			if (EMMPStatement.SOURCEPATH.matches(stmt)) {
				if (!usedSourcePath
				&&  (lastSourcePathBlock == block || (lastSourcePathBlock != null && lastSourcePathBlock.contains(block)))) {
					deleteStatement(lastSourcePath);
				}
				lastSourcePath = stmt;
				lastSourcePathBlock = block;
				usedSourcePath = false;
			}
			else if (statementUsesSourcePath(stmt)) {
				usedSourcePath = true;
			}
		}
		
		if (lastSourcePath != null && !usedSourcePath) {
			deleteStatement(lastSourcePath);
		}
	}
	
	protected void fixupOrdering() {
		applyReordering(resourceToStatementMap, mmpView.resourceBlockToStatementMap, new ResourceBlockListConverter(mmpView, null));
		applyReordering(bitmapToStatementMap, mmpView.bitmapBlockToStatementMap, new BitmapBlockListConverter(mmpView));
		applyReordering(aifToStatementMap, mmpView.aifToStatementMap, new AIFConverter(mmpView));
	}
	
}
