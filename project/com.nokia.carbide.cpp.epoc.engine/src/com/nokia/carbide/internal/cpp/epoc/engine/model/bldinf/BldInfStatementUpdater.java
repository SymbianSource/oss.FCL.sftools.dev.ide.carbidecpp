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

package com.nokia.carbide.internal.cpp.epoc.engine.model.bldinf;

import com.nokia.carbide.cpp.epoc.engine.model.bldinf.*;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.*;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bldinf.*;
import com.nokia.carbide.internal.cpp.epoc.engine.model.*;
import com.nokia.cpp.internal.api.utils.core.*;

import java.util.*;

/**
 * This class updates the bld.inf by updatening one statement at a time, 
 * maintaining state about what statements it has handled.  This
 * is a destructive operation because it changes the view as
 * it updates the TU.   
 *
 */
public class BldInfStatementUpdater extends BaseStatementUpdater {

	private BldInfView bldInfView;
	private Map<IMakMakeReference, IASTBldInfMakMakeStatement> makToStatementMap; 
	private Map<IMakMakeReference, IASTBldInfMakMakeStatement> testMakToStatementMap; 
	private Map<IExport, IASTBldInfExportStatement> exportToStatementMap;
	private Map<IExport, IASTBldInfExportStatement> testExportToStatementMap; 
	private Map<IExtension, IASTBldInfExtensionBlockStatement> extensionToStatementMap; 
	private Map<IExtension, IASTBldInfExtensionBlockStatement> testExtensionToStatementMap; 
	
	private Map<IASTBldInfMakMakeStatement, IMakMakeReference> statementToMakMap; 
	private Map<IASTBldInfMakMakeStatement, IMakMakeReference> statementToTestMakMap; 
	private Map<IASTBldInfExportStatement, IExport> statementToExportMap; 
	private Map<IASTBldInfExportStatement, IExport> statementToTestExportMap; 
	private Map<IASTBldInfExtensionBlockStatement, IExtension> statementToExtensionsMap; 
	private Map<IASTBldInfExtensionBlockStatement, IExtension> statementToTestExtensionsMap; 
	private ListIterator<String> platformIter;
	private IASTBldInfPrjPlatformsBlockStatement lastPlatformStatement;
	
	private boolean isTestMmpFiles, isTestExports, isTestExtensions;
	private IASTBldInfBlockStatement lastBlockStatement;
	private IASTStatement lastStatement;
	private IASTStatement lastMakStatement;
	private IASTStatement lastTestMakStatement;
	private IASTStatement lastExportStatement;
	private IASTStatement lastTestExportStatement;
	private IASTStatement lastExtensionStatement;
	private IASTStatement lastTestExtensionStatement;
	
	public BldInfStatementUpdater(BldInfView view, List<IViewModification> modifications, List<IMessage> messages) {
		super(view, modifications, messages);
		this.bldInfView = view;
		this.statementToExportMap = new IdentityHashMap<IASTBldInfExportStatement, IExport>();
		this.statementToTestExportMap = new IdentityHashMap<IASTBldInfExportStatement, IExport>();
		this.statementToMakMap = new IdentityHashMap<IASTBldInfMakMakeStatement, IMakMakeReference>();
		this.statementToTestMakMap = new IdentityHashMap<IASTBldInfMakMakeStatement, IMakMakeReference>();
		this.statementToExtensionsMap = new IdentityHashMap<IASTBldInfExtensionBlockStatement, IExtension>();
		this.statementToTestExtensionsMap = new IdentityHashMap<IASTBldInfExtensionBlockStatement, IExtension>();
		
		platformIter = view.getPlatforms().listIterator();

	}
	
	protected void scanStatements() {
		// Make an ordered map of model objects -> existing statements.
		// The ordering in the map will be used to drive the reordering modification
		// later on.  So if the model refers to new objects, store a null entry for those
		// until we later fix them up.
		makToStatementMap = scanStructuredBlocks(
				bldInfView.makToStatementMap, statementToMakMap, 
				bldInfView.getMakMakeReferences(), IASTBldInfPrjMmpfilesBlockStatement.class,
				new MakListConverter(bldInfView, BldInfView.PRJ_MMPFILES_KEYWORD));
		testMakToStatementMap = scanStructuredBlocks(
				bldInfView.testMakToStatementMap, statementToTestMakMap, 
				bldInfView.getTestMakMakeReferences(), IASTBldInfPrjMmpfilesBlockStatement.class,
				new MakListConverter(bldInfView, BldInfView.PRJ_TESTMMPFILES_KEYWORD));
		exportToStatementMap = scanStructuredBlocks(
				bldInfView.exportToStatementMap, statementToExportMap, 
				bldInfView.getExports(), IASTBldInfPrjExportsBlockStatement.class,
				new ExportListConverter(bldInfView, BldInfView.PRJ_EXPORTS_KEYWORD));
		testExportToStatementMap = scanStructuredBlocks(
				bldInfView.testExportToStatementMap, statementToTestExportMap, 
				bldInfView.getTestExports(), IASTBldInfPrjExportsBlockStatement.class,
				new ExportListConverter(bldInfView, BldInfView.PRJ_TESTEXPORTS_KEYWORD));
		extensionToStatementMap = scanStructuredBlocks(
				bldInfView.extensionToStatementMap, statementToExtensionsMap, 
				bldInfView.getExtensions(), IASTBldInfPrjExtensionsBlockStatement.class,
				new ExtensionListConverter(bldInfView, BldInfView.PRJ_EXTENSIONS_KEYWORD));
		testExtensionToStatementMap = scanStructuredBlocks(
				bldInfView.testExtensionToStatementMap, statementToTestExtensionsMap, 
				bldInfView.getTestExtensions(), IASTBldInfPrjExtensionsBlockStatement.class,
				new ExtensionListConverter(bldInfView, BldInfView.PRJ_TESTEXTENSIONS_KEYWORD));
	}
	
	/**
	 * Update a MMP statement from the model.
	 * @return false: statement was deleted, true: statement was retained
	 */
	private boolean updateMakMakeStatement(IASTBldInfMakMakeStatement makmake, boolean test) {
		if (!test) {
			return applyStructuredItemChange(bldInfView.getMakMakeReferences(),
					makmake,
					new MakListConverter(bldInfView, BldInfView.PRJ_MMPFILES_KEYWORD),
					makToStatementMap,
					statementToMakMap) != null;
		} else {
			return applyStructuredItemChange(bldInfView.getTestMakMakeReferences(),
					makmake,
					new MakListConverter(bldInfView, BldInfView.PRJ_TESTMMPFILES_KEYWORD),
					testMakToStatementMap,
					statementToTestMakMap) != null;
		}
	}
	
	/**
	 * Update an export statement from the model.
	 * @return false: statement was deleted, true: statement was retained
	 */
	private boolean updateExportStatement(IASTBldInfExportStatement export, boolean test) {
		if (!test) {
			return applyStructuredItemChange(bldInfView.getExports(),
					export,
					new ExportListConverter(bldInfView, BldInfView.PRJ_EXPORTS_KEYWORD),
					exportToStatementMap,
					statementToExportMap) != null;
		} else {
			return applyStructuredItemChange(bldInfView.getTestExports(),
					export,
					new ExportListConverter(bldInfView, BldInfView.PRJ_TESTEXPORTS_KEYWORD),
					testExportToStatementMap,
					statementToTestExportMap) != null;
			
		}
	}
	
	/**
	 * Update an extension statement from the model.
	 * @return false: statement was deleted, true: statement was retained
	 */
	private boolean updateExtensionStatement(IASTBldInfExtensionBlockStatement extension, boolean test) {
		if (!test) {
			return applyStructuredItemChange(bldInfView.getExtensions(),
					extension,
					new ExtensionListConverter(bldInfView, BldInfView.PRJ_EXTENSIONS_KEYWORD),
					extensionToStatementMap,
					statementToExtensionsMap) != null;
		} else {
			return applyStructuredItemChange(bldInfView.getTestExtensions(),
					extension,
					new ExtensionListConverter(bldInfView, BldInfView.PRJ_TESTEXTENSIONS_KEYWORD),
					testExtensionToStatementMap,
					statementToTestExtensionsMap) != null;
			
		}
	}

	/**
	 * update a string list.
	 */
	protected boolean updateStringListStatement(IASTListHolder<IASTLiteralTextNode> statement,
			List<String> list,
			ListIterator<String> listIterator) { 
		return updateListStatement(statement, list, listIterator,   
				new PlatformStatementListConverter());
	}
	
	@Override
	public void update() {
		resetInsertPoints();
		super.update();
	}

	protected void doUpdateStatement(IASTStatement statement) {
		if (lastStatement != null && lastStatement.getSourceRegion().getFirstDocument() != statement.getSourceRegion().getFirstDocument()) {
			resetInsertPoints();
		}
		if (statement instanceof IASTBldInfPrjPlatformsBlockStatement) {
			IASTBldInfPrjPlatformsBlockStatement platformsStatement = (IASTBldInfPrjPlatformsBlockStatement) statement;
			lastPlatformStatement = platformsStatement;
			if (updateStringListStatement(platformsStatement, 
					bldInfView.getPlatforms(), platformIter)) {
				lastPlatformStatement = null;
			} else {
				lastStatement = statement;
				lastBlockStatement = null;
			}
		}
		else if (statement instanceof IASTBldInfPrjExportsBlockStatement) {
			if (BldInfView.PRJ_EXPORTS_KEYWORD.equalsIgnoreCase(
					((IASTBldInfPrjExportsBlockStatement) statement).getKeywordName())) {
				isTestExports = false;
				lastExportStatement = statement;
			} else {
				isTestExports = true;
				lastTestExportStatement = statement;
			}
			lastBlockStatement = (IASTBldInfBlockStatement) statement;
			lastStatement = statement;
			changeStatement(statement);
		}
		else if (statement instanceof IASTBldInfExportStatement) {
			if (updateExportStatement((IASTBldInfExportStatement) statement, isTestExports)) {
				lastStatement = statement;
				if (!isTestExports)
					lastExportStatement = statement;
				else
					lastTestExportStatement = statement;
			}
		}
		else if (statement instanceof IASTBldInfPrjMmpfilesBlockStatement) {
			if (BldInfView.PRJ_MMPFILES_KEYWORD.equalsIgnoreCase(
					((IASTBldInfPrjMmpfilesBlockStatement) statement).getKeywordName())) {
				isTestMmpFiles = false;
				lastMakStatement = statement;
			} else {
				isTestMmpFiles = true;
				lastTestMakStatement = statement;
			}
			lastBlockStatement = (IASTBldInfBlockStatement) statement;
			lastStatement = statement;
			changeStatement(statement);
		}
		else if (statement instanceof IASTBldInfMakMakeStatement) {
			if (updateMakMakeStatement((IASTBldInfMakMakeStatement) statement, isTestMmpFiles)) {
				lastStatement = statement;
				if (!isTestMmpFiles)
					lastMakStatement = statement;
				else
					lastTestMakStatement = statement;
			}
		}
		else if (statement instanceof IASTBldInfPrjExtensionsBlockStatement) {
			if (BldInfView.PRJ_EXTENSIONS_KEYWORD.equalsIgnoreCase(
					((IASTBldInfPrjExtensionsBlockStatement) statement).getKeywordName())) {
				isTestExtensions = false;
				lastExtensionStatement = statement;
			} else {
				isTestExtensions = true;
				lastTestExtensionStatement = statement;
			}
			lastBlockStatement = (IASTBldInfBlockStatement) statement;
			lastStatement = statement;
			changeStatement(statement);
		}
		else if (statement instanceof IASTBldInfExtensionBlockStatement) {
			if (updateExtensionStatement((IASTBldInfExtensionBlockStatement) statement, isTestExtensions)) {
				lastStatement = statement;
				if (!isTestExtensions)
					lastExtensionStatement = statement;
				else
					lastTestExtensionStatement = statement;
			}
		}
		else {
			// ignore
			lastStatement = statement;
		}
	}

	private void resetInsertPoints() {
		lastStatement = null;
		lastBlockStatement = null;
		lastMakStatement = null;
		lastTestMakStatement = null;
		lastExportStatement = null;
		lastTestExportStatement = null;
		lastExtensionStatement = null;
		lastTestExtensionStatement = null;
	}
	
	class NewMakListConverter extends MakListConverter {

		private final boolean isTest;

		NewMakListConverter(BldInfView bldInfView, boolean isTest) {
			super(bldInfView, isTest ? BldInfView.PRJ_TESTMMPFILES_KEYWORD : BldInfView.PRJ_MMPFILES_KEYWORD);
			this.isTest = isTest;
		}
		
		@Override
		public Pair<IASTNode, IASTNode> getInsertAnchors() {
			IASTNode after = isTest ? lastTestMakStatement : lastMakStatement;
			return adjustForConditionalBlocks(after);
		}


		@Override
		public IASTStatement createContextStatement(IMakMakeReference model) {
			IASTNode after = isTest ? lastTestMakStatement : lastMakStatement;
			if (after != null) {
				return null;
			}
			if (isTest ? lastTestMakStatement != null : lastMakStatement != null) {
				return null;
			}
			/*
			if (lastBlockStatement instanceof IASTBldInfPrjMmpfilesBlockStatement 
					&& ((IASTBldInfPrjMmpfilesBlockStatement) lastBlockStatement).getKeywordName().equalsIgnoreCase(sectionName)) {
				return null;
			}*/
			lastBlockStatement = ASTBldInfFactory.createBldInfBlockStatement(sectionName);
			lastStatement = null;
			if (isTest) {
				lastTestMakStatement = lastBlockStatement;
			} else {
				lastMakStatement = lastBlockStatement;
			}
			return lastBlockStatement;
		}
		
		@Override
		public IASTBldInfMakMakeStatement toNode(IMakMakeReference mak) {
			IASTBldInfMakMakeStatement stmt = super.toNode(mak);
			if (stmt != null) {
				lastStatement = stmt;
				if (isTest) {
					lastTestMakStatement = lastStatement;
				} else {
					lastMakStatement = lastStatement;
				}
			}
			return stmt;
		}
		
	}

	class NewExportListConverter extends ExportListConverter {

		private final boolean isTest;

		NewExportListConverter(BldInfView bldInfView, boolean isTest) {
			super(bldInfView, isTest ? BldInfView.PRJ_TESTEXPORTS_KEYWORD : BldInfView.PRJ_EXPORTS_KEYWORD);
			this.isTest = isTest;
		}
		
		@Override
		public Pair<IASTNode, IASTNode> getInsertAnchors() {
			IASTNode after = isTest ? lastTestExportStatement : lastExportStatement;
			return adjustForConditionalBlocks(after);
		}

		@Override
		public IASTStatement createContextStatement(IExport model) {
			if (lastBlockStatement instanceof IASTBldInfPrjExportsBlockStatement 
					&& ((IASTBldInfPrjExportsBlockStatement) lastBlockStatement).getKeywordName().equalsIgnoreCase(sectionName)) {
				return null;
			}
			if (isTest ? lastTestExportStatement != null : lastExportStatement != null) {
				return null;
			}
			lastBlockStatement = ASTBldInfFactory.createBldInfBlockStatement(sectionName);
			lastStatement = null;
			if (isTest) {
				lastTestExportStatement = lastBlockStatement;
			} else {
				lastExportStatement = lastBlockStatement;
			}

			return lastBlockStatement;
		}
		
		@Override
		public IASTBldInfExportStatement toNode(IExport export) {
			IASTBldInfExportStatement stmt = super.toNode(export);
			if (stmt != null) {
				lastStatement = stmt;
				if (isTest) {
					lastTestExportStatement = lastStatement;
				} else {
					lastExportStatement = lastStatement;
				}
			}
			return stmt;
		}
		
		
	}

	class NewExtensionListConverter extends ExtensionListConverter {

		private final boolean isTest;

		NewExtensionListConverter(BldInfView bldInfView, boolean isTest) {
			super(bldInfView, isTest ? BldInfView.PRJ_TESTEXTENSIONS_KEYWORD : BldInfView.PRJ_EXTENSIONS_KEYWORD);
			this.isTest = isTest;
		}
		
		@Override
		public Pair<IASTNode, IASTNode> getInsertAnchors() {
			IASTNode after = isTest ? lastTestExtensionStatement : lastExtensionStatement;
			return adjustForConditionalBlocks(after);
		}

		@Override
		public IASTStatement createContextStatement(IExtension model) {
			if (lastBlockStatement instanceof IASTBldInfPrjExtensionsBlockStatement 
					&& ((IASTBldInfPrjExtensionsBlockStatement) lastBlockStatement).getKeywordName().equalsIgnoreCase(sectionName)) {
				return null;
			}
			if (isTest ? lastTestExtensionStatement != null : lastExtensionStatement != null) {
				return null;
			}
			lastBlockStatement = ASTBldInfFactory.createBldInfBlockStatement(sectionName);
			lastStatement = null;
			if (isTest) {
				lastTestExtensionStatement = lastBlockStatement;
			} else {
				lastExtensionStatement = lastBlockStatement;
			}

			return lastBlockStatement;
		}
		
		@Override
		public IASTBldInfExtensionBlockStatement toNode(IExtension export) {
			IASTBldInfExtensionBlockStatement stmt = super.toNode(export);
			if (stmt != null) {
				lastStatement = stmt;
				if (isTest) {
					lastTestExtensionStatement = lastStatement;
				} else {
					lastExtensionStatement = lastStatement;
				}
			}
			return stmt;
		}
		
		
	}

	private void applyNewBlockStatements() {
		if (lastBlockStatement != null && 
				view.getFilteredTranslationUnit().getMainDocument() != lastBlockStatement.getSourceRegion().getFirstDocument()) {
			resetInsertPoints();
		}
		applyNewStructuredItemStatements(bldInfView.getMakMakeReferences(),
				new NewMakListConverter(bldInfView, false),
				makToStatementMap);
		applyNewStructuredItemStatements(bldInfView.getExports(), 
				new NewExportListConverter(bldInfView, false),
				exportToStatementMap);
		applyNewStructuredItemStatements(bldInfView.getExtensions(), 
				new NewExtensionListConverter(bldInfView, false),
				extensionToStatementMap);
		applyNewStructuredItemStatements(bldInfView.getTestMakMakeReferences(), 
				new NewMakListConverter(bldInfView, true),
				testMakToStatementMap);
		applyNewStructuredItemStatements(bldInfView.getTestExports(), 
				new NewExportListConverter(bldInfView, true),
				testExportToStatementMap);
		applyNewStructuredItemStatements(bldInfView.getTestExtensions(), 
				new NewExtensionListConverter(bldInfView, true),
				testExtensionToStatementMap);

	}
	
	/**
	 * Find the insert anchors given this nominal 'after' anchor by using the
	 * anchor if it's not in a conditional block and inserting before the next
	 * block statement otherwise.
	 * @param after
	 * @return insert anchors
	 */
	public Pair<IASTNode, IASTNode> adjustForConditionalBlocks(IASTNode after) {
		IASTNode before = null;
		if (after != null && view.isLangNodeInConditionalBlock(after)) {
			IASTListNode list = ((IASTListNode)after.getParent());
			IASTNode node = null;
			int idx = list.indexOf(after);
			for (Iterator<IASTNode> nodeIter = list.listIterator(idx);
				nodeIter.hasNext();) {
				node = nodeIter.next();
				if (node instanceof IASTBldInfBlockStatement)
					break;
				idx++;
				node = null;
			}
			if (node != null) {
				before = node;
				after = null;
			} else {
				after = null;
			}
		}
		return new Pair(before, after);
	}

	static class PlatformStatementListConverter implements ListStatementListConverter<IASTLiteralTextNode, String> {


		public IASTListHolder<IASTLiteralTextNode> createNewListStatement() {
			return ASTBldInfFactory.createBldInfPrjPlatformsBlockStatement(new String[0]);
		}

		public boolean allowEmptyStatements() {
			return true;
		}

		public boolean canAddToStatement(String model) {
			return true;
		}

		public boolean changeRequiresNewContext(String existing,
				String newElement) {
			return false;
		}

		public IASTStatement createContextStatement(String model) {
			return null;
		}

		public String fromNode(IASTLiteralTextNode node) {
			return node.getValue();
		}

		public IASTLiteralTextNode toNode(String elementObj) {
			return ASTFactory.createPreprocessorLiteralTextNode(elementObj);
		}
		
		public Pair<IASTNode, IASTNode> getInsertAnchors() {
			return null;
		}
		
		public void associateContextStatement(IASTStatement stmt,
				IASTStatement contextStmt) {
			
		}
		
	}
	private void applyNewListArgumentStatements() {
		applyNewListArgumentStatement(platformIter,
				lastPlatformStatement,
				new PlatformStatementListConverter());
	}
	
	/**
	 * Add new statements to the bld.inf.  The previous calls to
	 * #updateStatement() have already updated the view to delete
	 * view model elements corresponding to previously existing nodes in the TU,
	 * so anything left in the view is new.  Once finished, everything
	 * in the view should be reverted to empty. 
	 */
	protected void applyNewStatements() {
		bldInfView.updateCurrentDirectory(bldInfView.getModel().getPath());

		applyNewListArgumentStatements();
		applyNewBlockStatements();
	}
	
	protected void fixupOrdering() {
		applyReordering(makToStatementMap, bldInfView.makToStatementMap, 
				new MakListConverter(bldInfView, BldInfView.PRJ_MMPFILES_KEYWORD));
		applyReordering(testMakToStatementMap, bldInfView.testMakToStatementMap, 
				new MakListConverter(bldInfView, BldInfView.PRJ_TESTMMPFILES_KEYWORD));
		applyReordering(exportToStatementMap, bldInfView.exportToStatementMap, 
				new ExportListConverter(bldInfView, BldInfView.PRJ_EXPORTS_KEYWORD));
		applyReordering(testExportToStatementMap, bldInfView.testExportToStatementMap, 
				new ExportListConverter(bldInfView, BldInfView.PRJ_TESTEXPORTS_KEYWORD));
		applyReordering(extensionToStatementMap, bldInfView.extensionToStatementMap, 
				new ExtensionListConverter(bldInfView, BldInfView.PRJ_EXTENSIONS_KEYWORD));
		applyReordering(testExtensionToStatementMap, bldInfView.testExtensionToStatementMap, 
				new ExtensionListConverter(bldInfView, BldInfView.PRJ_TESTEXTENSIONS_KEYWORD));
	}
	
}
